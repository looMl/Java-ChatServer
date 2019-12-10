
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains some useful methods
 * 
 * @author Luca Landolfo
 */
public class Tools {

    private OutputStreamWriter osw;
    private BufferedWriter bw;
    private PrintWriter out;

    public Tools() {
    }

    public boolean isCommand(String str) {
        if (str.length() > 1) {
            return str.charAt(0) == '/';
        } else {
            return false;
        }
    }

    public void sendPM(Socket dest, String user, String msg) {
        try {
            osw = new OutputStreamWriter(dest.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        bw = new BufferedWriter(osw);
        out = new PrintWriter(bw, true);
        out.println(user + " whispered you: " + msg);
    }

}
