package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class ArithExpr extends BinaryExpr {

    public ArithExpr(Expr l, Expr r) {
        super(l, r);
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2)
     * ==> (G|-u1 arith u2 -> G|-e1 arith e2:INT,q1Uq2U{t1=t2=INT})
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        Substitution sub = lRes.s.compose(
                rRes.s.compose(
                        lRes.t.unify(Type.INT).compose(
                                rRes.t.unify(Type.INT))));
        return TypeResult.of(sub, Type.INT);
    }

    @Override
    public boolean isPure() {
        return l.isPure() && r.isPure();
    }
}
