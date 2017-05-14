package com.github.runningforlife.concurrency;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * a proxy to new connection and connection handler
 */

public class DbConnectionProxy {
	
	static class ConnectionHandler implements InvocationHandler{

		public Object invoke(Object object, Method m, Object[] arg) throws Throwable {
			if("commit".equals(m.getName())){
				System.out.println("sleeping....");
				SleepUtils.sleep(2);
			}
			return null;
		}
		
	}
	
	
	public static final Connection newConnection(){
		
		return (Connection)Proxy.newProxyInstance(DbConnectionProxy.class.getClassLoader(), 
				new Class<?>[]{Connection.class}, new ConnectionHandler());
	}

}
