package mst;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmaACOMST {
    public static void main(String[] args) {
        // Generator Graph
        int numVertex = 5;
        int[][] adjacency = new int[numVertex][numVertex];
        
        for (int i = 0; i < numVertex; i++){
            adjacency[i][i] = 0;
            for (int j = i+1; j < numVertex; j++){
                int value = ThreadLocalRandom.current().nextInt(0, 10);
                adjacency[i][j] = value;
                adjacency[j][i] = value;
            }
        }
        
        // Cetak Matrix
        System.out.print("\t");
        for (int i = 0; i < numVertex; i++){
            System.out.print("V"+i+" ");
        }
        System.out.println("\n\n");
        
        for (int i = 0; i < adjacency.length; i++){
            System.out.print("V"+i+"\t");
            System.out.println(Arrays.toString(adjacency[i]));
        }
        
        // Problem
        // MST (Minimum Spanning Tree)
        
        // Parameter Algoritma ACO
        double[][] pheromone = new double[numVertex][numVertex]; // Tau
        double[][] visibility = new double[numVertex][numVertex]; // Eta
        int S = 10; // Banyaknya semut
        int NCMax = 5; // Jumlah siklus maksimum
        double alpha = 0.6; // Konstanta pengendali pheromone (α), nilai (α) >=  0
        double beta = 0.5; // Konstanta pengendai intensitas visibilitas (β), nilai (β) >= 0
        double rho = 0.4; // Konstanta penguapan pheromone => Evaporasi
        double Q = 0.5; // Konstanta Siklus semut 
        
        // Persiapan
        // Inisialisasi array Pheromone dan Visibility
        for (int i = 0; i < numVertex; i++){
            for (int j = 0; j < numVertex; j++){
                if(adjacency[i][j] > 0){
                    pheromone[i][j] = 1;
                    visibility[i][j] = 1.0 / (double)adjacency[i][j];
                }
            }
        }
        
        // BEST SOLUTION
        double MIN_TOTAL_WIGHT = Double.MAX_VALUE;
        ArrayList<Edge> bestMSTSolution = null;
        
        // Loop Siklus Semut
        for (int n = 0; n < NCMax; n++){ // Loop Siklus Semut
            double[][] deltaTau = new double[numVertex][numVertex];
            for (int semut = 0; semut < S; semut++){ // loop pencarian oleh semut
                
                int vertex = ThreadLocalRandom.current().nextInt(0, numVertex);
                LinkedList<Integer> visited = new LinkedList<>();
                visited.add(vertex);
                
                // Untuk menyimpan solusi MST
                ArrayList<Edge> mst = new ArrayList<>();
                
                while(visited.size() < numVertex){
                    // Cari Kandidat
                    ArrayList<Edge> candidates = new ArrayList<>();
                    for (int i = 0; i < visited.size(); i++){
                        int origin =visited.get(i);
                        for (int j = 0; j < numVertex; j++){
                            if(!visited.contains(j) && adjacency[origin][j] > 0){
                                int destination = j;
                                double weight = adjacency[origin][destination];
                                Edge e = new Edge(origin, destination, weight);
                                candidates.add(e);
                            }
                        }
                    }
                    
                    // Hitung Probabilitas Semua Kandidat
                    double[] pembilang = new double[candidates.size()];
                    double penyebut =  0;
                    for (int i = 0; i < candidates.size(); i++){
                        Edge e = candidates.get(i);
                        int origin = e.vertexOrigin;
                        int destination = e.vertexDest;
                        double tau = pheromone[origin][destination];
                        double eta = visibility[origin][destination];
                        double tau_eta = Math.pow(tau, alpha) * Math.pow(eta, beta);
                        pembilang[i] = tau_eta;
                        penyebut += tau_eta; // Increment penyebut = SIGMA tau x eta
                    }
                    
                    // Hitung probabilitas semut
                    double[] probabilitasKomulatif = new double[candidates.size()];
                    double totalProbabilitas = 0;
                    for (int i = 0; i < candidates.size(); i++){
                        double probabilitas = pembilang[i] / penyebut;
                        totalProbabilitas += probabilitas;
                        probabilitasKomulatif[i] = totalProbabilitas;
                    }
                    
//                    System.out.println(Arrays.toString(probabilitasKomulatif));
                    double randomProbabilitas = Math.random() * totalProbabilitas;
                    for (int i = 0; i < candidates.size(); i++){
                        if(randomProbabilitas <= probabilitasKomulatif[i]){
                            // selected candidates = i
                            Edge selested = candidates.get(i);
                            mst.add(selested);
                            visited.add(selested.vertexDest);
                            break;
                        }
                    }
                }// end of while
                
                // Hitung Total Jarak yang telah dilalui oleh 1 semut
                double Lk = 0;
                for (int i = 0; i < mst.size(); i++){
                    Lk += mst.get(i).weight;
                }
                
                
                // Temp Solution
                System.out.println("Solusi : "+mst);
                
                // SAVE BEST SOLUTION
                if(Lk < MIN_TOTAL_WIGHT){
                    MIN_TOTAL_WIGHT = Lk;
                    bestMSTSolution = mst;
                }
                
                // Update Delta Tau => Setiap 1 semut selesai maka akan meng-Update Delta Tau
                for (int i = 0; i < mst.size(); i++){
                    Edge e = mst.get(i);
                    int origin = e.vertexOrigin;
                    int destination = e.vertexDest;
                    deltaTau[origin][destination] += (Q / Lk);
                }
                
            }// end of semut
            
            // Update Pheromone (Tau)
            for (int i = 0; i < numVertex; i++){
                for (int j = 0; j < numVertex; j++){
                    pheromone[i][j] = (1.0 - rho) * pheromone[i][j] + deltaTau[i][j]; //rumus update pheromone (1-rho) * tau + p 
                }
            }
            
        } // end loop siklus semut
        
        System.out.println("-------------------------------------------");
        System.out.println("Best Solution : "+bestMSTSolution);
        System.out.println("Total Weight : "+MIN_TOTAL_WIGHT);
        System.out.println("-------------------------------------------");
        
    }//end of main
}
