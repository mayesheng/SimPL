package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    /* (G|-u->e:t,q) ==> (G|-!u -> !e:t',qU{t=ref(t')} */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult eTpe = e.typecheck(E);
        TypeVar tv = new TypeVar(true);
        Substitution sub = eTpe.s.compose(
                eTpe.t.unify(new RefType(tv)));
        return TypeResult.of(sub, sub.apply(tv));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        /* extract ptr from RefType */
        Integer ptr = ((RefValue) e.eval(s)).p;
        return s.M.get(ptr);
    }
}
