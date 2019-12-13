
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

    public void userMessage(String username, String option) {
        String message = checkOption(option);
        ServerMain.username.entrySet().forEach((check) -> {
            try {
                osw = new OutputStreamWriter(check.getKey().getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            bw = new BufferedWriter(osw);
            out = new PrintWriter(bw, true);
            out.println("Server> " + username + message);
        });
    }

    private String checkOption(String option) {
        String message;
        if (option.equals("disconnected")) {
            return message = " disconnected from your channel.";
        } else {
            return message = " has connected.";
        }
    }

}
