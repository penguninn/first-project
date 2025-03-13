package com.daipc.form;

import com.daipc.UI.PanelChart;
import com.daipc.component.Card;
import com.daipc.model.ChiTietSP;
import com.daipc.model.ThongKe;
import com.daipc.modelUI.Model_Card;
import com.daipc.repo.QuanLiThongKe;
import com.daipc.swing.PanelBorder;
import com.daipc.table.TableCustom;
import com.formdev.flatlaf.extras.components.FlatRadioButton;
import com.formdev.flatlaf.extras.components.FlatScrollPane;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public final class Form_Home extends javax.swing.JPanel {

    private JPanel panel;
    private JLayeredPane layerPane;
    private PanelChart panelChart;
    private PanelBorder panelBorder;
    private PanelBorder panelHeader;
    private JPanel panelTable;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int index = 0;
    private JRadioButton chartDas;
    private JRadioButton tableDas;
    private ButtonGroup groupBtn;
    private JLabel labelHeader;
    private JTabbedPane tabbedPane;

    private JPanel doanhThu;
    private JPanel khachHang;
    private JPanel nhanVien;
    private JPanel sanPham;

    private JPanel doanhThuFilter;
    private JPanel khachHangFilter;
    private JPanel nhanVienFilter;
    private JPanel sanPhamFilter;

    private JTable doanhThuTable;
    private JTable sanPhamTable;
    private JTable nhanVienTable;
    private JTable khachHangTable;

    private JComboBox cboFilterDT;
    private JComboBox cboFilterSP;
    private JComboBox cboFilterNV;
    private JComboBox cboFilterKH;
    
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;

    private final QuanLiThongKe QLTK = new QuanLiThongKe();
    private final DefaultTableModel modelSP = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Bán", "Màu Sắc", "Size", "Chất Liệu", "Độ Dày", "Số Lượng"}, 0);
    private DefaultTableModel modelDT;
    private DefaultTableModel modelNV;
    private DefaultTableModel modelKH;
    private List<ChiTietSP> listSP;
    private List<ChiTietSP> listDT;
    private List<ChiTietSP> listNV;
    private List<ChiTietSP> listKH;
    

    public Form_Home() {
        initComponents();
        init();
        loadDataCard();
        loadDoanhThuData();
        loadSanPhamData();
        loadNhanVienData();
        loadKhachHangData();
        
        chartDas.addActionListener(e -> {
            index = 0;
            swicthForm();
        });

        tableDas.addActionListener(e -> {
            index = 1;
            swicthForm();
        });
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadDataCard();
                loadDoanhThuData();
                loadSanPhamData();
                loadNhanVienData();
                loadKhachHangData();
                panelChart.reloadData();
            }
        });
    }

    public void init() {
        panel = new JPanel();
        layerPane = new JLayeredPane();
        panelChart = new PanelChart();
        panelBorder = new PanelBorder();
        panelHeader = new PanelBorder();
        panelTable = new JPanel();
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        groupBtn = new ButtonGroup();
        chartDas = new JRadioButton();
        tableDas = new FlatRadioButton();
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        cboFilterDT = new JComboBox<>();
        cboFilterSP = new JComboBox<>();
        cboFilterNV = new JComboBox<>();
        cboFilterKH = new JComboBox<>();
        

        doanhThu = new JPanel(new MigLayout("fill", "0[]0", "0[fill]5[grow]0"));
        sanPham = new JPanel(new MigLayout("fill", "0[]0", "0[fill]5[grow]0"));
        nhanVien = new JPanel(new MigLayout("fill", "0[]0", "0[fill]5[grow]0"));
        khachHang = new JPanel(new MigLayout("fill", "0[]0", "0[fill]5[grow]0"));

        doanhThuFilter = new JPanel(new MigLayout("fill", "[grow][right]", "[]"));
        sanPhamFilter = new JPanel(new MigLayout("fill", "[grow][right]", "[]"));
        nhanVienFilter = new JPanel(new MigLayout("fill"));
        khachHangFilter = new JPanel(new MigLayout("fill"));

        doanhThuFilter.setBackground(Color.white);
        sanPhamFilter.setBackground(Color.white);
        nhanVienFilter.setBackground(Color.white);
        khachHangFilter.setBackground(Color.white);
        doanhThu.setBackground(Color.white);
        sanPham.setBackground(Color.white);
        nhanVien.setBackground(Color.white);
        khachHang.setBackground(Color.white);

        cboFilterSP.setPreferredSize(new Dimension(350, cboFilterSP.getPreferredSize().height));

        sanPhamFilter.add(cboFilterSP, "align right, gapright 30");

        doanhThuTable = new JTable();
        sanPhamTable = new JTable();
        nhanVienTable = new JTable();
        khachHangTable = new JTable();

        labelHeader = new JLabel("Tổng Hợp Thống Kê");
        labelHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelHeader.setForeground(Color.BLACK);

        panel.setBackground(new Color(234, 234, 234));
        panelChart.setBackground(Color.white);
        panelTable.setBackground(Color.white);
        cardPanel.setBackground(Color.white);
        panelBorder.setBackground(Color.white);
        panelHeader.setBackground(Color.white);

        cardPanel.setLayout(cardLayout);
        cardPanel.add(panelChart, "Chart");
        cardPanel.add(panelTable, "Table");

        FlatScrollPane scrollPaneDoanhThu = new FlatScrollPane();
        scrollPaneDoanhThu.setViewportView(doanhThuTable);
        FlatScrollPane scrollPaneSanPham = new FlatScrollPane();
        scrollPaneSanPham.setViewportView(sanPhamTable);
        FlatScrollPane scrollPaneNhanVien = new FlatScrollPane();
        scrollPaneNhanVien.setViewportView(nhanVienTable);
        FlatScrollPane scrollPaneKhachHang = new FlatScrollPane();
        scrollPaneKhachHang.setViewportView(khachHangTable);

        TableCustom.apply(scrollPaneDoanhThu, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneSanPham, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneNhanVien, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollPaneKhachHang, TableCustom.TableType.DEFAULT);

        doanhThu.add(doanhThuFilter, "h 50!, span, growx, wrap");
        doanhThu.add(scrollPaneDoanhThu, "grow");
        tabbedPane.addTab("Doanh Thu", doanhThu);

        sanPham.add(sanPhamFilter, "h 50!, span, growx, wrap");
        sanPham.add(scrollPaneSanPham, "grow");
        tabbedPane.addTab("Sản Phẩm", sanPham);

        nhanVien.add(nhanVienFilter, "h 50!, span, growx, wrap");
        nhanVien.add(scrollPaneNhanVien, "grow");
        tabbedPane.addTab("Nhân Viên", nhanVien);

        khachHang.add(khachHangFilter, "h 50!, span, growx, wrap");
        khachHang.add(scrollPaneKhachHang, "grow");
        tabbedPane.addTab("Khách Hàng", khachHang);

        panelTable.setLayout(new MigLayout("fill"));
        panelTable.add(tabbedPane, "grow");
        groupBtn.add(chartDas);
        groupBtn.add(tableDas);
        chartDas.setText("Biểu đồ");
        chartDas.setSelected(true);
        tableDas.setText("Bảng");
        panelHeader.setLayout(new MigLayout("fill", "20[]80[]50[grow]", "10[]"));
        panelHeader.add(labelHeader, "align left");
        panelHeader.add(chartDas, "center");
        panelHeader.add(tableDas, "left");

        panelBorder.setLayout(new MigLayout("fill", "0[fill]0", "0[]8[grow]5"));
        panelBorder.add(panelHeader, "h 40!, wrap");
        panelBorder.add(cardPanel, "grow");

        layerPane.setLayout(new GridLayout(0, 4, 10, 0));
        panel.setLayout(new MigLayout("fill", "10[fill]10", "10[]8[grow]10"));
        panel.add(layerPane, "h 150!, wrap");
        panel.add(panelBorder, "grow");

        this.setLayout(new MigLayout("fill", "0[fill]0", "0[grow]0"));
        this.add(panel, "grow");
        
        Model_Card model1 = new Model_Card("1", "", "", "#FF3333", "#FF6666");
        Model_Card model2 = new Model_Card("2", "Tổng Doanh Thu", "", "#9999FF", "#0066FF");
        Model_Card model3 = new Model_Card("3", "Tổng Chi Phí", "", "#66FF66", "#00CCCC");
        Model_Card model4 = new Model_Card("4", "Tổng Lợi Nhuận", "", "#FFCC00", "#FF6633");
        card1 = new Card(model1);
        card2 = new Card(model2);
        card3 = new Card(model3);
        card4 = new Card(model4);
        layerPane.add(card1);
        layerPane.add(card2);
        layerPane.add(card3);
        layerPane.add(card4);
    }

    public void loadDataCard() {
        ThongKe tk = QLTK.getCardThongKe();
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        String htmlText = String.format(
                "<html><table border='0' cellpadding='5'>"
                + "<tr><td>Đơn Thành Công</td><td>%d</td></tr>"
                + "<tr><td>Đơn Hủy</td><td>%d</td></tr>"
                + "</table></html>",
                tk.getDonThanhCong(),
                tk.getDonHuy()
        );
        Model_Card model1 = new Model_Card("1", String.format("Tổng Đơn Hàng   %d", tk.getDonHang()), htmlText, "#FF3333", "#FF6666");
        Model_Card model2 = new Model_Card("2", "Tổng Doanh Thu", numberFormat.format(tk.getDoanhThu()) + " VNĐ", "#9999FF", "#0066FF");
        Model_Card model3 = new Model_Card("3", "Tổng Chi Phí", numberFormat.format(tk.getChiPhi()) + " VNĐ", "#66FF66", "#00CCCC");
        Model_Card model4 = new Model_Card("4", "Tổng Lợi Nhuận", numberFormat.format(tk.getLoiNhuan()) + " VNĐ", "#FFCC00", "#FF6633");
        card1.setData(model1);
        card2.setData(model2);
        card3.setData(model3);
        card4.setData(model4);
    }

    public void swicthForm() {
        switch (index) {
            case 0 -> {
                cardLayout.show(cardPanel, "Chart");
            }

            case 1 -> {
                cardLayout.show(cardPanel, "Table");
            }
            default ->
                throw new AssertionError();
        }
    }

    private void loadDoanhThuData() {
        // Sample data for Doanh Thu table
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"Tháng 1", "10000000"},
                    {"Tháng 2", "15000000"}
                },
                new String[]{"Tháng", "Doanh Thu"}
        );
        doanhThuTable.setModel(model);
    }

    private void loadSanPhamData() {
        modelSP.setRowCount(0);
        listSP = QLTK.getThongKeSP_TBL();
        for (ChiTietSP sp : listSP) {
            modelSP.addRow(sp.getSPCT());
        }
        sanPhamTable.setModel(modelSP);
    }

    private void loadNhanVienData() {
        // Sample data for Nhân Viên table
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"NV001", "Nguyễn Văn A", "Kế toán"},
                    {"NV002", "Trần Thị B", "Nhân sự"}
                },
                new String[]{"Mã NV", "Tên NV", "Chức Vụ"}
        );
        nhanVienTable.setModel(model);
    }

    private void loadKhachHangData() {
        // Sample data for Khách Hàng table
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"KH001", "Lê Văn C", "0123456789"},
                    {"KH002", "Hoàng Thị D", "0987654321"}
                },
                new String[]{"Mã KH", "Tên KH", "Số Điện Thoại"}
        );
        khachHangTable.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        model_Card1 = new com.daipc.modelUI.Model_Card();

        setBackground(new java.awt.Color(234, 234, 234));
        setForeground(new java.awt.Color(255, 255, 255));
        setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.modelUI.Model_Card model_Card1;
    // End of variables declaration//GEN-END:variables
}
