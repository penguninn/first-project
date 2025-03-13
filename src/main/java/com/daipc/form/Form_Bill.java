package com.daipc.form;

import com.daipc.customTable.TableEvent;
import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDon;
import com.daipc.model.HoaDonCho;
import com.daipc.model.HoaDonModel;
import com.daipc.model.KhachHang;
import com.daipc.model.PhuongThucTT;
import com.daipc.model.Voucher;
import com.daipc.repo.HoaDonDao;
import com.daipc.repo.KhachHangService;
import com.daipc.repo.ProductDetaisDAO;
import com.daipc.repo.QuanLiBanHang;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Form_Bill extends javax.swing.JPanel {

    private final QuanLiBanHang QLBH = new QuanLiBanHang();

    private final List<HoaDonCho> listHDC = new ArrayList<>();
    private final int selectedRowHDC = -1;

    private final List<GioHang> listGH = new ArrayList<>();
    private final int selectedRowGH = -1;

    private final List<ChiTietSP> listSP = new ArrayList<>();
    private final int selectedRowSP = -1;

    private final List<KhachHang> listKH = new ArrayList<>();
    private final int selectedRowKH = -1;

    private final List<Voucher> listVoucher = new ArrayList<>();

    private final List<PhuongThucTT> listPTTT = new ArrayList<>();

    private final List<HoaDon> listHD = new ArrayList<>();

    private DefaultTableModel modelHDC;
    private DefaultTableModel modelGH;
    private DefaultTableModel modelSP;

    private TableEvent eventHuyHD;
    private int[] idVoucher;

    public Form_Bill() {
        initComponents();
        init();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                init();
            }

        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_HD = new javax.swing.JTable();
        txt_searchHD = new javax.swing.JTextField();
        btnClearFiltersDetails = new com.daipc.swing.Button();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_SPCT = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_GT = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_tongGTHD = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_maKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_tenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_diaChi = new javax.swing.JTextField();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn tại quầy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14)), "", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbl_HD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Tên Khách Hàng ", "Số Điện thoại", "Tổng Giá Trị", "Trạng Thái", "Ngày Tạo", "Nhân Viên"
            }
        ));
        tbl_HD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_HD);

        txt_searchHD.setBackground(new java.awt.Color(242, 242, 242));
        txt_searchHD.setForeground(java.awt.Color.gray);
        txt_searchHD.setText("Tìm kiếm hóa đơn theo tên khách hàng, SĐT ....");
        txt_searchHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 255)));
        txt_searchHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_searchHDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_searchHDFocusLost(evt);
            }
        });
        txt_searchHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchHDActionPerformed(evt);
            }
        });
        txt_searchHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchHDKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchHDKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_searchHDKeyTyped(evt);
            }
        });

        btnClearFiltersDetails.setText("Làm Mới");
        btnClearFiltersDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFiltersDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(txt_searchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearFiltersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearFiltersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_searchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm của hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N

        tbl_SPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Thương hiệu", "Size", "Màu sắc", "Số lượng", "Giá bán", "Tổng Tiền"
            }
        ));
        jScrollPane3.setViewportView(tbl_SPCT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N

        jLabel9.setText("Giới tính : ");
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_GT.setBackground(new java.awt.Color(242, 242, 242));
        txt_GT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_GT.setDoubleBuffered(true);
        txt_GT.setEnabled(false);
        txt_GT.setOpaque(true);
        txt_GT.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel11.setText("Tổng HĐ : ");
        jLabel11.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_tongGTHD.setBackground(new java.awt.Color(242, 242, 242));
        txt_tongGTHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_tongGTHD.setDoubleBuffered(true);
        txt_tongGTHD.setEnabled(false);
        txt_tongGTHD.setOpaque(true);
        txt_tongGTHD.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel2.setText("Mã khách hàng :");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_maKH.setBackground(new java.awt.Color(242, 242, 242));
        txt_maKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_maKH.setDoubleBuffered(true);
        txt_maKH.setEnabled(false);
        txt_maKH.setOpaque(true);
        txt_maKH.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel5.setText("Tên khach hàng : ");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_tenKH.setBackground(new java.awt.Color(242, 242, 242));
        txt_tenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_tenKH.setDoubleBuffered(true);
        txt_tenKH.setEnabled(false);
        txt_tenKH.setOpaque(true);
        txt_tenKH.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel6.setText("Số điện thoại : ");
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_sdt.setBackground(new java.awt.Color(242, 242, 242));
        txt_sdt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_sdt.setDoubleBuffered(true);
        txt_sdt.setEnabled(false);
        txt_sdt.setOpaque(true);
        txt_sdt.setPreferredSize(new java.awt.Dimension(200, 25));

        jLabel8.setText("Địa Chỉ : ");
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 25));

        txt_diaChi.setBackground(new java.awt.Color(242, 242, 242));
        txt_diaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 255)));
        txt_diaChi.setDoubleBuffered(true);
        txt_diaChi.setEnabled(false);
        txt_diaChi.setOpaque(true);
        txt_diaChi.setPreferredSize(new java.awt.Dimension(200, 25));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_maKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_tongGTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_GT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_maKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_GT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tongGTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_HDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HDMouseClicked
        int row = tbl_HD.getSelectedRow();
        String maHD = (String) tbl_HD.getValueAt(row, 1);
        fillSPCT_By_IdHD(maHD);//Fill spct
        fill_TTKH(maHD);
        System.out.println("Ma HD La : " + maHD);
    }//GEN-LAST:event_tbl_HDMouseClicked

    private void txt_searchHDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchHDFocusGained
        if (txt_searchHD.getText().equals("Tìm kiếm hóa đơn theo tên khách hàng, SĐT ....")) {
            txt_searchHD.setText(null);
            txt_searchHD.requestFocus(); //Yêu cầu tập trung Focus vào 1 component nào đó.
            //remove placeholder style
            removePlaceholderStyle(txt_searchHD);
        }
    }//GEN-LAST:event_txt_searchHDFocusGained

    private void txt_searchHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_searchHDFocusLost
        //Sự kiện xảy ra khi thành phần txt_timKiemSP UI nhận đc trạng thái focusLost.
        if (txt_searchHD.getText().length() == 0) {
            //add Placeholder style
            addPlaceholderStyle(txt_searchHD);
            txt_searchHD.setText("Tìm kiếm hóa đơn theo tên khách hàng, SĐT ....");
        }
    }//GEN-LAST:event_txt_searchHDFocusLost

    private void txt_searchHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchHDKeyReleased
        // Lấy giá trị từ ô tìm kiếm
        String searchText = txt_searchHD.getText().trim();
        System.out.println(searchText);
        if (!searchText.isEmpty()) {
            if (isPhoneNumber(searchText)) {
                timKiemSDT(searchText);
            } else if (isCustomerName(searchText)) {
                timKiemTenKH(searchText);
               
            }else{
 //           if(searchText.matches("\\d+")&&!searchText.matches("\\d+")){
                timKiem(searchText); 
            }
        }
//        else if (!searchText.isEmpty()) {
//            timKiem(searchText);
//        }


    }//GEN-LAST:event_txt_searchHDKeyReleased

    private void txt_searchHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchHDActionPerformed
        // TODO add your handling code here:

//            System.out.println("id laf : " + ma);
    }//GEN-LAST:event_txt_searchHDActionPerformed

    private void btnClearFiltersDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFiltersDetailsActionPerformed
        init();
        txt_searchHD.setText("");
        loadtxtseachHD();
        
    }//GEN-LAST:event_btnClearFiltersDetailsActionPerformed

    private void txt_searchHDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchHDKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_searchHDKeyTyped

    private void txt_searchHDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchHDKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_searchHDKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.Button btnClearFiltersDetails;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_HD;
    private javax.swing.JTable tbl_SPCT;
    private javax.swing.JTextField txt_GT;
    private javax.swing.JTextField txt_diaChi;
    private javax.swing.JTextField txt_maKH;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_searchHD;
    private javax.swing.JTextField txt_tenKH;
    private javax.swing.JTextField txt_tongGTHD;
    // End of variables declaration//GEN-END:variables

    HoaDonDao hdd = new HoaDonDao();
    DefaultTableModel dtmHD;

    ProductDetaisDAO prdd = new ProductDetaisDAO();
    DefaultTableModel dtmSCPT;

    KhachHangService khs = new KhachHangService();

    public final void init() {
//        fill_HTTT();
//        fill_NVPT();
        if (hdd.getListHD() != null) {//Fill hóa đơn
            fillHD(hdd.getListHD());//Fill hóa đơn đã thanh toán thành công.
        } else {
//            MsgBox.alter(this, "Chưa có hóa đơn nào được thanh toán");
        }
    }

    private void fillHD(List<HoaDonModel> listHD) {
        dtmHD = (DefaultTableModel) this.tbl_HD.getModel();
        dtmHD.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng

        int i = 1;
        for (HoaDonModel hd : listHD) {
            Object[] row = new Object[]{
                i++, // Số thứ tự
                hd.getMaHD(), // Mã hóa đơn
                (hd.getTenKH() == null) ? "Khách Bán lẻ" : hd.getTenKH(), // Tên khách hàng
                (hd.getSdt() == null) ? "Trống" : hd.getSdt(), // Số điện thoại
                //            hdd.getTienMatByIDHD(hd.getMaHD(), 1), // Tiền mặt
                //            hdd.getTienMatByIDHD(hd.getMaHD(), 2), // Chuyển khoản
                hd.getDonGia(), // Tổng giá trị hóa đơn
                ("1".equals(hd.getTrangThai())) ? "Đã thanh toán" : "Chưa thanh toán", // Trạng thái hóa đơn
                hd.getNgayTao(), // Ngày tạo
                hd.getTenNguoiTao() // Tên người tạo
            };
            dtmHD.addRow(row); // Thêm hàng vào bảng
        }
    }

    public void fillSPCT_By_IdHD(String idHD) {//fill SPCT
        dtmSCPT = (DefaultTableModel) tbl_SPCT.getModel();
        dtmSCPT.setRowCount(0);
        if (tbl_HD.getSelectedRow() != - 1) {
            List<ChiTietSP> listSPCT = prdd.selectAll_By_ID_MaHD(idHD);
            int i = 1;
            for (ChiTietSP spct : listSPCT) {
                Object[] row = new Object[]{
                    i++,
                    spct.getMaCTSP(),
                    spct.getTenSP(),
                    spct.getTenNhaCungCap(),
                    spct.getTenSize(),
                    spct.getTenMauSac(),
                    spct.getSoLuong(),//SL mua
                    spct.getGiaBan(),
                    spct.getGiaBan().multiply(new BigDecimal(spct.getSoLuong()))//Giá bán * SL mua
                };
                dtmSCPT.addRow(row);
            }
        }
    }

    public void fill_TTKH(String idHD) {
        KhachHang kh = khs.get_TTKH_In_HD(idHD);

        if (kh != null) {
            txt_maKH.setText(kh.getMaKhachHang() != null ? kh.getMaKhachHang() : "Không có dữ liệu");
            txt_tenKH.setText(kh.getHoTen() != null ? kh.getHoTen() : "Không có dữ liệu");
            txt_sdt.setText(kh.getSoDT() != null ? kh.getSoDT() : "Trống");
            txt_diaChi.setText(kh.getDiaChi() != null ? kh.getDiaChi() : "Trống");
            txt_tongGTHD.setText(String.valueOf(kh.getTongGTHD()));
            txt_GT.setText(kh.isGioiTinh() ? "Nam" : "Nữ");
            // Cập nhật trường tiền thừa nếu cần
            // txt_tienThua.setText(String.valueOf(kh.getTienThua()));
        } else {
            txt_maKH.setText("Không tìm thấy");
            txt_tenKH.setText("Không tìm thấy");
            txt_sdt.setText("Trống");
            txt_diaChi.setText("Trống");
            txt_tongGTHD.setText("0");
            txt_GT.setText("Không xác định");
            // txt_tienThua.setText("0"); // Nếu trường này cần xử lý
        }
    }

    private void removePlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.BLACK);
    }

    public void addPlaceholderStyle(JTextField textField) {
        Font font = textField.getFont();//Lấy font hiện tại của JTextField
        font = font.deriveFont(Font.ITALIC);//Tạo 1 bản sao mới của Font với kiểu in nghiêng ( Italic )
        textField.setFont(font); //Đặt font mới cho JTextField
        textField.setForeground(Color.GRAY); //Đặt màu chữ là Gray ( Xám ) 
    }

    public void timKiem(String maHD) {

        ArrayList<HoaDonModel> list = (ArrayList<HoaDonModel>) hdd.getSeachListHD(maHD);
        dtmHD = (DefaultTableModel) this.tbl_HD.getModel();
        dtmHD.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng

        int i = 1;
        for (HoaDonModel hd : list) {
            if (hd.getMaHD().isEmpty()) {
                init();
            } else {

                Object[] row = new Object[]{
                    i++, // Số thứ tự
                    hd.getMaHD(), // Mã hóa đơn
                    (hd.getTenKH() == null) ? "Khách vãng lai" : hd.getTenKH(), // Tên khách hàng
                    (hd.getSdt() == null) ? "Trống" : hd.getSdt(), // Số điện thoại
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 1), // Tiền mặt
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 2), // Chuyển khoản
                    hd.getDonGia(), // Tổng giá trị hóa đơn
                    ("1".equals(hd.getTrangThai())) ? "Đã thanh toán" : "Chưa thanh toán", // Trạng thái hóa đơn
                    hd.getNgayTao(), // Ngày tạo
                    hd.getTenNguoiTao() // Tên người tạo
                };
                dtmHD.addRow(row); // Thêm hàng vào bảng
            }

            System.out.println(maHD);
        }

    }

    public void timKiemSDT(String SDT) {

        ArrayList<HoaDonModel> list = (ArrayList<HoaDonModel>) hdd.getSeachList_SDT_HD(SDT);
        dtmHD = (DefaultTableModel) this.tbl_HD.getModel();
        dtmHD.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng

        int i = 0;
        for (HoaDonModel hd : list) {
            if (hd.getMaHD() == null) {
                init();
            } else {

                Object[] row = new Object[]{
                    i++, // Số thứ tự
                    hd.getMaHD(), // Mã hóa đơn
                    (hd.getTenKH() == null) ? "Khách vãng lai" : hd.getTenKH(), // Tên khách hàng
                    (hd.getSdt() == null) ? "Trống" : hd.getSdt(), // Số điện thoại
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 1), // Tiền mặt
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 2), // Chuyển khoản
                    hd.getDonGia(), // Tổng giá trị hóa đơn
                    ("1".equals(hd.getTrangThai())) ? "Đã thanh toán" : "Chưa thanh toán", // Trạng thái hóa đơn
                    hd.getNgayTao(), // Ngày tạo
                    hd.getTenNguoiTao() // Tên người tạo
                };
                dtmHD.addRow(row); // Thêm hàng vào bảng
            }

        }

    }

    public void timKiemTenKH(String TenKH) {

        ArrayList<HoaDonModel> list = (ArrayList<HoaDonModel>) hdd.getSeachList_TenKH_HD(TenKH);
        dtmHD = (DefaultTableModel) this.tbl_HD.getModel();
        dtmHD.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng

        int i = 0;
        for (HoaDonModel hd : list) {
            if (hd.getMaHD() == null) {
                init();
            } else {

                Object[] row = new Object[]{
                    i++, // Số thứ tự
                    hd.getMaHD(), // Mã hóa đơn
                    (hd.getTenKH() == null) ? "Khách vãng lai" : hd.getTenKH(), // Tên khách hàng
                    (hd.getSdt() == null) ? "Trống" : hd.getSdt(), // Số điện thoại
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 1), // Tiền mặt
                    //            hdd.getTienMatByIDHD(hd.getMaHD(), 2), // Chuyển khoản
                    hd.getDonGia(), // Tổng giá trị hóa đơn
                    ("1".equals(hd.getTrangThai())) ? "Đã thanh toán" : "Chưa thanh toán", // Trạng thái hóa đơn
                    hd.getNgayTao(), // Ngày tạo
                    hd.getTenNguoiTao() // Tên người tạo
                };
                dtmHD.addRow(row); // Thêm hàng vào bảng
            }

        }

    }

    private boolean isPhoneNumber(String text) {
        return text.matches("\\d+");
    }

    private boolean isCustomerName(String text) {
        return !text.matches("\\d+");
    }

    public static boolean maHD(String text) {
        if (text.matches(".*[a-zA-Z]+.*") && text.matches(".*\\d+.*")) {
            System.out.println("Ma la : " + text);
        }
        return text.matches(".*[a-zA-Z]+.*") && text.matches(".*\\d+.*");
    }
    
    
    public void loadtxtseachHD(){
        if (txt_searchHD.getText().length() == 0) {
            //add Placeholder style
            addPlaceholderStyle(txt_searchHD);
            txt_searchHD.setText("Tìm kiếm hóa đơn theo tên khách hàng, SĐT ....");
        }
//        if (txt_searchHD.getText().equals("Tìm kiếm hóa đơn theo tên khách hàng, SĐT ....")) {
//            txt_searchHD.setText(null);
//            txt_searchHD.requestFocus(); //Yêu cầu tập trung Focus vào 1 component nào đó.
//            //remove placeholder style
//            removePlaceholderStyle(txt_searchHD);
//            return;
//        }
        
        
    }
}
