import java.util.ArrayList;

public class Person {
    private int fromFloor;
    private int toFloor;
    private int id;
    private String nxtele;
    private ArrayList<Integer> canA;
    private ArrayList<Integer> canB;
    private ArrayList<Integer> canC;

    Person(int aa, int bb, int cc) {
        fromFloor = aa;
        toFloor = bb;
        id = cc;
        canA = new ArrayList<>();
        canB = new ArrayList<>();
        canC = new ArrayList<>();
        init1();
    }

    public void init1() {
        canA.add(-3);
        canA.add(-2);
        canA.add(-1);
        canA.add(1);
        canA.add(15);
        canA.add(16);
        canA.add(17);
        canA.add(18);
        canA.add(19);
        canA.add(20);
        canB.add(-2);
        canB.add(-1);
        canB.add(1);
        canB.add(2);
        canB.add(4);
        canB.add(5);
        canB.add(6);
        canB.add(7);
        canB.add(8);
        canB.add(9);
        canB.add(10);
        canB.add(11);
        canB.add(12);
        canB.add(13);
        canB.add(14);
        canB.add(15);
        canC.add(1);
        canC.add(3);
        canC.add(5);
        canC.add(7);
        canC.add(9);
        canC.add(11);
        canC.add(13);
        canC.add(15);
    }

    public void getin(String ss) {
        SafeOutput.println(String.format("IN-%d-%d-%s", id, fromFloor,ss));
    }

    public void getout(String ss,int floor) {
        fromFloor = floor;
        SafeOutput.println(String.format("OUT-%d-%d-%s", id, floor,ss));
    }

    public int getFromFloor() { return fromFloor; }

    public int getToFloor() { return toFloor; }

    public int getFirstToFloor(String type) {
        int temp = 30;
        int ans = 0;
        if (canA.contains(toFloor)) {
            if (type.equals("B")) {
                for (int i : canA) {
                    if (canB.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            } else if (type.equals("C")) {
                for (int i : canA) {
                    if (canC.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            } nxtele = "A";
        }
        else if (canB.contains(toFloor)) {
            if (type.equals("A")) {
                for (int i : canB) {
                    if (canA.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            } else if (type.equals("C")) {
                for (int i : canB) {
                    if (canC.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            } nxtele = "B";
        }
        else if (canC.contains(toFloor)) {
            if (type.equals("A")) {
                for (int i : canC) {
                    if (canA.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            } else if (type.equals("B")) {
                for (int i : canC) {
                    if (canB.contains(i) && Math.abs(i - toFloor) < temp) {
                        ans = i;
                        temp = Math.abs(i - toFloor);
                    }
                }
            }
            nxtele = "C";
        }
        return ans;
    }

    public String getNxtele() {
        return nxtele;
    }
}
