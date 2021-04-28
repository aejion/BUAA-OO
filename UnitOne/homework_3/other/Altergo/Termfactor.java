import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Termfactor implements Factor {
    private ArrayList<Factor> arrayList = new ArrayList<>();
    private String string;
    private boolean sign;

    public Termfactor(String s, boolean sign) {
        this.string = s;
        this.sign = sign;
    }

    public String derivation() {
        getFactor();
        String ret = "";
        for (Factor factor1 : arrayList) {
            if (sign) {
                ret += "+";
            } else {
                ret += "-";
            }
            ret += factor1.derivation();
            for (Factor factor2 : arrayList) {
                if (factor1 != factor2) {
                    ret += "*" + factor2.toString();
                }
            }
        }
        return ret;
    }

    private void getFactor() {
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + num;
        String trig = "(?<sin>sin" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]"
                + sp + "(" + exp + ")?)";
        trig += "|" + "(?<cos>cos" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]"
                + sp + "(" + exp + ")?)";
        String polyfactor = "\\[" + sp + "(?<polyfactor>[^\\]]*)" + sp + "\\]";
        String powFun = "x" + sp + "(" + exp + ")?";
        String vfactor = "(?<powFun>" + powFun + ")" + "|" + trig;
        String factor = "(" + vfactor + "|" + "(?<coef>" + num + ")" + "|" + polyfactor + ")";
        Pattern pattern = Pattern.compile(factor);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            if (matcher.group("powFun") != null && !"".equals(matcher.group("powFun"))) {
                Factor powfactor = new Powfactor(matcher.group("powFun"));
                arrayList.add(powfactor);
            } else if (matcher.group("sin") != null && !"".equals(matcher.group("sin"))) {
                Factor sinfactor = new Sinfactor(matcher.group("sin"));
                arrayList.add(sinfactor);
            } else if (matcher.group("cos") != null && !"".equals(matcher.group("cos"))) {
                Factor cosfactor = new Cosfactor(matcher.group("cos"));
                arrayList.add(cosfactor);
            } else if (matcher.group("coef") != null && !"".equals(matcher.group("coef"))) {
                Factor coeffactor = new Coeffactor(matcher.group("coef"));
                arrayList.add(coeffactor);
            } else if (matcher.group("polyfactor") != null &&
                    !"".equals(matcher.group("polyfactor"))) {
                Factor poly = new Polyfactor(matcher.group("polyfactor").trim());
                arrayList.add(poly);
            } else {
                System.out.println("WRONG FORMAT!");
                System.exit(0);
            }
        }
    }

    @Override
    public String toString() {
        return string.replace("[ \\t]", "");
    }
}
