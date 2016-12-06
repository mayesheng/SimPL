package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        E = TypeEnv.of(
                TypeEnv.of(
                        TypeEnv.of(
                                TypeEnv.of(
                                        TypeEnv.empty,
                                        Symbol.symbol("fst"),
                                        new ArrowType(new TypeVar(true), new TypeVar(true))
                                ),
                                Symbol.symbol("snd"),
                                new ArrowType(new TypeVar(true), new TypeVar(true))
                        ),
                        Symbol.symbol("hd"),
                        new ArrowType(new ListType(new TypeVar(true)), new TypeVar(true))
                ),
                Symbol.symbol("tl"),
                new ArrowType(new ListType(new TypeVar(true)), new TypeVar(true))
        );
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
