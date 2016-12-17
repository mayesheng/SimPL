package simpl.interpreter;

public abstract class Value {

    public static final Value NIL = new NilValue();
    public static final Value UNIT = new UnitValue();

    public boolean mark = false;
    public abstract boolean equals(Object other);
}
