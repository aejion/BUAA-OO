import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression implements Diffable {
    private List<Term> expression = new ArrayList<>();
    
    private String sp = "([ \\t]*)";
    private String unsignedNum = "(\\d+)";
    private String signedNum = "([\\+\\-]?" + unsignedNum + ")";
    private String index = "(\\^" + sp + signedNum + ")";
    private String pow = "(x(" + sp + index + ")?)";
    private String tri1 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac1>.+?)" + sp + "\\]" +
            "(" + sp + index + ")?)";
    private String tri2 = "((sin|cos)" + sp
            + "\\[" + sp + "(?<fac2>.+?)" + sp + "\\]" +
            "(" + sp + index + ")?)";
    private String vari1 = "(" + tri1 + "|" + pow + ")";
    private String vari2 = "(" + tri2 + "|" + pow + ")";
    private String factor1 = sp + "(" + vari1 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp1>.+?)\\]" + sp + "))" + sp;
    private String factor2 = sp + "(" + vari2 + "|" + signedNum
            + "|(" + sp + "\\[(?<exp2>.+?)\\]" + sp + "))" + sp;
    private String term = "(([+\\-]?" + sp + ")?" + factor1
            + "(\\*" + sp + factor2 + ")*)";
    private Pattern patTerm = Pattern.compile(term);
    
    public Expression(String tmp) {
        String strExp = tmp;
        Pre pre = new Pre();
        strExp = pre.process(strExp);
        while (strExp.charAt(0) == '[' && strExp.charAt(strExp.length() - 1) == ']') {
            strExp = strExp.substring(1, strExp.length() - 1);
            strExp = pre.process(strExp);
        }
        Matcher matTerm = patTerm.matcher(strExp);
        while (matTerm.find()) {
            String strTerm = matTerm.group(0);
            Term addTerm = new Term(strTerm);
            expression.add(addTerm);
        }
    }
    
    @Override
    public String getDiff() {
        String str = "";
        int size = expression.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                str += "+";
            }
            str += expression.get(i).getDiff();
        }
        return "(" + str + ")";
    }
    
    @Override
    public String toString() {
        String str = "";
        int size = expression.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                str += "+";
            }
            str += expression.get(i).toString();
        }
        return "(" + str + ")";
    }
}
