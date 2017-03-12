/**
 * 
 */
package com.runningforlife.jason.okhttp.OkHttpExamples;

/**
 * an interface to access okhttp client
 * @author JasonWang
 * @since 0.0.1
 */
public interface NetInterface {

	/*
	 * a get method to retrieve url
	 */
	String download(String url);
	
	/*
	 * download source async
	 */
	String downloadAsync(String url);
	
	/*
	 * post data to a given server 
	 */
	String postData(String url,String data);
}
