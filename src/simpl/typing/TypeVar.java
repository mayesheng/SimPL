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
        if (!(t instanceof TypeVar) && (t.contains(this))) {
            throw new TypeCircularityError();
        }

        Substitution sub1 = Substitution.of(this, t);
        try {
            Type t1 = TypeEnv.sub.apply(this);
            if (t1 != this) {
                Substitution s2 = t1.unify(t);
                return sub1.compose(s2);
            }
        } catch (TypeError e) {
            throw new TypeCircularityError(this);
        }
        return sub1;
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
    public boolean typeEquals(Type t) {
        return t instanceof TypeVar && this.name.equals(((TypeVar) t).name);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this.contains(a) ? t : this;
    }
}
