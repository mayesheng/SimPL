package simpl.typing;

import com.sun.org.apache.regexp.internal.RE;

public final class RefType extends Type {

    public Type t;

    public RefType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        } else if (t instanceof RefType) {
            return this.t.unify(((RefType) t).t);
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new RefType(this.t.replace(a, t));
    }

    @Override
    public boolean typeEquals(Type t) {
        return t instanceof RefType && this.t.equals(((RefType) t).t);
    }

    public String toString() {
        return t + " ref";
    }
}
