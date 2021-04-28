import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant implements Factor {
    private String str;

    Constant(String str) { this.str = str; }

    @Override
    public int Iscorrect() {
        String allint = "[+-]?[0-9]+";
        Pattern pat = Pattern.compile(allint);
        Matcher mat = pat.matcher(str);
        String getstr = "";
        if (mat.find()) {
            String str2 = mat.group(0);
            getstr = getstr + str2;
        }
        if (!getstr.isEmpty() && getstr.length() == str.length()) { return 1; }
        return 0;
    }

    @Override
    public String drivation() {
        return "(0)";
    }
}
