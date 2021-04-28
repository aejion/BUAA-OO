import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private ArrayList<Factor> myTerm = new ArrayList<>();

    public Term() {
    }

    public ArrayList<Factor> getMyTerm() {
        return myTerm;
    }

    public int size() {
        return myTerm.size();
    }

    public Factor get(int index) {
        return myTerm.get(index);
    }

    public Term remove(int index) throws CloneNotSupportedException {
        Term result = this.clone();
        result.getMyTerm().remove(index);
        return result;
    }

    public Term clone() throws CloneNotSupportedException {
        Term result = new Term();
        for (Factor f : myTerm) {
            result.getMyTerm().add((Factor) f.clone());
        }
        return result;
    }

    public Term clear() {
        Term result = new Term();
        return result;
    }

    public Term multiply(Factor f) throws CloneNotSupportedException {
        Term result = this.clone();
        result.getMyTerm().add((Factor) f.clone());
        return result;
    }

    public Term multiply(Term t) throws CloneNotSupportedException {
        Term result = this.clone();
        for (Factor f : t.getMyTerm()) {
            result = result.multiply((Factor) f.clone());
        }
        return result;
    }

    public Expression multiply(Expression e) throws CloneNotSupportedException {
        Expression result = new Expression();

        for (Term t : e.getMyExpression()) {
            Term temp = t.clone();
            temp = temp.multiply(this.clone());
            result = result.add(temp);
        }
        return result;
    }

    public Expression derivative() throws CloneNotSupportedException {
        Term tempTerm = this.clone();
        return work(tempTerm);
    }

    private Expression work(Term t) throws CloneNotSupportedException {
        if (t.size() == 1) {
            return t.get(0).derivative();
        }
        Factor tempFactor = t.get(t.size() - 1);
        Term tempTerm = t.remove(t.size() - 1);
        Expression one = work(tempTerm).multiply(tempFactor);
        Expression two = tempTerm.multiply(tempFactor.derivative());

        return one.add(two);
    }

    public String toString() {
        StringBuffer resultBuffer = new StringBuffer();

        if (myTerm.get(0) instanceof ConstFactor && myTerm.
                get(0).getIndex().compareTo(BigInteger.ZERO) > 0) {
            if (myTerm.size() == 1) {
                resultBuffer.append("+" + myTerm.get(0));
            } else if (myTerm.get(0).getIndex().equals(BigInteger.ONE)) {
                resultBuffer.append("+");
                int i = 0;
                for (i = 1; i < myTerm.size(); i++) {
                    resultBuffer.append(myTerm.get(i) + "*");
                }
            } else {
                resultBuffer.append("+");
                for (Factor f : myTerm) {
                    resultBuffer.append(f + "*");
                }
            }
        } else if (myTerm.get(0) instanceof ConstFactor
                && myTerm.get(0).getIndex().compareTo(BigInteger.ZERO) < 0) {
            if (myTerm.size() == 1) {
                resultBuffer.append(myTerm.get(0));
            } else if (myTerm.get(0).getIndex().equals(BigInteger.ONE.negate())) {
                resultBuffer.append("-");
                int i = 0;
                for (i = 1; i < myTerm.size(); i++) {
                    resultBuffer.append(myTerm.get(i) + "*");
                }
            } else {
                for (Factor f : myTerm) {
                    resultBuffer.append(f + "*");
                }
            }
        } else {
            for (Factor f : myTerm) {
                resultBuffer.append(f + "*");
            }
        }


        String result = resultBuffer.toString();
        if (result.endsWith("*")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
