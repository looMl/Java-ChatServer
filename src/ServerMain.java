
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Main server class
 * @author Luca
 */
public class ServerMain {

    public static MessageMap container = new MessageMap();
    public static HashMap<Socket, String> username = new HashMap<>();
    public static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server running!");
        new ServerWriter().start();

        while (true) {
            new ServerReader(serverSocket.accept()).start();
        }
    }

}
