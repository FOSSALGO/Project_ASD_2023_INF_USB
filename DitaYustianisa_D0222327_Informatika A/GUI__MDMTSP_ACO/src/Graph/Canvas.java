package graph;

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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    ArrayList<Titik> listTitik = new ArrayList<>();
    String url = "datagraph.csv";    
    int MAX_X = 400;
    int MAX_Y = 300;
    double[][] adjacency = null;
    PathDepot[] bestSolution = null;
    double bestDistance = Double.MAX_VALUE;

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
    
    public void resetGraph() {
        listTitik = new ArrayList<>();
        adjacency = null;
        bestSolution = null;
        repaint();
    }

    public void save() {
        if (listTitik != null) {
            int numVertex = listTitik.size();
            try {
                FileWriter fw = new FileWriter(url);
                BufferedWriter bw = new BufferedWriter(fw);
                if (numVertex > 0) {
                    bw.append("numVertex;" + numVertex + "\n");
                    for (Titik t : listTitik) {
                        bw.append(t.x + ";" + t.y + "\n");
                    }
                }
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void open() {
        try {
            FileReader fr = new FileReader(url);
            BufferedReader br = new BufferedReader(fr);
            listTitik = new ArrayList<>();
            String baris = br.readLine();
            String[] kolom = baris.split(";");
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
        } catch (Exception ex) {
            System.out.println("Error di bagian open");
        }
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

    public void hitungMDMTSP() {
        hitungAdjacency();
        if (adjacency != null) {
            int numVertex = adjacency.length;
            //PROBLEM
            // find the shortest path from start vertex to finish vertex
            int D = 3;//D adalah jumlah Depot

            int d0 = 2;//vertex-2 sebagai depot-0
            int d1 = 4;//vertex-2 sebagai depot-1
            int d2 = 5;//vertex-2 sebagai depot-2  
            int[] depot = {d0, d1, d2};

            int M0 = 2;//m adalah jumlah salesman do depot-0
            int M1 = 3;//m adalah jumlah salesman do depot-1
            int M2 = 1;//m adalah jumlah salesman do depot-2
            int[] M = {M0, M1, M2};

            //PARAMETER ALGORITMA ACO
            double[][] pheromone = new double[numVertex][numVertex];//Tau
            double[][] visibility = new double[numVertex][numVertex];//Eta
            int S = 10;//banyaknya semut
            int NCMax = 20;//menyatakan jumlah siklus maksimum
            double alpha = 0.6; //Konstanta pengendali pheromone (α), nilai α ≥ 0. 
            double beta = 0.5; //Konstanta pengendali intensitas visibilitas (β), nilai β ≥ 0. 
            double rho = 0.4; //Konstanta penguapan pheromone
            double Q = 0.5;//Konstanta Siklus Semut

            //PERSIAPAN
            //inisialisasi array pehromone dan visibility
            for (int i = 0; i < numVertex; i++) {
                for (int j = 0; j < numVertex; j++) {
                    if (adjacency[i][j] > 0) {
                        pheromone[i][j] = 1;
                        visibility[i][j] = 1.0 / (double) adjacency[i][j];
                    }
                }
            }

            bestSolution = null;
            bestDistance = Double.MAX_VALUE;

            //LOOP SIKLUS SEMUT
            for (int n = 0; n < NCMax; n++) {//siklus semut
                double[][] deltaTau = new double[numVertex][numVertex];
                for (int semut = 0; semut < S; semut++) {//loop pencarian oleh semut
                    PathDepot[] path = new PathDepot[D];
                    int[] m = new int[D];
                    for (int i = 0; i < D; i++) {
                        path[i] = new PathDepot();
                        path[i].visited.push(depot[i]);
                        m[i] = 1;
                    }

                    //semut mulai mencari lintasan
                    //vertex lain yang harus dikunjungi itu sebanyak numVertex - D
                    int MAX_VISIT = numVertex - D;
                    int nVisited = 0;//banyaknya vertex selain depot yang telah dikunjungi

                    while (nVisited < MAX_VISIT) {
                        //random depot
                        int idDepot = ThreadLocalRandom.current().nextInt(0, D);
                        int origin = path[idDepot].visited.peek();

                        //hitung probabilitas semut ke vertex di depannya
                        int i = origin;
                        double[] pembilang = new double[numVertex];
                        double penyebut = 0;
                        int[] canditate = new int[numVertex];
                        for (int j = 0; j < numVertex; j++) {
                            if (adjacency[i][j] > 0
                                    && canVisited(j, idDepot, path, depot, m, M)) {
                                canditate[j] = 1;
                                double tau = pheromone[i][j];
                                double eta = visibility[i][j];
                                double tau_eta = Math.pow(tau, alpha) * Math.pow(eta, beta);
                                pembilang[j] = tau_eta;
                                penyebut += tau_eta;//increment penyebut = SIGMA tau x eta
                            }
                        }//end of for j

                        //Hitung Probabilitas Semut
                        double[] probabilitasKomulatif = new double[numVertex];
                        double totalProbabilitas = 0;
                        for (int j = 0; j < numVertex; j++) {
                            if (canditate[j] == 1) {
                                double probabilitas = pembilang[j] / penyebut;
                                totalProbabilitas += probabilitas;
                                probabilitasKomulatif[j] = totalProbabilitas;
                            }
                        }//end of for j

                        //double randomProbabilitas = new Random().nextDouble(0, totalProbabilitas);
                        double randomProbabilitas = Math.random() * totalProbabilitas;
                        int destination = i;
                        for (int j = 0; j < numVertex; j++) {
                            if (canditate[j] == 1
                                    && probabilitasKomulatif[j] > 0
                                    && randomProbabilitas < probabilitasKomulatif[j]) {
                                destination = j;
                                break;
                            }
                        }//end of for j

                        //Set Destination
                        if (destination == depot[idDepot]) {
                            if (m[idDepot] < M[idDepot]) {
                                path[idDepot].visited.push(destination);
                                m[idDepot]++;
                            }
                        } else {
                            path[idDepot].visited.push(destination);
                            nVisited++;
                        }

                    }//end of while(nVisited<MAX_VISIT)

                    for (int i = 0; i < D; i++) {
                        path[i].visited.push(depot[i]);
                    }

                    //Hitung Jarak yang telah dilalui oleh semut
                    double Lk = 0;
                    for (int i = 0; i < path.length; i++) {
                        Stack<Integer> visited = path[i].visited;
                        int origin = visited.get(0);
                        for (int j = 1; j < visited.size(); j++) {
                            int destination = visited.get(j);
                            Lk += adjacency[origin][destination];
                            origin = destination;
                        }
                    }

                    //SAVE BEST SOLUTION
                    if (Lk < bestDistance) {
                        bestDistance = Lk;
                        bestSolution = path;
                    }

                    //update delta Tau oleh semut ke-m
                    for (int i = 0; i < path.length; i++) {
                        Stack<Integer> visited = path[i].visited;
                        int origin = visited.get(0);//mula-mula tetapkan vertex start sebagai origin
                        for (int k = 1; k < visited.size(); k++) {
                            int destination = visited.get(k);
                            deltaTau[origin][destination] += (Q / Lk);//update SIGMA delta Tau xy
                        }
                    }

                    //
                }//end of loop for pencarian oleh semut

                //UPDATE PHEROMONE (Tau)
                for (int i = 0; i < numVertex; i++) {
                    for (int j = 0; j < numVertex; j++) {
                        pheromone[i][j] = (1.0 - rho) * pheromone[i][j] + deltaTau[i][j];
                    }
                }

            }//end of for loop siklus semut

            //Cetak Solusi
            //cetak best solution
//            System.out.println("----------------------------------------------------");
//            System.out.println("BEST SOLUTION");
//            System.out.println("jarak: " + bestDistance);
//            for (int i = 0; i < bestSolution.length; i++) {
//                System.out.println(bestSolution[i].visited);
//            }
//            System.out.println("----------------------------------------------------");
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        //----------------------------------------------------------------------    
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);
        //---------------------------------------------------------------------- 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //----------------------------------------------------------------------  
        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));
        //----------------------------------------------------------------------

        //gambar edges
        for (int i = 0; i < listTitik.size(); i++) {
            int x0 = listTitik.get(i).x;
            int y0 = listTitik.get(i).y;
            for (int j = 0; j < i; j++) {
                int x1 = listTitik.get(j).x;
                int y1 = listTitik.get(j).y;
                g2d.setColor(Color.black);
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
                g2d.drawLine(x0, y0, x1, y1);
            }
        }

        for (int i = 0; i < listTitik.size(); i++) {
            //gambar vertex
            g2d.setColor(Color.GREEN);
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
            int xo = listTitik.get(i).x;
            int yo = listTitik.get(i).y;
            g2d.fillOval(xo - 10, yo - 10, 20, 20);
            g2d.setColor(Color.RED);
            g2d.drawString("V" + i, xo, yo - 10);
        }

        //gambar solusi
        if (bestSolution != null) {
            g2d.setColor(Color.RED);
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5F));
            g2d.setStroke(new BasicStroke(10));
            for (int i = 0; i < bestSolution.length; i++) {
                Stack<Integer> visited = bestSolution[i].visited;
                int origin = visited.get(0);
                for (int j = 1; j < visited.size(); j++) {
                    int destination = visited.get(j);
                    int x0 = listTitik.get(origin).x;
                    int y0 = listTitik.get(origin).y;
                    int x1 = listTitik.get(destination).x;
                    int y1 = listTitik.get(destination).y;
                    g2d.drawLine(x0, y0, x1, y1);
                    origin = destination;
                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xo = (int) (e.getX() - translateX / scaleX);
        int yo = (int) (e.getY() - translateY / scaleY);
        listTitik.add(new Titik(xo, yo));
        System.out.println("Titik: " + listTitik);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // capture titik start x dan y
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // defenisikan posisi x dan y yang baru
        // hitung translasi x dan y
        int newX = e.getX() - lastOffsetX;
        int newY = e.getY() - lastOffsetY;

        // increment last offset oleh even drag mouse
        lastOffsetX += newX;
        lastOffsetY += newY;

        // update posisi canvas
        //tx.translate(tx.getTranslateY() + newX, tx.getTranslateY() + newY);
        translateX += newX;
        translateY += newY;

        System.out.println("Click e: " + e.getX() + ", " + e.getY() + "; translate: " + translateX + ", " + translateY);

        // schedule a repaint.
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean canVisited(int vertex, int idDepot, PathDepot[] path, int[] depot, int[] m, int[] M) {
        boolean result = true;
        int indexDepot = -1;
        for (int i = 0; i < depot.length; i++) {
            if (depot[i] == vertex) {
                indexDepot = i;
                break;
            }
        }
        if (indexDepot >= 0) {
            if (indexDepot != idDepot) {
                result = false;
            } else {
                if (m[idDepot] >= M[idDepot]) {
                    result = false;
                }
            }
        } else {
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

}//end class canvas

class PathDepot {

    Stack<Integer> visited = new Stack<>();
}

class Titik {

    public int x, y;

    public Titik(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Titik{" + "x=" + x + ", y=" + y + '}';
    }
}