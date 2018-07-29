package com.github.runningforlife.ConcurrencyExamples.ConcurrentDegree;

import com.github.runningforlife.concurrency.ThreadProfiler;
import com.github.runningforlife.concurrency.ConcurrentDegree.ConcurrentTask;
import com.github.runningforlife.concurrency.ConcurrentDegree.SerialTask;
import com.github.runningforlife.concurrency.ConcurrentDegree.Task;

import junit.framework.TestCase;

public class ConcurrencySerialTest extends TestCase {
	
	private static long counts[] = {1, 10, 100, 1000};	
	private static long COUNT_BASE = 100000;
	private static int NUM_THREADS = 2;
		
	@Override
	public void setUp() {
	}
	
	public void testConcurrencySerial() {
		for (int i = 0; i < counts.length; ++i) {
			long count = counts[i] * COUNT_BASE;
			
			System.out.println("do calculation for count = " + count);
			Task concurrent = new ConcurrentTask(count, NUM_THREADS);
			Task serial = new SerialTask(count);
			
			ThreadProfiler.start();
			try {
				concurrent.execute();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//ThreadProfiler.end();
			//ThreadProfiler.getTime();
			System.out.println("concurrent task takes " + ThreadProfiler.getTime() + "ms for "
					+ NUM_THREADS + " threads");
			
			ThreadProfiler.start();
			try {
				serial.execute();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("serial task takes " + ThreadProfiler.getTime() + "ms");	
		}
	}
}
