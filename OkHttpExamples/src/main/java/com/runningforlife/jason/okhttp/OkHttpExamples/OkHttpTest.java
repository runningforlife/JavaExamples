package com.runningforlife.jason.okhttp.OkHttpExamples;

/**
 * Hello world!
 *
 */
public class OkHttpTest 
{
	private final static String POST_TEXT = "hello, here is JasonWang from earth";

    public static void main( String[] args )
    {
       OkHttpProxy proxy = OkHttpProxy.getInstance();
       
       String url = "https://www.baidu.com/";
       
       System.out.println("GET:");
       
       String result = proxy.download(url);
       if(result == null){
    	   System.out.println("fail to get data");
       }else{
    	   System.out.println(result);
       }
       
       System.out.println("POST:");
       result = proxy.postData(url, POST_TEXT);
       if(result == null){
    	   System.out.println("fail to post data");
       }else{
    	   System.out.println(result);
       }
       
       
    }
}
