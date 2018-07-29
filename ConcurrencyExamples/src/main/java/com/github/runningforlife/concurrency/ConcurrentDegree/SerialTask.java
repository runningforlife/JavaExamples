package com.github.runningforlife.concurrency.ConcurrentDegree;

public class SerialTask implements Task {
	private long count;
	
	public SerialTask(long count) {
		this.count = count;
	}
	
	
	public void execute() {
		int res = 0;
		
		for (long i = 1; i <= count; ++i) {
			res += 3;
		}
		
		int b = 0x7FFF;
		for (long i = 1; i <= count; ++i) {
			--b;
		}
		
		System.out.println("addition result: " + res + ", minus result:" + b);
	}
}
