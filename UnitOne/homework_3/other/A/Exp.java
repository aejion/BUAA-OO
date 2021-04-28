package me;

import java.math.BigInteger;

public class Exp implements Index {
    private String str;
    private Factor[] factors = new Factor[105];
    private int cnt;
    private BigInteger xi;

    public Exp(String sin,BigInteger xi) {
        //System.out.println(sin + " " + xi);
        String s = "";
        int l = sin.length();
        if (sin.charAt(0) == '(' && sin.charAt(l - 1) == ')') {
            s = simplify(sin);
        }
        else {
            s = sin;
        }
        //System.out.print(s);
        l = s.length();
        this.str = s;
        int cnt = 0;
        int st = 0;
        for (int i = 0; i < l;i++) {
            if (s.charAt(i) == '(') {
                int match = 0;
                int j;
                for (j = i;j < l;j++) {
                    if (s.charAt(j) == '(') {
                        match++;
                    }
                    else if (s.charAt(j) == ')') {
                        match--;
                    }
                    if (match == 0) {
                        break;
                    }
                }
                i = j;
            }
            else if (s.charAt(i) == '*' && i < l - 1) {
                if (s.charAt(i + 1) == '+' || s.charAt(i + 1) == '-') {
                    i++;
                    continue;
                }
            }
            else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                if (i == 0) {
                    continue;
                }
                factors[cnt] = new Factor(s.substring(st,i));
                cnt++;
                st = i;
            }
        }
        factors[cnt] = new Factor(s.substring(st,l));
        for (int i = 0;i <= cnt;i++) {
            factors[i].pre();
        }
        this.cnt = cnt;
        this.xi = xi;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String diff() {
        String diff = "";
        for (int i = 0;i <= cnt;i++) {
            String tmp = simplify(factors[i].diff());
            if (tmp.equalsIgnoreCase("") == true) {
                continue;
            }
            if (tmp.charAt(0) == '+') {
                diff += tmp;
            }
            else if (tmp.charAt(0) == '-') {
                diff += tmp;
            }
            else {
                if (tmp.matches("0") == false) {
                    diff += "+" + tmp;
                }
            }
        }
        if (diff == "") {
            return "0";
        }
        if (diff.charAt(0) == '+') {
            diff =  diff.substring(1);
        }
        if (xi.compareTo(BigInteger.ONE) != 0) {
            if (xi.compareTo(new BigInteger("-1")) == 0) {
                diff = "-(" + diff + ")";
            }
            else {
                diff = xi + "*(" + diff + ")";
            }
        }
        else {
            diff = "(" + diff + ")";
        }
        return diff;
    }

    @Override
    public String print() {
        String out = "";
        String str = "";
        for (int i = 0;i <= cnt;i++) {
            str += factors[i].print();
            if (i != cnt) {
                if (factors[i + 1].print().charAt(0) != '-') {
                    str += "+";
                }
            }
        }
        out += "(" + str + ")";
        if (xi.compareTo(BigInteger.ONE) != 0) {
            if (xi.compareTo(new BigInteger("-1")) == 0) {
                out = "-" + out;
            }
            else {
                out = xi + "*" + out;
            }
        }
        return out;
    }

    public String simplify(String s) {
        int[] stack = new int[105];
        int match = 0;
        int l = s.length();
        int cnt = 0;
        for (int i = 0;i < l;i++) {
            if (s.charAt(i) == '(') {
                stack[cnt++] = i;
            }
            else if (s.charAt(i) == ')') {
                if (i != l - 1) {
                    cnt--;
                }
                else {
                    if (stack[0] == 0) {
                        match = 1;
                    }
                }
            }
        }
        if (match == 1) {
            return simplify(s.substring(1,l - 1));
        }
        else {
            return s;
        }
    }

}
