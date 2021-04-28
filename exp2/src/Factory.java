import java.util.ArrayList;

public class Factory {
    public Vehicle create(ArrayList<String> nows) {
        if (nows.get(1).equals("Car")) {
            int nowId = Integer.parseInt(nows.get(2));
            int nowPrice = Integer.parseInt(nows.get(3));
            int nowMS = Integer.parseInt(nows.get(4));
            return new Car(nowId, nowPrice, nowMS);
        }
        else if (nows.get(1).equals("Tractor")) {
            int nowId = Integer.parseInt(nows.get(2));
            int nowPrice = Integer.parseInt(nows.get(3));
            return new Tractor(nowId, nowPrice);
        }
        else if (nows.get(1).equals("Sprinkler")) {
            int nowId = Integer.parseInt(nows.get(2));
            int nowPrice = Integer.parseInt(nows.get(3));
            int nowMS = Integer.parseInt(nows.get(4));
            return new Sprinkler(nowId, nowPrice, nowMS);
        }
        else if (nows.get(1).equals("Rocket")) {
            int nowId = Integer.parseInt(nows.get(2));
            int nowPrice = Integer.parseInt(nows.get(3));
            return new Rocket(nowId, nowPrice);
        }
        else {
            int nowId = Integer.parseInt(nows.get(2));
            int nowPrice = Integer.parseInt(nows.get(3));
            return new Rotcart(nowId, nowPrice);
        }
    }
}
