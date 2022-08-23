import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public void startClient() {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost",
                23334);
        try (final SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(socketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg;
            while (true) {
                System.out.println("Enter message for server...");
                msg = scanner.nextLine();
                if ("end".equals(msg)) break;
                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
