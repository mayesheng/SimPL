package simpl.interpreter.pcf;

import simpl.interpreter.BoolValue;
import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.*;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class iszero extends FunValue {

    public iszero() {
        super(
                Env.empty,
                Symbol.symbol("arg iszero"),
                new Cond(
                        new Eq(new Name(Symbol.symbol("arg iszero")),
                                new IntegerLiteral(0)),
                        new BooleanLiteral(true),
                        new BooleanLiteral(false)));
    }
}
