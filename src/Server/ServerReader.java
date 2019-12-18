package Server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
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
                    tool.userMessage(this.clientName, "connected");
                    break;
                }
            }

            while (true) {
                String msg = in.readLine();
                if (!msg.isEmpty()) {
                    if (tool.isCommand(msg)) {

                        if (msg.equals("/quit")) {
                            tool.userMessage(this.clientName, "disconnected");
                            ServerMain.username.remove(this.socket);
                            ServerMain.container.remove(this.clientName);
                            break;

                        } else if (msg.contains("/msg")) {
                            String[] command = msg.split(" ", 3);
                            if (command.length == 2) {
                                out.println("Server> The message is missing!");
                            } else {
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
                        }
                    } else {
                        ServerMain.container.addMessage(clientName, msg);
                    }
                }
            }
            //If it exists from the while(true) it means that the user left.
            //So we have to close her/his socket.
            this.socket.close();

        } catch (SocketException se) {
            System.out.println(clientName + " has disconnected from the server");
        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
