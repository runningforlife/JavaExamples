package com.github.runningforlife.concurrency.ConcurrentDegree;

import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentTask implements Task {
	private long count;
	/** number of threads to sub task */
	private int nThreads; 
	/** lock for concurrent calculation */
	private Object lock;
	
	private long addRes;
	private long minusRes;
	
	private AtomicLong atomicAdd = new AtomicLong();
	private AtomicLong atomicMin = new AtomicLong();
	
	public ConcurrentTask(long count, int threads) {
		this.count = count;
		this.nThreads = threads;
		
		addRes = 0;
		minusRes = 0x7FFF;
		
		atomicAdd.set(0);
		atomicMin.set(0x7FFF);
		
		lock = new Object();
	}
	
	
	public void execute() throws InterruptedException {
		long average = this.count/this.nThreads;
		long start = 0, end = 1;
		for (int c = 1; c <= nThreads; ++c) {
			start = end;
			end = start + average;
			Thread thread = new Thread(new SubTask(start, end));
			thread.start();
			
			thread.join();
		}
		
		System.out.println("addition result: " + this.atomicAdd.get() + ", minus result:" + this.atomicMin.get());
	}
	
	private final class SubTask implements Runnable {
		long countStart;
		long countEnd;
		
		public SubTask(long start, long end) {
			this.countStart = start;
			this.countEnd = end;
		}

		public void run() {
			for (long i = countStart; i < countEnd; ++i) {
				//addRes += 3;
				atomicAdd.addAndGet(3);
			}
		
			for (long i = countStart; i < countEnd; ++i) {
				atomicMin.decrementAndGet();
			}
			/*synchronized(lock) {
				for (long i = countStart; i < countEnd; ++i) {
					//addRes += 3;
					atomicAdd.addAndGet(3);
				}
			
				for (long i = countStart; i < countEnd; ++i) {
					atomicMin.decrementAndGet();
				}
			}*/
		}
	}

}
