import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Power extends Factor implements Diffable {
    private BigInteger index;
    private BigInteger coeff;
    private boolean tagIndex = false;
    
    private BigInteger zero = BigInteger.ZERO;
    private BigInteger one = BigInteger.ONE;
    
    public Power(String strPow) {
        super();
        coeff = one;
        Pattern patPow = Pattern.compile("^x(\\^(?<in>[\\+-]?\\d+))?$");
        Matcher matPow = patPow.matcher(strPow);
        if (!matPow.matches()) {
            System.out.println(
                    "CANNOT MATCH in power function initiation, IMPOSSIBLE!!!");
        }
        String strIn = matPow.group("in");
        if (strIn == null) {
            index = one;
        } else {
            index = new BigInteger(strIn);
            if (index.equals(zero)) {
                tagIndex = true;
            }
        }
    }
    
    @Override
    public String getDiff() {
        if (tagIndex) {
            return "0";
        }
        BigInteger diffCoeff = coeff.multiply(index);
        BigInteger diffIndex = index.subtract(one);
        String str;
        if (!diffCoeff.equals(zero)) {
            if (diffIndex.equals(zero)) {
                str = diffCoeff.toString();
            } else if (diffIndex.equals(one)) {
                str = diffCoeff + "*x";
            } else {
                str = diffCoeff + "*x**" + diffIndex;
            }
        } else {
            str = "1";
        }
        return str;
    }
    
    @Override
    public String toString() {
        if (tagIndex) {
            return "1";
        }
        if (index.equals(one)) {
            return coeff + "*x";
        } else if (index.equals(zero)) {
            return coeff.toString();
        } else {
            return coeff + "*x**" + index;
        }
    }
}
