package com.runningforlife.jason.network.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * handle http request from client
 */

public class HttpRequestHandler implements Runnable{
	private static final String HTTP_STATUS_OK = "HTTP/1.1 200 OK";
	private static final String HTTP_REPSONSE_HEADER = "Server: Morris";
	private static final String CONTENT_TYPE_IMAGE = "Content-Type: image/jpg";
	private static final String CONTENT_TYPE_TEXT = "Content-Type: text/html; charset=UTF-8";
	private String mRootPath;
	private Socket mSocket;
	
	public HttpRequestHandler(String rootPath, Socket s){
		mRootPath = rootPath;
		mSocket = s;
	}
	

	public void run() {
		String line = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		InputStream is;
		try{
			reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			writer = new PrintWriter(mSocket.getOutputStream());
			// request line
			line = reader.readLine();
			line.trim();
			if(line.startsWith(HttpMethod.GET.getMethod())){
				String fp = mRootPath +  line.split(" ")[1];
				if(fp.endsWith("jpg")){
					is = new FileInputStream(fp);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i;
					while((i = is.read()) != -1){
						baos.write(i);
					}
					
					byte[] data = baos.toByteArray();
					writer.println(HTTP_STATUS_OK);
					writer.println(HTTP_REPSONSE_HEADER);
					writer.println(CONTENT_TYPE_IMAGE);
					writer.println("Content-Length: " + data.length);
					writer.println("");
					mSocket.getOutputStream().write(data,0,data.length);;
				}else{
					is = new FileInputStream(fp);
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					
					writer.println(HTTP_STATUS_OK);
					writer.println(HTTP_REPSONSE_HEADER);
					writer.println(CONTENT_TYPE_TEXT);
					writer.println();
					while((line = br.readLine()) != null){
						writer.println(line);
					}
				}
				// flush to socket
				writer.flush();
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
