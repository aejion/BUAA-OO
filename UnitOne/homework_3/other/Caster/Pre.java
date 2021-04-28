public class Pre {
    public String process(String tmp) {
        String str = tmp;
        int branket = 0;
        for (int i = 0; i < str.length(); i++) {
            if (branket == 0) {
                if (str.charAt(i) == '(') {
                    str = changeChar(str, i, true);
                    branket++;
                } else if (str.charAt(i) == ')') {
                    return null;
                }
            } else if (branket == 1 && str.charAt(i) == ')') {
                str = changeChar(str, i, false);
                branket--;
            } else if (str.charAt(i) == '(') {
                branket++;
            } else if (str.charAt(i) == ')') {
                branket--;
            }
        }
        if (branket > 0) {
            return null;
        }
        return str;
    }
    
    public String changeChar(String tmp, int i, boolean tag) {
        String str = tmp;
        int max = tmp.length() - 1;
        String change;
        if (tag) {
            change = "[";
        } else {
            change = "]";
        }
        if (i == 0) {
            return change + tmp.substring(1);
        } else if (i == max) {
            return tmp.substring(0, max) + change;
        }
        String head = str.substring(0, i);
        String tail = str.substring(i + 1);
        return head + change + tail;
    }
}
