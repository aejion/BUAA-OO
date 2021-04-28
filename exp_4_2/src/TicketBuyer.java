import java.util.Random;

public class TicketBuyer extends Thread {
    private final Channel channel;
    private static final Random random = new Random();

    public TicketBuyer(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    public void run() {
        int i = 0;
        while (channel.has()) {
            channel.putRequest(new Request(getName(), i));
            i++;
            try {
                Thread.sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}