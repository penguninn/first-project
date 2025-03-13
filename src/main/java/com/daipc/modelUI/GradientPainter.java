/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.modelUI;

import javax.swing.*;
import java.awt.*;

public class GradientPainter {
    public static void applyGradient(JPanel panel, Color startColor, Color endColor) {
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0)); // Transparent background

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.repaint();
            }
        });

        panel.addPropertyChangeListener("size", evt -> panel.repaint());

        panel.addPropertyChangeListener("background", evt -> panel.repaint());

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                panel.repaint();
            }
        });

        panel.addPropertyChangeListener("border", evt -> panel.repaint());

        panel.addPropertyChangeListener("enabled", evt -> panel.repaint());

        panel.addPropertyChangeListener("font", evt -> panel.repaint());

        panel.addPropertyChangeListener("foreground", evt -> panel.repaint());

        panel.addPropertyChangeListener("layout", evt -> panel.repaint());

        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gradient = new GradientPaint(0, 0, startColor, c.getWidth(), c.getHeight(), endColor);
                g2.setPaint(gradient);
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());

                g2.dispose();
                super.paint(g, c);
            }
        });
    }
}    