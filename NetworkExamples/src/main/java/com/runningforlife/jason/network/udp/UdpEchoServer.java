package com.runningforlife.jason.network.udp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*
 * a UDP echo server to receiver message to 
 */

public class UdpEchoServer {
	/*
	 * maximum buffer size
	 */
	public static final int BUF_SIZE = 1024;
	
	/*
	 * read timeout
	 */
	public static final int READ_TIMEOUT = 5000;
	/*
	 * thread pool size
	 */
	public static final int POOL_SIZE = 5;
	
	private static DatagramPacket packet;
	private static DatagramSocket socket;
	
	
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("USAGE: java UdpEchoServer <Port Number>");
			System.exit(1);
		}		
		int port = Integer.parseInt(args[0]);
		init(port);
		
		while(true){
			processRequest();
			reply();
		}
	}
	
	private static void init(int port){
		byte[] buf = new byte[BUF_SIZE];
		packet = new DatagramPacket(buf,BUF_SIZE);
		try{
			socket = new DatagramSocket(port);
			//socket.setSoTimeout(READ_TIMEOUT);
		}catch (SocketException e) {
			System.out.println("cannot create UDP socket");
			e.printStackTrace();
		}
	}
	
	private static void processRequest(){
		if(socket == null){
			throw new IllegalArgumentException("socket is not initialized");
		}
		byte[] receive = new byte[1024];
		
		try{
			// receive from server
			socket.receive(packet);
		
			ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
			BufferedInputStream bufIs = new BufferedInputStream(bis);
			bufIs.read(receive,0,receive.length);
			String str = new String(receive);
			// read data from socket
			System.out.println("UdpEchoServer: packet receive from client address=" + packet.getAddress() +
					",port=" + packet.getPort());
			System.out.println("UdpEchoServer: packet received=" + str);
		}catch(IOException e){
			System.out.println("fail to read data from socket");
			e.printStackTrace();
		}
	}
	
	private static void reply(){
		// tell client we have received a message
		String reply = "I have receive your message";
		byte[] replyMsg = reply.getBytes();
		DatagramPacket replyPacket = new DatagramPacket(replyMsg,replyMsg.length);
		replyPacket.setPort(packet.getPort());
		replyPacket.setAddress(packet.getAddress());
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
