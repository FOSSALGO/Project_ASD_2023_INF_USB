package Mhmd;

import Mhmd.Barang;
import java.awt.AlphaComposite;
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
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

    ///VARIABEL PROSES
    public static ArrayList<Barang> dataBarang = new ArrayList<>();
    public int panjangContainer = 80;//DM
    public int lebarContainer = 40;//DM
    public int generasi = 5;
    public int populasi = 20;
    public int terseleksi = 15;
    public double probMutasi = 0.5;
    int[] kromosomTerbaik=null;
    double fitnerTerbaik = 0;
    int[][] containerTerbaik = new int[this.panjangContainer][this.lebarContainer];
    int cellSize = 10;

    Color[] daftarWarna =
    {
        Color.decode("#1abc9c"),
        Color.decode("#3498db"),
        Color.decode("#f1c40f"),
        Color.decode("#e74c3c"),
        Color.decode("#7f8c8d"),
        Color.decode("#16a085"),
        Color.decode("#d35400"),
        Color.decode("#27ae60"),
    };

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
    }

    public int getPanjangContainer() {
        return panjangContainer;
    }

    public int getLebarContainer() {
        return lebarContainer;
    }

    public int getGenerasi() {
        return generasi;
    }

    public int getPopulasi() {
        return populasi;
    }

    public int getTerseleksi() {
        return terseleksi;
    }

    public double getProbMutasi() {
        return probMutasi;
    }

    public void setPanjangContainer(int panjangContainer) {
        this.panjangContainer = panjangContainer;
    }

    public void setLebarContainer(int lebarContainer) {
        this.lebarContainer = lebarContainer;
    }

    public void setGenerasi(int generasi) {
        this.generasi = generasi;
    }

    public void setPopulasi(int populasi) {
        this.populasi = populasi;
    }

    public void setTerseleksi(int terseleksi) {
        this.terseleksi = terseleksi;
    }

    public void setProbMutasi(double probMutasi) {
        this.probMutasi = probMutasi;
    }

    public void run() {
        if (dataBarang != null)
        {
            int[][] container = new int[this.panjangContainer][this.lebarContainer];
            int panjangKromosom = this.dataBarang.size();
            int[] kromosomTerbaik = new int[this.dataBarang.size()];
            double fitnessTerbaik = Double.MIN_VALUE;
            int[][] populasiAwal = new int[this.populasi][panjangKromosom];

            //Membangkitkan Populasi Awal
            for (int i = 0; i < this.populasi; i++)
            {
                ArrayList<Integer> kromosom = new ArrayList<>();
                int index = 0;
                while (kromosom.size() < panjangKromosom)
                {
                    int nilai = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                    if (!kromosom.contains(nilai))
                    {
                        if (index >= panjangKromosom)
                        {
                            index = 0;
                        }
                        if (index < panjangKromosom)
                        {
                            kromosom.add(nilai);
                            populasiAwal[i][index] = nilai + 1;
                            index++;
                        }
                    }
                }
            }
            //Memasukkan Barang dan Menghitung Nilai fitness
            double[][] dataFitnessAwal = new double[this.populasi][2];
            for (int i = 0; i < this.populasi; i++)
            {
                int[][] container1 = new int[this.panjangContainer][this.lebarContainer];
                int[] individu = populasiAwal[i];
                masukkanBarang(container1, individu,this.dataBarang);
                double fitness = hitungFitness(container1);
                dataFitnessAwal[i][0] = i;
                dataFitnessAwal[i][1] = fitness;
            }
            //Mengurutkan Nilai Fitness
            urutkanFitness(dataFitnessAwal);
            int[][] populasi = new int[this.populasi][panjangKromosom];
            for (int i = 0; i < this.populasi; i++)
            {
                int index = (int) dataFitnessAwal[i][0];
                populasi[i] = populasiAwal[index];
            }
            //Proses elitism
            for (int i = 0; i < populasi.length; i++)
            {
                int[][] container1 = new int[this.panjangContainer][this.lebarContainer];
                int[] barang = populasi[i];
                masukkanBarang(container1, barang,this.dataBarang);
                double fitness1 = hitungFitness(container1);
                if (fitness1 > fitnessTerbaik)
                {
                    kromosomTerbaik = barang;
                    fitnessTerbaik = fitness1;
                }
            }
            //Proses Evolusi
            for (int g = 1; g <this.generasi; g++)
            {
                int[][] populasiBaru = new int[this.populasi][panjangKromosom];

                //Seleksi Turnamen
                for (int i = 0; i < this.terseleksi; i++)
                {
                    populasiBaru[i] = populasi[i];
                }
                //Melengkapi Populasi
                for (int i = this.terseleksi; i < populasiBaru.length; i++)
                {
                    ArrayList<Integer> kromosom = new ArrayList<>();
                    int index = 0;
                    while (kromosom.size() < panjangKromosom)
                    {
                        int nilai = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                        if (!kromosom.contains(nilai))
                        {
                            if (index >= panjangKromosom)
                            {
                                index = 0;
                            }
                            if (index < panjangKromosom)
                            {
                                kromosom.add(nilai);
                                populasiBaru[i][index] = nilai + 1;
                                index++;
                            }
                        }
                    }
                }

                //Mutasi
                double[][] dataFitnesBaru = new double[this.populasi][2];
                for (int i = 0; i < populasiBaru.length; i++)
                {
                    double random = Math.random();
                    if (random >= this.probMutasi)
                    {
                        int index1 = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                        int index2 = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                        while (index1 == index2)
                        {
                            int index = ThreadLocalRandom.current().nextInt(0, panjangKromosom);
                            if (index != index1)
                            {
                                index2 = index;
                            }
                        }
                        //Swap Gen Yang Telah Dipilih
                        int[] individu = populasiBaru[i];
                        int temp = individu[index1];
                        individu[index1] = individu[index2];
                        individu[index2] = temp;
                    }
                    int[][] container1 = new int[this.panjangContainer][this.lebarContainer];
                    int[] barang = populasiBaru[i];
                    masukkanBarang(container1, barang,this.dataBarang);
                    double fitness1 = hitungFitness(container1);
                    dataFitnesBaru[i][0] = i;
                    dataFitnesBaru[i][1] = fitness1;
                }

                urutkanFitness(dataFitnesBaru);//Mengurutkan Individu Berdasarkan Nilai Fitness

                //Mengurutkan Populasi Berdasarkan Urutan Data Nilai Fitness Yang telah Diurutkan
                populasi = new int[this.populasi][panjangKromosom];
                for (int i = 0; i < this.populasi; i++)
                {
                    int index = (int) dataFitnessAwal[i][0];
                    populasi[i] = populasiBaru[index];
                }

                for (int i = 0; i < populasi.length; i++)
                {
                    int[][] container1 = new int[this.panjangContainer][this.lebarContainer];
                    int[] barang = populasi[i];
                    masukkanBarang(container1, barang,this.dataBarang);
                    double fitness1 = hitungFitness(container1);
                    if (fitness1 > fitnessTerbaik)
                    {
                        kromosomTerbaik = barang;
                        fitnessTerbaik = fitness1;
                    }
                }
            }//End Of Evolusi
            masukkanBarang(container, kromosomTerbaik, this.dataBarang);
            this.containerTerbaik = container;
            this.kromosomTerbaik = kromosomTerbaik;
            this.fitnerTerbaik = fitnessTerbaik; 
        }
    }
    public static void masukkanBarang(int[][] container, int[] barang, ArrayList<Barang> daftarBarang) {
        for (int j = 0; j < barang.length; j++) {
            int idBarang = barang[j]-1;
            Barang barang1 = daftarBarang.get(idBarang);
            int panjangBarang = barang1.panjang;
            int lebarBarang = barang1.lebar;

            selesai:
                for (int k = 0; k < container.length; k++) {
            for (int l = container[0].length-1; l >=0; l--) {

                    if (container[k][l] == 0) {
                        if ((k + panjangBarang ) < container.length && (l - lebarBarang) >=0) {
                            boolean muat = false;
                            cekTempat:
                            for (int n = l; n >= (l - lebarBarang); n--) {
                                for (int m = k; m <= (k + panjangBarang); m++) {
                                    if (container[m][n] == 0) {
                                        muat = true;
                                    } else {
                                        muat = false;
                                        break cekTempat;
                                    }
                                }
                            }
                            if (muat) {
                                for (int n = l; n >= (l - lebarBarang); n--) {
                                    for (int m = k; m <= (k + panjangBarang); m++) {
                                        container[m][n] = idBarang+1;
                                    }
                                }
                                break selesai;
                            }
                        } else {
                            container[k][l] = -1;
                        }
                    }
                }
            }
        }
    }

    public static double hitungFitness(int[][] container) {
        int terluar = -1;
        int ruangkosong = 0;
        for (int i = 0; i < container.length; i++)
        {
            for (int j = 0; j < container[i].length; j++)
            {
                if (container[i][j] != 0 && i > terluar)
                {
                    terluar = i;
                }
            }
        }
        for (int i = 0; i <=terluar; i++)
        {
            for (int j = 0; j < container[i].length; j++)
            {
                if (container[i][j] <= 0)
                {
                    ruangkosong++;
                }
            }
        }
        double fitness = 1.0 / ruangkosong;
        return fitness;
    }

    public static void urutkanFitness(double[][] data) {
        int indek_min;
        double tampungindex;
        double tampungfitnes;
        for (int i = 0; i < data.length; i++)
        {
            indek_min = i;
            for (int j = i + 1; j < data.length; j++)
            {
                if (data[j][1] > data[indek_min][1])
                {
                    indek_min = j;
                }
            }
            if (indek_min != i)
            {
                tampungindex = data[indek_min][0];
                tampungfitnes = data[indek_min][1];

                data[indek_min][0] = data[i][0];
                data[indek_min][1] = data[i][1];

                data[i][0] = tampungindex;
                data[i][1] = tampungfitnes;
            }
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //----------------------------------------------------------------------    
        AffineTransform at = new AffineTransform();
        at.translate(translateX, translateY);
        at.scale(scaleX, scaleY);
        //----------------------------------------------------------------------
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //---------------------------------------------------------------------- 
        g2d.setTransform(at);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //----------------------------------------------------------------------

        //gambar background container
        g2d.setColor(Color.decode("#bdc3c7"));
        g2d.fillRect(0, 0, this.panjangContainer * cellSize, this.lebarContainer * cellSize);
       

     

        //gambar barang di dalam container
        if (this.containerTerbaik != null)
        {
            for (int i = 0; i < this.containerTerbaik.length; i++)
            {
                for (int j = 0; j < this.containerTerbaik[0].length; j++)
                {
                    int value = this.containerTerbaik[i][j];
                    if (value > 0)
                    {
                        value = value % daftarWarna.length;
                        Color c = daftarWarna[value];
                        g2d.setColor(c);
                        g2d.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                    }

                }
            }
        }

        //gambar grid lines
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.4f));
        for (int i = 0; i <= this.panjangContainer; i++)
        {
            g2d.setColor(Color.decode("#95a5a6"));
            g2d.drawLine(i * cellSize, 0, i * cellSize, this.lebarContainer * cellSize);
        }
        for (int i = 0; i < this.lebarContainer; i++)
        {
            g2d.setColor(Color.decode("#95a5a6"));
            g2d.drawLine(0, i * cellSize, this.panjangContainer * cellSize, i * cellSize);
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
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
        {
            if (scaleX >= MIN_SCALE && scaleX <= MAX_SCALE && scaleY >= MIN_SCALE && scaleY <= MAX_SCALE)
            {
                double oldScale = scaleX;

                double newScale = (0.1 * e.getWheelRotation());
                newScale = scaleX + newScale;
                newScale = Math.max(0.00001, newScale);

                //cek max dan min scale
                if (newScale < MIN_SCALE)
                {
                    newScale = MIN_SCALE;
                } else if (newScale > MAX_SCALE)
                {
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
