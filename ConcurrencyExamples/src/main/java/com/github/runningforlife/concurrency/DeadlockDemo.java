package com.github.runningforlife.concurrency;

public class DeadlockDemo {
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	
	public static void main(String[] args) {
		DeadlockDemo deadlock = new DeadlockDemo();
		deadlock.deadlock();
	}
	
	
	public void deadlock() {
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("try to get lock1...");
				synchronized (lock1) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("get lock1, try get lock2...");
					synchronized(lock2) {
						System.out.println("get lock2");
					}
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				System.out.println("try to get lock2");
				synchronized(lock2) {
					System.out.println("get lock1, trying to get lock1");
					synchronized(lock1) {
						System.out.println("get lock1");
						try {
							Thread.sleep(120);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		
		thread1.start();
		thread2.start();
	}

}
