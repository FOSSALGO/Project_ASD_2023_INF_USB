package AwalFrustasi;

import java.awt.*;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

public class CanvasGila extends JPanel implements MouseListener, MouseMotionListener {
    private int translateX;
    private int translateY;
    private double scaleX;
    private double scaleY;
    private int lastOffsetX;
    private int lastOffsetY;
    
    ArrayList<Point> titik = new ArrayList<>();
    int[][] adjacency = null;
    public int start = 0;
    public int finish = 0;
    int minJarak;
    ArrayList<Integer> bestPath = null;
    
    String url = "datagraph.csv";
    int MAX_X = 400;
    int MAX_Y = 300;
    
    public CanvasGila() {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        AffineTransform tx = gc.getDefaultTransform();
        System.out.println("SCALE X : " + tx.getScaleX());
        System.out.println("SCALE X : " + tx.getScaleY());
        translateX = 0;
        translateY = 0;
        scaleX = tx.getScaleX();
        scaleY = tx.getScaleY();
        setOpaque(false);
        setDoubleBuffered(true);
//        set handler
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void reset() {
        titik = new ArrayList<>();
        adjacency = null;
        bestPath = null;
        repaint();
    }

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
            } catch (IOException ex) {
//                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

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
            System.out.println("Error di bagian open");
        }
    }

    public void randomVertex(int n) {
        titik = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, MAX_X);
            int y = ThreadLocalRandom.current().nextInt(0, MAX_Y);
            titik.add(new Point(x, y));
        }
        repaint();
    }
    
    public int[][] generateAdjacency() {
        adjacency = null;
        if(titik.size() > 0){
            int numVertex=titik.size();
            adjacency = new int[numVertex][numVertex];
            for (int i = 0; i < numVertex; i++) {
                adjacency[i][i] = 0;
                for (int j = i + 1; j < numVertex; j++) {
                    int value = ThreadLocalRandom.current().nextInt(1, 10);
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

    public void solusiBF(){
        int numVertex = adjacency.length;

        adjacency[0][0]= 0;
        adjacency[0][0]= 0;

        //PROBLEM
       
        //SOLUSI BF
        Queue<VertexBFS> antrian = new LinkedList<>();
        ArrayList<Integer> visitedRoot = new ArrayList<>();
        visitedRoot.add(start);
        VertexBFS root = new VertexBFS(start,visitedRoot,0);
        antrian.offer(root);//masukkan root kedalam antrian

        //MEMULAI PENCARIAN BruteForce
        minJarak= Integer.MAX_VALUE;
        bestPath = new ArrayList<>();
        
        while (!antrian.isEmpty()){
            VertexBFS currentVertex = antrian.poll();
            int ori = currentVertex.id;
            ArrayList<Integer> visitedOri = currentVertex.visited;
            int jarakOri = currentVertex.jarak;

            if(ori==finish){
                //PENCARIAN SELESAI
                System.out.print("Solusi Brute Force: ");
                System.out.print(visitedOri);
                System.out.println(" jarak: "+jarakOri);
                if(jarakOri < minJarak){
                    minJarak = jarakOri;
                    bestPath = visitedOri;
                }
            }else{
            for (int j = 0; j < numVertex; j++) {
                int value = adjacency[ori][j];
                if(value>0 && !visitedOri.contains(j)){
                    ArrayList<Integer> visitedDestination = new ArrayList<>();
                    for (int i = 0; i < visitedOri.size(); i++) {
                        visitedDestination.add(visitedOri.get(i));
                    }
                    visitedDestination.add(j);
//                    ArrayList<Integer> visitedDestination = (ArrayList<Integer>) visitedOri.clone();
//                    visitedDestination.add(j);
                    int jarakDestination = jarakOri + value;
                    VertexBFS vertexDestinatoin = new VertexBFS(j,visitedDestination,jarakDestination);
                    antrian.offer(vertexDestinatoin);//masukkan root kedalam antrian
                    }
                }
            }
        }
        System.out.print("Solusi BruteForce terbaik: ");
        System.out.print(bestPath);
        System.out.println(" jarak: "+minJarak);
        
        String  SolusiBF = "Solusi BruteForce terbaik: ";
        var bP = bestPath;
        String Jarak = "jarak: ";
        Integer minJ = minJarak;
    }
    
    @Override
    public void paint (Graphics g){
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,getWidth(),getHeight());

        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));

//        g2d.setColor(Color.WHITE);
//        g2d.setComposite(AlphaComposite.SrcOver.derive(0.9F));//transparansi
//        g2d.fillRect(0, 0, 200, 120);

//        gambar edge

        for (int i = 0; i < titik.size(); i++) {
            int x0 = titik.get(i).x;
            int y0 = titik.get(i).y;
            for (int j = 0; j < i; j++) {
                int x1 = titik.get(j).x;
                int y1 = titik.get(j).y;
                g2d.setColor(Color.RED);
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
                g2d.drawLine(x0, y0, x1, y1);
            }

        }

//        gambar vertex
        int idx = 0;
        for (Point point : titik) {
            g2d.setColor(Color.GREEN);
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
            int x0 = point.x;
            int y0 = point.y;
            g2d.fillOval(x0 - 5, y0 - 5, 10, 10);
            g2d.setColor(Color.RED);
            g2d.drawString("V"+idx, x0, y0 - 15);
            idx++;
        }
        
        // gambar solusi
        if(bestPath != null){
            g2d.setColor(Color.GREEN);
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.7F));
            g2d.setStroke(new BasicStroke(10));
            int ori = bestPath.get(0);
            for (int i = 1; i < bestPath.size(); i++){
                int dest = bestPath.get(i);
                
                int x0 = titik.get(ori).x;
                int y0 = titik.get(ori).y;
                int x1 = titik.get(dest).x;
                int y1 = titik.get(dest).y;
                g2d.drawLine(x0, y0, x1, y1);
                ori = dest;
            }
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int xo = (int) (e.getX() - translateX / scaleX);
        int yo = (int) (e.getY() - translateY / scaleY);
        titik.add(new Point(xo,yo));
        System.out.println("Titik "+ titik);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void setText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

class Titik {

    public int x, y;


    Titik(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return "Titik{" + "x=" + x + ", y=" + y + '}';
    }

}

