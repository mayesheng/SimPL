package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Div extends ArithExpr {

    public Div(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " / " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lval = l.eval(s);
        Value rval = r.eval(s);
        if (lval instanceof IntValue && rval instanceof IntValue)
            return new IntValue(((IntValue) lval).n / ((IntValue) rval).n);
        throw new RuntimeError("Runtime: operands of div is not integer");
    }
}
