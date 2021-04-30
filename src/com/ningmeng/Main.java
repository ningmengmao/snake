package com.ningmeng;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph(100);

        long l = System.currentTimeMillis();
        int[] dijkstra = graph.dijkstra(0, 100 * 50 + 50);

        System.out.println(System.currentTimeMillis() - l + "ms");
//        for (int i = 0; i < dijkstra.length; i++) {
//            System.out.println(i + " -> " + dijkstra[i]);
//        }
    }
}
