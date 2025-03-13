package com.daipc.customTable;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class HeaderRenderer implements TableCellRenderer {

    private final TableCellRenderer delegate;
    private final int[] toggleStates;

    public HeaderRenderer(TableCellRenderer delegate, int columnCount) {
        this.delegate = delegate;
        this.toggleStates = new int[columnCount];
    }

    public void setToggleState(int column, int state) {
        toggleStates[column] = state;
    }

    public int getToggleState(int column) {
        return toggleStates[column];
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JLabel label = (JLabel) comp;
        int state = toggleStates[column];
        if (state == 1) {
            label.setIcon(UIManager.getIcon("Table.ascendingSortIcon")); // Mũi tên lên
        } else if (state == -1) {
            label.setIcon(UIManager.getIcon("Table.descendingSortIcon")); // Mũi tên xuống
        } else {
            label.setIcon(null); // Trạng thái mặc định không có mũi tên
        }
        label.setHorizontalTextPosition(SwingConstants.LEFT); // Đặt icon ở bên phải của text
        return label;
    }
}
