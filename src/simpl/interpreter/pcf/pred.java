package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.IntegerLiteral;
import simpl.parser.ast.Name;
import simpl.parser.ast.Sub;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class pred extends FunValue {

    public pred() {
        super(
                Env.empty,
                Symbol.symbol("arg pred"),
                new Sub(
                        new Name(Symbol.symbol("arg pred")),
                        new IntegerLiteral(1)));
    }
}
