import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class Input extends Thread {
    private ArrayBlockingQueue<Person> pp;
    private Control con;

    Input(ArrayBlockingQueue<Person> pp1) {
        pp = pp1;
    }

    public void setCon(Control con1) {
        con = con1;
    }

    @Override
    public void run() {
        try {
            ElevatorInput elevatorInput = new ElevatorInput(System.in);
            while (true) {
                PersonRequest request = elevatorInput.nextPersonRequest();
                if (request == null) {
                    synchronized (con) {
                        con.setHasin();
                        con.run();
                    }
                    break;
                } else {
                    Person temper = new Person(request.getFromFloor(),request.getToFloor(),
                                                request.getPersonId());
                    pp.add(temper);
                    synchronized (con) { con.run(); }
                }
            }
            elevatorInput.close();
        } catch (IOException e) {
            System.out.print("\n");
        }
    }
}
