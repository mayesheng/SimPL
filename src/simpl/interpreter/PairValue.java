package simpl.interpreter;

import simpl.parser.ast.Pair;

public class PairValue extends Value {

    public final Value v1, v2;

    public PairValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return "pair@" + v1 + "@" + v2;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PairValue &&
                ((PairValue) other).v1.equals(this.v1) &&
                ((PairValue) other).v2.equals(this.v2);

    }
}
