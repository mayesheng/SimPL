package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

import simpl.typing.TypeVar;

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2; G|-u3->G|-e3:t3,q3
     * ==> (G|-if u1 then u2 else u3 ->
     *      if e1 then e2 else e3: t2, q1Uq2Uq3U{t1=BOOL}U{t2=t3}
     */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e1Res = e1.typecheck(E);
        TypeResult e2Res = e2.typecheck(E);
        TypeResult e3Res = e3.typecheck(E);
        Type e1Tpe = e2Res.s.compose(e3Res.s).apply(e1Res.t);
        Type e2Tpe = e3Res.s.compose(e1Res.s).apply(e2Res.t);
        Type e3Tpe = e1Res.s.compose(e2Res.s).apply(e3Res.t);
        Substitution sub = e1Res.s.compose(
                e2Res.s.compose(
                        e3Res.s.compose(
                            e1Tpe.unify(Type.BOOL).compose(
                                    e2Tpe.unify(e3Tpe)))));
        return TypeResult.of(sub, sub.apply(e2Tpe));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value e1Val = e1.eval(s);
        if (!(e1Val instanceof BoolValue))
            throw new RuntimeError("Runtime: nonbool value in cond predicate");
        if (((BoolValue) e1.eval(s)).b)
            return e2.eval(s);
        else
            return e3.eval(s);
    }
}
