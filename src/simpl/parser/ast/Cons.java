package simpl.parser.ast;

import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    /* (G|-u1->e1:a,q1; G|-u2->e2:b,q2)
     * ==> (G|-cons(u1,u2) -> G|-cons(e1,e2):b,q1Uq2U{b=List(a)}
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        Substitution sub1 = lRes.s.compose(rRes.s);
        Substitution sub = sub1.compose(sub1.apply(rRes.t).unify(sub1.apply(new ListType(lRes.t))));
        return TypeResult.of(sub, sub.apply(rRes.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lval = l.eval(s);
        Value rval = r.eval(s);
        return new ConsValue(lval, rval);
    }
}
