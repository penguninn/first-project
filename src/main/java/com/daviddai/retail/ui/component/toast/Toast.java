package com.daviddai.retail.ui.component.toast;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

public class Toast extends JWindow {

    private final JPanel panel;
    private final JLabel lblMessage;

    public enum Type {
        SUCCESS(new Color(46, 204, 113), "✔"),
        ERROR(new Color(231, 76, 60), "✖"),
        INFO(new Color(52, 152, 219), "ℹ"),
        WARNING(new Color(241, 196, 15), "⚠");

        final Color bgColor;
        final String icon;

        Type(Color bgColor, String icon) {
            this.bgColor = bgColor;
            this.icon = icon;
        }
    }

    public Toast(Window owner, String message, Type type) {
        super(owner);
        setAlwaysOnTop(true);

        panel = new JPanel(new MigLayout("insets 12 16, gap 10", "[]", "[]"));
        panel.setBackground(type.bgColor);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:12");

        // Icon
        JLabel lblIcon = new JLabel(type.icon);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        lblIcon.setForeground(Color.WHITE);

        // Message
        lblMessage = new JLabel(message);
        lblMessage.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMessage.setForeground(Color.WHITE);

        panel.add(lblIcon);
        panel.add(lblMessage);

        setContentPane(panel);
        pack();

        if (owner != null) {
            int x = owner.getX() + owner.getWidth() - getWidth() - 30;
            int y = owner.getY() + owner.getHeight() - getHeight() - 30;
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

    private void showToast(int durationMs) {
        setVisible(true);
        Timer timer = new Timer(durationMs, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }

    public static void success(Component parent, String message) {
        show(parent, message, Type.SUCCESS);
    }

    public static void error(Component parent, String message) {
        show(parent, message, Type.ERROR);
    }

    public static void info(Component parent, String message) {
        show(parent, message, Type.INFO);
    }

    public static void warning(Component parent, String message) {
        show(parent, message, Type.WARNING);
    }

    private static void show(Component parent, String message, Type type) {
        Window owner = parent == null ? null : SwingUtilities.getWindowAncestor(parent);
        
        SwingUtilities.invokeLater(() -> {
            Toast toast = new Toast(owner, message, type);
            toast.showToast(2500);
        });
    }
}
