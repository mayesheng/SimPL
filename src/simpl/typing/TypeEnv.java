package simpl.typing;

import simpl.parser.Symbol;

public abstract class TypeEnv {

    public abstract Type get(Symbol x);
    public static Substitution sub=Substitution.IDENTITY;
    public static void compose(final Substitution s) {
        sub = sub.compose(s);
    }

    /* use Java anonymous class: actually a closure! */
    public static TypeEnv of(final TypeEnv E, final Symbol x, final Type t) {
        return new TypeEnv() {
            public Type get(Symbol x1) {
                if (x == x1) {
                    if (sub.apply(t) == null) {
                        return t;
                    }
                    Type t1 = sub.apply(t);
                    while (!t1.typeEquals(t1)) {
                        t1 = sub.apply(t1);
                    }
                    return t1;
                } else
                    return E.get(x1);
            }

            public String toString() {
                return x + ":" + t + ";" + E;
            }
        };
    }

    public static final TypeEnv empty = new TypeEnv() {
        @Override
        public Type get(Symbol x) {
            return null;
        }
    };
}
