package com.daviddai.retail.ui.product;

import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.daviddai.retail.model.dto.command.ProductCommand;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class ProductDialog extends JDialog {

    private final SaveHandler saveHandler;

    private final JLabel lblTitle = new JLabel("");
    private final JLabel lblId = new JLabel("Id");
    private final JLabel lblCode = new JLabel("Mã sản phẩm");
    private final JLabel lblName = new JLabel("Tên sản phẩm");
    private final JLabel lblDescription = new JLabel("Mô tả");
    private final JLabel lblIsActive = new JLabel("Kích hoạt");

    private final JTextField txtId = new JTextField();
    private final JTextField txtCode = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtDescription = new JTextField();

    private final ButtonGroup group = new ButtonGroup();
    private final JRadioButton rdoActive = new JRadioButton("Kích hoạt");
    private final JRadioButton rdoInActive = new JRadioButton("Không kích hoạt");

    private final JButton btnSave = new JButton("Lưu");
    private final JButton btnCancel = new JButton("Hủy");
    private final JLabel lblError = new JLabel(" ");

    public ProductDialog(Window owner, ProductCommand initial, SaveHandler saveHandler) {
        super(owner, getDialogTitle(initial), ModalityType.APPLICATION_MODAL);
        this.saveHandler = saveHandler;

        lblTitle.setText(getDialogTitle(initial));
        initComponents();
        initLayout();
        initData(initial);
        initEvent();

        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12; foreground:#000000");
        lblError.putClientProperty(FlatClientProperties.STYLE, "font:plain; foreground:#f7000d");

        txtId.setEditable(false);
        txtCode.setEditable(false);
    }

    private void initLayout() {
        JPanel root = new JPanel(new MigLayout(
                "fill, insets 20 40 20 40, gap 10",
                "[100!][200!][100!][200!]",
                "[][][][][]"));

        // Radio Group
        JPanel radioPanel = new JPanel(new MigLayout("insets 0, gap 10"));
        radioPanel.setOpaque(false);
        radioPanel.add(rdoActive);
        radioPanel.add(rdoInActive);
        group.add(rdoActive);
        group.add(rdoInActive);

        // Button Panel
        JPanel btnPanel = new JPanel(new MigLayout("insets 0, gap 10"));
        btnPanel.setOpaque(false);
        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);

        root.add(lblTitle, "span 2, wrap");

        root.add(lblId);
        root.add(txtId, "growx");
        root.add(lblCode);
        root.add(txtCode, "growx, wrap");

        root.add(lblName);
        root.add(txtName, "growx");
        root.add(lblDescription);
        root.add(txtDescription, "growx, wrap");

        root.add(lblIsActive);
        root.add(radioPanel, "span 3, growx, wrap");

        root.add(lblError, "span 2, alignx left, gapleft 0");
        root.add(btnPanel, "span 2, alignx right");

        setContentPane(root);
    }

    private void initData(ProductCommand initial) {
        if (initial == null) {
            rdoActive.setSelected(true);
        } else {
            txtId.setText(initial.id() != null ? String.valueOf(initial.id()) : "");
            txtCode.setText(initial.code() != null ? initial.code() : "");
            txtName.setText(initial.name() != null ? initial.name() : "");
            txtDescription.setText(initial.description() != null ? initial.description() : "");

            if (initial.isActive()) {
                rdoActive.setSelected(true);
            } else {
                rdoInActive.setSelected(true);
            }
        }
    }

    private void initEvent() {
        btnSave.addActionListener(e -> handleSaveAction());
        btnCancel.addActionListener(e -> dispose());
    }

    private void handleSaveAction() {
        clearError();

        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            showError("Không được để trống tên sản phẩm");
            return;
        }

        Integer id = txtId.getText().isBlank() ? null : Integer.parseInt(txtId.getText());
        String code = txtCode.getText().trim();
        String description = txtDescription.getText().trim();
        boolean isActive = rdoActive.isSelected();

        ProductCommand command = new ProductCommand(id, code, name, description, isActive);

        showLoading(true);
        saveHandler.save(command, this::handleSaveResult);
    }

    private void handleSaveResult(String errorMessage) {
        showLoading(false);

        if (errorMessage == null) {
            resetForm();
        } else {
            showError(errorMessage);
        }
    }

    private void showLoading(boolean loading) {
        btnSave.setEnabled(!loading);
        btnSave.setText(loading ? "Đang lưu..." : "Lưu");
        btnCancel.setEnabled(!loading);
        txtName.setEnabled(!loading);
        txtDescription.setEnabled(!loading);
        rdoActive.setEnabled(!loading);
        rdoInActive.setEnabled(!loading);
    }

    private void showError(String message) {
        lblError.setText(message);
    }

    private void clearError() {
        lblError.setText(" ");
    }

    private void resetForm() {
        txtId.setText("");
        txtCode.setText("");
        txtName.setText("");
        txtDescription.setText("");

        rdoActive.setSelected(true);
    }

    private static String getDialogTitle(ProductCommand command) {
        return (command == null || command.id() == null) ? "Thêm sản phẩm" : "Sửa sản phẩm";
    }
}
