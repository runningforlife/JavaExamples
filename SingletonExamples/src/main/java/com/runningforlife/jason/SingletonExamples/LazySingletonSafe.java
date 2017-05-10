package com.runningforlife.jason.SingletonExamples;

/*
 * a thread-safe singleton
 */

public class LazySingletonSafe {
	private volatile static  LazySingletonSafe sInstance = null;
	
	private LazySingletonSafe(){}
	
	public static LazySingletonSafe getInstance(){
		if(sInstance == null){
			synchronized(LazySingletonSafe.class){
				if(sInstance == null){
					sInstance = new LazySingletonSafe();
				}
			}
		}
		
		return sInstance;
	}

	@Override
	public String toString(){
		return "LazySingletonSafe";
	}
}
