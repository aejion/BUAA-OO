import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cos implements Factor {
    private String str;
    private CreateFac ff = new CreateFac();
    private String allint = "[+-]?[0-9]+";
    private String pp = "cos\\[(?<expression>.*?)\\]" + "(\\^" + allint + ")?";

    Cos(String str) { this.str = str; }

    @Override
    public int Iscorrect() {
        if (!str.substring(0,4).equals("cos(")) { return 0; }
        int len = str.length();
        int numofbr = 0;
        StringBuilder str1 = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                if (numofbr == 0 && flag == 0) { str1.append("["); }
                else { str1.append("("); }
                numofbr++;
            }
            else if (ch == ')') {
                if (numofbr == 1 && flag == 0) {
                    flag = 1;
                    str1.append("]"); }
                else { str1.append(")"); }
                numofbr--;
            }
            else { str1.append(ch); }
        }
        Pattern pat = Pattern.compile(pp);
        Matcher mat = pat.matcher(str1.toString());
        String getstr = "";
        String str3 = "";
        String str4 = "";
        if (mat.find()) {
            String str2 = mat.group(0);
            str3 = mat.group("expression");
            str4 = mat.group(2);
            getstr = getstr + str2;
        }
        if (getstr.length() != str1.length()) { return 0; }
        if (str4 != null) {
            BigInteger temppow = new BigInteger(str4.substring(1));
            if (temppow.abs().compareTo(BigInteger.valueOf(50)) > 0) { return 0; }
        }
        if (str3.isEmpty()) { return 0; }
        Factor ff1 = ff.create(str3);
        return ff1.Iscorrect();
    }

    @Override
    public String drivation() {
        if (str.equals("cos(x)")) { return "(-sin(x))"; }
        int len = str.length();
        int numofbr = 0;
        StringBuilder str1 = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                if (numofbr == 0 && flag == 0) { str1.append("["); }
                else { str1.append("("); }
                numofbr++;
            }
            else if (ch == ')') {
                if (numofbr == 1 && flag == 0) {
                    flag = 1;
                    str1.append("]"); }
                else { str1.append(")"); }
                numofbr--;
            }
            else { str1.append(ch); }
        }
        Pattern pat = Pattern.compile(pp);
        Matcher mat = pat.matcher(str1.toString());
        String str3 = "";
        String str4 = "";
        if (mat.find()) {
            str3 = mat.group("expression");
            str4 = mat.group(2);
        }
        StringBuilder ans = new StringBuilder();
        ans.append("(");
        int big = 1;
        if (str4 != null) { big = Integer.parseInt(str4.substring(1)); }
        if (big == 0) { return "(0)"; }
        if (big == 1) { ans.append("-sin(").append(str3).append(")"); }
        else if (big == 2) {
            ans.append("-2*cos(").append(str3).append(")*sin(");
            ans.append(str3).append(")");
        }
        else {
            ans.append(-big).append("*cos(").append(str3).append(")^").append(big - 1);
            ans.append("*sin(").append(str3).append(")");
        }
        Factor fac = ff.create(str3);
        ans.append("*").append(fac.drivation()).append(")");
        return ans.toString();
    }
}
