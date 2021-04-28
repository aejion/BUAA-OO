import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    private BigInteger temp = BigInteger.valueOf(0);
    private BigInteger aa = BigInteger.valueOf(0);
    private BigInteger bb = BigInteger.valueOf(0);
    private BigInteger cc = BigInteger.valueOf(0);
    private PolyClass poly = new PolyClass();
    private int haschange = 0;
    private int type = 0;
    private int hasnum = 0;
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

    public boolean isnum(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public void handlestr(String str1) {
        String str = str1;
        int symbol = 1;
        BigInteger temp1 = BigInteger.valueOf(0);
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '+') { symbol *= 1; }
            else if (ch == '-') { symbol *= -1; }
            else if (ch == 'x' && type == 0) { type = 1; }
            else if (ch == 's' && type == 0) { type = 2; }
            else if (ch == 'c' && type == 0) { type = 3; }
            else if (isnum(ch)) {
                temp1 = temp1.multiply(BigInteger.valueOf(10));
                temp1 = temp1.add(BigInteger.valueOf(ch - '0'));
                hasnum = 1;
            }
        }
        if (type == 0) {
            if (haschange == 1) {
                temp = temp.multiply(BigInteger.valueOf(symbol));
                temp = temp.multiply(temp1);
            }
            else {
                haschange = 1;
                temp = temp1;
                temp = temp.multiply(BigInteger.valueOf(symbol));
            }
        }
        if (hasnum == 0) { temp1 = BigInteger.valueOf(1); }
        if (type == 1) {
            temp1 = temp1.multiply(BigInteger.valueOf(symbol));
            aa = aa.add(temp1);
            if (temp1.compareTo(maxindex) > 0) { casex = false; }
            if (temp1.compareTo(minindex) < 0) { casex = false; }
        }
        if (type == 2) {
            temp1 = temp1.multiply(BigInteger.valueOf(symbol));
            bb = bb.add(temp1);
        }
        if (type == 3) {
            temp1 = temp1.multiply(BigInteger.valueOf(symbol));
            cc = cc.add(temp1); }
        type = 0;
    }

    public PolyClass handlein(String str) {
        String str1 = str;
        String allint = "[+-]?[0-9]+";
        String index = "(\\*{2}" + allint + ")";
        String trig = "(" + "(sin|cos)\\(x\\)" + index + "?" + ")";
        String pow = "(" + "x" + index + "?" + ")";
        String finalfac = "(" + pow + "|" + trig + "|" + allint + ")";
        String term = "(" + "[+-]?" + finalfac + "(\\*" + finalfac + ")*" + ")";
        Pattern pat = Pattern.compile(term);
        Matcher mat = pat.matcher(str1);
        while (mat.find()) {
            String str2 = mat.group(0);
            Pattern pat1 = Pattern.compile(finalfac);
            Matcher mat1 = pat1.matcher(str2);
            while (mat1.find()) {
                String str3 = mat1.group(0);
                hasnum = 0;
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String test = in.nextLine();
        MainClass mm = new MainClass();
        test = test.replaceAll("[ \\t]*","");
        boolean define = mm.correctin(test);
        if (!define) { System.out.println("WRONG FORMAT!"); }
        else {
            test = test.replaceAll("\\+\\+","+");
            test = test.replaceAll("-\\+","-");
            test = test.replaceAll("\\+-","-");
            test = test.replaceAll("--","+");
            test = test.replaceAll("\\+\\+","+");
            test = test.replaceAll("-\\+","-");
            test = test.replaceAll("\\+-","-");
            test = test.replaceAll("--","+");
            String test1 = new String();
            char pch = test.charAt(0);
            test1 = test1 + pch;
            for (int i = 1; i < test.length(); i++) {
                char ch = test.charAt(i);
                if (pch == '+' || pch == '-') {
                    if (ch == 'x' || ch == 's' || ch == 'c') {
                        test1 = test1 + "1*";
                    }
                }
                test1 = test1 + ch;
                pch = ch;
            }
            test = test1;
            PolyClass poly = mm.handlein(test);
            if (!mm.getcasex()) { System.out.println("WRONG FORMAT!"); }
            else {
                poly.getderivfunc();
                poly.handleterm();
                for (int i = 0; i < 50; i++) {
                    poly.findOptimal();
                }
                poly.printout();
            }
        }
    }
}