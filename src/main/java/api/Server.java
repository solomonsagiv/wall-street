package api;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public Socket getMySocket ( int port ) {
		try {
			ServerSocket serverSocket = new ServerSocket ( port );
			return serverSocket.accept ( );
		} catch ( Exception e ) {
			// TODO: handle exception
		}
		return null;
	}

	// Recieve stream
	@SuppressWarnings( "resource" )
	public Object reciever ( Socket socket ) {
		ObjectInputStream inputStream;
		try {
			inputStream = new ObjectInputStream ( socket.getInputStream ( ) );
			try {
				return inputStream.readObject ( );
			} catch ( ClassNotFoundException e ) {
				e.printStackTrace ( );
			}
		} catch ( IOException e ) {
			e.printStackTrace ( );
		}
		return null;

	}

	// Send stream
	public void send ( Object object, Socket socket ) {
		PrintStream printStream;
		try {
			printStream = new PrintStream ( socket.getOutputStream ( ) );
			printStream.println ( object );
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace ( );
		}
	}
}
