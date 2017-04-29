package com.runningforlife.jason.SingletonExamples;

/*
 * a very simple singleton
 * 
 * this is not thread safe
 */

public class SimpleSingletonUnsafe {
	private static SimpleSingletonUnsafe sInstance = null;
	
	private SimpleSingletonUnsafe(){}
	
	public static SimpleSingletonUnsafe getInstance(){
		if(sInstance == null){
			sInstance = new SimpleSingletonUnsafe();
		}
		
		return sInstance;
		
	}
	
	
	@Override
	public String toString(){
		return "SimpleSingletonUnsafe";
	}
}
