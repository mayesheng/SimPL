package simpl.parser.ast;

import javafx.beans.binding.BooleanBinding;
import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2)
     * ==> (G|-u1 andalso u2->(u1 andalso u2):BOOL,q1Uq2U{t1=BOOL}U{t2=BOOL}
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lRes = l.typecheck(E);
        TypeResult rRes = r.typecheck(E);
        Substitution sub = lRes.s.compose(
                rRes.s.compose(
                        lRes.t.unify(Type.BOOL).compose(
                                rRes.t.unify(Type.BOOL))));
        return TypeResult.of(sub, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        /* short-circuit eval */
        Value lval = l.eval(s);
        if (!(lval instanceof BoolValue))
            throw new RuntimeError("Runtime: nonbool operand in andalso");
        if (!((BoolValue) lval).b)
            return new BoolValue(false);
        Value rval = r.eval(s);
        if (!(rval instanceof BoolValue))
            throw new RuntimeError("Runtime: nonbool operand in andalso");
        return rval;
    }
}
