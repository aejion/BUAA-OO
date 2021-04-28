public class Car extends Manned {
    private int maxSpeed;

    Car(int id,int price,int maxSpeed) {
        super(id,price);
        this.maxSpeed = maxSpeed;
    }

    public void run() {
        System.out.println("Wow, I can Run (maxSpeed:" + maxSpeed + ")!");
    }

    public int getPrice() {
        int ret = 0;
        if (this.maxSpeed >= 1000) { ret = 1000; }
        return ret + super.getPrice();
    }
}
