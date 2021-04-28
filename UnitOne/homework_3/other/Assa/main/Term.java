package main;

import factor.Factor;
import factor.FactorFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term implements Comparable<Term> {
    // C * x ** k * sin(x) ** m * cos(x) ** n
    private BigInteger coe; // C
    private BigInteger eksPow; // k
    private ArrayList<Factor> factors;

    static final String sp = "[ \\t]*"; // space term
    static final String sigint = "[-+]?\\d+"; // Signaled integer
    static final String exp = "\\*\\*" + sp + "(?:" + sigint + ")"; // exponent

    static final String pow = "(?:x(?:" + sp + exp + ")?)"; // power function
    static final String sin = "(?:sin" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String cos = "(?:cos" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String expFac = "\\(\\{}.*?\\)\\{}";

    static final String var = "(?:" + pow + "|" + sin + "|" + cos + "|" + expFac + ")"; // variable
    static final String factor = "(?:" + var + "|" + sigint + ")"; // factor

    public Term(String input) {
        this.coe = BigInteger.ONE;
        this.eksPow = BigInteger.ZERO;
        this.factors = new ArrayList<>();
        String rawInput = input;
        while (rawInput.startsWith("+") || rawInput.startsWith("-")) {
            if (rawInput.startsWith("-")) {
                this.coe = this.coe.multiply(BigInteger.valueOf(-1));
            }
            rawInput = rawInput.substring(1);
        } // parsing term signs
        Pattern facReg = Pattern.compile(factor);
        Matcher m = facReg.matcher(rawInput);

        while (m.find()) {
            String f = m.group();
            FactorFactory factorFactory = new FactorFactory();
            Factor newFac = factorFactory.getFactor(f);
            switch (newFac.getType()) {
                case "Coefficient" :
                    this.coe = this.coe.multiply(newFac.getPow());
                    break;
                case "Eks" :
                    this.eksPow = this.eksPow.add(newFac.getPow());
                    break;
                default :
                    this.factors.add(newFac);
            }
        }
    }

    public Term() {
        this.coe = BigInteger.ONE;
        this.eksPow = BigInteger.ZERO;
        this.factors = new ArrayList<>();
    }

    public Expression diff() {
        Expression diffTerms = new Expression();
        if (!this.eksPow.equals(BigInteger.ZERO)) {
            Term diffEks = new Term();
            diffEks.setCoe(this.coe.multiply(this.eksPow));
            diffEks.setEksPow(this.eksPow.add(BigInteger.valueOf(-1)));
            for (Factor factor : this.factors) {
                diffEks.addFactors(factor);
            }
            diffTerms.addTerm(diffEks);
        }
        for (int i = 0; i < this.factors.size(); i++) {
            Term diffFac = new Term();
            diffFac.setCoe(this.coe);
            diffFac.setEksPow(this.eksPow);
            Term innerDiff = this.factors.get(i).getDiff();
            diffFac.setCoe(diffFac.getCoe().multiply(innerDiff.getCoe()));
            diffFac.setEksPow(diffFac.getEksPow().add(innerDiff.getEksPow()));
            for (Factor f : innerDiff.getFactors()) {
                diffFac.addFactors(f);
            }
            for (int j = 0; j < this.factors.size(); j++) {
                if (j != i) {
                    diffFac.addFactors(this.factors.get(j));
                }
            } // add undifferentiated factors
            diffTerms.addTerm(diffFac);
        }
        return diffTerms;
    }

    public BigInteger getCoe() {
        return this.coe;
    }

    public BigInteger getEksPow() {
        return this.eksPow;
    }

    public ArrayList<Factor> getFactors() {
        return factors;
    }

    public void setCoe(BigInteger coe) {
        this.coe = coe;
    }

    public void setEksPow(BigInteger eksPow) {
        this.eksPow = eksPow;
    }

    public void addFactors(Factor factor) {
        this.factors.add(factor);
    }

    @Override
    public int compareTo(Term oth) {
        if (this.eksPow.compareTo(oth.getEksPow()) < 0) {
            return 1;
        }
        if (this.eksPow.compareTo(oth.getEksPow()) > 0) {
            return -1;
        }
        if (this.coe.compareTo(oth.getCoe()) < 0) {
            return 1;
        }
        if (this.coe.compareTo(oth.getCoe()) > 0) {
            return -1;
        }
        return 0;
    }

    public boolean similarTo(Term oth) {
        if (!this.eksPow.equals(oth.getEksPow())) {
            return false;
        }
        if (!(this.factors.size() == oth.getFactors().size())) {
            return false;
        }
        return false;
        // to be rectified
    }
}
