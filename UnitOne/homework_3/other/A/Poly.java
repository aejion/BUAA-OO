package me;

import java.math.BigInteger;

public class Poly implements Index {
    private BigInteger xi;
    private BigInteger mi;
    private String str;

    public String getS() {
        return str;
    }

    public BigInteger getm() {
        return mi;
    }

    public BigInteger getx() {
        return xi;
    }

    public Poly(BigInteger xi,BigInteger mi,String s) {
        this.xi = xi;
        this.mi = mi;
        this.str = s;
    }

    public Poly(String s) {
        String str = s.replaceAll("\\+","");
        BigInteger m;
        String ts;
        if (s.charAt(0) == '-') {
            this.xi = new BigInteger("-1");
        }
        else {
            this.xi = BigInteger.ONE;
        }
        int pos1 = str.indexOf("sin(x)");
        int pos2 = str.indexOf("cos(x)");
        int pos;
        if (pos1 != -1 || pos2 != -1) {
            if (pos1 != -1) {
                pos = pos1;
                ts = "sin(x)";
            }
            else {
                pos = pos2;
                ts = "cos(x)";
            }
            pos += 5;
            if (str.length() == pos + 1) {
                m = new BigInteger("1");
            }
            else if (str.charAt(pos + 1) == '*' && str.charAt(pos + 2) == '*') {
                m = new BigInteger(str.substring(pos + 3));
            }
            else {
                m = new BigInteger(str.substring(pos + 2));
            }
        }
        else {
            pos = str.indexOf("x");
            if (pos == -1) {
                ts = "";
                m = new BigInteger(str);
            }
            else {
                ts = "x";
                if (str.length() == pos + 1) {
                    m = new BigInteger("1");
                }
                else if (str.charAt(pos + 1) == '*' && str.charAt(pos + 2) == '*') {
                    m = new BigInteger(str.substring(pos + 3));
                }
                else {
                    m = new BigInteger(str.substring(pos + 2));
                }
            }
        }
        this.mi = m;
        this.str = ts;
    }

    @Override
    public String diff() {
        return null;
    }

    public String print() {
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger minus = new BigInteger("-1");
        String tmp = "";
        tmp = str + "**" + mi;
        return tmp;
    }

}