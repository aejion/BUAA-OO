import java.util.ArrayList;

public class Ter implements Factor {
    private String str;
    private CreateFac crea = new CreateFac();

    Ter(String str) { this.str = str; }

    @Override
    public int Iscorrect() {
        return 0;
    }

    @Override
    public String drivation() {
        ArrayList<String> str1 = new ArrayList<>();
        int len = str.length();
        StringBuilder str3 = new StringBuilder();
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
        str3.append('(');
        str1.add(str2);
        len = str1.size();
        int numofter = 0;
        for (int i = 0; i < len; i++) {
            Factor fac = crea.create(str1.get(i));
            if (fac.getClass() == Constant.class) { continue; }
            String str4 = fac.drivation();
            if (str4.equals("0")) { continue; }
            if (numofter > 0) { str3.append('+'); }
            str3.append(str4);
            numofter++;
            for (int j = 0; j < len; j++) {
                if (j != i) {
                    str3.append('*');
                    str3.append(str1.get(j));
                }
            }
        }
        str3.append(')');
        if (str3.length() == 2) { return "(0)"; }
        return str3.toString();
    }
}
