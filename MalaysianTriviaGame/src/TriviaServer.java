import java.net.*;
import java.io.*;

/**
 *
 * @author A188384
 */
public class TriviaServer {
   public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8888.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        TriviaProtocol nis = new TriviaProtocol();

        outputLine = nis.processInput(null);
        out.println(outputLine);

        while ((inputLine = in.readLine()) != null) {
             outputLine = nis.processInput(inputLine);
             out.println(outputLine);
             if (outputLine.equals("Bye bye! Jumpa lagi!"))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
    
}
