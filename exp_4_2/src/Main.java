public class Main {
    public static void main(String[] args) {
        TicketPool ticketPool = new TicketPool();
        TicketCenter ticketCenter = new TicketCenter(ticketPool);
        Thread tCenter = new Thread(ticketCenter);
        tCenter.start();
        Channel channel = new Channel(5, ticketPool);
        channel.startWorkers();
        TicketBuyer[] buyers = new TicketBuyer[3];
        for(int i = 0; i < 3; i++) {
            buyers[i] = new TicketBuyer("Buyer" + i, channel);
            buyers[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}