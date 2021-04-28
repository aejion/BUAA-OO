import java.util.Scanner;

public class Input {
    private Scanner sc;

    Input() {
        sc = new Scanner(System.in);
    }

    boolean hasNextOperation() {
        return sc.hasNextLine();
    }

    String getNewOperation() {
        return sc.nextLine();
    }
}