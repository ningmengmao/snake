package com.ningmeng;


import java.util.LinkedList;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * @Author ningmengmao
 * @Date 2021-04-30 21:06:42
 * @Version 1.0
 */
public class Graph {

	private final Node[] matrix;
	private final int size;
	private LinkedList<int[]> snake;
	private int[] apple;

	/**
	 * @param size 图的大小为size * size
	 */
	public Graph(int size) {
		this.size = size;
		matrix = new Node[size * size];
		initMatrix();

	}

	private void initMatrix() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int index = j + i * size;
				// 右点
				if (j < size - 1) {
					addEdge(index, index + 1);
				}

				// 下点
				if (i < size - 1) {
					addEdge(index, index + size);
				}

				// 上点
				if (i > 0) {
					addEdge(index, index - size);
				}

				// 左点
				if (j > 0) {
					addEdge(index, index - 1);
				}
			}
		}

	}

	private void addEdge(int n, int m) {
		Node node = this.matrix[n];
		if (node == null) {
			node = new Node(m, 1);
			matrix[n] = node;
		} else {
			while(node.next != null) {
				node = node.next;
			}
			node.next = new Node(m, 1);
		}
	}

	public int[] dijkstra(int start, int target) {

		// 我觉得可以优化大小
		boolean[] used = new boolean[size * size];

		// 设置蛇身体位置的点无法到达

		snake.forEach(arr -> {
			int index = arr[1] * size + arr[0];
			used[index] = true;

		});
		int[] cost = new int[size * size];
		int[] way = new int[size * size];
		setWay(way, start);
		way[start] = -1;

		Node node = matrix[start];
		used[start] = true;

		while (node != null) {
			cost[node.index] = node.val;
			node = node.next;
		}

		while (true) {
			int index = start;
			index = getVertex(cost, used, start, index);
			if (index == -1) {
				break;
			}

			used[index] = true;

			node = matrix[index];
			while (node != null) {
				if (!used[node.index]) {
					int start2new = cost[node.index];

					int start2now = cost[index];
					int now2new = node.val;

					if (start2new == 0) {
						// 首次能到达新顶点
						cost[node.index] = start2now + now2new;
						way[node.index] = index;
					} else if (start2new > start2now + now2new) {
						cost[node.index] = start2now + now2new;
						way[node.index] = index;
					}
				}
				node = node.next;
			}
			if (used[target]) {
				break;
			}
		}

		return way;

	}

	private void setWay(int[] way, int start) {
		int i = start / size;
		int j = start % size;
		if (j > 0) {
			way[start - 1] = start;
		}
		if (j < size - 1) {
			way[start + 1] = start;
		}
		if (i > 0) {
			way[start - size] = start;
		}

		if ( i < size - 1) {
			way[start + size] = start;
		}
	}

	private int getVertex(int[] cost, boolean[] used, int start, int index) {
		int weight = Integer.MAX_VALUE;
		int res = -1;
		if (index == start) {
			for (int i = 0; i < cost.length; i++) {
				if (!used[i] && cost[i] > 0 && cost[i] < weight) {
					weight = cost[i];
					res = i;
				}
			}
		} else {
			Node node = matrix[index];
			while (node != null) {
				int target = node.val;
				if (!used[node.index] && target > 0 && target < weight ) {
					weight = target;
					res = node.index;
				}
				node = node.next;
			}

		}
		return res;
	}


	/**
	 * 移动蛇
	 */
	public void move() {
		int[] header = snake.getFirst();


		// todo 修改逻辑策略
		// dijkstra() 获取最短路径
		int[] way = dijkstra(header[1] * size + header[0], apple[1] * size + apple[0]);

		// 根据路径获取下一个方向
		char direction = getDirection(way);

		int[] next = new int[2];
		switch (direction) {
			case 'U' -> {
				next[0] = header[0];
				next[1] = header[1] - 1;
			}
			case 'D' -> {
				next[0] = header[0];
				next[1] = header[1] + 1;
			}
			case 'L' -> {
				next[0] = header[0] - 1;
				next[1] = header[1];
			}
			case 'R' -> {
				next[0] = header[0] + 1;
				next[1] = header[1];
			}
			default -> throw new RuntimeException();
		}

		// 判断header是否和apple位置相同
		snake.addFirst(next);
		if (next[0] == apple[0] && next[1] == apple[1]) {
			generateNewApple();
		} else {
			snake.removeLast();
		}

		// 如果相同, 生成新的apple
	}

	private Random random = new Random();
	// 生成新的苹果
	private void generateNewApple() {
		if (snake.size() == size * size - 1) {
			throw new RuntimeException("done");
		}
		while (true) {
			int x = random.nextInt(size);
			int y = random.nextInt(size);
			boolean b = snake.stream().anyMatch(arr -> arr[0] == x && arr[1] == y);
			if (!b) {
				apple[0] = x;
				apple[1] = y;
				break;
			}
		}
	}

	private char getDirection(int[] way) {
		int index = apple[1] * size + apple[0];
		System.out.println();

		int start = snake.getFirst()[1] * size + snake.getFirst()[0];
		while (true) {
			System.out.print(index + "\t");
			if (way[index] == start) {
				if (index - start == -1) {
					return 'L';
				} else if (index - start == 1) {
					return 'R';
				} else if (index - start == size) {
					return 'D';
				} else if (index - start == -size) {
					return 'U';
				}
			}
			index = way[index];
		}
	}


	public void setSnake(LinkedList<int[]> snake) {
		this.snake = snake;


	}


	/**
	 * @param f 对于的增加边或删除边
	 * @param i y轴位置
	 * @param j x轴位置
	 * @param index 目标的 x + y * size
	 */
	private void setEdge(BiConsumer<Integer, Integer> f, int i, int j,  int index) {
		if (j < size - 1) {
			f.accept(index, index + 1);
		}
		// 下点
		if (i < size - 1) {
			f.accept(index, index + size);
		}
		// 上点
		if (i > 0) {
			f.accept(index, index - size);
		}
		// 左点
		if (j > 0) {
			f.accept(index, index - 1);
		}
	}

	public void setApple(int[] apple) {
		this.apple = apple;
	}

	private static class Node{
		int index;
		int val;
		Node next;

		public Node(int index, int val) {
			this.index = index;
			this.val = val;
		}

		@Override
		public String toString() {
			return "Node{" +
					"index=" + index +
					", val=" + val +
					", next=" + next +
					'}';
		}
	}
}
