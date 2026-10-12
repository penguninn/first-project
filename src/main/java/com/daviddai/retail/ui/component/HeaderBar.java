package com.daviddai.retail.ui.component;

import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.ui.UiUtils;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.daviddai.retail.ui.UiUtils.loadIcon;

public class HeaderBar extends JPanel {

    private static final Color GRADIENT_START = Color.decode("#38cedc");
    private static final Color GRADIENT_END = Color.decode("#38cedc");
    private static final Color HOVER_CLOSE = new Color(255, 42, 28);
    private static final Color HOVER_DEFAULT = new Color(0, 0, 0, 80);

    private static final int BUTTON_WIDTH = 45;
    private static final int HEADER_HEIGHT = 40;

    private static final ImageIcon ICON_CLOSE = loadIcon("close_white.png");
    private static final ImageIcon ICON_MINIMIZE = loadIcon("window_minimize_white.png");
    private static final ImageIcon ICON_MAXIMIZE = loadIcon("window_maximize_white.png");

    private final JLabel lblemployeeDtoName = new JLabel();
    private final JButton btnMinimize = new JButton();
    private final JButton btnMaximize = new JButton();
    private final JButton btnClose = new JButton();

    // ===== Callbacks =====
    private Runnable onCloseAction;
    private Runnable onMinimizeAction;
    private Runnable onMaximizeAction;
    private Runnable onProfileAction;

    // ===== State =====
    private boolean maximized = false;
    private Point dragInitialClick;

    public HeaderBar(EmployeeResult currentUser) {
        initLayout();
        initStyle(currentUser);
        initActions();
    }

    // ===== Layout =====
    private void initLayout() {
        setLayout(new MigLayout(
                "fill, insets 0, gap 0",
                "[right, 220][grow][left]",
                "[" + HEADER_HEIGHT + ", center]"));

        JPanel btnsPanel = new JPanel(new MigLayout(
                "fill, insets 0, gap 0",
                "[grow][" + BUTTON_WIDTH + "!][" + BUTTON_WIDTH + "!][" + BUTTON_WIDTH + "!]",
                "[grow, center]"));
        btnsPanel.setOpaque(false);
        btnsPanel.add(lblemployeeDtoName, "growx, h " + HEADER_HEIGHT + "!, alignx right, gapright 20");
        btnsPanel.add(btnMinimize, "w " + BUTTON_WIDTH + "!, h " + HEADER_HEIGHT + "!, aligny center");
        btnsPanel.add(btnMaximize, "w " + BUTTON_WIDTH + "!, h " + HEADER_HEIGHT + "!, aligny center");
        btnsPanel.add(btnClose, "w " + BUTTON_WIDTH + "!, h " + HEADER_HEIGHT + "!, aligny center");
        setOpaque(false);
        add(createLogoPanel(), "center, grow");
        add(new JLabel(""));
        add(btnsPanel, "aligny center");
    }

    // ===== Style =====
    private void initStyle(EmployeeResult currentUser) {
        String displayName = (currentUser != null && currentUser.fullName() != null)
                ? currentUser.fullName()
                : "Guest";
        lblemployeeDtoName.setText(displayName);
        lblemployeeDtoName.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        lblemployeeDtoName.setForeground(Color.WHITE);

        styleButton(btnMinimize, ICON_MINIMIZE, HOVER_DEFAULT);
        styleButton(btnMaximize, ICON_MAXIMIZE, HOVER_DEFAULT);
        styleButton(btnClose, ICON_CLOSE, HOVER_CLOSE);
    }

    private JPanel createLogoPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fill, align center center, insets 0 30 0 30, gap 20",
                "[grow][grow]",
                "[30!]")) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 60));
                    g2.setStroke(new BasicStroke(1f));

                    int x = getWidth() - 1;
                    g2.drawLine(x, 0, x, getHeight());

                } finally {
                    g2.dispose();
                }
            }

        };
        panel.setOpaque(false);

        JLabel icon = new JLabel(UiUtils.loadIcon("shopping_cart_blue"));
        JLabel title = new JLabel("Pengunin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);

        panel.add(title, "aligny center");
        panel.add(icon, "aligny center");
        return panel;
    }

    private void styleButton(JButton button, ImageIcon icon, Color hoverBg) {
        button.setIcon(icon);
        button.setText(null);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(false);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.getModel().addChangeListener(e -> {
            ButtonModel model = button.getModel();
            if (model.isRollover()) {
                button.setBackground(hoverBg);
            } else {
                button.setBackground(new Color(0, 0, 0, 0));
            }
        });
    }

    // ===== Actions =====
    private void initActions() {
        MouseAdapter profileClick = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onProfileAction != null)
                    onProfileAction.run();
            }
        };
        lblemployeeDtoName.addMouseListener(profileClick);

        btnClose.addActionListener(e -> onCloseAction.run());
        btnMinimize.addActionListener(e -> onMinimizeAction.run());
        btnMaximize.addActionListener(e -> onMaximizeAction.run());

        lblemployeeDtoName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblemployeeDtoName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblemployeeDtoName.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public void setOnClose(Runnable action) {
        this.onCloseAction = action;
    }

    public void setOnMinimize(Runnable action) {
        this.onMinimizeAction = action;
    }

    public void setOnMaximize(Runnable action) {
        this.onMaximizeAction = action;
    }

    public void setOnProfileClick(Runnable action) {
        this.onProfileAction = action;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public void enableDragging(JFrame frame) {
        MouseAdapter handler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource() != HeaderBar.this)
                    return;
                dragInitialClick = e.getLocationOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (maximized || dragInitialClick == null)
                    return;

                Point current = e.getLocationOnScreen();
                int dx = current.x - dragInitialClick.x;
                int dy = current.y - dragInitialClick.y;

                frame.setLocation(
                        frame.getLocation().x + dx,
                        frame.getLocation().y + dy);
                dragInitialClick = current;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragInitialClick = null;
            }
        };

        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

    // ===== Paint =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(
                    0, 0, GRADIENT_START,
                    0, getHeight(), GRADIENT_END);
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        } finally {
            g2.dispose();
        }
    }

}
