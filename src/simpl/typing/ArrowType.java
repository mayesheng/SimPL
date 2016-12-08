package simpl.typing;

import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaServerTube;

public final class ArrowType extends Type {

    public Type t1, t2;

    public ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        } else if (t instanceof ArrowType) {
            ArrowType ct = (ArrowType) t;
            /* need mutual-info to guide further unification */
            Substitution sub1 = t1.unify(ct.t1);
            Substitution sub2 = t2.unify(ct.t2);
            Type tpe1 = sub2.apply(t1);
            Type tpe2 = sub1.apply(t2);
            return tpe1.unify(ct.t1).compose(tpe2.unify(ct.t2));
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new ArrowType(t1.replace(a, t), t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
