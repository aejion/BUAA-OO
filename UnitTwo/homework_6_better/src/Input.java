import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class Input extends Thread {
    private final ArrayBlockingQueue<Person> pp;
    private final ArrayList<String> ss;
    private Control con;
    private String []s1 = {"A","B","C","D","E"};

    Input(ArrayBlockingQueue<Person> pp1,ArrayList<String> ss1) {
        pp = pp1;
        ss = ss1;
    }

    public void setCon(Control con1) {
        con = con1;
    }

    @Override
    public void run() {
        try {
            ElevatorInput elevatorInput = new ElevatorInput(System.in);
            int elenum = elevatorInput.getElevatorNum();
            synchronized (ss) {
                ss.addAll(Arrays.asList(s1).subList(0, elenum));
                ss.notify();
            }
            while (true) {
                PersonRequest request = elevatorInput.nextPersonRequest();
                if (request == null) {
                    con.shut(1);
                    synchronized (pp) {
                        pp.notifyAll();
                    }
                    break;
                } else {
                    Person temper = new Person(request.getFromFloor(), request.getToFloor(),
                            request.getPersonId());
                    synchronized (pp) {
                        pp.add(temper);
                        pp.notifyAll();
                    }
                }
            }
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
