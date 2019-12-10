
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread client che legge
 * @author Luca
 */
public class ThreadHost extends Thread{
    
    private Socket socket = null;
    
    /**
     *
     * @param socket
     */
    public ThreadHost(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
                  
        try {       
            BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream()));
            
            while(true){
                System.out.println(in.readLine());
            }  
            
        } catch (IOException ex) {
            Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
