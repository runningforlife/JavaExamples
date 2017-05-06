package com.github.runningforlife.concurrency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * a class to show thread state
 *
 */
public class ThreadWaitNotify 
{
	private static List<File> mAllFiles = new ArrayList<File>();
	
	private static final String PATH_USR = "/usr";
	private static final String PATH_PROC = "/proc";
	private static final String PATH_ETC = "/etc";
	private static final String PATH_SAVE = "/tmp/temp.txt";
	
	
	enum FilePath{
		USR(PATH_USR),
		PROC(PATH_PROC),
		ETC(PATH_ETC);
		
		String path;
		
		FilePath(String p){
			path = p;
		}
		
		public String path(){
			return path;
		}
	}
	
    public static void main( String[] args)
    {
    	
    	ThreadFactory factory = new MyThreadFactory();
    	
    	factory.newThread(new SaveRunnable())
    		   .start();
    	
    	for(FilePath path : FilePath.values()){
    		factory.newThread(new ListFileRunnable(path.path()))
    			   .start();
    	}
    }
   
   /**
    *  use to list file 
    */
    static class ListFileRunnable implements Runnable{

    	private String path;
    	
    	public ListFileRunnable(String path){
    		this.path = path;
    	}
    	
		public void run() {
    		ThreadProfiler.start();
			listFile(path);
			//SleepUtils.sleep(1);
			ThreadProfiler.end();
			System.out.println("thread " + Thread.currentThread().getName() + " execution time " + ThreadProfiler.getTime());
		}
    }
    
    /**
     * save file information to a file
     */
    static class SaveRunnable implements Runnable{
    	private static final ThreadLocal<Integer> threadCount = new ThreadLocal<Integer>(){
    		protected Integer initialValue(){
    			return 0;
    		}
    	};
    	private volatile static int count = 0;
    	
    	public void run(){
    		// keep running 
			ThreadProfiler.start();
    		while(threadCount.get() < FilePath.values().length){
	    		try {
					threadCount.set(++count);
					System.out.println("current executed thread = " + threadCount.get());
					saveFileInfo();
				} catch (InterruptedException e) {
					System.out.println("Fail to save file information");
					e.printStackTrace();
					break;
				}
    		}
			ThreadProfiler.end();
        	System.out.println("thread save execution time = " + ThreadProfiler.getTime());
    	}
    	
    }
    
    public static void listFile(final String path){
    	if(path == null || path.isEmpty()){
    		throw new IllegalArgumentException("path should not be null or empty");
    	}
    	
    	synchronized(mAllFiles){
	    	System.out.println("listFile():" + path);
	    	File file = new File(path);
	    	
	    	if(file.exists() && file.isDirectory()){
	    		File[] files = file.listFiles();
	    		mAllFiles.addAll(Arrays.asList(files));
	    		// notify to save
	    		mAllFiles.notifyAll();
	    		
	    		System.out.println("listFile(): file number = " + files.length);
	    	}
    	}
    }
    
    static void saveFileInfo() throws InterruptedException{
    	synchronized(mAllFiles){
    		while(mAllFiles.isEmpty()){
    			mAllFiles.wait(3000);
    		}
    		
    		System.out.println("saveFileInfo(): file number " + mAllFiles.size());
    		
    		String rootDir = null;
    		String path = mAllFiles.get(0).getAbsolutePath();
    		if(path.startsWith(PATH_USR)){
    			rootDir = PATH_USR;
    		}else if(path.startsWith(PATH_PROC)){
    			rootDir = PATH_PROC;
    		}else if(path.startsWith(PATH_ETC)){
    			rootDir = PATH_ETC;
    		}
    		
    		File file = new File(PATH_SAVE);
    		if(!file.exists()){
    			file.mkdirs();
    			file.setWritable(true);
    		}
    		
    		FileWriter writer = null;
    		PrintWriter print = null;
    		try {
				writer = new FileWriter(file,true);
				print = new PrintWriter(writer);
				
				print.println("----------" + rootDir + "--------");
				for(File f : mAllFiles){
					print.println(f.getAbsolutePath());
				}
				
				print.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(print != null){
					print.close();
				}
				
				if(writer != null){
					try {
						writer.close();
					} catch (IOException e) {
						// nothing to do
					}
				}
				
				mAllFiles.clear();
				// sleep for a while
				SleepUtils.sleep(1);
			}
    	}
    }
}
