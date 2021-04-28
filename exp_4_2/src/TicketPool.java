public class TicketPool {

    private int ticketNum;
    private int ticketNo = 1;

    public synchronized void addTicket(int num) {
        ticketNum += num;
    }

    public synchronized boolean hasTicket() {
        return ticketNo <= ticketNum;
    }

    public synchronized int getTicket() {
        if (ticketNo <= ticketNum) {
            return ticketNo++;
        } else {
            return 0;
        }
    }
}