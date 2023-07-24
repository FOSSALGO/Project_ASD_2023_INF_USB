/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projecttspgreedy;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GradasiPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Atur warna gradiasi
        Color color1 = Color.decode("#11009E");
        Color color2 = Color.decode("#9336B4");

        // Atur sudut gradiasi
        int startX = 0;
        int startY = 0;
        int endX = getWidth();
        int endY = getHeight();

        // Buat gradiasi paint
        GradientPaint gradient = new GradientPaint(startX, startY, color1, endX, endY, color2);

        // Atur latar belakang dengan gradiasi paint
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}
