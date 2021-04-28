public class Rotcart extends Manned{
    private int people;

    Rotcart(int id, int price) {
        super(id, price);
        people = 0;
    }

    public void run() {
        System.out.println("Wow, I can Run and Plough all day!");
    }

    public int getPrice() {
        return super.getPrice();
    }

    public void work() {
        System.out.println("I have an engine for pulling wagons or ploughs!");
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
}
