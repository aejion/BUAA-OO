import java.util.ArrayList;
import java.util.HashMap;

public class Elevator extends Thread {
    private final HashMap<Integer,ArrayList<Person>> mm = new HashMap<>();
    private HashMap<Integer,ArrayList<Person>> mmin = new HashMap<>();
    private String type;
    private String name;
    private int floor = 1;
    private int peronele = 0;
    private int direction = 0;
    private final HashMap<Integer,ArrayList<Person>> pp;
    private int maxper;
    private int shut = 0;
    private int onetime;
    private ArrayList<Integer> cango;
    private int iswait = 0;
    private final Object oo;
    private Control con;

    Elevator(String type1,String name1,HashMap<Integer,ArrayList<Person>> ppp1,
             Object o1,Control con1) {
        con = con1;
        type = type1;
        name = name1;
        pp = ppp1;
        oo = o1;
        switch (type) {
            case "A":
                maxper = 6;
                onetime = 400;
                break;
            case "B":
                maxper = 8;
                onetime = 500;
                break;
            case "C":
                maxper = 7;
                onetime = 600;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        cango = new ArrayList<>();
        getcango();
    }

    public void getcango() {
        if (type.equals("A")) {
            cango.add(-3);
            cango.add(-2);
            cango.add(-1);
            cango.add(1);
            cango.add(15);
            cango.add(16);
            cango.add(17);
            cango.add(18);
            cango.add(19);
            cango.add(20);
        } else if (type.equals("B")) {
            cango.add(-2);
            cango.add(-1);
            cango.add(1);
            cango.add(2);
            cango.add(4);
            cango.add(5);
            cango.add(6);
            cango.add(7);
            cango.add(8);
            cango.add(9);
            cango.add(10);
            cango.add(11);
            cango.add(12);
            cango.add(13);
            cango.add(14);
            cango.add(15);
        } else {
            cango.add(1);
            cango.add(3);
            cango.add(5);
            cango.add(7);
            cango.add(9);
            cango.add(11);
            cango.add(13);
            cango.add(15);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int nxtfloor = 0;
                synchronized (pp) {
                    while (mm.isEmpty() && mmin.isEmpty() && nothave()) {
                        if (shut == 1) { return; }
                        iswait = 1;
                        synchronized (oo) {
                            oo.notify();
                        }
                        pp.wait();
                        direction = 0;
                        iswait = 0;
                    }
                }
                if (direction == 0) {
                    nxtfloor = findone();
                }
                else if (direction == 1) {
                    nxtfloor = floor + 1;
                    if (nxtfloor == 0) { nxtfloor = 1; }
                }
                else if (direction == -1) {
                    nxtfloor = floor - 1;
                    if (nxtfloor == 0) { nxtfloor = -1; }
                }
                if ((nxtfloor == -1 && floor == 1) || (nxtfloor == 1 && floor == -1)) {
                    sleep(onetime);
                }
                else { sleep(Math.abs(nxtfloor - floor) * onetime); }
                cometo(nxtfloor);
                floor = nxtfloor;
                direction = nxtdire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name);
    }

    public synchronized int findone() {
        synchronized (pp) {
            if ((pp.containsKey(floor) || mm.containsKey(floor) || mmin.containsKey(floor))
                    && cango.contains(floor)) {
                return floor;
            }
            for (int i = floor - 1; i >= -3; i--) {
                if ((pp.containsKey(i) || mm.containsKey(i)) && cango.contains(i)) {
                    direction = -1;
                    int ans = floor - 1;
                    if (ans == 0) {
                        ans = -1;
                    }
                    return ans;
                }
            }
            for (int i = floor + 1; i <= 20; i++) {
                if ((pp.containsKey(i) || mm.containsKey(i)) && cango.contains(i)) {
                    direction = 1;
                    int ans = floor + 1;
                    if (ans == 0) {
                        ans = 1;
                    }
                    return ans;
                }
            }
            return floor;
        }
    }

    public boolean nothave() {
        for (int i = -3; i <= 20; i++) {
            if (pp.containsKey(i) && cango.contains(i)) {
                return false;
            }
        }
        return true;
    }

    public void cometo(int nxt) throws InterruptedException {
        if (floor != nxt) {
            SafeOutput.println(String.format("ARRIVE-%d-%s", nxt,name));
        }
        if (!cango.contains(nxt)) { return; }
        synchronized (pp) {
            if (!needopen(nxt)) { return; }
            SafeOutput.println(String.format("OPEN-%d-%s", nxt, name));
            if (mmin.containsKey(nxt)) {
                getoff(nxt);
            }
        }
        sleep(400);
        synchronized (pp) {
            if (pp.containsKey(nxt) || mm.containsKey(nxt)) {
                getin(nxt);
            }
        }
        SafeOutput.println(String.format("CLOSE-%d-%s", nxt,name));
    }

    public boolean needopen(int nxt) {
        if (mmin.containsKey(nxt)) { return true; }
        if (peronele == maxper) { return false; }
        if (mm.containsKey(nxt)) { return true; }
        ArrayList<Person> temp;
        if (pp.containsKey(nxt)) {
            temp = pp.get(nxt);
            for (Person i : temp) {
                if (!cango.contains(i.getToFloor())) {
                    int to = i.getFirstToFloor(type);
                    if (to != nxt) {
                        return true;
                    }
                }
                else { return true; }
            }
        }
        return false;
    }

    public void getoff(int nxt) {
        ArrayList<Person> temppp = mmin.get(nxt);
        for (Person i : temppp) {
            i.getout(name,nxt);
            if (nxt != i.getToFloor()) {
                con.add(i);
            }
            peronele--;
        }
        mmin.remove(nxt);
    }

    public void getin(int nxt) {
        if (peronele == maxper) { return; }
        ArrayList<Person> temp;
        ArrayList<Person> tempp = new ArrayList<>();
        if (mm.containsKey(nxt)) {
            temp = mm.get(nxt);
            for (Person i : temp) {
                if (peronele < maxper) {
                    peronele++;
                    ArrayList<Person> temppp = new ArrayList<>();
                    if (mmin.containsKey(i.getToFloor())) { temppp = mmin.get(i.getToFloor()); }
                    temppp.add(i);
                    mmin.put(i.getToFloor(),temppp);
                    i.getin(name);
                }
                else { tempp.add(i); }
            }
            if (tempp.isEmpty()) { mm.remove(nxt); }
            else { mm.put(nxt,tempp); }
        }
        tempp = new ArrayList<>();
        if (pp.containsKey(nxt)) {
            temp = pp.get(nxt);
            for (Person i : temp) {
                if (peronele < maxper) {
                    peronele++;
                    ArrayList<Person> temppp = new ArrayList<>();
                    if (cango.contains(i.getToFloor())) {
                        if (mmin.containsKey(i.getToFloor())) {
                            temppp = mmin.get(i.getToFloor());
                        }
                        temppp.add(i);
                        mmin.put(i.getToFloor(), temppp);
                    } else {
                        int to = i.getFirstToFloor(type);
                        if (to == nxt) {
                            peronele--;
                            con.add(i);
                            continue;
                        }
                        if (mmin.containsKey(to)) {
                            temppp = mmin.get(to);
                        }
                        temppp.add(i);
                        mmin.put(to, temppp);
                    }
                    i.getin(name);
                } else { tempp.add(i); }
            }
            if (tempp.isEmpty()) { pp.remove(nxt); }
            else { pp.put(nxt, tempp); }
        }
    }

    public int nxtdire() {
        synchronized (pp) {
            synchronized (mm) {
                if (mmin.containsKey(floor)) { return 0; }
                if (direction == -1) {
                    if (peronele == maxper) {
                        for (int i = -3; i < floor; i++) {
                            if (mmin.containsKey(i)) { return -1; }
                        }
                        return 1;
                    }
                    for (int i = -3; i < floor; i++) {
                        if ((mmin.containsKey(i) || mm.containsKey(i) || pp.containsKey(i))
                            && cango.contains(i)) {
                            return -1;
                        }
                    }
                    return 1;
                } else {
                    if (peronele == maxper) {
                        for (int i = floor; i <= 20; i++) {
                            if (mmin.containsKey(i)) { return 1; }
                        }
                        return -1;
                    }
                    for (int i = floor; i <= 20; i++) {
                        if ((mmin.containsKey(i) || mm.containsKey(i) || pp.containsKey(i))
                                && cango.contains(i)) {
                            return 1;
                        }
                    }
                    return -1;
                }
            }
        }
    }

    public void setShut(int shut) {
        this.shut = shut;
    }

    public HashMap<Integer, ArrayList<Person>> getMm() {
        return mm;
    }

    public String getType() {
        return type;
    }

    public int getIswait() {
        return iswait;
    }
}
