package com.github.runningforlife.concurrency;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory{
	
	public Thread newThread(Runnable r, String name){
		return new Thread(r,name);
	}

	public Thread newThread(Runnable r) {
		return new Thread(r);
	}

}
