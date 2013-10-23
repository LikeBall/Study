package com.haiqiang.tankWar.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class ShowView extends Frame{

	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		g.fillOval(245, 475, 20, 20);
	}
	private static final long serialVersionUID = 1L;
	public ShowView(String s) {
		super(s);
	}
	public void launch() {
		this.setBounds(100, 100, 500, 500);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.setVisible(true);
	}
	
}
