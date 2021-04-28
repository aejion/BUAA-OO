import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power implements Factor {
    private String str;

    Power(String str) { this.str = str; }

    @Override
    public int Iscorrect() {
        String allint = "[+-]?[0-9]+";
        String pow = "x" + "(\\^" + allint + ")?";
        Pattern pat = Pattern.compile(pow);
        Matcher mat = pat.matcher(str);
        String getstr = "";
        if (mat.find()) {
            String str2 = mat.group(0);
            getstr = getstr + str2;
        }
        if (getstr.length() > 1) {
            BigInteger big = new BigInteger(getstr.substring(2));
            if (big.abs().compareTo(BigInteger.valueOf(50)) > 0) { return 0; }
        }
        if (!getstr.isEmpty() && getstr.length() == str.length()) { return 1; }
        return 0;
    }

    @Override
    public String drivation() {
        StringBuilder str1 = new StringBuilder();
        int big = 1;
        if (str.length() > 1) { big = Integer.parseInt(str.substring(2)); }
        if (big == 0) { return "0"; }
        str1.append("(");
        if (big == 1) { str1.append("1"); }
        //else if (big == -1) { str1.append("-1"); }
        else if (big == 2) { str1.append(big).append("*x"); }
        else { str1.append(big).append("*x^").append(big - 1); }
        str1.append(")");
        return str1.toString();
    }
}
