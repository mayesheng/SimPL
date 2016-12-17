package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

        // start GC
        if (true) {
            // mark all variables and related values
            for (Env curEnv = s.E; curEnv != Env.empty; curEnv = curEnv.getPrevEnv()) {
                for (Value v = curEnv.getValue(); v instanceof RefValue && !v.mark; v = s.M.get(((RefValue) v).p)) {
                    v.mark = true;
                }
                curEnv.getValue().mark = true;
            }
            // sweep through memory refs
            for (Iterator<Mem.Entry<Integer, Value>> it = s.M.entrySet().iterator();
                    it.hasNext(); ) {
                Mem.Entry<Integer, Value> entry = it.next();
                if (!entry.getValue().mark) {
                    it.remove();
                }
            }
        }




        // put evaluated value to current ptr
        int stPtr = s.p.get();
        s.M.put(stPtr, val);
        // inc memory ptr by 1
        s.p.set(stPtr + 1);
        return new RefValue(stPtr);

    }
}
