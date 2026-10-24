package com.daviddai.retail.ui.component.sidebar;

import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.ui.component.Screen;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class SidebarMenu extends JPanel {

    private static final int WIDTH = 220;

    private final EmployeeResult currentUser;
    private final Map<Screen, SidebarItem> items = new EnumMap<>(Screen.class);

    private Consumer<Screen> onScreenSelected;
    private Runnable onLogout;
    private Screen selectedScreen;
    private SidebarItem logoutItem;

    public SidebarMenu(EmployeeResult currentUser) {
        this.currentUser = currentUser;
        initLayout();
    }

    // ===== Layout =====
    private void initLayout() {
        setLayout(new MigLayout(
                "fill, insets 20 0, gap 0",
                "[grow]",
                "[grow]0[]0[]"));
        setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, 0));

        add(createMenuPanel(), "grow, push, wrap");
        add(createSeparator(), "growx, wrap");
        add(createLogoutItem(), "growx");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "fillx, insets 0, gap 0",
                "[grow]",
                ""));
        panel.setOpaque(false);

        for (Screen screen : Screen.forRole(currentUser.role())) {
            SidebarItem item = new SidebarItem(
                    screen.getIconName(),
                    screen.getTitle(),
                    () -> selectScreen(screen));
            items.put(screen, item);
            panel.add(item, "growx, wrap");
        }
        return panel;
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 60));
        sep.setBackground(new Color(0, 0, 0, 0));
        return sep;
    }

    private SidebarItem createLogoutItem() {
        logoutItem = new SidebarItem("logout_white", "Đăng xuất", this::handleLogout);
        return logoutItem;
    }

    // ===== Logic =====
    private void selectScreen(Screen screen) {
        if (selectedScreen == screen)
            return;

        SidebarItem prev = items.get(selectedScreen);
        if (prev != null)
            prev.setSelected(false);

        selectedScreen = screen;
        SidebarItem curr = items.get(screen);
        if (curr != null)
            curr.setSelected(true);

        if (onScreenSelected != null) {
            onScreenSelected.accept(screen);
        }
    }

    private void handleLogout() {
        if (onLogout != null)
            onLogout.run();
    }

    // ===== Public API =====
    public void setOnScreenSelected(Consumer<Screen> listener) {
        this.onScreenSelected = listener;
    }

    public void setOnLogout(Runnable listener) {
        this.onLogout = listener;
    }

    public void select(Screen screen) {
        selectScreen(screen);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(
                    0, 0, Color.decode("#36D1DC"),
                    0, getHeight(), Color.decode("#5B86E5")));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 60));
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(0, 0, getWidth(), 0);
        } finally {
            g2.dispose();
        }
        super.paintComponent(g);
    }

}
