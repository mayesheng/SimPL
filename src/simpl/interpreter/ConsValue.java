package simpl.interpreter;

import simpl.parser.ast.Cons;

public class ConsValue extends Value {

    public final Value v1, v2;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    private int getLength() {
        if (v2 instanceof NilValue)
            return 1;
        else if (v2 instanceof ConsValue)
            return 1 + ((ConsValue) v2).getLength();
        else
            return -1;
    }

    public String toString() {
        return "list@" + String.valueOf(getLength());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ConsValue &&
                ((ConsValue) other).v1.equals(this.v1) &&
                ((ConsValue) other).v2.equals(this.v2);
    }
}
