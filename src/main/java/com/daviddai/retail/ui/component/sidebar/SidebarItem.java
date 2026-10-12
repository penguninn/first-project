package com.daviddai.retail.ui.component.sidebar;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.daviddai.retail.ui.UiUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarItem extends JPanel {

    private static final int ITEM_HEIGHT = 38;
    private static final int ICON_TEXT_GAP = 18;
    private static final int LEFT_PADDING = 20;
    private static final int HIGHLIGHT_INSET = 10;
    private static final int HIGHLIGHT_RADIUS = 6;

    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    private static final Color HOVER_BG = new Color(255, 255, 255, 40);
    private static final Color SELECTED_BG = new Color(255, 255, 255, 90);

    private final String label;
    private final Runnable onClick;

    private boolean hovered;
    private boolean selected;

    public SidebarItem(String iconName, String label, Runnable onClick) {
        this.label = label;
        this.onClick = onClick;
        initLayout(iconName);
        initMouse();
    }

    private void initLayout(String iconName) {
        setLayout(new MigLayout(
                "fill, insets 0 " + LEFT_PADDING + " 0 " + LEFT_PADDING + ", gapx " + ICON_TEXT_GAP + ", gapy 0",
                "[][grow]",
                "[center]"));
        setOpaque(false);
        setPreferredSize(new Dimension(0, ITEM_HEIGHT));

        JLabel icon = new JLabel(UiUtils.loadIcon(iconName));
        icon.setForeground(Color.WHITE);

        JLabel text = new JLabel(label);
        text.setFont(new Font("SansSerif", Font.BOLD, 12));
        text.setForeground(TEXT_COLOR);

        add(icon, "aligny 50%");
        add(text, "aligny 50%, growx");
    }

    private void initMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                setCursor(Cursor.getDefaultCursor());
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null)
                    onClick.run();
            }
        });
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (selected || hovered) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(selected ? SELECTED_BG : HOVER_BG);
                g2.fillRoundRect(
                        HIGHLIGHT_INSET, 0,
                        getWidth() - 2 * HIGHLIGHT_INSET, getHeight(),
                        HIGHLIGHT_RADIUS, HIGHLIGHT_RADIUS);
            } finally {
                g2.dispose();
            }
        }
        super.paintComponent(g);
    }

}
