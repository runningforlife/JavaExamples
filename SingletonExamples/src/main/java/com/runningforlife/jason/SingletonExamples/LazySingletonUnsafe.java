package com.runningforlife.jason.SingletonExamples;

/*
 *  a lazy singleton to prevent object creation earlier
 *  
 *  this is not thread safe
 */

public class LazySingletonUnsafe {
	private static LazySingletonUnsafe sInstance = null;
	
	private LazySingletonUnsafe(){}
	
	public static LazySingletonUnsafe getInstance(){
		if(sInstance == null){
			synchronized(LazySingletonUnsafe.class){
				if(sInstance == null){
					sInstance = new LazySingletonUnsafe();
				}
			}
		}
		
		return sInstance;
	}
	
	
	@Override
	public String toString(){
		return "LazySingletonUnsafe";
	}

}
