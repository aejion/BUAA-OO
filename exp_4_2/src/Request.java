import java.util.Random;

public class Request {
    private final String name;
    private final int number;
    private static final Random random = new Random();

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute(int tNumber) {
        System.out.println(Thread.currentThread().getName()
            + " executes " + this + ", ticketnumber: " + tNumber);
        try {
            Thread.sleep(random.nextInt(200));
        } catch (InterruptedException e) {
        }
    }

    public String toString() {
        return "[ Request from " + name + " No." + number + " ]";
    }
}