import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Control extends Thread {
    private ArrayList<Elevator> eleset = new ArrayList<>();
    private final ArrayList<HashMap<Integer,ArrayList<Person>>> mmset = new ArrayList<>();
    private final ArrayBlockingQueue<Person> pp;
    private boolean hasin = true;
    private int now = 1;

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
                    synchronized (pp) {
                        pp.wait();
                    }
                }
                synchronized (pp) {
                    while (!pp.isEmpty()) {
                        Person temp = pp.poll();
                        synchronized (mmset) {
                            HashMap<Integer, ArrayList<Person>> mm1 = mmset.get(now - 1);
                            synchronized (mm1) {
                                ArrayList<Person> tempp = new ArrayList<>();
                                if (mm1.containsKey(temp.getFromFloor())) {
                                    tempp = mm1.get(temp.getFromFloor());
                                }
                                tempp.add(temp);
                                mm1.put(temp.getFromFloor(), tempp);
                                mm1.notify();
                            }
                            now++;
                            if (now > mmset.size()) { now = 1; }
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
}
