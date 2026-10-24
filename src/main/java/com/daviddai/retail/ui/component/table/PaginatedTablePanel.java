package com.daviddai.retail.ui.component.table;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

public class PaginatedTablePanel<T> extends JPanel {

    private final PaginatedTableModel<T> model;
    private final JTable table;
    private final JScrollPane scroll;
    private final TableRowSorter<PaginatedTableModel<T>> sorter;

    private List<T> allData = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 20;
    private int totalPages = 1;

    private Consumer<T> onEdit;
    private Consumer<T> onDelete;
    private Consumer<T> onRowDoubleClick;

    private final JButton btnPrev = new JButton("<<");
    private final JButton btnNext = new JButton(">>");
    private final JTextField txtPage = new JTextField();
    private final JLabel lblTotalPages = new JLabel(" / 1");
    private final JLabel lblInfo = new JLabel("0 bản ghi");

    public PaginatedTablePanel(List<TableColumn<T>> columns) {
        this.model = new PaginatedTableModel<>(columns);
        this.table = new JTable(model) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int row = getSelectedRow();

                if (row < 0 || getColumnCount() == 0) {
                    return;
                }
                Graphics2D g2 = (Graphics2D) g.create();

                try {
                    Rectangle first = getCellRect(row, 0, false);

                    int y = first.y;
                    int h = first.height;

                    g2.setColor(new Color(210, 210, 210));

                    for (int col = 0; col < getColumnCount() - 1; col++) {
                        Rectangle cell = getCellRect(row, col, true);

                        int lineX = cell.x + cell.width - 1;

                        g2.drawLine(
                                lineX, y + 1,
                                lineX, y + h - 2);
                    }

                } finally {
                    g2.dispose();
                }
            }

        };
        this.scroll = new JScrollPane(table);
        this.sorter = new TableRowSorter<>(model);

        initComponents();
        initLayout();
        initListeners();
    }

    private void initComponents() {
        table.setRowHeight(34);
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(210, 230, 250));
        table.setSelectionForeground(Color.BLACK);
        table.setAutoCreateRowSorter(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));

        table.setRowSorter(sorter);
        sorter.setSortable(model.getActionColumnIndex(), false);
        sorter.setRowFilter(createPageFilter());

        int actionIdx = model.getActionColumnIndex();
        table.getColumnModel().getColumn(actionIdx).setCellRenderer(new ActionCellRenderer());
        table.getColumnModel().getColumn(actionIdx).setPreferredWidth(150);
        table.getColumnModel().getColumn(actionIdx).setMaxWidth(150);
        table.getColumnModel().getColumn(actionIdx).setMinWidth(150);

        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        // Footer
        txtPage.setHorizontalAlignment(SwingConstants.CENTER);
        txtPage.setText("1");
        lblTotalPages.setForeground(new Color(120, 120, 120));
        lblInfo.setForeground(new Color(120, 120, 120));
    }

    private void initLayout() {
        setLayout(new MigLayout(
                "fill, insets 0 0 0 0, gap 0",
                "[grow]",
                "[grow][]"));
        add(scroll, "grow, wrap");
        add(buildFooter(), "growx");
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new MigLayout(
                "fill, insets 8 12 8 12, gap 6",
                "[grow][]",
                "[]"));
        footer.setBackground(new Color(250, 250, 250));
        footer.add(lblInfo, "growx");

        JPanel pager = new JPanel(new MigLayout(
                "insets 0 0 0 0, gap 6",
                "[][][][][]",
                "[]"));
        pager.setOpaque(false);
        pager.add(btnPrev);
        pager.add(new JLabel("Trang"));
        pager.add(txtPage);
        pager.add(lblTotalPages);
        pager.add(btnNext);
        footer.add(pager);

        return footer;
    }

    private void initListeners() {
        btnPrev.addActionListener(e -> goToPage(currentPage - 1));
        btnNext.addActionListener(e -> goToPage(currentPage + 1));

        txtPage.addActionListener(e -> {
            try {
                int page = Integer.parseInt(txtPage.getText().trim());
                goToPage(page);
            } catch (NumberFormatException ex) {
                txtPage.setText(String.valueOf(currentPage));
            }
        });

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = table.rowAtPoint(e.getPoint());
                int viewColumn = table.columnAtPoint(e.getPoint());
                if (viewRow < 0 || viewColumn < 0)
                    return;
                if (viewColumn == model.getActionColumnIndex()) {
                    handleActionClick(viewRow, viewColumn, e);
                } else if (e.getClickCount() == 2 && onRowDoubleClick != null) {
                    onRowDoubleClick.accept(getItemAt(viewRow));
                }
            }

        });
    }

    private void handleActionClick(int viewRow, int viewCol, MouseEvent e) {
        Rectangle cellRect = table.getCellRect(viewRow, viewCol, false);
        int relX = e.getX() - cellRect.x;
        int half = cellRect.width / 2;
        T item = getItemAt(viewRow);
        if (relX < half) {
            if (onEdit != null)
                onEdit.accept(item);
        } else {
            if (onDelete != null)
                onDelete.accept(item);
        }
    }

    // Pagination
    private RowFilter<PaginatedTableModel<T>, Integer> createPageFilter() {
        return new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends PaginatedTableModel<T>, ? extends Integer> entry) {
                int modelRow = entry.getIdentifier();
                int from = (currentPage - 1) * pageSize;
                int to = from + pageSize;
                return modelRow >= from && modelRow < to;
            }
        };
    }

    private void refreshPagination() {
        int total = allData.size();
        totalPages = Math.max(1, (int) Math.ceil((double) total / pageSize));

        if (currentPage < 1)
            currentPage = 1;
        if (currentPage > totalPages)
            currentPage = totalPages;

        model.setData(allData);
        sorter.sort();
        updateFooter(total);
    }

    private void updateFooter(int total) {
        int from = total == 0 ? 0 : (currentPage - 1) * pageSize + 1;
        int to = Math.min(currentPage * pageSize, total);

        lblInfo.setText(total == 0
                ? "Không có bản ghi"
                : String.format("Hiển thị %d-%d / %d bản ghi", from, to, total));
        txtPage.setText(String.valueOf(currentPage));
        lblTotalPages.setText(" / " + totalPages);

        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
    }

    // Public api
    public void setData(List<T> data) {
        this.allData = new ArrayList<>(data != null ? data : List.of());
        this.currentPage = 1;
        refreshPagination();
    }

    public void refresh() {
        refreshPagination();
    }

    public void goToPage(int page) {
        if (page < 1 || page > totalPages || page == currentPage)
            return;
        this.currentPage = page;
        refreshPagination();
    }

    public void setPageSize(int size) {
        this.pageSize = Math.max(1, size);
        this.currentPage = 1;
        refreshPagination();
    }

    public T getSelectedItem() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0)
            return null;
        return getItemAt(viewRow);
    }

    public List<T> getSelectedItems() {
        List<T> result = new ArrayList<>();
        for (int viewRow : table.getSelectedRows()) {
            result.add(getItemAt(viewRow));
        }
        return result;
    }

    public void clearSelection() {
        table.clearSelection();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public JTable getTable() {
        return table;
    }

    public PaginatedTableModel<T> getModel() {
        return model;
    }

    public TableRowSorter<PaginatedTableModel<T>> getSorter() {
        return sorter;
    }

    public void setOnEdit(Consumer<T> onEdit) {
        this.onEdit = onEdit;
    }

    public void setOnDelete(Consumer<T> onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnRowDoubleClick(Consumer<T> onDoubleClick) {
        this.onRowDoubleClick = onDoubleClick;
    }

    private T getItemAt(int viewRow) {
        int modelRow = table.convertRowIndexToModel(viewRow);
        return model.getItemAt(modelRow);
    }

    private static class ActionCellRenderer extends JPanel implements TableCellRenderer {

        private static final Color EDIT_BG = new Color(59, 130, 246);
        private static final Color DELETE_BG = new Color(239, 68, 68);
        private static final Color TEXT = Color.WHITE;

        ActionCellRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setFont(table.getFont());
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                int half = w / 2;
                int padding = 6;
                int btnW = half - padding * 2;
                int btnH = h - padding * 2;
                drawButton(g2, padding, padding, btnW, btnH, "Sửa", EDIT_BG);
                drawButton(g2, half + padding, padding, btnW, btnH, "Xóa", DELETE_BG);

                g2.setColor(Color.decode("#e6e6e6"));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(0, h - 1, w - 1, h - 1);
            } finally {
                g.dispose();
            }
        }

        private void drawButton(Graphics2D g2, int x, int y, int w, int h, String text, Color bg) {
            g2.setColor(bg);
            g2.fillRoundRect(x, y, w, h, 6, 6);
            g2.setColor(TEXT);
            g2.setFont(getFont().deriveFont(12F));

            var fm = g2.getFontMetrics();
            int textX = x + (w - fm.stringWidth(text)) / 2;
            int textY = y + (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(text, textX, textY);
        }
    }

}
