package mdmtsp_aco;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MDMTSP_ACO {
    public static void main(String[] args) {
        //GENERATOR GRAPH
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
        // CETAK ADJACENCY
        System.out.print("\t");
        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("V" + (i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < adjacency.length; i++) {
            System.out.print("V" + (i) + "\t");
            for (int j = 0; j < adjacency[i].length; j++) {
                System.out.print(adjacency[i][j] + "\t");
            }
            System.out.println();
        }
        //PROBLEM
        //find the shortest path from start vertex to finish vertex
        int D = 3;//D adlah jumlah depot
        int d0 = 2; // vertex-2 sebagai depot-0
        int d1 = 4; // vertex-2 sebagai depot-1
        int d2 = 7; // vertex-2 sebagai depot-2
        int[] depot = {d0, d1, d2};

        int M0 = 2; // M adlh jumlah salesmen do depot-0
        int M1 = 3; // M adlh jumlah salesmen do depot-1
        int M2 = 1; // M adlh jumlah salesmen do depot-2
        int[] M = {M0, M1, M2};


        //PARAMETER ALGORITMA ACO
        double[][] pheromone = new double[numVertex][numVertex]; // TAU
        double[][] visibility = new double[numVertex][numVertex]; // Eta
        int S = 10; //banyaknya semut
        int NCMax = 20; // menyatakan jumlah siklus maksimum
        double alpha = 0.6;//kostanta pengendali pheromone (α), nilai α ≥ 0
        double beta = 0.5;//kostanta pengendali intensitas visibility (β), nilai β ≥ 0
        double rho = 0.4; // konstanta penguapan pheromon
        double Q = 0.5;//konstanta siklus semut

        //PERSIAPAN
        //inisialisasi array pheromone dan visibility
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                if (adjacency[i][j] > 0) {
                    pheromone[i][j] = 1;
                    visibility[i][j] = 1.0 / (double) adjacency[i][j];
                }
            }
        }

        PathDepot[] bestSolution = null;
        double bestDistance = Double.MAX_VALUE;

        //loop siklus semut
        for (int n = 0; n < NCMax; n++) { //siklus semut
            double[][] deltaTau = new double[numVertex][numVertex];
            for (int semut = 0; semut < S; semut++) { // loop pencarian oleh semut
                PathDepot[] path = new PathDepot[D];
                int[] m = new int[D];
                for (int i = 0; i < D; i++) {
                    path[i] = new PathDepot();
                    path[i].visited.push(depot[i]);
                    m[i] = 1;
                }
                // semut mulai mencari lintasannya
                // vertex lain yang harus dikunjungi itu sebnyak numVertex - D
                int MAX_VISIT = numVertex - D;
                int nVisited = 0;// banykannya vertex selain depot yang telah dikunjungi

                while (nVisited < MAX_VISIT) {
                    //random depot
                    int idDepot = ThreadLocalRandom.current().nextInt(0, D);
                    int origin = path[idDepot].visited.peek();
                    //hitung probabilitas semut ke vertex didepannya
                    int i = origin;
                    double[] pembilang = new double[numVertex];
                    double penyebut = 0;
                    int[] canditate = new int[numVertex];
                    for (int j = 0; j < numVertex; j++) {
                        if (adjacency[i][j] > 0 && canBeVisited(j, idDepot, path, depot, m, M)){
                            canditate[j] = 1;
                            double tau = pheromone[i][j];
                            double eta = visibility[i][j];
                            double tau_eta = Math.pow(tau, alpha) * Math.pow(eta, beta);
                            pembilang[j] = tau_eta;
                            penyebut += tau_eta; // increment penyebut = SIGMA tau x eta
                        }
                    }// end of for j
                    //hitung probabilitas semut
                    double[] probabilitasKomulatif = new double[numVertex];
                    double totalProbabilitas = 0;
                    for (int j = 0; j < numVertex; j++) {
                        if (canditate[j] == 1) {
                            double probabilitas = pembilang[j] / penyebut;
                            totalProbabilitas += probabilitas;
                            probabilitasKomulatif[j] = totalProbabilitas;
                        }
                    }//end of for j

                    double randomProbabilitas = Math.random()*totalProbabilitas ;
                    int destination = i;
                    for (int j = 0; j < numVertex; j++) {
                        if(canditate[j]==1 && probabilitasKomulatif[j] >0 && randomProbabilitas<probabilitasKomulatif[j]){
                            destination = j;
                            break;
                        }
                    }// end of for j
                    if (destination==depot[idDepot]){
                        if (m[idDepot] < M[idDepot]){
                            path[idDepot].visited.push(destination);
                            m[idDepot]++;
                        }
                    }else{
                        path[idDepot].visited.push(destination);
                        nVisited++;
                    }

                }// end of while (nVisited < MAX_VISIT)
                for (int i = 0; i < D; i++) {
                    path[i].visited.push(depot[i]);
                }
                // hitung jarak yang telah dilalui semut
                double lk = 0;
                for (int i = 0; i < path.length ; i++) {
                    Stack<Integer> visited = path[i].visited;
                    int origin = visited.get(0);
                    for (int j = 1; j < visited.size(); j++) {
                        int destination = visited.get(j);
                        lk += adjacency[origin][destination];
                        origin = destination;
                    }
                }

                // SAVE BEST SOLUSION
                if (lk<bestDistance){
                    bestDistance = lk;
                    bestSolution = path;
                }
                // update delta Tau oleh semut ke-m
                for (int i = 0; i < path.length ; i++) {
                    Stack<Integer> visited = path[i].visited;
                    int origin = visited.get(0); // mula2 tetapkan vertex start sebagai origin
                    for (int k = 0; k < visited.size() ; k++) {
                        int destination = visited.get(k);
                        deltaTau[origin][destination] += (Q / lk); //update SIGMA delta Tau xy
                    }
                }
            }//end of loop for pencarian semut
            //UPDATE PHEROMONE (Tau)
            for (int i = 0; i < numVertex; i++) {
                for (int j = 0; j < numVertex; j++) {
                    pheromone[i][j] = (1.0 - rho)* pheromone[i][j] + deltaTau[i][j];
                }
            }
        }//end of for loop siklus semut

        //CETAK BEST SOLUTION
        System.out.println("------------------------------------------------------------------------");
        System.out.println("BEST SOLUSION");
        System.out.println("Jarak : " + bestDistance);
        for (int i = 0; i < bestSolution.length ; i++) {
            System.out.println(bestSolution[i].visited);
        }
        System.out.println("------------------------------------------------------------------------");
    }// end of main

    public static boolean canBeVisited(int vertex,int idDepot, PathDepot[] path, int[] depot, int[] m, int[] M) {
        boolean result = true;
        int indexDepot = -1;
        for (int i = 0; i < depot.length; i++) {
            if (depot [i] == vertex){
                indexDepot = i;
                break;
            }
        }
        if (indexDepot >= 0){
            if (indexDepot != idDepot){
                result = false;
            }else{
                if (m[idDepot] >= M[idDepot]){
                    result=false;
                }
            }
        }else {
            //bukan depot
            for (int i = 0; i < path.length; i++) {
                if (path[i].visited.contains(vertex)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}// end of class

class PathDepot{
    Stack<Integer> visited = new Stack<>();
}
   
    
