package com.runningforlife.jason.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * a handler class to handle client request
 */
public class RequestHandler implements Runnable{
	private static final String TAG = "RequestHandler";
	
	private Socket client;
	private BufferedReader reader;
	private PrintWriter writer;
	private Scanner scanner;
	
	public RequestHandler(Socket s){
		client = s;
		
		scanner = new Scanner(System.in);
	}

	public void run() {
		processRequets();
	}
	
	
	
	private void processRequets(){
		try{
			receive();
		} catch(IOException e){
			System.out.println("fail to read for IO error");
			e.printStackTrace();
		}

		try{
			reply();
		} catch(IOException e){
			System.out.println("fail to write for IO error");
			e.printStackTrace();
		}
		
		close();
	}
	
	private void receive() throws IOException{
		System.out.println(TAG + ": request from client address = " + client.getInetAddress()
		+ " port = " + client.getPort());

		reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		
		String line = reader.readLine();
		System.out.println(TAG + ": message from client = " + line);
	}
	
	private void reply() throws IOException{
		writer = new PrintWriter(new PrintStream(client.getOutputStream()));
		// reply to client
		writer.println("ok, I have received the message");
		// flush to the socket
		writer.flush();
	}

	private void close(){
		writer.close();
		try{
			reader.close();
			client.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
