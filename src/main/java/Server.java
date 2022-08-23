import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public void startServer() {

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            serverChannel.bind(new InetSocketAddress("localhost", 23334));
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0, bytesCount,
                            StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    System.out.println("Получено сообщение от клиента: " + msg);
                    socketChannel.write(ByteBuffer.wrap(("Сервер: " +
                            noGapsString(msg)).getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String noGapsString(String str) {
        while (str.contains(" ")) {
            String replace = str.replace(" ", "");
            str = replace;
        }
        return str;
    }
}
