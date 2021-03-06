package simpl.parser.ast;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    /* (G,x:a|-u=>e:t,q) ==> (G|-\x.u=>\x:a.u:a->b, qU{t=b}) */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // type check for premise
        Type xTpe = new TypeVar(true);
        TypeResult predRes = e.typecheck(TypeEnv.of(E, x, xTpe));
        // unify and compose for conclusion
        Substitution sub = predRes.s;
        return TypeResult.of(sub,
                new ArrowType(sub.apply(xTpe), sub.apply(predRes.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new FunValue(s.E, x, e);
    }
}
