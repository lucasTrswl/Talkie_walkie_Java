import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            
            System.out.println("Connecté au serveur: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Entrez votre nom d'utilisateur: ");
            String username = userInput.readLine();

            Thread messageListener = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("");
                        System.out.println("Reçu de " + serverResponse);
                        System.out.print("Entrez un message: ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            messageListener.start();

            String input;
            while (true) {
                System.out.print("Entrez un message: ");
                input = userInput.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(username + ": " + input);
            }

            userInput.close();
            in.close();
            out.close();
            socket.close();
        }
    }
}
