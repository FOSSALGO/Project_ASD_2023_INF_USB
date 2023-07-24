package ACO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmaACO_TSP {
    public static void main(String[] args) {
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
        //cetak adjacency
        System.out.print(" \t");
        for(int i = 0; i < adjacency.length; i++){
            System.out.print("V"+(i)+"\t");
        }

        System.out.println();
        for (int i = 0; i < adjacency.length; i++){
            System.out.print("V" + (i)+ "\t");
            for (int j = 0; j < adjacency[i].length; j++) {
                System.out.print(adjacency[i][j] + "\t");
            }
            System.out.println();
        }
        //Problem
        int depot = 4; // start & finish di v4

        //PARAMETER ACO
        double [][] pheromone = new double[numVertex][numVertex]; //tau
        double [][] visibility = new double[numVertex][numVertex]; //eta
        int S = 20; //BANYAKNYA SEMUT
        int NCMax = 30; //menyatakan jumlah siklus maksimum
        double alpha = 0.6; // konstanta pengendali pheromone (), nilai a >= 0
        double beta = 0.5; //konstanta pengendali intensitas visibilitas
        double rho = 0.4; //konstanta penguapan pheromon
        double Q = 0.5; //konstanta siklus semut

        //PERSIAPAN
        //Inisialisasi array pheromone  dan visibility
        for (int i = 0; i < numVertex; i++){
            for (int j = 0; j < numVertex; j++){
                if (adjacency[i][j] > 0){
                    pheromone[i][j] = 1;
                    visibility[i][j] = 1.0/ (double) adjacency[i][j];
                }
            }
        }
        //cetak visibility
        System.out.println("------------------------");
        System.out.println("VISIBILTY");
        for (int i = 0; i < numVertex; i++){
            System.out.println(Arrays.toString(visibility[i]));
        }
        System.out.println("------------------------");
        //cetak pheromone
        System.out.println("pheromone");
        for (int i=0; i < numVertex; i++){
            System.out.println(Arrays.toString(pheromone[i]));
        }
        System.out.println("---------------------------------");
        ArrayList<Integer> bestSolution = null;
        double bestDistance = Double.MAX_VALUE;

        //looop siklus semut
        for (int n = 0; n < NCMax; n++){ //siklus semut
            double[][] deltaTau = new double[numVertex][numVertex];
            for (int semut = 0; semut < S; semut++){
                Stack<Integer> visited = new Stack<>();
                visited.push(depot);

                while (true){
                    int origin = visited.peek();
                    if (visited.size() >= numVertex) {
                        //pencarian berakhir krn jlur ditemukan
                        visited.add(depot);
                        System.out.println("solusi: "+visited);
                        // JARAK yang dilalui semut sampai ke titik finish
                        double jarak =0;
                        int ori = visited.get(0);
                        for( int i = 1; i < visited.size();i++){
                            int dest = visited.get(i);
                            jarak += adjacency[ori][dest];
                            ori = dest;
                        }
                        System.out.println("jarak :"+jarak);

                        if(jarak < bestDistance){
                            bestDistance = jarak;
                            bestSolution = new ArrayList<>();
                            for (int i = 0; i <visited.size(); i++){
                                bestSolution.add(visited.get(i));
                            }
                        }
                        break;
                    }else{
                        int i =origin;
                        double [] pembilang= new double[numVertex];
                        double penyebut = 0;
                        int[] candidate = new int[numVertex];
                        for (int j = 0; j < numVertex; j++){
                            if (adjacency[i][j] > 0 && !visited.contains(j)) {
                                candidate[j] = 1;
                                double tau = pheromone[i][j];
                                double eta = visibility[i][j];
                                double tau_eta = Math.pow(tau, alpha)* Math.pow(eta, beta);
                                pembilang[j] = tau_eta;
                                penyebut += tau_eta; //increment penyebut = SIGMA tau x eta
                            }
                        }
                        //hitung probabilitas semut
                        double[] probabilitasKomulatif = new double[numVertex];
                        double totalProbabilitas = 0;
                        for (int j = 0; j < numVertex; j++){
                            if (candidate[j] == 1){
                                double probabilitas = pembilang[j]/ penyebut;
                                totalProbabilitas += probabilitas;
                                probabilitasKomulatif[j] = totalProbabilitas;
                            }
                        }
                        //tetapkan dstinasi
                        int destination =  origin;
                        double randomProbabilitas = new Random().nextDouble()* totalProbabilitas;
                        for (int j = 0; j < numVertex; j++){
                            if (candidate[j] == 1 && probabilitasKomulatif[j] > 0 && randomProbabilitas < probabilitasKomulatif[j]){
                                destination = j;
                                break;
                            }
                        }
                        if (destination != origin) {//menemukan tujuan baru
                            visited.push(destination);
                        }else{
                            visited.pop(); //ada tambahan operasi backtracking
                        }
                    }
                }
                //hitung jarak yang telah dilalui
                double Lk =0;
                int origin = visited.get(0);
                for( int i = 1; i < visited.size();i++){
                    int destination = visited.get(i);
                    Lk += adjacency[origin][destination];
                    origin = destination;
                }
                //update delta tau oleh semut ke-m
                origin = visited.get(0); //mula mula tetapkan vertex start sebagai origin
                for (int k = 1; k < visited.size(); k++){
                    int destination = visited.get(k);
                    deltaTau[origin][destination] += (Q/ Lk); //update SIGMA delta tau xy
                }
                //UPDATE PHEROMONE            
                for (int i = 0; i < numVertex; i++) {
                    for (int j = 0; j < numVertex; j++) {
                        pheromone[i][j] = (1.0 - rho) * pheromone[i][j] + deltaTau[i][j];
                    }
                }


            }//end of pencarian oleh semut

        }// end siklus semut
        System.out.println("-------------------------------------------");
        System.out.println("solusi ACO");
        System.out.println(bestSolution);
        System.out.println("jarak: "+bestDistance);
        System.out.println("-------------------------------------------");
    }
}