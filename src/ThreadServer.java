
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
 * Server che legge
 *
 * @author Luca
 */
public class ThreadServer extends Thread {

    private Socket socket;
    private OutputStreamWriter strOut;
    private BufferedWriter buffer;
    private PrintWriter out;
    private boolean isUsed;
    private String clientName;

    public ThreadServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            strOut = new OutputStreamWriter(socket.getOutputStream());
            buffer = new BufferedWriter(strOut);
            out = new PrintWriter(buffer, true);
            out.println(socket.getInetAddress() + " has just connected.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            while (true) {
                out.println("Imposta un nickname: ");
                clientName = in.readLine();
                for (HashMap.Entry<Socket, String> check : Server.username.entrySet()) {
                    if (clientName.equals(check.getValue())) {
                        out.println("L'username è già in uso!");
                        isUsed = true;
                        break;
                    } else {
                        isUsed = false;
                    }
                }
                if (!isUsed) {
                    System.out.println("ok");
                    out.println("ok");
                    Server.container.addClient(clientName);
                    Server.username.put(socket, clientName);
                    break;
                }

            }

            while (true) {
                String msg = in.readLine();
                System.out.println(msg);
                Server.container.addMessage(clientName, msg);
            }

        } catch (IOException ex) {
            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
