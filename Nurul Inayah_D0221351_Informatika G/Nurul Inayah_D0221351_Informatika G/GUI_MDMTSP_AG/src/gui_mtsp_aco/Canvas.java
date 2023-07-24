package gui_mtsp_aco;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private int translateX;
    private int translateY;
    private double scaleX;
    private double scaleY;
    private int lastOffsetX;
    private int lastOffsetY;

    // private int Xo , Yo;
    ArrayList<Titik> listTitik = new ArrayList<>();
    String url = "datagraph.csv";
    int MAX_X = 400;
    int MAX_Y = 300;
    double[][] adjacency = null;
    Individu bestIndividu = null;
    double bestFitness = 0;

    public Canvas() {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        AffineTransform tx = gc.getDefaultTransform();
        System.out.println("SCALE X: " + tx.getScaleX());
        System.out.println("SCALE Y: " + tx.getScaleY());
        translateX = 0;
        translateY = 0;
        scaleX = tx.getScaleX();
        scaleY = tx.getScaleY();
        setOpaque(false);
        setDoubleBuffered(true);
        //set handler
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void randomVertex(int n) {
        listTitik = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, MAX_X);
            int y = ThreadLocalRandom.current().nextInt(0, MAX_Y);
            listTitik.add(new Titik(x, y));
        }
        repaint();
    }

    public double[][] hitungAdjacency() {
        adjacency = null;
        if (listTitik.size() > 0) {
            int numVertex = listTitik.size();
            adjacency = new double[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++) {
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++) {
                    double x0 = listTitik.get(i).x;
                    double y0 = listTitik.get(i).y;
                    double x1 = listTitik.get(j).x;
                    double y1 = listTitik.get(j).y;

                    double x0x1 = x0 - x1;
                    double xox1Kuadrat = x0x1 * x0x1;

                    double y0y1 = y0 - y1;
                    double yoy1Kuadrat = y0y1 * y0y1;

                    double jumlahKuadrat = xox1Kuadrat + yoy1Kuadrat;

                    double value = Math.sqrt(jumlahKuadrat);//hitung jarak antar vertex
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
        }
        return adjacency;
    }

    public void RunAlgoritmaACO_MTSP() {
        hitungAdjacency();
        if (adjacency != null) {
            int numVertex = adjacency.length;
            //PROBLEM
            // find the shortest path from start vertex to finish vertex
            int D = 3;//D adalah jumlah Depot

            int d0 = 2;//vertex-2 sebagai depot-0
            int d1 = 4;//vertex-2 sebagai depot-1
            int d2 = 7;//vertex-2 sebagai depot-2  
            int[] depot = {d0, d1, d2};

            int M0 = 2;//m adalah jumlah salesman do depot-0
            int M1 = 3;//m adalah jumlah salesman do depot-1
            int M2 = 1;//m adalah jumlah salesman do depot-2
            int[] M = {M0, M1, M2};

            // PARAMETER GA
            int MAX_GENERATION = 20; //maksimum iterasi dari proses awal 
            int numPopulasi = 10;// jumlah individu dalam satu populasi 
            double mutationRate = 0.6;
            int numberofMutationPoint = 6;

            // SAVE BEST SOLUTION 
            Individu bestIndividu = null;
            double bestFitness = 0;

            // Generate Populasi Awal 
            Individu[] populasi = new Individu[numPopulasi];
            for (int p = 0; p < numPopulasi; p++) {
                populasi[p] = new Individu();
                populasi[p].generate(depot, M, adjacency );
                populasi[p].hitungFitness(adjacency);
                if (populasi[p].fitness > bestFitness) {
                    bestIndividu = populasi[p];
                    bestFitness = populasi[p].fitness;
                }
            }

            for (int g = 0; g <= MAX_GENERATION; g++) {

                for (int i = 0; i < numPopulasi; i++) {
                    double rm = Math.random();
                    if (rm < mutationRate) {
                        // MUTATION------------------------------------------------- 
                        populasi[i].mutation(numberofMutationPoint);
                        populasi[i].hitungFitness(adjacency);
                    }

                    //ELITISM-------------------------------------------------------
                    if (populasi[i].fitness > bestFitness) {
                        bestIndividu = populasi[i];
                        bestFitness = populasi[i].fitness;
                    }

                }
            }// end of loop proses evelosi

        }
    }// end RunAlgoritmaACO_MTSP

    public void paint(Graphics g) {
        //---------------------------------------------
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);
        Graphics2D g2d = (Graphics2D) g;
        //-------------------------------------------
        // gambar background
        g2d.setColor(Color.decode("#D6E8DB"));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // gambar edge
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));
        g2d.setColor(Color.decode("#FFF8DE"));
        for (int i = 0; i < listTitik.size(); i++) {
            for (int j = 0; j < i; j++) {
                Titik v0 = listTitik.get(i);
                int x0 = v0.x;
                int y0 = v0.y;

                Titik v1 = listTitik.get(j);
                int x1 = v1.x;
                int y1 = v1.y;
                g2d.drawLine(x0, y0, x1, y1);
            }

        }
        // gambar Vertex

        for (int i = 0; i < listTitik.size(); i++) {
            Titik titik = listTitik.get(i);
            int xo = titik.x;
            int yo = titik.y;
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));
            g2d.setColor(Color.decode("#99A98F"));
            g2d.fillOval(xo - 10, yo - 10, 20, 20);

            g2d.setColor(Color.red);
            g2d.drawString("V" + i, xo, yo - 10);

        }
        // gambar Solusi
        //g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
        g2d.setColor(Color.decode("#99A98F"));
        g2d.setStroke(new BasicStroke(6));
        if (bestSolution != null) {
            int origin = bestSolution.get(0);
            for (int i = 1; i < bestSolution.size(); i++) {
                int destination = bestSolution.get(i);
                Titik v0 = listTitik.get(origin);
                int x0 = v0.x;
                int y0 = v0.y;

                Titik v1 = listTitik.get(destination);
                int x1 = v1.x;
                int y1 = v1.y;
                g2d.drawLine(x0, y0, x1, y1);
                origin = destination;

            }

        }

    }

    public void save() {
        try {

            FileWriter fw = new FileWriter(url);
            BufferedWriter bw = new BufferedWriter(fw);
            if (listTitik.size() > 0) {
                bw.append("numVertex:" + listTitik.size() + "\n");
                for (Titik t : listTitik) {
                    bw.append(t.x + ";" + t.y + "\n"); // ini untuk menyimpan titiknya

                }

            }
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Open() {
        try {
            FileReader fr = new FileReader(url);
            BufferedReader br = new BufferedReader(fr);
            listTitik = new LinkedList<>();
            String baris = br.readLine();
            String[] kolom = baris.split(":");
            int numVertex = Integer.parseInt(kolom[1]);
            for (int i = 0; i < numVertex; i++) {
                baris = br.readLine();
                kolom = baris.split(";");
                int x = Integer.parseInt(kolom[0]);
                int y = Integer.parseInt(kolom[1]);

                listTitik.add(new Titik(x, y));

            }
            repaint();

            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xo = (int) (e.getX() - translateX / scaleX);
        int yo = (int) (e.getY() - translateY / scaleY);
        listTitik.add(new Titik(xo, yo));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = e.getX() - lastOffsetX;
        int newY = e.getY() - lastOffsetY;

        lastOffsetX += newX;
        lastOffsetY += newY;

        translateX += newX;
        translateY += newY;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Chromosome {

    Stack<Integer> gens = new Stack<>();

}

class Individu {

    Chromosome[] chromosome = null;// menampung urutan rute yang di lakukan oleh salesmen dari setiap depot 
    int[] depot = null; // titik yang menjadi depot 
    int[] M = null; // nilai salemen di setiap depot 

    // OUTPUT 
    public double totalJarak = -1;
    public double fitness = -1;

    public Chromosome[] generate(int[] depot, int[] M, int[][] adjacency) {// M jumlah salesemn setiap depot

        this.depot = depot;
        this.M = M;
        int D = depot.length;
        chromosome = new Chromosome[D]; // membuat objek nama chromosome dengan memakai kela chromosome 
        int[] m = new int[D];// salesmen yang sudah di pake 

        // membuat daftar jalur yang akan di lewati  salemen di dalam satu depot 
        for (int i = 0; i < D; i++) {   // cromosom kan per depot 
            chromosome[i] = new Chromosome();//  diinisialisasi sebagai objek Chromosome baru  
            chromosome[i].gens.push(depot[i]); // menambahkan nilai jalur depot  ke dalam stack yang berada di dalam stack yang bernama gens 
            m[i] = 1;// penenanda bahwa ada salesemen satu yang bekerja 

        }
        int numVertex = adjacency.length;
        int MAX_VISIT = numVertex - D;// maksimal kunjungan di kurang depot krn kan 3 titik sudah jadi titik depot atau asal
        int nVisited = 0; // "nVisited" akan mencatat berapa banyak pelanggan yang telah dikunjungi 

        // isi kromosom
        while (nVisited < MAX_VISIT) {
            int indexDepot = ThreadLocalRandom.current().nextInt(0, D);// random titik depot mana yang mau di sisi 
            int origin = chromosome[indexDepot].gens.peek();// menampilkan titik awak atau depot 
            int destination = -1; //titik yang di kunjungi 
            while (destination < 0) {
                int candidate = ThreadLocalRandom.current().nextInt(0, numVertex);// kandidate kunjungan yang nilai random sampai nilai numvertex
                if (adjacency[origin][candidate] > 0 && canBeVisited(candidate, indexDepot, chromosome, depot, m, M)) {
                    if (candidate == depot[indexDepot]) {
                        destination = candidate;
                        m[indexDepot]++;
                    } else {
                        destination = candidate;
                        nVisited++;
                    }
                }
            }
            if (destination >= 0) {
                chromosome[indexDepot].gens.push(destination);
            }

        }
        for (int i = 0; i < D; i++) {
            chromosome[i].gens.push(depot[i]);

        }
        return chromosome;
    }

    public double hitungJarak(int[][] djacency) {
        // hitung total jarak 

        if (chromosome != null) {
            totalJarak = 0;
            for (int i = 0; i < chromosome.length; i++) {
                Stack<Integer> gens = chromosome[i].gens;
                int origin = gens.get(0);
                for (int j = 0; j < gens.size(); j++) {
                    int destination = gens.get(j);
                    totalJarak += djacency[origin][destination];
                    origin = destination;
                }
            }
        }
        return totalJarak;
    }

    public double hitungFitness() {
        if (totalJarak > 0) {
            fitness = 1 / totalJarak;
        }
        return fitness;
    }

    public double hitungFitness(int[][] adjacency) {
        totalJarak = hitungJarak(adjacency);
        fitness = hitungFitness();
        return fitness;
    }

    public void mutation(int numberofMutationPoint) {
        if (chromosome != null && depot != null) {
            // lakukan mutasi antar chromosome sebanyak numberofMutationPoint
            for (int n = 0; n < numberofMutationPoint; n++) {
                int c1 = ThreadLocalRandom.current().nextInt(0, chromosome.length);
                int c2 = c1;
                while (c1 == c2) {
                    c2 = ThreadLocalRandom.current().nextInt(0, chromosome.length);
                }

                // pilih titik mutasi di depot c1
                int titiMutasiC1 = 0;
                while (chromosome[c1].gens.size() > M[c1] + 1 && chromosome[c1].gens.get(titiMutasiC1) == depot[c1]) {
                    titiMutasiC1 = ThreadLocalRandom.current().nextInt(1, chromosome[c1].gens.size());
                }

                // pilih titik mutasi di depot c2
                int titiMutasiC2 = 0;
                while (chromosome[c2].gens.size() > M[c1] + 1 && chromosome[c2].gens.get(titiMutasiC2) == depot[c2]) {
                    titiMutasiC2 = ThreadLocalRandom.current().nextInt(1, chromosome[c2].gens.size());
                }

                // SWAP
                int temp = chromosome[c1].gens.get(titiMutasiC1);
                chromosome[c1].gens.set(titiMutasiC1, chromosome[c2].gens.get(titiMutasiC2));
                chromosome[c2].gens.set(titiMutasiC2, temp);
            }
        }
    }

    public static boolean canBeVisited(int candidate, int idDepot, Chromosome[] chromosome, int[] depot, int[] m, int[] M) {
        boolean result = true;
        int indexDepot = -1;
        for (int i = 0; i < depot.length; i++) { // di gunakan untuk mengecek apakah itu depot 
            if (depot[i] == candidate) {// apakah kandidate itu merupakan titik depot kalo iya langsung break
                indexDepot = i; //[[1][1]]arinya salemenya mengaggur
                break;
            }
        }
        if (indexDepot >= 0) {
            //depot
            if (indexDepot != idDepot) {// jika indeks depot yang sudah di update tidak sesai idex maka salah 
                result = false;
            } else {
                if (m[idDepot] >= M[idDepot]) {// na cek ada ji kah salesmennya dan mengecek maksimal dari salesemn dari depot 
                    result = false;
                }
            }
        } else {
            //bukan depot
            for (int i = 0; i < chromosome.length; i++) {
                if (chromosome[i].gens.contains(candidate)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        String info = "";
        if (chromosome != null) {
            for (int i = 0; i < chromosome.length; i++) {
                info = "chromosome-" + i + "[depot = " + depot[i] + "] :" + chromosome[i].gens;
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(info);
            }
        } else {
            info = "NULL";
            sb.append(info);
        }
        return sb.toString();
    }

 
}
