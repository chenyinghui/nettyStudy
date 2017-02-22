package normalNio;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

	public static void main(String[] args) throws IOException {
		String host = "10.0.1.172";
		int port = 8120;
		Socket socket = new Socket(host, port);
		PrintWriter out = null;
		BufferedReader in = null;
		try{
			out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
			out.println("Query Time");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.err.println("Response from Server, Now is : " + in.readLine());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			socket.close();
			socket = null;
			if(in != null){
				in.close();
				in  = null;
			}
			if(out != null){
				out.close();
				out = null;
			}
		}
	}
}
