import java.math.BigInteger;

public class PowerFactor extends Factor implements Cloneable {
    public PowerFactor(BigInteger index) {
        super(index);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new PowerFactor(super.getIndex());
    }

    @Override
    public Expression derivative() throws CloneNotSupportedException {
        Expression result = new Expression();
        Term tempTerm = new Term();
        tempTerm = tempTerm.multiply(new ConstFactor(super.getIndex()));
        tempTerm = tempTerm.multiply(new PowerFactor(super.getIndex().subtract(BigInteger.ONE)));
        result = result.add(tempTerm);
        return result;
    }

    public String toString() {
        String result = "";
        result += "x";
        if (!super.getIndex().equals(BigInteger.ONE)) {
            result += "**" + super.getIndex();
        }
        return result;
    }

}
