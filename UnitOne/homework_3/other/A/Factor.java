package me;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Factor {
    private String str;
    private Term term;
    private ArrayList<Index> index = new ArrayList<Index>();
    private int have;

    private static String num = "[+-]?\\d*";
    private static String exp = "\\*\\*" + "(" + num + ")";
    private static String sx = "sin" + "\\(" + "x" + "\\)";
    private static String cx = "cos" + "\\(" + "x" + "\\)";
    private static String sum = "x|(" + sx + ")|(" + cx + ")|(" + num + ")";
    private static String powFunc = "[+-]?" + "(" + sum + ")?" + "(" + exp + ")?" + "\\*?";
    private static String sf = num + "\\*?" + "sin\\(.*\\)" + "(" + exp + ")?";
    private static String cf = num + "\\*?" + "cos\\(.*\\)" + "(" + exp + ")?";

    public Factor(String s) {
        this.str = s.replaceAll("\\*\\*","\\^");
        this.term = new Term();
        this.have = 0;
    }

    public String getStr() {
        return str;
    }

    public void pre() {
        String str1 = "";
        int l = str.length();
        int flag = 0;
        for (int i = 0;i < l;i++) {
            if (str.charAt(i) == '(') {
                flag++;
                str1 += str.charAt(i);
            }
            else if (str.charAt(i) == ')') {
                flag--;
                str1 += str.charAt(i);
            }
            else if (str.charAt(i) == '*' && flag != 0) {
                str1 += "@";
            }
            else {
                str1 += str.charAt(i);
            }
        }
        String[] list = str1.split("\\*");
        int now = 0;
        for (String s : list) {
            work(s,now);
            now++;
        }
        if (have == 1) {
            if (!term.zero() || term.getEe() != BigInteger.ONE) {
                index.add(term);
            }
        }
    }

    public void work(String s,int now) {
        String s2 = s.replaceAll("\\@","\\*");
        String s1 = s2.replaceAll("\\^","\\*\\*");
        if (now != 0 && s1.charAt(0) == '-') {
            if (s1.matches(num) == false) {
                System.out.print("WRONG FORMAT!");
                exit(0); } }
        if (s1.matches(powFunc)) {
            Poly tmp = new Poly(s1);
            if (tmp.getS().equalsIgnoreCase("") == false) {
                if (tmp.getm().abs().compareTo(new BigInteger("50")) > 0) {
                    System.out.print("WRONG FORMAT!");
                    exit(0); } }
            term = term.add(tmp);
            have = 1;
            return; }
        if (s1.matches(sf)) {
            Tri tmp = new Tri(s1,"sin");
            index.add(tmp);
            return; }
        if (s1.matches(cf)) {
            Tri tmp = new Tri(s1,"cos");
            index.add(tmp);
            return; }
        int l = s1.length();
        if (s1.charAt(l - 1) != ')') {
            System.out.print("WRONG FORMAT!");
            exit(0); }
        int head = s1.indexOf('(');
        BigInteger xi = BigInteger.ONE;
        if (head == 0) {
            xi = BigInteger.ONE; }
        else if (s1.charAt(0) == '-' && head == 1) {
            xi = new BigInteger("-1"); }
        else if (s1.charAt(0) == '+' && head == 1) {
            xi = BigInteger.ONE; }
        else {
            if (s1.charAt(head - 1) == '*') {
                xi = new BigInteger(s1.substring(0,head - 1)); }
            else {
                xi = new BigInteger(s1.substring(0,head)); } }
        if (xi.compareTo(BigInteger.ZERO) != 0) {
            String ss = simplify(s1);
            if (ss.matches(powFunc)) {
                have = 1;
                Poly tmp = new Poly(ss);
                if (tmp.getS().equalsIgnoreCase("") == false) {
                    if (tmp.getm().abs().compareTo(new BigInteger("50")) > 0) {
                        System.out.print("WRONG FORMAT!");
                        exit(0); } }
                term = term.add(tmp);
            }
            else {
                //System.out.println(xi + " " + s1.substring(head + 1, l - 1));
                Exp tmp = new Exp(s1.substring(head + 1,l - 1),xi);
                index.add(tmp);
            }
            return;
        }
        return; }

    public String print() {
        String out = "";
        if (term.zero() && term.getEe().compareTo(BigInteger.ZERO) == 0) {
            return out;
        }
        for (int i = 0; i < index.size();i++) {
            out += index.get(i).print();
        }
        return out;
    }

    public String diff() {
        if (term.zero() && term.getEe().compareTo(BigInteger.ZERO) == 0) {
            return "";
        }
        int cnt = 0;
        String diff = "";
        for (int i = 0;i < index.size();i++) {
            if (index.get(i).diff().equalsIgnoreCase("0") == false) {
                diff += "+" + index.get(i).diff();
                for (int j = 0;j < index.size();j++) {
                    if (j != i) {
                        diff += "*" + index.get(j).print();
                    }
                }
            }
        }
        if (diff.equals("") == false && diff.charAt(0) == '+') {
            return diff.substring(1);
        }
        else {
            return diff;
        }
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
