import java.math.BigInteger;

public class Constant extends Factor implements Diffable {
    private BigInteger constant;
    
    public Constant(String strConst) {
        constant = new BigInteger(strConst);
    }
    
    @Override
    public String getDiff() {
        return BigInteger.ZERO.toString();
    }
    
    public String toString() {
        return constant.toString();
    }
}
