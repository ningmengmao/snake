package com.ningmeng;

import java.util.LinkedList;

/**
 * @Author ningmengmao
 * @Date 2021-05-01 12:45:47
 * @Version 1.0
 */
public class App {

	public static void main(String[] args) throws InterruptedException {
		GameFrame frame = new GameFrame();

		Thread.sleep(1000);
		frame.panel.setApple(new int[]{10, 15});
		for (int i = 0; i < 5; i++) {
			LinkedList<int[]> snake = frame.panel.getSnake();
			snake.removeLast();
			int[] first = snake.getFirst();
			int[] n = {first[0] + 1, first[1]};
			snake.addFirst(n);
			frame.panel.move();
			Thread.sleep(1000);

		}


	}
}
