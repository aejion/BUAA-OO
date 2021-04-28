public class Term implements Comparable<Term> {
    private int coef;
    private int index;

    public Term(int c,int i) {
        this.coef = c;
        this.index = i;
    }

    @Override
    public String toString() {
        String ret;
        if (coef == 0) {
            ret = "0";
        }
        else {
            if (index == 0) {
                ret = String.valueOf(coef);
            }
            else if (coef == 1 && index != 1) {
                ret = "x^" + String.valueOf(index);
            }
            else if (coef == 1 && index == 1) {
                ret = "x";
            }
            else if (coef == -1 && index != 1) {
                ret = "-x^" + String.valueOf(index);
            }
            else if (coef == -1 && index == 1) {
                ret = "-x";
            }
            else if (index == 1) {
                ret = String.valueOf(coef) + "*x";
            }
            else {
                ret = String.valueOf(coef) + "*x^" + String.valueOf(index);
            }
        }
        return ret;
    }

    @Override
    public int compareTo(Term term) {
        if (index == term.index) {
            if (coef < term.coef) { return -1; }
            else { return 1; }
        }
        if (index < term.index) { return 1; }
        else { return -1; }
    }
}