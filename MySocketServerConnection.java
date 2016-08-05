package Aufgabe3;

import java.io.*;
import java.net.*;
 
public class MySocketServerConnection extends Thread {
	
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter showOnPage;
	private PrintWriter serverlog;
	
	public MySocketServerConnection(Socket socket, PrintWriter serverlog) throws IOException {
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		showOnPage = new PrintWriter(socket.getOutputStream()); // <-- Senden um die Seite anzeigen zu lassen
		this.serverlog = serverlog;
		serverlog.println("serverlog: Verbindung akzeptiert.");
	}
	
	// Methode um das HTML File aus der Request zu lesen
	public File getFile() throws IOException {
		String request = "";
		try {
			request = reader.readLine();
			System.out.println("Client: " + request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int endOfFile = 0;

		for (int i = 4; i < request.length(); i++) {
			if (request.charAt(i) == ' ') {
				endOfFile = i;
			}
		}
		String path = request.substring(5, endOfFile);
		serverlog.println("serverlog: Laden von localhost:1234/" + path);
		
		File file = new File(path);
		
		return file;
	}
	
	// Methode für die Ausgabe auf der Seite
	public void outputFile() throws IOException {
		        
        try {
        	reader = new BufferedReader(new FileReader(getFile()));
        	
	        do {
	        	showOnPage.println(reader.readLine());
			} while (reader.readLine() != null);
	        
	        serverlog.println("serverlog: Laden erfolgreich");
	        
		} catch (FileNotFoundException e1) {
			showOnPage.print("<h1>Error 404</h1>");
			serverlog.println("serverlog: Error 404 - Seite gibt es nicht");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		
		showOnPage.println("HTTP/1.0 200 OK");
		showOnPage.println("Content-Type: text/html");
		showOnPage.println("Server: Bot");
		showOnPage.println("");
        
        try {
			outputFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        showOnPage.flush();
	
		try {
			showOnPage.close();
			reader.close();
			serverlog.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void run() {
		send();
	}
}
