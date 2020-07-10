import net.volodin.intercom.Intercom;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try (Intercom intercom = new Intercom("127.0.0.1", 8000)) {
            System.out.println("Connected to server!");
            String request = "Sacramento";
            intercom.writeLine(request);

            String response = intercom.readLine();
            System.out.println("Response: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
