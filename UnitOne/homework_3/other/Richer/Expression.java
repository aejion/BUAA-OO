import java.util.ArrayList;

public class Expression {
    private ArrayList<Term> myExpression = new ArrayList<>();

    public Expression() {
    }

    public ArrayList<Term> getMyExpression() {
        return myExpression;
    }

    public int size() {
        return myExpression.size();
    }

    public Term get(int index) {
        return myExpression.get(index);
    }

    public Expression clone() throws CloneNotSupportedException {
        Expression result = new Expression();
        for (Term t : myExpression) {
            result = result.add(t.clone());
        }
        return result;
    }

    public Expression add(Term t) throws CloneNotSupportedException {
        Expression result = this.clone();
        result.getMyExpression().add(t);
        return result;
    }

    public Expression add(Expression e) throws CloneNotSupportedException {
        Expression result = this.clone();
        for (Term t : e.getMyExpression()) {
            result.getMyExpression().add(t.clone());
        }
        return result;
    }

    public Expression multiply(Factor f) throws CloneNotSupportedException {
        Expression result = new Expression();
        for (Term i : this.getMyExpression()) {
            Term temp = i.clone();
            temp = temp.multiply((Factor) f.clone());
            result = result.add(temp);
        }
        return result;
    }

    public Expression multiply(Term t) throws CloneNotSupportedException {
        Expression result = new Expression();

        for (Term i : this.getMyExpression()) {
            Term tempTerm = i.clone();
            tempTerm = tempTerm.multiply(t.clone());
            result = result.add(tempTerm);
        }
        return result;
    }

    public Expression multiply(Expression e) throws CloneNotSupportedException {
        Expression result = new Expression();
        for (Term t : e.getMyExpression()) {
            for (Term i : this.getMyExpression()) {
                result = result.add(t.clone().multiply(i.clone()));
            }
        }
        return result;
    }

    public Expression derivative() throws CloneNotSupportedException {
        Expression result = new Expression();
        for (Term t : this.getMyExpression()) {
            result = result.add(t.derivative());
        }
        return result;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Term t : myExpression) {
            result.append(t);
        }
        String finalResult = result.toString();
        if (finalResult.startsWith("+")) {
            finalResult = finalResult.substring(1, finalResult.length());
        }
        return finalResult;
    }
}
