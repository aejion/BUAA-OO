import java.math.BigInteger;

public class SinFactor extends Factor {

    private Expression nestedExpression;

    public SinFactor(BigInteger index, Expression e) throws CloneNotSupportedException {
        super(index);
        nestedExpression = e.clone();
    }

    public Expression getNestedExpression() {
        return nestedExpression;
    }

    public void setNestedExpression(Expression nestedExpression) {
        this.nestedExpression = nestedExpression;
    }

    @Override
    public Factor clone() throws CloneNotSupportedException {
        return new SinFactor(super.getIndex(), nestedExpression.clone());
    }

    @Override
    public Expression derivative() throws CloneNotSupportedException {

        Term tempTerm = new Term();
        tempTerm = tempTerm.multiply(new ConstFactor(super.getIndex()));
        tempTerm = tempTerm.multiply(new SinFactor(
                super.getIndex().subtract(BigInteger.ONE), this.nestedExpression.clone()));
        tempTerm = tempTerm.multiply(new CosFactor(BigInteger.ONE, this.nestedExpression.clone()));
        Expression result = new Expression();
        result = tempTerm.multiply(this.nestedExpression.derivative());
        return result;
    }

    public String toString() {
        String result = "";
        if ((nestedExpression.size() == 1 && nestedExpression.get(0).size() == 1) ||
                ((nestedExpression.size() == 1 && nestedExpression.get(0).size() == 2 &&
                        nestedExpression.get(0).get(0) instanceof ConstFactor) &&
                        (nestedExpression.get(0).get(0).getIndex().equals(BigInteger.ONE) ||
                                nestedExpression.get(0).get(0).getIndex().
                                        equals(BigInteger.ONE.negate())))) {
            result += "sin(" + nestedExpression.toString() + ")";
        } else {
            result += "sin((" + nestedExpression.toString() + "))";
        }

        if (!super.getIndex().equals(BigInteger.ONE)) {
            result += "**" + super.getIndex();
        }
        return result;
    }
}
