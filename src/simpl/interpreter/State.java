package simpl.interpreter;

public class State {

    public final Env E;
    public final Mem M;
    public final Int p;
    public static FunCache c = new FunCache();

    protected State(Env E, Mem M, Int p) {
        this.E = E;
        this.M = M;
        this.p = p;
    }

    public static State of(Env E, Mem M, Int p) {
        return new State(E, M, p);
    }
}
