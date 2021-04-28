import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public PolyClass handlein(String str) {
        PolyClass temppoly = new PolyClass();
        Pattern pat = Pattern.compile("[+-]?([0-9]*(\\*))?x(\\*{2}[+-]?[0-9]*)?");
        Matcher mat = pat.matcher(str);
        while (mat.find()) {
            String temp = mat.group();
            int len = temp.length();
            int visx = 0;
            int symbol1 = 1;
            int symbol2 = 1;
            int flag1 = 0;
            int flag2 = 0;
            BigInteger tempcoeff = BigInteger.valueOf(0);
            BigInteger tempstr = BigInteger.valueOf(0);
            for (int i = 0; i < len; i++) {
                char ch = temp.charAt(i);
                if (visx == 0) {
                    if (ch == '-') { symbol1 = -1; }
                    else if (ch >= '0' && ch <= '9') {
                        tempcoeff = tempcoeff.multiply(BigInteger.valueOf(10));
                        tempcoeff = tempcoeff.add(BigInteger.valueOf(ch - '0'));
                        flag1 = 1; }
                    else if (ch == 'x') {
                        visx = 1;
                        if (flag1 == 0) { tempcoeff = BigInteger.valueOf(1); }
                        tempcoeff = tempcoeff.multiply(BigInteger.valueOf(symbol1));
                    }
                }
                else if (visx == 1) {
                    if (ch == '-') { symbol2 = -1; }
                    else if (ch >= '0' && ch <= '9') {
                        tempstr = tempstr.multiply(BigInteger.valueOf(10));
                        tempstr = tempstr.add(BigInteger.valueOf(ch - '0'));
                        flag2 = 1; }
                }
            }
            if (flag2 == 0) { tempstr = BigInteger.valueOf(1); }
            tempstr = tempstr.multiply(BigInteger.valueOf(symbol2));
            temppoly.addtocoeff(tempstr,tempcoeff);
        }
        return temppoly;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String test = in.nextLine();
        test = test.replaceAll("\\s","");
        test = test.replaceAll("\\+\\+","+");
        test = test.replaceAll("-\\+","-");
        test = test.replaceAll("\\+-","-");
        test = test.replaceAll("--","+");
        MainClass mm = new MainClass();
        PolyClass poly = mm.handlein(test);
        poly.derivfunc();
        poly.printderiv1();
        poly.printderiv2();
    }
}
