public class Tray {
    private int num;
    private int value1;
    private int value2;

    Tray() {
        num = 0;
        value1 = 1;
    }

    public synchronized int get() {
        while (num == 0) {
            try {
                wait();
            } 
            catch (InterruptedException e) {
                ;
            }
        }
        notifyAll();
        int ans = value1;
        System.out.println("Consumer get:" + ans);
        if (num == 2) { value1 = value2; }
        num--;
        return ans;
    }

    public synchronized void put(int v) {
        while (num == 2) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                ;
            }
        }
        System.out.println("Producer put:" + v);
        if (num == 1) {
            value2 = v;
        }
        else if (num == 0) {
            value1 = v;
        }
        num++;
        notifyAll();
    }
}

