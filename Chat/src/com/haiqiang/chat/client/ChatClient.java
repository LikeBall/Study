package com.haiqiang.chat.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient extends Frame {

	private static final long serialVersionUID = 1L;

	TextArea taContext;
	TextField tfTxt;
	Socket s;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	
	ChatClient() {
		this.setLocation(300, 400);
		this.setSize(300, 300);
		taContext = new TextArea();
		tfTxt = new TextField();
		tfTxt.addActionListener(new Tfmonitor());
		this.add(taContext, BorderLayout.NORTH);
		this.add(tfTxt, BorderLayout.SOUTH);
		this.pack();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeCon();
				System.exit(1);
			}
		});
		this.setVisible(true);
		connect();
	}
	
	public static void main(String[] args) {
		new ChatClient();
		
	}
	private class Tfmonitor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tfStr = tfTxt.getText();
//			taContext.append(tfStr + "\n");
			tfTxt.setText("");
			try {
				dos.writeUTF(tfStr);
				dos.flush(); 
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void connect() {
		try {
			this.s = new Socket("localhost", 8888);
			dos = new DataOutputStream(this.s.getOutputStream());
			dis = new DataInputStream(this.s.getInputStream());
			while(true) {
				taContext.append(dis.readUTF() + "\n");
			}		
		} catch (SocketException e) {
			System.out.println("���˳���");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	public void closeCon() {
		try {
			s.close();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
