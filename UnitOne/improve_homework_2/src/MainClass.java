import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Expression exp = new Expression();
        String test = in.nextLine();
        test = test.replaceAll("[ \\t]*","");
        boolean define = exp.correctin(test);
        test = exp.changeExp(test);
        PolyClass poly = exp.handlein(test);
        if (!exp.getcasex() || !define) { System.out.println("WRONG FORMAT!"); }
        else {
            poly.getderivfunc();
            poly.handleterm();

            //这一层循环是指的对当前的所有项进行拆分
            for (int i = 0; i < 1500; i++) {
                poly.findOptimal();
            }

            poly.printout();
        }
        in.close();
    }
}