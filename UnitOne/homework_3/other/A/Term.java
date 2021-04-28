package me;

import java.math.BigInteger;

public class Term implements Index {
    //e*x**a*sinx**b*cosx**c
    private BigInteger ee;
    private BigInteger ax;
    private BigInteger bs;
    private BigInteger cc;

    public BigInteger getEe() {
        return ee;
    }

    public BigInteger getAx() {
        return ax;
    }

    public BigInteger getBs() {
        return bs;
    }

    public BigInteger getCc() {
        return cc;
    }

    public Term() {
        this.ee = BigInteger.ONE;
        this.ax = BigInteger.ZERO;
        this.bs = BigInteger.ZERO;
        this.cc = BigInteger.ZERO;
    }

    public Term(Poly poly) {
        this.ee = BigInteger.ONE;
        this.ax = BigInteger.ZERO;
        this.bs = BigInteger.ZERO;
        this.cc = BigInteger.ZERO;
        BigInteger minus = new BigInteger("-1");
        if (poly.getx().compareTo(minus) == 0) {
            this.ee = ee.multiply(minus);
        }
        if (poly.getS() == "") {
            this.ee = poly.getm();
        }
        else if (poly.getS().equalsIgnoreCase("x")) {
            this.ax = poly.getm();
        }
        else if (poly.getS().equalsIgnoreCase("sin(x)")) {
            this.bs = poly.getm();
        }
        else if (poly.getS().equalsIgnoreCase("cos(x)")) {
            this.cc = poly.getm();
        }
    }

    public Term add(Poly poly) {
        Term tmp = new Term();
        tmp.ee = ee;
        tmp.ax = ax;
        tmp.bs = bs;
        tmp.cc = cc;
        BigInteger minus = new BigInteger("-1");
        if (poly.getx().compareTo(minus) == 0) {
            tmp.ee = ee.multiply(minus);
        }
        if (poly.getS() == "") {
            tmp.ee = ee.multiply(poly.getm());
        }
        else if (poly.getS().equalsIgnoreCase("x")) {
            tmp.ax = ax.add(poly.getm());
        }
        else if (poly.getS().equalsIgnoreCase("sin(x)")) {
            tmp.bs = bs.add(poly.getm());
        }
        else if (poly.getS().equalsIgnoreCase("cos(x)")) {
            tmp.cc = cc.add(poly.getm());
        }
        return tmp;
    }

    @Override
    public String diff() {
        if (zero()) {
            return "0";
        }
        String diff = "";
        Term d1 = diff1();
        Term d2 = diff2();
        Term d3 = diff3();
        if (d1.getEe().compareTo(BigInteger.ZERO) > 0) {
            diff = "+" + d1.print() + diff;
        }
        else {
            diff += d1.print();
        }
        if (d2.getEe().compareTo(BigInteger.ZERO) > 0) {
            diff = "+" + d2.print() + diff;
        }
        else {
            diff += d2.print();
        }
        if (d3.getEe().compareTo(BigInteger.ZERO) > 0) {
            diff = "+" + d3.print() + diff;
        }
        else {
            diff += d3.print();
        }
        if (diff.charAt(0) == '+') {
            diff =  diff.substring(1);
        }
        if (diff.equalsIgnoreCase("") == false) {
            diff = "(" + diff + ")";
        }
        return diff;
    }

    @Override
    public String print() {
        String out = "";
        BigInteger z = BigInteger.ZERO;
        if (ee.compareTo(z) == 0) {
            return out;
        }
        if (ax.compareTo(z) == 0 && bs.compareTo(z) == 0 && cc.compareTo(z) == 0) {
            out += ee;
            return out;
        }
        if (ee.compareTo(new BigInteger("-1")) == 0) {
            out += '-';
        }
        else {
            out += ee;
        }
        if (ax.compareTo(BigInteger.ZERO) != 0) {
            if (ax.compareTo(BigInteger.ONE) == 0) {
                out += "*x";
            }
            else {
                out += "*x**";
                out += ax;
            }
        }
        if (bs.compareTo(BigInteger.ZERO) != 0) {
            if (bs.compareTo(BigInteger.ONE) == 0) {
                out += "*sin(x)";
            }
            else {
                out += "*sin(x)**";
                out += bs;
            }
        }
        if (cc.compareTo(BigInteger.ZERO) != 0) {
            if (cc.compareTo(BigInteger.ONE) == 0) {
                out += "*cos(x)";
            }
            else {
                out += "*cos(x)**";
                out += cc;
            }
        }
        if (ee.compareTo(BigInteger.ONE) == 0) {
            return out.substring(2);
        }
        if (ee.compareTo(new BigInteger("-1")) == 0) {
            return "-" + out.substring(2);
        }
        if (out.charAt(0) == '+') {
            return out.substring(1);
        }
        else {
            return out;
        }
    }

    public Term diff1() {
        Term tmp = new Term();
        tmp.ee = ee;
        tmp.ax = ax;
        tmp.bs = bs;
        tmp.cc = cc;
        if (ax.compareTo(BigInteger.ZERO) == 0) {
            tmp.ee = BigInteger.ZERO;
            return tmp;
        }
        else {
            tmp.ee = ee.multiply(ax);
            tmp.ax = ax.subtract(BigInteger.ONE);
        }
        return tmp;
    }

    public Term diff2() {
        Term tmp = new Term();
        tmp.ee = ee;
        tmp.ax = ax;
        tmp.bs = bs;
        tmp.cc = cc;
        if (bs.compareTo(BigInteger.ZERO) == 0) {
            tmp.ee = BigInteger.ZERO;
            return tmp;
        }
        else {
            tmp.ee = ee.multiply(bs);
            tmp.bs = bs.subtract(BigInteger.ONE);
            tmp.cc = cc.add(BigInteger.ONE);
        }
        return tmp;
    }

    public Term diff3() {
        Term tmp = new Term();
        tmp.ee = ee;
        tmp.ax = ax;
        tmp.bs = bs;
        tmp.cc = cc;
        if (cc.compareTo(BigInteger.ZERO) == 0) {
            tmp.ee = BigInteger.ZERO;
            return tmp;
        }
        else {
            tmp.ee = ee.multiply(cc).multiply(new BigInteger("-1"));
            tmp.cc = cc.subtract(BigInteger.ONE);
            tmp.bs = bs.add(BigInteger.ONE);
        }
        return tmp;
    }

    public boolean zero() {
        BigInteger z = BigInteger.ZERO;
        if (ax.compareTo(z) == 0 && bs.compareTo(z) == 0 && cc.compareTo(z) == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}