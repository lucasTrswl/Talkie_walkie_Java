import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Connecté au serveur: " + SERVER_ADDRESS + ":" + SERVER_PORT);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String input;
        while (true) {
            System.out.print("Entrez un message: ");
            input = userInput.readLine();
            if (input == null || input.equalsIgnoreCase("exit")) {
                break;
            }

            out.println(input);
            String serverResponse = in.readLine();
            System.out.println("Serveur: " + serverResponse);
        }

        userInput.close();
        in.close();
        out.close();
        socket.close();
    }
}
