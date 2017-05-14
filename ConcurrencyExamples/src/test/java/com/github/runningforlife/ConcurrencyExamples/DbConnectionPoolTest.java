package com.github.runningforlife.ConcurrencyExamples;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.runningforlife.concurrency.DbConnectionPool;

import junit.framework.TestCase;
import junit.framework.Test;

/**
 * test database connection pool
 * @author jason
 *
 */

public class DbConnectionPoolTest extends TestCase{
	private static final int MAX_CONNECTIONS = 10;
	private static DbConnectionPool connectionPool;
	private static CountDownLatch start;
	private static CountDownLatch end;
	
	
	protected void setUp(){
		connectionPool = new DbConnectionPool(MAX_CONNECTIONS);
		start = new CountDownLatch(1);
	}
	
	protected void tearDown(){
		//connectionPool = null;
	}
	
	public void testConnect() throws InterruptedException{
		end = new CountDownLatch(MAX_CONNECTIONS);
		
		int count = 20;
		AtomicInteger get = new AtomicInteger(0);
		AtomicInteger notGet = new AtomicInteger(0);
		
		for(int i = 0; i < MAX_CONNECTIONS; ++i){
			Thread t = new Thread(new ConnectionRunner(count, get, notGet),"DbConnectionPoolTest");
			t.start();
		}
		
		start.countDown();
		end.await();
		
		System.out.println("total invocation: " + (MAX_CONNECTIONS*count));
		System.out.println("get connections: " +  get);
		System.out.println("not get connections: " + notGet);
	}
	
	static class ConnectionRunner implements Runnable{
		int count;
		AtomicInteger get;
		AtomicInteger notGet;
		
		public ConnectionRunner(int count, AtomicInteger g, AtomicInteger ng){
			this.count = count;
			this.get = g;
			this.notGet = ng;
		}

		public void run() {
			
			try{
				start.await();
			}catch(Exception e){
				
			}
			
			while(count-- > 0){
				try{
					Connection connection = connectionPool.takeConnection(500);
					
					if(connection != null){
						try{
							try {
								connection.createStatement();
								connection.commit();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}finally{
							connectionPool.releaseConnection(connection);
							get.incrementAndGet();
						}
					}else{
						notGet.incrementAndGet();
					}
					
				}catch(InterruptedException e){
					
				}
			}
			// this thread is starting...
			end.countDown();
			
		}
		
	}
}
