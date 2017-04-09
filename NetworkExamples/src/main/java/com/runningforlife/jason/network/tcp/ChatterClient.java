package com.runningforlife.jason.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * chatter client to talk with server
 */

public class ChatterClient {
	private static final String TAG = "ChatterClient";
	
	private Socket socket;
	// get input from user
	private Scanner scanner;
	private BufferedReader reader;
	private PrintWriter writer;
	
	
	public static void main(String args[]){
		if(args.length != 2){
			System.out.println("Usage: java ChatterClient <host name> <port number>");
			System.exit(1);
		}
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		ChatterClient client = new ChatterClient(host,port);
		client.start();
	}
	
	public ChatterClient(String host, int port){
		try {
			socket = new Socket(host,port);
			scanner = new Scanner(System.in);
			// read data from socket
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("failt to create socket for host is unknow");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("fail to create socket for IO error");
			e.printStackTrace();
		}
		
	}
	
	public void start(){
		// send message to server
		send();
		// read from server
		receive();
	
		close();
	}
	
	private void send(){
		String line = scanner.nextLine();
		//String line = "hi,this is the client no.1";
		System.out.println("send message to server = " + line);
		writer.println(line);
		writer.flush();
	}
	
	private void receive(){
		try {
			String line = reader.readLine();
			System.out.println("message from server = " + line);
		} catch (IOException e) {
			System.out.println("fail to read from server for IO error");
			e.printStackTrace();
		}
	}
	
	private void close(){
		try{
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		writer.close();
		scanner.close();
	}
}
