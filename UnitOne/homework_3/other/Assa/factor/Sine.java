package factor;

import main.Term;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sine implements Factor {
    private BigInteger pow;
    private Factor subFac;

    static final String sp = "[ \\t]*"; // space term
    static final String sigint = "[-+]?\\d+"; // Signaled integer
    static final String exp = "\\*\\*" + sp + "(" + sigint + ")"; // exponent

    static final String sin = "(?:sin" + sp + "\\(\\{}(.*)\\)\\{}(?:" + sp + exp + ")?)";

    public Sine(String content) {
        Pattern p = Pattern.compile(sin);
        Matcher m = p.matcher(content);
        if (!m.find()) {
            throw new RuntimeException();
        }
        if (m.group(2) != null) {
            this.pow = new BigInteger(m.group(2));
        } else { this.pow = BigInteger.ONE; }
        if (this.pow.abs().add(BigInteger.valueOf(-50)).compareTo(BigInteger.ZERO) > 0) {
            throw new RuntimeException();
        }
        String newFac = m.group(1).replaceAll("\\{@", "{");
        FactorFactory newFactory = new FactorFactory();
        this.subFac = newFactory.getFactor(newFac);
    }

    public Sine(BigInteger pow, Factor subFac) {
        this.pow = pow;
        this.subFac = subFac;
    }

    @Override
    public String getType() {
        return "Sine";
    }

    @Override
    public BigInteger getPow() {
        return this.pow;
    }

    @Override
    public Factor getSubFac() {
        return this.subFac;
    }

    @Override
    public boolean equals(Factor oth) {
        if (!oth.getType().equals("Sine")) {
            return false;
        }
        if (!oth.getPow().equals(this.pow)) {
            return false;
        }
        return this.subFac.equals(oth.getSubFac());
    }

    @Override
    public Term getDiff() {
        Term diffTerm = new Term();
        diffTerm.setCoe(this.pow);
        Factor newSine = new Sine(this.pow.add(BigInteger.valueOf(-1)), this.subFac);
        Factor newCosine = new Cosine(BigInteger.ONE, this.subFac);
        diffTerm.addFactors(newSine);
        diffTerm.addFactors(newCosine);
        Term innerTerm = this.subFac.getDiff();
        diffTerm.setCoe(diffTerm.getCoe().multiply(innerTerm.getCoe()));
        diffTerm.setEksPow(diffTerm.getEksPow().add(innerTerm.getEksPow()));
        for (Factor f : innerTerm.getFactors()) {
            diffTerm.addFactors(f);
        }
        return diffTerm;
    }

    @Override
    public String print() {
        if (this.pow.equals(BigInteger.ZERO)) {
            return "1";
        }
        return "sin(" + this.subFac.print() + ")**" + this.pow.toString();
    }
}
