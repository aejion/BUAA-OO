package com.oocourse.spec3.main;

import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyNetworkTest {
    private MyNetwork my = new MyNetwork();
    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 3000;i++) {
            my.addPerson(new MyPerson(i,"1", BigInteger.valueOf(0),3));
        }
        /*my.addRelation(0,1,10);
        my.addRelation(0,5,10);
        my.addRelation(0,6,10);
        my.addRelation(1,2,10);
        my.addRelation(2,3,10);
        my.addRelation(2,9,10);
        my.addRelation(4,5,10);
        my.addRelation(4,6,10);
        my.addRelation(4,9,10);
        my.addRelation(6,7,10);
        my.addRelation(8,9,10);*/
        for (int i = 1; i <= 2999;i++) {
            my.addRelation(i-1,i,0);
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void queryMinPath() throws PersonIdNotFoundException {
        for(int i = 0;i < 3000;i++) {
            int temp = my.queryMinPath(0, 2999);
            //System.out.println(temp);
            assertTrue(temp == 0);
        }
    }
}