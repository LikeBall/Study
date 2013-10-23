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
			System.out.println("端口已被占用...");
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while(true) {
				ClientThread ct = new ClientThread(ss.accept(), sClient);
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
}
