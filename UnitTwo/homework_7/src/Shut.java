import java.util.ArrayList;
import java.util.HashMap;

public class Shut extends Thread {
    private boolean shut = false;
    private ArrayList<Elevator> eleset;
    private final Object oo;
    private final HashMap<Integer,ArrayList<Person>> pp;

    Shut(ArrayList<Elevator> ele,Object o1,HashMap<Integer,ArrayList<Person>> ppp1) {
        eleset = ele;
        oo = o1;
        pp = ppp1;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (oo) {
                while (!shut || judge() || !pp.isEmpty()) {
                    try {
                        oo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
        setAll();
    }

    public boolean judge() {
        boolean ans = false;
        synchronized (eleset) {
            for (Elevator ele : eleset) {
                if (ele.getIswait() == 0) {
                    ans = true;
                    break;
                }
            }
        }
        return ans;
    }

    public void setShut(boolean s) {
        shut = s;
    }

    public void setAll() {
        for (Elevator ele: eleset) {
            ele.setShut(1);
        }
        synchronized (pp) {
            pp.notifyAll();
        }
    }
}
