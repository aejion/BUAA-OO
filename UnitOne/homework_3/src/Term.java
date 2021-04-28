import java.util.ArrayList;

public class Term {
    private  CreateFac crea = new CreateFac();

    public int IsCorrect(String str) {

        int flag = 1;
        int len = str.length();
        if (str.isEmpty() || str.charAt(len - 1) == '*') { return 0; }
        ArrayList<String> str1 = new ArrayList<>();
        int numofbr = 0;
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
            Factor fac = crea.create(str1.get(i));
            flag *= fac.Iscorrect();
        }
        return flag;
    }
}
