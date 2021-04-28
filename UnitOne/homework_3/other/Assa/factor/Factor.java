package factor;

import main.Term;

import java.math.BigInteger;

public interface Factor {
    String getType();

    BigInteger getPow();

    Factor getSubFac();

    boolean equals(Factor oth);

    Term getDiff();

    String print();
}
