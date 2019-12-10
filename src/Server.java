
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Luca
 */
public class Server {

    public static MessageMap container = new MessageMap();
    public static HashMap<Socket, String> username = new HashMap<>();
    public static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server avviato!\nSocket del server: " + serverSocket);
        new Thread1().start();
        
        while (true){
            new ThreadServer(serverSocket.accept()).start();
        }   
    }

}
