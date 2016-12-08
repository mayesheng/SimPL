package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        // TODO: may need more type var here
        TypeVar tv1 = new TypeVar(true);
        TypeVar tv2 = new TypeVar(true);
        E = TypeEnv.of(
                TypeEnv.of(
                        TypeEnv.of(
                                TypeEnv.of(
                                        TypeEnv.of(
                                                TypeEnv.of(
                                                        TypeEnv.of(TypeEnv.empty,
                                                                Symbol.symbol("fst"), new ArrowType(new PairType(tv1, tv2), tv1)),
                                                        Symbol.symbol("snd"), new ArrowType(new PairType(tv1, tv2), tv2)),
                                                Symbol.symbol("hd"), new ArrowType(new ListType(tv1), tv1)),
                                        Symbol.symbol("tl"), new ArrowType(new ListType(tv1), new ListType(tv1))),
                                Symbol.symbol("iszero"), new ArrowType(Type.INT, Type.BOOL)),
                        Symbol.symbol("pred"), new ArrowType(Type.INT, Type.INT)),
                Symbol.symbol("succ"), new ArrowType(Type.INT,Type.INT)
        );
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
