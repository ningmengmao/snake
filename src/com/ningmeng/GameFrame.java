package com.ningmeng;

import javax.swing.*;

/**
 * @Author ningmengmao
 * @Date 2021-05-01 12:46:04
 * @Version 1.0
 */
public class GameFrame extends JFrame {

	public final GamePanel panel;

	GameFrame() {
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
