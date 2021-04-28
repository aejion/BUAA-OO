public class Rocket extends Manned {
    private int People;

    Rocket(int id,int price) {
        super(id, price);
        People = 0;
    }

    public void run() {
        System.out.println("Wow! I can fly!");
    }

    public void getIn() {
        if (People == 0) {
            People = 1;
            System.out.println("Hello, Astronaut!");
        }
        else {
            System.out.println("Only one seat!");
        }
    }

    public void getOff() {
        if (People > 0) {
            People = People - 1;
            System.out.println("Wow! Stop dreaming and come back to earth ^_^");
        }
        else {
            System.out.println("Error");
        }
    }
}
