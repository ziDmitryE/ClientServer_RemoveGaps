public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        Client client = new Client();

        new Thread(null, server::startServer, "Server").start();
        new Thread(null, client::startClient, "Client").start();
    }
}
