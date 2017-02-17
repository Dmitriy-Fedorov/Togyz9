package server_T9;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ServerThread extends Thread{
	
	Socket socket;
	static int num=-1;
	
	ServerThread(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		InputStream inputStream;
		try {
			String message = null;
			inputStream = socket.getInputStream();
	        InputStreamReader inputStreamReader = new InputStreamReader(
	                inputStream);
	        BufferedReader inputFromClient = new BufferedReader(
	                inputStreamReader);
	        while((message = inputFromClient.readLine())!=null){
	        	System.out.println("client: "+message);
	        	getNum(message);
	        }
	        socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	static synchronized int getNum(String message){
		num = Integer.parseInt(message);
		return num;
	}
	
	static int getNum2(){
		
		return num;
	}

}
