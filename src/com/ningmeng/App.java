package com.ningmeng;

import java.util.LinkedList;

/**
 * @Author ningmengmao
 * @Date 2021-05-01 12:45:47
 * @Version 1.0
 */
public class App {

	public static void main(String[] args) throws InterruptedException {
		run();
	}

	private static void run() throws InterruptedException {
		final int size = 20;
		GameFrame frame = new GameFrame(size);

		Graph graph = new Graph(size);
		graph.setSnake(frame.panel.getSnake());
		graph.setApple(frame.panel.getApple());

		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(5);
					graph.move();
					frame.panel.move();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();

		thread.join();


	}


	private static void test() throws InterruptedException {
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
