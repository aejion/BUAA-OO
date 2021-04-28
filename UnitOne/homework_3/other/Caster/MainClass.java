import java.util.Scanner;

public class MainClass {
    
    public static String replace(String tmp, String from, String to) {
        String str = tmp;
        String[] strs = str.split(from);
        int len = strs.length;
        String strTmp = strs[0];
        for (int i = 1; i < len; i++) {
            strTmp = strTmp + to + strs[i];
        }
        return strTmp;
    }
    
    public static String process(String str) {
        String newStr = str;
        newStr = replace(newStr, "\\*\\*", "^");
        newStr = replace(newStr, " ", "");
        newStr = replace(newStr, "\t", "");
        newStr = replace(newStr, "\\+\\+", "+");
        newStr = replace(newStr, "\\+-", "-");
        newStr = replace(newStr, "-\\+", "-");
        newStr = replace(newStr, "--", "+");
        newStr = replace(newStr, "\\+\\+", "+");
        newStr = replace(newStr, "\\+-", "-");
        newStr = replace(newStr, "-\\+", "-");
        newStr = replace(newStr, "--", "+");
        newStr = replace(newStr, "\\+sin", "+1*sin");
        newStr = replace(newStr, "-sin", "-1*sin");
        newStr = replace(newStr, "\\+cos", "+1*cos");
        newStr = replace(newStr, "-cos", "-1*cos");
        newStr = replace(newStr, "\\+x", "+1*x");
        newStr = replace(newStr, "-x", "-1*x");
        return newStr;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            System.out.println("WRONG FORMAT!");
            return;
        }
        String str = scanner.nextLine();
        CheckFormat checkFormat = new CheckFormat();
        if (!checkFormat.isExpre(str)) {
            System.out.println("WRONG FORMAT!");
            return;
        }
        str = "(" + str + ")";
        str = process(str);
        if (!checkFormat.checkIndex(str)) {
            System.out.println("WRONG FORMAT!");
            return;
        }
        Expression expre = new Expression(str);
        String output = expre.getDiff();
        if (output.charAt(0) == '+') {
            output = output.substring(1);
        }
        if (output.equals("")) {
            output = "0";
        }
        output = replace(output, "\\^", "**");
        output = finalProcess(output);
        output = replace(output, "\\[", "(");
        output = replace(output, "\\]", ")");
        System.out.println(output);
    }
    
    public static String finalProcess(String tmp) {
        String str = tmp;
        int cntS = 0;
        int cntM = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == '[' && cntM == 0) {
                cntM++;
            }
            if (cntM > 0) {
                if (str.charAt(i) == '(') {
                    cntS++;
                } else if (str.charAt(i) == ')') {
                    cntS--;
                }
                if (cntS < 0) {
                    String head = str.substring(0, i);
                    String tail = str.substring(i);
                    str = head + "]" + tail;
                    len++;
                    i++;
                    cntM = 0;
                    cntS = 0;
                    continue;
                }
            }
        }
        return str;
    }
}
