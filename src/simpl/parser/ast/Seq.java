package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2)
     * ==> (G|-u1;u2->(u1;u2):t2,q1Uq2)
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        Substitution sub = lRes.s.compose(rRes.s);
        return TypeResult.of(sub, sub.apply(rRes.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        l.eval(s);
        return r.eval(s);
    }

    @Override
    public boolean isPure() {
        return l.isPure() && r.isPure();
    }
}
