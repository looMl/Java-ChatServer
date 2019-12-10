
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerMain che legge
 *
 * @author Luca
 */
public class ServerReader extends Thread {

    private Socket socket;
    private OutputStreamWriter strOut;
    private BufferedWriter buffer;
    private PrintWriter out;
    private boolean isUsed;
    private String clientName;

    public ServerReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            strOut = new OutputStreamWriter(this.socket.getOutputStream());
            buffer = new BufferedWriter(strOut);
            out = new PrintWriter(buffer, true);
            out.println(this.socket.getInetAddress() + " has just connected.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));

            while (true) {
                out.println("Insert a nickname: ");
                clientName = in.readLine();
                for (HashMap.Entry<Socket, String> check : ServerMain.username.entrySet()) {
                    if (clientName.equals(check.getValue())) {
                        out.println("The username is already used!");
                        isUsed = true;
                        break;
                    } else {
                        isUsed = false;
                    }
                }
                if (!isUsed) {
                    ServerMain.container.addClient(clientName);
                    ServerMain.username.put(this.socket, clientName);
                    break;
                }
            }

            while (true) {
                String msg = in.readLine();
                if (!msg.equals("")) {
                    ServerMain.container.addMessage(clientName, msg);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
