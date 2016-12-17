package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class hd extends FunValue {

    public hd() {
        super(Env.empty, Symbol.symbol("arg hd"),
                new Expr() {
                    public TypeResult typecheck(TypeEnv E) throws TypeError {
                        return TypeResult.of(new TypeVar(false));
                    }
                    public Value eval(State s) throws RuntimeError {
                        Value val = s.E.get(Symbol.symbol("arg hd"));
                        if (val instanceof ConsValue)
                            return ((ConsValue) val).v1;
                        throw new RuntimeError("Runtime: invalid arg of hd");
                    }
                    public boolean isPure() {
                        return false;
                    }
                });
    }
}
