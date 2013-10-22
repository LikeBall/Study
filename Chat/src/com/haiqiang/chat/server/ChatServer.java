package com.haiqiang.chat.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	
	ServerSocket ss = null;
	Set<ClientThread> sClient = new HashSet<ClientThread>();	
	
	
	public static void main(String[] args) {
		new ChatServer().start();
	}
	
	public void start() {
		try {
			ss = new ServerSocket(8888);
		} catch(BindException e) {
			System.out.println("�˿��ѱ�ռ��...");
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(true) {
				ClientThread ct = new ClientThread(ss.accept());
				System.out.println("A Client connect!");
				new Thread(ct).start();
				sClient.add(ct);
			}
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				if(ss != null) {
					ss.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class ClientThread implements Runnable {
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		
		ClientThread(Socket s) {
			this.s = s;
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
				} catch (IOException e) {
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
				sClient.remove(s);
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
}