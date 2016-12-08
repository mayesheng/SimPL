package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    /* (G,x:a|-u -> G,x:a|-u:t,q2)
     * ==> (G|-rec u -> G|-rec e:t, q2
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeVar xTpe = new TypeVar(true);
        TypeResult eRes = e.typecheck(TypeEnv.of(E, x, xTpe));
        Substitution sub = eRes.s.compose(
                eRes.s.apply(xTpe).unify(eRes.s.apply(eRes.t)));
        return TypeResult.of(sub, sub.apply(eRes.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return e.eval(State.of(new Env(s.E, x, new RecValue(s.E,x,e)), s.M, s.p));
    }
}
