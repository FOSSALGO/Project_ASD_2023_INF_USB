package Mhmd;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Main extends javax.swing.JFrame {

    String[] header = new String[]
    {
        "ID Barang", "Panjang Barang", "Lebar Barang"
    };
    int row = 0;
    DefaultTableModel dtm = new DefaultTableModel(header, 0);

    public Main() {
        initComponents();

        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);

        inputPContainer.setText(String.valueOf(canvas1.getPanjangContainer()));
        inputLContainer.setText(String.valueOf(canvas1.getLebarContainer()));
        inputPopulasi.setText(String.valueOf(canvas1.getPopulasi()));
        inputGenerasi.setText(String.valueOf(canvas1.getGenerasi()));
        inputProbMutasi.setText(String.valueOf(canvas1.getProbMutasi()));
        InputIndividuTerseleksi.setText(String.valueOf(canvas1.getTerseleksi()));

        table.setModel(dtm);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
            }
        });

        btnTambahBarang.setMnemonic(KeyEvent.VK_ENTER);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        btnHome = new fosalgo.FButton();
        btnDaftarBarang = new fosalgo.FButton();
        btnSetParameter = new fosalgo.FButton();
        btnGO = new fosalgo.FButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        SolusiPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        solusiTextArea = new javax.swing.JTextArea();
        canvas1 = new Mhmd.Canvas();
        daftarBarangPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        labelPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        menuTambahPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        inputPBarang = new fosalgo.FTextField();
        inputLBarang = new fosalgo.FTextField();
        btnTambahBarang = new fosalgo.FButton();
        menuHapusPanel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        inputCariBarang = new fosalgo.FTextField();
        btnCari = new fosalgo.FButton();
        btnHapus = new fosalgo.FButton();
        btnHapusSemua = new fosalgo.FButton();
        btnRefresh = new fosalgo.FButton();
        menuSavePanel = new javax.swing.JPanel();
        btnSave = new fosalgo.FButton();
        btnOpen = new fosalgo.FButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        setParameterPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        inputLContainer = new fosalgo.FTextField();
        inputPopulasi = new fosalgo.FTextField();
        inputGenerasi = new fosalgo.FTextField();
        btnSaveSetParameter = new fosalgo.FButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        inputPContainer = new fosalgo.FTextField();
        jLabel18 = new javax.swing.JLabel();
        inputProbMutasi = new fosalgo.FTextField();
        jLabel2 = new javax.swing.JLabel();
        InputIndividuTerseleksi = new fosalgo.FTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Container Loading Problem 2D");

        bodyPanel.setBackground(new java.awt.Color(230, 126, 34));

        menuPanel.setBackground(new java.awt.Color(230, 126, 34));
        menuPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        btnHome.setText("HOME");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnDaftarBarang.setText("DAFTAR BARANG");
        btnDaftarBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarBarangActionPerformed(evt);
            }
        });

        btnSetParameter.setText("SET PARAMETER");
        btnSetParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetParameterActionPerformed(evt);
            }
        });

        btnGO.setText("GO");
        btnGO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGOActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/logoContainer_prev_ui (2).png"))); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(44, 62, 80));
        jLabel7.setText("CLP");

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDaftarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnGO, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDaftarBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSetParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGO, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        mainPanel.setBackground(new java.awt.Color(230, 126, 34));
        mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        mainPanel.setLayout(new java.awt.CardLayout());

        homePanel.setBackground(new java.awt.Color(230, 126, 34));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HOME");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/logoContainer_prev_ui.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(44, 62, 80));
        jLabel19.setText("CONTAINER LOADING POBLEM ");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(44, 62, 80));
        jLabel20.setText("Menemukan Solusi Terbaik Dalam Menyusun Barang Kedalam Container");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(130, 130, 130))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(40, 40, 40))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(100, 100, 100)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addContainerGap(241, Short.MAX_VALUE))
        );

        mainPanel.add(homePanel, "card2");

        SolusiPanel.setBackground(new java.awt.Color(211, 84, 0));
        SolusiPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(628, 80));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(0, 255, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(628, 30));
        jPanel2.setLayout(new java.awt.CardLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("SOLUSI ALGORITMA GENETIKA");
        jPanel2.add(jLabel6, "card2");

        jPanel4.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        solusiTextArea.setColumns(20);
        solusiTextArea.setRows(5);
        jScrollPane2.setViewportView(solusiTextArea);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        SolusiPanel.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout canvas1Layout = new javax.swing.GroupLayout(canvas1);
        canvas1.setLayout(canvas1Layout);
        canvas1Layout.setHorizontalGroup(
            canvas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 661, Short.MAX_VALUE)
        );
        canvas1Layout.setVerticalGroup(
            canvas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
        );

        SolusiPanel.add(canvas1, java.awt.BorderLayout.CENTER);

        mainPanel.add(SolusiPanel, "card3");

        daftarBarangPanel.setBackground(new java.awt.Color(243, 156, 18));
        daftarBarangPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel1.setPreferredSize(new java.awt.Dimension(628, 150));
        jPanel1.setLayout(new java.awt.BorderLayout());

        labelPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        labelPanel.setPreferredSize(new java.awt.Dimension(624, 35));
        labelPanel.setLayout(new java.awt.CardLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DAFTAR BARANG");
        labelPanel.add(jLabel3, "card2");

        jPanel1.add(labelPanel, java.awt.BorderLayout.PAGE_START);

        menuTambahPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.gray));
        menuTambahPanel.setPreferredSize(new java.awt.Dimension(280, 111));

        jLabel4.setText("Panjang Barang");

        jLabel16.setText("Lebar Barang");

        inputPBarang.setPreferredSize(new java.awt.Dimension(80, 30));

        inputLBarang.setPreferredSize(new java.awt.Dimension(80, 30));

        btnTambahBarang.setMnemonic(13);
        btnTambahBarang.setText("Tambah Barang");
        btnTambahBarang.setToolTipText("ALT+ENTER");
        btnTambahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuTambahPanelLayout = new javax.swing.GroupLayout(menuTambahPanel);
        menuTambahPanel.setLayout(menuTambahPanelLayout);
        menuTambahPanelLayout.setHorizontalGroup(
            menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuTambahPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTambahBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(inputPBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputLBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        menuTambahPanelLayout.setVerticalGroup(
            menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuTambahPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(inputPBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(menuTambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(inputLBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTambahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(menuTambahPanel, java.awt.BorderLayout.LINE_START);

        menuHapusPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.gray));
        menuHapusPanel.setPreferredSize(new java.awt.Dimension(280, 90));

        jLabel17.setText("ID Barang");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnHapusSemua.setText("Hapus Semua");
        btnHapusSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSemuaActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");

        javax.swing.GroupLayout menuHapusPanelLayout = new javax.swing.GroupLayout(menuHapusPanel);
        menuHapusPanel.setLayout(menuHapusPanelLayout);
        menuHapusPanelLayout.setHorizontalGroup(
            menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHapusPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menuHapusPanelLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(inputCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuHapusPanelLayout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapusSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuHapusPanelLayout.setVerticalGroup(
            menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuHapusPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(menuHapusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusSemua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.add(menuHapusPanel, java.awt.BorderLayout.LINE_END);

        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnOpen.setText("OPEN");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuSavePanelLayout = new javax.swing.GroupLayout(menuSavePanel);
        menuSavePanel.setLayout(menuSavePanelLayout);
        menuSavePanelLayout.setHorizontalGroup(
            menuSavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSavePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        menuSavePanelLayout.setVerticalGroup(
            menuSavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSavePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.add(menuSavePanel, java.awt.BorderLayout.CENTER);

        daftarBarangPanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane1.setViewportView(table);

        daftarBarangPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mainPanel.add(daftarBarangPanel, "card5");

        setParameterPanel.setBackground(new java.awt.Color(46, 204, 113));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SET PARAMETER ");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Panjang Contaiener");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Lebar Container");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Jumlah Populasi");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Jumlah Generasi");

        inputLContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputLContainerActionPerformed(evt);
            }
        });

        btnSaveSetParameter.setText("SAVE");
        btnSaveSetParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSetParameterActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("DesiMeter");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("DesiMeter");

        inputPContainer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPContainerActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Probabilitas Mutasi");

        inputProbMutasi.setToolTipText("Input  Angka 0-1");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Individu Terseleksi");

        InputIndividuTerseleksi.setToolTipText("Lebih Kecil Dari Jumlah Populasi");

        javax.swing.GroupLayout setParameterPanelLayout = new javax.swing.GroupLayout(setParameterPanel);
        setParameterPanel.setLayout(setParameterPanelLayout);
        setParameterPanelLayout.setHorizontalGroup(
            setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
            .addGroup(setParameterPanelLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel18)))
                    .addGroup(setParameterPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))))
                .addGap(52, 52, 52)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InputIndividuTerseleksi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(setParameterPanelLayout.createSequentialGroup()
                        .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputLContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPopulasi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)))
                    .addComponent(inputProbMutasi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputGenerasi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, setParameterPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveSetParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        setParameterPanelLayout.setVerticalGroup(
            setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setParameterPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(38, 38, 38)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(inputPContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputPopulasi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(22, 22, 22)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputGenerasi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputProbMutasi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(setParameterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InputIndividuTerseleksi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(152, 152, 152)
                .addComponent(btnSaveSetParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        mainPanel.add(setParameterPanel, "card6");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(homePanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnDaftarBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarBarangActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(daftarBarangPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_btnDaftarBarangActionPerformed

    private void btnSetParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetParameterActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(setParameterPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_btnSetParameterActionPerformed

    private void btnGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGOActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(SolusiPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

        try{
        canvas1.run();

        int[] urutan = canvas1.kromosomTerbaik;
        solusiTextArea.setText("Urutan Memasukkan Barang : " + Arrays.toString(urutan));
        solusiTextArea.append("\nNilai Fitness : " + String.valueOf(canvas1.fitnerTerbaik));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Eror","Solusi Tidak Ditemukan!!!",JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnGOActionPerformed

    private void btnHapusSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSemuaActionPerformed
        if (canvas1.dataBarang.size() != 0)
        {
            int option = JOptionPane.showConfirmDialog(this, "Yakin Menghapus Semua Barang?", "delete", JOptionPane.YES_NO_OPTION);
            if (option == 0)
            {
                canvas1.dataBarang.clear();
                refresh();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Tidak Ada Barang","EROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusSemuaActionPerformed

    private void inputPContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPContainerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputPContainerActionPerformed

    private void btnSaveSetParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSetParameterActionPerformed
        canvas1.setPanjangContainer(Integer.parseInt(inputPContainer.getText()));
        canvas1.setLebarContainer(Integer.parseInt(inputLContainer.getText()));
        canvas1.setPopulasi(Integer.parseInt(inputPopulasi.getText()));
        canvas1.setGenerasi(Integer.parseInt(inputGenerasi.getText()));
        canvas1.setProbMutasi(Double.parseDouble(inputProbMutasi.getText()));
        canvas1.setTerseleksi(Integer.parseInt(InputIndividuTerseleksi.getText()));
    }//GEN-LAST:event_btnSaveSetParameterActionPerformed

    private void inputLContainerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputLContainerActionPerformed

    }//GEN-LAST:event_inputLContainerActionPerformed

    private void btnTambahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBarangActionPerformed
        try
        {
            int inPnjng = Integer.parseInt(inputPBarang.getText());
            int inLbr = Integer.parseInt(inputLBarang.getText());
            canvas1.dataBarang.add(new Barang(inPnjng, inLbr));
            JOptionPane.showMessageDialog(null, "Barang Berhasil Ditambahkan", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            inputPBarang.setText(null);
            inputLBarang.setText(null);
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Peringatan", "Data Yang Anda Masukkan Invalid", JOptionPane.ERROR_MESSAGE);
        }

        refresh();
    }//GEN-LAST:event_btnTambahBarangActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        String barang = String.valueOf(inputCariBarang.getText());
        int option = JOptionPane.showConfirmDialog(this, "Hapus Barang " + barang, "Delete", JOptionPane.YES_NO_OPTION);
        if (option == 0)
        {
            int idBarang = Integer.parseInt(inputCariBarang.getText());
            canvas1.dataBarang.remove(idBarang - 1);
            refresh();
            inputCariBarang.setText(null);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        int idBarang = Integer.parseInt(inputCariBarang.getText());
        dtm.setRowCount(0);
        Barang barang = canvas1.dataBarang.get(idBarang - 1);
        int id = idBarang;
        int panjang = barang.panjang;
        int lebar = barang.lebar;

        Object[] data =
        {
            id, panjang, lebar
        };
        dtm.addRow(data);

    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
//        try{
//            String namaFile = JOptionPane.showInputDialog(null,"Masukkan Nama File","Save File",JOptionPane.WARNING_MESSAGE);
//            save(namaFile,canvas1.dataBarang);
//        }catch(Exception e){
//            e.printStackTrace();
//        
        save(canvas1.dataBarang);

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        open();
        refresh();
    }//GEN-LAST:event_btnOpenActionPerformed

    public  void refresh() {
        if (canvas1.dataBarang != null)
        {
            dtm.setRowCount(0);
            for (int i = 0; i < canvas1.dataBarang.size(); i++)
            {
                Barang barang = canvas1.dataBarang.get(i);
                int IDBarang = i + 1;
                int panjang = barang.panjang;
                int lebar = barang.lebar;

                Object[] data =
                {
                    IDBarang, panjang, lebar
                };
                dtm.addRow(data);

            }
        }
    }
    
    public static void save(ArrayList<Barang> data){
        try {
            String url = "src//Save//DataBarang.txt";
            FileWriter fw = new FileWriter(url);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < data.size(); i++) {
                Barang barang = data.get(i);
                bw.append(barang.panjang+";"+ barang.lebar+"\n");
            }
            bw.close();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void open(){
        try{
            ArrayList<Barang> data = new ArrayList<>();
            FileReader fr = new FileReader("src//Save//DataBarang.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null){
                String [] kolom = line.split(";");
                int panjang =Integer.parseInt(kolom[0]);
                int lebar = Integer.parseInt(kolom[1]);
                data.add(new Barang(panjang, lebar));
            }
            br.close();
            fr.close();
            Canvas.dataBarang = data;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fosalgo.FTextField InputIndividuTerseleksi;
    private javax.swing.JPanel SolusiPanel;
    private javax.swing.JPanel bodyPanel;
    private fosalgo.FButton btnCari;
    private fosalgo.FButton btnDaftarBarang;
    private fosalgo.FButton btnGO;
    private fosalgo.FButton btnHapus;
    private fosalgo.FButton btnHapusSemua;
    private fosalgo.FButton btnHome;
    private fosalgo.FButton btnOpen;
    private fosalgo.FButton btnRefresh;
    private fosalgo.FButton btnSave;
    private fosalgo.FButton btnSaveSetParameter;
    private fosalgo.FButton btnSetParameter;
    private fosalgo.FButton btnTambahBarang;
    private Mhmd.Canvas canvas1;
    private javax.swing.JPanel daftarBarangPanel;
    private javax.swing.JPanel homePanel;
    private fosalgo.FTextField inputCariBarang;
    private fosalgo.FTextField inputGenerasi;
    private fosalgo.FTextField inputLBarang;
    private fosalgo.FTextField inputLContainer;
    private fosalgo.FTextField inputPBarang;
    private fosalgo.FTextField inputPContainer;
    private fosalgo.FTextField inputPopulasi;
    private fosalgo.FTextField inputProbMutasi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuHapusPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel menuSavePanel;
    private javax.swing.JPanel menuTambahPanel;
    private javax.swing.JPanel setParameterPanel;
    private javax.swing.JTextArea solusiTextArea;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
