import com.oocourse.TimableOutput;

import java.util.ArrayList;
import java.util.HashMap;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        HashMap<Integer,ArrayList<Person>> pp = new HashMap<>();
        ArrayList<Elevator> ele = new ArrayList<>();
        Object o = new Object();
        Control con = new Control(ele,pp);
        Shut shut = new Shut(ele,o,pp);
        Elevator ele1 = new Elevator("A","A",pp,o,con);
        Elevator ele2 = new Elevator("B","B",pp,o,con);
        Elevator ele3 = new Elevator("C","C",pp,o,con);
        ele.add(ele1);
        ele.add(ele2);
        ele.add(ele3);
        Input in = new Input(pp,ele,shut,o,con);
        shut.start();
        in.start();
        ele1.start();
        ele2.start();
        ele3.start();
    }
}
