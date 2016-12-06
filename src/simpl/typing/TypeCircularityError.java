package simpl.typing;

public class TypeCircularityError extends TypeError {

    private static final long serialVersionUID = -5845539927612802390L;

    public TypeCircularityError() {
        super("Circularity");
    }

    public TypeCircularityError(Type t) {
        super("Type circularity: on type variable" + t);
    }
}
