package Aufgabe3;

import java.io.*;
import java.net.*;
 
public class MySocketServer {
	
	private ServerSocket socket;
	private int port;
	private String file;
	
	public MySocketServer(int port, String file) throws IOException {
		this.port = port;
		socket = new ServerSocket(port);
		this.file = file;
	}

	public void listen() {
		while(true) {
			try {
				
				
				File logFile = new File(file);
				FileWriter fileWriter = new FileWriter(logFile, true);		
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
			    PrintWriter serverlog = new PrintWriter(bufferedWriter);
			    serverlog.println("serverlog: Warten auf Verbindungen auf Port " + port);
			    
			    System.out.println("Server: listening on port " + port);
			    Socket newConnection = socket.accept(); // <-- wartet bis eingehende verbindung kommt.
			    
				MySocketServerConnection connection = new MySocketServerConnection(newConnection, serverlog);
				connection.start();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
