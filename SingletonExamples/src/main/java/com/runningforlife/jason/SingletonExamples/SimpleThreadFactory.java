package com.runningforlife.jason.SingletonExamples;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory{

	public Thread newThread(Runnable r) {
		// r could be null
		return new Thread(r);
	}
	
	

}
