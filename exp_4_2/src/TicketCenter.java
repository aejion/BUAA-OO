public class TicketCenter implements Runnable{

    private TicketPool ticketPool;

    public TicketCenter(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public void run() {
        ticketPool.addTicket(20);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ticketPool.addTicket(10);
    }
}