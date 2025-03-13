/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.daipc.form;

import com.daipc.model.NhanVien;
import com.daipc.repo.QuanLiTaiKhoan;
import com.daipc.table.TableCustom;
import com.formdev.flatlaf.FlatClientProperties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaiPc
 */
public class Form_Staffs extends javax.swing.JPanel {

    QuanLiTaiKhoan qltk = new QuanLiTaiKhoan();
    private ArrayList<NhanVien> dataNV;
    private String keyword = null;
    private int i = -1;
    private String getMaNVCuoi = null;
    private String tkCu = null;
    private String cccdCu = null;

    public Form_Staffs() {
        initComponents();
        this.init();
        ArrayList<NhanVien> listNV = qltk.getAccount();
        this.fillTable(listNV);
        this.search();
        this.showData(tbl_danglam, 0);
        tbl_danglam.setRowSelectionInterval(0, 0);
    }

    public void init() {
//        panel_tk.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        txt_timkiem2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm theo Mã, tên, cccd, tài khoản");

    }

    public void fillTable(ArrayList<NhanVien> listNV) {
        DefaultTableModel modelDangLam = (DefaultTableModel) tbl_danglam.getModel();
        DefaultTableModel modelDaNghi = (DefaultTableModel) tbl_danghi.getModel();

        modelDangLam.setRowCount(0);
        modelDaNghi.setRowCount(0);

        int sttDangLam = 1;
        int sttDaNghi = 1;

        for (NhanVien nv : listNV) {
            if (nv.isTrangThai()) {
                modelDangLam.addRow(new Object[]{
                    sttDangLam++, nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getChucVu().equals("ql") ? "Quản lý" : "Nhân viên"
                });
            } else {
                modelDaNghi.addRow(new Object[]{
                    sttDaNghi++, nv.getMaNhanVien(), nv.getHoTen(), nv.getCccd(), nv.getChucVu().equals("ql") ? "Quản lý" : "Nhân viên"
                });
            }
        }

        i = listNV.size();
    }

    public void search() {
        txt_timkiem2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }

            private void performSearch() {
                keyword = txt_timkiem2.getText().trim();
                ArrayList<NhanVien> result = qltk.searchNhanVien(keyword);
                fillTable(result);
            }
        });
    }

    public String handleMaMoi() {
        ArrayList<NhanVien> listNV = qltk.getAccount();
        NhanVien nv = listNV.get(listNV.size() - 1);
        getMaNVCuoi = nv.getMaNhanVien();
        System.out.println("Get manv cuoi: " + getMaNVCuoi);
        String numberPart = getMaNVCuoi.substring(2);
        int number = Integer.parseInt(numberPart);
        number++;
        String newMaNV = "NV" + String.format("%03d", number);
        return newMaNV;
    }

    public void showData(JTable table, int i) {
        dataNV = qltk.getDataNV(table.getValueAt(i, 1).toString());
        NhanVien nv = dataNV.get(0);

        tkCu = nv.getTaiKhoan();
        cccdCu = nv.getCccd();

        txt_MaNV.setText(nv.getMaNhanVien());
        txt_NgayTao.setText(nv.getNgayTao().toString());
        if (nv.getChucVu().equals("ql")) {
            rdo_ql.setSelected(true);
        } else {
            rdo_nv.setSelected(true);
        }
        if (nv.isTrangThai()) {
            rdo_danglam.setSelected(true);
        } else {
            rdo_danghi.setSelected(true);
        }
        txt_tk.setText(nv.getTaiKhoan());
        txt_mk.setText(nv.getMatKhau());
        txt_hoTen.setText(nv.getHoTen());
        txt_ngaySinh.setDate(nv.getNgaySinh());
        if (nv.isGioiTinh()) {
            rdo_nam.setSelected(true);
        } else {
            rdo_nu.setSelected(true);
        }
        txt_sdt.setText(nv.getSoDT());
        txt_cccd.setText(nv.getCccd());
        txt_diachi.setText(nv.getDiaChi());

        if (rdo_danglam.isSelected()) {
            btn_xoa.setText("Đã nghỉ");
        } else if (rdo_danghi.isSelected()) {
            btn_xoa.setText("Đang làm");
        }
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^0\\d{9}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public boolean isValidCCCDNumber(String cccd) {
        String pattern = "^\\d{12}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(cccd);
        return m.matches();
    }

    public boolean isEmpty() {
        if (txt_tk.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường Tài khoản không được trống!");
            return false;
        }
        if (txt_mk.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường Mật khẩu không được trống!");
            return false;
        }
        if (txt_hoTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường Họ tên không được trống!");
            return false;
        }
        if (txt_ngaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Trường Ngày sinh không được trống!");
            return false;
        }
        if (txt_sdt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường SĐT không được trống!");
            return false;
        } else {
            if (!isValidPhoneNumber(txt_sdt.getText().trim())) {
                JOptionPane.showMessageDialog(this, "SĐT không đúng định dạng");
                return false;
            }
        }
        if (txt_cccd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường CCCD không được trống!");
            return false;
        } else {
            if (!isValidCCCDNumber(txt_cccd.getText().trim())) {
                JOptionPane.showMessageDialog(this, "CCCD không đúng định dạng");
                return false;
            }
        }
        if (txt_diachi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trường Địa chỉ không được trống!");
            return false;
        }
        return true;
    }

    public boolean checkadd() {
        if (qltk.checkTrung("TaiKhoan", txt_tk.getText()) != null) {
            JOptionPane.showMessageDialog(this, "Trùng tên tài khoản!");
            return false;
        }
        if (qltk.checkTrung("CCCD", txt_cccd.getText()) != null) {
            JOptionPane.showMessageDialog(this, "Trùng số CCCD!");
            return false;
        }
        return true;
    }
    
    public boolean checkupdate() {
        if (!tkCu.equals(txt_tk.getText().trim())) {
            if (qltk.checkTrung("TaiKhoan", txt_tk.getText()) != null) {
                JOptionPane.showMessageDialog(this, "Trung tai khoan!");
                return false;
            }
        }
        if (!cccdCu.equals(txt_cccd.getText().trim())) {
            if (qltk.checkTrung("CCCD", txt_cccd.getText()) != null) {
                JOptionPane.showMessageDialog(this, "Trung so CCCD!");
                return false;
            }
        }
        System.out.println("TK cu: " + tkCu + " tk moi: " + txt_tk.getText().trim());
        System.out.println("cccd cu: " + cccdCu + " cccd moi: " + txt_cccd.getText().trim());
        return true;
    }

    public NhanVien readform() {
        String manv = handleMaMoi();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date ngaySinh = txt_ngaySinh.getDate();

        String cv;
        if (rdo_ql.isSelected()) {
            cv = "ql";
        } else {
            cv = "nv";
        }

        boolean gt;
        gt = rdo_nam.isSelected();

        boolean tt;
        tt = rdo_danglam.isSelected();

        return new NhanVien(manv,
                txt_hoTen.getText().trim(),
                txt_sdt.getText().trim(),
                txt_cccd.getText().trim(),
                txt_ngaySinh.getDate(),
                cv,
                gt,
                txt_diachi.getText().trim(),
                txt_tk.getText().trim(),
                txt_mk.getText().trim(),
                tt
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgr_cv = new javax.swing.ButtonGroup();
        bgr_gt = new javax.swing.ButtonGroup();
        bgr_tt = new javax.swing.ButtonGroup();
        panelBorder1 = new com.daipc.swing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        txt_timkiem2 = new com.daipc.searchbar.MyTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_danglam = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_danghi = new javax.swing.JTable();
        panelBorder2 = new com.daipc.swing.PanelBorder();
        panel_tk = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_NgayTao = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rdo_ql = new javax.swing.JRadioButton();
        rdo_nv = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        rdo_danghi = new javax.swing.JRadioButton();
        rdo_danglam = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txt_tk = new javax.swing.JTextField();
        txt_mk = new javax.swing.JTextField();
        button2 = new com.daipc.swing.Button();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panel_tt = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_hoTen = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdo_nu = new javax.swing.JRadioButton();
        rdo_nam = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_cccd = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_diachi = new javax.swing.JTextArea();
        txt_ngaySinh = new com.toedter.calendar.JDateChooser();
        btn_them = new com.daipc.swing.Button();
        btn_sua = new com.daipc.swing.Button();
        btn_xoa = new com.daipc.swing.Button();

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        tbl_danglam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Họ Tên", "CCCD", "Chức Vụ"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_danglam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_danglamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_danglam);

        jTabbedPane1.addTab("Đang làm", jScrollPane1);

        tbl_danghi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Họ Tên", "CCCD", "Chức Vụ"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_danghi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_danghiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_danghi);

        jTabbedPane1.addTab("Đã nghỉ", jScrollPane2);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txt_timkiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_timkiem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));

        panel_tk.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Mã NV:");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel2.setText("Tài khoản");

        jLabel5.setText("Ngày tạo:");

        txt_NgayTao.setEditable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Chức vụ:");

        bgr_cv.add(rdo_ql);
        rdo_ql.setSelected(true);
        rdo_ql.setText("Quản lý");

        bgr_cv.add(rdo_nv);
        rdo_nv.setText("Nhân viên");

        jLabel17.setText("Trạng thái:");

        bgr_tt.add(rdo_danghi);
        rdo_danghi.setText("Đã nghỉ");

        bgr_tt.add(rdo_danglam);
        rdo_danglam.setSelected(true);
        rdo_danglam.setText("Đang làm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(rdo_ql))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_danglam)))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdo_nv)
                    .addComponent(rdo_danghi))
                .addGap(0, 71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rdo_ql)
                    .addComponent(rdo_nv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rdo_danghi)
                    .addComponent(rdo_danglam)))
        );

        jLabel6.setText("Tài khoản:");

        button2.setText("Làm mới");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Mật khẩu:");

        javax.swing.GroupLayout panel_tkLayout = new javax.swing.GroupLayout(panel_tk);
        panel_tk.setLayout(panel_tkLayout);
        panel_tkLayout.setHorizontalGroup(
            panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tkLayout.createSequentialGroup()
                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tkLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_tkLayout.createSequentialGroup()
                                .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_NgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tkLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_tkLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_tkLayout.createSequentialGroup()
                                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tk)
                                    .addComponent(txt_mk))))))
                .addContainerGap())
        );
        panel_tkLayout.setVerticalGroup(
            panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_NgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_mk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setText("THÔNG TIN NHÂN VIÊN");

        panel_tt.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel9.setText("Thông tin");

        jLabel11.setText("Họ tên:");

        jLabel12.setText("Ngày sinh:");

        jLabel10.setText("Giới tính:");

        bgr_gt.add(rdo_nu);
        rdo_nu.setText("Nữ");

        bgr_gt.add(rdo_nam);
        rdo_nam.setSelected(true);
        rdo_nam.setText("Nam");

        jLabel13.setText("Số ĐT:");

        jLabel14.setText("Số CCCD:");

        txt_cccd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cccdActionPerformed(evt);
            }
        });

        jLabel15.setText("Địa chỉ:");

        txt_diachi.setColumns(20);
        txt_diachi.setRows(5);
        jScrollPane3.setViewportView(txt_diachi);

        javax.swing.GroupLayout panel_ttLayout = new javax.swing.GroupLayout(panel_tt);
        panel_tt.setLayout(panel_ttLayout);
        panel_ttLayout.setHorizontalGroup(
            panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ttLayout.createSequentialGroup()
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cccd)
                            .addComponent(jScrollPane3)))
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(rdo_nam)
                        .addGap(18, 18, 18)
                        .addComponent(rdo_nu)
                        .addGap(0, 211, Short.MAX_VALUE))
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_sdt)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_ttLayout.createSequentialGroup()
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)))
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );
        panel_ttLayout.setVerticalGroup(
            panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_ttLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txt_ngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_nam)
                    .addComponent(rdo_nu)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_cccd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_ttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_ttLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xoá");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(146, 146, 146))
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_tt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_tk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel4)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel4)
                .addGap(34, 34, 34)
                .addComponent(panel_tk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cccdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cccdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cccdActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        txt_MaNV.setText("");
        txt_NgayTao.setText("");
        rdo_ql.setSelected(true);
        rdo_danglam.setSelected(true);
        txt_tk.setText("");
        txt_mk.setText("");
        txt_hoTen.setText("");
        txt_ngaySinh.setDate(null);
        rdo_nu.setSelected(true);
        txt_sdt.setText("");
        txt_cccd.setText("");
        txt_diachi.setText("");
    }//GEN-LAST:event_button2ActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        if (this.isEmpty()) {
            if (this.checkadd()) {
                qltk.add(this.readform(), this.handleMaMoi());
                ArrayList<NhanVien> listNV = qltk.getAccount();
                this.fillTable(listNV);
                if (keyword != null) {
                    ArrayList<NhanVien> result = qltk.searchNhanVien(keyword);
                    fillTable(result);
                }
                int lastRowIndex = tbl_danglam.getRowCount() - 1;
//                int lastRowIndexNghi = tbl_danghi.getRowCount() - 1;
                this.showData(tbl_danglam, lastRowIndex);
                tbl_danglam.setRowSelectionInterval(lastRowIndex, lastRowIndex);
//                tbl_danghi.setRowSelectionInterval(lastRowIndexNghi, lastRowIndexNghi);
            }
        }
    }//GEN-LAST:event_btn_themActionPerformed

    private void tbl_danglamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_danglamMouseClicked
        i = tbl_danglam.getSelectedRow();
        this.showData(tbl_danglam, i);
    }//GEN-LAST:event_tbl_danglamMouseClicked

    private void tbl_danghiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_danghiMouseClicked
        i = tbl_danghi.getSelectedRow();
        this.showData(tbl_danghi, i);
    }//GEN-LAST:event_tbl_danghiMouseClicked

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        if (txt_MaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        } else {
            if (rdo_danglam.isSelected()) {
                int check = new QuanLiTaiKhoan().updateTrangThai(txt_MaNV.getText(), false);
                if (check != 0) {
                    ArrayList<NhanVien> listNV = qltk.getAccount();
                    this.fillTable(listNV);
                    if (keyword != null) {
                        ArrayList<NhanVien> result = qltk.searchNhanVien(keyword);
                        fillTable(result);
                    }
                } else {
                }
            } else {
                if (qltk.updateTrangThai(txt_MaNV.getText(), true) != 0) {
                    ArrayList<NhanVien> listNV = qltk.getAccount();
                    this.fillTable(listNV);
                    if (keyword != null) {
                        ArrayList<NhanVien> result = qltk.searchNhanVien(keyword);
                        fillTable(result);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Thay đổi trạng thái thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btn_xoaActionPerformed


    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        if (txt_MaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        } else {
            if (this.isEmpty()) {
                if (this.checkupdate()) {
                    if (qltk.update(this.readform(), txt_MaNV.getText()) != 0) {
                        JOptionPane.showMessageDialog(this, "Sửa thành công!");
                        ArrayList<NhanVien> listNV = qltk.getAccount();
                        this.fillTable(listNV);

                        if (keyword != null) {
                            ArrayList<NhanVien> result = qltk.searchNhanVien(keyword);
                            fillTable(result);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                    }
                }
            }

        }

//        
    }//GEN-LAST:event_btn_suaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgr_cv;
    private javax.swing.ButtonGroup bgr_gt;
    private javax.swing.ButtonGroup bgr_tt;
    private com.daipc.swing.Button btn_sua;
    private com.daipc.swing.Button btn_them;
    private com.daipc.swing.Button btn_xoa;
    private com.daipc.swing.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.daipc.swing.PanelBorder panelBorder1;
    private com.daipc.swing.PanelBorder panelBorder2;
    private javax.swing.JPanel panel_tk;
    private javax.swing.JPanel panel_tt;
    private javax.swing.JRadioButton rdo_danghi;
    private javax.swing.JRadioButton rdo_danglam;
    private javax.swing.JRadioButton rdo_nam;
    private javax.swing.JRadioButton rdo_nu;
    private javax.swing.JRadioButton rdo_nv;
    private javax.swing.JRadioButton rdo_ql;
    private javax.swing.JTable tbl_danghi;
    private javax.swing.JTable tbl_danglam;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_NgayTao;
    private javax.swing.JTextField txt_cccd;
    private javax.swing.JTextArea txt_diachi;
    private javax.swing.JTextField txt_hoTen;
    private javax.swing.JTextField txt_mk;
    private com.toedter.calendar.JDateChooser txt_ngaySinh;
    private javax.swing.JTextField txt_sdt;
    private com.daipc.searchbar.MyTextField txt_timkiem2;
    private javax.swing.JTextField txt_tk;
    // End of variables declaration//GEN-END:variables
}
