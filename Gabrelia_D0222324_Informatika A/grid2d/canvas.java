
package grid2d;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JPanel;

public class canvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int translateX;
    private int translateY;
    private double scaleX;
    private double scaleY;
    private int lastOffsetX;
    private int lastOffsetY;
    //SCALE
    final private double MIN_SCALE = 0.1;
    final private double MAX_SCALE = 20;

    //VARIABEL GRID
    public int BARIS = 20;
    public int KOLOM = 10;
    public int gridSize = 40;
    public int[][] grid = null;

    //SHORTEST PATH GRID
    public Titik start;
    public Titik finish;
    ArrayList<Titik> pathBFS = null;

    public canvas() {
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
        addMouseWheelListener(this);
        //grid
        grid = new int[BARIS][KOLOM];
        grid[8][2] = 1;
        grid[5][5] = 1;
      

        start = new Titik(0, 2);
        finish = new Titik(10, 5);
    }
    
    


    public void runBFS() {
        if (grid != null
                && start.x >= 0
                && start.y >= 0
                && start.x < BARIS
                && start.y < KOLOM
                && grid.length == BARIS
                && grid[0].length == KOLOM) {
            //proses BFS
            start.setPath(null);
            int[][] arrayProses = grid.clone();
            arrayProses[start.x][start.y] = 1;
            Queue<Titik> antrianTitik = new LinkedList<>();
            antrianTitik.offer(start);
            while (!antrianTitik.isEmpty()) {
                Titik current = antrianTitik.poll();
                //cari tetangga
                int cx = current.x;
                int cy = current.y;
                int label = grid[cx][cy];

                if (cx == finish.x && cy == finish.y) {
                    //pencarian berakhir
                    System.out.println("PENCARIAN BERAKHIR");
                    System.out.println(current.path);
                    pathBFS = current.path;
                    repaint();
                    break;
                } else {
                    //NORTH-------------------------
                    int x = cx - 1;
                    int y = cy;
                    if (x >= 0
                            && x < BARIS
                            && y >= 0
                            && y < KOLOM
                            && arrayProses[x][y] == 0
                            && grid[x][y] == 0) {
                        arrayProses[x][y] = label + 1;
                        Titik titikBaru = new Titik(x, y);
                        titikBaru.setPath(current.path);
                        antrianTitik.offer(titikBaru);
                    }

                    //EAST-------------------------
                    x = cx;
                    y = cy + 1;
                    if (x >= 0
                            && x < BARIS
                            && y >= 0
                            && y < KOLOM
                            && arrayProses[x][y] == 0
                            && grid[x][y] == 0) {
                        arrayProses[x][y] = label + 1;
                        Titik titikBaru = new Titik(x, y);
                        titikBaru.setPath(current.path);
                        antrianTitik.offer(titikBaru);
                    }

                    //SOUTH-------------------------
                    x = cx + 1;
                    y = cy;
                    if (x >= 0
                            && x < BARIS
                            && y >= 0
                            && y < KOLOM
                            && arrayProses[x][y] == 0
                            && grid[x][y] == 0) {
                        arrayProses[x][y] = label + 1;
                        Titik titikBaru = new Titik(x, y);
                        titikBaru.setPath(current.path);
                        antrianTitik.offer(titikBaru);
                    }

                    //WEST-------------------------
                    x = cx;
                    y = cy - 1;
                    if (x >= 0
                            && x < BARIS
                            && y >= 0
                            && y < KOLOM
                            && arrayProses[x][y] == 0
                            && grid[x][y] == 0) {
                        arrayProses[x][y] = label + 1;
                        Titik titikBaru = new Titik(x, y);
                        titikBaru.setPath(current.path);
                        antrianTitik.offer(titikBaru);
                    }
                    label++;
                }

            }//end of while

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
        //----------------------------------------------------------------------

        //gambar grid
        if (grid != null) {
            for (int i = 0; i < BARIS; i++) {
                for (int j = 0; j < KOLOM; j++) {
                    int x = i * gridSize;
                    int y = j * gridSize;
                    int value = grid[i][j];
                    if (value == 1) {
                        g2d.setColor(Color.BLACK);
                    } else {
                        g2d.setColor(Color.gray);
                    }
                    g2d.fillRect(x, y, gridSize - 1, gridSize - 1);
                }
            }
        }
        
        //gambar pathBFS
        if(pathBFS!=null&&pathBFS.size()>0){
            for (Titik current:pathBFS) {
                int x = current.x * gridSize;
                int y = current.y * gridSize;
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x, y, gridSize - 1, gridSize - 1);
            }
        }

        //gambar titik start dan finish
        if (start != null && finish != null) {
            //start
            int x = start.x * gridSize;
            int y = start.y * gridSize;
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, gridSize - 1, gridSize - 1);

            //finish
            x = finish.x * gridSize;
            y = finish.y * gridSize;
            g2d.setColor(Color.RED);
            g2d.fillRect(x, y, gridSize - 1, gridSize - 1);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            double scale = scaleX;
            if (scale >= MIN_SCALE && scale <= MAX_SCALE) {
                double oldScale = scale;

                double newScale = (0.1 * e.getWheelRotation());
                newScale = scale + newScale;
                newScale = Math.max(0.00001, newScale);

                //cek max dan min scale
                if (newScale < MIN_SCALE) {
                    newScale = MIN_SCALE;
                } else if (newScale > MAX_SCALE) {
                    newScale = MAX_SCALE;
                }

                double x1 = e.getX() - translateX;
                double y1 = e.getY() - translateY;

                double x2 = (x1 * newScale) / oldScale;
                double y2 = (y1 * newScale) / oldScale;

                double newTranslateX = translateX + x1 - x2;
                double newTranslateY = translateY + y1 - y2;

                //set nilai skala dan translasi yang baru
                translateX = (int) newTranslateX;
                translateY = (int) newTranslateY;
                scaleX = newScale;
                scaleY = newScale;
                repaint();
            }
        }
    }

   
}

class Titik {

    public int x, y;
    ArrayList<Titik> path = null;

    public Titik(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPath(ArrayList<Titik> p) {
        path = new ArrayList<>();
        if (p != null) {
            for (int i = 0; i < p.size(); i++) {
                Titik t = p.get(i);
                path.add(new Titik(t.x, t.y));
            }
        }
        path.add(new Titik(x,y));
    }

    @Override
    public String toString() {
        return "Titik{" + "x=" + x + ", y=" + y + '}';
    }

}
