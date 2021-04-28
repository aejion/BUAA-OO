public class Exp implements Factor {
    private String str;
    private Expression ex = new Expression();

    Exp(String str) { this.str = str; }

    public boolean isdigit(char ch) { return ch >= '0' && ch <= '9'; }

    @Override
    public String drivation() {
        int len = str.length();
        String str1 = str;
        if (str.charAt(0) == '(') {
            int tempnum = 1;
            for (int i = 1; i < len; i++) {
                char ch = str.charAt(i);
                if (ch == '(') { tempnum++; }
                if (ch == ')') {
                    tempnum--;
                    if (tempnum == 0) {
                        if (i == len - 1) { str1 = str.substring(1,len - 1); }
                        break;
                    }
                }
            }
        }
        len = str1.length();
        StringBuilder str2 = new StringBuilder();
        StringBuilder ans = new StringBuilder();
        char pch = str1.charAt(0);
        int numofbr = 0;
        ans.append('(');
        int numofterm = 0;
        char symbol = '+';
        for (int i = 0; i < len; i++) {
            char ch = str1.charAt(i);
            if (str2.length() == 0 && (ch == '+' || ch == '-')) {
                symbol = ch;
                continue;
            }
            if (ch == '+' || ch == '-') {
                if ((pch == ')' || isdigit(pch) || pch == 'x') && numofbr == 0) {
                    i--;
                    if (symbol == '-' || numofterm > 0) { ans.append(symbol); }
                    Ter te = new Ter(str2.toString());
                    ans.append(te.drivation());
                    numofterm++;
                    symbol = '+';
                    str2 = new StringBuilder();
                    continue;
                }
            }
            if (ch == '(') { numofbr++; }
            if (ch == ')') { numofbr--; }
            pch = ch;
            str2.append(ch);
        }
        if (symbol == '-' || numofterm > 0) { ans.append(symbol); }
        Ter te = new Ter(str2.toString());
        ans.append(te.drivation());
        ans.append(')');
        return ans.toString();
    }

    @Override
    public int Iscorrect() {
        if (str.isEmpty()) { return 0; }
        if (str.charAt(str.length() - 1) != ')') { return 0; }
        String str1 = str.substring(1,str.length() - 1);
        return ex.IsCorrect(str1);
    }

    @Override
    public String toString() {
        return drivation();
    }
}
