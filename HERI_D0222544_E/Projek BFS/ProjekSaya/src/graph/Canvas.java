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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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
    public int start = 1;//start dari v2
    public int finish = 4;//finish di v4
    ArrayList<Integer> bestSolution = null;
    double bestDistance;

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
            int x = ThreadLocalRandom.current().nextInt(1, MAX_X);
            int y = ThreadLocalRandom.current().nextInt(1, MAX_Y);
            listTitik.add(new Titik(x, y));
        }
        repaint();
    }

    public double[][] hitungAdjacency() {
        adjacency = null;
        if (listTitik.size() > 0) {
            bestSolution = null;
            int numVertex = listTitik.size();
            adjacency = new double[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++) {
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++) {
                    int value = ThreadLocalRandom.current().nextInt(1, 10);
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
            for (int i = 0; i < adjacency.length; i++) {
                System.out.println(Arrays.toString(adjacency[i]));
            }
        }
        return adjacency;
    }

    public void hitungShortestPathGraphBFS() {
        hitungAdjacency();
        if (adjacency != null) {
            int numVertex = adjacency.length;
            //PROBLEM
            //problem
            // find the shortest path from start vertex to finish vertex            

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
                double jarakOrigin = currentVertex.jarak;

                if (origin == finish) {
                    //pencarian selesai
                    System.out.print("Solusi BFS: ");
                    System.out.print(visitedOrigin);
                    System.out.println(" jarak: " + jarakOrigin);
                    bestSolution = visitedOrigin;
                    bestDistance = jarakOrigin;
                    break;

                } else {
                    for (int j = 0; j < numVertex; j++) {
                        double value = adjacency[origin][j];
                        if (value > 0 && !visitedOrigin.contains(j)) {
                            //tambahkan vertex ke antrian
                            ArrayList<Integer> visitedDestination = new ArrayList<>();
                            //copy visited dari origin
                            for (int k = 0; k < visitedOrigin.size(); k++) {
                                visitedDestination.add(visitedOrigin.get(k));

                            }
                            visitedDestination.add(j);

                            double jarakDestination = jarakOrigin + value;
                            VertexBFS vertexDestination = new VertexBFS(j, visitedDestination, jarakDestination);
                            antrian.offer(vertexDestination); //masukkan root kedalam antrian
                        }
                    }
                }
            }
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
            int origin = bestSolution.get(0);
            for (int i = 1; i < bestSolution.size(); i++) {
                int destination = bestSolution.get(i);
                int x0 = listTitik.get(origin).x;
                int y0 = listTitik.get(origin).y;
                int x1 = listTitik.get(destination).x;
                int y1 = listTitik.get(destination).y;
                g2d.drawLine(x0, y0, x1, y1);
                origin = destination;
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

}//end class canvas

class VertexBFS {

    int id; //nomor vertex
    ArrayList<Integer> visited; //lintasan
    double jarak;

    public VertexBFS(int id, ArrayList<Integer> visited, double jarak) {
        this.id = id;
        this.visited = visited;
        this.jarak = jarak;
    }
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
