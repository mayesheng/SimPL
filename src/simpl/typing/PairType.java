package simpl.typing;

import simpl.parser.ast.Pair;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return t1.isEqualityType() && t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        }
        else if (t instanceof PairType) {
            PairType pairType = (PairType) t;
            return t1.unify(pairType.t1).compose(t2.unify(pairType.t2));
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new PairType(t1.replace(a, t), t1.replace(a, t));
    }

    @Override
    public boolean typeEquals(Type t) {
        if (t instanceof PairType) {
            PairType tp1 = (PairType)t;
            return t1.equals(tp1.t1) && t2.equals(tp1.t2);
        }
        return false;
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
