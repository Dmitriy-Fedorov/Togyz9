package server_T9;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class T9_server {
	
	int port = 60080;
	static int num=-1;
    static ServerSocket serverSocket;
    private static Scanner in;
    private static boolean isPrevious = false;
    

	T9_server(){
		
	}
	
	T9_server(int port_number){
		port = port_number;
	}
	
	
	
	void startServer(){
		t1.start();
	}
	
	
	
	static int getHod(){
		return num;
	}
	
	static void setPreviousTo(boolean a){
		isPrevious = a;
	}
	
	static boolean isIntegerNew(){
		return isPrevious;
	}
	
	void ser() throws IOException{
		 while (true) {
			 System.out.println("sdf");
	            //Socket connectionSocket;
				
					Socket connectionSocket = serverSocket.accept();
					// for taking input from client
		            InputStream inputStream = connectionSocket.getInputStream();
		            InputStreamReader inputStreamReader = new InputStreamReader(
		                    inputStream);
		            BufferedReader inputFromClient = new BufferedReader(
		                    inputStreamReader);
		            // for giving output to the client.
		            OutputStream outputStream = connectionSocket.getOutputStream();
		            // output to client, to send data to the server
		            DataOutputStream dataOutputStream = new DataOutputStream(
		                    outputStream);
					// get output from server

		            String readingLineFromClientSocket = inputFromClient.readLine();
		            // sending data to client
		            System.out.println(readingLineFromClientSocket);
		            //String modified = doOperation(readingLineFromClientSocket);
		            num = Integer.parseInt(readingLineFromClientSocket);
		            isPrevious = true;
		            // send data to client
		            dataOutputStream.writeBytes(num + "\n");

	        }
		 
		 
		 
	}
	
	
	void runServer() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("server is running");
		//while(true){
			Socket connectionSocket = serverSocket.accept();
			new ServerThread(connectionSocket).start();
		//}

		
	}
	
    
    private Thread t1 = new Thread(new Runnable(){

		@Override
		public void run() {
			
			try {
				ser();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	});
	
   


}
