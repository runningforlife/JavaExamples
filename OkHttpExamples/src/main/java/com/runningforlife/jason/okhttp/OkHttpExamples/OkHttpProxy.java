package com.runningforlife.jason.okhttp.OkHttpExamples;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpProxy implements NetInterface{
	
	private final static OkHttpClient.Builder builder = new OkHttpClient.Builder();

	private static OkHttpProxy sInstance;
	private static OkHttpClient client;
	
	private DownloadCompleteListener mListener;
	
	private final static MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
	
	public interface DownloadCompleteListener{
		void onDownloadComplete(String result,boolean success);
	}
	
	public static OkHttpProxy getInstance(){
		if(sInstance == null){
			sInstance = new OkHttpProxy();
		}
		
		client = builder.connectTimeout(5000, TimeUnit.MILLISECONDS).build();
		
		return sInstance;
	}
	
	public void setDownloadListener(DownloadCompleteListener listener){
		mListener = listener;
	}
	
	public String download(String url) {
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		Response reponse;
		try {
			reponse = client.newCall(request).execute();
			if(reponse.isSuccessful()){
				return reponse.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return null;
	}

	public String downloadAsync(String url) {
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		client.newCall(request).enqueue(new Callback(){

			public void onFailure(Call call, IOException e) {
				mListener.onDownloadComplete(null, false);
			}

			public void onResponse(Call call, Response response) throws IOException {
				mListener.onDownloadComplete(response.body().string(), true);
			}
			
		});
		
		return null;
	}

	public String postData(String url,String data) {
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.create(MEDIA_TYPE_TEXT, data))
				.build();
		
		try{
			Response response = client.newCall(request).execute();
			if(response.isSuccessful()){
				return response.body().string();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}

}
