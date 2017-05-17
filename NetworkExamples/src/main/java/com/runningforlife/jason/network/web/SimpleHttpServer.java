package com.runningforlife.jason.network.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * a simple http server to return a page to client
 */

public class SimpleHttpServer {
	private static final int MAX_THREADS_NUMBER = 5;
	private static final int PORT = 8080;
	
	private String mRootPath;
	private ExecutorService mExecutor;
	// client count
	private static AtomicInteger sClients = new AtomicInteger(0);
	private ServerSocket mServer;
	
	public static  void main(String args[]){
		if(args.length != 1){
			throw new IllegalArgumentException("http server root dir should be given");
		}
		
		SimpleHttpServer server = new SimpleHttpServer(args[0]);
		try {
			server.start();
		} catch (IOException e) {
			System.out.println("fail to start http server");
			e.printStackTrace();
		}
	}
	
	public SimpleHttpServer(String path){
		mRootPath = path;
		mExecutor = Executors.newFixedThreadPool(MAX_THREADS_NUMBER);
	}
	
	
	public void start() throws IOException{
		mServer = new ServerSocket(PORT);

		while(true){
			Socket socket = mServer.accept();
			
			System.out.println("current clients " + sClients.incrementAndGet());
			mExecutor.submit(new HttpRequestHandler(mRootPath,socket));
		}
	}

}
