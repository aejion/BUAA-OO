import java.util.ArrayList;
import java.util.HashMap;

public class Control {
    private ArrayList<Elevator> eleset;
    private int now = 0;
    private final HashMap<Integer, ArrayList<Person>> pp1;

    Control(ArrayList<Elevator> ele1,
            HashMap<Integer, ArrayList<Person>> ppp) {
        eleset = ele1;
        pp1 = ppp;
    }

    public void add(Person temp) {
        String type = temp.getNxtele();
        while (true) {
            synchronized (eleset) {
                Elevator ele = eleset.get(now);
                now++;
                if (now == eleset.size()) {
                    now = 0;
                }
                if (ele.getType().equals(type)) {
                    synchronized (pp1) {
                        HashMap<Integer, ArrayList<Person>> mm = ele.getMm();
                        ArrayList<Person> per = new ArrayList<>();
                        if (mm.containsKey(temp.getFromFloor())) {
                            per = mm.get(temp.getFromFloor());
                        }
                        per.add(temp);
                        mm.put(temp.getFromFloor(), per);
                        pp1.notifyAll();
                    }
                    break;
                }
            }
        }
    }
}
