package simpl.parser.ast;

import com.sun.org.apache.xpath.internal.operations.Bool;
import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    /* (G|-u1->G|-e1:t1,q1; G|-u2->G|-e2:t2,q2)
     * ==> (G|-while u1 do u2 -> G|-while e1 do e2:UNIT,q1Uq2U{t1=BOOL}
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e1Res = e1.typecheck(E);
        TypeResult e2Res = e2.typecheck(E);
        Substitution sub = e1Res.s.compose(
                e2Res.s.compose(
                        e1Res.t.unify(Type.BOOL)));
        return TypeResult.of(sub, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value predVal = e1.eval(s);
        /* eval according to spec, may not be efficient */
        if (predVal instanceof BoolValue) {
            if (((BoolValue) predVal).b)
                return new Seq(e2, this).eval(s);
            else
                return Value.UNIT;
        }
        throw new RuntimeError("Runtime: nonbool value in loop predicate");
    }

    @Override
    public boolean isPure() {
        return e1.isPure() && e2.isPure();
    }
}
