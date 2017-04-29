package com.runningforlife.jason.SingletonExamples;

/*
 *  a static nested holder to be singleton
 *  
 *  JVM will acquire a lock when initializing a class, so it is thread-safe
 */

public class SingletonHolder {
	private static class InstanceHolder{
		public static final SingletonHolder INSTANCE = new SingletonHolder();
	}
	
	
	public static SingletonHolder getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	
	
	@Override
	public String toString(){
		return "SingletonHolder";
	}

}
