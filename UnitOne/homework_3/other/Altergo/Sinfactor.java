import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sinfactor implements Factor {
    private Factor inside;
    private String string;
    private String tools;
    private BigInteger expnum;

    public Sinfactor(String s) {
        this.string = s;
    }

    @Override
    public String derivation() {
        getFactor();
        if (expnum.equals(BigInteger.ZERO)) {
            return "0";
        } else if (expnum.equals(BigInteger.ONE)) {
            return "cos(" + tools + ")*" + inside.derivation();
        } else if (expnum.equals(BigInteger.ONE.add(BigInteger.ONE))) {
            return expnum + "*sin(" + tools + ")" + "*cos(" + tools + ")*" + inside.derivation();
        } else {
            return expnum + "*sin(" + tools + ")**" + expnum.subtract(BigInteger.ONE)
                    + "*cos(" + tools + ")*" + inside.derivation();
        }
    }

    private void getFactor() {
        check();
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + num;
        String sin = "sin" + sp + "\\[" + sp + "(?<factor>[^\\]]*)" + sp + "\\]"
                + sp + "(" + exp + ")?";
        String cos = "cos" + sp + "\\[" + sp + "(?<factor>[^\\]]*)" + sp + "\\]"
                + sp + "(" + exp + ")?";
        Pattern pattern = Pattern.compile(sin);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            this.tools = matcher.group("factor").trim();
        }
        prepare();
        String polyfactor = "\\[" + sp + "[^\\]]*" + sp + "\\]";
        String powFun = "x" + sp + "(" + exp + ")?";
        pattern = Pattern.compile(sin);
        matcher = pattern.matcher(tools);
        if (matcher.matches()) {
            inside = new Sinfactor(tools);
        } else {
            pattern = Pattern.compile(cos);
            matcher = pattern.matcher(tools);
            if (matcher.matches()) {
                inside = new Cosfactor(tools);
            } else {
                pattern = Pattern.compile(polyfactor);
                matcher = pattern.matcher(tools);
                if (matcher.matches()) {
                    inside = new Polyfactor(tools);
                } else {
                    pattern = Pattern.compile(powFun);
                    matcher = pattern.matcher(tools);
                    if (matcher.matches()) {
                        inside = new Powfactor(tools);
                    } else {
                        pattern = Pattern.compile(num);
                        matcher = pattern.matcher(tools);
                        if (matcher.matches()) {
                            inside = new Coeffactor(tools);
                        } else {
                            System.out.println("WRONG FORMAT!");
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    private void check() {
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + "(?<exp>" + num + ")";
        String sin = "sin" + sp + "\\[" + sp + "(?<factor>[^\\]]*)" + sp + "\\]"
                + sp + "(" + exp + ")?";
        Pattern pattern = Pattern.compile(sin);
        Matcher matcher = pattern.matcher(this.string);
        if (matcher.find()) {
            if (matcher.group("exp") != null && !"".equals(matcher.group("exp"))) {
                this.expnum = new BigInteger(matcher.group("exp"));
            } else {
                this.expnum = BigInteger.ONE;
            }
        }
        if (expnum.compareTo(BigInteger.valueOf(50)) > 0) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
    }

    public void prepare() {
        char[] c = tools.toCharArray();
        int count = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '(' || c[i] == '[') {
                if (count == 0) {
                    c[i] = '[';
                }
                count++;
            } else if (c[i] == ')' || c[i] == ']') {
                if (count == 1) {
                    c[i] = ']';
                }
                count--;
            }
        }
        tools = new String(c);
    }

    @Override
    public String toString() {
        return string.replace("[ \\t]", "");
    }
}
