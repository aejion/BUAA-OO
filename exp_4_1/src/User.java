public class User implements Observer {
    private String name;

    User(String name1) {
        name = name1;
    }

    @Override
    public void update(String msg) {
        System.out.println(name + ":" + msg);
    }
}
