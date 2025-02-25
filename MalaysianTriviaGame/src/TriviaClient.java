import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author A188384
 */
public class TriviaClient extends JFrame implements ActionListener{
    public static void main(String[] args) throws IOException {

        Socket nisSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            nisSocket = new Socket("localhost", 8888);
            out = new PrintWriter(nisSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(nisSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("tata titi tutu"))
                break;
		    
            fromUser = stdIn.readLine();
	    if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
	    }
        }

        out.close();
        in.close();
        stdIn.close();
        nisSocket.close();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

