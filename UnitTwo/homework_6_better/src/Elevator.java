import com.oocourse.TimableOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Elevator extends Thread {
    private final HashMap<Integer, ArrayList<Person>> mm;
    private final HashMap<Integer,ArrayList<Person>> mmin = new HashMap<>();
    private int direction;
    private int floor;
    private String name;
    private Control con;
    private int peronele = 0;
    private ArrayBlockingQueue<Person> pp;

    Elevator(HashMap<Integer,ArrayList<Person>> mm1,String nn,Control con1,
             ArrayBlockingQueue<Person> pp1) {
        mm = mm1;
        direction = 0;
        floor = 1;
        name = nn;
        con = con1;
        pp = pp1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int nxtfloor = 0;
                int flag = 0;
                synchronized (mm) {
                    while (mm.isEmpty() && mmin.isEmpty()) {
                        if (con.shut(0) && pp.isEmpty()) {
                            flag = 1;
                            break;
                        }
                        mm.wait();
                    }
                }
                if (flag == 1) { break; }
                if (direction == 0) { nxtfloor = findone(); }
                else if (direction == 1) {
                    nxtfloor = floor + 1;
                    if (nxtfloor == 0) { nxtfloor = 1; }
                }
                else if (direction == -1) {
                    nxtfloor = floor - 1;
                    if (nxtfloor == 0) { nxtfloor = -1; }
                }
                if ((nxtfloor == -1 && floor == 1) || (nxtfloor == 1 && floor == -1)) {
                    sleep(400);
                }
                else { sleep(Math.abs(nxtfloor - floor) * 400); }
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
            if (mmin.isEmpty()) { return 0; }
            if (direction == -1) {
                if (peronele == 7) {
                    for (int i = -3; i < floor; i++) {
                        if (mmin.containsKey(i)) { return -1; }
                    }
                    return 1;
                }
                for (int i = -3; i < floor; i++) {
                    if (mmin.containsKey(i) || mm.containsKey(i)) { return -1; }
                }
                return 1;
            }
            else {
                if (peronele == 7) {
                    for (int i = floor; i < 16; i++) {
                        if (mmin.containsKey(i)) { return 1; }
                    }
                    return -1;
                }
                for (int i = floor; i <= 16; i++) {
                    if (mmin.containsKey(i) || mm.containsKey(i)) { return 1; }
                }
                return -1;
            }
        }
    }

    public synchronized int findone() {
        if (mm.containsKey(floor)) { return floor; }
        for (int i = floor - 1; i >= -3; i--) {
            if (mm.containsKey(i)) {
                direction = -1;
                int ans = floor - 1;
                if (ans == 0) { ans = -1; }
                return ans;
            }
        }
        for (int i = floor + 1; i <= 16; i++) {
            if (mm.containsKey(i)) {
                direction = 1;
                int ans = floor + 1;
                if (ans == 0) { ans = 1; }
                return ans;
            }
        }
        return floor + 1;
    }

    public void cometo(int nxt) throws InterruptedException {
        if (floor != nxt) {
            TimableOutput.println(String.format("ARRIVE-%d-%s", nxt,name));
        }
        int flag = 0;
        synchronized (mm) {
            synchronized (mmin) {
                if (mm.containsKey(nxt) || mmin.containsKey((nxt))) {
                    flag = 1;
                    TimableOutput.println(String.format("OPEN-%d-%s", nxt,name));
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
        if (flag == 1) { TimableOutput.println(String.format("CLOSE-%d-%s", nxt,name)); }
    }

    public void getin(int nxt) {
        synchronized (mm) {
            ArrayList<Person> temppp = mm.get(nxt);
            ArrayList<Person> temppp1 = new ArrayList<>();
            for (Person i : temppp) {
                if (peronele < 7) {
                    peronele++;
                    i.getin(name);
                    ArrayList<Person> tempin = new ArrayList<>();
                    if (mmin.containsKey(i.getToFloor())) {
                        tempin = mmin.get(i.getToFloor());
                    }
                    tempin.add(i);
                    mmin.put(i.getToFloor(), tempin);
                }
                else { temppp1.add(i); }
            }
            if (temppp1.isEmpty()) { mm.remove(nxt); }
            else { mm.put(nxt,temppp1); }
        }
    }

    public void getoff(int nxt) {
        ArrayList<Person> temppp = mmin.get(nxt);
        for (Person i : temppp) {
            i.getout(name);
            peronele--;
        }
        mmin.remove(nxt);
    }

    public int getPeronele() {
        return peronele;
    }

    public int getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }
}
