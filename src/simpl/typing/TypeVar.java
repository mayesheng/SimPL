package simpl.typing;

import simpl.parser.Symbol;
import simpl.parser.ast.Sub;

public class TypeVar extends Type {
    /* for unique naming of type variables */
    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        if (t.equals(this)) {
            return Substitution.IDENTITY;
        } else if (!t.contains(this)) {
            if (!(t instanceof TypeVar))
                return Substitution.of(this, t);
            TypeVar tv1 = this;
            TypeVar tv2 = (TypeVar) t;

            /* make substitution consistent with type variable order */
            if (tv1.name.toString().compareTo(tv2.name.toString()) < 0)
                return Substitution.of(tv2, tv1);
            else
                return Substitution.of(tv1, tv2);
        }
        throw new TypeCircularityError(this);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        /* both are type variables: only compare name for them */
        return this.name.equals(tv.name);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this.contains(a) ? t : this;
    }
}
