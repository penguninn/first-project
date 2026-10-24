package com.daviddai.retail.ui.component;

import com.daviddai.retail.app.AppContext;
import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.ui.component.sidebar.SidebarMenu;
import com.daviddai.retail.ui.product.ProductPresenter;
import com.daviddai.retail.ui.product.ProductScreen;
import com.daviddai.retail.ui.product.ProductView;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainFrame extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    // ===== Layout constants =====
    private static final int DEFAULT_WIDTH = 1400;
    private static final int DEFAULT_HEIGHT = 850;
    private static final int MIN_WIDTH = 1000;
    private static final int MIN_HEIGHT = 600;
    private static final int SIDEBAR_WIDTH = 220;
    private static final int HEADER_HEIGHT = 40;

    private final EmployeeResult currentUser;
    private final CardLayout screenLayout = new CardLayout();
    private final JPanel screenContainer = new JPanel(screenLayout);
    private final Map<Screen, JPanel> screens = new EnumMap<>(Screen.class);

    private final SidebarMenu sidebarMenu;
    private final HeaderBar headerBar;

    private boolean maximized = false;

    // ===== Constructor =====
    public MainFrame(EmployeeResult currentUser) {
        if (currentUser == null) {
            throw new IllegalArgumentException("currentUser không được null");
        }
        this.currentUser = currentUser;

        this.sidebarMenu = new SidebarMenu(currentUser);
        this.headerBar = new HeaderBar(currentUser);

        initScreens();
        initLayout();
        initEvents();
        setupWindow();
    }

    // ===== Window setup =====
    private void setupWindow() {
        setTitle("Retail App — " + safeName(currentUser));
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    }

    // ===== Screens =====
    private void initScreens() {
        registerScreen(Screen.HOME, () -> placeholder("Home"));
        registerScreen(Screen.PRODUCTS, () -> {
            ProductView view = new ProductScreen();
            new ProductPresenter(view, AppContext.getInstance().getProductService());
            return (ProductScreen) view;
        });
        registerScreen(Screen.SELL, () -> placeholder("Bán hàng"));
        registerScreen(Screen.BILL, () -> placeholder("Hóa đơn"));
        registerScreen(Screen.PROMOTION, () -> placeholder("Khuyến mãi"));
        registerScreen(Screen.STAFFS, () -> placeholder("Nhân viên"));
        registerScreen(Screen.CUSTOMER, () -> placeholder("Khách hàng"));
        registerScreen(Screen.REFUND, () -> placeholder("Trả hàng"));
        registerScreen(Screen.PROFILE, () -> placeholder("Tài khoản"));
    }

    private void registerScreen(Screen screen, Supplier<JPanel> factory) {
        JPanel panel = factory.get();
        screens.put(screen, panel);
        screenContainer.add(panel, screen.name());
    }

    private JPanel placeholder(String title) {
        JPanel p = new JPanel(new MigLayout("fill, insets 24", "[center]", "[center]"));
        JLabel lbl = new JLabel(title + " — đang phát triển");
        lbl.putClientProperty("FlatLaf.style", "font:italic +4; foreground:#D3D3D3");
        p.add(lbl);
        return p;
    }

    // ===== Layout =====
    private void initLayout() {
        JPanel root = new JPanel(new MigLayout(
                "fill, insets 0, gap 0",
                "[grow, fill]",
                "[" + HEADER_HEIGHT + "!][grow, fill]"));
        root.add(headerBar, "growx, wrap");

        JPanel contentArea = new JPanel(new MigLayout(
                "fill, insets 0, gap 0",
                "[" + SIDEBAR_WIDTH + "!][grow, fill]",
                "[grow, fill]"));
        contentArea.add(sidebarMenu, "growy");
        contentArea.add(screenContainer, "grow, push");

        root.add(contentArea, "grow, push");

        setContentPane(root);
    }

    // ===== Events =====
    private void initEvents() {
        sidebarMenu.setOnScreenSelected(this::showScreen);
        sidebarMenu.setOnLogout(this::onLogout);

        headerBar.setOnClose(this::onExit);
        headerBar.setOnMinimize(() -> setState(Frame.ICONIFIED));
        headerBar.setOnMaximize(this::toggleMaximize);
        headerBar.setOnProfileClick(() -> sidebarMenu.select(Screen.PROFILE));

        headerBar.enableDragging(this);

        sidebarMenu.select(Screen.HOME);
    }

    // ===== Public API =====
    public void showScreen(Screen screen) {
        JPanel panel = screens.get(screen);
        if (panel == null) {
            log.warn("Screen chưa đăng ký: {}", screen);
            return;
        }
        log.debug("Chuyển màn hình: {}", screen);
        screenLayout.show(screenContainer, screen.name());
        setTitle("Retail App — " + screen.getTitle());
    }

    // ===== Actions =====
    private void toggleMaximize() {
        if (maximized) {
            setExtendedState(Frame.NORMAL);
        } else {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        maximized = !maximized;
        headerBar.setMaximized(maximized);
    }

    private void onLogout() {
        if (!confirm("Đăng xuất khỏi tài khoản?"))
            return;

        log.info("Đăng xuất: {}", safeName(currentUser));
        dispose();
        /// AppNavigator.showLogin();
    }

    private void onExit() {
        if (!confirm("Thoát ứng dụng?"))
            return;

        log.info("Thoát ứng dụng...");
        // AppContext.getInstance().close();
        dispose();
        System.exit(0);
    }

    private boolean confirm(String message) {
        int r = JOptionPane.showConfirmDialog(
                this, message, "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return r == JOptionPane.YES_OPTION;
    }

    private static String safeName(EmployeeResult user) {
        if (user == null)
            return "Guest";
        String name = user.fullName();
        return (name == null || name.isBlank()) ? "Guest" : name;
    }
}
