import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
    private static String sp = "([ \\t]*)";
    private static String unsignedNum = "(\\d+)";
    private static String signedNum = "(" + sp + "[+\\-]?" + unsignedNum + ")";
    private static String index = "(" + sp + "\\*\\*" + sp + signedNum + ")";
    private static String pow = "(x(" + sp + index + ")?)";
    
    private static String tri1 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac1>.+?)" + sp + "\\]" +
            "(" + sp + "\\*\\*" + signedNum + ")?)";
    private static String tri2 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac2>.+?)" + sp + "\\]" +
            "(" + sp + "\\*\\*" + signedNum + ")?)";
    private static String tri3 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac3>.+?)" + sp + "\\]" +
            "(" + sp + "\\*\\*" + signedNum + ")?)";
    private static String tri4 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac4>.+?)" + sp + "\\]" +
            "(" + sp + "\\*\\*" + signedNum + ")?)";
    
    private static String vari1 = "(" + tri1 + "|" + pow + ")";
    private static String vari2 = "(" + tri2 + "|" + pow + ")";
    private static String vari3 = "(" + tri3 + "|" + pow + ")";
    private static String vari4 = "(" + tri4 + "|" + pow + ")";
    
    private static String factor1 = sp + "(" + vari1 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp1>.+?)\\]" + sp + "))" + sp;
    private static String factor2 = sp + "(" + vari2 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp2>.+?)\\]" + sp + "))" + sp;
    private static String factor3 = sp + "(" + vari3 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp3>.+?)\\]" + sp + "))" + sp;
    private static String factor4 = sp + "(" + vari4 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp4>.+?)\\]" + sp + "))" + sp;
    
    private static String term1 = "(([+\\-]?" + sp + ")?" + factor1
            + "(\\*" + sp + factor2 + ")*)";
    private static String term2 = "(([+\\-]?" + sp + ")?" + factor3
            + "(\\*" + sp + factor4 + ")*)";
    
    private static String expression = "^(" + sp + "([+\\-]?" + sp + ")?"
            + term1 + sp + "([+\\-]" + sp + term2 + sp + ")*)$";
    
    private Pattern factorPattern = Pattern.compile(factor1);
    private static Pattern exprePattern = Pattern.compile(expression);
    
    public boolean checkIndex(String str) {
        Pattern patIndex = Pattern.compile("\\^(?<index>[\\+-]?\\d+)");
        Matcher matIndex = patIndex.matcher(str);
        while (matIndex.find()) {
            String strIn = matIndex.group("index");
            BigInteger in = new BigInteger(strIn);
            if (in.abs().compareTo(new BigInteger("50")) > 0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isFactor(String tmp) {
        String str = tmp;
        Pre pre = new Pre();
        str = pre.process(str);
        if (str == null) {
            return false;
        }
        Matcher matcherF = factorPattern.matcher(str);
        if (!matcherF.matches()) {
            return false;
        }
        String ex = matcherF.group("exp1");
        if (ex != null && !isExpre(ex)) {
            return false;
        }
        String fac = matcherF.group("fac1");
        if (fac != null && !isFactor(fac)) {
            return false;
        }
        return true;
    }
    
    public boolean isExpre(String tmp) {
        String str = tmp;
        Pre pre = new Pre();
        str = pre.process(str);
        if (str == null) {
            return false;
        }
        Matcher matcherE = exprePattern.matcher(str);
        if (!matcherE.matches()) {
            return false;
        }
        String ex1 = matcherE.group("exp1");
        if (ex1 != null && !isExpre(ex1)) {
            return false;
        }
        String fac1 = matcherE.group("fac1");
        if (fac1 != null && !isFactor(fac1)) {
            return false;
        }
        String ex2 = matcherE.group("exp2");
        if (ex2 != null && !isExpre(ex2)) {
            return false;
        }
        String fac2 = matcherE.group("fac2");
        if (fac2 != null && !isFactor(fac2)) {
            return false;
        }
        String ex3 = matcherE.group("exp3");
        if (ex3 != null && !isExpre(ex3)) {
            return false;
        }
        String fac3 = matcherE.group("fac3");
        if (fac3 != null && !isFactor(fac3)) {
            return false;
        }
        String ex4 = matcherE.group("exp4");
        if (ex4 != null && !isExpre(ex4)) {
            return false;
        }
        String fac4 = matcherE.group("fac4");
        if (fac4 != null && !isFactor(fac4)) {
            return false;
        }
        return true;
    }
}
