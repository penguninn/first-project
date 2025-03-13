/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.modelUI;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class DashedBorder extends AbstractBorder {
    private final Color color;
    private final float[] dashPattern;
    private final int thickness;

    public DashedBorder(Color color, float[] dashPattern, int thickness) {
        this.color = color;
        this.dashPattern = dashPattern;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
        g2d.drawRect(x, y + height - thickness, width, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, thickness, 0);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = 0;
        insets.bottom = thickness;
        return insets;
    }
}
