package com.daviddai.retail.ui.component.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PaginatedTableModel<T> extends AbstractTableModel {

    private final List<TableColumn<T>> columns;
    private final int actionColumnIndex;
    private List<T> data = new ArrayList<>();

    public PaginatedTableModel(List<TableColumn<T>> columns) {
        this.columns = columns;
        this.actionColumnIndex = columns.size();
    }

    public void setData(List<T> data) {
        this.data = new ArrayList<>(data);
        fireTableDataChanged();
    }

    public T getItemAt(int modelRow) {
        return data.get(modelRow);
    }

    public List<TableColumn<T>> getColumns() {
        return columns;
    }

    public int getActionColumnIndex() {
        return actionColumnIndex;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == actionColumnIndex)
            return null;
        return columns.get(columnIndex).extractor().apply(data.get(rowIndex));
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == actionColumnIndex)
            return Object.class;
        return columns.get(columnIndex).type();
    }

    @Override
    public String getColumnName(int column) {
        if (column == actionColumnIndex)
            return "Thao tác";
        return columns.get(column).name();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
