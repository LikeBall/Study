package com.haiqiang.tankWar.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.haiqiang.tankWar.param.Parameter;

public class ShowView extends Frame{
	
	private static final long serialVersionUID = 1L;
	
	private int x = (Parameter.FRAME_WIDTH-20) / 2;
	private int y = (Parameter.FRAME_HEIGHT - 30);
	
	public void paint(Graphics g) {
		System.out.println("paint" + y);
		g.fillOval(x, y--, 20, 20);
	}
	
	public ShowView(String s) {
		super(s);
	}
	
	public void launch() {
		this.setBounds(100, 100, Parameter.FRAME_WIDTH, Parameter.FRAME_HEIGHT);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.setVisible(true);
		Thread t = new Thread(new PaintThread());
		t.start();
	}
	
	class PaintThread implements Runnable {
		
		public void run() {
			System.out.println("run");
			repaint(1);
		}
	}
}
