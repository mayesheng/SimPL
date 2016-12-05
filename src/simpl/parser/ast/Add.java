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
        Value vl = l.eval(s);
        Value vr = r.eval(s);
        if (vl instanceof IntValue && vr instanceof IntValue) {
            return new IntValue(((IntValue) vl).n + ((IntValue) vr).n);
        } else {
            throw new RuntimeError("Arguments of Add should be Int");
        }
    }
}
