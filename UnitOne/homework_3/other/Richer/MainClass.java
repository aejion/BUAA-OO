import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws CloneNotSupportedException {
        Scanner scan = new Scanner(System.in);
        Optimizer optimizer = new Optimizer();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            ExpressionAnalyst expressionAnalyst = new ExpressionAnalyst();
            Expression myExpression = expressionAnalyst.analyse(line);
            //System.out.println("分析完成");
            myExpression = optimizer.optimize(myExpression);
            Expression resultExpression = myExpression.derivative();
            //###
            //System.out.println("优化前结果："+resultExpression);
            resultExpression = optimizer.optimize(resultExpression);
            String result = resultExpression.toString();

            System.out.println(result);
        }
    }
}
