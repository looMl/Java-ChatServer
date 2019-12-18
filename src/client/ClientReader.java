package client;

import Server.ServerWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client Reader
 *
 * @author Luca Landolfo
 */
public class ClientReader extends Thread {

    private Socket socket = null;

    /**
     *
     * @param socket
     */
    public ClientReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));

            while (true) {
                try {
                    System.out.println(in.readLine());
                } catch (SocketException ek) {
                    System.out.println("\nLost connection to the server...\nDisconnected.");
                    System.exit(0);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
