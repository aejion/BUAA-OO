public class CreateFac {
    public boolean isdigit(char ch) { return ch >= '0' && ch <= '9'; }

    public Factor create(String str) {
        if (str.charAt(0) == 'x') { return new Power(str); }
        else if (str.charAt(0) == 's') { return new Sin(str); }
        else if (str.charAt(0) == 'c') { return new Cos(str); }
        else if (str.charAt(0) == '(') { return new Exp(str); }
        return new Constant(str);
    }
}
