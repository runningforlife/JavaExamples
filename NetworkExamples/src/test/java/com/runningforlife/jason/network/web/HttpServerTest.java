package com.runningforlife.jason.network.web;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * test step:
 * 
 * 1. run cmd : java com.runningforlife.jason.network.web.SimpleHttpServer /home/jason/GitHub/JavaExamples/NetworkExamples/src/main/java/com/runningforlife/jason/network/web/res
 * 2. open browser and input 
 *    http://localhost:8080/index.html
 * 3. you can see the PAGE
 */
public class HttpServerTest extends TestCase{
	private static final String PATH = "/home/jason/GitHub/JavaExamples/NetworkExamples"
			+ "/src/main/java/com/runningforlife/jason/network/web/res";
	
	@Test
	public void startSever(){
		SimpleHttpServer.main(new String[]{PATH});
	}

}
