package mst;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private int translateX, translateY, lastOffsetX, lastOffsetY;
    private double scaleX, scaleY;

    ArrayList<Point> titik = new ArrayList<>();
    double[][] adjacency = null;
    int MAX_X = 400;
    int MAX_Y = 300;
    String url = "datagraph.csv";
    
    // BEST SOLUTION
    double MIN_TOTAL_WIGHT;
    ArrayList<Edge> bestMSTSolution = null;

    public Canvas() {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        AffineTransform tx = gc.getDefaultTransform();
        System.out.println("Scale X : " + tx.getScaleX());
        System.out.println("Scale Y : " + tx.getScaleY());
        translateX = 0;
        translateY = 0;
        scaleX = tx.getScaleX();
        scaleY = tx.getScaleY();
        setOpaque(false);
        setDoubleBuffered(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    // reset canvas
    public void resetGraph(){
        titik = new ArrayList<>();
        adjacency = null;
        bestMSTSolution = null;
        repaint();
    }
    
    // Save Graph
    public void save() {
        if (titik != null) {
            int numVertex = titik.size();
            try {
                FileWriter fw = new FileWriter(url);
                BufferedWriter bw = new BufferedWriter(fw);
                if (numVertex > 0) {
                    bw.append("numVertex;" + numVertex + "\n");
                    for (Point t : titik) {
                        bw.append(t.x + ";" + t.y + "\n");
                    }
                }
                bw.close();
                fw.close();
            } catch (Exception e) {
                System.out.println("Save Error");
            }

        }
    }
    
    // open
    public void open() {
        try {
            FileReader fr = new FileReader(url);
            BufferedReader br = new BufferedReader(fr);
            titik = new ArrayList<>();
            String baris = br.readLine();
            String[] kolom = baris.split(";");
            int numVertex = Integer.parseInt(kolom[1]);
            for (int i = 0; i < numVertex; i++) {
                baris = br.readLine();
                kolom = baris.split(";");
                int x = Integer.parseInt(kolom[0]);
                int y = Integer.parseInt(kolom[1]);
                titik.add(new Point(x, y));
            }
            repaint();
            br.close();
            fr.close();
        } catch (Exception ex) {
            System.out.println("");
        }
    }
    
    // Reset
    public void randomVertex(int n){
        titik = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, MAX_X);
            int y = ThreadLocalRandom.current().nextInt(0, MAX_Y);
            titik.add(new Point(x,y));
        }
        repaint();
    }

    public void paint(Graphics g) {
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.decode("#eaeaea"));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Gambar Edge
        for (int i = 0; i < titik.size(); i++)
        {
            int x0 = titik.get(i).x;
            int y0 = titik.get(i).y;
            for (int j = 0; j < i; j++)
            {
                int x1 = titik.get(j).x;
                int y1 = titik.get(j).y;
                g2d.setColor(Color.decode("#E966A0"));
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
                g2d.drawLine(x0, y0, x1, y1);
            }
        }

        //gambar vertex
        int idx = 0;
        for (Point point : titik)
        {
            g2d.setColor(Color.decode("#090580"));
            g2d.setComposite(AlphaComposite.SrcOver.derive(1F));
            int x0 = point.x;
            int y0 = point.y;
            g2d.fillOval(x0 - 5, y0 - 5, 10, 10);
            g2d.setColor(Color.BLUE);
            g2d.drawString("V" + idx, x0, y0 - 15);
            idx++;
        }
        
        // Gambar Solusi
        if (bestMSTSolution != null){
            g2d.setColor(Color.decode("#6554AF"));
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5F));
            g2d.setStroke(new BasicStroke(10));
            for (Edge e : bestMSTSolution){
                int origin = e.vertexOrigin;
                int dest = e.vertexDest;
                
                int x0 = titik.get(origin).x;
                int y0 = titik.get(origin).y;
                int x1 = titik.get(dest).x;
                int y1 = titik.get(dest).y;
                g2d.drawLine(x0, y0, x1, y1);
            }
        }
        repaint();
    }

    // Generate Adjacency
    public double[][] generateAdjacency() {
        adjacency = null;
        if (titik.size() > 0){
            int numVertex = titik.size();
            adjacency = new double[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++)
            {
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++)
                {
                    int value = ThreadLocalRandom.current().nextInt(0, 10);
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
        }

        for (int i = 0; i < adjacency.length; i++){
            System.out.println(Arrays.toString(adjacency[i]));
        }
        return adjacency;
    }

    public void hitungMST() {
        if (adjacency != null)
        {
            int numVertex = adjacency.length;
            // Problem
            // MST (Minimum Spanning Tree)

            // Parameter Algoritma ACO
            double[][] pheromone = new double[numVertex][numVertex]; // Tau
            double[][] visibility = new double[numVertex][numVertex]; // Eta
            int S = 10; // Banyaknya semut
            int NCMax = 10; // Jumlah siklus maksimum
            double alpha = 0.6; // Konstanta pengendali pheromone (α), nilai (α) >=  0
            double beta = 0.5; // Konstanta pengendai intensitas visibilitas (β), nilai (β) >= 0
            double rho = 0.4; // Konstanta penguapan pheromone => Evaporasi
            double Q = 0.5; // Konstanta Siklus semut 

            // Persiapan
            // Inisialisasi array Pheromone dan Visibility
            for (int i = 0; i < numVertex; i++)
            {
                for (int j = 0; j < numVertex; j++)
                {
                    if (adjacency[i][j] > 0)
                    {
                        pheromone[i][j] = 1;
                        visibility[i][j] = 1.0 / (double) adjacency[i][j];
                    }
                }
            }

            // BEST SOLUTION
            MIN_TOTAL_WIGHT = Double.MAX_VALUE;
            bestMSTSolution = null;

            // Loop Siklus Semut
            for (int n = 0; n < NCMax; n++)
            { // Loop Siklus Semut
                double[][] deltaTau = new double[numVertex][numVertex];
                for (int semut = 0; semut < S; semut++)
                { // loop pencarian oleh semut

                    int vertex = ThreadLocalRandom.current().nextInt(0, numVertex);
                    LinkedList<Integer> visited = new LinkedList<>();
                    visited.add(vertex);

                    // Untuk menyimpan solusi MST
                    ArrayList<Edge> mst = new ArrayList<>();

                    while (visited.size() < numVertex)
                    {
                        // Cari Kandidat
                        ArrayList<Edge> candidates = new ArrayList<>();
                        for (int i = 0; i < visited.size(); i++)
                        {
                            int origin = visited.get(i);
                            for (int j = 0; j < numVertex; j++)
                            {
                                if (!visited.contains(j) && adjacency[origin][j] > 0)
                                {
                                    int destination = j;
                                    double weight = adjacency[origin][destination];
                                    Edge e = new Edge(origin, destination, weight);
                                    candidates.add(e);
                                }
                            }
                        }

                        // Hitung Probabilitas Semua Kandidat
                        double[] pembilang = new double[candidates.size()];
                        double penyebut = 0;
                        for (int i = 0; i < candidates.size(); i++)
                        {
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
                        for (int i = 0; i < candidates.size(); i++)
                        {
                            double probabilitas = pembilang[i] / penyebut;
                            totalProbabilitas += probabilitas;
                            probabilitasKomulatif[i] = totalProbabilitas;
                        }

                        // System.out.println(Arrays.toString(probabilitasKomulatif));
                        double randomProbabilitas = Math.random() * totalProbabilitas;
                        for (int i = 0; i < candidates.size(); i++)
                        {
                            if (randomProbabilitas <= probabilitasKomulatif[i])
                            {
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
                    for (int i = 0; i < mst.size(); i++)
                    {
                        Lk += mst.get(i).weight;
                    }

                    // Temp Solution
                    System.out.println("Solusi : " + mst);

                    // SAVE BEST SOLUTION
                    if (Lk < MIN_TOTAL_WIGHT)
                    {
                        MIN_TOTAL_WIGHT = Lk;
                        bestMSTSolution = mst;
                    }

                    // Update Delta Tau => Setiap 1 semut selesai maka akan meng-Update Delta Tau
                    for (int i = 0; i < mst.size(); i++)
                    {
                        Edge e = mst.get(i);
                        int origin = e.vertexOrigin;
                        int destination = e.vertexDest;
                        deltaTau[origin][destination] += (Q / Lk);
                    }

                }// end of semut

                // Update Pheromone (Tau)
                for (int i = 0; i < numVertex; i++)
                {
                    for (int j = 0; j < numVertex; j++)
                    {
                        pheromone[i][j] = (1.0 - rho) * pheromone[i][j] + deltaTau[i][j]; //rumus update pheromone (1-rho) * tau + p 
                    }
                }

            } // end loop siklus semut

            System.out.println("-------------------------------------------");
            System.out.println("Best Solution : " + bestMSTSolution);
            System.out.println("Total Weight : " + MIN_TOTAL_WIGHT);
            System.out.println("-------------------------------------------");
            repaint();
        }
    }

    public String bestPath(){
        if(bestMSTSolution != null){
            String path = ""+bestMSTSolution+", Total Bobot : "+MIN_TOTAL_WIGHT;
            return  path;
        }
        return null;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int x0 = (int) (e.getX() - translateX / scaleX);
        int y0 = (int) (e.getY() - translateY / scaleY);
        titik.add(new Point(x0, y0));
        System.out.println("Tititk " + titik);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        // capture titik start x dan y
//        lastOffsetX = e.getX();
//        lastOffsetY = e.getY();
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
//        // defenisikan posisi x dan y yang baru
//        // hitung translasi x dan y
//        int newX = e.getX() - lastOffsetX;
//        int newY = e.getY() - lastOffsetY;
//
//        // increment last offset oleh even drag mouse
//        lastOffsetX += newX;
//        lastOffsetY += newY;
//
//        // update posisi canvas
//        //tx.translate(tx.getTranslateY() + newX, tx.getTranslateY() + newY);
//        translateX += newX;
//        translateY += newY;
//
//        System.out.println("Click e: " + e.getX() + ", " + e.getY() + "; translate: " + translateX + ", " + translateY);
//
//        // schedule a repaint.
//        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
