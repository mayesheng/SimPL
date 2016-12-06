package simpl.typing;

import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import simpl.parser.ast.Sub;

final class BoolType extends Type {

    protected BoolType() {
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if (t instanceof TypeVar) {
            return t.unify(this);
        } else if (t instanceof BoolType) {
            return Substitution.IDENTITY;
        }
        throw new TypeMismatchError(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return Type.BOOL;
    }

    public String toString() {
        return "bool";
    }
}
