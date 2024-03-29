package client;

import java.net.*;
import java.io.*;

/**
 * Client main class
 *
 * @author Luca Landolfo
 */
public class ClientMain {

    public static void main(String[] args) throws IOException {

        String indirizzo = "localhost";

        try {

            Socket socket = new Socket(indirizzo, 5000);
            System.out.println("EchoClient: avviato");
            System.out.println("Socket del client: " + socket);

            new ClientReader(socket).start();
            new ClientWriter(socket).run();

        } catch (UnknownHostException e) {
            System.err.println("Host non riconosciuto... " + indirizzo);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Non riesco ad avere I/O per la connessione a: " + indirizzo);
            System.exit(1);
        }
    }
}
