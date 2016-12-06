package simpl.typing;

import javax.swing.plaf.TabbedPaneUI;

final class IntType extends Type {

    protected IntType() {
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        } else if (t instanceof IntType) {
            return Substitution.IDENTITY;
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return Type.INT;
    }

    public String toString() {
        return "int";
    }
}
