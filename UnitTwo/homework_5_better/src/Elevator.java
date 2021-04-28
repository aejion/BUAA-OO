import com.oocourse.TimableOutput;

import java.util.ArrayList;
import java.util.HashMap;

public class Elevator extends Thread {
    private final HashMap<Integer,ArrayList<Person>> mm;
    private final HashMap<Integer,ArrayList<Person>> mmin;
    private int direction;
    private int floor;
    private Control con;

    Elevator(HashMap<Integer,ArrayList<Person>> mm1,HashMap<Integer,ArrayList<Person>> mm2) {
        mmin = mm2;
        mm = mm1;
        direction = 0;
        floor = 1;
    }

    public void setCon(Control cc) { con = cc; }

    @Override
    public void run() {
        try {
            while (true) {
                int nxtfloor = 0;
                int flag = 0;
                synchronized (mm) {
                    while (mm.isEmpty() && mmin.isEmpty()) {
                        synchronized (con) {
                            if (con.shut()) {
                                flag = 1;
                                break;
                            }
                        }
                        mm.wait();
                    }
                }
                if (flag == 1) { break; }
                if (direction == 0) { nxtfloor = findone(); }
                else if (direction == 1) { nxtfloor = floor + 1; }
                else if (direction == -1) { nxtfloor = floor - 1; }
                sleep(Math.abs(nxtfloor - floor) * 400);
                cometo(nxtfloor);
                floor = nxtfloor;
                direction = nxtdire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int nxtdire() {
        synchronized (mm) {
            synchronized (mmin) {
                if (mmin.isEmpty()) { return 0; }
                if (direction == -1) {
                    for (int i = 1; i < floor; i++) {
                        if (mmin.containsKey(i) || mm.containsKey(i)) { return -1; }
                    }
                    return 1;
                }
                else {
                    for (int i = floor; i <= 15; i++) {
                        if (mmin.containsKey(i) || mm.containsKey(i)) { return 1; }
                    }
                    return -1;
                }
            }
        }
    }

    public synchronized int findone() {
        if (mm.containsKey(floor)) { return floor; }
        for (int i = floor - 1; i >= 1; i--) {
            if (mm.containsKey(i)) {
                direction = -1;
                return floor - 1;
            }
        }
        for (int i = floor + 1; i <= 15; i++) {
            if (mm.containsKey(i)) {
                direction = 1;
                return floor + 1;
            }
        }
        return floor + 1;
    }

    public void cometo(int nxt) throws InterruptedException {
        if (floor != nxt) {
            TimableOutput.println(String.format("ARRIVE-%d", nxt));
        }
        int flag = 0;
        synchronized (mm) {
            synchronized (mmin) {
                if (mm.containsKey(nxt) || mmin.containsKey((nxt))) {
                    flag = 1;
                    TimableOutput.println(String.format("OPEN-%d", nxt));
                }
                if (mmin.containsKey(nxt)) {
                    getoff(nxt);
                }
            }
        }
        if (flag == 1) { sleep(400); }
        synchronized (mm) {
            if (mm.containsKey(nxt)) { getin(nxt); }
        }
        if (flag == 1) { TimableOutput.println(String.format("CLOSE-%d", nxt)); }
    }

    public void getin(int nxt) {
        synchronized (mm) {
            synchronized (mmin) {
                ArrayList<Person> temppp = mm.get(nxt);
                for (Person i : temppp) {
                    i.getin();
                    ArrayList<Person> tempin = new ArrayList<>();
                    if (mmin.containsKey(i.getToFloor())) {
                        tempin = mmin.get(i.getToFloor());
                    }
                    tempin.add(i);
                    mmin.put(i.getToFloor(), tempin);
                }
                mm.remove(nxt);
            }
        }
    }

    public void getoff(int nxt) {
        ArrayList<Person> temppp = mmin.get(nxt);
        for (Person i : temppp) {
            i.getout();
        }
        mmin.remove(nxt);
    }
}
