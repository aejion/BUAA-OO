import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class Control {
    private ArrayBlockingQueue<Person> pp;
    private boolean hasin = true;
    private final HashMap<Integer, ArrayList<Person>> mm;

    Control(ArrayBlockingQueue<Person> pp1,HashMap<Integer,ArrayList<Person>> mm1) {
        pp = pp1;
        mm = mm1;
    }

    public void run() {
        ArrayList<Person> tempp = new ArrayList<>();
        synchronized (mm) {
            while (!pp.isEmpty()) {
                Person temp = pp.poll();
                if (mm.containsKey(temp.getFromFloor())) {
                    tempp = mm.get(temp.getFromFloor());
                }
                tempp.add(temp);
                mm.put(temp.getFromFloor(), tempp);
            }
            mm.notify();
        }
    }

    public void setHasin() {
        hasin = false;
    }

    public boolean shut() {
        return !hasin;
    }
}
