import java.util.Scanner;
import main.Expression;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            input = bracketLayer(input);
            Expression expression = new Expression(input);
            expression.validCheck();
            expression.parse();
            expression.diff();
            expression.standardize();
            System.out.println(expression.print(1));
        } catch (RuntimeException e) {
            System.out.println("WRONG FORMAT!");
        }

    }

    private static String bracketLayer(String raw) {
        int depth = 0;
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '(') {
                out.append("({");
                for (int j = 0; j < depth; j++) {
                    out.append("@");
                }
                out.append("}");
                depth++;
            } else if (c == ')') {
                depth--;
                out.append("){");
                for (int j = 0; j < depth; j++) {
                    out.append("@");
                }
                out.append("}");
            } else {
                out.append(c);
            }
        }
        if (depth != 0) {
            throw new RuntimeException();
        }
        return out.toString();
    }
}
