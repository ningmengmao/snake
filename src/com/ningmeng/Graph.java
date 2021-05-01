package com.ningmeng;


/**
 * @Author ningmengmao
 * @Date 2021-04-30 21:06:42
 * @Version 1.0
 */
public class Graph {

	private final Node[] matrix;
	private final int size;

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
			int index = 0;
			index = getVertex(cost, used, start, index);
			if (index == -1) {
				break;
			}

			used[index] = true;
			if (used[target]) {
				break;
			}
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
