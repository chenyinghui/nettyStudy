package normalNio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	
	public static void main(String[] args) throws IOException {
		int port = 8120;
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			System.err.println("TimeServer is started!");
			Socket socket = null;
			while(true){
				socket = serverSocket.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		}finally{
			if(serverSocket != null){
				serverSocket.close();
				serverSocket = null;
			}
		}
	}

}
