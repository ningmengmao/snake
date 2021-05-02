package com.ningmeng;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 *
 * 通过getSnake来设置snake的位置
 * 通过getApple来设置apple的位置
 *
 * @Author ningmengmao
 * @Date 2021-05-01 12:46:49
 * @Version 1.0
 */
public class GamePanel extends JPanel {
	private final int WIDTH;
	private final int HEIGHT;

	private Graphics graphics;

	private int[] apple = {10, 10};
	private LinkedList<int[]> snake;

	/**
	 * 单元格长宽
	 */
	private final int UNIT_SIZE = 20;

	GamePanel(){
		this.WIDTH = 500;
		this.HEIGHT = 500;
		init();
	}
	GamePanel(int width, int height) {
		this.WIDTH = width * UNIT_SIZE;
		this.HEIGHT = height * UNIT_SIZE;
		init();
	}

	private void init() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		snake = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			int[] arr = {i, 0};
			snake.addFirst(arr);
		}
	}


	@Override
	public void paintComponent(Graphics g) {
		this.graphics = g;
		super.paintComponent(g);
		draw(g);

	}

	public void draw(Graphics g) {
		drawBaseLine(g);
		drawSnake();
		drawApple();
	}

	private void drawSnake() {

		for (int[] i : snake) {
			drawRect(graphics, i[0], i[1], Color.white);
		}

		int[] i = snake.get(0);
		// header
		drawRect(graphics, i[0], i[1], Color.blue);
	}

	public void drawApple() {
		drawRect(graphics, apple[0], apple[1], Color.red);
	}


	private void drawRect(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x * UNIT_SIZE,y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
	}


	private void drawBaseLine(Graphics g) {
		g.setColor(Color.gray);
		for (int i = 0; i < HEIGHT / UNIT_SIZE; i++) {
			g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
			g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
		}
	}

	public void move() {
		repaint();
	}


	public void setApple(int[] apple) {
		assert apple.length == 2;
		this.apple = apple;
	}

	public int[] getApple() {
		return this.apple;
	}

	public void setSnake(LinkedList<int[]> snake) {
		assert snake != null && snake.size() > 0;
		this.snake = snake;
	}


	public LinkedList<int[]> getSnake() {
		return this.snake;
	}



}
