import java.math.BigInteger;

public class CosFactor extends Factor implements Cloneable {
    private Expression nestedExpression;

    public CosFactor(BigInteger index, Expression e) throws CloneNotSupportedException {
        super(index);
        nestedExpression = e.clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CosFactor(super.getIndex(), nestedExpression.clone());
    }

    public Expression getNestedExpression() {
        return nestedExpression;
    }

    public void setNestedExpression(Expression nestedExpression) {
        this.nestedExpression = nestedExpression;
    }

    @Override
    public Expression derivative() throws CloneNotSupportedException {

        Term tempTerm = new Term();
        tempTerm = tempTerm.multiply(new ConstFactor(super.getIndex().negate()));
        tempTerm = tempTerm.multiply(new CosFactor(super.getIndex().
                subtract(BigInteger.ONE), this.nestedExpression.clone()));
        tempTerm = tempTerm.multiply(new SinFactor(BigInteger.ONE, this.nestedExpression.clone()));
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
            result += "cos(" + nestedExpression.toString() + ")";
        } else {
            result += "cos((" + nestedExpression.toString() + "))";
        }

        if (!super.getIndex().equals(BigInteger.ONE)) {
            result += "**" + super.getIndex();
        }
        return result;
    }
}
