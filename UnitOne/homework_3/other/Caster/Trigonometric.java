import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trigonometric extends Factor implements Diffable {
    private BigInteger index;
    private BigInteger coeff;
    private String strInside;
    private Diffable factor;
    private boolean type;
    private boolean tagIndex = false;
    /* true -> sin
     * false -> cos
     */
    
    private BigInteger one = BigInteger.ONE;
    private BigInteger minone = new BigInteger("-1");
    private Pattern patTri = Pattern.compile(
            "^((sin|cos)\\[(?<fac>.+)\\])(\\^(?<in>[\\+-]?\\d+))?$");
    
    public Trigonometric(String strTri) {
        coeff = one;
        type = strTri.substring(0,3).equals("sin");
        Matcher matTri = patTri.matcher(strTri);
        if (!matTri.matches()) {
            System.out.println("CANNOT MATCH " +
                    "in trigonometric function initiation, IMPOSSIBLE!!!");
        }
        String strIn = matTri.group("in");
        if (strIn == null) {
            index = one;
        } else {
            index = new BigInteger(strIn);
            if (index.equals(BigInteger.ZERO)) {
                tagIndex = true;
            }
        }
        strInside = matTri.group("fac");
        String strFac = strInside;
        Pre pre = new Pre();
        strFac = pre.process(strFac);
        factor = new Factor(strFac);
    }
    
    @Override
    public String getDiff() {
        if (tagIndex) {
            return "0";
        }
        BigInteger diffCoeff = coeff.multiply(index);
        BigInteger diffIndex = index.subtract(one);
        if (!type) {
            diffCoeff = diffCoeff.multiply(minone);
        }
        String strCoeff = "";
        if (diffCoeff.equals(BigInteger.ZERO)) {
            return "";
        } else if (!diffCoeff.equals(BigInteger.ONE)) {
            strCoeff = diffCoeff + "*";
        }
        String strOri = "";
        if (!diffIndex.equals(BigInteger.ZERO)) {
            if (type) {
                if (diffIndex.equals(BigInteger.ONE)) {
                    strOri = "sin(" + strInside + ")*";
                } else {
                    strOri = "sin(" + strInside + ")**" + diffIndex + "*";
                }
            } else {
                if (diffIndex.equals(BigInteger.ONE)) {
                    strOri = "cos(" + strInside + ")*";
                } else {
                    strOri = "cos(" + strInside + ")**" + diffIndex + "*";
                }
            }
        }
        String strDiff;
        if (type) {
            strDiff = "cos(" + strInside + ")*";
        } else {
            strDiff = "sin(" + strInside + ")*";
        }
        return strCoeff + strOri + strDiff + factor.getDiff();
    }
    
    @Override
    public String toString() {
        if (!index.equals(BigInteger.ZERO)) {
            if (type) {
                if (index.equals(BigInteger.ONE)) {
                    return "sin(" + strInside + ")";
                } else {
                    return "sin(" + strInside + ")**" + index;
                }
            } else {
                if (index.equals(BigInteger.ONE)) {
                    return "cos(" + strInside + ")";
                } else {
                    return "cos(" + strInside + ")**" + index;
                }
            }
        }
        return "1";
    }
}
