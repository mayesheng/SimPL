package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    /* (G|-u -> G|-e:t,q) => (G|-ref u -> G|-ref e:Ref(t),q) */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult eTpe = e.typecheck(E);
        Substitution sub = eTpe.s;
        return TypeResult.of(sub, new RefType(sub.apply(eTpe.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value val = e.eval(s);
        // put evaluated value to current ptr
        int stPtr = s.p.get();
        s.M.put(stPtr, val);
        // inc memory ptr by 1
        s.p.set(stPtr + 1);
        return new RefValue(stPtr);
    }
}
