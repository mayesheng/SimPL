package simpl.parser.ast;

import simpl.typing.*;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2)
     * ==> (G|-u1 cmp u2 -> G|-(e1 cmp e2):BOOL,q1Uq2U{t1=t2}
     * and t should be equality
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        if (!lRes.t.isEqualityType() || !rRes.t.isEqualityType())
            throw new TypeMismatchError("Type mismatch:" +
                    "cannot compare types that are not equality type.");
        Substitution sub = lRes.s.compose(
                rRes.s.compose(
                        lRes.t.unify(rRes.t)));
        return TypeResult.of(sub, Type.BOOL);
    }
}
