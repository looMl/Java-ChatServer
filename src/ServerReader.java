
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
 * Server Reader
 *
 * @author Luca Landolfo
 */
public class ServerReader extends Thread {

    private final Socket socket;
    private OutputStreamWriter strOut;
    private BufferedWriter buffer;
    private PrintWriter out;
    private boolean isUsed;
    private String clientName;
    private final Tools tool;

    public ServerReader(Socket socket) {
        this.socket = socket;
        this.tool = new Tools();
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
                out.println("Server> Insert a nickname: ");
                this.clientName = in.readLine();
                for (HashMap.Entry<Socket, String> check : ServerMain.username.entrySet()) {
                    if (this.clientName.equals(check.getValue())) {
                        out.println("Server> The username is already used!");
                        isUsed = true;
                        break;
                    } else {
                        isUsed = false;
                    }
                }
                if (!isUsed) {
                    ServerMain.container.addClient(this.clientName);
                    ServerMain.username.put(this.socket, this.clientName);
                    break;
                }
            }

            while (true) {
                String msg = in.readLine();
                if (!msg.isEmpty()) {
                    if (tool.isCommand(msg)) {
                        
                        if (msg.equals("/quit")) {
                            out.println("Server> " + this.clientName + " disconnected from your channel.");
                            ServerMain.username.remove(this.socket);
                            ServerMain.container.remove(this.clientName);

                        } else if (msg.contains("/msg")) {
                            String[] command = msg.split(" ");
                            Boolean found = false;
                            for (HashMap.Entry<Socket, String> check : ServerMain.username.entrySet()) {
                                if (command[1].trim().equals(check.getValue())) {
                                    tool.sendPM(check.getKey(), this.clientName, command[2]);
                                    found = true;
                                }
                            }
                            if (!found) {
                                out.println("Server> The user doesn't exist!");
                            }

                        }
                    } else {
                        ServerMain.container.addMessage(clientName, msg);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
