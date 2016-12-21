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
            ArrowType ct = (ArrowType)t;
            return t1.unify(ct.t1).compose(t2.unify(ct.t2));
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

    @Override
    public boolean typeEquals(Type t) {
        if (t instanceof ArrowType) {
            ArrowType arrowType = (ArrowType)t;
            return t1.equals(arrowType.t1) && t2.equals(arrowType.t2);
        } else {
            return false;
        }
    }

    public String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
