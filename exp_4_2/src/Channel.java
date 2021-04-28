import java.util.ArrayList;

public class Channel {
    private final TicketWindow[] threadPool;
    private final ArrayList<Request> requestQueue;
    private TicketPool ticket;
    //以上两个变量可以用静态数组或LinkedList等任何容器实现，可以修改构造方法中的内容

    public Channel(int threadNum, TicketPool ticketPool) { //构造方法(利用数组实现)
        threadPool = new TicketWindow[threadNum];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new TicketWindow("TicketWindow-" + i, this, ticketPool);
        }
        requestQueue = new ArrayList<>();
        ticket = ticketPool;
    }

    public void startWorkers() { //启动售票窗口线程
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();
        }
    }

    public synchronized void putRequest(Request request) {
        requestQueue.add(request);
    } //TicketBuyer调用，生成请求并放入requestQueue

    public Request takeRequest() {
        if (requestQueue.isEmpty()) { return null; }
        Request temp = requestQueue.get(0);
        requestQueue.remove(temp);
        return temp;
    } //TicketWindow调用，获取requestQueue中的请求

    public synchronized boolean has() {
        return ticket.hasTicket();
    }
    //可以根据需要增加变量或者方法，也可以修改已有方法的内容，但不能修改参数和返回值
}