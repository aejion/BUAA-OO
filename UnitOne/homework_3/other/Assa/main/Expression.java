package main;

import factor.Factor;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private ArrayList<Term> primFunc; // primitive function
    private ArrayList<Term> deriFunc; // derivative function
    private String content; // Unparsed expression

    static final String sp = "[ \\t]*"; // space term
    static final String sigint = "[-+]?\\d+"; // Signaled integer
    static final String exp = "\\*\\*" + sp + "(?:" + sigint + ")"; // exponent

    static final String pow = "(?:x(?:" + sp + exp + ")?)"; // power function
    static final String sin = "(?:sin" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String cos = "(?:cos" + sp + "\\(\\{}.*?\\)\\{}(?:" + sp + exp + ")?)";
    static final String expFac = "\\(\\{}.*?\\)\\{}";

    static final String var = "(?:" + pow + "|" + sin + "|" + cos + "|" + expFac + ")"; // variable
    static final String factor = "(?:" + var + "|" + sigint + ")"; // factor

    public Expression(String input) {
        this.primFunc = new ArrayList<>();
        this.deriFunc = new ArrayList<>();
        this.content = input;
    }

    public Expression() {
        this.primFunc = new ArrayList<>();
        this.deriFunc = new ArrayList<>();
        this.content = "DerivationFromTerm";
    }

    public void validCheck() {
        final String term = "(?:(?:[-+]" + sp + ")?" + factor
                + "(?:" + sp + "\\*" + sp + factor + ")*)"; // term
        final String expression = "^(?:" + sp + "(?:(?:[-+])" + sp + ")?(" + term + ")" + sp
                + "(?:(?:[-+])" + sp + term + sp + ")*)$"; // expression
        Pattern expReg = Pattern.compile(expression);
        Matcher m = expReg.matcher(this.content);
        if (!m.matches()) {
            throw new RuntimeException();
        }
    }

    public void parse() { // raw primitive function
        final String term = "(?:(?:[-+]" + sp + ")*" + factor
                + "(?:" + sp + "\\*" + sp + factor + ")*)"; // term
        StringBuilder deleteBlank = new StringBuilder();
        int layer = 0; // out of bracket
        for (int i = 0; i < this.content.length(); i++) {
            char c = this.content.charAt(i);
            if (c == '(') { layer++; }
            else if (c == ')') { layer--; }
            else {
                if (layer == 0 && (c == ' ' || c == '\t')) { continue; }
            }
            deleteBlank.append(c);
        }
        Pattern termReg = Pattern.compile(term);
        Matcher m = termReg.matcher(deleteBlank.toString());
        int mark;
        mark = 0;
        while (m.find()) {
            //System.out.println(m.group());
            //System.out.println("start: " + m.start());
            //System.out.println("end: " + m.end());
            if (m.start() != mark) {
                throw new RuntimeException();
            }
            Term elem = new Term(m.group());
            this.primFunc.add(elem);
            mark = m.end();
        }
    }

    public void diff() {
        for (Term term : this.primFunc) {
            Expression termDiff = term.diff();
            this.deriFunc.addAll(termDiff.getPrimFunc());
        }
    }

    public void standardize() {
        // delete zero terms
        this.deriFunc.removeIf(term -> term.getCoe().equals(BigInteger.ZERO));
        Collections.sort(this.deriFunc); // sort remaining terms
        /*
        int size = this.deriFunc.size();
        for (int i = 0; i < size - 1; i++) { // merge similar terms
            Term curTerm = this.deriFunc.get(i);
            Term nextTerm = this.deriFunc.get(i + 1);
            if (curTerm.similarTo(nextTerm)) {
                curTerm.setCoe(curTerm.getCoe().add(nextTerm.getCoe()));
                this.deriFunc.remove(i + 1);
                size--;
                i--;
            }
        }
        iter = this.deriFunc.iterator();
        while (iter.hasNext()) { // delete zero terms again
            Term term = iter.next();
            if (term.getCoe().equals(BigInteger.ZERO)) {
                iter.remove();
            }
        }*/
        /*
        size = this.deriFunc.size();
        for (int i = 0; i < size; i++) { // merge sin(x)^2 + cos(x)^2
            Term baseTerm = this.deriFunc.get(i);
            for (int j = i; j < size; j++) {
                Term curTerm = this.deriFunc.get(j);
                boolean merge = curTerm.getEksPow().equals(baseTerm.getEksPow());
                boolean large = curTerm.getSinPow().equals(baseTerm.getSinPow().
                        add(BigInteger.valueOf(2)));
                large = large && baseTerm.getCosPow().equals(curTerm.getCosPow().
                        add(BigInteger.valueOf(2)));
                boolean small = baseTerm.getSinPow().equals(curTerm.getSinPow().
                        add(BigInteger.valueOf(2)));
                small = small && curTerm.getCosPow().equals(baseTerm.getCosPow().
                        add(BigInteger.valueOf(2)));
                if (merge && large) {
                    curTerm.setCoe(curTerm.getCoe().add(baseTerm.getCoe().
                            multiply(BigInteger.valueOf(-1))));
                    baseTerm.setCosPow(baseTerm.getCosPow().add(BigInteger.valueOf(-2)));
                } else if (merge && small) {
                    baseTerm.setCoe(baseTerm.getCoe().add(curTerm.getCoe().
                            multiply(BigInteger.valueOf(-1))));
                    curTerm.setCosPow(curTerm.getCosPow().add(BigInteger.valueOf(-2)));
                }
            }
        }*/
    }

    public String print(int primOrDeri) {
        ArrayList<Term> toPrint;
        if (primOrDeri == 0) {
            toPrint = this.primFunc;
        } else if (primOrDeri == 1) {
            toPrint = this.deriFunc;
        } else {
            return "";
        }
        if (toPrint.isEmpty()) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (Term term : toPrint) {
            result.append("&"); // as a split mark between terms
            if (term.getCoe().compareTo(BigInteger.ZERO) > 0) {
                result.append("+");
            }
            result.append(term.getCoe());
            if (term.getEksPow().compareTo(BigInteger.ZERO) != 0) {
                result.append("*x**").append(term.getEksPow());
            }
            for (Factor factor : term.getFactors()) {
                result.append("*");
                result.append(factor.print());
            }
        }
        result.append("\t");
        String out = result.toString();
        out = out.replaceAll("\\*\\*1\\+", "+");
        out = out.replaceAll("\\*\\*1-", "-");
        out = out.replaceAll("\\*\\*1\\*", "*");
        out = out.replaceAll("\\*\\*1\\t", "");
        out = out.replaceAll("\\*\\*1\\)", ")");
        out = out.replaceAll("\\*\\*1&", "&");
        out = out.replaceAll("sin\\(x\\)\\*\\*0", "1");
        out = out.replaceAll("cos\\(x\\)\\*\\*0", "1");
        out = out.replaceAll("&-(?:1\\*)+", "&-");
        out = out.replaceAll("&\\+(?:1\\*)+", "&+");
        out = out.replaceAll("\\*(?:1\\*)+", "*");
        out = out.replaceAll("\\*1\\+", "+");
        out = out.replaceAll("\\*1-", "-");
        out = out.replaceAll("\\*1\\t", "");
        out = out.replaceAll("\\*1\\)", ")");
        out = out.replaceAll("\\*1&", "&");
        out = out.replaceAll("&|\\t", "");
        if (out.startsWith("+")) {
            out = out.substring(1);
        }
        return out;
    }

    public ArrayList<Term> getPrimFunc() {
        return this.primFunc;
    }

    public ArrayList<Term> getDeriFunc() {
        return deriFunc;
    }

    public void addTerm(Term term) {
        this.primFunc.add(term);
    }
}