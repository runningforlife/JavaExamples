package com.github.runningforlife.concurrency;

/*
 * a profiler to record execution time of a thread
 */

public class ThreadProfiler {
	private static final ThreadLocal<Long> TIME = new ThreadLocal<Long>(){
		protected Long initialValue(){
			return System.currentTimeMillis();
		}
	};
	
	private static final ThreadLocal<Boolean> TEMINATED = new ThreadLocal<Boolean>();
	
	public static final void start(){
		TIME.set(System.currentTimeMillis());
		TEMINATED.set(false);
	}
	
	public static final void end(){
		TEMINATED.set(true);
	}
	
	public static final long getTime(){
		return System.currentTimeMillis() - TIME.get();
	}

	public static final boolean isTeminated(){
		return TEMINATED.get();
	}
}
