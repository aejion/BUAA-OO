import java.util.ArrayList;

public class Coeffactor implements Factor {
    private ArrayList<Factor> arrayList = new ArrayList<>();
    private String string;
    private String tools;

    public Coeffactor(String s) {
        this.string = s;
    }

    public String derivation() {
        return "0";
    }

    @Override
    public String toString() {
        return string.replace("[ \\t]", "");
    }
}
