package simpl.typing;

import java.util.List;

public final class ListType extends Type {

    public Type t;

    public ListType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        return t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        } else if (t instanceof ListType) {
            return this.t.unify(((ListType) t).t);
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
       return new ListType(this.t.replace(a, t));
    }

    @Override
    public boolean typeEquals(Type t) {
        return t instanceof ListType && this.t.equals(((ListType) t).t);
    }

    public String toString() {
        return t + " list";
    }
}
