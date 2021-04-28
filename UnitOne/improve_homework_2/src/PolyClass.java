import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyClass {
    private HashMap<String,BigInteger> coeff = new HashMap<>();
    private HashMap<String,BigInteger> deriv = new HashMap<>();
    private ArrayList<SetofTerm> allset = new ArrayList<>();
    private int nowlen = 0;

    String createstr(int aa, BigInteger bb, BigInteger cc) {
        String str = "powerx " + aa;
        str = str + "powersin " + bb;
        str = str + "powercos " + cc;
        return str;
    }

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
        String str = createstr(aa - 1,bb,cc);
        BigInteger temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        BigInteger temp2 = temp.multiply(BigInteger.valueOf(aa));
        deriv.put(str,temp1.add(temp2));
        str = createstr(aa,bb.add(BigInteger.valueOf(-1)),cc.add(BigInteger.valueOf(1)));
        temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        temp2 = temp.multiply(bb);
        deriv.put(str,temp1.add(temp2));
        str = createstr(aa,bb.add(BigInteger.valueOf(1)),cc.add(BigInteger.valueOf(-1)));
        temp1 = BigInteger.valueOf(0);
        if (deriv.containsKey(str)) { temp1 = deriv.get(str); }
        temp2 = temp.multiply(cc);
        temp2 = temp2.multiply(BigInteger.valueOf(-1));
        deriv.put(str,temp1.add(temp2));
    }

    void getderivfunc() {
        String key;
        BigInteger value;
        for (Map.Entry<String, BigInteger> stringBigIntegerEntry : coeff.entrySet()) {
            Map.Entry entry = (Map.Entry) stringBigIntegerEntry;
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            Pattern pat = Pattern.compile("power(x|sin|cos) ([+-]?[0-9]+)");
            Matcher mat = pat.matcher(key);
            int type = 0;
            while (mat.find()) {
                String str = mat.group(2);
                if (type == 0) {
                    aa = Integer.parseInt(str);
                } else if (type == 1) {
                    bb = new BigInteger(str);
                } else if (type == 2) {
                    cc = new BigInteger(str);
                }
                type++;
            }
            derivfunc(aa, bb, cc, value);
        }
    }

    void handleterm() {
        String  key;
        BigInteger value;
        for (Map.Entry<String, BigInteger> stringBigIntegerEntry : deriv.entrySet()) {
            Map.Entry entry = (Map.Entry) stringBigIntegerEntry;
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            Pattern pat = Pattern.compile("power(x|sin|cos) ([+-]?[0-9]+)");
            Matcher mat = pat.matcher(key);
            int type = 0;
            while (mat.find()) {
                String str = mat.group(2);
                if (type == 0) {
                    aa = Integer.parseInt(str);
                } else if (type == 1) {
                    bb = new BigInteger(str);
                } else if (type == 2) {
                    cc = new BigInteger(str);
                }
                type++;
            }
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
            if (set1.isPos() > 0) { haspos++; }
            len += set1.lenOf();
        }
        len += (sizeofarr - 1);
        if (haspos == 0) { len++; }
        return len;
    }

    void findOptimal() {
        ArrayList<SetofTerm> copyset = new ArrayList<>();

        //对当前的所有项进行copy
        copyset = (ArrayList<SetofTerm>) allset.clone();

        //z参数表示对当前的所有项进行多少次操作（这里的操作指的是拆的操作）
        for (int z = 0; z < 10; z++) {

            int sizeofarr = copyset.size();
            if (sizeofarr == 0) { return; }
            Random random = new Random();

            //str数组中1的位置代表拆，0的位置代表不拆，随机生成
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < sizeofarr; i++) {
                int num = random.nextInt(2);
                if (num == 0) { str.append(0); }
                else { str.append(1); }
            }

            //newderiv代表当前的所有项（三元组）
            HashMap<String,BigInteger> newderiv = new HashMap<>();

            for (int i = 0; i < sizeofarr; i++) {
                char ch = str.charAt(i);

                //当前的一项
                SetofTerm tempset = copyset.get(i);

                //拆
                if (ch == '1') {
                    ArrayList<SetofTerm> tmparr = tempset.getnewarr();
                    for (SetofTerm nowsett : tmparr) {
                        String str1 = createstr(nowsett.getAa(), nowsett.getBb(), nowsett.getCc());
                        BigInteger temp1 = BigInteger.valueOf(0);
                        if (newderiv.containsKey(str1)) { temp1 = newderiv.get(str1); }
                        temp1 = temp1.add(nowsett.getTerm());
                        newderiv.put(str1, temp1);
                    }
                }

                //不拆
                else {
                    String str1 = createstr(tempset.getAa(),tempset.getBb(),tempset.getCc());
                    BigInteger temp1 = BigInteger.valueOf(0);
                    if (newderiv.containsKey(str1)) { temp1 = newderiv.get(str1); }
                    temp1 = temp1.add(tempset.getTerm());
                    newderiv.put(str1,temp1);
                }
            }

            ArrayList<SetofTerm> newarr1;
            newarr1 = changehash(newderiv);
            int newlen = getLenofset(newarr1);
            if (newlen < nowlen) {
                nowlen = newlen;
                allset = newarr1;
                break;
            }
            copyset = (ArrayList<SetofTerm>)newarr1.clone();
        }
    }

    private ArrayList<SetofTerm> changehash(HashMap<String, BigInteger> newderiv1) {
        ArrayList<SetofTerm> temp = new ArrayList<>();
        String key;
        BigInteger value;
        for (Map.Entry<String, BigInteger> stringBigIntegerEntry : newderiv1.entrySet()) {
            Map.Entry entry = (Map.Entry) stringBigIntegerEntry;
            key = (String) entry.getKey();
            value = (BigInteger) entry.getValue();
            int aa = 0;
            BigInteger bb = BigInteger.valueOf(0);
            BigInteger cc = BigInteger.valueOf(0);
            Pattern pat = Pattern.compile("power(x|sin|cos) ([+-]?[0-9]+)");
            Matcher mat = pat.matcher(key);
            int type = 0;
            while (mat.find()) {
                String str = mat.group(2);
                if (type == 0) {
                    aa = Integer.parseInt(str);
                } else if (type == 1) {
                    bb = new BigInteger(str);
                } else if (type == 2) {
                    cc = new BigInteger(str);
                }
                type++;
            }
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
        for (SetofTerm nowset : allset) {
            if (nowset.getTerm().compareTo(BigInteger.valueOf(0)) > 0) {
                nowset.printstring1();
                nowset.setTerm(BigInteger.valueOf(0));
                break;
            }
        }
        for (SetofTerm nowset : allset) {
            nowset.printstring2();
        }
    }
}
