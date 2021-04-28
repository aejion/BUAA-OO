import com.oocourse.TimableOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        ArrayBlockingQueue<Person> pp = new ArrayBlockingQueue<>(40, true);
        HashMap<Integer,ArrayList<Person>> mm = new HashMap<>();
        HashMap<Integer,ArrayList<Person>> mm2 = new HashMap<>();
        Input inp = new Input(pp);
        Elevator ele = new Elevator(mm,mm2);
        Control con = new Control(pp,mm);
        inp.setCon(con);
        ele.setCon(con);
        inp.start();
        ele.start();
    }
}
