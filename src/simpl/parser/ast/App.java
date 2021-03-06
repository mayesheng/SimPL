package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.interpreter.PairValue;
import simpl.interpreter.ConsValue;
import simpl.interpreter.BoolValue;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    /* (G|-u1->e1:t1,q1; G|-u2->e2:t2,q2)
     * ==> (G|-u1 u2 -> (e1 e2):a, q1Uq2U{t1=t2->a}*/
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult t1=l.typecheck(E);
        TypeResult t2=r.typecheck(E);
        if (t1.t instanceof TypeVar) {
            Substitution s1=t1.t.unify(new ArrowType(t2.t,new TypeVar(false)));
            TypeEnv.compose(s1);
            return TypeResult.of(t1.s.compose(t2.s.compose(s1)),TypeEnv.sub.apply(((ArrowType)s1.apply(t1.t)).t2));
        }
        if (t1.t instanceof ArrowType) {
            Substitution s2=((ArrowType)t1.t).t1.unify(t2.t);
            TypeEnv.compose(s2);
            return TypeResult.of(t1.s.compose(t2.s.compose(s2)), ((ArrowType)s2.apply((ArrowType)t1.t)).t2);
        }
        throw new TypeError("not match");
//        TypeResult e1Res = l.typecheck(E);
//        TypeResult e2Res = r.typecheck(E);
//        Type e1Tpe = e2Res.s.apply(e1Res.t);
//        Type e2Tpe = e1Res.s.apply(e2Res.t);
//        Type appTpe = new TypeVar(true);
//        Substitution sub0 = e1Tpe.unify(new ArrowType(e2Tpe, appTpe));
//        Substitution sub1 = e1Res.s.compose(sub0);
//        Substitution sub2 = e2Res.s.compose(sub0);
//        Substitution sub = sub1.compose(sub2);
//        return TypeResult.of(sub, sub.apply(appTpe));
//        Substitution sub = e1Res.s.compose(
//                e2Res.s.compose(
//                        e1Tpe.unify(new ArrowType(e2Tpe, appTpe))));
        //return TypeResult.of(sub, sub.apply(appTpe));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lVal = l.eval(s);
        Value rVal = r.eval(s);
        if (lVal instanceof FunValue) {
            FunValue fv = (FunValue) lVal;
            return fv.e.eval(State.of(new Env(fv.E, fv.x, rVal), s.M, s.p));
        } else if (lVal instanceof PairValue && rVal instanceof BoolValue) {
            PairValue t0 = (PairValue) lVal;
            BoolValue v0 = (BoolValue) rVal;
            if (v0.b)
                return t0.v1;
            else
                return t0.v2;
        } else if (lVal instanceof ConsValue){
            ConsValue t0=(ConsValue) lVal;
            BoolValue v0=(BoolValue) rVal;
            if (v0.b)
                return t0.v1;
            else
                return t0.v2;
        } else {
            throw new RuntimeError("Error!");
        }
    }
}
