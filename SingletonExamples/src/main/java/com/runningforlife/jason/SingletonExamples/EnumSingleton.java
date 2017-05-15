package com.runningforlife.jason.SingletonExamples;

import java.io.Serializable;

/**
 * a singleton implemented using Enum
 * @author jason
 *
 */

public enum EnumSingleton implements Serializable{
	INSTANCE;
	
	@Override
	public String toString(){
		return "EnumSingleton";
	}
	
	// prevent object creation
	private Object readResolve(){
		return INSTANCE;
	}
}
