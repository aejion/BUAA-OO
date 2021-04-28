import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private BigInteger temp = BigInteger.valueOf(0);
    private BigInteger aa = BigInteger.valueOf(0);
    private BigInteger bb = BigInteger.valueOf(0);
    private BigInteger cc = BigInteger.valueOf(0);
    private PolyClass poly = new PolyClass();
    private int haschange = 0;
    private boolean casex = true;
    private BigInteger maxindex = BigInteger.valueOf(10000);
    private BigInteger minindex = BigInteger.valueOf(-10000);

    public boolean getcasex() { return casex; }

    public boolean correctin(String str) {
        String str1 = str;
        String allint = "[+-]?[0-9]+";
        String index = "(\\*{2}" + allint + ")";
        String trig = "(" + "(sin|cos)\\(x\\)" + index + "?" + ")";
        String pow = "(" + "x" + index + "?" + ")";
        String finalfac = "(" + pow + "|" + trig + "|" + allint + ")";
        String term = "(" + "[+-]?" + finalfac + "(\\*" + finalfac + ")*" + ")";
        String finalstr = "[+-]?" + term + "([+-]" + term + ")*";
        Pattern pat = Pattern.compile(finalstr);
        Matcher mat = pat.matcher(str1);
        String getstr = "";
        if (mat.find()) {
            String str2 = mat.group(0);
            getstr = getstr + str2;
        }
        return !getstr.isEmpty() && getstr.length() == str1.length();
    }

    public void handleterm(String temp1,String temp2) {
        BigInteger temp3 = new BigInteger("1");
        if (temp1.charAt(0) == 'x') {
            if (temp2 != null) { temp3 = new BigInteger(temp2); }
            if (temp3.compareTo(maxindex) > 0 || temp3.compareTo(minindex) < 0) {
                casex = false;
            }
            aa = aa.add(temp3);
        }
        else if (temp1.charAt(0) == 's') {
            if (temp2 != null) { temp3 = new BigInteger(temp2); }
            bb = bb.add(temp3);
        }
        else if (temp1.charAt(0) == 'c') {
            if (temp2 != null) { temp3 = new BigInteger(temp2); }
            cc = cc.add(temp3);
        }
        else {
            temp3 = new BigInteger(temp2);
            if (haschange == 1) { temp = temp.multiply(temp3); }
            else {
                haschange = 1;
                temp = temp3;
            }
        }
    }

    public void handlestr(String str1) {
        String allint = "([+-]?[0-9]+)";
        String trig =  "(sin|cos)(\\(x\\))(\\*{2})?" + allint + "?";
        String pow = "(x)(\\*{2})?" + allint + "?";
        String finalfac = "(" + trig + "|" + pow + "|" + allint + ")";//trig:5 pow:8 allint:9
        Pattern pat = Pattern.compile(finalfac);
        Matcher mat = pat.matcher(str1);
        if (mat.find()) {
            String temp1 = mat.group();
            String temp2;
            if (temp1.charAt(0) == 'x') { temp2 = mat.group(8); }
            else if (temp1.charAt(0) == 's' || temp1.charAt(0) == 'c') { temp2 = mat.group(5); }
            else { temp2 = mat.group(9); }
            handleterm(temp1,temp2);
        }
    }

    public PolyClass handlein(String str) {
        String allint = "[+-]?[0-9]+";
        String index = "(\\*{2}" + allint + ")";
        String trig = "(" + "(sin|cos)\\(x\\)" + index + "?" + ")";
        String pow = "(" + "x" + index + "?" + ")";
        String finalfac = "(" + pow + "|" + trig + "|" + allint + ")";
        String term = "(" + "[+-]?" + finalfac + "(\\*" + finalfac + ")*" + ")";
        Pattern pat = Pattern.compile(term);
        Matcher mat = pat.matcher(str);
        while (mat.find()) {
            String str2 = mat.group(0);
            Pattern pat1 = Pattern.compile(finalfac);
            Matcher mat1 = pat1.matcher(str2);
            while (mat1.find()) {
                String str3 = mat1.group(0);
                handlestr(str3);
            }
            if (haschange == 0) { temp = BigInteger.valueOf(1); }
            poly.addtocoeff(aa,bb,cc,temp);
            aa = BigInteger.valueOf(0);
            bb = BigInteger.valueOf(0);
            cc = BigInteger.valueOf(0);
            temp = BigInteger.valueOf(0);
            haschange = 0;
        }
        return poly;
    }

    public String changeExp(String str1) {
        String str = str1;
        str = str.replaceAll("\\+\\+","+");
        str = str.replaceAll("-\\+","-");
        str = str.replaceAll("\\+-","-");
        str = str.replaceAll("--","+");
        str = str.replaceAll("\\+\\+","+");
        str = str.replaceAll("-\\+","-");
        str = str.replaceAll("\\+-","-");
        str = str.replaceAll("--","+");
        String test1 = "";
        char pch = str.charAt(0);
        test1 = test1 + pch;
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (pch == '+' || pch == '-') {
                if (ch == 'x' || ch == 's' || ch == 'c') {
                    test1 = test1 + "1*";
                }
            }
            test1 = test1 + ch;
            pch = ch;
        }
        return test1;
    }
}
