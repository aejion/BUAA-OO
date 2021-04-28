package com.oocourse.spec2.main;

import com.oocourse.spec2.exceptions.EqualPersonIdException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyNetworkTest {
    private MyNetwork my;
    @Before
    public void setup() throws EqualPersonIdException {
        System.out.println("----Begin test----");
        my = new MyNetwork();
        MyPerson per = new MyPerson(1,"123", BigInteger.valueOf(1),1);
        my.addPerson(per);
    }

    @Test
    public void contains() {
        Boolean t = my.contains(1);
        assertTrue(t);
    }

    @After
    public void end() {
        System.out.println("----End test----");
    }
}