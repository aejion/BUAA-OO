import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Control extends Thread {
    private ArrayList<Elevator> eleset = new ArrayList<>();
    private final ArrayList<HashMap<Integer,ArrayList<Person>>> mmset = new ArrayList<>();
    private final ArrayBlockingQueue<Person> pp;
    private boolean hasin = true;

    Control(ArrayBlockingQueue<Person> pp1) {
        pp = pp1;
    }

    public void add(Elevator ele,HashMap<Integer,ArrayList<Person>> mm1) {
        eleset.add(ele);
        mmset.add(mm1);
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (eleset.isEmpty()) {
                    synchronized (pp) { pp.wait(); }
                }
                synchronized (pp) {
                    while (!pp.isEmpty()) {
                        Person temp = pp.poll();
                        int from = temp.getFromFloor();
                        int to = temp.getToFloor();
                        synchronized (mmset) {
                            int now = 0;
                            int personon = eleset.get(0).getPeronele();
                            int getto = gettoper(eleset.get(0),temp);
                            for (int i = 1; i < eleset.size(); i++) {
                                int tempper = eleset.get(i).getPeronele();
                                if (tempper < 7) {
                                    if (personon == 7) {
                                        personon = tempper;
                                        now = i;
                                    }
                                    else {
                                        int tempgetto = gettoper(eleset.get(i),temp);
                                        if (tempgetto < getto) {
                                            getto = tempgetto;
                                            now = i;
                                        }
                                    }
                                }
                            }
                            HashMap<Integer, ArrayList<Person>> mm1 = mmset.get(now);
                            synchronized (mm1) {
                                ArrayList<Person> tempp = new ArrayList<>();
                                if (mm1.containsKey(temp.getFromFloor())) {
                                    tempp = mm1.get(temp.getFromFloor());
                                }
                                tempp.add(temp);
                                mm1.put(temp.getFromFloor(), tempp);
                                mm1.notify();
                            }
                        }
                    }
                    if (shut(0) && pp.isEmpty()) {
                        break;
                    }
                    pp.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (HashMap<Integer, ArrayList<Person>> mm1 : mmset) {
            synchronized (mm1) {
                mm1.notify();
            }
        }
    }

    public synchronized boolean shut(int x) {
        if (x == 1) { hasin = false; }
        return !hasin;
    }

    public int gettoper(Elevator ele,Person per) {
        int dir = ele.getDirection();
        if (dir == 0) {
            return Math.abs(per.getFromFloor() - ele.getFloor());
        }
        else if (dir == 1) {
            if (per.getFromFloor() >= ele.getFloor()) {
                return per.getFromFloor() - ele.getFloor();
            }
            else {
                return 16 - per.getFromFloor();
            }
        }
        else {
            if (per.getFromFloor() <= ele.getFloor()) {
                return - per.getFromFloor() + ele.getFloor();
            }
            else {
                return per.getFromFloor() + 3;
            }
        }
    }
}
