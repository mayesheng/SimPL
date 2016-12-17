package simpl.interpreter;

import simpl.parser.Symbol;
import simpl.parser.ast.Expr;

public class PureFunApp {
    public final Symbol x;
    public final Expr e;
    public final Value arg;

    public PureFunApp(Symbol x, Expr e, Value arg) {
        this.x = x;
        this.e = e;
        this.arg = arg;
    }
}
