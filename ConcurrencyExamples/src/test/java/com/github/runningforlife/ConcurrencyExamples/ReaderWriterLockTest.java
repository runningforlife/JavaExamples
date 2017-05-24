package com.github.runningforlife.ConcurrencyExamples;

import java.io.IOException;

import com.github.runningforlife.concurrency.MyThreadFactory;
import com.github.runningforlife.concurrency.ReaderWriterLock;

import junit.framework.TestCase;

public class ReaderWriterLockTest extends TestCase{
	private ReaderWriterLock readerWriter;
	public void setUp(){
		// setup
		try {
			readerWriter = new ReaderWriterLock();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tearDown(){
		// tear down
	}
	
	public void testReadWriteLock(){
		MyThreadFactory factory = new MyThreadFactory();
		for(int i = 0; i < 10; ++i){
			factory.newThread(readerWriter. new ReadWriteRunnable(),"Thread " + i)
			.start();;
		}
	}

}
