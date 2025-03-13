package com.daipc.form;

import com.daipc.ScrollBar.ScrollbarCustom;
import com.daipc.customTable.HeaderRenderer;
import com.daipc.customTable.PanelActionCellEditor;
import com.daipc.customTable.PanelActionCellRender;
import com.daipc.customTable.PanelBtnDeleteCellEditor;
import com.daipc.customTable.PanelBtnDeleteCellRender;
import com.daipc.customTable.QtySpinnerCellEditor;
import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDonCho;
import com.daipc.repo.QuanLiBanHang;
import com.daipc.table.TableCustom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.daipc.customTable.TableEvent;
import com.daipc.enumm.TrangThaiCRUD;
import com.daipc.model.HoaDon;
import com.daipc.model.KhachHang;
import com.daipc.model.NhanVien;
import com.daipc.model.PhuongThucTT;
import com.daipc.model.Voucher;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Random;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Form_Sell extends javax.swing.JPanel {

    private final QuanLiBanHang QLBH = new QuanLiBanHang();

    private final List<HoaDonCho> listHDC = new ArrayList<>();
    private int selectedRowHDC = -1;

    private final List<GioHang> listGH = new ArrayList<>();
    private int selectedRowGH = -1;

    private final List<ChiTietSP> listSP = new ArrayList<>();
    private int selectedRowSP = -1;

    private final List<KhachHang> listKH = new ArrayList<>();
    private final int selectedRowKH = -1;

    private Voucher voucher = new Voucher();

    private final List<PhuongThucTT> listPTTT = new ArrayList<>();

    private final List<HoaDon> listHD = new ArrayList<>();

    private final DefaultTableModel modelHDC;
    private final DefaultTableModel modelGH;
    private final DefaultTableModel modelSP;

    private TableEvent eventHuyHD;
    private HeaderRenderer headerRenderer;
    private final boolean toggle = false;
    private int column = 0;
    private int sort = 0;
    private final NhanVien nv;

    public Form_Sell(NhanVien nv) {
        initComponents();
        TableCustom.apply(scrollHoaDonCho, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollGioHang, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollDanhSachSanPham, TableCustom.TableType.DEFAULT);

        modelHDC = (DefaultTableModel) tblHoaDonCho.getModel();
        modelGH = (DefaultTableModel) tblGioHang.getModel();
        modelSP = (DefaultTableModel) tblDanhSachSanPham.getModel();

        scrollMoTa.setVerticalScrollBar(new ScrollbarCustom());
        ScrollbarCustom sp = new ScrollbarCustom();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollMoTa.setHorizontalScrollBar(sp);

        this.nv = nv;
        initTableHD();
        initTableGH();
        loadDataHDC();
        loadDataSP();
        loadDataHTTT();
        filterGroupSP();
        if (selectedRowHDC >= 0) {
            showDetails();
        }
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                updateDisplay();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                updateDisplay();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                updateDisplay();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                initTableHD();
                initTableGH();
                loadDataHDC();
                loadDataSP();
                loadDataHTTT();
                if (selectedRowHDC >= 0) {
                    showDetails();
                }
            }

        });

        modelGH.addTableModelListener((TableModelEvent e) -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                Object cellValue = modelGH.getValueAt(row, column);
                try {
                    int oldVulue = 0;
                    int newValue = 0;
                    if (cellValue instanceof Number) {
                        newValue = ((Number) cellValue).intValue();
                    } else if (cellValue instanceof String) {
                        String strValue = ((String) cellValue).trim();
                        if (!strValue.isEmpty() && strValue.matches("-?\\d+")) {
                            newValue = Integer.parseInt(strValue);
                        } else {
                            System.err.println("Giá trị ô không phải số: " + strValue);
                            return;
                        }
                    } else {
                        System.err.println("Giá trị ô không hợp lệ: " + cellValue);
                        return;
                    }
                    oldVulue = listGH.get(row).getSoLuong();
                    ChiTietSP prod = new ChiTietSP();
                    for (ChiTietSP ctsp : listSP) {
                        if (ctsp.getMaCTSP().equals(listGH.get(row).getMaCTSP())) {
                            prod = ctsp;
                            break;
                        }
                    }
                    QLBH.update("update sanphamchitiet set SoLuong = ? where id = ?", prod.getSoLuong() - (newValue - oldVulue), prod.getId());
                    QLBH.update("update HoaDonCT set soluong = ? where id = ?", newValue, listGH.get(row).getId());
                    loadDataGH();
                    loadDataSP();
                } catch (NumberFormatException ex) {
                    System.err.println("Lỗi khi chuyển đổi giá trị ô thành số: " + cellValue);
                } catch (Exception ex) {
                    System.err.println("Lỗi xảy ra: " + ex.getMessage());
                }
            }
        });

    }

    public void filterGroupSP() {
        TableCellRenderer defaultRenderer = tblDanhSachSanPham.getTableHeader().getDefaultRenderer();
        headerRenderer = new HeaderRenderer(defaultRenderer, tblDanhSachSanPham.getColumnCount());
        JTableHeader headerTblSP = tblDanhSachSanPham.getTableHeader();
        headerTblSP.setDefaultRenderer(headerRenderer);

        headerTblSP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TableColumnModel columnModel = tblDanhSachSanPham.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                column = tblDanhSachSanPham.convertColumnIndexToModel(viewColumn);
                if (column != -1) {
                    System.out.println(column);
                    for (int i = 0; i < columnModel.getColumnCount(); i++) {
                        if (i != column) {
                            headerRenderer.setToggleState(i, 0);
                        }
                    }
                    
                    
                    int currentState = headerRenderer.getToggleState(column);
                    switch (currentState) {
                        case 0:
                            headerRenderer.setToggleState(column, 1); // Mặc định -> tăng dần
                            sort = 1;
                            loadDataSP();
                            break;
                        case 1:
                            headerRenderer.setToggleState(column, -1); // Tăng dần -> giảm dần
                            sort = -1;
                            loadDataSP();
                            break;
                        default:
                            headerRenderer.setToggleState(column, 0); // Giảm dần -> mặc định
                            sort = 10;
                            loadDataSP();
                            break;
                    }
                    headerTblSP.repaint();
                }
            }
        });
    }

    public void loadDataHDC() {
        modelHDC.setRowCount(0);
        listHDC.clear();
        listHDC.addAll(QLBH.selectAllHDC());
        for (HoaDonCho hd : listHDC) {
            modelHDC.addRow(hd.getHDC());
        }
        if (selectedRowHDC >= listHDC.size() || selectedRowHDC == -1) {
            selectedRowHDC = listHDC.size() - 1;
            if (selectedRowHDC >= 0) {
                tblHoaDonCho.setRowSelectionInterval(selectedRowHDC, selectedRowHDC);
            }
        } else if (selectedRowHDC >= 0) {
            tblHoaDonCho.setRowSelectionInterval(selectedRowHDC, selectedRowHDC);
        } else {
            clearFormHD();
            btnKiemTra.setEnabled(false);
        }
        if (listHDC.isEmpty()) {
            clearFormHD();
            btnKiemTra.setEnabled(false);
            btnCheckVoucher.setEnabled(false);
        } else {
            btnKiemTra.setEnabled(true);
            btnCheckVoucher.setEnabled(true);
        }
    }

    public void loadDataGH() {
        selectedRowHDC = tblHoaDonCho.getSelectedRow();
        listGH.clear();
        modelGH.setRowCount(0);
        if (selectedRowHDC >= 0) {
            int id = listHDC.get(selectedRowHDC).getId();
            listGH.addAll(
                    QLBH.selectAllGH(
                            """
                                        select hdct.id, spct.MaSPCT, spct.TenSPCT, spct.GiaBan, hdct.SoLuong, spct.GiaBan * hdct.SoLuong, hdct.TrangThai from HoaDonCT hdct
                                                                    join SanPhamChiTiet spct on hdct.IDCTSP = spct.id
                                                                    where hdct.IDHoaDon = ? and hdct.TrangThai = ?
                                    """,
                            id, 1));
        }
        for (GioHang gh : listGH) {
            modelGH.addRow(gh.getGioHang());
        }
        selectedRowGH = listGH.size() - 1;
    }

    public void loadDataSP() {
        modelSP.setRowCount(0);
        listSP.clear();
        listSP.addAll(QLBH.selectAllSPCT(column, sort));
        for (ChiTietSP sp : listSP) {
            modelSP.addRow(sp.getSPCT());
        }
        selectedRowSP = listSP.size() - 1;
        if (selectedRowSP >= 0) {
            tblDanhSachSanPham.setRowSelectionInterval(selectedRowSP, selectedRowSP);
        }

    }

    public void loadDataHTTT() {
        listPTTT.clear();
        cboHinhThucTT.removeAllItems();
        listPTTT.addAll(QLBH.getAllPhuongThucTT());
        for (PhuongThucTT tt : listPTTT) {
            cboHinhThucTT.addItem(tt.getTenPTTT());
        }
        cboHinhThucTT.setSelectedIndex(-1);
    }

    public void initTableHD() {
        TableEvent event = new TableEvent() {
            @Override
            public void onUpdate(int row) {
                Popup_UpdateHoaDon updateHD = new Popup_UpdateHoaDon(null, true);
                updateHD.setAlwaysOnTop(true);
                updateHD.setLocationRelativeTo(null);
                updateHD.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        selectedRowHDC = row;
                        loadDataHDC();
                        showDetails();

                    }
                });
                updateHD.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                String sqlSofDeleteHD = """
                            update hoadon set trangthai = 2 where id = ?
                        """;
                if (tblHoaDonCho.isEditing()) {
                    tblHoaDonCho.getCellEditor().stopCellEditing();
                }
                for (int id = 0; id < listGH.size(); id++) {
                    deleteAndUpdate(id);
                }

                int id = listHDC.get(row).getId();
                if (QLBH.update(sqlSofDeleteHD, id) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Xóa Hóa Đơn Chờ Thành Công !!!");
                    loadDataHDC();
                    selectedRowHDC = tblHoaDonCho.getSelectedRow();
                    loadDataGH();
                    loadDataSP();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa Hóa Đơn Chờ Thất Bại !!!");
                }
                if (selectedRowHDC > 0) {
                    showDetails();
                }
            }

            @Override
            public void quantity(int qty) {

            }

        };
        this.eventHuyHD = event;
        tblHoaDonCho.getColumnModel().getColumn(5).setCellRenderer(new PanelActionCellRender(tblHoaDonCho));
        tblHoaDonCho.getColumnModel().getColumn(5).setCellEditor(new PanelActionCellEditor(event));
    }

    public void initTableGH() {
        TableEvent event = new TableEvent() {
            @Override
            public void onUpdate(int row) {
            }

            @Override
            public void onDelete(int row) {
                if (tblGioHang.isEditing()) {
                    tblGioHang.getCellEditor().stopCellEditing();
                }
                deleteAndUpdate(row);
                loadDataGH();
                loadDataSP();
                loadDataHDC();
                if (selectedRowHDC > 0) {
                    showDetails();
                }
            }

            @Override
            public void quantity(int qty) {

            }

        };
        tblGioHang.getColumnModel().getColumn(5).setCellRenderer(new PanelBtnDeleteCellRender(tblGioHang));
        tblGioHang.getColumnModel().getColumn(5).setCellEditor(new PanelBtnDeleteCellEditor(event));
        tblGioHang.getColumnModel().getColumn(3).setCellEditor(new QtySpinnerCellEditor(event, tblGioHang));
    }

    public void deleteAndUpdate(int row) {
        String sqlSofDelete = """
                    update hoadonct set trangthai = 0 where id = ?
                """;
        String sqlUpdate = """
                    update sanphamchitiet set SoLuong = ? where id = ?
                """;
        int idOfListSP = -1;
        System.out.println(listSP.size());
        for (int i = 0; i < listSP.size(); i++) {
            if (listSP.get(i).getMaCTSP().equals(listGH.get(row).getMaCTSP())) {
                idOfListSP = i;
            }
        }
        QLBH.update(sqlUpdate, listSP.get(idOfListSP).getSoLuong() + listGH.get(row).getSoLuong(),
                listSP.get(idOfListSP).getId());
        QLBH.update(sqlSofDelete, listGH.get(row).getId());
    }

    public String autoTangMaHD() {
        listHD.clear();
        listHD.addAll(QLBH.selectAllHD());
        String maHDCuoiCung = listHD.get(listHD.size() - 1).getMaHD();
        return String.format("HD%03d", Integer.parseInt(maHDCuoiCung.substring(2)) + 1);
    }

    public void clearFormHD() {
        txtTenKH.setText("Khách Bán Lẻ");
        txtSDT.setText("");
        txtMaHD.setText("");
        txtThanhToan.setText("0.0");
        txtTongTien.setText("0.0");
        txtTienKhachDua.setText("");
        txtTienThua.setText("0.0");
        txtGhiChu.setText("");
        txtVoucher.setText("");
        txtMoTaVoucher.setText("");
        cboHinhThucTT.setSelectedIndex(-1);
    }

    public void showDetails() {
        HoaDonCho hdc = listHDC.get(selectedRowHDC);
        txtTenKH.setText(hdc.getTenKhachHang());
        txtSDT.setText(hdc.getSDT());
        txtMaHD.setText(hdc.getMaHD());
        txtTongTien.setText(String.valueOf(hdc.getTong()));
        txtThanhToan.setText(String.valueOf(hdc.getThanhToan()));
        cboHinhThucTT.setSelectedItem(hdc.getHinhThucTT());
        txtGhiChu.setText(hdc.getGhiChu());
        if (hdc.getVoucher() != null) {
            txtVoucher.setText(hdc.getVoucher());
            txtMoTaVoucher.setText(hdc.getThongTinVoucher());
        } else {
            txtVoucher.setText(" ");
            txtMoTaVoucher.setText(" ");
        }
        tblHoaDonCho.setRowSelectionInterval(selectedRowHDC, selectedRowHDC);
    }

    public int checKhachHang() {
        int i = 0;
        for (KhachHang kh : listKH) {
            if (txtSDT.getText().equals(kh.getSoDT())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void updateDisplay() {
        try {
            BigDecimal tienKhach = BigDecimal.valueOf(Double.parseDouble(txtTienKhachDua.getText()));
            BigDecimal thanhToan = BigDecimal.valueOf(Double.parseDouble(txtThanhToan.getText()));
            SwingUtilities.invokeLater(() -> {
                BigDecimal tienThua = tienKhach.subtract(thanhToan);
                if (tienThua.equals(BigDecimal.valueOf(0))) {
                    txtTienThua.setText("0");
                } else {
                    txtTienThua.setText(String.valueOf(tienThua));

                }
            });
        } catch (Exception e) {

        }
    }

    private boolean validateForm() {
        if (cboHinhThucTT.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn phương thức thanh toán");
            return false;
        }
        return true;
    }

    private int checkGioHang(ChiTietSP sp) {
        for (int i = 0; i < listGH.size(); i++) {
            if (listGH.get(i).getMaCTSP().equals(sp.getMaCTSP())) {
                return i;
            }
        }
        return -1;
    }

    // @SuppressWarnings("unchecked");
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.daipc.swing.PanelBorder();
        scrollHoaDonCho = new javax.swing.JScrollPane();
        tblHoaDonCho = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        panelBorder8 = new com.daipc.swing.PanelBorder();
        scrollDanhSachSanPham = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        panelBorder6 = new com.daipc.swing.PanelBorder();
        scrollGioHang = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        panelBorder7 = new com.daipc.swing.PanelBorder();
        jLabel12 = new javax.swing.JLabel();
        txtSDT = new com.daipc.textfield.TextField();
        jLabel13 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        btnTaoHD = new com.daipc.swing.Button();
        btnKiemTra = new com.daipc.swing.Button();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnHuy = new com.daipc.swing.Button();
        btnLamMoi = new com.daipc.swing.Button();
        scrollMoTa = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThanhToan = new com.daipc.swing.Button();
        txtMaHD = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JLabel();
        cboHinhThucTT = new com.daipc.combobox.Combobox();
        txtTienKhachDua = new com.daipc.textfield.TextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtVoucher = new com.daipc.textfield.TextField();
        txtMoTaVoucher = new javax.swing.JLabel();
        btnCheckVoucher = new com.daipc.swing.Button();

        setBackground(new java.awt.Color(234, 234, 234));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDonCho.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblHoaDonCho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Tên Khách Hàng", "Người Tạo", "Ngày Tạo", "Tổng", "Thao Tác"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonCho.setRowHeight(45);
        tblHoaDonCho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChoMouseClicked(evt);
            }
        });
        scrollHoaDonCho.setViewportView(tblHoaDonCho);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setText("Hóa Đơn Chờ");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollHoaDonCho, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder8.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSachSanPham.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Màu Sắc", "Kích Thước", "Chất Liệu", "Độ Dày", "Tồn Kho"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamMouseClicked(evt);
            }
        });
        scrollDanhSachSanPham.setViewportView(tblDanhSachSanPham);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setText("Sản Phẩm");

        javax.swing.GroupLayout panelBorder8Layout = new javax.swing.GroupLayout(panelBorder8);
        panelBorder8.setLayout(panelBorder8Layout);
        panelBorder8Layout.setHorizontalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollDanhSachSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(panelBorder8Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBorder8Layout.setVerticalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollDanhSachSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelBorder6.setBackground(new java.awt.Color(255, 255, 255));

        tblGioHang.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền", "Thao Tác"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setRowHeight(45);
        scrollGioHang.setViewportView(tblGioHang);

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setText("Giỏ Hàng");

        javax.swing.GroupLayout panelBorder6Layout = new javax.swing.GroupLayout(panelBorder6);
        panelBorder6.setLayout(panelBorder6Layout);
        panelBorder6Layout.setHorizontalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(panelBorder6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBorder6Layout.setVerticalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setText("Khách Hàng");

        txtSDT.setLabelText("SĐT Khách Hàng");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setText("Tên Khách Hàng");

        txtTenKH.setText("Khách Bán Lẻ ");

        btnTaoHD.setText("Tạo");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnKiemTra.setText("Kiểm Tra");
        btnKiemTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTraActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setText("Mã Hóa Đơn");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setText("Tổng Tiền");

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setText("Thanh Toán");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setText("Tiền Thừa Trả KHách");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel21.setText("Ghi Chú");

        btnHuy.setText("Hủy Hóa Đơn");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm Mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        scrollMoTa.setViewportView(txtGhiChu);

        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        txtMaHD.setText("...");

        txtTongTien.setText("0");

        txtThanhToan.setText("0");

        txtTienThua.setText("0");

        cboHinhThucTT.setLabeText("Hình Thức Thanh Toán");

        txtTienKhachDua.setLabelText("Tiền Khách Đưa");

        jLabel3.setText("VNĐ");

        jLabel6.setText("VNĐ");

        jLabel8.setText("VNĐ");

        jLabel18.setText("VNĐ");

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setText("Đơn Hàng");

        txtVoucher.setLabelText("Voucher");

        txtMoTaVoucher.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtMoTaVoucher.setForeground(new java.awt.Color(255, 51, 51));

        btnCheckVoucher.setText("Kiểm Tra");
        btnCheckVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckVoucherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder7Layout = new javax.swing.GroupLayout(panelBorder7);
        panelBorder7.setLayout(panelBorder7Layout);
        panelBorder7Layout.setHorizontalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCheckVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMoTaVoucher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollMoTa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorder7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel21)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel18))
                                        .addGroup(panelBorder7Layout.createSequentialGroup()
                                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnKiemTra, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cboHinhThucTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorder7Layout.createSequentialGroup()
                                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBorder7Layout.createSequentialGroup()
                                        .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelBorder7Layout.createSequentialGroup()
                                        .addComponent(txtTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8))
                                    .addGroup(panelBorder7Layout.createSequentialGroup()
                                        .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3))
                                    .addGroup(panelBorder7Layout.createSequentialGroup()
                                        .addComponent(txtThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)))))
                        .addGap(20, 20, 20))))
        );
        panelBorder7Layout.setVerticalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addGap(20, 20, 20)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKiemTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtMaHD)
                            .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTongTien)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCheckVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMoTaVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtThanhToan)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTienThua)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(cboHinhThucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckVoucherActionPerformed
        voucher = QLBH.getVoucher(txtVoucher.getText().trim());
        if (voucher.getMaVoucher() == null) {
            txtMoTaVoucher.setText("Voucher không tồn tại hoặc đã hết hạn");
        } else {
            txtMoTaVoucher.setText(voucher.getMoTa());
            QLBH.update("update HoaDon set IDVoucher = ? where id = ?", voucher.getId(), listHDC.get(tblHoaDonCho.getSelectedRow()).getId());
            loadDataHDC();
            showDetails();
        }
    }//GEN-LAST:event_btnCheckVoucherActionPerformed

    private void tblDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblDanhSachSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            selectedRowHDC = tblHoaDonCho.getSelectedRow();
            selectedRowSP = tblDanhSachSanPham.getSelectedRow();
            if (selectedRowHDC >= 0) {
                HoaDonCho hdc = listHDC.get(selectedRowHDC);
                ChiTietSP sp = listSP.get(selectedRowSP);
                int soLuong = 0;
                int checkSoLuong = -999;
                String inputValue = JOptionPane.showInputDialog(null, "Nhập số lượng:", "",
                        JOptionPane.QUESTION_MESSAGE);
                if (inputValue != null) {
                    try {
                        soLuong = Integer.parseInt(inputValue);
                        if (soLuong < 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (soLuong > sp.getSoLuong()) {
                            checkSoLuong = JOptionPane.showConfirmDialog(null,
                                    "Số lượng vượt quá tồn kho\nBạn có muốn nhập số lượng tối đa ?", "",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Lỗi định dạng. Nhấp số nguyên lớn hơn 0.", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                String sqlInsert = """
                         insert into HoaDonCT (IDHoaDon, IDCTSP, DonGia, TrangThai, SoLuong)
                         Values (?, ?, ?, ?, ?)
                        """;
                String sqlUpdate = """
                            update sanphamchitiet set SoLuong = ? where id = ?
                        """;
                if (checkSoLuong == JOptionPane.YES_OPTION) {
                    soLuong = sp.getSoLuong();
                }
                if (soLuong > 0 && soLuong <= sp.getSoLuong()) {
                    int idGioHang = checkGioHang(sp);
                    if (idGioHang >= 0) {
                        QLBH.update("update HoaDonCT set soluong = soluong + ? where id = ?", soLuong,
                                listGH.get(idGioHang).getId());
                    } else {
                        QLBH.update(sqlInsert, hdc.getId(), sp.getId(), sp.getGiaBan(), 1, soLuong);
                    }
                    QLBH.update(sqlUpdate, sp.getSoLuong() - soLuong, sp.getId());
                    loadDataHDC();
                    loadDataGH();
                    loadDataSP();
                    showDetails();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn chờ !!!");
            }

        }
    }// GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTaoHDActionPerformed
        clearFormHD();
        txtMaHD.setText(autoTangMaHD());
        String sqlInsert = """
                 INSERT INTO HoaDon
                 (MaHD, IDKhachHang, IDNhanVien, IDVoucher, TongGiaTriHoaDon, ThanhToan, NgayTao, TrangThai)
                 VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        if (QLBH.update(sqlInsert, txtMaHD.getText(), 1, nv.getId(), null, 0, 0, LocalDate.now(),
                0) == TrangThaiCRUD.ThanhCong) {
            JOptionPane.showMessageDialog(null, "Tạo Hóa Đơn Thành Công !!!");
        }
        loadDataHDC();
        selectedRowHDC = listHDC.size() - 1;
        showDetails();
    }// GEN-LAST:event_btnTaoHDActionPerformed

    private void tblHoaDonChoMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblHoaDonChoMouseClicked
        selectedRowHDC = tblHoaDonCho.getSelectedRow();
        loadDataGH();
        showDetails();
    }// GEN-LAST:event_tblHoaDonChoMouseClicked

    private void btnKiemTraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKiemTraActionPerformed
        String sqlUpdate = """
                 Update HoaDon
                 set IDKhachHang = ? where id = ?
                """;
        selectedRowHDC = tblHoaDonCho.getSelectedRow();
        int tempSelectedRow = tblHoaDonCho.getSelectedRow();
        listKH.addAll(QLBH.getAllKH());
        int id = listHDC.get(selectedRowHDC).getId();
        if (checKhachHang() == -1) {
            if (JOptionPane.showConfirmDialog(null, "Khách hàng không tồn tại !\nTạo khách hàng mới ?", "",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                Popup_KhachHang popup_KhachHang = new Popup_KhachHang(null, true, txtSDT.getText(), id);
                popup_KhachHang.setLocationRelativeTo(null);
                popup_KhachHang.setAlwaysOnTop(true);
                popup_KhachHang.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        loadDataHDC();
                        selectedRowHDC = tempSelectedRow;
                        showDetails();
                    }
                });
                popup_KhachHang.setVisible(true);
            }
        } else {
            if (JOptionPane.showConfirmDialog(null, "Khách hàng đã tồn tại\nDùng thông tin khách hàng này ?", "",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                KhachHang kh = listKH.get(checKhachHang());
                if (QLBH.update(sqlUpdate, kh.getId(), id) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Cập Nhật Khách Hàng Thành Công !!!");
                }
            }
        }
        loadDataHDC();

    }// GEN-LAST:event_btnKiemTraActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
        clearFormHD();
    }// GEN-LAST:event_btnLamMoiActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
        eventHuyHD.onDelete(tblHoaDonCho.getSelectedRow());
    }// GEN-LAST:event_btnHuyActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThanhToanActionPerformed
        String sqlUpdate = """
                            UPDATE HoaDon 
                            SET 
                                IDVoucher = CASE WHEN ? > 0 THEN ? ELSE IDVoucher END, 
                                IDPhuongThucTT = ?, 
                                trangThai = ?, 
                                GhiChu = ?, 
                                TongGiaTriHoaDon = ?,
                                idnhanvien = ?
                            WHERE ID = ?;
                           """;
        if (validateForm()) {
            if (cboHinhThucTT.getSelectedIndex() == 0) {
                if (QLBH.update(sqlUpdate, voucher.getId(), voucher.getId(),
                        listPTTT.get(cboHinhThucTT.getSelectedIndex()).getId(), 1, txtGhiChu.getText(),
                        BigDecimal.valueOf(Double.parseDouble(txtThanhToan.getText())),
                        nv.getId(),
                        listHDC.get(tblHoaDonCho.getSelectedRow()).getId()) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Thanh Toán Thành Công !");
                    loadDataHDC();
                    loadDataGH();
                } else {
                    JOptionPane.showMessageDialog(null, "Thanh Toán Thất Bại !");
                }
            } else {
                if (QLBH.update(sqlUpdate, voucher.getId(), voucher.getId(),
                        listPTTT.get(cboHinhThucTT.getSelectedIndex()).getId(), 1, txtGhiChu.getText(),
                        BigDecimal.valueOf(Double.parseDouble(txtThanhToan.getText())),
                        nv.getId(),
                        listHDC.get(tblHoaDonCho.getSelectedRow()).getId()) == TrangThaiCRUD.ThanhCong) {
                    JOptionPane.showMessageDialog(null, "Thanh Toán Thành Công !");
                    loadDataHDC();
                } else {
                    JOptionPane.showMessageDialog(null, "Thanh Toán Thất Bại !");
                }
            }
        }

    }// GEN-LAST:event_btnThanhToanActionPerformed

    private void btnTaoHD1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTaoHD1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnTaoHD1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.Button btnCheckVoucher;
    private com.daipc.swing.Button btnHuy;
    private com.daipc.swing.Button btnKiemTra;
    private com.daipc.swing.Button btnLamMoi;
    private com.daipc.swing.Button btnTaoHD;
    private com.daipc.swing.Button btnThanhToan;
    private com.daipc.combobox.Combobox cboHinhThucTT;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.daipc.swing.PanelBorder panelBorder1;
    private com.daipc.swing.PanelBorder panelBorder6;
    private com.daipc.swing.PanelBorder panelBorder7;
    private com.daipc.swing.PanelBorder panelBorder8;
    private javax.swing.JScrollPane scrollDanhSachSanPham;
    private javax.swing.JScrollPane scrollGioHang;
    private javax.swing.JScrollPane scrollHoaDonCho;
    private javax.swing.JScrollPane scrollMoTa;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDonCho;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JLabel txtMaHD;
    private javax.swing.JLabel txtMoTaVoucher;
    private com.daipc.textfield.TextField txtSDT;
    private javax.swing.JLabel txtTenKH;
    private javax.swing.JLabel txtThanhToan;
    private com.daipc.textfield.TextField txtTienKhachDua;
    private javax.swing.JLabel txtTienThua;
    private javax.swing.JLabel txtTongTien;
    private com.daipc.textfield.TextField txtVoucher;
    // End of variables declaration//GEN-END:variables
}
