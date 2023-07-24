package rahmtia;

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
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private int translateX;
    private int translateY;
    private double scaleX;
    private double scaleY;
    private int lastOffsetX;
    private int lastOffsetY;

    //Graph
    ArrayList<Titik> listTitik = new ArrayList<>();
    double[][] adjacency = null;
    String url = "datagraph.csv";
    int MAX_X = 400;
    int MAX_Y = 300;
    public int start = 0;
    public int finish = 0;
    ArrayList<Integer> bestSolution = null;
    double bestDistance = Double.MAX_VALUE;

    public Canvas() {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        AffineTransform at = gc.getDefaultTransform();
        translateX = 0;
        translateY = 0;
        scaleX = at.getScaleX();
        scaleY = at.getScaleY();
        setOpaque(false);
        setDoubleBuffered(true);
        //set handler
        addMouseListener(this);
        addMouseMotionListener(this);
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
                //Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

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

    public void resetGraph() {
        listTitik = new ArrayList<>();
        adjacency = null;
        //vertexDepot = 0;
        //solusiTSPGreedy = null;
        repaint();
    }

    public void setOriginDestination(int origin, int destination) {
        if (origin >= 0 && origin < listTitik.size() && destination >= 0 && destination < listTitik.size()) {
            start = origin;
            finish = destination;
            repaint();
        }
    }

    public double[][] hitungAdjacency() {
        adjacency = null;
        if (listTitik != null && listTitik.size() > 0) {
            int numVertex = listTitik.size();
            adjacency = new double[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++) {
                Titik t1 = listTitik.get(i);
                int x1 = t1.x;
                int y1 = t1.y;
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++) {
                    Titik t2 = listTitik.get(j);
                    int x2 = t2.x;
                    int y2 = t2.y;
                    //double value = Math.abs(x1-x2)+Math.abs(y1-y2);//manhattan Distance
                    double value = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));//Chebisev
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
        }
        return adjacency;
    }

    public void runACO() {
        hitungAdjacency();
        if (adjacency != null) {
            int numVertex = adjacency.length;
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

            bestSolution = null;
            bestDistance = Double.MAX_VALUE;

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
            }//end of siklus semut

            repaint();
        }
    }

    public void paint(Graphics g) {
        //----------------------------------------------------------------------    
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);
        //----------------------------------------------------------------------
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //---------------------------------------------------------------------- 
        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //----------------------------------------------------------------------
        //gambar edge
        if (listTitik != null) {
            for (int i = 0; i < listTitik.size(); i++) {
                Titik t1 = listTitik.get(i);
                int x1 = t1.x;
                int y1 = t1.y;
                for (int j = 0; j < i; j++) {
                    Titik t2 = listTitik.get(j);
                    int x2 = t2.x;
                    int y2 = t2.y;
                    g2d.setColor(Color.decode("#6554AF"));
                    g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }

        //gambar vertex
        if (listTitik != null) {
            for (int i = 0; i < listTitik.size(); i++) {
                Titik t = listTitik.get(i);
                int x = t.x;
                int y = t.y;
                g2d.setColor(Color.decode("#E966A0"));
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
                g2d.fillOval(x - 10, y - 10, 20, 20);
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));
                g2d.setColor(Color.decode("#F9F5F6"));
                g2d.drawString("V" + i, x, y - 12);
            }
        }

        //gambar solusinya
        if (bestSolution != null && listTitik != null && listTitik.size() >= bestSolution.size()) {
            int origin = bestSolution.get(0);
            for (int i = 1; i < bestSolution.size(); i++) {
                int destination = bestSolution.get(i);
                Titik t1 = listTitik.get(origin);
                int x1 = t1.x;
                int y1 = t1.y;
                Titik t2 = listTitik.get(destination);
                int x2 = t2.x;
                int y2 = t2.y;
                g2d.setColor(Color.decode("#6554AF"));
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.4F));
                g2d.setStroke(new BasicStroke(10));
                g2d.drawLine(x1, y1, x2, y2);
                origin = destination;
            }
        }

        //gambar start dan finish
        if (listTitik != null && start >= 0 && start < listTitik.size() && finish >= 0 && finish < listTitik.size()) {
            //start
            Titik t = listTitik.get(start);
            int x = t.x;
            int y = t.y;
            g2d.setColor(Color.decode("#00DFA2"));
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
            g2d.fillOval(x - 10, y - 10, 20, 20);
            //finish
            t = listTitik.get(finish);
            x = t.x;
            y = t.y;
            g2d.setColor(Color.decode("#F6FA70"));
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
            g2d.fillOval(x - 10, y - 10, 20, 20);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int) (e.getX() - translateX / scaleX);
        int y = (int) (e.getY() - translateY / scaleY);
        listTitik.add(new Titik(x, y));
        System.out.println(listTitik.size());
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
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = e.getX() - lastOffsetX;
        int newY = e.getY() - lastOffsetY;

        // increment last offset oleh even drag mouse
        lastOffsetX += newX;
        lastOffsetY += newY;

        // update posisi canvas
        //tx.translate(tx.getTranslateY() + newX, tx.getTranslateY() + newY);
        translateX += newX;
        translateY += newY;

        // schedule a repaint.
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

}//end of class

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
