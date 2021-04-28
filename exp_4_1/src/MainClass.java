public class MainClass {
    public static void main(String[] args) {
        Server server = new Server(); //该类实现Observerable接口

        Observer user1 = new User("user1"); //该类实现Observer接口
        Observer user2 = new User("user2"); //该类实现Observer接口

        server.addObserver(user1);
        server.addObserver(user2);
        server.notifyObserver("北航的OO课使用Java语言。");

        server.removeObserver(user1);
        server.notifyObserver("Java是世界上最好用的语言！");
    }
}
