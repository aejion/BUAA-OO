package factor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactorFactory {
    static final String sp = "[ \\t]*"; // space term
    static final String sigint = "[-+]?\\d+"; // Signaled integer
    static final String exp = "\\*\\*" + sp + "(?:" + sigint + ")"; // exponent

    static final String pow = "(?:x(?:" + sp + exp + ")?)"; // power function
    static final String sin = "(?:sin" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String cos = "(?:cos" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String expFac = "\\(\\{}.*?\\)\\{}";

    static final String var = "(?:" + pow + "|" + sin + "|" + cos + "|" + expFac + ")"; // variable
    static final String factor = "(?:" + var + "|" + sigint + ")"; // factor

    public Factor getFactor(String input) {
        String f = input;
        Pattern facReg = Pattern.compile(factor);
        Matcher m = facReg.matcher(f);
        if (!m.find()) {
            throw new RuntimeException();
        } else if (m.find()) {
            throw new RuntimeException();
        }
        if (isDigit(f)) {
            f = f.replaceAll("[ \\t]", "");
            return new Coefficient(f);
        } else if (isEks(f)) {
            f = f.replaceAll("[ \\t]", "");
            return new Eks(f);
        } else if (isSine(f)) {
            f = f.trim();
            return new Sine(f);
        } else if (isCosine(f)) {
            f = f.trim();
            return new Cosine(f);
        } else {
            return new SubExp(f);
        }
    }

    private static boolean isDigit(String factor) {
        Pattern p = Pattern.compile(sp + sigint + sp);
        Matcher m = p.matcher(factor);
        return m.matches();
    }

    private static boolean isEks(String factor) {
        Pattern p = Pattern.compile(sp + pow + sp);
        Matcher m = p.matcher(factor);
        return m.matches();
    }

    private static boolean isSine(String factor) {
        Pattern p = Pattern.compile(sp + sin + sp);
        Matcher m = p.matcher(factor);
        return m.matches();
    }

    private static boolean isCosine(String factor) {
        Pattern p = Pattern.compile(sp + cos + sp);
        Matcher m = p.matcher(factor);
        return m.matches();
    }
}
