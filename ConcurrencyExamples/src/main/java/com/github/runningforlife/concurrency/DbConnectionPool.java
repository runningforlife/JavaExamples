package com.github.runningforlife.concurrency;

import java.sql.Connection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * a connection pool to simulate database connection
 * 
 * @author jason
 *
 */

public class DbConnectionPool {
	private LinkedBlockingQueue<Connection> queue = new LinkedBlockingQueue();
	
	
	public DbConnectionPool(int initialSize){
		if(initialSize > 0){
			for(int i = 0; i < initialSize; ++i){
				queue.add(DbConnectionProxy.newConnection());
			}
		}
	}
	
	public void releaseConnection(Connection connection){
		if(connection != null){
			queue.add(connection);
		}
	}
	
	public Connection takeConnection(long timeout) throws InterruptedException{
		if(timeout <= 0){
			if(!queue.isEmpty()){
				return queue.take();
			}
			
			return queue.take();
		}
		
		return queue.poll(timeout,TimeUnit.MILLISECONDS);
	}

}
