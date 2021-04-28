import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
    public static void main(final String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<Term> poly = new ArrayList<Term>();
        while (kb.hasNextInt()) {
            int co = kb.nextInt();
            int in = kb.nextInt();
            poly.add(new Term(co,in));
        }

        Collections.sort(poly);
        for (Term t : poly) {
            System.out.println(t.toString());
        }
    }
}