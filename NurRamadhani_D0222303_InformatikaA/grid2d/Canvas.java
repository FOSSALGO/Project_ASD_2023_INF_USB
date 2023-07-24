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
import java.util.Stack;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

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
    Stack<Titik> pathBacktracking = null;

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
        addMouseWheelListener(this);
        //grid
        grid = new int[BARIS][KOLOM];
        grid[19][2] = 1;
        grid[18][7] = 1;
        grid[3][2] = 1;
        grid[10][5] = 1;
        start = new Titik(0, 2);
        finish = new Titik(9, 5);
    }
        
    public void resetStartFinish() {
        start = new Titik(0, 2);
        finish = new Titik(10, 1);
        repaint();
    }

    public void runBacktracking() {
        if (grid != null
                && start.x >= 0
                && start.y >= 0
                && start.x < BARIS
                && start.y < KOLOM
                && grid.length == BARIS
                && grid[0].length == KOLOM) {
            //proses Backtracking
            start.setPath(null);
            int[][] arrayProses = grid.clone();
            arrayProses[start.x][start.y] = 1;
            Stack<Titik> stackTitik = new Stack<>();
            stackTitik.push(start);
            int arah = 0;
            while (!stackTitik.isEmpty()) {
                Titik current = stackTitik.peek();
                int cx = current.x;
                int cy = current.y;
                int label = arrayProses[cx][cy];
                if (cx == finish.x && cy == finish.y) {
                    System.out.println("PENCARIANPUN BERAKHIR");
                    pathBacktracking = stackTitik;
                    if (pathBacktracking != null) {
                        System.out.println("Langkah-langkah yang dilalui:");
                        for (Titik titik : pathBacktracking) {
                            System.out.print("(" + titik.x + ", " + titik.y + ")");
                        }
                    }
                    repaint();
                    break;
                } else {
                    //Titik arah
                    Titik north = new Titik(cx - 1, cy);
                    Titik east = new Titik(cx, cy + 1);
                    Titik south = new Titik(cx + 1, cy);
                    Titik west = new Titik(cx, cy - 1);

                    Titik depan = current, kanan = current, kiri = current;
                    if (arah == 0) {
                        depan = north;
                        kanan = east;
                        kiri = west;
                    } else if (arah == 1) {
                        depan = east;
                        kanan = south;
                        kiri = north;
                    } else if (arah == 2) {
                        depan = south;
                        kanan = west;
                        kiri = east;
                    } else if (arah == 3) {
                        depan = west;
                        kanan = north;
                        kiri = south;
                    }

                    //cek apakah bisa maju
                    int x = depan.x;
                    int y = depan.y;
                    if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 0 && arrayProses[x][y] >= 0) {
                        depan.setPath(current.path);
                        stackTitik.push(depan);
                        arrayProses[x][y] = label + 1;
                    } else {
                        //cek kanan
                        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                            arrayProses[x][y] = -1;
                        }
                        x = kanan.x;
                        y = kanan.y;
                        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 0 && arrayProses[x][y] >= 0) {
                            depan.setPath(current.path);
                            stackTitik.push(kanan);
                            arrayProses[x][y] = label + 1;
                            arah = arah + 1;
                            arah = arah % 4;
                        } else {
                            //cek kiri
                            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                                arrayProses[x][y] = -1;
                            }
                            x = kiri.x;
                            y = kiri.y;
                            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 0 && arrayProses[x][y] >= 0) {
                                depan.setPath(current.path);
                                stackTitik.push(kiri);
                                arrayProses[x][y] = label + 1;
                                arah = arah + 3;
                                arah = arah % 4;
                            } else {
                                //mundur
                                stackTitik.pop();
                                arrayProses[cx][cy] = -1;
                            }
                        }
                    }
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

        //gambar pathBacktracking
        if (pathBacktracking != null && pathBacktracking.size() > 0) {
            for (Titik current : pathBacktracking) {
                int x = current.x * gridSize;
                int y = current.y * gridSize;
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x, y, gridSize - 1, gridSize - 1);
            }
        }

        //Cetak langkah-langkah yang dilalui
        if (pathBacktracking != null && pathBacktracking.size() > 0) {
            g2d.setColor(Color.YELLOW);
            for (int i = 0; i < pathBacktracking.size() - 1; i++) {
                Titik current = pathBacktracking.get(i);
                Titik next = pathBacktracking.get(i + 1);

                int x1 = current.x * gridSize + gridSize / 2;
                int y1 = current.y * gridSize + gridSize / 2;
                int x2 = next.x * gridSize + gridSize / 2;
                int y2 = next.y * gridSize + gridSize / 2;

                g2d.drawLine(x1, y1, x2, y2);
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
        path.add(new Titik(x, y));
    }

    @Override
    public String toString() {
        return "Titik{" + "x=" + x + ", y=" + y + '}';
    }

}
