package com.daipc.form;

import com.daipc.IO.Export;
import com.daipc.IO.Import;
import com.daipc.ScrollBar.ScrollbarCustom;
import com.daipc.enumm.HienThi;
import com.daipc.enumm.TrangThai;
import com.daipc.enumm.TrangThaiCRUD;
import static com.daipc.enumm.TrangThaiCRUD.DaTonTai;
import static com.daipc.enumm.TrangThaiCRUD.ThanhCong;
import static com.daipc.enumm.TrangThaiCRUD.ThatBai;
import com.daipc.model.ChatLieu;
import com.daipc.model.ChiTietSP;
import com.daipc.model.DoDay;
import com.daipc.model.MauSac;
import com.daipc.model.NhaCungCap;
import com.daipc.model.SPCT;
import com.daipc.model.SanPham;
import com.daipc.model.Size;
import com.daipc.repo.ChatLieuRepo;
import com.daipc.repo.DoDayRepo;
import com.daipc.repo.MauSacRepo;
import com.daipc.repo.NhaCungCapRepo;
import com.daipc.repo.QuanLiChiTietSP;
import com.daipc.repo.QuanLiSanPham;
import com.daipc.repo.Repository;
import com.daipc.repo.SizeRepo;
import com.daipc.searchbar.DataSearch;
import com.daipc.searchbar.EventClick;
import com.daipc.searchbar.PanelSearch;
import com.daipc.table.TableCustom;
import com.daipc.textfield.TextField;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import jnafilechooser.api.JnaFileChooser;
public class Form_Products extends javax.swing.JPanel {

    private JPopupMenu menu;
    private JPopupMenu menuDetails;
    private PanelSearch search;
    private PanelSearch searchDetails;
    private List<SanPham> listSP;
    private List<ChiTietSP> listCTSP;
    private final QuanLiSanPham QLSP = new QuanLiSanPham();
    private final Repository repository = new QuanLiSanPham();
    private final DefaultComboBoxModel boxSanPham = new DefaultComboBoxModel();
    private final QuanLiChiTietSP chiTietSPService = new QuanLiChiTietSP();
    private DefaultTableModel modelCTSP;
    private final HashMap<String, Integer> selectedRow = new HashMap<>();
    private Form_ChooserChoice chooserChoice;
    private Popup_ReviewDataImport popupReviewDataImport;
    private final JFrame frame;
    private Export exported;
    private Import imported;

    private String originalMaMauSac;
    private String originalMaDoDay;
    private String originalMaSize;
    private String originalMaChatLieu;
    private String originalMaNhaCungCap;
    private List<MauSac> listMS;
    private List<DoDay> listDD;
    private List<Size> listSize;
    private List<ChatLieu> listChatLieu;
    private List<NhaCungCap> listNhaCungCap;
    private final MauSacRepo MauRepo = new MauSacRepo();
    private final DoDayRepo doDayRepo = new DoDayRepo();
    private final SizeRepo sizeRepo = new SizeRepo();
    private final ChatLieuRepo chatLieuRepo = new ChatLieuRepo();
    private final NhaCungCapRepo nhaCungCapRepo = new NhaCungCapRepo();
    

    DefaultTableModel modelSP = new DefaultTableModel(new String[]{"Mã Sản Phẩm", "Tên Sản Phẩm", "Mô Tả"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelSPCT = new DefaultTableModel(new String[]{"Mã Sản Phẩm Chi Tiết", "Mã Sản Phẩm", "Tên Sản Phẩm", "Tên Sản Phẩm Chi Tiết",
        "Giá Bán", "Giá Nhập", "Số Lượng", "Nhà Cung Cấp  ", "Chất Liệu", "Màu sắc",
        "Size", "Độ Dày", "Trạng Thái"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelMauSac = new DefaultTableModel(new String[]{"Mã Màu Sắc", "Tên Màu Sắc"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelDoDay = new DefaultTableModel(new String[]{"Mã Độ Dày", "Tên Độ Dày"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelSize = new DefaultTableModel(new String[]{"Mã Size", "Tên Size"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelChatLieu = new DefaultTableModel(new String[]{"Mã Chất liệu", "Tên Chất Liệu"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    DefaultTableModel modelNhaCungCap = new DefaultTableModel(new String[]{"Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Địa Chỉ", "Liên Hệ"}, 10) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public Form_Products(JFrame frame) {
        initComponents();
        TableCustom.apply(scrollInfoProducts, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollDetailsProducts, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollMau, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollDoDay, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollSize, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollChatLieu, TableCustom.TableType.DEFAULT);
        TableCustom.apply(scrollNhaCc, TableCustom.TableType.DEFAULT);

        this.frame = frame;

        listSP = new ArrayList<>();
        listMS = new ArrayList<>();
        listDD = new ArrayList<>();
        listSize = new ArrayList<>();
        listChatLieu = new ArrayList<>();
        listNhaCungCap = new ArrayList<>();

        selectedRow.put("SP", -1);
        selectedRow.put("SPCT", -1);
        selectedRow.put("MauSac", -1);
        selectedRow.put("DoDay", -1);
        selectedRow.put("Size", -1);
        selectedRow.put("ChatLieu", -1);
        selectedRow.put("NhaCungCap", -1);

        tblDetailsProducts.setModel(modelSPCT);
        tblMauSac.setModel(modelMauSac);
        tblDoDay.setModel(modelDoDay);
        tblSize.setModel(modelSize);
        tblChatLieu.setModel(modelChatLieu);
        tblNhaCc.setModel(modelNhaCungCap);

        scrollMoTa.setVerticalScrollBar(new ScrollbarCustom());
        ScrollbarCustom sp = new ScrollbarCustom();
        sp.setOrientation(JScrollBar.HORIZONTAL);
        scrollMoTa.setHorizontalScrollBar(sp);

        initFormSP();
        initFormTTSP();
        initFormSPCT();
        initSearch();
        initSearchDetails();
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        materialTabbed = new com.daipc.swing.MaterialTabbed();
        infoProducts = new javax.swing.JPanel();
        panelInfoProducts = new com.daipc.swing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSP = new com.daipc.textfield.TextField();
        txtTenSP = new com.daipc.textfield.TextField();
        scrollInfoProducts = new javax.swing.JScrollPane();
        tblInfoProducts = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        scrollMoTa = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem = new com.daipc.swing.Button();
        btnSua = new com.daipc.swing.Button();
        btnXoaMem = new com.daipc.swing.Button();
        btnClear = new com.daipc.swing.Button();
        panelFilter = new com.daipc.swing.PanelBorder();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new com.daipc.searchbar.MyTextField();
        detailsProducts = new javax.swing.JPanel();
        panelDetailsProducts = new com.daipc.swing.PanelBorder();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbb_SP = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        txt_MSPCT = new javax.swing.JTextField();
        txt_Soluong = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_GiaBan = new javax.swing.JTextField();
        cbb_TT = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cbb_Size = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        cbb_MauSac = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        cbb_ChatLieu = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        jLabel27 = new javax.swing.JLabel();
        cbb_DoDay = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        cbb_NCC = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        scrollDetailsProducts = new javax.swing.JScrollPane();
        tblDetailsProducts = new javax.swing.JTable();
        txt_GiaNhap = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txt_TenSPCT = new javax.swing.JTextField();
        btnThemDetails = new com.daipc.swing.Button();
        btnSuaDetails = new com.daipc.swing.Button();
        btnClearDetails = new com.daipc.swing.Button();
        btnImportDetails = new com.daipc.swing.Button();
        btnExportDetails = new com.daipc.swing.Button();
        btnXoaDetails = new com.daipc.swing.Button();
        panelFilter1 = new com.daipc.swing.PanelBorder();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbb_SP_Filter = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSearchDetails = new com.daipc.searchbar.MyTextField();
        jLabel16 = new javax.swing.JLabel();
        btnClearFiltersDetails = new com.daipc.swing.Button();
        jLabel18 = new javax.swing.JLabel();
        cbb_searchGN = new javax.swing.JComboBox<>();
        cbb_searchGB = new javax.swing.JComboBox<>();
        cbb_TT_Fil = new com.daipc.combo_suggestion.ComboBoxSuggestion();
        attributesProducts = new javax.swing.JPanel();
        panelTong = new com.daipc.swing.PanelBorder();
        panelMauSac = new com.daipc.swing.PanelBorder();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtTenMauSac = new javax.swing.JTextField();
        txtMaMauSac = new javax.swing.JTextField();
        btnThemMau = new com.daipc.swing.Button();
        btnSuaMau = new com.daipc.swing.Button();
        btnClearMau = new com.daipc.swing.Button();
        btnXoaMau = new com.daipc.swing.Button();
        panelDoDay = new com.daipc.swing.PanelBorder();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtMaDoDay = new javax.swing.JTextField();
        txtTenDoDay = new javax.swing.JTextField();
        btnThemDoDay = new com.daipc.swing.Button();
        ClearDoDay = new com.daipc.swing.Button();
        btnSuaDoDay = new com.daipc.swing.Button();
        btnXoaDoDay = new com.daipc.swing.Button();
        panelSize = new com.daipc.swing.PanelBorder();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtTenSize = new javax.swing.JTextField();
        txtMaSize = new javax.swing.JTextField();
        btnThemSize = new com.daipc.swing.Button();
        btnSuaSize = new com.daipc.swing.Button();
        btnClearSize = new com.daipc.swing.Button();
        btnXoaSize = new com.daipc.swing.Button();
        panelChatLieu = new com.daipc.swing.PanelBorder();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtTenChatLieu = new javax.swing.JTextField();
        txtMaChatLieu = new javax.swing.JTextField();
        btnThemChatLieu = new com.daipc.swing.Button();
        btnSuaChatLieu = new com.daipc.swing.Button();
        btnClearChatLieu = new com.daipc.swing.Button();
        btnXoaChatLieu = new com.daipc.swing.Button();
        panelNhaCc = new com.daipc.swing.PanelBorder();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtTenNcc = new javax.swing.JTextField();
        txtMaNcc = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtLienHe = new javax.swing.JTextField();
        btnThemNCC = new com.daipc.swing.Button();
        btnSuaNCC = new com.daipc.swing.Button();
        btnClearNCC = new com.daipc.swing.Button();
        btnXoaNhaCungCap = new com.daipc.swing.Button();
        panelBorder2 = new com.daipc.swing.PanelBorder();
        btnMauSac = new com.daipc.radiobutton.RadioButtonCustom();
        btnDoDay = new com.daipc.radiobutton.RadioButtonCustom();
        btnSize = new com.daipc.radiobutton.RadioButtonCustom();
        btnChatLieu = new com.daipc.radiobutton.RadioButtonCustom();
        btnNhaCungCap = new com.daipc.radiobutton.RadioButtonCustom();
        panelTableTong = new com.daipc.swing.PanelBorder();
        panelTblMau = new com.daipc.swing.PanelBorder();
        scrollMau = new javax.swing.JScrollPane();
        tblMauSac = new javax.swing.JTable();
        panelTblDoDay = new com.daipc.swing.PanelBorder();
        scrollDoDay = new javax.swing.JScrollPane();
        tblDoDay = new javax.swing.JTable();
        panelTblSize = new com.daipc.swing.PanelBorder();
        scrollSize = new javax.swing.JScrollPane();
        tblSize = new javax.swing.JTable();
        panelTblChatLieu = new com.daipc.swing.PanelBorder();
        scrollChatLieu = new javax.swing.JScrollPane();
        tblChatLieu = new javax.swing.JTable();
        panelTblNhaCc = new com.daipc.swing.PanelBorder();
        scrollNhaCc = new javax.swing.JScrollPane();
        tblNhaCc = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        materialTabbed.setBackground(new java.awt.Color(234, 234, 234));

        infoProducts.setBackground(new java.awt.Color(234, 234, 234));

        panelInfoProducts.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("THÔNG TIN SẢN PHẨM");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setText("Mã Sản Phẩm");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setText("Tên Sản Phẩm");

        txtMaSP.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMaSP.setLabelText("Mã Sản Phẩm");

        txtTenSP.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTenSP.setLabelText("Tên Sản Phẩm");

        tblInfoProducts.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblInfoProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInfoProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInfoProductsMouseClicked(evt);
            }
        });
        scrollInfoProducts.setViewportView(tblInfoProducts);

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setText("Mô tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        scrollMoTa.setViewportView(txtMoTa);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoaMem.setText("Xóa");
        btnXoaMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMemActionPerformed(evt);
            }
        });

        btnClear.setText("Làm Mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInfoProductsLayout = new javax.swing.GroupLayout(panelInfoProducts);
        panelInfoProducts.setLayout(panelInfoProductsLayout);
        panelInfoProductsLayout.setHorizontalGroup(
            panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoProductsLayout.createSequentialGroup()
                .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoProductsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelInfoProductsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollInfoProducts)))
                .addContainerGap())
            .addGroup(panelInfoProductsLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoProductsLayout.createSequentialGroup()
                        .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(50, 50, 50)
                        .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(62, 62, 62)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoProductsLayout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnXoaMem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(62, 62, 62)
                .addComponent(scrollMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addGap(192, 192, 192))
        );
        panelInfoProductsLayout.setVerticalGroup(
            panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoProductsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoProductsLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(14, 14, 14)
                        .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoProductsLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(scrollMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addGroup(panelInfoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaMem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollInfoProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFilter.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("LỌC SẢN PHẨM");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setText("Tìm Kiếm");

        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6))
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE))))
                .addGap(190, 190, 190))
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout infoProductsLayout = new javax.swing.GroupLayout(infoProducts);
        infoProducts.setLayout(infoProductsLayout);
        infoProductsLayout.setHorizontalGroup(
            infoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        infoProductsLayout.setVerticalGroup(
            infoProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfoProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        materialTabbed.addTab("Thông Tin Sản Phẩm", infoProducts);

        detailsProducts.setBackground(new java.awt.Color(234, 234, 234));

        panelDetailsProducts.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("THÔNG TIN SẢN PHẨM");

        jLabel11.setText("Sản Phẩm");

        jLabel17.setText("Mã Sản Phẩm Chi Tiết");

        jLabel19.setText("Số Lượng");

        jLabel20.setText("Nhà Cung Cấp");

        txt_MSPCT.setEditable(false);

        jLabel21.setText("Trạng Thái");

        jLabel22.setText("Giá Bán");

        cbb_TT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Còn hàng", "Hết hàng " }));

        jLabel23.setText("Size");

        jLabel24.setText("Màu Sắc");

        jLabel25.setText("Chất Liệu");

        cbb_Size.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "2", "3", "3", "3", "3", "3", "3" }));
        cbb_Size.setSelectedIndex(-1);

        cbb_MauSac.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "2", "3", "3", "3", "3", "3", "3" }));
        cbb_MauSac.setSelectedIndex(-1);

        cbb_ChatLieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "2", "3", "3", "3", "3", "3", "3" }));
        cbb_ChatLieu.setSelectedIndex(-1);

        jLabel27.setText("Độ Dày");

        cbb_DoDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "2", "3", "3", "3", "3", "3", "3" }));
        cbb_DoDay.setSelectedIndex(-1);

        cbb_NCC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "2", "3", "3", "3", "3", "3", "3" }));
        cbb_NCC.setSelectedIndex(-1);

        scrollDetailsProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollDetailsProductsMouseClicked(evt);
            }
        });

        tblDetailsProducts.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblDetailsProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetailsProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailsProductsMouseClicked(evt);
            }
        });
        scrollDetailsProducts.setViewportView(tblDetailsProducts);

        jLabel28.setText("Tên Sản Phẩm Chi Tiết");

        jLabel45.setText("Giá Nhập");

        btnThemDetails.setText("Thêm");
        btnThemDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDetailsActionPerformed(evt);
            }
        });

        btnSuaDetails.setText("Sửa");
        btnSuaDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDetailsActionPerformed(evt);
            }
        });

        btnClearDetails.setText("Làm Mới");
        btnClearDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearDetailsActionPerformed(evt);
            }
        });

        btnImportDetails.setText("Nhập File");
        btnImportDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportDetailsActionPerformed(evt);
            }
        });

        btnExportDetails.setText("Xuất File");
        btnExportDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportDetailsActionPerformed(evt);
            }
        });

        btnXoaDetails.setText("Xóa");
        btnXoaDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetailsProductsLayout = new javax.swing.GroupLayout(panelDetailsProducts);
        panelDetailsProducts.setLayout(panelDetailsProductsLayout);
        panelDetailsProductsLayout.setHorizontalGroup(
            panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel10))
                            .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel19))
                                .addGap(30, 30, 30)
                                .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_Soluong)
                                            .addComponent(txt_MSPCT, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cbb_SP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                                                .addComponent(txt_TenSPCT)
                                                .addGap(2, 2, 2)))
                                        .addGap(70, 70, 70)
                                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel45))
                                        .addGap(30, 30, 30)
                                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_GiaNhap)
                                            .addComponent(txt_GiaBan)
                                            .addComponent(cbb_Size, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbb_MauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(70, 70, 70)
                                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel21))
                                        .addGap(30, 30, 30)
                                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbb_TT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbb_ChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbb_DoDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbb_NCC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                                        .addComponent(btnThemDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSuaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXoaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnClearDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnImportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnExportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(64, 64, 64))
                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollDetailsProducts)))
                .addContainerGap())
        );
        panelDetailsProductsLayout.setVerticalGroup(
            panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10)
                .addGap(27, 27, 27)
                .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_ChatLieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(cbb_SP, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25)
                                .addComponent(jLabel45)
                                .addComponent(txt_GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)
                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_MSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel27)
                            .addComponent(jLabel22)
                            .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(cbb_Size, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(cbb_NCC, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(txt_TenSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(cbb_MauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19)
                                .addComponent(txt_Soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(cbb_TT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDetailsProductsLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(cbb_DoDay, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClearDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThemDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDetailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scrollDetailsProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelFilter1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("LỌC SẢN PHẨM");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setText("Sản Phẩm");

        cbb_SP_Filter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbb_SP_FilterFocusGained(evt);
            }
        });
        cbb_SP_Filter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbb_SP_FilterMouseClicked(evt);
            }
        });
        cbb_SP_Filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_SP_FilterActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setText("Trạng Thái");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setText("Tìm Kiếm");

        txtSearchDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchDetailsMouseClicked(evt);
            }
        });
        txtSearchDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchDetailsActionPerformed(evt);
            }
        });
        txtSearchDetails.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDetailsKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setText("Giá Bán");

        btnClearFiltersDetails.setText("Làm Mới");
        btnClearFiltersDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFiltersDetailsActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setText("Giá Nhập");

        cbb_searchGN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dưới 500.000đ", "500.000 - 1 triệu", "1 - 2 triệu ", "Giá trên 3 triệu " }));
        cbb_searchGN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_searchGNActionPerformed(evt);
            }
        });

        cbb_searchGB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dưới 500.000đ", "500.000 - 1 triệu", "1 - 2 triệu ", "Giá trên 3 triệu " }));
        cbb_searchGB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_searchGBActionPerformed(evt);
            }
        });

        cbb_TT_Fil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Còn hàng", "Hết hàng " }));
        cbb_TT_Fil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbb_TT_FilFocusGained(evt);
            }
        });
        cbb_TT_Fil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbb_TT_FilMouseClicked(evt);
            }
        });
        cbb_TT_Fil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_TT_FilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFilter1Layout = new javax.swing.GroupLayout(panelFilter1);
        panelFilter1.setLayout(panelFilter1Layout);
        panelFilter1Layout.setHorizontalGroup(
            panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilter1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilter1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelFilter1Layout.createSequentialGroup()
                        .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_searchGN, 0, 201, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbb_searchGB, 0, 201, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbb_SP_Filter, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(cbb_TT_Fil, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFilter1Layout.createSequentialGroup()
                                .addComponent(txtSearchDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addGap(28, 28, 28)
                                .addComponent(btnClearFiltersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelFilter1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelFilter1Layout.setVerticalGroup(
            panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilter1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelFilter1Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addGap(40, 40, 40))
                        .addGroup(panelFilter1Layout.createSequentialGroup()
                            .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel15))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbb_searchGB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelFilter1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbb_SP_Filter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSearchDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbb_TT_Fil, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClearFiltersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbb_searchGN, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detailsProductsLayout = new javax.swing.GroupLayout(detailsProducts);
        detailsProducts.setLayout(detailsProductsLayout);
        detailsProductsLayout.setHorizontalGroup(
            detailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFilter1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDetailsProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        detailsProductsLayout.setVerticalGroup(
            detailsProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detailsProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDetailsProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        materialTabbed.addTab("Chi Tiết Sản Phẩm", detailsProducts);

        panelTong.setBackground(new java.awt.Color(255, 255, 255));
        panelTong.setLayout(new java.awt.CardLayout());

        panelMauSac.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("Màu sắc");

        jLabel29.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel29.setText("Mã màu sắc :");

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel30.setText("Tên màu sắc :");

        btnThemMau.setText("Thêm");
        btnThemMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMauActionPerformed(evt);
            }
        });

        btnSuaMau.setText("Sửa");
        btnSuaMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMauActionPerformed(evt);
            }
        });

        btnClearMau.setText("Clear");
        btnClearMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearMauActionPerformed(evt);
            }
        });

        btnXoaMau.setText("Xóa");
        btnXoaMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMauSacLayout = new javax.swing.GroupLayout(panelMauSac);
        panelMauSac.setLayout(panelMauSacLayout);
        panelMauSacLayout.setHorizontalGroup(
            panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMauSacLayout.createSequentialGroup()
                .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMauSacLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMauSacLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29))))
                .addGap(11, 11, 11)
                .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenMauSac)
                    .addComponent(txtMaMauSac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMauSacLayout.createSequentialGroup()
                        .addComponent(btnThemMau, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaMau, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMauSacLayout.createSequentialGroup()
                        .addComponent(btnClearMau, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaMau, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        panelMauSacLayout.setVerticalGroup(
            panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMauSacLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtMaMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMauSacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtTenMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelTong.add(panelMauSac, "card3");

        panelDoDay.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setText("Độ dày");

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel32.setText("Mã độ dày :");

        jLabel33.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel33.setText("Tên độ dày :");

        btnThemDoDay.setText("Thêm");
        btnThemDoDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDoDayActionPerformed(evt);
            }
        });

        ClearDoDay.setText("Clear");
        ClearDoDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearDoDayActionPerformed(evt);
            }
        });

        btnSuaDoDay.setText("Sửa");
        btnSuaDoDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDoDayActionPerformed(evt);
            }
        });

        btnXoaDoDay.setText("Xoá");
        btnXoaDoDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDoDayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDoDayLayout = new javax.swing.GroupLayout(panelDoDay);
        panelDoDay.setLayout(panelDoDayLayout);
        panelDoDayLayout.setHorizontalGroup(
            panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoDayLayout.createSequentialGroup()
                .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDoDayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDoDayLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaDoDay, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                            .addComponent(txtTenDoDay))))
                .addGap(36, 36, 36)
                .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThemDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        panelDoDayLayout.setVerticalGroup(
            panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDoDayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtMaDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtTenDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelTong.add(panelDoDay, "card2");

        panelSize.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel34.setText("Size");

        jLabel35.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel35.setText("Mã size :");

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel36.setText("Tên size");

        btnThemSize.setText("Thêm");
        btnThemSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSizeActionPerformed(evt);
            }
        });

        btnSuaSize.setText("Sửa");
        btnSuaSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSizeActionPerformed(evt);
            }
        });

        btnClearSize.setText("Clear");
        btnClearSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSizeActionPerformed(evt);
            }
        });

        btnXoaSize.setText("Xóa");
        btnXoaSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSizeLayout = new javax.swing.GroupLayout(panelSize);
        panelSize.setLayout(panelSizeLayout);
        panelSizeLayout.setHorizontalGroup(
            panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSizeLayout.createSequentialGroup()
                .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSizeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSizeLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(39, 39, 39)
                .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenSize, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                    .addComponent(txtMaSize))
                .addGap(34, 34, 34)
                .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSizeLayout.createSequentialGroup()
                        .addComponent(btnThemSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSizeLayout.createSequentialGroup()
                        .addComponent(btnClearSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        panelSizeLayout.setVerticalGroup(
            panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSizeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtMaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtTenSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelTong.add(panelSize, "card5");

        panelChatLieu.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel37.setText("Chất liệu");

        jLabel38.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel38.setText("Mã chất liệu :");

        jLabel39.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel39.setText("Tên màu sắc :");

        btnThemChatLieu.setText("Thêm");
        btnThemChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChatLieuActionPerformed(evt);
            }
        });

        btnSuaChatLieu.setText("Sửa");
        btnSuaChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaChatLieuActionPerformed(evt);
            }
        });

        btnClearChatLieu.setText("Clear");
        btnClearChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearChatLieuActionPerformed(evt);
            }
        });

        btnXoaChatLieu.setText("Xóa");
        btnXoaChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChatLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChatLieuLayout = new javax.swing.GroupLayout(panelChatLieu);
        panelChatLieu.setLayout(panelChatLieuLayout);
        panelChatLieuLayout.setHorizontalGroup(
            panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatLieuLayout.createSequentialGroup()
                .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChatLieuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChatLieuLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel38))))
                .addGap(18, 18, 18)
                .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(txtTenChatLieu))
                .addGap(18, 18, 18)
                .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChatLieuLayout.createSequentialGroup()
                        .addComponent(btnThemChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChatLieuLayout.createSequentialGroup()
                        .addComponent(btnClearChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        panelChatLieuLayout.setVerticalGroup(
            panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChatLieuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelTong.add(panelChatLieu, "card6");

        panelNhaCc.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel40.setText("Nhà cung cấp");

        jLabel41.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel41.setText("Mã nhà cung cấp :");

        jLabel42.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel42.setText("Tên nhà cung cấp :");

        jLabel43.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel43.setText("Địa chỉ :");

        jLabel44.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel44.setText("Liên hệ :");

        btnThemNCC.setText("Thêm");
        btnThemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNCCActionPerformed(evt);
            }
        });

        btnSuaNCC.setText("Sửa");
        btnSuaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNCCActionPerformed(evt);
            }
        });

        btnClearNCC.setText("Clear");
        btnClearNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearNCCActionPerformed(evt);
            }
        });

        btnXoaNhaCungCap.setText("Xóa");
        btnXoaNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNhaCungCapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNhaCcLayout = new javax.swing.GroupLayout(panelNhaCc);
        panelNhaCc.setLayout(panelNhaCcLayout);
        panelNhaCcLayout.setHorizontalGroup(
            panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhaCcLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNcc, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(txtTenNcc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44))
                .addGap(18, 18, 18)
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(txtLienHe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSuaNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaNhaCungCap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelNhaCcLayout.setVerticalGroup(
            panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNhaCcLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelNhaCcLayout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelNhaCcLayout.createSequentialGroup()
                                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSuaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClearNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLienHe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelNhaCcLayout.createSequentialGroup()
                                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel41)
                                    .addComponent(txtMaNcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel42)
                                    .addComponent(txtTenNcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelNhaCcLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(8, 8, 8)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelTong.add(panelNhaCc, "card4");

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup1.add(btnMauSac);
        btnMauSac.setSelected(true);
        btnMauSac.setText("Màu sắc");
        btnMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMauSacActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnDoDay);
        btnDoDay.setText("Độ dày");
        btnDoDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoDayActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnSize);
        btnSize.setText("Size");
        btnSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSizeActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnChatLieu);
        btnChatLieu.setText("Chất liệu");
        btnChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatLieuActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnNhaCungCap);
        btnNhaCungCap.setText("Nhà cung cấp");
        btnNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaCungCapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(btnDoDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChatLieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addComponent(btnNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTableTong.setBackground(new java.awt.Color(255, 255, 255));
        panelTableTong.setLayout(new java.awt.CardLayout());

        panelTblMau.setBackground(new java.awt.Color(255, 255, 255));

        tblMauSac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã màu sắc", "Tên màu sắc"
            }
        ));
        tblMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMauSacMouseClicked(evt);
            }
        });
        scrollMau.setViewportView(tblMauSac);

        javax.swing.GroupLayout panelTblMauLayout = new javax.swing.GroupLayout(panelTblMau);
        panelTblMau.setLayout(panelTblMauLayout);
        panelTblMauLayout.setHorizontalGroup(
            panelTblMauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTblMauLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollMau, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTblMauLayout.setVerticalGroup(
            panelTblMauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblMauLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollMau, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTableTong.add(panelTblMau, "card3");

        panelTblDoDay.setBackground(new java.awt.Color(255, 255, 255));
        panelTblDoDay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTblDoDayMouseClicked(evt);
            }
        });

        tblDoDay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã độ dày", "Tên độ dày"
            }
        ));
        tblDoDay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoDayMouseClicked(evt);
            }
        });
        tblDoDay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDoDayKeyPressed(evt);
            }
        });
        scrollDoDay.setViewportView(tblDoDay);

        javax.swing.GroupLayout panelTblDoDayLayout = new javax.swing.GroupLayout(panelTblDoDay);
        panelTblDoDay.setLayout(panelTblDoDayLayout);
        panelTblDoDayLayout.setHorizontalGroup(
            panelTblDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblDoDayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDoDay, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTblDoDayLayout.setVerticalGroup(
            panelTblDoDayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblDoDayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollDoDay, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTableTong.add(panelTblDoDay, "card2");

        panelTblSize.setBackground(new java.awt.Color(255, 255, 255));

        tblSize.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã Size", "Tên Size"
            }
        ));
        tblSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSizeMouseClicked(evt);
            }
        });
        scrollSize.setViewportView(tblSize);

        javax.swing.GroupLayout panelTblSizeLayout = new javax.swing.GroupLayout(panelTblSize);
        panelTblSize.setLayout(panelTblSizeLayout);
        panelTblSizeLayout.setHorizontalGroup(
            panelTblSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblSizeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollSize, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTblSizeLayout.setVerticalGroup(
            panelTblSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblSizeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollSize, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTableTong.add(panelTblSize, "card6");

        panelTblChatLieu.setBackground(new java.awt.Color(255, 255, 255));

        tblChatLieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã chất liệu", "Tên chất liệu"
            }
        ));
        tblChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChatLieuMouseClicked(evt);
            }
        });
        scrollChatLieu.setViewportView(tblChatLieu);

        javax.swing.GroupLayout panelTblChatLieuLayout = new javax.swing.GroupLayout(panelTblChatLieu);
        panelTblChatLieu.setLayout(panelTblChatLieuLayout);
        panelTblChatLieuLayout.setHorizontalGroup(
            panelTblChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblChatLieuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTblChatLieuLayout.setVerticalGroup(
            panelTblChatLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblChatLieuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTableTong.add(panelTblChatLieu, "card5");

        panelTblNhaCc.setBackground(new java.awt.Color(255, 255, 255));

        tblNhaCc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Liên hệ"
            }
        ));
        tblNhaCc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaCcMouseClicked(evt);
            }
        });
        scrollNhaCc.setViewportView(tblNhaCc);

        javax.swing.GroupLayout panelTblNhaCcLayout = new javax.swing.GroupLayout(panelTblNhaCc);
        panelTblNhaCc.setLayout(panelTblNhaCcLayout);
        panelTblNhaCcLayout.setHorizontalGroup(
            panelTblNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblNhaCcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollNhaCc, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTblNhaCcLayout.setVerticalGroup(
            panelTblNhaCcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblNhaCcLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollNhaCc, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTableTong.add(panelTblNhaCc, "card4");

        javax.swing.GroupLayout attributesProductsLayout = new javax.swing.GroupLayout(attributesProducts);
        attributesProducts.setLayout(attributesProductsLayout);
        attributesProductsLayout.setHorizontalGroup(
            attributesProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attributesProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(attributesProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTableTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(attributesProductsLayout.createSequentialGroup()
                        .addComponent(panelTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        attributesProductsLayout.setVerticalGroup(
            attributesProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attributesProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(attributesProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTableTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        materialTabbed.addTab("Thuộc Tính Sản Phẩm", attributesProducts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public void initSearch() {
        menu = new JPopupMenu();
        search = new PanelSearch();
        menu.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, new Color(97, 203, 227)));
        menu.add(search);
        menu.setFocusable(false);
        search.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menu.setVisible(false);
                txtSearch.setText(data.getText());
                System.out.println(data.getText());
                search(data.getText());
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {

            }
        });
    }

    public void initSearchDetails() {
        menuDetails = new JPopupMenu();
        searchDetails = new PanelSearch();
        menuDetails.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, new Color(97, 203, 227)));
        menuDetails.add(searchDetails);
        menuDetails.setFocusable(false);
        searchDetails.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menuDetails.setVisible(false);
                txtSearchDetails.setText(data.getText());
                System.out.println(data.getText());
                searchDetails(data.getText());
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {

            }
        });
    }

    // Form San Pham
    public void initFormSP() {
        tblInfoProducts.setModel(modelSP);

        loadDataToTable(modelSP, listSP, "SP", QLSP, "select * from SanPham where hienthi = ?", HienThi.Hien.name());
        if (selectedRow.get("SP") >= 0) {
            showDetailsSP();
        }
    }

    public void initFormSPCT() {
        loadDataCTSP();
        loadCbbSP();
        loadcombDD();
        loadcombMauSac();
        loadcombSize();
        loadcombbChatLieu();
        loadcombbNCC();
    }

    public void initFormTTSP() {
        initFormMS();
        initFormDD();
        initFormSize();
        initFormCL();
        initFormNhaCungCap();
        loadMaMauSac();
        loadMaDoDay();
        loadMaSize();
        loadMaChatLieu();
        loadMaNhaCungCap();
    }

    public void loadMaMauSac(){
        String ma = MauRepo.generateMaMau();
        txtMaMauSac.setEditable(false);
        txtMaMauSac.setText(ma);
    }
    public void loadMaDoDay(){
        String ma = doDayRepo.generateMaDoDay();
        txtMaDoDay.setEditable(false);
        txtMaDoDay.setText(ma);
    }
    public void loadMaSize(){
        String ma = sizeRepo.generateMaSize();
        txtMaSize.setEditable(false);
        txtMaSize.setText(ma);
    }
    public void loadMaChatLieu(){
        String ma = chatLieuRepo.generateMaChatLieu();
        txtMaChatLieu.setEditable(false);
        txtMaChatLieu.setText(ma);
    }
    public void loadMaNhaCungCap(){
        String ma = nhaCungCapRepo.generateMaNhaCungCap();
        txtMaNcc.setEditable(false);
        txtMaNcc.setText(ma);
    }
    public void loadDataToTable(DefaultTableModel model, List<SanPham> list, String str, Repository<SanPham> repo, String sqlQuery, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.select(sqlQuery, params));
        for (SanPham sp : list) {
            model.addRow(sp.getSanPham());
        }
        selectedRow.put(str, list.size() - 1);
    }

    public void showDetailsSP() {
        SanPham sp = listSP.get(selectedRow.get("SP"));
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
//        cboSuggestionStatus.setSelectedItem(sp.getTrangThai() == TrangThai.KinhDoanh ? "Kinh Doanh" : "Ngừng Kinh Doanh");
        txtMoTa.setText(sp.getMoTa());
        tblInfoProducts.setRowSelectionInterval(selectedRow.get("SP"), selectedRow.get("SP"));
    }

    public SanPham readForm() {
        return new SanPham(
                txtMaSP.getText(), txtTenSP.getText(), txtMoTa.getText()
        );
    }

    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    private boolean checkNull(TextField... textFields) {
        boolean allValid = true;

        for (TextField textField : textFields) {
            if (isTextFieldEmpty(textField)) {
                JOptionPane.showMessageDialog(this, "Không để trống trường này", "", JOptionPane.WARNING_MESSAGE);
                textField.requestFocus();
                allValid = false;
                break;
            }
        }

        return allValid;
    }

    // Search
    public void search(String text) {
        String patternString = "SP\\d+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            int id = 0;
            for (SanPham sp : listSP) {
                if (sp.getMaSP() == text) {
                    selectedRow.put("SP", id);
                    showDetailsSP();
                    break;
                }
                id++;
            }
        } else {
            int id = 0;
            for (SanPham sp : listSP) {
                if (sp.getTenSP() == text) {
                    selectedRow.put("SP", id);
                    showDetailsSP();
                    break;
                }
                id++;
            }

        }
    }

    private List<DataSearch> rcdSearch(String search) {
        int limitData = 7;
        List<DataSearch> list = new ArrayList<>();
        for (SanPham sp : listSP) {
            if (sp.getTenSP().toLowerCase().contains(search)) {
                list.add(new DataSearch(sp.getTenSP()));
                if (list.size() == limitData) {
                    break;
                }
            }
            if (sp.getMaSP().toLowerCase().contains(search)) {
                list.add(new DataSearch(sp.getMaSP()));
                if (list.size() == limitData) {
                    break;
                }
            }
        }
        return list;
    }

    //Seacrh ChiTietSP
    public void searchDetails(String text) {
        String patternString = "SPCT\\d+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            int id = 0;
            for (ChiTietSP sp : listCTSP) {
                if (sp.getMaCTSP().equals(text)) {
                    selectedRow.put("SPCT", id);
                    showDetailsCTSP(selectedRow.get("SPCT"));
                    break;
                }
                id++;
            }
        } else {
            int id = 0;
            for (ChiTietSP sp : listCTSP) {
                if (sp.getTenSPCT().equals(text)) {
                    selectedRow.put("SPCT", id);
                    showDetailsCTSP(selectedRow.get("SPCT"));
                    break;
                }
                id++;
            }

        }
    }

    private List<DataSearch> rcdSearchDetails(String search) {
        int limitData = 7;
        List<DataSearch> list = new ArrayList<>();
        for (ChiTietSP sp : listCTSP) {
            if (sp.getTenSP().toLowerCase().contains(search)) {
                list.add(new DataSearch(sp.getTenSPCT()));
                if (list.size() == limitData) {
                    break;
                }
            }
            if (sp.getMaCTSP().toLowerCase().contains(search)) {
                list.add(new DataSearch(sp.getMaCTSP()));
                if (list.size() == limitData) {
                    break;
                }
            }
        }
        return list;
    }

    private boolean isFileOpen(File file) {
        try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.WRITE)) {
            // Nếu mở file thành công, có nghĩa là file không bị khóa bởi ứng dụng khác
            return false;
        } catch (IOException e) {
            // Nếu gặp IOException, có thể là file đang được sử dụng bởi ứng dụng khác
            return true;
        }
    }

    private void exportedType(DefaultTableModel modelTb, String filePath, String fileType) {
        switch (fileType) {
            case "pdf":
                exported.exportPdf(modelTb, filePath);
                break;
            case "xlsx":
                exported.exportExcel(modelTb, filePath);
                break;
            default:
                throw new IllegalArgumentException("Unsupported exported type: " + fileType);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Mau Sac
    public void initFormMS() {
        tblMauSac.setModel(modelMauSac);
        loadTableMauSac(modelMauSac, listMS, MauRepo, "select ID, MaMauSac, TenMauSac from MauSac");
        if (selectedRow.get("MauSac") >= 0) {
            showDetailsMauSac();
        }
    }

    public void loadTableMauSac(DefaultTableModel model, List<MauSac> list, MauSacRepo repo, String sqlQuery, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.select(sqlQuery, params));
        for (MauSac ms : list) {
            model.addRow(new Object[]{ms.getMaMauSac(), ms.getTenMauSac()});
        }
        if (!list.isEmpty()) {
            selectedRow.put("MauSac", -1);
        }
    }

    public void showDetailsMauSac() {
        int index = selectedRow.get("MauSac");
        if (index >= 0 && index < listMS.size()) {
            MauSac ms = listMS.get(index);

            txtMaMauSac.setText(ms.getMaMauSac());
            txtTenMauSac.setText(ms.getTenMauSac());
            originalMaMauSac = ms.getMaMauSac();
            tblMauSac.setRowSelectionInterval(index, index);
        }
    }

    public void addMauSac(MauSac mauSac) {
        int rowsInserted = MauRepo.addDoDay(mauSac);
        if (rowsInserted > 0) {
            listMS.add(mauSac);
            modelMauSac.addRow(new Object[]{mauSac.getMaMauSac(), mauSac.getTenMauSac()});
        }
    }

    //Form Do Day
    public void initFormDD() {
        tblDoDay.setModel(modelDoDay);
        loadTableDoDay(modelDoDay, listDD, doDayRepo, "select ID, MaDoDay, TenDoDay from DoDay");
        if (selectedRow.get("DoDay") >= 0) {
            showDetailsDoDay();
        }
    }

    public void loadTableDoDay(DefaultTableModel model, List<DoDay> list, DoDayRepo repo, String sql, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.select(sql, params));
        for (DoDay dd : list) {
            model.addRow(new Object[]{dd.getMaDoDay(), dd.getTenDoDay()});
        }
        if (!list.isEmpty()) {
            selectedRow.put("DoDay", -1);
        }
    }

    public void showDetailsDoDay() {
        int index = selectedRow.get("DoDay");
        if (index >= 0 && index < listDD.size()) {
            DoDay dd = listDD.get(index);

            txtMaDoDay.setText(dd.getMaDoDay());
            txtTenDoDay.setText(dd.getTenDoDay());
            originalMaDoDay = dd.getMaDoDay();
            tblDoDay.setRowSelectionInterval(index, index);
        }
    }

    public void addDoDay(DoDay doDay) {
        int rowsInserted = doDayRepo.add(doDay);
        if (rowsInserted > 0) {
            listDD.add(doDay);
            modelDoDay.addRow(new Object[]{doDay.getMaDoDay(), doDay.getTenDoDay()});
        }
    }
//Form Size

    public void initFormSize() {
        tblSize.setModel(modelSize);
        loadTableSize(modelSize, listSize, sizeRepo, "select ID, MaSize, TenSize from Size");
        if (selectedRow.get("Size") >= 0) {
            showDetailsSize();
        }
    }

    public void loadTableSize(DefaultTableModel model, List<Size> list, SizeRepo repo, String sql, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.selectAllSize(sql, params));
        for (Size s : list) {
            model.addRow(new Object[]{s.getMaSize(), s.getTenSize()});
        }
        if (!list.isEmpty()) {
            selectedRow.put("Size", -1);
        }
    }

    public void showDetailsSize() {
        int index = selectedRow.get("Size");
        if (index >= 0 && index < listSize.size()) {
            Size s = listSize.get(index);
            txtMaSize.setText(s.getMaSize());
            txtTenSize.setText(s.getTenSize());
            originalMaSize = s.getMaSize();
            tblSize.setRowSelectionInterval(index, index);
        }
    }

    public void addSize(Size size) {
        int rowsInserted = sizeRepo.add(size);
        if (rowsInserted > 0) {
            listSize.add(size);
            modelSize.addRow(new Object[]{size.getMaSize(), size.getTenSize()});
        }
    }
//Form Chat Lieu

    public void initFormCL() {
        tblChatLieu.setModel(modelChatLieu);
        loadTableChatLieu(modelChatLieu, listChatLieu, chatLieuRepo, "select ID, MaChatLieu, TenChatLieu from ChatLieu");
        if (selectedRow.get("ChatLieu") >= 0) {
            showDetailsChatLieu();
        }
    }

    public void loadTableChatLieu(DefaultTableModel model, List<ChatLieu> list, ChatLieuRepo repo, String sqlQuery, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.selectAll(sqlQuery, params));
        for (ChatLieu cl : list) {
            model.addRow(new Object[]{cl.getMaChatLieu(), cl.getTenChatLieu()});
        }
        if (!list.isEmpty()) {
            selectedRow.put("ChatLieu", -1);
        }
    }

    public void showDetailsChatLieu() {
        int index = selectedRow.get("ChatLieu");
        if (index >= 0 && index < listChatLieu.size()) {
            ChatLieu cl = listChatLieu.get(index);

            txtMaChatLieu.setText(cl.getMaChatLieu());
            txtTenChatLieu.setText(cl.getTenChatLieu());
            originalMaChatLieu = cl.getMaChatLieu();
            tblChatLieu.setRowSelectionInterval(index, index);
        }
    }

    public void addChatLieu(ChatLieu chatLieu) {
        int rowsInserted = chatLieuRepo.add(chatLieu);
        if (rowsInserted > 0) {
            listChatLieu.add(chatLieu);
            modelChatLieu.addRow(new Object[]{chatLieu.getMaChatLieu(), chatLieu.getTenChatLieu()});
        }
    }
//Form Nha cung cap

    public void initFormNhaCungCap() {
        tblNhaCc.setModel(modelNhaCungCap);
        loadTableNhaCungCap(modelNhaCungCap, listNhaCungCap, nhaCungCapRepo, "select ID, MaNhaCungCap, TenNhaCungCap, DiaChi, LienHe from NhaCungCap");
        if (selectedRow.get("NhaCungCap") >= 0) {
            showDetailsNhaCungCap();
        }
    }

    public void loadTableNhaCungCap(DefaultTableModel model, List<NhaCungCap> list, NhaCungCapRepo repo, String sqlQuery, Object... params) {
        model.setRowCount(0);
        list.clear();
        list.addAll(repo.selectAll(sqlQuery, params));
        for (NhaCungCap ncc : list) {
            model.addRow(new Object[]{ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getDiaChi(), ncc.getLienHe()});
        }
        if (!list.isEmpty()) {
            selectedRow.put("NhaCungCap", -1);
        }
    }

    public void showDetailsNhaCungCap() {
        int index = selectedRow.get("NhaCungCap");
        if (index >= 0 && index < listNhaCungCap.size()) {
            NhaCungCap ncc = listNhaCungCap.get(index);

            txtMaNcc.setText(ncc.getMaNhaCungCap());
            txtTenNcc.setText(ncc.getTenNhaCungCap());
            txtDiaChi.setText(ncc.getDiaChi());
            txtLienHe.setText(ncc.getLienHe());
            originalMaNhaCungCap = ncc.getMaNhaCungCap();
            tblNhaCc.setRowSelectionInterval(index, index);
        }
    }

    public void addNhaCungCap(NhaCungCap nhaCungCap) {
        int rowsInserted = nhaCungCapRepo.add(nhaCungCap);
        if (rowsInserted > 0) {
            listNhaCungCap.add(nhaCungCap);
            modelNhaCungCap.addRow(new Object[]{nhaCungCap.getMaNhaCungCap(), nhaCungCap.getTenNhaCungCap(), nhaCungCap.getDiaChi(), nhaCungCap.getLienHe()});
        }
    }

    private void cboSuggestionStatusFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSuggestionStatusFilterActionPerformed

    }//GEN-LAST:event_cboSuggestionStatusFilterActionPerformed

    private void tblNhaCcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCcMouseClicked
        selectedRow.put("NhaCungCap", tblNhaCc.getSelectedRow());
        showDetailsNhaCungCap();
    }//GEN-LAST:event_tblNhaCcMouseClicked

    private void tblChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatLieuMouseClicked
        selectedRow.put("ChatLieu", tblChatLieu.getSelectedRow());
        showDetailsChatLieu();
    }//GEN-LAST:event_tblChatLieuMouseClicked

    private void tblSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSizeMouseClicked
        selectedRow.put("Size", tblSize.getSelectedRow());
        showDetailsSize();
    }//GEN-LAST:event_tblSizeMouseClicked

    private void panelTblDoDayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTblDoDayMouseClicked
        //ko dung
    }//GEN-LAST:event_panelTblDoDayMouseClicked

    private void tblDoDayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDoDayKeyPressed
        //ko dung
    }//GEN-LAST:event_tblDoDayKeyPressed

    private void tblDoDayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoDayMouseClicked
        selectedRow.put("DoDay", tblDoDay.getSelectedRow());
        showDetailsDoDay();
    }//GEN-LAST:event_tblDoDayMouseClicked

    private void tblMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMauSacMouseClicked
        selectedRow.put("MauSac", tblMauSac.getSelectedRow());
        showDetailsMauSac();
    }//GEN-LAST:event_tblMauSacMouseClicked

    private void btnNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhaCungCapActionPerformed
        panelTong.removeAll();
        panelTong.add(panelNhaCc);
        panelTong.repaint();
        panelTong.revalidate();

        panelTableTong.removeAll();
        panelTableTong.add(panelTblNhaCc);
        panelTableTong.repaint();
        panelTableTong.revalidate();
    }//GEN-LAST:event_btnNhaCungCapActionPerformed

    private void btnChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatLieuActionPerformed
        panelTong.removeAll();
        panelTong.add(panelChatLieu);
        panelTong.repaint();
        panelTong.revalidate();

        panelTableTong.removeAll();
        panelTableTong.add(panelTblChatLieu);
        panelTableTong.repaint();
        panelTableTong.revalidate();
    }//GEN-LAST:event_btnChatLieuActionPerformed

    private void btnSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSizeActionPerformed
        panelTong.removeAll();
        panelTong.add(panelSize);
        panelTong.repaint();
        panelTong.revalidate();

        panelTableTong.removeAll();
        panelTableTong.add(panelTblSize);
        panelTableTong.repaint();
        panelTableTong.revalidate();
    }//GEN-LAST:event_btnSizeActionPerformed

    private void btnDoDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoDayActionPerformed
        panelTong.removeAll();
        panelTong.add(panelDoDay);
        panelTong.repaint();
        panelTong.revalidate();

        panelTableTong.removeAll();
        panelTableTong.add(panelTblDoDay);
        panelTableTong.repaint();
        panelTableTong.revalidate();
    }//GEN-LAST:event_btnDoDayActionPerformed

    private void btnMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMauSacActionPerformed
        panelTong.removeAll();
        panelTong.add(panelMauSac);
        panelTong.repaint();
        panelTong.revalidate();

        panelTableTong.removeAll();
        panelTableTong.add(panelTblMau);
        panelTableTong.repaint();
        panelTableTong.revalidate();
    }//GEN-LAST:event_btnMauSacActionPerformed

    private void btnClearNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearNCCActionPerformed
        String manew = nhaCungCapRepo.generateMaNhaCungCap();
        txtMaNcc.setText(manew);
        txtTenNcc.setText("");
        txtDiaChi.setText("");
        txtLienHe.setText("");
        tblNhaCc.clearSelection();
    }//GEN-LAST:event_btnClearNCCActionPerformed

    private void btnSuaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNCCActionPerformed
        int selectedRowIndex = tblNhaCc.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!");
            return;
        }

        String ma = txtMaNcc.getText();
        String ten = txtTenNcc.getText();
        String DiaChi = txtDiaChi.getText();
        String LienHe = txtLienHe.getText();

        if (ma.isEmpty() || ten.isEmpty() || DiaChi.isEmpty() || LienHe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để sửa!");
            return;
        }
        for (NhaCungCap nhaCc : listNhaCungCap) {
            if (nhaCc.getTenNhaCungCap().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên nhà cung cấp đã tồn tại");
                return;
            }
            if (nhaCc.getLienHe().equals(LienHe)) {
                JOptionPane.showMessageDialog(this, "Liên hệ đã tồn tại");
                return;
            }
        }
        
        NhaCungCap ncc = listNhaCungCap.get(selectedRowIndex);
        ncc.setMaNhaCungCap(ma);
        ncc.setTenNhaCungCap(ten);
        ncc.setDiaChi(DiaChi);
        ncc.setLienHe(LienHe);

        int result = nhaCungCapRepo.update(ncc);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            modelNhaCungCap.setValueAt(ma, selectedRowIndex, 0);
            modelNhaCungCap.setValueAt(ten, selectedRowIndex, 1);
            modelNhaCungCap.setValueAt(DiaChi, selectedRowIndex, 2);
            modelNhaCungCap.setValueAt(LienHe, selectedRowIndex, 3);
            
            String manew = nhaCungCapRepo.generateMaNhaCungCap();
            txtMaNcc.setText(manew);
            txtTenNcc.setText("");
            txtDiaChi.setText("");
            txtLienHe.setText("");
            tblNhaCc.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaNCCActionPerformed

    private void btnThemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNCCActionPerformed
        String ma = txtMaNcc.getText();
        String ten = txtTenNcc.getText();
        String DiaChi = txtDiaChi.getText();
        String LienHe = txtLienHe.getText();
        NhaCungCap Ncc = new NhaCungCap(null, ma, ten, DiaChi, LienHe);
        if (ma.isEmpty() || ten.isEmpty() || DiaChi.isEmpty() || LienHe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        for (NhaCungCap nhaCc : listNhaCungCap) {
            if (nhaCc.getTenNhaCungCap().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên nhà cung cấp đã tồn tại");
                return;
            }
            if (nhaCc.getLienHe().equals(LienHe)) {
                JOptionPane.showMessageDialog(this, "Liên hệ đã tồn tại");
                return;
            }
        }
        addNhaCungCap(Ncc);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        initFormSPCT();
        
        String manew = nhaCungCapRepo.generateMaNhaCungCap();
        txtMaNcc.setText(manew);
        txtTenNcc.setText("");
        txtDiaChi.setText("");
        txtLienHe.setText("");
        tblNhaCc.clearSelection();
    }//GEN-LAST:event_btnThemNCCActionPerformed

    private void btnClearChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearChatLieuActionPerformed
        String manew = chatLieuRepo.generateMaChatLieu();
        txtMaChatLieu.setText(manew);
        txtTenChatLieu.setText("");
        tblChatLieu.clearSelection();
    }//GEN-LAST:event_btnClearChatLieuActionPerformed

    private void btnSuaChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaChatLieuActionPerformed
        int selectedRowIndex = tblChatLieu.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!");
            return;
        }

        String ma = txtMaChatLieu.getText();
        String ten = txtTenChatLieu.getText();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để sửa!");
            return;
        }
        for (ChatLieu chatLieu : listChatLieu) {
            if (chatLieu.getTenChatLieu().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên chất liệu đã tồn tại");
                return;
            }
        }

        ChatLieu cl = listChatLieu.get(selectedRowIndex);
        cl.setMaChatLieu(ma);
        cl.setTenChatLieu(ten);

        int result = chatLieuRepo.update(cl);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            modelChatLieu.setValueAt(ma, selectedRowIndex, 0);
            modelChatLieu.setValueAt(ten, selectedRowIndex, 1);
            
            String manew = chatLieuRepo.generateMaChatLieu();
            txtMaChatLieu.setText(manew);
            txtTenChatLieu.setText("");
            tblChatLieu.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaChatLieuActionPerformed

    private void btnThemChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChatLieuActionPerformed
        String ma = txtMaChatLieu.getText();
        String ten = txtTenChatLieu.getText();
        ChatLieu cl = new ChatLieu(null, ma, ten);
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        for (ChatLieu chatLieu : listChatLieu) {
            if (chatLieu.getTenChatLieu().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên chất liệu đã tồn tại");
                return;
            }
        }
        addChatLieu(cl);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        initFormSPCT();
        
        String manew = chatLieuRepo.generateMaChatLieu();
        txtMaChatLieu.setText(manew);
        txtTenChatLieu.setText("");
        tblChatLieu.clearSelection();
    }//GEN-LAST:event_btnThemChatLieuActionPerformed

    private void btnClearSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSizeActionPerformed
        String manew = sizeRepo.generateMaSize();
        txtMaSize.setText(manew);
        txtTenSize.setText("");
        tblSize.clearSelection();
    }//GEN-LAST:event_btnClearSizeActionPerformed

    private void btnSuaSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSizeActionPerformed
        int selectedRowIndex = tblSize.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!");
            return;
        }
        String ma = txtMaSize.getText();
        String ten = txtTenSize.getText();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để sửa!");
            return;
        }
        
        for (Size size : listSize) {
            if (size.getTenSize().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên size đã tồn tại");
                return;
            }
        }
        Size s = listSize.get(selectedRowIndex);
        s.setMaSize(ma);
        s.setTenSize(ten);

        int result = sizeRepo.update(s);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            modelSize.setValueAt(ma, selectedRowIndex, 0);
            modelSize.setValueAt(ten, selectedRowIndex, 1);
            
            String manew = sizeRepo.generateMaSize();
            txtMaSize.setText(manew);
            txtTenSize.setText("");
            tblSize.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaSizeActionPerformed

    private void btnThemSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSizeActionPerformed
        String ma = txtMaSize.getText();
        String ten = txtTenSize.getText();
        Size s = new Size(null, ma, ten);
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        for (Size size : listSize) {
            if (size.getTenSize().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên size đã tồn tại");
                return;
            }
        }
        addSize(s);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        initFormSPCT();
        String manew = sizeRepo.generateMaSize();
        txtMaSize.setText(manew);
        txtTenSize.setText("");
        tblSize.clearSelection();
    }//GEN-LAST:event_btnThemSizeActionPerformed

    private void btnSuaDoDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDoDayActionPerformed
        int selectedRowIndex = tblDoDay.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!");
            return;
        }

        String maDoDay = txtMaDoDay.getText();
        String tenDoDay = txtTenDoDay.getText();

        if (maDoDay.isEmpty() || tenDoDay.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để sửa!");
            return;
        }
        for (DoDay doDay : listDD) {
            if (doDay.getTenDoDay().equals(tenDoDay)) {
                JOptionPane.showMessageDialog(this, "Tên độ dày đã tồn tại");
                return;
            }
        }
        DoDay dd = listDD.get(selectedRowIndex);
        dd.setMaDoDay(maDoDay);
        dd.setTenDoDay(tenDoDay);

        int result = doDayRepo.update(dd);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            modelDoDay.setValueAt(maDoDay, selectedRowIndex, 0);
            modelDoDay.setValueAt(tenDoDay, selectedRowIndex, 1);
            
            String manew = doDayRepo.generateMaDoDay();
            txtMaDoDay.setText(manew);
            txtTenDoDay.setText("");
            tblDoDay.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaDoDayActionPerformed

    private void ClearDoDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearDoDayActionPerformed
        String manew = doDayRepo.generateMaDoDay();
        txtMaDoDay.setText(manew);
        txtTenDoDay.setText("");
        tblDoDay.clearSelection();
    }//GEN-LAST:event_ClearDoDayActionPerformed

    private void btnThemDoDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDoDayActionPerformed
        String ma = txtMaDoDay.getText();
        String ten = txtTenDoDay.getText();
        DoDay dd = new DoDay(null, ma, ten);

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        for (DoDay doDay : listDD) {
            if (doDay.getMaDoDay().equals(ma)) {
                JOptionPane.showMessageDialog(this, "Mã độ dày đã tồn tại");
                return;
            }
        }
        
        for (DoDay doDay : listDD) {
            if (doDay.getTenDoDay().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên độ dày đã tồn tại");
                return;
            }
        }
        addDoDay(dd);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        initFormSPCT();
        String manew = doDayRepo.generateMaDoDay();
        txtMaDoDay.setText(manew);
        txtTenDoDay.setText("");
        tblDoDay.clearSelection();
    }//GEN-LAST:event_btnThemDoDayActionPerformed

    private void btnClearMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearMauActionPerformed
        String ma = MauRepo.generateMaMau();
        txtMaMauSac.setEditable(false);
        txtMaMauSac.setText(ma);
        txtTenMauSac.setText("");
        tblMauSac.clearSelection();
    }//GEN-LAST:event_btnClearMauActionPerformed

    private void btnSuaMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMauActionPerformed
        int selectedRowIndex = tblMauSac.getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!");
            return;
        }

        String maMauSac = txtMaMauSac.getText();
        String tenMauSac = txtTenMauSac.getText();

        if (tenMauSac.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin để sửa!");
            return;
        }
        for (MauSac ms : MauRepo.getAll()) {
            if (ms.getTenMauSac().equals(tenMauSac) && !ms.getMaMauSac().equals(maMauSac)) {
                JOptionPane.showMessageDialog(this, "Tên màu sắc đã tồn tại");
                tblMauSac.clearSelection();
                return;
            }
        }
        MauSac ms = new MauSac(null, maMauSac, tenMauSac);

        int result = MauRepo.update(ms);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

            modelMauSac.setValueAt(maMauSac, selectedRowIndex, 0);
            modelMauSac.setValueAt(tenMauSac, selectedRowIndex, 1);

            String ma = MauRepo.generateMaMau();
            txtMaMauSac.setText(ma);
            txtTenMauSac.setText("");
            tblMauSac.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaMauActionPerformed

    private void btnThemMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMauActionPerformed
        String ma = MauRepo.generateMaMau();
        String ten = txtTenMauSac.getText();
        MauSac m = new MauSac(null, ma, ten);
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        for (MauSac ms : MauRepo.getAll()) {
            if (ms.getTenMauSac().equals(ten)) {
                JOptionPane.showMessageDialog(this, "Tên màu sắc đã tồn tại");
                return;
            }
        }
        if (MauRepo.addDoDay(m) > 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            initFormSPCT();
            String manew = MauRepo.generateMaMau();
            txtMaMauSac.setText(manew);
            txtTenMauSac.setText("");
            tblMauSac.clearSelection();
            initFormTTSP();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }//GEN-LAST:event_btnThemMauActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String text = txtSearch.getText().trim().toLowerCase();
        search.setData(rcdSearch(text));
        if (search.getItemSize() > 0) {
            menu.show(txtSearch, 0, txtSearch.getHeight());
            menu.setPopupSize(txtSearch.getWidth(), (search.getItemSize() * 35) + 2);
        } else {
            menu.setVisible(false);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        if (search.getItemSize() > 0) {
            menu.show(txtSearch, 0, txtSearch.getHeight());
        }
    }//GEN-LAST:event_txtSearchMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtSearch.setText("");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtMoTa.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnXoaMemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMemActionPerformed
        int id = listSP.get(selectedRow.get("SP")).getId();
        TrangThaiCRUD status = QLSP.update("update SanPham set hienthi = ? where id = ?", HienThi.An.name(), id);
        switch (status) {
            case ThanhCong:
                loadDataToTable(modelSP, listSP, "SP", QLSP, "select * from SanPham where hienthi = ?", HienThi.Hien.name());
                if (selectedRow.get("SP") >= 0) {
                    showDetailsSP();
                }
                break;
            case ThatBai:
                JOptionPane.showMessageDialog(this, "Xóa thất bại",  "", JOptionPane.ERROR_MESSAGE);
            default:
        }
    }//GEN-LAST:event_btnXoaMemActionPerformed


    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        materialTabbed.setSelectedIndex(1);
        listCTSP = chiTietSPService.getSPCT(listSP.get(tblInfoProducts.getSelectedRow()).getId());
        modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
        modelCTSP.setRowCount(0);
        for (ChiTietSP ctsp : listCTSP) {
            modelCTSP.addRow(new Object[]{
                ctsp.getId(),
                ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
            });
        }
    }//GEN-LAST:event_btnChiTietActionPerformed


    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkNull(txtMaSP, txtTenSP)) {
            SanPham sp = readForm();
            int id = listSP.get(selectedRow.get("SP")).getId();
            System.out.println(sp.getId());
            TrangThaiCRUD status = QLSP.update("update SanPham set MaSP = ?, TenSP = ?, MoTa = ? where id = ?",
                    sp.getMaSP(), sp.getTenSP(), sp.getMoTa(), id);
            switch (status) {
                case ThanhCong:
                    JOptionPane.showMessageDialog(this, "Sửa thành công",  "", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable(modelSP, listSP, "SP", QLSP, "select * from SanPham where hienthi = ?", HienThi.Hien.name());
                    if (selectedRow.get("SP") >= 0) {
                        showDetailsSP();
                    }
                    break;
                case ThatBai:
                    JOptionPane.showMessageDialog(this, "Sửa Thất Bại",  "", JOptionPane.ERROR_MESSAGE);
                    break;
                case DaTonTai:
                    JOptionPane.showMessageDialog(this, "Sửa Thất Bại\nTrùng sản phẩm",  "", JOptionPane.ERROR_MESSAGE);
                    break;
                default:

            }
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkNull(txtMaSP, txtTenSP)) {
            SanPham sp = readForm();
            TrangThaiCRUD status = QLSP.update("insert into SanPham(MaSp, TenSP, MoTa) values (?, ?, ?)", sp.getMaSP(), sp.getTenSP(), sp.getMoTa());
            switch (status) {
                case ThanhCong:
                    JOptionPane.showMessageDialog(this, "Thêm thành công",  "", JOptionPane.INFORMATION_MESSAGE);
                    loadDataToTable(modelSP, listSP, "SP", QLSP, "select * from SanPham where hienthi = ?", HienThi.Hien.name());
                    if (selectedRow.get("SP") >= 0) {
                        showDetailsSP();
                    }
                    break;
                case ThatBai:
                    JOptionPane.showMessageDialog(this, "Thêm Thất Bại",  "", JOptionPane.ERROR_MESSAGE);
                    break;
                case DaTonTai:
                    JOptionPane.showMessageDialog(this, "Thêm Thất Bại\nTrùng sản phẩm",  "", JOptionPane.ERROR_MESSAGE);
                    break;
                default:

            }
        }
        initFormSPCT();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblInfoProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInfoProductsMouseClicked
        selectedRow.put("SP", tblInfoProducts.getSelectedRow());
        showDetailsSP();
    }//GEN-LAST:event_tblInfoProductsMouseClicked

    private void btnXoaMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMauActionPerformed
        int row = this.tblMauSac.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
            return;
        }
        MauSac ms = this.MauRepo.getAll().get(row);
        if (ms == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy màu sắc để xóa");
            return;
        }
        if (MauRepo.deleteByMaMauSac(ms.getMaMauSac()) > 0) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            initFormTTSP();
            String manew = MauRepo.generateMaMau();
            txtMaMauSac.setText(manew);
            txtTenMauSac.setText("");
            tblMauSac.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaMauActionPerformed

    private void btnXoaDoDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDoDayActionPerformed
        int row = this.tblDoDay.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
            return;
        }
        DoDay dd = this.doDayRepo.getAll().get(row);
        if (dd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy độ dày để xóa");
            return;
        }
        if (doDayRepo.deleteByMa(dd.getMaDoDay()) > 0) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            initFormTTSP();
            String manew = doDayRepo.generateMaDoDay();
            txtMaDoDay.setText(manew);
            txtTenDoDay.setText("");
            tblDoDay.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaDoDayActionPerformed

    private void btnXoaSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSizeActionPerformed
        int row = this.tblSize.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
            return;
        }
        Size s = this.sizeRepo.getAll().get(row);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy size để xóa");
            return;
        }
        if (sizeRepo.deleteByMa(s.getMaSize()) > 0) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            initFormTTSP();
            String manew = sizeRepo.generateMaSize();
            txtMaSize.setText(manew);
            txtTenSize.setText("");
            tblSize.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaSizeActionPerformed

    private void btnXoaChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChatLieuActionPerformed
        int row = this.tblChatLieu.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
                return;
            }
            ChatLieu cl = this.chatLieuRepo.getAll().get(row);
            if (cl == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chất liệu để xóa");
                return;
            }
            if (chatLieuRepo.deleteByMa(cl.getMaChatLieu()) > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                initFormTTSP();
                String manew = chatLieuRepo.generateMaChatLieu();
                txtMaChatLieu.setText(manew);
                txtTenChatLieu.setText("");
                tblChatLieu.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
    }//GEN-LAST:event_btnXoaChatLieuActionPerformed

    private void btnXoaNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNhaCungCapActionPerformed
        int row = this.tblNhaCc.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
                return;
            }
            NhaCungCap ncc = this.nhaCungCapRepo.getAll().get(row);
            if (ncc == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp để xóa");
                return;
            }
            if (nhaCungCapRepo.deleteByMa(ncc.getMaNhaCungCap()) > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                initFormTTSP();
                String manew = nhaCungCapRepo.generateMaNhaCungCap();
                txtMaNcc.setText(manew);
                txtTenNcc.setText("");
                txtDiaChi.setText("");
                txtLienHe.setText("");
                tblNhaCc.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
    }//GEN-LAST:event_btnXoaNhaCungCapActionPerformed

    private void tblDetailsProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailsProductsMouseClicked
        // TODO add your handling code here:
        int row = tblDetailsProducts.getSelectedRow();
        showDetailsCTSP(row);
    }//GEN-LAST:event_tblDetailsProductsMouseClicked

    private void scrollDetailsProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollDetailsProductsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollDetailsProductsMouseClicked

    private void btnThemDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDetailsActionPerformed
        {

            Boolean check = checkCTSP();
            if (check) {

                int coHoi = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không?", "", JOptionPane.YES_NO_OPTION);
                if (coHoi == JOptionPane.YES_OPTION) {
                    SPCT spct = getCTSP();
                    chiTietSPService.addCTSP1(spct);
                    JOptionPane.showMessageDialog(this, "Thêm thành công", "", JOptionPane.INFORMATION_MESSAGE);
                    //MessageAlerts.getInstance().showMessage("Dữ liệu không được bỏ trống", "", MessageAlerts.MessageType.ERROR);
                    loadDataCTSP();
                    txt_MSPCT.setText("");
                    txt_Soluong.setText("");
                    txt_GiaBan.setText("");
                    txt_GiaNhap.setText("");
                    txt_TenSPCT.setText("");
                    cbb_searchGN.setSelectedIndex(-1);
                    cbb_searchGB.setSelectedIndex(-1);
                    cbb_TT_Fil.setSelectedIndex(-1);
                } else if (coHoi == JOptionPane.NO_OPTION) {
                    //                    MessageAlerts.getInstance().showMessage("Thêm thất bại", "", MessageAlerts.MessageType.ERROR);
                    JOptionPane.showMessageDialog(this, "Thêm thất bại", "", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //                MessageAlerts.getInstance().showMessage("Vui lòng xem lại dữ liệu", "", MessageAlerts.MessageType.WARNING);
            }
        }
        initFormSPCT();
    }//GEN-LAST:event_btnThemDetailsActionPerformed

    private void btnSuaDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDetailsActionPerformed
        if (txt_MSPCT.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không được bỏ trống", "", JOptionPane.WARNING_MESSAGE);
        } else {
            Boolean check = checkCTSP();
            if (check) {
                int coHoi = JOptionPane.showConfirmDialog(this, "Bạn có muốn Sửa không?", "", JOptionPane.YES_NO_OPTION);
                if (coHoi == JOptionPane.YES_OPTION) {
                    int row = tblDetailsProducts.getSelectedRow();
                    String maCTSP = txt_MSPCT.getText();
                    System.out.println("maSPCT Sua : " + maCTSP);
                    SPCT spct = getCTSP();
                    chiTietSPService.Update(spct, maCTSP);
                    txt_MSPCT.setText("");
                    txt_Soluong.setText("");
                    txt_GiaBan.setText("");
                    txt_GiaNhap.setText("");
                    txt_TenSPCT.setText("");
                    cbb_searchGN.setSelectedIndex(-1);
                    cbb_searchGB.setSelectedIndex(-1);
                    cbb_TT_Fil.setSelectedIndex(-1);
                    loadDataCTSP();
                    JOptionPane.showMessageDialog(this, "Sửa Thành Công", "", JOptionPane.INFORMATION_MESSAGE);
                } else if (coHoi == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại", "", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //                MessageAlerts.getInstance().showMessage("Vui lòng xem lại đữ liệu", "", MessageAlerts.MessageType.WARNING);
            }
        }
        initFormSPCT();
    }//GEN-LAST:event_btnSuaDetailsActionPerformed

    private void btnClearDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearDetailsActionPerformed
        txt_MSPCT.setText("");
        txt_Soluong.setText("");
        txt_GiaBan.setText("");
        txt_GiaNhap.setText("");
        txt_TenSPCT.setText("");
        cbb_ChatLieu.setSelectedIndex(-1);
        cbb_DoDay.setSelectedIndex(-1);
        cbb_MauSac.setSelectedIndex(-1);
        cbb_NCC.setSelectedIndex(-1);
        cbb_SP.setSelectedIndex(-1);
        cbb_Size.setSelectedIndex(-1);
        cbb_searchGN.setSelectedIndex(-1);
        cbb_searchGB.setSelectedIndex(-1);
        cbb_TT_Fil.setSelectedIndex(-1);
    }//GEN-LAST:event_btnClearDetailsActionPerformed

    private void btnImportDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportDetailsActionPerformed
        imported = new Import();
        final String[] fileType = new String[1];
        final String[] filePath = new String[1];

        JnaFileChooser fileChooser = new JnaFileChooser();
        fileChooser.setMode(JnaFileChooser.Mode.Files);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.addFilter("Excel File", "xlsx", "xls");
        File selectedFile = null;
        if (fileChooser.showOpenDialog(frame)) {
            selectedFile = fileChooser.getSelectedFile();
            filePath[0] = fileChooser.getSelectedFile().getAbsolutePath();
        }
        try {
            popupReviewDataImport = new Popup_ReviewDataImport(frame, true, imported.importData(filePath[0]));
            popupReviewDataImport.setLocationRelativeTo(frame);
            popupReviewDataImport.setVisible(true);
            switch ((popupReviewDataImport.getUpdateTbl())) {
                case ThanhCong:
                JOptionPane.showMessageDialog(this, "Thêm tất cả thành công", "", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable(modelSPCT, listSP, "SP", QLSP, "select * from SanPham where hienthi = ?", HienThi.Hien.name());
                if (selectedRow.get("SP") >= 0) {
                    showDetailsSP();
                }
                break;
                case ThatBai:
                JOptionPane.showMessageDialog(this, "Thêm Thất Bại", "", JOptionPane.ERROR_MESSAGE);
                case DaTonTai:
                JOptionPane.showMessageDialog(this, "Thêm Thất Bại\nTrùng sản phẩm", "", JOptionPane.ERROR_MESSAGE);
                default:
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportDetailsActionPerformed

    private void btnExportDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDetailsActionPerformed
        exported = new Export();
        final String[] fileType = new String[1];
        final String[] filePath = new String[1];
        String defaultFileName = "documentSPCT.";
        chooserChoice = new Form_ChooserChoice(frame, true);
        chooserChoice.setLocationRelativeTo(frame);
        chooserChoice.setVisible(true);
        fileType[0] = chooserChoice.getFileType();

        if (fileType[0] != null) {
            JnaFileChooser fileChooser = new JnaFileChooser();
            fileChooser.setMode(JnaFileChooser.Mode.Files);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setDefaultFileName(defaultFileName + fileType[0]);
            fileChooser.addFilter(fileType[0] + "File", fileType[0]);

            if (fileChooser.showSaveDialog(frame)) {
                filePath[0] = fileChooser.getSelectedFile().getAbsolutePath();
            }
            try {
                File file = new File(filePath[0]);
                if (file.exists()) {
                    //                    MessageAlerts.getInstance().showMessage("File đã tồn tại", "Oops! Bạn có muốn ghi đè lên file hiện tại?", MessageAlerts.MessageType.ERROR, MessageAlerts.OK_OPTION, new PopupCallbackAction() {
                        //                        @Override
                        //                        public void action(PopupController pc, int i) {
                            //                            if (i == MessageAlerts.OK_OPTION) {
                                //                                if (isFileOpen(file)) {
                                    //                                    MessageAlerts.getInstance().showMessage("File đang được mở", "Vui lòng đóng file !!", MessageAlerts.MessageType.ERROR);
                                    //                                } else {
                                    //                                    exportedType(modelSPCT, filePath[0], fileType[0]);
                                    //                                    MessageAlerts.getInstance().showMessage("Export thành công", "", MessageAlerts.MessageType.SUCCESS);
                                    //                                }
                                //                            }
                            //                        }
                        //                    });
                if (JOptionPane.showConfirmDialog(this, "File đã tồn tại !\nBạn có muốn ghi đè lên file hiện tại?", "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    if (isFileOpen(file)) {
                        JOptionPane.showMessageDialog(this, "File đang được mở\nVui lòng đóng file !!", "", JOptionPane.ERROR_MESSAGE);
                    } else {
                        exportedType(modelSPCT, filePath[0], fileType[0]);
                        JOptionPane.showMessageDialog(this, "Export thành công", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else {
                exportedType(modelSPCT, filePath[0], fileType[0]);
                JOptionPane.showMessageDialog(this, "Export thành công", "", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }//GEN-LAST:event_btnExportDetailsActionPerformed

    private void btnXoaDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDetailsActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Xóa sản phẩm này ?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int id = listCTSP.get(tblDetailsProducts.getSelectedRow()).getId();
            TrangThaiCRUD status = QLSP.update("update SanPhamChiTiet set hienthi = ? where id = ?", HienThi.An.name(), id);

            switch (status) {
                case ThanhCong:
                loadDataCTSP();
                cbb_searchGN.setSelectedIndex(-1);
                cbb_searchGB.setSelectedIndex(-1);
                cbb_TT_Fil.setSelectedIndex(-1);
                break;
                case ThatBai:
                JOptionPane.showMessageDialog(this, "Xóa Thất Bại", "", JOptionPane.ERROR_MESSAGE);
                cbb_searchGN.setSelectedIndex(-1);
                cbb_searchGB.setSelectedIndex(-1);
                cbb_TT_Fil.setSelectedIndex(-1);
                default:
            }
        }
    }//GEN-LAST:event_btnXoaDetailsActionPerformed

    private void cbb_SP_FilterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbb_SP_FilterFocusGained
        // TODO add your handling code here:
        String spTimKiem = String.valueOf(cbb_SP_Filter.getSelectedIndex());
        if (spTimKiem.equals("Tìm kiếm sản phẩm theo mã sản phẩm , tên ....")) {
            cbb_SP_Filter.getSelectedIndex(null);
            cbb_SP_Filter.requestFocus();
            //Yêu cầu tập trung Focus vào 1 component nào đó.
            //remove placeholder style
            //            removePlaceholderStyle(cbb_SP);
        }
    }//GEN-LAST:event_cbb_SP_FilterFocusGained

    private void cbb_SP_FilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbb_SP_FilterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_SP_FilterMouseClicked

    private void cbb_SP_FilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_SP_FilterActionPerformed
        // TODO add your handling code here:
        String cbbTimKiem = String.valueOf(cbb_SP_Filter.getSelectedItem());
        if (cbbTimKiem.isEmpty()) {
            loadDataCTSP();
        } else {
            System.out.println("Lua chọn cua ban : " + cbbTimKiem);
            ArrayList<ChiTietSP> listCTSP = (ArrayList<ChiTietSP>) chiTietSPService.timKiemSp(cbbTimKiem);
            modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
            modelCTSP.setRowCount(0);
            for (ChiTietSP ctsp : listCTSP) {
                System.out.println("NCC : " + ctsp.getTenNhaCungCap());
                modelCTSP.addRow(new Object[]{
                    ctsp.getId(),
                    ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                    ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
                });
            }
        }
    }//GEN-LAST:event_cbb_SP_FilterActionPerformed

    private void txtSearchDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchDetailsMouseClicked
        if (searchDetails.getItemSize() > 0) {
            menuDetails.show(txtSearchDetails, 0, txtSearchDetails.getHeight());
        }
    }//GEN-LAST:event_txtSearchDetailsMouseClicked

    private void txtSearchDetailsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDetailsKeyReleased
        String text = txtSearchDetails.getText().trim().toLowerCase();
        searchDetails.setData(rcdSearchDetails(text));
        if (searchDetails.getItemSize() > 0) {
            menuDetails.show(txtSearchDetails, 0, txtSearchDetails.getHeight());
            menuDetails.setPopupSize(txtSearchDetails.getWidth(), (searchDetails.getItemSize() * 35) + 2);
        } else {
            menuDetails.setVisible(false);
        }
    }//GEN-LAST:event_txtSearchDetailsKeyReleased

    private void btnClearFiltersDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFiltersDetailsActionPerformed
        cbb_SP_Filter.setSelectedIndex(-1);
        cbb_TT_Fil.setSelectedIndex(-1);
        txtSearchDetails.setText("");
        cbb_searchGN.setSelectedIndex(-1);
        cbb_searchGB.setSelectedIndex(-1);
        loadDataCTSP();
    }//GEN-LAST:event_btnClearFiltersDetailsActionPerformed

    private void cbb_searchGNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_searchGNActionPerformed
        // TODO add your handling code here:
        String cbbGiaNhap = String.valueOf(cbb_searchGN.getSelectedItem()).trim();
        BigDecimal giaN = null;
        List<ChiTietSP> chiTietList = chiTietSPService.Get_GiaNhap();
        System.out.println("GiaNhapcbb" + cbbGiaNhap);
        if (cbbGiaNhap == null || cbbGiaNhap.isEmpty()) {
            loadDataCTSP();
        } else {
            // Find the maximum selling price
            for (ChiTietSP chiTietSP : chiTietList) {
                if (giaN == null || chiTietSP.getGiaNhap().compareTo(giaN) > 0) {
                    giaN = chiTietSP.getGiaBan();
                }
            }

            // Default price range
            int GiaNhapMin = 1000;
            int GiaNhapMax = 1000;
            BigDecimal MinGN = new BigDecimal(GiaNhapMin);
            BigDecimal MaxGN = new BigDecimal(GiaNhapMax);

            // Print the selected combo box value for debugging
            System.out.println("Selected combo box value: " + cbbGiaNhap);

            switch (cbbGiaNhap) {
                case "Dưới 500.000đ" -> {
                    System.out.println("Gia Nhap Min Duoi 500: 0");
                    GiaNhapMin = 0;
                    GiaNhapMax = 500000;
                }
                case "500.000 - 1 triệu" -> {
                    GiaNhapMin = 500000;
                    GiaNhapMax = 1000000;
                }
                case "1 - 2 triệu" -> {
                    GiaNhapMin = 1000000;
                    GiaNhapMax = 2000000;
                }
                case "Giá trên 3 triệu" -> {
                    GiaNhapMin = 3000000;
                    GiaNhapMax = giaN != null ? giaN.intValue() : 3000000;
                }
                default -> {
                    System.err.println("Unrecognized price range: " + cbbGiaNhap);
                }
            }

            // Convert updated int values to BigDecimal
            MinGN = BigDecimal.valueOf(GiaNhapMin);
            MaxGN = BigDecimal.valueOf(GiaNhapMax);

            // Print the results for verification
            System.out.println("GiaNhapMin: " + GiaNhapMin);
            System.out.println("GiaNhapMax: " + GiaNhapMax);
            System.out.println("MinGN: " + MinGN);
            System.out.println("MaxGN: " + MaxGN);
            System.out.println("Gia Max: " + giaN);

            List<ChiTietSP> list = chiTietSPService.timKiemGiaNhap(MinGN, MaxGN);

            // Check the returned list for debugging
            System.out.println("Returned list size: " + list.size());
            for (ChiTietSP ctsp : list) {
                System.out.println("Product ID: " + ctsp.getId() + ", GiaNhap: " + ctsp.getGiaNhap());
            }

            modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
            modelCTSP.setRowCount(0);
            for (ChiTietSP ctsp : list) {
                modelCTSP.addRow(new Object[]{
                    ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                    ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
                });
            }
        }

        //        Dưới 500.000đ
        //500.0000 - 1 triệu
        //1 - 2 triệu
        //Giá trên 3 triệu
    }//GEN-LAST:event_cbb_searchGNActionPerformed

    private void cbb_searchGBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_searchGBActionPerformed
        // TODO add your handling code here:

        String cbbGiaNhap = String.valueOf(cbb_searchGB.getSelectedItem()).trim();
        BigDecimal giaN = null;
        List<ChiTietSP> chiTietList = chiTietSPService.Get_GiaBan();

        if (cbbGiaNhap == null || cbbGiaNhap.isEmpty()) {
            loadDataCTSP();
        } else {
            // Find the maximum selling price
            for (ChiTietSP chiTietSP : chiTietList) {
                if (giaN == null || chiTietSP.getGiaBan().compareTo(giaN) > 0) {
                    giaN = chiTietSP.getGiaBan();
                }
            }

            // Default price range
            int GiaNhapMin = 1000;
            int GiaNhapMax = 1000;
            BigDecimal MinGN = new BigDecimal(GiaNhapMin);
            BigDecimal MaxGN = new BigDecimal(GiaNhapMax);

            // Print the selected combo box value for debugging
            System.out.println("Selected combo box value: " + cbbGiaNhap);

            switch (cbbGiaNhap) {
                case "Dưới 500.000đ" -> {
                    System.out.println("Gia Nhap Min Duoi 500: 0");
                    GiaNhapMin = 0;
                    GiaNhapMax = 500000;
                }
                case "500.000 - 1 triệu" -> {
                    System.out.println("Gia Nhap Min  500 - 1000000: 1000000");
                    GiaNhapMin = 500000;
                    GiaNhapMax = 1000000;
                }
                case "1 - 2 triệu" -> {
                    GiaNhapMin = 1000000;
                    GiaNhapMax = 2000000;
                }
                case "Giá trên 3 triệu" -> {
                    GiaNhapMin = 3000000;
                    GiaNhapMax = giaN != null ? giaN.intValue() : 3000000;
                }
                default -> {
                    System.err.println("Unrecognized price range: " + cbbGiaNhap);
                }
            }

            // Convert updated int values to BigDecimal
            MinGN = BigDecimal.valueOf(GiaNhapMin);
            MaxGN = BigDecimal.valueOf(GiaNhapMax);

            // Print the results for verification
            System.out.println("GiaBanMin: " + GiaNhapMin);
            System.out.println("GiaBanMax: " + GiaNhapMax);
            System.out.println("MinGB: " + MinGN);
            System.out.println("MaxGB: " + MaxGN);
            System.out.println("Gia Max: " + giaN);

            List<ChiTietSP> list = chiTietSPService.timKiemGiaBan(MinGN, MaxGN);

            // Check the returned list for debugging
            System.out.println("Returned list size: " + list.size());
            for (ChiTietSP ctsp : list) {
                System.out.println("Product ID: " + ctsp.getId() + ", GiaNhap: " + ctsp.getGiaNhap());
            }

            modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
            modelCTSP.setRowCount(0);
            for (ChiTietSP ctsp : list) {
                modelCTSP.addRow(new Object[]{
                    ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                    ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
                });
            }
        }
    }//GEN-LAST:event_cbb_searchGBActionPerformed

    private void cbb_TT_FilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbb_TT_FilFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_TT_FilFocusGained

    private void cbb_TT_FilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbb_TT_FilMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_TT_FilMouseClicked

    private void cbb_TT_FilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_TT_FilActionPerformed
        // TODO add your handling code here:
        String cbbTT = String.valueOf(cbb_TT_Fil.getSelectedItem());
        // Kiểm tra mục được chọn
        //String selectedItem = (String) comboBox.getSelectedItem();
        if ("Chọn một mục".equals(cbbTT)) {
            // Không làm gì cả hoặc thông báo người dùng chọn một mục khác
        } else {
            // Thực hiện hành động với mục đã chọn

            //            if (cbbTT) {
                //                loadDataCTSP();
                //                loadDataCTSP();
                //            } else
            {
                System.out.println("Lua chọn cua ban : " + cbbTT);
                ArrayList<ChiTietSP> listCTSP = (ArrayList<ChiTietSP>) chiTietSPService.seachtTT(cbbTT);

                modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
                modelCTSP.setRowCount(0);
                for (ChiTietSP ctsp : listCTSP) {
                    modelCTSP.addRow(new Object[]{
                        ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                        ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
                    });
                }
            }
        }
    }//GEN-LAST:event_cbb_TT_FilActionPerformed

    private void txtSearchDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchDetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchDetailsActionPerformed

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loadCbbSP() {
        List<SanPham> list = repository.getAll();
        cbb_SP_Filter.removeAllItems();
        cbb_SP.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_SP.getModel();
        DefaultComboBoxModel comboboxTimKiem = (DefaultComboBoxModel) cbb_SP_Filter.getModel();
        for (SanPham sanPham : list) {
            comboboxTimKiem.addElement("");
            combobox.addElement(sanPham.getTenSP());
            comboboxTimKiem.addElement(sanPham.getTenSP());
        }
    }

    public void loadDataCTSP() {
        listCTSP = chiTietSPService.getAll();
        modelCTSP = (DefaultTableModel) tblDetailsProducts.getModel();
        modelCTSP.setRowCount(0);
        for (ChiTietSP ctsp : listCTSP) {
            modelCTSP.addRow(new Object[]{
                ctsp.getMaCTSP(), ctsp.getMaSP(), ctsp.getTenSP(), ctsp.getTenSPCT(), ctsp.getGiaBan(), ctsp.getGiaNhap(), ctsp.getSoLuong(), ctsp.getTenNhaCungCap(),
                ctsp.getTenChatLieu(), ctsp.getTenMauSac(), ctsp.getTenSize(), ctsp.getTenDoDay(), ctsp.getTrangThai()
            });
        }
    }

    public void loadcombbNCC() {

        List<NhaCungCap> list = nhaCungCapRepo.getAll();
        cbb_NCC.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_NCC.getModel();
        for (NhaCungCap nhaCungCap : list) {
            combobox.addElement(nhaCungCap.getTenNhaCungCap());
        }
    }

    public void loadcombSize() {

        List<Size> listSize = sizeRepo.getAll();
        cbb_Size.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_Size.getModel();
        for (Size s : listSize) {
            combobox.addElement(s.getTenSize());
        }
    }

    public void loadcombMauSac() {

        List<MauSac> listMS = MauRepo.getAll();
        cbb_MauSac.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_MauSac.getModel();
        for (MauSac s : listMS) {
            combobox.addElement(s.getTenMauSac());
        }
    }

    public void loadcombbChatLieu() {

        List<ChatLieu> listChatLieu = chatLieuRepo.getAll();
        cbb_ChatLieu.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_ChatLieu.getModel();
        for (ChatLieu s : listChatLieu) {
            combobox.addElement(s.getTenChatLieu());
        }
    }

    public void loadcombDD() {

        List<DoDay> listDD = doDayRepo.getAll();
        cbb_DoDay.removeAllItems();
        DefaultComboBoxModel combobox = (DefaultComboBoxModel) cbb_DoDay.getModel();
        for (DoDay s : listDD) {
            combobox.addElement(s.getTenDoDay());
        }
    }

    private SPCT getCTSP() {
        SPCT spct = new SPCT();
        List<SanPham> listSanPham = repository.getAll();
        listChatLieu = chatLieuRepo.getAll();
        listDD = doDayRepo.getAll();
        listMS = MauRepo.getAll();
        listNhaCungCap = nhaCungCapRepo.getAll();
        listSP = repository.getAll();
        listSize = sizeRepo.getAll();

        Integer maChatLieu = 0;
        for (ChatLieu cl : listChatLieu) {
            if (cl.getTenChatLieu().equals(cbb_ChatLieu.getSelectedItem())) {
                maChatLieu = cl.getId();
            }
        }
        spct.setIdChatLieu(maChatLieu);

        Integer maDD = 0;
        for (DoDay dd : listDD) {
            if (dd.getTenDoDay().equals(cbb_DoDay.getSelectedItem())) {
                maDD = dd.getId();
            }
        }
        spct.setIdDoDay(maDD);

        Integer MS = 0;
        for (MauSac ms : listMS) {
            if (ms.getTenMauSac().equals(cbb_MauSac.getSelectedItem())) {
                MS = ms.getId();
                System.out.println(MS);
            }
        }
        spct.setIdMauSac(MS);

        Integer maNCC = 0;
        for (NhaCungCap ncc : listNhaCungCap) {
            if (ncc.getTenNhaCungCap().equals(cbb_NCC.getSelectedItem())) {
                maNCC = ncc.getId();
            }
        }
        spct.setIdNhaCungCap(maNCC);

        Integer maSP = 0;
        for (SanPham sp : listSanPham) {
            if (sp.getTenSP().equals(cbb_SP.getSelectedItem())) {
                maSP = sp.getId();
                System.out.println(maSP);
            }
        }
        spct.setIdSanPham(maSP);
        Integer maSize = 0;
        for (Size size : listSize) {
            if (size.getTenSize().equals(cbb_Size.getSelectedItem())) {
                maSize = size.getId();
            }
        }
        spct.setIdSize(maSize);

        String MaSPCT = txt_MSPCT.getText();
        int SoLuong = Integer.parseInt(txt_Soluong.getText());
        String MoTa = txt_TenSPCT.getText();
        String Gia = txt_GiaBan.getText();
        BigDecimal GiaBan = new BigDecimal(Gia);

        // Lấy đối tượng TrangThai từ ComboBox
        Object TrangThai = cbb_TT.getSelectedItem();

        // Chuyển đổi TrangThai thành String
        String TT;
        if (TrangThai instanceof TrangThai) {
            TT = TrangThai.toString(); // Sử dụng phương thức toString() của TrangThai
        } else {
            TT = TrangThai.toString();
        }

        System.out.println(TT);
        String GN = txt_GiaNhap.getText();
        BigDecimal GiaNhap = new BigDecimal(GN);

        spct.setIdChatLieu(maChatLieu);
        spct.setIdDoDay(maDD);
        spct.setIdMauSac(MS);
        spct.setIdNhaCungCap(maNCC);
        spct.setIdSanPham(maSP);
        spct.setGiaNhap(GiaNhap);
        spct.setGiaBan(GiaBan);
        spct.setMaSPCT(MaSPCT);
        spct.setSoLuong(SoLuong);
        spct.setTrangThai(TT); 
        spct.setTenSPCT(MoTa);

        System.out.println(GiaBan);
        System.out.println(GiaNhap);

        return spct;
    }

    private void showDetailsCTSP(int row) {
        String maCTSP = tblDetailsProducts.getValueAt(row, 0).toString();
        String MaSP = tblDetailsProducts.getValueAt(row, 1).toString();
        String TenSp = tblDetailsProducts.getValueAt(row, 2).toString();
        String TenSPCT = tblDetailsProducts.getValueAt(row, 3).toString();
        String GiaBan = tblDetailsProducts.getValueAt(row, 4).toString();
        String GiaNhap = tblDetailsProducts.getValueAt(row, 5).toString();
        String SoLuong = tblDetailsProducts.getValueAt(row, 6).toString();
        String tenDD = tblDetailsProducts.getValueAt(row, 11).toString();
        String tenSize = tblDetailsProducts.getValueAt(row, 10).toString();
        String CL = tblDetailsProducts.getValueAt(row, 8).toString();
        String mauSac = tblDetailsProducts.getValueAt(row, 9).toString();
        String NCC = tblDetailsProducts.getValueAt(row, 7).toString();
        String trangThai = tblDetailsProducts.getValueAt(row, 12).toString();
        txt_MSPCT.setText(maCTSP);
        txt_Soluong.setText(SoLuong);
        txt_GiaBan.setText(GiaBan);
        txt_GiaNhap.setText(GiaNhap);
        txt_TenSPCT.setText(TenSPCT);

        cbb_SP.setSelectedItem(TenSp);
        cbb_Size.setSelectedItem(tenSize);
        cbb_MauSac.setSelectedItem(mauSac);
        cbb_ChatLieu.setSelectedItem(CL);
        cbb_DoDay.setSelectedItem(tenDD);
        cbb_NCC.setSelectedItem(NCC);
        cbb_TT.setSelectedItem(trangThai);
        tblDetailsProducts.setRowSelectionInterval(row, row);
    }

    private boolean checkCTSP() {

        try {
            Float gia = Float.parseFloat(txt_GiaBan.getText());
            if (gia <= 0) {
                JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0",  "", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số",  "", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Float gia = Float.parseFloat(txt_GiaNhap.getText());
            if (gia <= 0) {
                JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0",  "", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số",  "", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Integer soLuong = Integer.parseInt(txt_Soluong.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0",  "", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số",  "", JOptionPane.WARNING_MESSAGE);

            return false;
        }
        return true;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.daipc.swing.Button ClearDoDay;
    private javax.swing.JPanel attributesProducts;
    private com.daipc.radiobutton.RadioButtonCustom btnChatLieu;
    private com.daipc.swing.Button btnClear;
    private com.daipc.swing.Button btnClearChatLieu;
    private com.daipc.swing.Button btnClearDetails;
    private com.daipc.swing.Button btnClearFiltersDetails;
    private com.daipc.swing.Button btnClearMau;
    private com.daipc.swing.Button btnClearNCC;
    private com.daipc.swing.Button btnClearSize;
    private com.daipc.radiobutton.RadioButtonCustom btnDoDay;
    private com.daipc.swing.Button btnExportDetails;
    private com.daipc.swing.Button btnImportDetails;
    private com.daipc.radiobutton.RadioButtonCustom btnMauSac;
    private com.daipc.radiobutton.RadioButtonCustom btnNhaCungCap;
    private com.daipc.radiobutton.RadioButtonCustom btnSize;
    private com.daipc.swing.Button btnSua;
    private com.daipc.swing.Button btnSuaChatLieu;
    private com.daipc.swing.Button btnSuaDetails;
    private com.daipc.swing.Button btnSuaDoDay;
    private com.daipc.swing.Button btnSuaMau;
    private com.daipc.swing.Button btnSuaNCC;
    private com.daipc.swing.Button btnSuaSize;
    private com.daipc.swing.Button btnThem;
    private com.daipc.swing.Button btnThemChatLieu;
    private com.daipc.swing.Button btnThemDetails;
    private com.daipc.swing.Button btnThemDoDay;
    private com.daipc.swing.Button btnThemMau;
    private com.daipc.swing.Button btnThemNCC;
    private com.daipc.swing.Button btnThemSize;
    private com.daipc.swing.Button btnXoaChatLieu;
    private com.daipc.swing.Button btnXoaDetails;
    private com.daipc.swing.Button btnXoaDoDay;
    private com.daipc.swing.Button btnXoaMau;
    private com.daipc.swing.Button btnXoaMem;
    private com.daipc.swing.Button btnXoaNhaCungCap;
    private com.daipc.swing.Button btnXoaSize;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_ChatLieu;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_DoDay;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_MauSac;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_NCC;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_SP;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_SP_Filter;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_Size;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_TT;
    private com.daipc.combo_suggestion.ComboBoxSuggestion cbb_TT_Fil;
    private javax.swing.JComboBox<String> cbb_searchGB;
    private javax.swing.JComboBox<String> cbb_searchGN;
    private javax.swing.JPanel detailsProducts;
    private javax.swing.JPanel infoProducts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private com.daipc.swing.MaterialTabbed materialTabbed;
    private com.daipc.swing.PanelBorder panelBorder2;
    private com.daipc.swing.PanelBorder panelChatLieu;
    private com.daipc.swing.PanelBorder panelDetailsProducts;
    private com.daipc.swing.PanelBorder panelDoDay;
    private com.daipc.swing.PanelBorder panelFilter;
    private com.daipc.swing.PanelBorder panelFilter1;
    private com.daipc.swing.PanelBorder panelInfoProducts;
    private com.daipc.swing.PanelBorder panelMauSac;
    private com.daipc.swing.PanelBorder panelNhaCc;
    private com.daipc.swing.PanelBorder panelSize;
    private com.daipc.swing.PanelBorder panelTableTong;
    private com.daipc.swing.PanelBorder panelTblChatLieu;
    private com.daipc.swing.PanelBorder panelTblDoDay;
    private com.daipc.swing.PanelBorder panelTblMau;
    private com.daipc.swing.PanelBorder panelTblNhaCc;
    private com.daipc.swing.PanelBorder panelTblSize;
    private com.daipc.swing.PanelBorder panelTong;
    private javax.swing.JScrollPane scrollChatLieu;
    private javax.swing.JScrollPane scrollDetailsProducts;
    private javax.swing.JScrollPane scrollDoDay;
    private javax.swing.JScrollPane scrollInfoProducts;
    private javax.swing.JScrollPane scrollMau;
    private javax.swing.JScrollPane scrollMoTa;
    private javax.swing.JScrollPane scrollNhaCc;
    private javax.swing.JScrollPane scrollSize;
    private javax.swing.JTable tblChatLieu;
    private javax.swing.JTable tblDetailsProducts;
    private javax.swing.JTable tblDoDay;
    private javax.swing.JTable tblInfoProducts;
    private javax.swing.JTable tblMauSac;
    private javax.swing.JTable tblNhaCc;
    private javax.swing.JTable tblSize;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtLienHe;
    private javax.swing.JTextField txtMaChatLieu;
    private javax.swing.JTextField txtMaDoDay;
    private javax.swing.JTextField txtMaMauSac;
    private javax.swing.JTextField txtMaNcc;
    private com.daipc.textfield.TextField txtMaSP;
    private javax.swing.JTextField txtMaSize;
    private javax.swing.JTextArea txtMoTa;
    private com.daipc.searchbar.MyTextField txtSearch;
    private com.daipc.searchbar.MyTextField txtSearchDetails;
    private javax.swing.JTextField txtTenChatLieu;
    private javax.swing.JTextField txtTenDoDay;
    private javax.swing.JTextField txtTenMauSac;
    private javax.swing.JTextField txtTenNcc;
    private com.daipc.textfield.TextField txtTenSP;
    private javax.swing.JTextField txtTenSize;
    private javax.swing.JTextField txt_GiaBan;
    private javax.swing.JTextField txt_GiaNhap;
    private javax.swing.JTextField txt_MSPCT;
    private javax.swing.JTextField txt_Soluong;
    private javax.swing.JTextField txt_TenSPCT;
    // End of variables declaration//GEN-END:variables
}
