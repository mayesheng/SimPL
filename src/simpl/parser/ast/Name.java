package simpl.parser.ast;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    /* (G(x)=t) ==> (G|-x=>x:t,{}) */
    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        if (E.get(x) != null) {
            return TypeResult.of(Substitution.IDENTITY, E.get(x));
        }
        throw new TypeMismatchError("Type checking:" +
                " missing name in typing context" + this.toString());
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value val = s.E.get(x);
        // case of recursive call & normal name
        if (val instanceof RecValue) {
            return new Rec(x, ((RecValue) val).e).eval(State.of(((RecValue) val).E, s.M , s.p));
        } else {
            return val;
        }
    }
}
