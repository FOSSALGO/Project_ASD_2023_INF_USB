package AwalFrustasi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmaBruteForce{
    public static void main(String[] args) {
        int numVertex = 12;
        int[][] adjacency = new int[numVertex][numVertex];
        for (int i = 0; i < numVertex; i++) {
            adjacency[i][i] = 0;
            for (int j = i + 1; j < numVertex; j++) {
                int value = ThreadLocalRandom.current().nextInt(1, 10);
                adjacency[i][j] = value;
                adjacency[j][i] = value;
            }
        }

        adjacency[2][4]= 0;
        adjacency[4][2]= 0;

        //CETAK ADJACENCY
        System.out.print("  \t");
        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("V"+ (i) +"\t");
        }
        System.out.println();
        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("V" + (i) + "\t");
            for (int j = 0; j < adjacency[i].length; j++) {
                System.out.print(adjacency[i][j]+"\t");
            }
            System.out.println();
        }
        //PROBLEM
        int start = 2;
        int finish = 4;

        //SOLUSI BFS
        Queue<VertexBFS> antrian = new LinkedList<>();

        ArrayList<Integer> visitedRoot = new ArrayList<>();
        visitedRoot.add(start);
        VertexBFS root = new VertexBFS(start,visitedRoot,0);
        antrian.offer(root);//masukkan root kedalam antrian

        //MEMULAI PENCARIAN BFS
        while (!antrian.isEmpty()){
            VertexBFS currentVertex = antrian.poll();
            int ori = currentVertex.id;
            ArrayList<Integer> visitedOri = currentVertex.visited;
            int jarakOri = currentVertex.jarak;

            if(ori==finish){
                //PENCARIAN SELESAI
                System.out.print("Solusi BFS: ");
                System.out.print(visitedOri);
                System.out.println(" jarak: "+jarakOri);
                break;
            }else{
            for (int j = 0; j < numVertex; j++) {
                int value = adjacency[ori][j];
                if(value>0 && !visitedOri.contains(j)){
                    ArrayList<Integer> visitedDestination = new ArrayList<>();
                    for (int i = 0; i < visitedOri.size(); i++) {
                        visitedDestination.add(visitedOri.get(i));
                    }
                    visitedDestination.add(j);
//                    ArrayList<Integer> visitedDestination = (ArrayList<Integer>) visitedOri.clone();
//                    visitedDestination.add(j);
                    int jarakDestination = jarakOri + value;
                    VertexBFS vertexDestinatoin = new VertexBFS(j,visitedDestination,jarakDestination);
                    antrian.offer(vertexDestinatoin);//masukkan root kedalam antrian
                }
            }
            }
        }
    }//end of main
}

class VertexBFS{
    int id;
    ArrayList<Integer> visited;
    int jarak;

    public VertexBFS(int id, ArrayList<Integer> visited, int jarak) {
        this.id = id;
        this.visited = visited;
        this.jarak = jarak;
    }
}
