import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionAnalyst {

    private final String space = "([ \\t]*)";
    private final String signedint = "([+\\-]?\\d+)";
    private final String index = "(" + "(\\*\\*|\\^)" + space + signedint + ")";

    private final String sincos = "((sin|cos)" + space + "\\(" +
            space + "x" + space + "\\)" + "(" + space + index + ")?)";

    private final String power = "(x" + "(" + space + index + ")?)";
    private final String constant = signedint;
    private final String factor = "(" + sincos + "|" + power + "|" + constant + ")";

    private final String term = "(((?<termSign>[+\\-])" + space + ")?" +
            "(?<unsignedTerm>" + factor + "(" + space + "\\*" + space + factor + ")*))";
    private final String expression = space + "([+\\-]" + space +
            ")?" + term + space + "([+\\-]" + space + term + space + ")*";

    private final String catchSign = "^" + space + "(?<signOne>[+\\-])"
            + "(" + space + "(?<signTwo>[+\\-]))?" + space;

    private final String catchInt = "(?<index>[+\\-]?\\d+)";
    private final String catchIndex = "(" + "(\\*\\*)" + space + catchInt + ")";

    private final String catchStarConstant = "^" + space + "\\*" +
            space + "(?<constant>[+\\-]?\\d+)" + space;

    private final String catchStarPower = "^" + space + "\\*" + space +
            "(x" + "(" + space + catchIndex + ")?)" + space;

    private final String catchStarSin = "^" + space + "\\*" + space + "(sin" + space + "\\["
            + "(?<expression>(.)*?)" + "\\]" + "(" + space + catchIndex + ")?)" + space;
    private final String catchStarCos = "^" + space + "\\*" + space + "(cos" + space + "\\["
            + "(?<expression>(.)*?)" + "\\]" + "(" + space + catchIndex + ")?)" + space;

    private final String catchStarExpression = "^" + space + "\\*" + space +
            "\\[" + "(?<expression>(.)*?)" + "\\]" + space;

    private final String catchConstant = "^" + space + "(?<constant>[+\\-]?\\d+)" + space;
    private final String catchPower = "^" + space + "(x" + "(" + space + catchIndex + ")?)" + space;
    private final String catchSin = "^" + space + "(sin" + space +
            "\\(" + "(?<expression>(.)*?)" + "\\)" + "(" + space + catchIndex + ")?)" + space;
    private final String catchCos = "^" + space + "(cos" + space +
            "\\(" + "(?<expression>(.)*?)" + "\\)" + "(" + space + catchIndex + ")?)" + space;

    private final String catchExpression = "^" + space +
            "\\[" + "(?<expression>(.)*?)" + "\\]" + space;

    private final Pattern rrStarConstant = Pattern.compile(catchStarConstant);
    private final Pattern rrStarPower = Pattern.compile(catchStarPower);
    private final Pattern rrStarSin = Pattern.compile(catchStarSin);
    private final Pattern rrStarCos = Pattern.compile(catchStarCos);
    private final Pattern rrStarExpression = Pattern.compile(catchStarExpression);

    private final Pattern rrConstant = Pattern.compile(catchConstant);
    private final Pattern rrPower = Pattern.compile(catchPower);
    private final Pattern rrSin = Pattern.compile(catchSin);
    private final Pattern rrCos = Pattern.compile(catchCos);
    private final Pattern rrExpression = Pattern.compile(catchExpression);

    private final Pattern r1 = Pattern.compile(catchSign);

    private int illegalSign = 0;

    private String preprocess(String line) {
        if (line.contains("[") || line.contains("]")) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        int stack = 0;
        int i = 0;
        String modifiedString = "";
        for (i = 0; i < line.length(); i++) {
            if (line.substring(i, i + 1).equals("(")) {
                if (stack == 0) {
                    modifiedString += "[";
                } else {
                    modifiedString += "(";
                }
                stack += 1;
            } else if (line.substring(i, i + 1).equals(")")) {
                if (stack == 1) {
                    modifiedString += "]";
                } else {
                    modifiedString += ")";
                }
                stack -= 1;
            } else {
                modifiedString += line.substring(i, i + 1);
            }
        }

        Pattern r0 = Pattern.compile("^" + space + "[+\\-]");
        Matcher m0 = r0.matcher(modifiedString);
        if (!m0.find()) {
            modifiedString = "+" + modifiedString;
            //###
            //System.out.println("预处理添加+！");
        }

        return modifiedString;
    }

    private String nestProcess(String line) {
        Matcher mmConstant = rrConstant.matcher(line);
        Matcher mmPower = rrPower.matcher(line);
        Matcher mmSin = rrSin.matcher(line);
        Matcher mmCos = rrCos.matcher(line);
        Matcher mmExpression = rrExpression.matcher(line);

        Pattern rrLeft = Pattern.compile("^" + space + "\\(");
        Pattern rrRight = Pattern.compile("\\)" + space + "$");
        Matcher mmLeft = rrLeft.matcher(line);
        Matcher mmRight = rrRight.matcher(line);
        if (mmConstant.matches() || mmPower.matches() || mmSin.matches()
                || mmCos.matches() || mmExpression.matches()) {
            return line;
        } else if (mmLeft.find() && mmRight.find()) {
            return line.substring(mmLeft.end(), mmRight.start());
        } else {
            //###
            //System.out.print("嵌套处理：");
            System.out.println("WRONG FORMAT!");
            System.exit(0);
            return line;
        }
    }

    public Expression analyse(String line1) throws CloneNotSupportedException {

        Expression resultExpression = new Expression();

        String line = preprocess(line1);

        while (line.length() != 0) {
            Term resultTerm = new Term();
            Term tempTerm = new Term();
            tempTerm = tempTerm.multiply(new ConstFactor(BigInteger.ONE));
            Expression tempExpression = new Expression();
            tempExpression = tempExpression.add(tempTerm);

            Medium medium = analyseSign(line);
            line = medium.getLine();
            int termSign = medium.getTermSign();

            int catchFlag = 0;
            while (true) {

                Matcher mmStarConstant = rrStarConstant.matcher(line);
                Matcher mmStarPower = rrStarPower.matcher(line);
                Matcher mmStarSin = rrStarSin.matcher(line);
                Matcher mmStarCos = rrStarCos.matcher(line);
                Matcher mmStarExpression = rrStarExpression.matcher(line);
                if (mmStarSin.find()) {
                    catchFlag = 1;
                    BigInteger a = createIndex(mmStarSin.group("index"));
                    Expression nestedExpression =
                                analyse(nestProcess(mmStarSin.group("expression")));
                    Factor resultFactor = new SinFactor(a, nestedExpression);
                    resultTerm = resultTerm.multiply(resultFactor);
                    line = line.substring(mmStarSin.end(), line.length());
                } else if (mmStarCos.find()) {
                    catchFlag = 1;
                    BigInteger a = createIndex(mmStarCos.group("index"));

                    Expression nestedExpression =
                                analyse(nestProcess(mmStarCos.group("expression")));
                    Factor resultFactor = new CosFactor(a, nestedExpression);
                    resultTerm = resultTerm.multiply(resultFactor);
                    line = line.substring(mmStarCos.end(), line.length());
                } else if (mmStarPower.find()) {
                    catchFlag = 1;
                    BigInteger a = createIndex(mmStarPower.group("index"));

                    Factor resultFactor = new PowerFactor(a);
                    resultTerm = resultTerm.multiply(resultFactor);
                    line = line.substring(mmStarPower.end(), line.length());
                } else if (mmStarConstant.find()) {
                    catchFlag = 1;
                    BigInteger a = new BigInteger(mmStarConstant.group("constant"));
                    Factor resultFactor = new ConstFactor(a);
                    resultTerm = resultTerm.multiply(resultFactor);
                    line = line.substring(mmStarConstant.end(), line.length());
                } else if (mmStarExpression.find()) {
                    catchFlag = 1;
                    Expression nestedExpression = analyse(nestProcess("("
                                + mmStarExpression.group("expression") + ")"));
                    tempExpression = tempExpression.multiply(nestedExpression);
                    line = line.substring(mmStarExpression.end(), line.length());
                }  else { break; } }

            if (catchFlag == 0) { wrongFormat(); }
            if (termSign == -1) {
                resultTerm = resultTerm.multiply(new ConstFactor(BigInteger.ONE.negate())); }
            resultExpression = resultExpression.add(resultTerm.multiply(tempExpression)); }
        return resultExpression; }

    private void isGreaterFifty(BigInteger a) {
        if (a.abs().compareTo(new BigInteger("50")) > 0) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
    }

    private void wrongFormat() {
        System.out.println("WRONG FORMAT!");
        System.exit(0);
    }

    private Medium analyseSign(String line) {
        Matcher m1 = r1.matcher(line);
        Medium medium = new Medium();
        medium.setTermSign(1);
        if (m1.find()) {
            //确定符号!!
            if (m1.group("signOne") == null) {
                wrongFormat();
            }
            if (m1.group("signTwo") == null) {
                if (m1.group("signOne").equals("-")) {
                    medium.setTermSign(-1);
                }
            } else if ((m1.group("signOne").equals("+") && m1.group("signTwo").equals("-"))
                    || (m1.group("signOne").equals("-") && m1.group("signTwo").equals("+"))) {
                medium.setTermSign(-1);
            }
            medium.setLine("*" + line.substring(m1.end(),line.length()));
        } else {
            wrongFormat();
        }
        return medium;
    }

    private BigInteger createIndex(String line) {
        BigInteger a;
        if (line == null) {
            a = BigInteger.ONE; } else {
            a = new BigInteger(line);
            isGreaterFifty(a); }
        return a;
    }
}
