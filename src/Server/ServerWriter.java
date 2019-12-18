package Server;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server writer class
 *
 * @author Luca Landolfo
 */
public class ServerWriter extends Thread {

    private int size;
    private OutputStreamWriter osw;
    private BufferedWriter bw;
    private PrintWriter out;

    public ServerWriter() {
        this.size = ServerMain.container.getSize();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ServerMain.container) {
                if (ServerMain.container.getSize() > this.size) {
                    ServerMain.username.entrySet().forEach((check) -> {
                        try {
                            osw = new OutputStreamWriter(check.getKey().getOutputStream());
                        } catch (IOException ex) {
                            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        bw = new BufferedWriter(osw);
                        out = new PrintWriter(bw, true);
                        out.println(ServerMain.container.toString());
                    });
                    this.size++;
                }
            }
        }
    }

}
