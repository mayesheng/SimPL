package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

import java.util.concurrent.ThreadPoolExecutor;

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    /* (G|-u1->G|-e1:t1,q1; G|-u2->G|-e2:t2,q2)
     * ==> (G|-u1 orelse u2 -> G|-e1 orelse e2:BOOL,q1Uq2U{t1=t2=BOOL})
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        Substitution sub = lRes.s.compose(
                rRes.s.compose(
                        lRes.t.unify(Type.BOOL).compose(
                                rRes.t.unify(Type.BOOL))));
        return TypeResult.of(sub, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue lval = (BoolValue) l.eval(s);
        if (lval.b)
            return new BoolValue(true);
        return r.eval(s);
    }
}
