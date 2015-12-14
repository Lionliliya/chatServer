package chatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server implements Runnable {

	Server(Socket a, ArrayList<DataOutputStream> stream) {
		this.cursoc = a;
		this.stream = stream;
	}

	boolean f = true;
	ArrayList<DataOutputStream> stream; /**Create a list of streams to send messages**/
	Socket cursoc;
	
/**
Description stream to communicate with 
one client**/
	@Override
	public void run() {
		try (DataInputStream in = new DataInputStream(cursoc.getInputStream());
				DataOutputStream out = new DataOutputStream(
						cursoc.getOutputStream())) {

			stream.add(out);
			String str = "";

			for ( ; f ; ) {
				try {
					str = in.readUTF();
					System.out.println(str);
					Iterator<DataOutputStream> itr = stream.iterator();
					for (; itr.hasNext();)
						itr.next().writeUTF(str);

				} catch (IOException e2) {
					stream.remove(out);
					System.out.println("Client disconnect"
							+ cursoc.getInetAddress());
					f = false;
					try {
						cursoc.close();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Client disconnect");
			System.out.println("2 exception");
		}
	}
}
