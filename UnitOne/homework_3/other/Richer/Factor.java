import java.math.BigInteger;

public abstract class Factor implements Cloneable {
    private BigInteger index;

    public Factor(BigInteger index) {
        this.index = index;
    }

    public abstract Expression derivative() throws CloneNotSupportedException;

    public BigInteger getIndex() {
        return index;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return null;
    }
}
