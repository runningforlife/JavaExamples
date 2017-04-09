package com.runningforlife.jason.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * a chatter server to accept chatter requests
 */

public class ChatterServer {
	private static final String TAG = "ChatterServer";
	
	private static final int MAX_THREAD_SIZE = 5;
	private ExecutorService  executor;
	private ServerSocket socket;

	
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Usage: java ChatterServer <port number>");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		ChatterServer server = new ChatterServer(port);
		server.start();
	}
	
	public ChatterServer(int port){
		try {
			socket = new ServerSocket(port);
			// init thread pool
			executor = Executors.newFixedThreadPool(MAX_THREAD_SIZE, SimpleThreadFactory.getInstance());
		} catch (IOException e) {
			System.out.println("cannot create server socket on port = " + port);
			e.printStackTrace();
		}
	}
	
	public void start(){
		System.out.println("chatter server is starting");
		
		try{
			for(;;){
				Socket client = socket.accept();
				// a new request is coming
				executor.execute(new RequestHandler(client));
			}
		} catch (IOException e) {
			System.out.println("socket I/O error");
			e.printStackTrace();
		}finally{
			if(socket != null){
				try{
					socket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

	}
}
