public class Tractor extends Worker {

    Tractor(int id, int price) {
        super(id, price);
    }

    public void run() {
        System.out.println("Wow, I can Run and Plough all day!");
    }

    public int getPrice() {
        return super.getPrice() * 2;
    }
    
    public void work() {
        System.out.println("I have an engine for pulling wagons or ploughs!");
    }
}
