package com.oocourse.spec3.main;

public class MyData implements Comparable<MyData> {
    private MyPerson my;
    private int minlen;

    MyData(MyPerson per, int a) {
        my = per;
        minlen = a;
    }

    public int getMinlen() {
        return minlen;
    }

    public MyPerson getMy() {
        return my;
    }

    @Override
    public int compareTo(MyData o) {
        return minlen - o.getMinlen();
    }
}
