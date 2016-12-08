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

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult res = e.typecheck(E);
        Substitution sub = res.t.unify(Type.BOOL);
        return TypeResult.of(sub.compose(res.s), Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value val = e.eval(s);
        if (val instanceof BoolValue)
            return new BoolValue(!((BoolValue) val).b);
        throw new RuntimeError("Runtime: operand of not is not boolean");
    }
}
