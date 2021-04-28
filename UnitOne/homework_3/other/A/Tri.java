package me;

import java.math.BigInteger;

import static java.lang.System.exit;

public class Tri implements Index {
    private BigInteger xi;
    private String type;
    private Exp inner;
    private String all;
    private BigInteger mi;
    private boolean chang;
    private boolean special;

    public static String reverse1(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public Tri(String str,String type) {
        this.type = type;
        int head = str.indexOf('(');
        int tail = reverse1(str).indexOf(')');
        int l = str.length();
        if (str.charAt(head + 1) != '(') {
            this.special = true;
        }
        String s = str.substring(head + 1,l - tail - 1);
        check(s);
        this.inner = new Exp(s,BigInteger.ONE);
        this.all = inner.getStr();
        String str1 = str.replaceAll("\\+","");
        int pos = str1.indexOf(type);
        if (pos == 0) {
            this.xi = BigInteger.ONE;
        }
        else if (str1.charAt(0) == '-') {
            this.xi = new BigInteger("-1");
        }
        else {
            this.xi = new BigInteger(str1.substring(0,pos));
        }
        int len = str1.length();
        for (int i = len - 1;i >= 0;i--) {
            if (str1.charAt(i) == ')') {
                if (i == len - 1) {
                    this.mi = BigInteger.ONE;
                    break;
                }
                else {
                    if (i <= len - 4 && str1.charAt(i + 1) == '*' && str1.charAt(i + 2) == '*') {
                        this.mi = new BigInteger(str1.substring(i + 3));
                        break;
                    }
                }
            }
        }
        if (this.mi.abs().compareTo(new BigInteger("50")) > 0) {
            System.out.print("WRONG FORMAT!");
            exit(0);
        }
    }

    @Override
    public String diff() {
        if (chang == true) {
            return "0";
        }
        if (mi.compareTo(BigInteger.ZERO) == 0) {
            return "0";
        }
        String out = "";
        BigInteger now = xi;
        if (type.equalsIgnoreCase("cos")) {
            now = now.multiply(new BigInteger("-1"));
        }
        now = now.multiply(mi);
        if (now.compareTo(BigInteger.ONE) != 0) {
            if (now.compareTo(new BigInteger("-1")) == 0) {
                out += '-';
            }
            else {
                out += now + "*";
            }
        }
        if (mi.compareTo(BigInteger.ONE) != 0) {
            if (special == true) {
                out += type + "(" + all + ")";
            }
            else {
                out += type + "((" + all + "))";
            }
            if (mi.compareTo(new BigInteger("2")) != 0) {
                out += "**" + mi.subtract(BigInteger.ONE);
            }
            out += "*";
        }
        //System.out.println(out);
        if (type.equalsIgnoreCase("sin")) {
            out += "cos(";
        }
        else {
            out += "sin(";
        }
        if (special == true) {
            out += all;
        }
        else {
            out += "(" + all + ")";
        }
        out += ")*(";
        if (inner.diff().charAt(0) == '+') {
            out += inner.diff().substring(1);
        }
        else {
            out += inner.diff();
        }
        out += ")";
        return out;
    }

    @Override
    public String print() {
        String out = "";
        if (xi.compareTo(BigInteger.ONE) != 0) {
            if (xi.compareTo(new BigInteger("-1")) == 0) {
                out += "-";
            }
            else {
                out += xi + "*";
            }
        }
        out += type;
        out += "(";
        out += all;
        out += ")";
        if (mi.compareTo(BigInteger.ONE) != 0) {
            out += "**";
            out += mi;
        }
        return out;
    }

    public void check(String s1) {
        String num = "[+-]?\\d*";
        if (s1.matches(num)) {
            this.chang = true;
            return;
        }
        int l = s1.length();
        if (s1.charAt(0) == '(' && s1.charAt(l - 1) == ')') {
            return;
        }
        else {
            String s = s1.replaceAll("\\*\\*","\\^");
            l = s.length();
            int match = 0;
            //System.out.println(s);
            for (int i = 0;i < l;i++) {
                if (s.charAt(i) == '(') {
                    match++;
                }
                else if (s.charAt(i) == ')') {
                    match--;
                }
                else if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*') {
                    if (i == 0) {
                        System.out.print("WRONG FORMAT!");
                        exit(0);
                    }
                    if (match == 0 && s.charAt(i - 1) != '^') {
                        System.out.print("WRONG FORMAT!");
                        exit(0);
                    }
                }
            }
        }
        return;
    }

}
