import java.util.ArrayList;

public class Server implements Observerable {
    private ArrayList<Observer> arr = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        arr.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        arr.remove(observer);
    }

    @Override
    public void notifyObserver(String msg) {
        System.out.println("server:" + msg);
        for (Observer i : arr) {
            i.update(msg);
        }
    }
}
