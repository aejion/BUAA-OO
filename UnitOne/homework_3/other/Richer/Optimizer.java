import java.math.BigInteger;

public class Optimizer {

    public Expression optimize(Expression e) throws CloneNotSupportedException {
        Expression result;
        result = mergeFactor(e);
        result = deleteZeroFactor(result);
        result = mergeFactor(result);
        result = deleteZeroTerm(result);
        result = mergeTerm(result);
        result = mergeFactor(result);
        for (Term t : result.getMyExpression()) {
            for (Factor f : t.getMyTerm()) {
                if (f instanceof SinFactor) {
                    ((SinFactor) f).setNestedExpression(
                            optimize(((SinFactor) f).getNestedExpression()));
                } else if (f instanceof CosFactor) {
                    ((CosFactor) f).setNestedExpression(
                            optimize(((CosFactor) f).getNestedExpression()));
                }
            }
        }

        return result;

    }

    public Expression deleteZeroFactor(Expression e) throws CloneNotSupportedException {
        Expression resultExpression = new Expression();
        for (Term t : e.getMyExpression()) {
            Term resultTerm = new Term();
            for (Factor f : t.getMyTerm()) {
                if (f instanceof ConstFactor && f.getIndex().equals(BigInteger.ZERO)) {
                    resultTerm = resultTerm.clear();
                    resultTerm = resultTerm.multiply(new ConstFactor(BigInteger.ZERO));
                    break;
                } else if (f.getIndex().equals(BigInteger.ZERO)) {
                    resultTerm = resultTerm.multiply(new ConstFactor(BigInteger.ONE));
                } else {
                    resultTerm = resultTerm.multiply(f);
                }
            }
            resultExpression = resultExpression.add(resultTerm);
        }
        return resultExpression;
    }

    public Expression mergeFactor(Expression e) throws CloneNotSupportedException {
        Expression resultExpression = new Expression();
        for (Term t : e.getMyExpression()) {
            Term tempTerm = new Term();
            ConstFactor tempConstant = new ConstFactor(BigInteger.ONE);
            PowerFactor tempPower = new PowerFactor(BigInteger.ZERO);
            for (Factor f : t.getMyTerm()) {
                if (f instanceof ConstFactor) {
                    tempConstant = new ConstFactor(tempConstant.getIndex().multiply(f.getIndex()));
                } else if (f instanceof PowerFactor) {
                    tempPower = new PowerFactor(tempPower.getIndex().add(f.getIndex()));
                } else {
                    tempTerm = tempTerm.multiply(f);
                }
            }
            Term resultTerm = new Term();
            resultTerm = resultTerm.multiply(tempConstant);
            if (!tempPower.getIndex().equals(BigInteger.ZERO)) {
                resultTerm = resultTerm.multiply(tempPower);
            }
            resultTerm = resultTerm.multiply(tempTerm);
            resultExpression = resultExpression.add(resultTerm);
        }
        return resultExpression;
    }

    public Expression deleteZeroTerm(Expression e) throws CloneNotSupportedException {
        Expression result = new Expression();
        for (Term t : e.getMyExpression()) {
            if (!(t.size() == 1 && t.get(0) instanceof ConstFactor &&
                    t.get(0).getIndex().equals(BigInteger.ZERO))) {
                result = result.add(t.clone());
            }
        }
        if (result.size() == 0) {
            Term tempTerm = new Term();
            tempTerm = tempTerm.multiply(new ConstFactor(BigInteger.ZERO));
            result = result.add(tempTerm);
        }
        return result;
    }

    public Expression mergeTerm(Expression e) throws CloneNotSupportedException {
        Expression resultExpression = new Expression();

        BigInteger tempConstant = BigInteger.ZERO;
        BigInteger tempCoef = BigInteger.ZERO;
        for (Term t : e.getMyExpression()) {
            if (t.size() == 1 && t.get(0) instanceof ConstFactor) {
                tempConstant = tempConstant.add(t.get(0).getIndex());
            } else if (t.size() == 2 && t.get(0) instanceof ConstFactor && t.get(1)
                    instanceof PowerFactor
                    && t.get(1).getIndex().equals(BigInteger.ONE)) {
                tempCoef = tempCoef.add(t.get(0).getIndex());
            } else {
                resultExpression = resultExpression.add(t.clone());
            }
        }
        if (!tempConstant.equals(BigInteger.ZERO)) {
            ConstFactor tempFactor = new ConstFactor(tempConstant);
            Term tempTerm = new Term();
            tempTerm = tempTerm.multiply(tempFactor);
            resultExpression = resultExpression.add(tempTerm);
        }
        if (!tempCoef.equals(BigInteger.ZERO)) {
            ConstFactor tempCoefFactor = new ConstFactor(tempCoef);
            PowerFactor tempFactor = new PowerFactor(BigInteger.ONE);
            Term tempTerm = new Term();
            tempTerm = tempTerm.multiply(tempCoefFactor);
            tempTerm = tempTerm.multiply(tempFactor);
            resultExpression = resultExpression.add(tempTerm);
        }
        if (resultExpression.size() == 0) {
            ConstFactor tempFactor = new ConstFactor(tempConstant);
            Term tempTerm = new Term();
            tempTerm = tempTerm.multiply(tempFactor);
            resultExpression = resultExpression.add(tempTerm);
        }
        return resultExpression;
    }

}
