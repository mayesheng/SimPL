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
        } else if (t instanceof PairType) {
            PairType ct = (PairType) t;
            Substitution sub1 = t1.unify(ct.t1);
            Substitution sub2 = t2.unify(ct.t2);
            Type tpe1 = sub2.apply(t1);
            Type tpe2 = sub1.apply(t2);
            return tpe1.unify(ct.t1).compose(tpe2.unify(ct.t2));
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

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
