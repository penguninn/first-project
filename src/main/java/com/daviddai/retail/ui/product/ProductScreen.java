package com.daviddai.retail.ui.product;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.ProductResult;
import com.daviddai.retail.ui.component.table.PaginatedTablePanel;
import com.daviddai.retail.ui.component.table.TableColumn;
import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class ProductScreen extends JPanel implements ProductView {

    private PaginatedTablePanel<ProductResult> tablePanel;

    private final JLabel lblTitle = new JLabel("Quản lý sản phẩm");
    private final JTextField txtSearch = new JTextField(255);
    private final JButton btnSearch = new JButton("Tìm kiếm");
    private final JButton btnAdd = new JButton("Thêm sản phẩm mới");

    private Runnable onAddAction;
    private Runnable onSearchAction;
    private Consumer<ProductResult> onEditItemAction;
    private Consumer<ProductResult> onDeleteItemAction;

    public ProductScreen() {
        initComponents();
        initTable();
        initLayout();
        initListeners();
    }

    public void initComponents() {
        lblTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12; foreground:#000000");
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên sản phẩm, mã sản phẩm,...");
    }

    private void initTable() {
        List<TableColumn<ProductResult>> columns = List.of(
                new TableColumn<>("Mã", ProductResult::code),
                new TableColumn<>("Tên", ProductResult::name),
                new TableColumn<>("Mô tả", ProductResult::description),
                new TableColumn<>("Trạng thái", p -> p.isActive() ? "Đang bán" : "Ngừng"));

        tablePanel = new PaginatedTablePanel<>(columns);
        tablePanel.setPageSize(15);
    }

    public void initLayout() {
        setLayout(new MigLayout(
                "fill, insets 20 10 20 10, gap 10",
                "[grow]",
                "[][][][grow]"));
        JPanel toolBars = new JPanel(new MigLayout("fill, insets 0", "[grow][]", "[]"));
        toolBars.setOpaque(false);
        toolBars.add(txtSearch, "grow");
        toolBars.add(btnSearch);

        add(lblTitle, "wrap");
        add(toolBars, "wrap");
        add(btnAdd, "align right, wrap");
        add(tablePanel, "grow");
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> {
            if (onAddAction != null) {
                onAddAction.run();
            }
        });

        btnSearch.addActionListener(e -> {
            if (onSearchAction != null) {
                onSearchAction.run();
            }
        });

        tablePanel.setOnEdit(item -> {
            if (onEditItemAction != null)
                onEditItemAction.accept(item);
        });

        tablePanel.setOnDelete(item -> {
            if (onDeleteItemAction != null)
                onDeleteItemAction.accept(item);
        });

        tablePanel.setOnRowDoubleClick(item -> {
            if (onEditItemAction != null)
                onEditItemAction.accept(item);
        });
    }

    @Override
    public boolean confirmDelete() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        int dialogResult = JOptionPane.showConfirmDialog(owner, "Đồng ý xóa sản phầm này ?",
                "Warning", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    @Override
    public void setOnAddAction(Runnable action) {
        this.onAddAction = action;
    }

    @Override
    public void setOnSearchAction(Runnable action) {
        this.onSearchAction = action;
    }

    @Override
    public void setOnEditItemAction(Consumer<ProductResult> action) {
        this.onEditItemAction = action;
    }

    @Override
    public void setOnDeleteItemAction(Consumer<ProductResult> action) {
        this.onDeleteItemAction = action;
    }

    @Override
    public String getSearchKeyword() {
        return txtSearch.getText().trim();
    }

    @Override
    public void setData(List<ProductResult> products) {
        tablePanel.setData(products);
    }

    @Override
    public void openForm(ProductCommand initial, SaveHandler saveHandler) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        ProductDialog productDialog = new ProductDialog(owner, initial, saveHandler);
        productDialog.setVisible(true);
    }
}
