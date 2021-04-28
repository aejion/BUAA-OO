import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Input extends Thread {
    private final HashMap<Integer,ArrayList<Person>> pp;
    private final ArrayList<Elevator> ele;
    private Shut ss;
    private final Object oo;
    private Control con;

    Input(HashMap<Integer,ArrayList<Person>> ppp1, ArrayList<Elevator> ele1,
          Shut ss1,Object o,Control con1) {
        con = con1;
        pp = ppp1;
        ele = ele1;
        ss = ss1;
        oo = o;
    }

    @Override
    public void run() {
        try {
            ElevatorInput elevatorInput = new ElevatorInput(System.in);
            while (true) {
                Request request = elevatorInput.nextRequest();
                if (request == null) {
                    ss.setShut(true);
                    synchronized (oo) {
                        oo.notifyAll();
                    }
                    break;
                } else {
                    if (request instanceof PersonRequest) {
                        Person temp = new Person(((PersonRequest) request).getFromFloor(),
                                                ((PersonRequest) request).getToFloor(),
                                                ((PersonRequest) request).getPersonId());
                        ArrayList<Person> tempp = new ArrayList<>();
                        synchronized (pp) {
                            if (pp.containsKey(temp.getFromFloor())) {
                                tempp = pp.get(temp.getFromFloor()); }
                            tempp.add(temp);
                            pp.put(temp.getFromFloor(),tempp);
                            pp.notifyAll();
                        }
                    } else if (request instanceof ElevatorRequest) {
                        Elevator newele = new Elevator(((ElevatorRequest) request).getElevatorType()
                                ,((ElevatorRequest) request).getElevatorId(),pp,oo,con);
                        newele.start();
                        synchronized (ele) {
                            ele.add(newele);
                        }
                    }
                }
            }
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
