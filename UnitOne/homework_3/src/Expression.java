public class Expression {
    private Term tt = new Term();

    public boolean isdigit(char ch) { return ch >= '0' && ch <= '9'; }

    public int IsCorrect(String str) {
        if (str.isEmpty()) { return 0; }
        int flag = 1;
        int len = str.length();
        int numofbr = 0;
        StringBuilder str1 = new StringBuilder();
        char pch = str.charAt(0);
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (str1.length() == 0 && (ch == '+' || ch == '-')) { continue; }
            if (ch == '+' || ch == '-') {
                if ((pch == ')' || isdigit(pch) || pch == 'x') && numofbr == 0) {
                    flag *= tt.IsCorrect(str1.toString());
                    str1 = new StringBuilder();
                    continue;
                }
            }
            if (ch == '(') { numofbr++; }
            if (ch == ')') { numofbr--; }
            pch = ch;
            str1.append(ch);
        }
        flag *= tt.IsCorrect(str1.toString());
        return flag;
    }
}
