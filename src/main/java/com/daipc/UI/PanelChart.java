package com.daipc.UI;

import com.daipc.chart.ModelChart;
import com.daipc.model.ThongKeDT;
import com.daipc.model.ThongKeKH;
import com.daipc.model.ThongKeNV;
import com.daipc.model.ThongKeSP;
import com.daipc.repo.QuanLiThongKe;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class PanelChart extends javax.swing.JPanel {

    private final QuanLiThongKe QLTK = new QuanLiThongKe();
    private List<ThongKeNV> listNV;
    private List<ThongKeDT> listDT;
    private List<ThongKeKH> listKH;
    private List<ThongKeSP> listSP;
    private CardLayout cardLayoutDT;
    private CardLayout cardLayoutNhom;

    private final DefaultComboBoxModel<String> modelNhom = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> modelTime = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> modelYear = new DefaultComboBoxModel<>();

    public PanelChart() {
        initComponents();
        setOpaque(false);
        init();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                reloadData();
            }
        });

    }

    public void reloadData() {
        for (ActionListener al : cboNhom.getActionListeners()) {
            cboNhom.removeActionListener(al);
        }
        for (ActionListener al : cboTime.getActionListeners()) {
            cboTime.removeActionListener(al);
        }
        for (ActionListener al : cboYear.getActionListeners()) {
            cboYear.removeActionListener(al);
        }
        init();
    }

    public void init() {
        modelNhom.removeAllElements();
        modelNhom.addElement("Doanh Thu");
        modelNhom.addElement("Sản Phẩm");
        modelNhom.addElement("Nhân Viên");
        modelNhom.addElement("Khách Hàng");
        cboNhom.setModel(modelNhom);
        cboNhom.setSelectedIndex(0);

        modelTime.removeAllElements();
        modelTime.addElement("Tháng");
        modelTime.addElement("Năm");
        cboTime.setModel(modelTime);
        cboTime.setSelectedIndex(0);

        modelYear.removeAllElements();
        for (Integer year : QLTK.getYears()) {
            modelYear.addElement(String.valueOf(year));
        }
        cboYear.setModel(modelYear);
        cboYear.setSelectedIndex(0);

        cboNhom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cboNhom.getSelectedIndex();
                System.out.println("Chỉ số được chọn của cboNhom: " + index);
                switch (index) {
                    case 0 -> {
                        cardLayoutNhom.show(panelLayoutNhom, "DT");
                        loadChartDT();
                    }
                    case 1 -> {
                        cardLayoutNhom.show(panelLayoutNhom, "SP");
                        thongKeSP();
                    }
                    case 2 -> {
                        cardLayoutNhom.show(panelLayoutNhom, "NV");
                        thongKeNV();
                    }
                    case 3 -> {
                        cardLayoutNhom.show(panelLayoutNhom, "KH");
                        thongKeKH();
                    }
                    default ->
                        throw new AssertionError();
                }
            }

        });
        cboTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cboTime.getSelectedIndex();

                switch (index) {
                    case 0 -> {
                        cardLayoutDT.show(panelLayout, "Months");
                        Object selectedItem = cboYear.getSelectedItem();
                        int year = Integer.parseInt(selectedItem.toString());
                        thongKeDTThang(year);
                    }
                    case 1 -> {
                        cardLayoutDT.show(panelLayout, "Years");
                        thongKeDTNam();
                    }
                    default ->
                        throw new AssertionError();
                }
            }
        });
        cboYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = cboYear.getSelectedItem();
                int year = Integer.parseInt(selectedItem.toString());
                thongKeDTThang(year);

            }
        });

        cardLayoutNhom = (CardLayout) panelLayoutNhom.getLayout();
        panelLayoutNhom.add(panelDoanhThu, "DT");
        panelLayoutNhom.add(panelKhachHang, "KH");
        panelLayoutNhom.add(panelNhanVien, "NV");
        panelLayoutNhom.add(panelSanPham, "SP");

        cardLayoutDT = (CardLayout) panelLayout.getLayout();
        panelLayout.add(panelThang, "Months");
        panelLayout.add(panelNam, "Years");

        loadChartDT();
    }

    public void thongKeNV() {
        chart.resetChart();
        listNV = QLTK.getThongKeNV();
        chart.addLegend("Số lượng hóa đơn", Color.RED);
        chart.addLegend("Số lượng khách hàng tiếp thị", Color.BLUE);
        for (ThongKeNV nv : listNV) {
            chart.addData(new ModelChart(nv.getHoTen(), new double[]{nv.getTongDonHang(), nv.getSoLuongKachHang()}));
        }
        chart.start();
    }

    public void thongKeKH() {
        chart.resetChart();
        txtSoLuongKH.setText("Top 10 khách hàng có giá trị hóa đơn cao nhất");
        listKH = QLTK.getThongKeKH();
        chart.addLegend("Tổng giá trị hóa đơn", Color.BLUE);
        for (ThongKeKH kh : listKH) {
            chart.addData(new ModelChart(kh.getHoTen(), new double[]{Double.parseDouble(kh.getTongGiaTriHD().toString())}));
        }
        chart.start();
    }

    public void thongKeSP() {
        chart.resetChart();
        txtNameChartSP.setText("Top 10 sản phẩm bán chạy nhất");
        listSP = QLTK.getThongKeSP();
        chart.addLegend("Số lượng bán", Color.BLUE);
        chart.addLegend("Số lượng tồn", Color.PINK);
        for (ThongKeSP sp : listSP) {
            chart.addData(new ModelChart(sp.getTenSP(), new double[]{sp.getSoLuongBan(), sp.getSoLuongTonKho()}));
        }
        chart.start();
    }

    public void thongKeDTNam() {
        chart.resetChart();
        listDT = QLTK.getThongKeDTYear();
        chart.addLegend("Doanh thu", Color.GREEN);
        chart.addLegend("Lợi nhuận", Color.BLUE);
        for (ThongKeDT dt : listDT) {
            chart.addData(new ModelChart(dt.getDate(), new double[]{Double.parseDouble(dt.getDoanhThu().toString()), Double.parseDouble(dt.getLoiNhuan().toString())}));
        }
        chart.start();
    }

    public void thongKeDTThang(int year) {
        chart.resetChart();
        listDT = QLTK.getThongKeDTMonth(year);
        chart.addLegend("Doanh thu", Color.GREEN);
        chart.addLegend("Lợi nhuận", Color.BLUE);
        for (ThongKeDT dt : listDT) {
            chart.addData(new ModelChart(dt.getDate(), new double[]{Double.parseDouble(dt.getDoanhThu().toString()), Double.parseDouble(dt.getLoiNhuan().toString())}));
        }
        chart.start();
    }

    public void loadChartDT() {
        int index = cboTime.getSelectedIndex();
        CardLayout cardLayout = (CardLayout) panelLayout.getLayout();
        panelLayout.add(panelThang, "Months");
        panelLayout.add(panelNam, "Years");

        switch (index) {
            case 0 -> {
                cardLayout.show(panelLayout, "Months");
                Object selectedItem = cboYear.getSelectedItem();
                int year = Integer.parseInt(selectedItem.toString());
                thongKeDTThang(year);
            }
            case 1 -> {
                cardLayout.show(panelLayout, "Years");
                thongKeDTNam();
            }
            default ->
                throw new AssertionError();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        chart = new com.daipc.chart.Chart();
        cboNhom = new javax.swing.JComboBox<>();
        panelLayoutNhom = new javax.swing.JPanel();
        panelDoanhThu = new javax.swing.JPanel();
        cboTime = new javax.swing.JComboBox<>();
        panelLayout = new javax.swing.JPanel();
        panelThang = new javax.swing.JPanel();
        cboYear = new javax.swing.JComboBox<>();
        panelNam = new javax.swing.JPanel();
        panelKhachHang = new javax.swing.JPanel();
        txtSoLuongKH = new javax.swing.JLabel();
        panelNhanVien = new javax.swing.JPanel();
        panelSanPham = new javax.swing.JPanel();
        txtNameChartSP = new javax.swing.JLabel();

        panelLayoutNhom.setBackground(new java.awt.Color(255, 255, 255));
        panelLayoutNhom.setLayout(new java.awt.CardLayout());

        panelDoanhThu.setBackground(new java.awt.Color(255, 255, 255));

        panelLayout.setBackground(new java.awt.Color(255, 255, 255));
        panelLayout.setLayout(new java.awt.CardLayout());

        panelThang.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelThangLayout = new javax.swing.GroupLayout(panelThang);
        panelThang.setLayout(panelThangLayout);
        panelThangLayout.setHorizontalGroup(
            panelThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(685, Short.MAX_VALUE))
        );
        panelThangLayout.setVerticalGroup(
            panelThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThangLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelLayout.add(panelThang, "card3");

        panelNam.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelNamLayout = new javax.swing.GroupLayout(panelNam);
        panelNam.setLayout(panelNamLayout);
        panelNamLayout.setHorizontalGroup(
            panelNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 841, Short.MAX_VALUE)
        );
        panelNamLayout.setVerticalGroup(
            panelNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelLayout.add(panelNam, "card4");

        javax.swing.GroupLayout panelDoanhThuLayout = new javax.swing.GroupLayout(panelDoanhThu);
        panelDoanhThu.setLayout(panelDoanhThuLayout);
        panelDoanhThuLayout.setHorizontalGroup(
            panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoanhThuLayout.createSequentialGroup()
                .addComponent(cboTime, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(panelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDoanhThuLayout.setVerticalGroup(
            panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoanhThuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboTime)
                    .addComponent(panelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelLayoutNhom.add(panelDoanhThu, "card2");

        panelKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        txtSoLuongKH.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtSoLuongKH.setForeground(new java.awt.Color(51, 51, 51));
        txtSoLuongKH.setText("Tổng Số Lượng Khách Hàng            200");

        javax.swing.GroupLayout panelKhachHangLayout = new javax.swing.GroupLayout(panelKhachHang);
        panelKhachHang.setLayout(panelKhachHangLayout);
        panelKhachHangLayout.setHorizontalGroup(
            panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKhachHangLayout.createSequentialGroup()
                .addComponent(txtSoLuongKH)
                .addGap(0, 774, Short.MAX_VALUE))
        );
        panelKhachHangLayout.setVerticalGroup(
            panelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSoLuongKH, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        panelLayoutNhom.add(panelKhachHang, "card3");

        panelNhanVien.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelNhanVienLayout = new javax.swing.GroupLayout(panelNhanVien);
        panelNhanVien.setLayout(panelNhanVienLayout);
        panelNhanVienLayout.setHorizontalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1041, Short.MAX_VALUE)
        );
        panelNhanVienLayout.setVerticalGroup(
            panelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelLayoutNhom.add(panelNhanVien, "card3");

        panelSanPham.setBackground(new java.awt.Color(255, 255, 255));

        txtNameChartSP.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtNameChartSP.setForeground(new java.awt.Color(51, 51, 51));
        txtNameChartSP.setText("Tổng Số Lượng Khách Hàng            200");

        javax.swing.GroupLayout panelSanPhamLayout = new javax.swing.GroupLayout(panelSanPham);
        panelSanPham.setLayout(panelSanPhamLayout);
        panelSanPhamLayout.setHorizontalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSanPhamLayout.createSequentialGroup()
                .addComponent(txtNameChartSP)
                .addGap(0, 774, Short.MAX_VALUE))
        );
        panelSanPhamLayout.setVerticalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNameChartSP, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        panelLayoutNhom.add(panelSanPham, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(panelLayoutNhom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(cboNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboNhom)
                    .addComponent(panelLayoutNhom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboNhom;
    private javax.swing.JComboBox<String> cboTime;
    private javax.swing.JComboBox<String> cboTime1;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JComboBox<String> cboYear1;
    private com.daipc.chart.Chart chart;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelDoanhThu1;
    private javax.swing.JPanel panelKhachHang;
    private javax.swing.JPanel panelLayout;
    private javax.swing.JPanel panelLayout1;
    private javax.swing.JPanel panelLayoutNhom;
    private javax.swing.JPanel panelNam;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelSanPham;
    private javax.swing.JPanel panelThang;
    private javax.swing.JPanel panelThang1;
    private javax.swing.JLabel txtNameChartSP;
    private javax.swing.JLabel txtSoLuongKH;
    // End of variables declaration//GEN-END:variables
}
