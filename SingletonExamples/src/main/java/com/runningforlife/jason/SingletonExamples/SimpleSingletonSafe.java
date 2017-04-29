package com.runningforlife.jason.SingletonExamples;


/*
 * a singleton which is thread safe
 */

public class SimpleSingletonSafe {
	private static final SimpleSingletonSafe INSTANCE = new SimpleSingletonSafe();
	
	private SimpleSingletonSafe(){}
	
	public static SimpleSingletonSafe getInstance(){
		return INSTANCE;
	}
	
	@Override
	public String toString(){
		return "SimpleSingletonSafe";
	}
	
}
