package com.runningforlife.jason.network.web;

public enum HttpMethod {
	GET("GET"),
	POST("POST"),
	HEAD("HEAD"),
	PUT("PUT"),
	DELETE("DELTE");
	
	private String method;
	
	
	private HttpMethod(String m){
		method = m;
	}
	
	
	public String getMethod(){
		return method;
	}

}
