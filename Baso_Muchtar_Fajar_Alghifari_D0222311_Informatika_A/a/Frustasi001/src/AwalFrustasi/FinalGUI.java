/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AwalFrustasi;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class FinalGUI extends javax.swing.JFrame {

    /**
     * Creates new form FinalGUI
     */
    public FinalGUI() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        canvasGila2 = new AwalFrustasi.CanvasGila();
        fButtonSave = new fosalgo.FButton();
        fButtonOpen = new fosalgo.FButton();
        fButtonReset = new fosalgo.FButton();
        fButtonRandom = new fosalgo.FButton();
        fButtonRun = new fosalgo.FButton();
        fTextFieldVertexAwal = new fosalgo.FTextField();
        fTextFieldVertexTujuan = new fosalgo.FTextField();
        fTextFieldVertexRandom = new fosalgo.FTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaSolusi = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fButtonExit = new fosalgo.FButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 0, 51));
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 255, 255));

        javax.swing.GroupLayout canvasGila2Layout = new javax.swing.GroupLayout(canvasGila2);
        canvasGila2.setLayout(canvasGila2Layout);
        canvasGila2Layout.setHorizontalGroup(
            canvasGila2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1036, Short.MAX_VALUE)
        );
        canvasGila2Layout.setVerticalGroup(
            canvasGila2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        fButtonSave.setText("Save");
        fButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonSaveActionPerformed(evt);
            }
        });

        fButtonOpen.setText("Open");
        fButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonOpenActionPerformed(evt);
            }
        });

        fButtonReset.setText("Reset");
        fButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonResetActionPerformed(evt);
            }
        });

        fButtonRandom.setText("Random");
        fButtonRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonRandomActionPerformed(evt);
            }
        });

        fButtonRun.setText("Run");
        fButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonRunActionPerformed(evt);
            }
        });

        jTextAreaSolusi.setBackground(new java.awt.Color(255, 255, 255));
        jTextAreaSolusi.setColumns(20);
        jTextAreaSolusi.setRows(5);
        jScrollPane1.setViewportView(jTextAreaSolusi);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setText("Vertex Awal");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Vertex Tujuan");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/[removal.ai]_tmp-648f9efacad0b (1) (1).png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Shortest Path Graph");

        fButtonExit.setText("Exit");
        fButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fButtonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(jLabel2))
                                        .addComponent(fTextFieldVertexAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fTextFieldVertexTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)))
                                .addComponent(fButtonSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fButtonOpen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel4))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(fButtonRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fTextFieldVertexRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fButtonRun, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(canvasGila2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(canvasGila2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fButtonOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fTextFieldVertexTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fTextFieldVertexAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fButtonRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fTextFieldVertexRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(fButtonRun, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonRunActionPerformed
        // TODO add your handling code here:
        String sStart = fTextFieldVertexAwal.getText();
        String sFinish = fTextFieldVertexTujuan.getText();
        
        int iStart = Integer.parseInt(sStart);
        int iFinish = Integer.parseInt(sFinish);
        
        canvasGila2.start = iStart;
        canvasGila2.finish = iFinish;
        canvasGila2.generateAdjacency();
        canvasGila2.solusiBF();
        
        
        ArrayList<Integer> urutan = canvasGila2.bestPath;
        jTextAreaSolusi.setText("Solusi Brute Force Terbaik: "+urutan);
        Integer mJ = canvasGila2.minJarak;
        jTextAreaSolusi.append(" Jarak: "+mJ);
    }//GEN-LAST:event_fButtonRunActionPerformed

    private void fButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonSaveActionPerformed
        // TODO add your handling code here:
                canvasGila2.save();
    }//GEN-LAST:event_fButtonSaveActionPerformed

    private void fButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonOpenActionPerformed
        // TODO add your handling code here:
        canvasGila2.open();
    }//GEN-LAST:event_fButtonOpenActionPerformed

    private void fButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonResetActionPerformed
        // TODO add your handling code here:
        canvasGila2.reset();
    }//GEN-LAST:event_fButtonResetActionPerformed

    private void fButtonRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonRandomActionPerformed
        // TODO add your handling code here:
        String sRandom = fTextFieldVertexRandom.getText();
        int iRandom = Integer.parseInt(sRandom);
        canvasGila2.randomVertex(iRandom);
    }//GEN-LAST:event_fButtonRandomActionPerformed

    private void fButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fButtonExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_fButtonExitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FinalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FinalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FinalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FinalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinalGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private AwalFrustasi.CanvasGila canvasGila2;
    private fosalgo.FButton fButtonExit;
    private fosalgo.FButton fButtonOpen;
    private fosalgo.FButton fButtonRandom;
    private fosalgo.FButton fButtonReset;
    private fosalgo.FButton fButtonRun;
    private fosalgo.FButton fButtonSave;
    private fosalgo.FTextField fTextFieldVertexAwal;
    private fosalgo.FTextField fTextFieldVertexRandom;
    private fosalgo.FTextField fTextFieldVertexTujuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaSolusi;
    private keeptoo.KGradientPanel kGradientPanel1;
    // End of variables declaration//GEN-END:variables
}
