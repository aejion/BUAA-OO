package factor;

import main.Term;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eks implements Factor {
    private BigInteger pow;

    static final String sp = "[ \\t]*"; // space term
    static final String sigint = "[-+]?\\d+"; // Signaled integer
    static final String exp = "\\*\\*" + sp + "(" + sigint + ")"; // exponent

    public Eks(String content) {
        this.pow = toEks(content);
        if (this.pow.abs().add(BigInteger.valueOf(-50)).compareTo(BigInteger.ZERO) > 0) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getType() {
        return "Eks";
    }

    @Override
    public BigInteger getPow() {
        return this.pow;
    }

    @Override
    public Factor getSubFac() {
        return this;
    }

    @Override
    public boolean equals(Factor oth) {
        if (!oth.getType().equals("Eks")) {
            return false;
        }
        return this.pow.equals(oth.getPow());
    }

    @Override
    public Term getDiff() {
        Term diffTerm = new Term();
        diffTerm.setCoe(this.pow);
        diffTerm.setEksPow(this.pow.add(BigInteger.valueOf(-1)));
        return diffTerm;
    }

    private static BigInteger toEks(String factor) {
        Pattern p = Pattern.compile("(?:x(?:" + sp + exp + ")?)");
        Matcher m = p.matcher(factor);
        m.find();
        String exponent = m.group(1);
        if (exponent == null) {
            return BigInteger.ONE;
        } else { return new BigInteger(m.group(1)); }
    }

    @Override
    public String print() {
        if (this.pow.equals(BigInteger.ZERO)) {
            return "1";
        }
        return "x**" + this.pow.toString();
    }
}
