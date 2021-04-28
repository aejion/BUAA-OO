import com.oocourse.TimableOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        TimableOutput.initStartTimestamp();
        ArrayBlockingQueue<Person> pp = new ArrayBlockingQueue<>(60, true);
        ArrayList<String> ss = new ArrayList<>();
        Control con = new Control(pp);
        Input in = new Input(pp,ss);
        in.setCon(con);
        in.start();
        con.start();
        synchronized (ss) {
            while (ss.isEmpty()) {
                ss.wait();
            }
        }
        for (String s : ss) {
            HashMap<Integer, ArrayList<Person>> mm = new HashMap<>();
            Elevator ele = new Elevator(mm, s, con, pp);
            ele.start();
            con.add(ele, mm);
        }
        synchronized (pp) {
            pp.notifyAll();
        }
    }
}
