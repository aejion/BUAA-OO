import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class SetofTerm {
    private BigInteger term;
    private int aa;
    private BigInteger bb;
    private BigInteger cc;
    private int len;
    private BigInteger const1 = BigInteger.valueOf(1);
    private BigInteger const0 = BigInteger.valueOf(0);

    SetofTerm(BigInteger t, int a, BigInteger b, BigInteger c) {
        term = t;
        aa = a;
        bb = b;
        cc = c;
    }

    public int getAa() {
        return aa;
    }

    public BigInteger getBb() {
        return bb;
    }

    public BigInteger getCc() {
        return cc;
    }

    public BigInteger getTerm() {
        return term;
    }

    public void setTerm(BigInteger value) {
        term = value;
    }

    public int isPos() {
        return term.compareTo(BigInteger.valueOf(0));
    }

    public void lenOf2() {
        if (aa != 0) {
            if (len > 0) { len++; }
            if (aa == 1) { len += 1; }
            else if (aa == 2) { len += 3; }
            else { len = len + 3 + String.valueOf(aa).length(); }
        }
        if (bb.compareTo(BigInteger.valueOf(0)) != 0) {
            if (len > 0) { len++; }
            if (bb.compareTo(const1) == 0) { len += 6; }
            else { len = len + 8 + String.valueOf(bb).length(); }
        }
        if (cc.compareTo(BigInteger.valueOf(0)) != 0) {
            if (len > 0) { len++; }
            if (cc.compareTo(const1) == 0) { len += 6; }
            else { len = len + 8 + String.valueOf(cc).length(); }
        }
    }

    public int lenOf() {
        if (term.compareTo(BigInteger.valueOf(1)) == 0) {
            if (aa == 0 && bb.equals(const0) && cc.equals(const0)) { len = 1; }
            else { lenOf2(); }
        }
        else if (term.compareTo(BigInteger.valueOf(-1)) == 0) {
            if (aa == 0 && bb.equals(const0) && cc.equals(const0)) { len = 1; }
            else { lenOf2(); }
        }
        else {
            len = String.valueOf(term).length();
            if (term.compareTo(BigInteger.valueOf(0)) < 0) { len--; }
            lenOf2();
        }
        return len;
    }

    ArrayList<SetofTerm> getnewarr() {
        ArrayList<SetofTerm> tem = new ArrayList<>();
        Random random = new Random();
        int now = random.nextInt(2);
        if (now == 0) {
            if (bb.compareTo(BigInteger.valueOf(2)) >= 0) {
                BigInteger bbnow = bb.add(BigInteger.valueOf(-2));
                BigInteger ccnow = cc.add(BigInteger.valueOf(2));
                SetofTerm tempset1 = new SetofTerm(term,aa,bbnow,cc);
                BigInteger termop = term.multiply(BigInteger.valueOf(-1));
                SetofTerm tempset2 = new SetofTerm(termop,aa,bbnow,ccnow);
                tem.add(tempset1);
                tem.add(tempset2);
            }
            else if (cc.compareTo(BigInteger.valueOf(2)) >= 0) {
                BigInteger ccnow = cc.add(BigInteger.valueOf(-2));
                BigInteger bbnow = bb.add(BigInteger.valueOf(2));
                SetofTerm tempset1 = new SetofTerm(term,aa,bb,ccnow);
                BigInteger termop = term.multiply(BigInteger.valueOf(-1));
                SetofTerm tempset2 = new SetofTerm(termop,aa,bbnow,ccnow);
                tem.add(tempset1);
                tem.add(tempset2);
            }
            else {
                SetofTerm tempset1 = new SetofTerm(term,aa,bb,cc);
                tem.add(tempset1);
            }
        }
        else if (now == 1) {
            if (cc.compareTo(BigInteger.valueOf(2)) >= 0) {
                BigInteger ccnow = cc.add(BigInteger.valueOf(-2));
                BigInteger bbnow = bb.add(BigInteger.valueOf(2));
                SetofTerm tempset1 = new SetofTerm(term,aa,bb,ccnow);
                BigInteger termop = term.multiply(BigInteger.valueOf(-1));
                SetofTerm tempset2 = new SetofTerm(termop,aa,bbnow,ccnow);
                tem.add(tempset1);
                tem.add(tempset2);
            }
            else if (bb.compareTo(BigInteger.valueOf(2)) >= 0) {
                BigInteger bbnow = bb.add(BigInteger.valueOf(-2));
                BigInteger ccnow = cc.add(BigInteger.valueOf(2));
                SetofTerm tempset1 = new SetofTerm(term,aa,bbnow,cc);
                BigInteger termop = term.multiply(BigInteger.valueOf(-1));
                SetofTerm tempset2 = new SetofTerm(termop,aa,bbnow,ccnow);
                tem.add(tempset1);
                tem.add(tempset2);
            }
            else {
                SetofTerm tempset1 = new SetofTerm(term,aa,bb,cc);
                tem.add(tempset1);
            }
        }
        return tem;
    }

    public void printstring1() {
        if (term.compareTo(BigInteger.valueOf(0)) == 0) { return; }
        int hascode = 0;
        if (term.compareTo(BigInteger.valueOf(1)) == 0) {
            if (aa == 0 && bb.equals(const0) && cc.equals(const0)) { System.out.print(1); }
            if (aa != 0) {
                if (aa == 1) { System.out.print("x"); }
                else if (aa == 2) { System.out.print("x*x"); }
                else { System.out.print("x**" + aa); }
                hascode = 1;
            }
            if (bb.compareTo(const0) != 0) {
                if (hascode == 1) { System.out.print("*"); }
                if (bb.equals(const1)) { System.out.print("sin(x)"); }
                else { System.out.print("sin(x)**" + bb); }
                hascode = 1;
            }
            if (cc.compareTo(const0) != 0) {
                if (hascode == 1) { System.out.print("*"); }
                if (cc.equals(const1)) { System.out.print("cos(x)"); }
                else { System.out.print("cos(x)**" + cc); }
            }
        }
        else {
            System.out.print(term);
            if (aa != 0) {
                if (aa == 1) { System.out.print("*x"); }
                else if (aa == 2) { System.out.print("*x*x"); }
                else { System.out.print("*x**" + aa); }
            }
            if (bb.compareTo(const0) != 0) {
                if (bb.equals(const1)) { System.out.print("*sin(x)"); }
                else { System.out.print("*sin(x)**" + bb); }
            }
            if (cc.compareTo(const0) != 0) {
                if (cc.equals(const1)) { System.out.print("*cos(x)"); }
                else { System.out.print("*cos(x)**" + cc); }
            }
        }
    }

    public void printstring2() {
        if (term.compareTo(BigInteger.valueOf(0)) == 0) { return; }
        int hascode = 0;
        if (term.compareTo(BigInteger.valueOf(0)) > 0) { System.out.print("+"); }
        if (term.compareTo(BigInteger.valueOf(1)) == 0) {
            if (aa == 0 && bb.equals(const0) && cc.equals(const0)) { System.out.print(1); }
            if (aa != 0) { if (aa == 1) { System.out.print("x"); }
                else if (aa == 2) { System.out.print("x*x"); }
                else { System.out.print("x**" + aa); }
                hascode = 1;
            }
            if (bb.compareTo(const0) != 0) { if (hascode == 1) { System.out.print("*"); }
                if (bb.equals(const1)) { System.out.print("sin(x)"); }
                else { System.out.print("sin(x)**" + bb); }
                hascode = 1;
            }
            if (cc.compareTo(const0) != 0) { if (hascode == 1) { System.out.print("*"); }
                if (cc.equals(const1)) { System.out.print("cos(x)"); }
                else { System.out.print("cos(x)**" + cc); }
            }
        }
        else if (term.compareTo(BigInteger.valueOf(-1)) == 0) {
            System.out.print("-");
            if (aa == 0 && bb.equals(const0) && cc.equals(const0)) { System.out.print(1); }
            if (aa != 0) {
                if (aa == 1) { System.out.print("x"); }
                else if (aa == 2) { System.out.print("x*x"); }
                else { System.out.print("x**" + aa); }
                hascode = 1;
            }
            if (bb.compareTo(const0) != 0) {
                if (hascode == 1) { System.out.print("*"); }
                if (bb.equals(const1)) { System.out.print("sin(x)"); }
                else { System.out.print("sin(x)**" + bb); }
                hascode = 1;
            }
            if (cc.compareTo(const0) != 0) {
                if (hascode == 1) { System.out.print("*"); }
                if (cc.equals(const1)) { System.out.print("cos(x)"); }
                else { System.out.print("cos(x)**" + cc); }
            }
        }
        else {
            System.out.print(term);
            if (aa != 0) { if (aa == 1) { System.out.print("*x"); }
                else if (aa == 2) { System.out.print("*x*x"); }
                else { System.out.print("*x**" + aa); }
            }
            if (bb.compareTo(const0) != 0) {
                if (bb.equals(const1)) { System.out.print("*sin(x)"); }
                else { System.out.print("*sin(x)**" + bb); }
            }
            if (cc.compareTo(const0) != 0) {
                if (cc.equals(const1)) { System.out.print("*cos(x)"); }
                else { System.out.print("*cos(x)**" + cc); }
            }
        }
    }
}