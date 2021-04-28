package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyJVMTest {

    private MyJVM my = new MyJVM();

    @Before
    public void setUp() throws Exception {
        my.createObject(15);
    }

    @After
    public void tearDown() throws Exception {
        //System.out.println("----End----");
    }

    @Test
    public void minorANDmajortest() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<Integer> temp = new ArrayList<>();
        temp.add(0);
        temp.add(3);
        temp.add(10);
        temp.add(7);
        my.setUnreference(temp);
        for (int i = 0 ; i < 9; i++) {
            my.minorGC();
            my.getSnapShoot();
            my.createObject(3);
        }
        temp.clear();
        temp.add(1);
        temp.add(2);
        my.setUnreference(temp);
        my.majorGC();
        my.getSnapShoot();
        String ss = new String("Eden: 0\n\nSurvive 0: 11\n1 2 4 5 6 8 9 11 12 13 14 \nSurvive 1: 0\n\nTenured: 0\n\n" +
                "---------------------------------\nEden: 0\n\nSurvive 0: 0\n\nSurvive 1: 14\n15 16 17 " +
                "1 2 4 5 6 8 9 11 12 13 14 \nTenured: 0\n\n---------------------------------\nEden: 0\n\nSurvive 0: 17" +
                "\n18 19 20 15 16 17 1 2 4 5 6 8 9 11 12 13 14 \nSurvive 1: 0\n\nTenured: 0\n\n---------------------" +
                "------------\nEden: 0\n\nSurvive 0: 0\n\nSurvive 1: 20\n21 22 23 18 19 20 15 16 17 1 2 4 5 6 8 9 11 12 13 14 \n" +
                "Tenured: 0\n\n---------------------------------\nEden: 0\n\nSurvive 0: 23\n24 25 26 21 22 23 " +
                "18 19 20 15 16 17 1 2 4 5 6 8 9 11 12 13 14 \nSurvive 1: 0\n\nTenured: 0\n\n----------------" +
                "-----------------\nEden: 0\n\nSurvive 0: 0\n\nSurvive 1: 26\n27 28 29 24 25 26 21 22 " +
                "23 18 19 20 15 16 17 1 2 4 5 6 8 9 11 12 13 14 \nTenured: 0\n\n-------------------" +
                "--------------\nEden: 0\n\nSurvive 0: 29\n30 31 32 27 28 29 24 25 26 21 22 23 18 19 " +
                "20 15 16 17 1 2 4 5 6 8 9 11 12 13 14 \nSurvive 1: 0\n\nTenured: 0\n\n----------" +
                "-----------------------\nEden: 0\n\nSurvive 0: 0\n\nSurvive 1: 21\n33 34 35 30 31 " +
                "32 27 28 29 24 25 26 21 22 23 18 19 20 15 16 17 \nTenured: 11\n1 2 4 5 6 8 9 11 12 13 14 \n------------" +
                "---------------------\nEden: 0\n\nSurvive 0: 21\n36 37 38 33 34 35 30 31 32 27 28 29 24 25 " +
                "26 21 22 23 18 19 20 \nSurvive 1: 0\n\nTenured: 14\n1 2 4 5 6 8 9 11 12 13 14 15 16 17 \n" +
                "---------------------------------\nEden: 3\n39 40 41 \nSurvive 0: 21\n36 37 38 33 34 " +
                "35 30 31 32 27 28 29 24 25 26 21 22 23 18 19 20 \nSurvive 1: 0\n\nTenured: 12\n4 5 6 8 9 11 12 13 " +
                "14 15 16 17 \n---------------------------------\n");
        assertEquals(ss,outContent.toString().replaceAll("\r",""));
    }
}