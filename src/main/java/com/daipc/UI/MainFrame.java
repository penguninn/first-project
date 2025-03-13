/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.daipc.UI;

import com.daipc.component.Menu;
import com.daipc.event.EventMenuSelected;
import com.daipc.form.Form_Bill;
import com.daipc.form.Form_Customer;
import com.daipc.form.Form_Refund;
import com.daipc.form.Form_Home;
import com.daipc.form.Form_Products;
import com.daipc.form.Form_Profile;
import com.daipc.form.Form_Promotion;
import com.daipc.form.Form_Sell;
import com.daipc.form.Form_Staffs;
import com.daipc.model.NhanVien;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author DaiPc
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private MigLayout mlayout;
    private Menu menu;
    private Header header;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Form_Home Form_Home;
    private Form_Products Form_Products;
    private Form_Sell Form_Sell;
    private Form_Bill Form_Bill;
    private Form_Promotion Form_Promotion;
    private Form_Staffs Form_Staffs;
    private Form_Customer Form_Customer;
    private Form_Refund Form_Refund;
    private Form_Profile Form_Profile;
    public static MainFrame mainFrame;
    private Login login;
    private NhanVien nv = new NhanVien();

    public MainFrame(NhanVien nv) {
        initComponents();
        this.setLocationRelativeTo(null);
        layerPane.removeAll();
        this.nv = nv;
        init();
        setContentPane(layerPane);
        revalidate();
        repaint();

    }

    public void init() {
        mlayout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        layerPane.setLayout(mlayout);
        if (!nv.getChucVu().equals("ql")) {
            menu = new Menu(5);
        } else {
            menu = new Menu(-1);
        }

        header = new Header(this, nv.getHoTen());
        Form_Home = new Form_Home();
        Form_Products = new Form_Products(this);
        Form_Sell = new Form_Sell(nv);
        Form_Bill = new Form_Bill();
        Form_Promotion = new Form_Promotion();
        Form_Staffs = new Form_Staffs();
        Form_Customer = new Form_Customer();
        Form_Profile = new Form_Profile(nv);
        Form_Refund = new Form_Refund();
        login = new Login();

        menu.initMoving(MainFrame.this);
        header.initMoving(MainFrame.this);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(Form_Home, "Home");
        cardPanel.add(Form_Products, "Form_Products");
        cardPanel.add(Form_Sell, "Form_Sell");
        cardPanel.add(Form_Bill, "Form_Bill");
        cardPanel.add(Form_Promotion, "Form_Promotion");
        cardPanel.add(Form_Staffs, "Form_Staffs");
        cardPanel.add(Form_Customer, "Form_Customer");
        cardPanel.add(Form_Profile, "Form_Profile");
        cardPanel.add(Form_Refund, "Form_Refund");

        layerPane.add(menu, "w 230!, spany 2");
        layerPane.add(header, "h 35!, wrap");
        layerPane.add(cardPanel, "w 100%, h 100%");

        menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selectedIndex(int index) {
                System.out.println(index);
                showForm(index);
            }
        });
    }

    public void showForm(int index) {
        switch (index) {
            case 0:
                cardLayout.show(cardPanel, "Home");
                break;
            case 1:
                cardLayout.show(cardPanel, "Form_Products");
                break;
            case 2:
                cardLayout.show(cardPanel, "Form_Sell");
                break;
            case 3:
                cardLayout.show(cardPanel, "Form_Bill");
                break;
            case 4:
                cardLayout.show(cardPanel, "Form_Promotion");
                break;
            case 5:
                cardLayout.show(cardPanel, "Form_Staffs");
                break;
            case 6:
                cardLayout.show(cardPanel, "Form_Customer");
                break;
            case 7:
                cardLayout.show(cardPanel, "Form_Refund");
                break;
            case 11:
                cardLayout.show(cardPanel, "Form_Profile");
                break;
            case 12:
                this.dispose();
                login.setVisible(true);
                break;
            default:
//                        throw new AssertionError();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layerPane = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1600, 800));

        layerPane.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layerPaneLayout = new javax.swing.GroupLayout(layerPane);
        layerPane.setLayout(layerPaneLayout);
        layerPaneLayout.setHorizontalGroup(
            layerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1600, Short.MAX_VALUE)
        );
        layerPaneLayout.setVerticalGroup(
            layerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layerPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layerPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame(null);
            mainFrame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layerPane;
    // End of variables declaration//GEN-END:variables
}
