public abstract class Worker extends Vehicle {

    Worker(int id, int price) {
        super(id, price);
    }

    public abstract void work();
    
    public int getPrice() {
        return super.getPrice();
    }
}