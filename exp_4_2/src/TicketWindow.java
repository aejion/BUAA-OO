import java.util.Random;

public class TicketWindow extends Thread {
    private final Channel channel;
    private TicketPool ticketPool;
    private static final Random random = new Random();

    public TicketWindow(String name, Channel channel, TicketPool ticketPool) {
        super(name);
        this.channel = channel;
        this.ticketPool = ticketPool;
    }

    public void run() {
        while (ticketPool.hasTicket()) {
            Request temp = channel.takeRequest();
            if (temp != null) {
                temp.execute(ticketPool.getTicket());
            }
            else {
                try {
                    Thread.sleep(random.nextInt(200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } 
}