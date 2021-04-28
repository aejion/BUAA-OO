import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term implements Diffable {
    private List<Factor> term = new ArrayList<>();
    private String str;
    
    private String sp = "([ \\t]*)";
    private String unsignedNum = "(\\d+)";
    private String signedNum = "([+\\-]?" + unsignedNum + ")";
    private String index = "(\\^" + sp + signedNum + ")";
    private String tri = "((sin|cos)" + sp
            + "\\[" + sp + "(.*?)" + sp + "\\]" +
            "(" + sp + index + ")?)";
    private String pow = "(x(" + sp + index + ")?)";
    private String exp = "\\[(.*?)\\]";
    
    private Pattern patConst = Pattern.compile(signedNum);
    private Pattern patTri = Pattern.compile(tri);
    private Pattern patPow = Pattern.compile(pow);
    private Pattern patExp = Pattern.compile(exp);
    
    public Term(String tmp) {
        String strTerm = tmp;
        Matcher matTri = patTri.matcher(strTerm);
        while (matTri.find()) {
            String strTri = matTri.group(0);
            int length = strTri.length();
            int start = strTerm.indexOf(strTri);
            String head = strTerm.substring(0, start);
            String tail = strTerm.substring(start + length);
            strTerm = head + tail;
            Factor addTri = new Factor(strTri);
            term.add(addTri);
        }
        if (strTerm.equals("")) {
            return;
        }
        Matcher matExp = patExp.matcher(strTerm);
        while (matExp.find()) {
            String strExp = matExp.group(0);
            int length = strExp.length();
            int start = strTerm.indexOf(strExp);
            String head = strTerm.substring(0, start);
            String tail = strTerm.substring(start + length);
            strTerm = head + tail;
            Factor addExp = new Factor(strExp);
            term.add(addExp);
        }
        if (strTerm.equals("")) {
            return;
        }
        Matcher matPow = patPow.matcher(strTerm);
        while (matPow.find()) {
            String strPow = matPow.group(0);
            int length = strPow.length();
            int start = strTerm.indexOf(strPow);
            String head = strTerm.substring(0, start);
            String tail = strTerm.substring(start + length);
            strTerm = head + tail;
            Factor addPow = new Factor(strPow);
            term.add(addPow);
        }
        if (strTerm.equals("")) {
            return;
        }
        Matcher matConst = patConst.matcher(strTerm);
        while (matConst.find()) {
            String strConst = matConst.group(0);
            Factor addConst = new Factor(strConst);
            term.add(addConst);
        }
        /*
        String[] strFacs = strTerm.split("\\*");
        for (String strFac: strFacs) {
            Factor addFac = new Factor(strFac);
            term.add(addFac);
        }
         */
    }
    
    public static String replace(String tmp, String from, String to) {
        String str = tmp;
        Pattern pat = Pattern.compile(from);
        Matcher mat = pat.matcher(tmp);
        if (mat.matches() && mat.group().equals(str)) {
            return "";
        }
        String[] strs = str.split(from);
        int len = strs.length;
        String strTmp = strs[0];
        for (int i = 1; i < len; i++) {
            strTmp = strTmp + to + strs[i];
        }
        return strTmp;
    }
    
    @Override
    public String getDiff() {
        String str = "";
        int size = term.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                str += "+";
            }
            for (int j = 0; j < size; j++) {
                if (j > 0) {
                    str += "*";
                }
                Factor factorj = term.get(j);
                if (i == j) {
                    str += factorj.getDiff();
                } else {
                    str += factorj.toString();
                }
            }
        }
        return str;
    }
    
    @Override
    public String toString() {
        String str = "";
        int size = term.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                str += "*";
            }
            Factor factori = term.get(i);
            str += factori.toString();
        }
        return str;
    }
}
