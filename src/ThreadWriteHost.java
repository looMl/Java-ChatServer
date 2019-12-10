
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
 * Thread client che scrive
 *
 * @author Luca
 */
public class ThreadWriteHost implements Runnable {

    private Socket socket = null;

    /**
     *
     * @param socket
     */
    public ThreadWriteHost(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while (true) {
                userInput = stdIn.readLine();
                out.println(userInput);
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadWriteHost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
