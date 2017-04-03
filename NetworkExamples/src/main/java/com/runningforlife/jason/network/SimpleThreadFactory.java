package com.runningforlife.jason.network;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory{
	private static class Holder{
		private static SimpleThreadFactory instance = new SimpleThreadFactory();
	}
	
	public static SimpleThreadFactory getInstance(){
		return Holder.instance;
	}

	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		return new Thread(r);
	}

}
