package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmaBFS_Heri {

    public static void main(String[] args) {
        //generator graph
        int numVertex = 10;
        int[][] adjacency = new int[numVertex][numVertex];
        for (int i = 0; i < numVertex; i++) {
            adjacency[i][i] = 0;
            for (int j = i + 1; j < numVertex; j++) {
                int value = ThreadLocalRandom.current().nextInt(1, 10);
                adjacency[i][j] = value;
                adjacency[j][i] = value;
            }
        }
        adjacency[2][4] = 0;
        adjacency[4][2] = 0;

        //cetak adjacency
        System.out.print(" \t");
        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("V" + (i) + "\t");
        }
        System.out.println();

        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("v" + (i) + ("\t"));
            for (int j = 0; j < adjacency[i].length; j++) {
                System.out.print(adjacency[i][j] + "\t");

            }
            System.out.println();
            //System.out.println(Arrays.toString(adjacency[i]));

        }
        //problem
        // find the shortest path from start vertex to finish vertex
        int start = 2;//start dari v2
        int finish = 4;//finish di v4

        Queue<VertexBFS> antrian = new LinkedList<>();

        ArrayList<Integer> visitedRoot = new ArrayList<>();
        visitedRoot.add(start);
        VertexBFS root = new VertexBFS(start, visitedRoot, 0);
        antrian.offer(root); //masukkan root kedalam antrian

        //MEMULAI PENCARIAN BFS
        while (!antrian.isEmpty()) {
            VertexBFS currentVertex = antrian.poll();
            int origin = currentVertex.id;
            ArrayList<Integer> visitedOrigin = currentVertex.visited;
            int jarakOrigin = currentVertex.jarak;

            if (origin == finish) {
                //pencarian selesai
                System.out.print("Solusi BFS: ");
                System.out.print(visitedOrigin);
                System.out.println(" jarak: " + jarakOrigin);
                break;

            } else {
                for (int j = 0; j < numVertex; j++) {
                    int value = adjacency[origin][j];
                    if (value > 0 && !visitedOrigin.contains(j)) {
                        //tambahkan vertex ke antrian
                        ArrayList<Integer> visitedDestination = new ArrayList<>();
                        //copy visited dari origin
                        for (int k = 0; k < visitedOrigin.size(); k++) {
                            visitedDestination.add(visitedOrigin.get(k));

                        }
                        visitedDestination.add(j);

                        int jarakDestination = jarakOrigin + value;
                        VertexBFS vertexDestination = new VertexBFS(j, visitedDestination, jarakDestination);
                        antrian.offer(vertexDestination); //masukkan root kedalam antrian
                    }
                }
            }
        }
    }
}

class VertexBFS {

    int id; //nomor vertex
    ArrayList<Integer> visited; //lintasan
    int jarak;

    public VertexBFS(int id, ArrayList<Integer> visited, int jarak) {
        this.id = id;
        this.visited = visited;
        this.jarak = jarak;
    }
}
