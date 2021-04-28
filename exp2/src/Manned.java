public abstract class Manned extends Vehicle {
    private int people;

    Manned(int id,int price) {
        super(id, price);
        people = 0;
    }
    
    public void getIn() {
        people = people + 1;
        System.out.println("Wow! We have a new passenger!");
    }

    public void getOff() {
        if (people > 0) {
            people = people - 1;
            System.out.println("Wow! A passenger arrived his or her destination!");
        }
        else {
            System.out.println("Wow! Only Driver!");
        }
    }

    public int getPrice() {
        return super.getPrice();
    }
}