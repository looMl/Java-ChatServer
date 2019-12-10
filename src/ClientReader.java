
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread client che legge
 *
 * @author Luca
 */
public class ClientReader extends Thread {

    private Socket socket = null;

    /**
     *
     * @param socket
     */
    public ClientReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));

            while (true) {
                System.out.println(in.readLine());
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
