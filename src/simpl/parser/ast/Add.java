package simpl.parser.ast;

import simpl.interpreter.*;

public class Add extends ArithExpr {

    public Add(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " + " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lval = l.eval(s);
        Value rval = r.eval(s);
        if (lval instanceof IntValue && rval instanceof IntValue)
            return new IntValue(((IntValue) lval).n + ((IntValue) rval).n);
        throw new RuntimeError("Runtime: operands of add is not integer");
    }
}
