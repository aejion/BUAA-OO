package factor;

import main.Expression;
import main.Term;

import java.math.BigInteger;
import java.util.ArrayList;

public class SubExp implements Factor {
    private Expression subExp;

    public SubExp(String input) {
        String content = input;
        content = content.substring(3, content.length() - 3);
        content = content.replaceAll("\\{@", "{");
        this.subExp = new Expression(content);
        this.subExp.validCheck();
        this.subExp.parse();
        this.subExp.diff();
        this.subExp.standardize();
        /* to be stuffed the same as MainClass */
    }

    public SubExp(ArrayList<Term> exp) {
        this.subExp = new Expression();
        for (Term term : exp) {
            this.subExp.addTerm(term);
        }
    }

    @Override
    public String getType() {
        return "SubExp";
    }

    @Override
    public BigInteger getPow() {
        return BigInteger.ONE;
    }

    @Override
    public Factor getSubFac() {
        return this;
    }

    public Expression getSubExp() {
        return this.subExp;
    }

    @Override
    public boolean equals(Factor oth) {
        if (!oth.getType().equals("SubExp")) {
            return false;
        }
        return this.subExp.equals(((SubExp)oth).getSubExp());
    }

    @Override
    public Term getDiff() {
        Term diffTerm = new Term();
        SubExp newSub = new SubExp(this.subExp.getDeriFunc());
        diffTerm.addFactors(newSub);
        return diffTerm;
    }

    @Override
    public String print() {
        return "(" + this.subExp.print(0) + ")";
    }
}
