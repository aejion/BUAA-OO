import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String ans = new Polyfactor(s).derivation();
        ans = ans.replaceAll("\\[", "(");
        ans = ans.replaceAll("\\]", ")");
        ans = ans.replaceAll("^\\(", "");
        ans = ans.replaceAll("\\)$", "");
        ans = ans.replaceAll("[ \\t]", "");
        ans = ans.replaceAll("(-\\+)|(\\+-)", "-");
        ans = ans.replaceAll("(--)|(\\+\\+)", "+");
        System.out.println(ans);
    }
}
