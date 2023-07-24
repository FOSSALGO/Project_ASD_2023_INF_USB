package rahmtia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class AntColonyOptimization {

    public static void main(String[] args) {
        //Geberate Graph
        int numVertex = 8;
        int[][] adjacency = new int[numVertex][numVertex];
        for (int i = 0; i < numVertex; i++) {
            adjacency[i][i] = 0;
            for (int j = 0; j < numVertex; j++) {
                int value = ThreadLocalRandom.current().nextInt(1, 10);
                adjacency[i][j] = value;
                adjacency[j][i] = value;
            }
        }
        System.out.print(" \t");
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

        int start = 1;
        int finish = 5;

        double[][] pheromone = new double[numVertex][numVertex];
        double[][] visibility = new double[numVertex][numVertex];
        int S = 3;
        int NCMax = 5;
        double alpha = 0.6;
        double beta = 0.5;
        double rho = 0.4;
        double Q = 0.5;

        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                if (adjacency[i][j] > 0) {
                    pheromone[i][j] = 1;
                    visibility[i][j] = 1.0 / (double) adjacency[i][j];
                }
            }
        }

        ArrayList<Integer> bestSolution = null;
        double bestDistance = Double.MAX_VALUE;

        for (int n = 0; n < NCMax; n++) {
            double[][] deltaTau = new double[numVertex][numVertex];
            for (int semut = 0; semut < S; semut++) {
                Stack<Integer> visited = new Stack<>();
                visited.push(start);

                while (true) {
                    int origin = visited.peek();
                    if (origin == finish) {
                        System.out.println("Solusi" + visited);

                        double jarak = 0;
                        int ori = visited.get(0);
                        for (int i = 0; i < visited.size(); i++) {
                            int dest = visited.get(i);
                            jarak += adjacency[ori][dest];
                            ori = dest;
                        }
                        if (jarak < bestDistance) {
                            bestDistance = jarak;
                            bestSolution = new ArrayList<>();
                            for (int i = 0; i < visited.size(); i++) {
                                bestSolution.add(visited.get(i));
                            }
                        }
                        break;
                    } else {
                        int i = origin;
                        double[] pembilang = new double[numVertex];
                        double penyebut = 0;
                        int[] candidate = new int[numVertex];
                        for (int j = 0; j < numVertex; j++) {
                            if (adjacency[i][j] > 0 && !visited.contains(j)) {
                                candidate[j] = 1;
                                double tau = pheromone[i][j];
                                double eta = visibility[i][j];
                                double tau_eta = Math.pow(tau, alpha) * Math.pow(eta, beta);
                                pembilang[j] = tau_eta;
                                penyebut += tau_eta;
                            }
                        }

                        double[] probabilityKomulatif = new double[numVertex];
                        double totalProbability = 0;
                        for (int j = 0; j < numVertex; j++) {
                            if (candidate[j] == 1) {
                                double probability = pembilang[j] / penyebut;
                                totalProbability += probability;
                                probabilityKomulatif[j] = totalProbability;
                            }
                        }

                        int destination = origin;
                        double randomProbability = new Random().nextDouble() * totalProbability;
                        for (int j = 0; j < numVertex; j++) {
                            if (candidate[j] == 1 && probabilityKomulatif[j] > 0 && randomProbability < probabilityKomulatif[j]) {
                                destination = j;
                                break;
                            }
                        }
                        if (destination != origin) {
                            visited.push(destination);
                        } else {
                            visited.pop();
                        }
                    }
                }

                double Lk = 0;
                int origin = visited.get(0);
                for (int i = 0; i < visited.size(); i++) {
                    int destination = visited.get(i);
                    double jarakAntarVertex = adjacency[origin][destination];
                    Lk = jarakAntarVertex;
                    origin = destination;
                }

                origin = visited.get(0);
                for (int k = 1; k < visited.get(0); k++) {
                    int destination = visited.get(k);
                    deltaTau[origin][destination] += (Q / Lk);
                }
                //UPDATE PHEROMONE
                for (int i = 0; i < numVertex; i++) {
                    for (int j = 0; j < numVertex; j++) {
                        pheromone[i][j] = (1.0 - rho) * pheromone[i][j] + deltaTau[i][j];
                    }
                }
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("SOLUSI ANT COLONY OPTIMIZATION ");
        System.out.println(bestSolution);
        System.out.println("Jarak : " + bestDistance);
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}

    