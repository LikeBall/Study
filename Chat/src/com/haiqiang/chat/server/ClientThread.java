package com.haiqiang.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Set;

public class ClientThread implements Runnable {

	private Socket s = null;
	private Set<ClientThread> sClient = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private boolean bConnected = false;
	
	ClientThread(Socket s, Set<ClientThread> sClient) {
		this.s = s;
		this.sClient = sClient;
		try {
			dis = new DataInputStream(this.s.getInputStream());
			dos = new DataOutputStream(this.s.getOutputStream());
			bConnected = true;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String str) {
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (SocketException e) {
				sClient.remove(this);
				System.out.println("删除一个已退出的客户端");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void run() {
		try {
			while(bConnected) {
				String str = dis.readUTF();
				System.out.println(str);
				for(Iterator<ClientThread> cIte = sClient.iterator(); cIte.hasNext(); ) {
					ClientThread ct = cIte.next();
					ct.send(str);
				}
			}
		} catch(EOFException e) {
			System.out.println("Client closed!");
//			sClient.remove(s);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(s != null) {
					s.close();
				}
				if(dis != null) {
					dis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


