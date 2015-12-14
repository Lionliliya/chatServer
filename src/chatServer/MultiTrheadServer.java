package chatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiTrheadServer {
	public static void main(String[] args) {
/**Array of output streams to clients who receive messages from the server**/		
		ArrayList<DataOutputStream> stream = new ArrayList<DataOutputStream>();
		System.out.println("Server start");
		
		try (ServerSocket soc = new ServerSocket(20000)) {
			
/**Create a server socket and creating a client when connection is start, 
 * then the "reset" it in a separate thread**/
			
			for (;true;) {
				
/**Accept () method implements a blocking call that will be
expected from the client connection to be initialized, and then returns
Socket object which will interact with
client**/
				Socket clisoc = soc.accept();
				Server cliserv = new Server(clisoc, stream);
				Thread t = new Thread(cliserv);
				t.setDaemon(true);
				t.start();
			}
		} catch (IOException e) {
			System.out.println("Error to server Socket Open!!!");
		}
	}
}