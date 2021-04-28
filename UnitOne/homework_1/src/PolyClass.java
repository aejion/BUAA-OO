import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PolyClass {
    private HashMap<BigInteger,BigInteger> coeff = new HashMap<>();
    private HashMap<BigInteger,BigInteger> deriv = new HashMap<>();
    private BigInteger tvalue = BigInteger.valueOf(0);
    private BigInteger tkey = BigInteger.valueOf(0);
    private int tflag = 0;
    private int first = 1;
    private BigInteger const0 = BigInteger.valueOf(0);
    private BigInteger const1 = BigInteger.valueOf(1);
    private BigInteger const11 = BigInteger.valueOf(-1);
    private boolean hasprint = false;

    void addtocoeff(BigInteger str, BigInteger temp) {
        BigInteger pcoe = BigInteger.valueOf(0);
        if (coeff.containsKey(str)) {
            pcoe = coeff.get(str);
        }
        coeff.put(str, temp.add(pcoe));
    }

    void derivfunc() {
        BigInteger key = BigInteger.valueOf(0);
        BigInteger value = BigInteger.valueOf(0);
        Iterator it = coeff.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (BigInteger) entry.getKey();
            value = (BigInteger) entry.getValue();
            if (key.equals(BigInteger.valueOf(0)) == false) {
                BigInteger pcoe = BigInteger.valueOf(0);
                if (deriv.containsKey(key.add(BigInteger.valueOf(-1)))) {
                    pcoe = deriv.get(key.add(BigInteger.valueOf(-1))); }
                pcoe = pcoe.add(key.multiply(value));
                deriv.put(key.add(BigInteger.valueOf(-1)),pcoe);
            }
        }
        Iterator it1 = deriv.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry entry = (Map.Entry) it1.next();
            key = (BigInteger) entry.getKey();
            value = (BigInteger) entry.getValue();
            if (value.compareTo(BigInteger.valueOf(0)) > 0) {
                tvalue = value;
                tkey = key;
                tflag = 1;
                break;
            }
        }
    }

    void printderiv1() {
        if (tflag == 0) { return; }
        first = 0;
        hasprint = true;
        if (tkey.equals(const0) == true) { System.out.print(tvalue); }
        else {
            if (tkey.equals(const1) && tvalue.equals(const1)) { System.out.print("x"); }
            else if (tkey.equals(const1) && tvalue.equals(const11)) { System.out.print("-x"); }
            else if (tkey.equals(const1)) { System.out.print(tvalue + "*x"); }
            else if (tvalue.equals(const1)) { System.out.print("x**" + tkey); }
            else if (tvalue.equals(const11)) { System.out.print("-x**" + tkey); }
            else { System.out.print(tvalue + "*x**" + tkey); }
        }
    }

    void printderiv2() {
        BigInteger key = const0;
        BigInteger value = const0;
        Iterator it = deriv.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (BigInteger) entry.getKey();
            value = (BigInteger) entry.getValue();
            if (value.equals(const0) || (tflag == 1 && key.equals(tkey))) { continue; }
            hasprint = true;
            if (first == 1) {
                first = 0;
                if (key.equals(const0)) { System.out.print(value); }
                else {
                    if (key.equals(const1) && value.equals(const1)) { System.out.print("x"); }
                    else if (key.equals(const1) && value.equals(const11)) {
                        System.out.print("-x"); }
                    else if (key.equals(const1)) { System.out.print(value + "*x"); }
                    else if (value.equals(const1)) { System.out.print("x**" + key); }
                    else if (value.equals(const11)) { System.out.print("-x**" + key); }
                    else { System.out.print(value + "*x**" + key); }
                }
            }
            else {
                if (key.equals(const0)) {
                    if (value.compareTo(const0) > 0) { System.out.print("+" + value); }
                    else { System.out.print(value); }
                }
                else {
                    if (value.compareTo(const0) > 0) { System.out.print("+"); }
                    if (key.equals(const1) && value.equals(const1)) { System.out.print("x"); }
                    else if (key.equals(const1) && value.equals(const11)) {
                        System.out.print("-x"); }
                    else if (key.equals(const1)) { System.out.print(value + "*x"); }
                    else if (value.equals(const1)) { System.out.print("x**" + key); }
                    else if (value.equals(const11)) { System.out.print("-x**" + key); }
                    else { System.out.print(value + "*x**" + key); }
                }
            }
        }
        if (hasprint == false) { System.out.print(0); }
    }
}
