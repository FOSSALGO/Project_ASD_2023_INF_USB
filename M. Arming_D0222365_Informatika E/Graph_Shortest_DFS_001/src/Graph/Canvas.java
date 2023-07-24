package Graph;

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
    int MAX_X = 400;
    int MAX_Y = 300;
    int numVertex;
    double[][] adjacency = null;
    public int start = 2;//start dari V2
    public int finish = 4;//finish di V4
    public Stack<Integer> solution = null;
    public String url = "graph.txt";

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
        //----------------------------------------------------------------------

        //gambar edge
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < listTitik.size(); i++) {
            for (int j = 0; j < i; j++) {
                Titik t1 = listTitik.get(i);
                int x1 = t1.x;
                int y1 = t1.y;

                Titik t2 = listTitik.get(j);
                int x2 = t2.x;
                int y2 = t2.y;
                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        //menggambar vertex        
        for (int i = 0; i < listTitik.size(); i++) {
            Titik t = listTitik.get(i);
            int xo = t.x;
            int yo = t.y;
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.6F));
            g2d.setColor(Color.cyan);
            g2d.fillOval(xo - 10, yo - 10, 20, 20);
            g2d.setColor(Color.RED);
            g2d.drawString("V" + i, xo, yo - 10);
        }

        //gambar solusi DFS
        if (solution != null) {
            int origin = solution.get(0);
            for (int i = 1; i < solution.size(); i++) {
                int destination = solution.get(i);
                Titik t1 = listTitik.get(origin);
                int x1 = t1.x;
                int y1 = t1.y;

                Titik t2 = listTitik.get(destination);
                int x2 = t2.x;
                int y2 = t2.y;

                g2d.setComposite(AlphaComposite.SrcOver.derive(0.4F));
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(x1, y1, x2, y2);

                origin = destination;
            }
        }

    }
    
    public void randomVertex(int n){
        listTitik = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, MAX_X);
            int y = ThreadLocalRandom.current().nextInt(0,MAX_Y);
            listTitik.add(new Titik(x,y));
        }
        repaint();
    }

    public void delete() {
        if (solution != null) {
            for (int i = 1; i < solution.size(); i++) {
                int origin = solution.get(i - 1);
                int destination = solution.get(i);
                adjacency[origin][destination] = 0;
                adjacency[destination][origin] = 0;
            }

            solution = null;

            repaint();
        }
    }

    public void clear() {
        listTitik.clear();
        adjacency = null;
        solution = null;
        repaint();
    }

    public void save() {
        try {
            FileWriter fw = new FileWriter(url);
            BufferedWriter bw = new BufferedWriter(fw);
            if (listTitik.size() > 0) {
                bw.append("numVertex:" + listTitik.size() + "\n");
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

    public void open() {
        try {
            FileReader fr = new FileReader(url);
            BufferedReader br = new BufferedReader(fr);
            listTitik = new ArrayList<>();
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

    public double[][] hitungAdjacency() {
        if (listTitik != null) {
            numVertex = listTitik.size();
            adjacency = new double[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++) {
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++) {
                    Titik t1 = listTitik.get(i);
                    int x1 = t1.x;
                    int y1 = t1.y;

                    Titik t2 = listTitik.get(j);
                    int x2 = t2.x;
                    int y2 = t2.y;
                    //Rumus Jarak Manhattan
                    double value = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                    adjacency[i][j] = value;
                    adjacency[j][i] = value;
                }
            }
        }
        return adjacency;
    }

    public void runAlgoritmaDFS() {
        solution = null;
        Stack<Integer> visited = new Stack<>();
        visited.push(start);
        while (true) {
            int currentVertex = visited.peek();
            if (currentVertex == finish) {
                //SELESAI
                //hitung total jarak sambil cetak solusi
                System.out.print("V" + start);
                int ori = start;
                int jarak = 0;
                for (int i = 1; i < visited.size(); i++) {
                    int dest = visited.get(i);
                    jarak += (adjacency[ori][dest]);
                    ori = dest;
                    System.out.print(" - V" + dest);
                }
                System.out.println(" total jarak = " + jarak);
                break;
            } else {
                //CARI VERTEX DESTINASI PERTAMA
                int destination = currentVertex;
                for (int j = 0; j < numVertex; j++) {
                    double value = adjacency[currentVertex][j];
                    if (value > 0 && !visited.contains(j)) {
                        destination = j;
                        break;
                    }
                }
                visited.push(destination);
            }
        }
        solution = visited;
        //System.out.println(solution);
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
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

class Titik {

    public int x, y;

    public Titik(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
