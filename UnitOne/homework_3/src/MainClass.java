import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public boolean hasotc(String str) {
        if (str.isEmpty()) { return true; }
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == ' ' || ch == '\t' || ch >= '0' && ch <= '9' ||
                    ch == 'x' || ch == 's' || ch == 'i') {
                continue;
            }
            if (ch == 'n' || ch == 'c' || ch == 'o' ||
                    ch == '(' || ch == ')' || ch == '*') { continue; }
            if (ch == '+' || ch == '-') { continue; }
            return true;
        }
        return false;
    }

    public boolean isdigit(char ch) { return ch >= '0' && ch <= '9'; }

    public boolean correctblank(String str) {
        int len = str.length();
        int state = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '*') {
                if (state == 0) { state = 1; }
                else if (state == 2) { return false; }
            }
            else if (ch == ' ' || ch == '\t') { if (state == 1) { state = 2; } }
            else { state = 0; }
        }
        state = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == 's' || ch == 'i' || ch == 'n' || ch == 'c' || ch == 'o') {
                if (state == 0) { state = 1; }
                else if (state == 2) { return false; }
            }
            else if (ch == ' ' || ch == '\t') { if (state == 1) { state = 2; } }
            else { state = 0; }
        }
        state = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (isdigit(ch)) {
                if (state == 0) { state = 1; }
                else if (state == 2) { return false; }
            }
            else if (ch == ' ' || ch == '\t') { if (state == 1) { state = 2; } }
            else { state = 0; }
        }
        return true;
    }

    public boolean correctblank1(String str) {
        int len = str.length();
        int numofsym = 0;
        int hasb = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                numofsym++;
                if (numofsym > 3) { return false; }
            }
            else if (ch == ' ' || ch == '\t') {
                if (numofsym == 3) { hasb = 1; }
            }
            else if (isdigit(ch)) {
                if (numofsym == 3 && hasb == 1) { return false; }
                hasb = 0;
                numofsym = 0;
            }
            else {
                if (numofsym == 3) { return false; }
                hasb = 0;
                numofsym = 0;
            }
        }
        int state = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '*') {
                if (state == 0) { state = 1; }
                else if (state != 1) { state = 1; }
            }
            else if (ch == '-' || ch == '+') { if (state == 1) { state = 2; } }
            else if (ch == ' ' || ch == '\t') { if (state == 2) { state = 3; } }
            else if (isdigit(ch)) {
                if (state == 3) { return false; }
                else { state = 0; }
            }
            else { state = 0; }
        }
        String str1 = "(sin|cos)\\s*\\(\\s*[+-]\\s+[0-9]+\\s*\\)";
        Pattern pat = Pattern.compile(str1);
        Matcher mat = pat.matcher(str);
        if (mat.find()) { return false; }
        return true;
    }

    public static void main(String[] args) {
        MainClass mm = new MainClass();
        Scanner in = new Scanner(System.in);
        String test = in.nextLine();
        if (mm.hasotc(test) || !mm.correctblank(test) || !mm.correctblank1(test)) {
            System.out.println("WRONG FORMAT!"); }
        else {
            test = test.replaceAll("[ \\t]*","");
            test = test.replaceAll("\\*{2}","^");
            Expression ex = new Expression();
            if (ex.IsCorrect(test) == 0) { System.out.println("WRONG FORMAT!"); }
            else {
                test = test.replaceAll("\\+\\+","+");
                test = test.replaceAll("-\\+","-");
                test = test.replaceAll("\\+-","-");
                test = test.replaceAll("--","+");
                test = test.replaceAll("\\+\\+","+");
                test = test.replaceAll("-\\+","-");
                test = test.replaceAll("\\+-","-");
                test = test.replaceAll("--","+");
                FindOptional find = new FindOptional();
                test = find.deletezero(test);
                Exp ee = new Exp(test);
                String finalans = ee.toString();
                String opans = find.deleteblank(finalans);
                // System.out.println(finalans.replaceAll("\\^","**"));
                while (!finalans.equals(opans)) {
                    finalans = opans;
                    finalans = finalans.replaceAll("\\+\\+","+");
                    finalans = finalans.replaceAll("-\\+","-");
                    finalans = finalans.replaceAll("\\+-","-");
                    finalans = finalans.replaceAll("--","+");
                    //System.out.println(finalans.replaceAll("\\^","**"));
                    opans = find.deleteblank(finalans);
                }
                opans = find.getopp(finalans);
                while (!finalans.equals(opans)) {
                    finalans = opans;
                    finalans = finalans.replaceAll("\\+\\+","+");
                    finalans = finalans.replaceAll("-\\+","-");
                    finalans = finalans.replaceAll("\\+-","-");
                    finalans = finalans.replaceAll("--","+");
                    opans = find.getopp(finalans);
                }
                finalans = finalans.replaceAll("\\^","**");
                System.out.println(finalans);
            }
        }
        in.close();
    }
}