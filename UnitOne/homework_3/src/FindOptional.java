import java.math.BigInteger;
import java.util.ArrayList;

public class FindOptional {
    private CreateFac ff = new CreateFac();

    public String deleteblank(String str) {
        ArrayList<String> arr = getarr(str);
        int len = arr.size();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str1 = arr.get(i);
            if (str1.equals("@")) { ans.append("+"); }
            else if (str1.equals("#")) { ans.append("-"); }
            else {
                String op = "@";
                if (i > 0) { op = arr.get(i - 1); }
                String temp = getopterm(str1,op);
                ans.append(temp);
            }
        }
        return ans.toString();
    }

    public boolean isdigit(char ch) { return ch >= '0' && ch <= '9'; }

    public ArrayList<String> getarr(String str) {
        ArrayList<String> arr = new ArrayList<>();
        int len = str.length();
        int numofbr = 0;
        StringBuilder str1 = new StringBuilder();
        char pch = str.charAt(0);
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (str1.length() == 0 && (ch == '+' || ch == '-')) {
                if (ch == '+') { arr.add("@"); }
                if (ch == '-') { arr.add("#"); }
                continue; }
            if (ch == '+' || ch == '-') {
                if ((pch == ')' || isdigit(pch) || pch == 'x') && numofbr == 0) {
                    arr.add(str1.toString());
                    if (ch == '+') { arr.add("@"); }
                    if (ch == '-') { arr.add("#"); }
                    str1 = new StringBuilder();
                    continue;
                }
            }
            if (ch == '(') { numofbr++; }
            if (ch == ')') { numofbr--; }
            pch = ch;
            str1.append(ch);
        }
        arr.add(str1.toString());
        return arr;
    }

    public String getopterm(String str,String op) {
        int i = 0;
        if (str.charAt(0) != '(') { return str; }
        int len = str.length();
        int numofbr = 0;
        for (i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '(') { numofbr++; }
            if (ch == ')') {
                numofbr--;
                if (numofbr == 0) { break; }
            }
        }
        StringBuilder ans = new StringBuilder(str);
        if (i == len - 1) {
            ans = new StringBuilder();
            String str1 = str.substring(1,len - 1);
            numofbr = 0;
            if (op.equals("#")) {
                char pch = ' ';
                for (int j = 0; j < str1.length(); j++) {
                    char ch = str1.charAt(j);
                    if (pch == ' ' && (ch == '+' || ch == '-')) {
                        if (ch == '+') { ans.append('+'); }
                        if (ch == '-') { ans.append('-'); }
                        continue;
                    }
                    else if ((ch == '+' || ch == '-')) {
                        if ((pch == ')' || isdigit(pch) || pch == 'x') && numofbr == 0) {
                            if (ch == '+') { ans.append('-'); }
                            if (ch == '-') { ans.append('+'); }
                            continue;
                        }
                    }
                    if (ch == ')') { numofbr--; }
                    if (ch == '(') { numofbr++; }
                    pch = ch;
                    ans.append(ch);
                }
            }
            else { ans.append(str1); }
        }
        return ans.toString();
    }

    public String getopp(String str) {
        ArrayList<String> arr = getarr(str);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            String str1 = arr.get(i);
            if (str1.equals("@")) { ans.append("+"); }
            else if (str1.equals("#")) { ans.append("-"); }
            else {
                String temp = getopyz(str1);
                ans.append(temp);
            }
        }
        return ans.toString();
    }

    public String getopyz(String str) {
        int len = str.length();
        ArrayList<String> str1 = new ArrayList<>();
        int numofbr = 0;
        StringBuilder ans = new StringBuilder();
        String str2 = "";
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '*' && numofbr == 0) {
                str1.add(str2);
                str2 = "";
                continue;
            }
            str2 = String.format("%s%s", str2, ch);
            if (ch == '(') { numofbr++; }
            if (ch == ')') { numofbr--; }
        }
        str1.add(str2);
        len = str1.size();
        for (int i = 0; i < len; i++) {
            int only = 1;
            String str3 = str1.get(i);
            int len1 = str3.length();
            for (int j = 0; j < len1; j++) {
                char ch = str3.charAt(j);
                if (isdigit(ch) || ch == '(' || ch == ')') { continue; }
                only = 0;
            }
            if (only == 1) {
                int temp = 0;
                while (str3.charAt(temp) == '(') { temp++; }
                str1.set(i,str3.substring(temp,len1 - temp));
            }
        }
        int flag = 0;
        for (int i = 0; i < len; i++) {
            String tempstr = str1.get(i);
            Factor fac = ff.create(tempstr);
            if (fac.getClass() == Constant.class) {
                BigInteger big = new BigInteger(tempstr);
                if (big.equals(BigInteger.valueOf(0))) { return "0"; }
                if (big.equals(BigInteger.valueOf(1))) { continue; }
                if (flag == 0) { flag = 1; }
                else if (flag == 1) { ans.append("*"); }
                ans.append(big);
            }
            else {
                if (flag == 0) { flag = 1; }
                else if (flag == 1) { ans.append("*"); }
                ans.append(tempstr);
            }
        }
        if (ans.length() == 0) { return "1"; }
        return ans.toString();
    }

    public String deletezero(String str) {
        int len = str.length();
        StringBuilder str1 = new StringBuilder();
        for (int i = 0; i < len;) {
            char ch = str.charAt(i);
            if (isdigit(ch)) {
                BigInteger big = new BigInteger("0");
                while (isdigit(ch) && i < len) {
                    big = big.multiply(BigInteger.valueOf(10));
                    big = big.add(BigInteger.valueOf(ch - '0'));
                    i++;
                    if (i < len) { ch = str.charAt(i); }
                }
                str1.append(big);
            }
            else {
                str1.append(ch);
                i++;
            }
        }
        return str1.toString();
    }
}
