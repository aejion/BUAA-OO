package factor;

import java.math.BigInteger;
import main.Term;

public class Coefficient implements Factor {
    private BigInteger pow;

    public Coefficient(String content) {
        this.pow = toDigit(content);
    }

    @Override
    public String getType() {
        return "Coefficient";
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
        if (!oth.getType().equals("Coefficient")) {
            return false;
        }
        return this.pow.equals(oth.getPow());
    }

    @Override
    public Term getDiff() {
        Term diffTerm = new Term();
        diffTerm.setCoe(BigInteger.ZERO);
        return diffTerm;
    }

    private static BigInteger toDigit(String factor) {
        String tmp = factor.replaceAll("\\+", "");
        String result = tmp.replaceAll("-", "");
        boolean sign = (tmp.length() - result.length()) % 2 == 1;
        BigInteger out = new BigInteger(result);
        if (sign) {
            out = out.multiply(BigInteger.valueOf(-1));
        }
        return out;
    }

    @Override
    public String print() {
        return this.pow.toString();
    }
}
