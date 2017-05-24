package com.github.runningforlife.concurrency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * multiple thread read/write a file
 */

public class ReaderWriterLock {
	private static final String WRITE_FILE_NAME = "tmp.txt";
	private static final String READ_FILE_PATH = "/home/jason/Document/Java/HelloWorld.java";
	
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static String userDir = System.getProperty("user.dir");
	private static Lock rLock = rwLock.readLock();
	private static Lock wLock = rwLock.writeLock();
	
	public ReaderWriterLock() throws IOException{
		File rf = new File(READ_FILE_PATH);
		File wf = new File(userDir,WRITE_FILE_NAME);
		
		if(!rf.exists()){
			throw new IllegalStateException("read file does not exist!!!");
		}
		
		if(!wf.exists()){
			wf.createNewFile();
			wf.setExecutable(true);
			wf.setReadable(true);
			wf.setWritable(true);
		}
	}
	
	public class ReadWriteRunnable implements Runnable{
		public void run(){
			try {
				byte[] buf = readFile();
				writeToFile(buf);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private byte[] readFile() throws FileNotFoundException{
		FileInputStream fis = null;
		byte[] buf = new byte[1024];
		try{
			rLock.lock();
			System.out.println("read lock got by thread " + Thread.currentThread().getName());
			fis = new FileInputStream(READ_FILE_PATH);
			fis.read(buf,0,1024);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			rLock.unlock();
		}
		
		return buf;
	}

	private void writeToFile(byte[] data) throws FileNotFoundException{
		// write it to another file
		FileOutputStream fos = new FileOutputStream(new File(userDir,WRITE_FILE_NAME));
		try{
			wLock.lock();
			System.out.println("write lock got by thread " + Thread.currentThread().getName());
			fos.write(data,0,data.length);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			wLock.unlock();
		}
	}
}
