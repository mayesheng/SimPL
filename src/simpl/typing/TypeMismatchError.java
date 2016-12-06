package simpl.typing;

public class TypeMismatchError extends TypeError {

    private static final long serialVersionUID = -9010997809568642250L;

    public TypeMismatchError() {
        super("Mismatch");
    }

    public TypeMismatchError(Type lhs, Type rhs) {
        super("Type mismatch: cannot unify" + lhs + " and " + rhs);
    }
}
