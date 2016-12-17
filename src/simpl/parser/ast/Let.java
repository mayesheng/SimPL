package simpl.parser.ast;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    /* (G|-u2->G|-e1:t1,q1; G,x:t1|-u2->G,x:t1|-e2:t2,q2)
     * ==> (G|-let x=u1 in u2 end -> G|-let x=e1 in e2 end:t2, q1Uq2
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e1Res = e1.typecheck(E);
        TypeResult e2Res = e2.typecheck(TypeEnv.of(E, x, e1Res.t));
        Substitution sub = e1Res.s.compose(e2Res.s);
        return TypeResult.of(sub, sub.apply(e2Res.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value e1Val = e1.eval(s);
        return e2.eval(State.of(new Env(s.E, x, e1Val), s.M, s.p));
    }

    @Override
    public boolean isPure() {
        return e1.isPure() && e2.isPure();
    }
}
