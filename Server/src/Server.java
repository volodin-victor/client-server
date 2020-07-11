import net.volodin.intercom.Intercom;

import java.io.IOException;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000);) {
            System.out.println("Server started!");
            while (true) {

                Intercom intercom = new Intercom(serverSocket);
                // Creating dedicated thread for each client connection
                new Thread(() -> {
                    String request = intercom.readLine();
                    if (request == null) {
                        request = "";
                    }
                    try {
                        // Modeling server work delay 4 sec
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String response = "Hello from server : " + request;
                    intercom.writeLine(response);
                    try {
                        intercom.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
