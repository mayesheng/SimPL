package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Less extends RelExpr {

    public Less(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " < " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        IntValue lval = (IntValue) l.eval(s);
        IntValue rval = (IntValue) r.eval(s);
        return new BoolValue(lval.n < rval.n);
    }
}
