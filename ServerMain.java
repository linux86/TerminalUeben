package Aufgabe3;

import java.io.*;
 
public class ServerMain {
	
	private static final int PORT = 1234;
	private static final String LOGFILE = "log.txt";
	private static MySocketServer server;
	
	public static void main(String args[]) {
		try {
			server = new MySocketServer(PORT, LOGFILE);
			server.listen();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
