import java.math.BigInteger;

public class ConstFactor extends Factor {

    public ConstFactor(BigInteger constant) {
        super(constant);
    }

    @Override
    public Expression derivative() throws CloneNotSupportedException {
        Expression result = new Expression();
        Term tempTerm = new Term();
        tempTerm = tempTerm.multiply(new ConstFactor(BigInteger.ZERO));
        result = result.add(tempTerm);
        return result;
    }

    @Override
    public Factor clone() {
        return new ConstFactor(super.getIndex());
    }

    public String toString() {
        return super.getIndex().toString();
    }
}
