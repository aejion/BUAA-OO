import com.oocourse.TimableOutput;

public class Person {
    private int fromFloor;
    private int toFloor;
    private int id;

    Person(int aa, int bb, int cc) {
        fromFloor = aa;
        toFloor = bb;
        id = cc;
    }

    public void getin(String ss) {
        TimableOutput.println(String.format("IN-%d-%d-%s", id, fromFloor,ss));
    }

    public void getout(String ss) {
        TimableOutput.println(String.format("OUT-%d-%d-%s", id, toFloor,ss));
    }

    public int getFromFloor() { return fromFloor; }

    public int getToFloor() { return toFloor; }
}
