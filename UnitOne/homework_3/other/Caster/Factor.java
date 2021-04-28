import java.util.regex.Pattern;

public class Factor implements Diffable {
    private Diffable factor;
    private boolean tag;
    
    private Pattern patConst = Pattern.compile("^[\\+-]?\\d+$");
    private Pattern patPow = Pattern.compile("^x(\\^[\\+-]?(?<in>\\d+))?$");
    private Pattern patTri = Pattern.compile(
            "^((sin|cos)\\[(?<fac>.+?)\\])(\\^[\\+-]?(?<in>\\d+))?$");
    private Pattern patExpre = Pattern.compile("^\\[(?<exp>.+?)\\]$");
    
    public Factor() {}
    
    public Factor(String strFac) {
        tag = false;
        if (patConst.matcher(strFac).matches()) {
            factor = new Constant(strFac);
        } else if (patPow.matcher(strFac).matches()) {
            factor = new Power(strFac);
        } else if (patTri.matcher(strFac).matches()) {
            factor = new Trigonometric(strFac);
        } else if (patExpre.matcher(strFac).matches()) {
            tag = true;
            String newStrFac = strFac.substring(1, strFac.length() - 1);
            factor = new Expression(newStrFac);
        } else {
            System.out.println("Format error in factor " +
                    "initiation, IMPOSSIBLE!!!");
        }
    }
    
    @Override
    public String getDiff() {
        String str = "";
        if (tag) {
            str = "(" + factor.getDiff() + ")";
        }
        str = factor.getDiff();
        if (str == "") {
            str = "0";
        }
        return str;
    }
    
    @Override
    public String toString() {
        String str = "";
        if (tag) {
            str = "(" + factor.toString() + ")";
        }
        str = factor.toString();
        if (str == "") {
            str = "0";
        }
        return str;
    }
}
