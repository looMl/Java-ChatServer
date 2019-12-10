
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client writer
 *
 * @author Luca Landolfo
 */
public class ClientWriter implements Runnable {

    private Socket socket = null;
    private String userInput;

    /**
     *
     * @param socket
     */
    public ClientWriter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                userInput = stdIn.readLine();
                if (userInput.equals("/quit")) {
                    this.socket.close();
                    stdIn.close();
                    osw.close();
                    System.out.println("Disconnected.");
                    break;
                } else {
                    out.println(userInput);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
