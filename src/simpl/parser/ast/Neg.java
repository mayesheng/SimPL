package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult res = e.typecheck(E);
        Substitution sub = res.t.unify(Type.INT);
        return TypeResult.of(sub.compose(res.s), Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value val = e.eval(s);
        if (val instanceof IntValue)
            return new IntValue(((IntValue) val).n);
        throw new RuntimeError("Runtime: operand of neg is not Int");
    }

    @Override
    public boolean isPure() {
        return e.isPure();
    }
}
