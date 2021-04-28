import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polyfactor implements Factor {
    private ArrayList<Factor> arrayList = new ArrayList<>();
    private String string;

    public Polyfactor(String s) {
        this.string = s;
    }

    @Override
    public String derivation() {
        prepare();
        getFactor();
        String ret = "";
        for (Factor factor : arrayList) {
            ret += factor.derivation();
        }
        return "(" + ret + ")";
    }

    private void getFactor() {
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + num;
        String trig = "sin" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]" + sp + "(" + exp + ")?";
        trig += "|" + "cos" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]" + sp + "(" + exp + ")?";
        String polyfactor = "\\[" + sp + "[^\\]]*" + sp + "\\]";
        String powFun = "x" + sp + "(" + exp + ")?";
        String vfactor = powFun + "|" + trig;
        String factor = "(" + vfactor + "|" + num + "|" + polyfactor + ")";
        String term = "(?<fhone>[+-]?)" + sp + "(?<fhtwo>[+-]?)" + sp +
                "(?<term>" + factor + "(" + sp + "\\*" + sp + factor + ")*)";
        Pattern pattern = Pattern.compile(term);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String fhone = matcher.group("fhone");
            String fhtwo = matcher.group("fhtwo");
            Boolean fh = ("".equals(fhone) | "+".equals(fhone) | fhone == null);
            fh = !(fh ^ ("".equals(fhtwo) | "+".equals(fhtwo) | fhtwo == null));
            String ss = matcher.group("term");
            Factor termfactor = new Termfactor(ss, fh);
            arrayList.add(termfactor);
        }
    }

    private void prepare() {
        if (string != null && !"".equals(string)) {
            char[] c = string.toCharArray();
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
            this.string = new String(c);
        }
        check();
    }

    private void check() {
        String sp = "[ \\t]*";
        String num = "[+-]?\\d+";
        String exp = "\\*\\*" + sp + num;
        String trig = "sin" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]" + sp + "(" + exp + ")?";
        trig += "|" + "cos" + sp + "\\[" + sp + "[^\\]]*" + sp + "\\]" + sp + "(" + exp + ")?";
        String polyfactor = "\\[" + sp + "[^\\]]*" + sp + "\\]";
        String powFun = "x" + sp + "(" + exp + ")?";
        String vfactor = powFun + "|" + trig;
        String factor = "(" + vfactor + "|" + num + "|" + polyfactor + ")";
        String term = "[+-]?" + sp + factor + "(" + sp + "\\*" + sp + factor + ")*";
        String expression = sp + "[+-]?" + sp + term + sp + "(" + "[+-]" + sp + term + sp + ")*";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(this.string);
        if (!matcher.matches()) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return "(" + string.replace("[ \\t]", "") + ")";
    }
}
