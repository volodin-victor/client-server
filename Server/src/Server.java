import net.volodin.intercom.Intercom;

import java.io.IOException;
import java.net.ServerSocket;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000);) {
            System.out.println("Server started!");
            try (Intercom intercom = new Intercom(serverSocket)) {
                String request = intercom.readLine();
                if (request == null) {
                    request = "";
                }
                String response = "Hello from server : " + request;
                intercom.writeLine(response);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
