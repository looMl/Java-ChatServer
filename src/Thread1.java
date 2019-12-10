
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server writer
 *
 * @author Luca
 */
public class Thread1 extends Thread {

    private int size;
    private OutputStreamWriter osw;
    private BufferedWriter bw;
    private PrintWriter out;

    public Thread1() {
        this.size = Server.container.getSize();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Server.container) {
                if (Server.container.getSize() > this.size) {
                    Server.username.entrySet().forEach((check) -> {
                        try {
                            osw = new OutputStreamWriter(check.getKey().getOutputStream());
                        } catch (IOException ex) {
                            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        bw = new BufferedWriter(osw);
                        out = new PrintWriter(bw, true);
                        out.println(Server.container.toString(check.getValue()));
                    });
                    this.size++;
                }
            }
        }
    }

}
