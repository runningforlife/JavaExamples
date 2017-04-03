package com.runningforlife.jason.network.udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.InterruptedByTimeoutException;

/*
 * a client to send message to the UDP server
 */

public class UdpClient {
	
	private DatagramPacket packet;
	private DatagramSocket socket;
	private DatagramPacket response;
	private byte[] buf;
	private volatile boolean isTimeout;
	/*
	 * UDP server port
	 */
	public static final int SERVER_PORT = 9100;
	/*
	 * socket timeout
	 */
	public static final int DEFAULT_TIMEOUT = 3000;
	/*
	 * buffer size
	 */
	public static final int BUF_SIZE = 1024;
	
	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("USAGE: java UdpClient <message> <host name> <port number>");
			System.exit(1);
		}
		
		String msg = args[0];
		String hostName = args[1];
		int port = Integer.parseInt(args[2]);
		UdpClient client = new UdpClient(port,hostName);
		
		client.send(msg);
	}

	public UdpClient(int port,String hostName){
		init(port,hostName);
	}
	
	private void init(int port,String name){
		InetAddress addr;
		try {
			addr = InetAddress.getByName(name);
		} catch (UnknownHostException e1) {
			System.out.println("unknown host name");
			e1.printStackTrace();
			return;
		}
		buf = new byte[BUF_SIZE];
		packet = new DatagramPacket(buf,buf.length,addr,port);		
		try {
			// bind to any available port
			socket = new DatagramSocket();
			//socket.setSoTimeout(DEFAULT_TIMEOUT);
		} catch (SocketException e) {
			System.out.println("fail to create socket");
			e.printStackTrace();
		}
		
		isTimeout = false;
	}
	
	public void send(String msg){
		try {
			packet.setData(msg.getBytes());
			socket.send(packet);
		} catch (IOException e) {
			System.out.println("fail to send data");
			e.printStackTrace();
		}
		
		byte[] resp = new byte[BUF_SIZE];
		response = new DatagramPacket(resp,BUF_SIZE);
		// wait for server to respond
		waitForResponse();
	}
	
	private void waitForResponse(){
		try {
			socket.receive(response);
		} catch (IOException e) {
			e.printStackTrace();
			isTimeout = true;
		}
		
		
		// receive message from server
		if(!isTimeout){
			ByteArrayInputStream bis = new ByteArrayInputStream(response.getData(),0,
					response.getLength());
			DataInputStream dis = new DataInputStream(bis);
			byte[] resp = new byte[BUF_SIZE];
			try {
				dis.read(resp);
				String str = new String(resp);
				System.out.println("server " + response.getAddress() + " response{}" + str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
