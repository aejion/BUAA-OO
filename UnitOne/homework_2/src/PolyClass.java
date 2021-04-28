import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class PolyClass {
    private HashMap<String,BigInteger> coeff = new HashMap<>();
    private HashMap<String,BigInteger> deriv = new HashMap<>();
    private ArrayList<SetofTerm> allset = new ArrayList<>();
    private int nowlen = 0;

    void addtocoeff(BigInteger aa, BigInteger bb, BigInteger cc, BigInteger temp) {
        BigInteger pcoe = BigInteger.valueOf(0);
        String str = "powerx " + aa.toString();
        str = str + "powersin " + bb.toString();
        str = str + "powercos " + cc.toString();
        if (coeff.containsKey(str)) {
            pcoe = coeff.get(str);
        }
        coeff.put(str, temp.add(pcoe));
    }

    void derivfunc(int aa, BigInteger bb, BigInteger cc, BigInteger temp) {
        String str = "powerx " + (aa - 1);
        str = str + "powersin " + bb;
        str = str + "powercos " + cc;
        BigInteger temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        BigInteger temp2 = temp.multiply(BigInteger.valueOf(aa));
        deriv.put(str,temp1.add(temp2));
        str = "powerx " + aa;
        str = str + "powersin " + (bb.add(BigInteger.valueOf(-1)));
        str = str + "powercos " + (cc.add(BigInteger.valueOf(1)));
        temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        temp2 = temp.multiply(bb);
        deriv.put(str,temp1.add(temp2));
        str = "powerx " + (aa);
        str = str + "powersin " + (bb.add(BigInteger.valueOf(1)));
        str = str + "powercos " + (cc.add(BigInteger.valueOf(-1)));
        temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        temp2 = temp.multiply(cc);
        temp2 = temp2.multiply(BigInteger.valueOf(-1));
        deriv.put(str,temp1.add(temp2));
    }

    void getderivfunc() {
        String key;
        BigInteger value;
        Iterator it = coeff.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int type = 0;
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            BigInteger symbol = BigInteger.valueOf(1);
            for (int i = 0; i < key.length(); i++) {
                char ch = key.charAt(i);
                if (ch == 'p') {
                    type++;
                    if (type == 2) { aa = aa * symbol.intValue(); }
                    else if (type == 3) { bb = bb.multiply(symbol); }
                    symbol = BigInteger.valueOf(1);
                }
                else if (ch == '-') { symbol = BigInteger.valueOf(-1); }
                else if (ch >= '0' && ch <= '9') {
                    if (type == 1) { aa = aa * 10 + ch - '0'; }
                    else if (type == 2) {
                        bb = bb.multiply(BigInteger.valueOf(10));
                        bb = bb.add(BigInteger.valueOf(ch - '0'));
                    }
                    else if (type == 3) {
                        cc = cc.multiply(BigInteger.valueOf(10));
                        cc = cc.add(BigInteger.valueOf(ch - '0'));
                    }
                }
            }
            cc = cc.multiply(symbol);
            derivfunc(aa, bb, cc, value);
        }
    }

    void handleterm() {
        String  key;
        BigInteger value;
        Iterator it = deriv.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int type = 0;
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            BigInteger symbol = BigInteger.valueOf(1);
            for (int i = 0; i < key.length(); i++) {
                char ch = key.charAt(i);
                if (ch == 'p') {
                    type++;
                    if (type == 2) { aa = aa * symbol.intValue(); }
                    else if (type == 3) { bb = bb.multiply(symbol); }
                    symbol = BigInteger.valueOf(1);
                }
                else if (ch == '-') { symbol = BigInteger.valueOf(-1); }
                else if (ch >= '0' && ch <= '9') {
                    if (type == 1) { aa = aa * 10 + ch - '0'; }
                    else if (type == 2) {
                        bb = bb.multiply(BigInteger.valueOf(10));
                        bb = bb.add(BigInteger.valueOf(ch - '0'));
                    }
                    else if (type == 3) {
                        cc = cc.multiply(BigInteger.valueOf(10));
                        cc = cc.add(BigInteger.valueOf(ch - '0'));
                    }
                }
            }
            cc = cc.multiply(symbol);
            if (value.compareTo(BigInteger.valueOf(0)) == 0) { continue; }
            allset.add(new SetofTerm(value, aa, bb, cc));
        }
        nowlen = getLenofset(allset);
    }

    int getLenofset(ArrayList<SetofTerm> temp) {
        int len = 0;
        int haspos = 0;
        int sizeofarr = temp.size();
        for (SetofTerm set1 : temp) {
            if (set1.isPos() > 0) {
                haspos++;
            }
            len += set1.lenOf();
        }
        len += (sizeofarr - 1);
        if (haspos == 0) { len++; }
        return len;
    }

    void findOptimal() {
        int sizeofarr = allset.size();
        if (sizeofarr == 0) { return; }
        Random random = new Random();
        String str = "";
        HashMap<String,BigInteger> newderiv = new HashMap<>();
        for (int i = 0; i < sizeofarr; i++) {
            int num = random.nextInt(2);
            if (num == 0) { str = str + 0; }
            else if (num == 1) {
                str = str + 1;
            }
        }
        for (int i = 0; i < sizeofarr; i++) {
            char ch = str.charAt(i);
            if (ch == '1') {
                SetofTerm tempset = allset.get(i);
                ArrayList<SetofTerm> tmparr = tempset.getnewarr();
                for (int j = 0; j < tmparr.size(); j++) {
                    SetofTerm nowsett = tmparr.get(j);
                    String str1 = "powerx " + nowsett.getAa();
                    str1 = str1 + "powersin " + (nowsett.getBb());
                    str1 = str1 + "powercos " + (nowsett.getCc());
                    BigInteger temp1 = BigInteger.valueOf(0);
                    if (newderiv.containsKey(str1)) { temp1 = newderiv.get(str1); }
                    temp1 = temp1.add(nowsett.getTerm());
                    newderiv.put(str1,temp1);
                }
            }
            else {
                SetofTerm tempset = allset.get(i);
                String str1 = "powerx " + tempset.getAa();
                str1 = str1 + "powersin " + (tempset.getBb());
                str1 = str1 + "powercos " + (tempset.getCc());
                BigInteger temp1 = BigInteger.valueOf(0);
                if (newderiv.containsKey(str1)) { temp1 = newderiv.get(str1); }
                temp1 = temp1.add(tempset.getTerm());
                newderiv.put(str1,temp1);
            }
        }
        ArrayList<SetofTerm> newarr1 = new ArrayList<>();
        newarr1 = changehash(newderiv);
        int newlen = getLenofset(newarr1);
        if (newlen < nowlen) {
            nowlen = newlen;
            allset = newarr1;
        }
    }

    private ArrayList<SetofTerm> changehash(HashMap<String, BigInteger> newderiv1) {
        ArrayList<SetofTerm> temp = new ArrayList<>();
        String key;
        BigInteger value;
        Iterator it = newderiv1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int type = 0;
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            BigInteger symbol = BigInteger.valueOf(1);
            for (int i = 0; i < key.length(); i++) {
                char ch = key.charAt(i);
                if (ch == 'p') {
                    type++;
                    if (type == 2) { aa = aa * symbol.intValue(); }
                    else if (type == 3) { bb = bb.multiply(symbol); }
                    symbol = BigInteger.valueOf(1);
                }
                else if (ch == '-') { symbol = BigInteger.valueOf(-1); }
                else if (ch >= '0' && ch <= '9') {
                    if (type == 1) { aa = aa * 10 + ch - '0'; }
                    else if (type == 2) {
                        bb = bb.multiply(BigInteger.valueOf(10));
                        bb = bb.add(BigInteger.valueOf(ch - '0'));
                    }
                    else if (type == 3) {
                        cc = cc.multiply(BigInteger.valueOf(10));
                        cc = cc.add(BigInteger.valueOf(ch - '0'));
                    }
                }
            }
            cc = cc.multiply(symbol);
            if (value.compareTo(BigInteger.valueOf(0)) == 0) { continue; }
            temp.add(new SetofTerm(value, aa, bb, cc));
        }
        return temp;
    }

    void printout() {
        int sizeof1 = allset.size();
        if (sizeof1 == 0) {
            System.out.println(0);
            return;
        }
        for (int i = 0; i < sizeof1; i++) {
            SetofTerm nowset = allset.get(i);
            if (nowset.getTerm().compareTo(BigInteger.valueOf(0)) > 0) {
                nowset.printstring1();
                nowset.setTerm(BigInteger.valueOf(0));
                break;
            }
        }
        for (int i = 0; i < sizeof1; i++) {
            SetofTerm nowset = allset.get(i);
            nowset.printstring2();
        }
    }
}
