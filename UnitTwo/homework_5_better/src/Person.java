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

    public void getin() {
        TimableOutput.println(String.format("IN-%d-%d", id, fromFloor));
    }

    public void getout() {
        TimableOutput.println(String.format("OUT-%d-%d", id, toFloor));
    }

    public int getFromFloor() { return fromFloor; }

    public int getToFloor() { return toFloor; }
}
