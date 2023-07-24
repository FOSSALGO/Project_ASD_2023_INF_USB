package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GA_Graph {

    public static void cetakArray(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            } 
            System.out.println();
        }
    }
    public static int hitungJarak(int[][] adjacency, int[] rute) {
        int jarak = 0;
        int start = rute[0];
        for (int i = 1 ; i < rute.length; i++) {
            int origin = start;
            int tujuan = rute[i];
            int nilai= adjacency[start][tujuan];
            jarak+=nilai;
            origin=tujuan;
        }
        return jarak;
    }
    public static double hitungFitness(int jarak) {
        double fitness = 1.0/jarak;
        return fitness;
    }
    public static void urutkanFitness(double[][] data) {
        int indek_min;
        double tampungindex;
        double tampungfitnes;
        for (int i = 0; i < data.length; i++) {
            indek_min = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j][1] > data[indek_min][1]) {
                    indek_min = j;
                }
            }
            if (indek_min != i) {
                tampungindex = data[indek_min][0];
                tampungfitnes = data[indek_min][1];

                data[indek_min][0] = data[i][0];
                data[indek_min][1] = data[i][1];

                data[i][0] = tampungindex;
                data[i][1] = tampungfitnes;
            }
        }
    }
   

    public static void main(String[] args) {
            
       int[][] adjacencyMatrix = {
            {0, 1, 2, 6, 9},
            {1, 0, 8, 3, 4},
            {2, 8, 0, 10, 5},
            {6, 3, 10, 0, 7},
            {9, 4, 5, 7, 0}
        };

        int generasi = 20;
        int jumlahPopulasi = 20;
        int jumlahIndividuTerseleksi = 15;
        int panjangKromosom = adjacencyMatrix[0].length;
        double probMutasi = 0.5;
        int [] kromosomTerbaik = null;
        int jarakTerbaik = 0;
        double fitnessterbaik = Double.MIN_VALUE;

        int[][] populasiAwal = new int[jumlahPopulasi][panjangKromosom];

        //Membangkitkan Populasi Awal
        for (int i = 0; i < jumlahPopulasi; i++) {
            ArrayList<Integer> kromosom = new ArrayList<>();
            int index = 0;
            while (kromosom.size() < panjangKromosom) {
                int nilai = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                if (!kromosom.contains(nilai)) {
                    if (index >= panjangKromosom) {
                        index = 0;
                    }
                    if (index < panjangKromosom) {
                        kromosom.add(nilai);
                        populasiAwal[i][index] = nilai;
                        index++;
                    }
                }
            }
        }
        //Memasukkan Barang dan Menghitung Nilai fitness
        double[][] dataFitnessAwal = new double[jumlahPopulasi][2];
        for (int i = 0; i < jumlahPopulasi; i++) {
           
            int[] individu = populasiAwal[i];
            int jarak = hitungJarak(adjacencyMatrix, individu);
            double fitness = hitungFitness(jarak);
            dataFitnessAwal[i][0] = i;
            dataFitnessAwal[i][1] = fitness;
        }
        //Mengurutkan Nilai Fitness
        urutkanFitness(dataFitnessAwal);
        int[][] populasi = new int[jumlahPopulasi][panjangKromosom];
        for (int i = 0; i < jumlahPopulasi; i++) {
            int index = (int) dataFitnessAwal[i][0];
            populasi[i] = populasiAwal[index];
        }

        //Proses elitism
        for (int i = 0; i < populasi.length; i++) {
            int [] barang = populasi[i];
            int jarak = hitungJarak(adjacencyMatrix, barang);
            double fitness = hitungFitness(jarak);
            if(fitness>fitnessterbaik){
                jarakTerbaik = jarak;
                kromosomTerbaik = barang;
                fitnessterbaik = fitness;
            }
        }
        //Proses Evolusi
        for (int g = 1; g < generasi; g++) {
            int [][] populasiBaru = new int[jumlahPopulasi][panjangKromosom];

            //Seleksi Turnamen
            for (int i = 0; i < jumlahIndividuTerseleksi; i++) {
                populasiBaru[i] = populasi[i];
            }
            //Melengkapi Populasi
            for (int i = jumlahIndividuTerseleksi; i < populasiBaru.length ; i++) {
                ArrayList<Integer> kromosom = new ArrayList<>();
                int index = 0;
                while (kromosom.size() < panjangKromosom) {
                    int nilai = ThreadLocalRandom.current().nextInt(0, panjangKromosom );
                    if (!kromosom.contains(nilai)) {
                        if (index >= panjangKromosom) {
                            index = 0;
                        }
                        if (index < panjangKromosom) {
                            kromosom.add(nilai);
                            populasiBaru[i][index] = nilai;
                            index++;
                        }
                    }
                }
            }

            //Mutasi
            double [][] dataFitnesBaru = new double[jumlahPopulasi][2];
            for (int i = 0; i < populasiBaru.length; i++) {
                double random = Math.random();
                if(random>=probMutasi){
                    int index1 = ThreadLocalRandom.current().nextInt(0,panjangKromosom);
                    int index2 = ThreadLocalRandom.current().nextInt(0,panjangKromosom);
                    while(index1==index2){
                        int index = ThreadLocalRandom.current().nextInt(0,panjangKromosom);
                        if(index!=index1){
                            index2=index;
                        }
                    }
                    //Swap Gen Yang Telah Dipilih
                    int [] individu = populasiBaru[i];
                    int temp = individu[index1];
                    individu[index1]=individu[index2];
                    individu[index2] = temp;
                }
               
                int [] barang = populasiBaru[i];
                int jarak = hitungJarak(adjacencyMatrix, barang);
                double fitness = hitungFitness(jarak);
                dataFitnesBaru[i][0] = i;
                dataFitnesBaru[i][1] = fitness;
            }

            urutkanFitness(dataFitnesBaru);//Mengurutkan Individu Berdasarkan Nilai Fitness

            //Mengurutkan Populasi Berdasarkan Urutan Data Nilai Fitness Yang telah Diurutkan
            populasi = new int[jumlahPopulasi][panjangKromosom];
            for (int i = 0; i < jumlahPopulasi; i++) {
                int index = (int) dataFitnessAwal[i][0];
                populasi[i] = populasiBaru[index];
            }

            for (int i = 0; i < populasi.length; i++) {
              
                int [] barang = populasi[i];
                int jarak = hitungJarak(adjacencyMatrix, barang);
                double fitness = hitungFitness(jarak);
                if(fitness>fitnessterbaik){
                    jarakTerbaik = jarak;
                    kromosomTerbaik = barang;
                    fitnessterbaik = fitness;
                }
            }
        }//End Of Evolusi

        System.out.println(Arrays.toString(kromosomTerbaik));
        System.out.println(jarakTerbaik);

    }

    private static class Barang {

        public Barang() {
        }

        private Barang(int i, int i0) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}