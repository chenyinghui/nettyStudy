package normalNio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{

	private Socket socket;
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); 
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String body = null;
			String currentTime = null;
			while(true){
				body = in.readLine();
				if(body == null){
					break;
				}
				currentTime = "Query Time".equalsIgnoreCase(body) ? new Date().toString() : "Bad Order";
				out.println(currentTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(this.socket != null){
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.socket = null;
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if(out != null){
				out.close();
				out = null;
			}
		}
	}

}
