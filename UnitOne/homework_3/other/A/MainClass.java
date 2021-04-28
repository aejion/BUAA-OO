package me;

import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainClass {
    private static Exp all;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sread = scanner.nextLine();
        check(sread);
        String stmp = "";
        String s = "";
        if (WFoo(sread) == false) {
            System.out.print("WRONG FORMAT!");
            return;
        }
        else {
            stmp = sread.replaceAll("[ \\t]","");
        }
        if (WF(stmp) == false) {
            System.out.print("WRONG FORMAT!");
            return;
        }
        else {
            s = change(stmp);
        }
        all = new Exp(s, BigInteger.ONE);
        String out = all.diff();
        String tmp = sb(out);
        String ans = simplify(tmp);
        System.out.print(ans);
    }

    public static boolean WFoo(String s) {
        String sp = "[ \\t]+";
        String s1 = ".*[+-]{2,}" + sp + "\\d.*";
        String s9 = ".*[+-]" + sp + "[+-]" + sp + "\\d.*";
        String s8 = ".*\\([+-]" + sp + "\\d.*";
        String s2 = ".*\\d" + sp + "\\d.*";
        String s3 = ".*s" + sp + "in.*";
        String s4 = ".*si" + sp + "n.*";
        String s5 = ".*c" + sp + "os.*";
        String s6 = ".*co" + sp + "s.*";
        String s7 = ".*\\*" + sp + "\\*.*";
        if (s.matches(s1) || s.matches(s2) || s.matches(s3) || s.matches(s4)) {
            return false;
        }
        else if (s.matches(s5) || s.matches(s6) || s.matches(s7) || s.matches(s8)) {
            return false;
        }
        else if (s.matches(s9)) {
            return false;
        }
        else {
            return true;
        }
    }

    public static void check(String s) {
        int l = s.length();
        String m = "[ \\txsin()co*+-1234567890]*";
        if (s.matches(m) == false) {
            System.out.print("WRONG FORMAT!");
            exit(0);
        }
        int match = 0;
        for (int i = 0; i < l;i++) {
            if (s.charAt(i) == '(') {
                match++;
            }
            if (s.charAt(i) == ')') {
                match--;
            }
        }
        if (match != 0) {
            System.out.print("WRONG FORMAT!");
            exit(0);
        }
        return;
    }

    public static boolean WF(String s) {
        int l = s.length();
        if (l == 0) {
            return false;
        }
        char ch = s.charAt(l - 1);
        if (ch == '+' || ch == '-' || ch == '*') {
            return false;
        }
        String s1 = s;
        String m1 = ".*[+-]{3}(x|sin\\(x\\)|cos\\(x\\)).*";
        String m2 = ".*[+-]{4}.*";
        String m3 = ".*\\*\\*(x|sin\\(x\\)|cos\\(x\\)).*";
        String m4 = ".*[+-]\\*(x|sin\\(x\\)|cos\\(x\\)|\\d).*";
        String m5 = ".*\\*\\*\\*.*";
        String m6 = ".*\\*{1,2}[+|-]{2}.*";
        String m7 = ".*(x|sin\\(x\\)|cos\\(x\\)){2,}.*";
        String m8 = ".*\\d\\*\\*[+-]*\\d.*";
        String m9 = ".*x\\(.*";
        String m10 = ".*\\(\\).*";
        String m11 = ".*\\)\\(.*";
        if (s1.matches(m1) || s1.matches(m2) || s1.matches(m3) || s1.matches(m4)) {
            return false;
        }
        else if (s1.matches(m5) || s1.matches(m6) || s1.matches(m7) || s1.matches(m8)) {
            return false;
        }
        else if (s1.matches(m9) || s1.matches(m10) || s1.matches(m11)) {
            return false;
        }
        else {
            return true;
        }
    }

    public static String change(String s) {
        String s1 = s.replaceAll("\\+\\+\\+","\\+");
        String s2 = s1.replaceAll("---","-");
        String s3 = s2.replaceAll("\\+-\\+","-");
        String s4 = s3.replaceAll("-\\+-","+");
        String s5 = s4.replaceAll("\\+\\+-","-");
        String s6 = s5.replaceAll("-\\+\\+","-");
        String s7 = s6.replaceAll("\\+--","+");
        String s8 = s7.replaceAll("--\\+","+");
        String s9 = s8.replaceAll("\\+-|-\\+","-");
        String s10 = s9.replaceAll("\\+\\+|--","\\+");
        return s10;
    }

    public static String simplify(String s) {
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

    public static String sb(String s) {
        String s1 = s.replaceAll("\\*\\([+]?1\\)","");
        String s2 = s1.replaceAll("\\([+]?1\\)\\*","");
        String s3 = s2.replaceAll("\\*\\([+]?1\\)","");
        String s4 = s3.replaceAll("\\(\\([+]?1\\)\\)\\*","");
        String s5 = s4.replaceAll("\\*\\(\\([+]?1\\)\\)","");
        return s5;
    }

}
