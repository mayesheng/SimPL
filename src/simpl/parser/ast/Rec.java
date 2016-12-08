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

//        TypeVar a = new TypeVar(false);
//        TypeResult tr = e.typecheck(TypeEnv.of(E,x,a));
//
//        Type t1 = a;
//        Type t2 = tr.t;
//
//        Substitution substitution = tr.s;
//
//        t1 = substitution.apply(t1);
//        t2 = substitution.apply(t2);
//
//        substitution = t1.unify(t2).compose(substitution);
//
//        Type resultType = substitution.apply(t1);
//
//        return TypeResult.of(substitution,resultType);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        return null;
    }
}
