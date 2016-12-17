package simpl.interpreter.lib;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class snd extends FunValue {

    public snd() {
        super(Env.empty, Symbol.symbol("arg snd"),
                new Expr() {
                    public TypeResult typecheck(TypeEnv E) throws TypeError {
                        return TypeResult.of(new TypeVar(false));
                    }
                    public Value eval(State s) throws RuntimeError {
                        Value val = s.E.get(Symbol.symbol("arg snd"));
                        if (val instanceof PairValue)
                            return ((PairValue) val).v2;
                        throw new RuntimeError("Runtime: invalid arg of snd");
                    }
                    public boolean isPure() {
                        return false;
                    }
                });
    }
}
