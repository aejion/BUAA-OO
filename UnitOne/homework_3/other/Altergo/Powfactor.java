import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Powfactor implements Factor {
    private String string;
    private BigInteger expnum;

    public Powfactor(String s) {
        this.string = s;
    }

    @Override
    public String derivation() {
        check();
        if (expnum.equals(BigInteger.ZERO)) {
            return "0";
        } else if (expnum.equals(BigInteger.ONE)) {
            return "1";
        } else if (expnum.equals(BigInteger.ONE.add(BigInteger.ONE))) {
            return "2*x";
        } else {
            return expnum + "*x**" + (expnum.subtract(BigInteger.ONE));
        }
    }

    private void check() {
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + "(?<exp>" + num + ")";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(this.string);
        if (matcher.find()) {
            this.expnum = new BigInteger(matcher.group("exp"));
        } else {
            this.expnum = BigInteger.ONE;
        }
        if (expnum.compareTo(BigInteger.valueOf(50)) > 0) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return string.replace("[ \\t]", "");
    }
}
