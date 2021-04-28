package GCSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHeapTest {
    private MyHeap my;
    @Before
    public void setUp() throws Exception {
        my = new MyHeap(16);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("----End----");
    }

    @Test
    public void add() {
        for (int i = 0; i < 16 ; i++) {
            MyObject temp = new MyObject();
            //temp.setAge(i);
            my.add(temp);
        }
        Object[] temp1 = my.getElementData();
        for(int i = 0; i < 1; i++) {
            if (temp1[i] == null) {continue;}
            if(2*i+1<16 && ((MyObject) temp1[2 * i + 1])!=null) {
                int temp2 = ((MyObject) temp1[i]).compareTo((MyObject) temp1[2 * i + 1]);
                assertTrue(temp2 < 0);
            }
            if(2*i+2<16 && ((MyObject) temp1[2 * i + 2])!=null) {
                int temp2 = ((MyObject) temp1[i]).compareTo((MyObject) temp1[2 * i + 2]);
                assertTrue(temp2 < 0 );
            }
        }
    }

    @Test
    public void removeFirst() {
        for (int i = 0; i < 16 ; i++) {
            MyObject temp = new MyObject();
            //temp.setAge(i);
            my.add(temp);
        }
        my.removeFirst();
        Object[] temp1 = my.getElementData();
        for(int i = 0; i < 15; i++) {
            if (temp1[i] == null) {continue;}
            if(2*i+1<16 && temp1[2 * i + 1]!=null) {
                int temp2 = ((MyObject) temp1[i]).compareTo((MyObject) temp1[2 * i + 1]);
                assertTrue(temp2 == -1);
            }
            if(2*i+2<16&&temp1[2 * i + 2]!=null) {
                int temp2 = ((MyObject) temp1[i]).compareTo((MyObject) temp1[2 * i + 2]);
                assertTrue(temp2 == -1);
            }
        }
    }
}