package com.github.runningforlife.concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * use pipe to do thread communication
 */

public class ThreadPipe {
	
	public static void main(String[] args) throws IOException{
		PipedWriter writer = new PipedWriter();
		PipedReader reader = new PipedReader();
		
		writer.connect(reader);
		
		Thread echo = new Thread(new Echo(reader));
		echo.start();
		
		byte[] ch = new byte[100];
		try{
			System.in.read(ch,0,100);
			writer.write(new String(ch));
			writer.flush();
		}finally{
			writer.close();
		}
	}

	
	static class Echo implements Runnable{
		private PipedReader reader;
		
		public Echo(PipedReader r){
			reader = r;
		}

		public void run() {
			int recv = 0;
			
			char[] buf = new char[100];
			try{
				while((recv = reader.read(buf,0,100)) != -1){
					System.out.print(new String(buf));
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
}
