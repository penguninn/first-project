/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author DaiPc
 */
public class PanelBtnDeleteCellEditor extends DefaultCellEditor{
    
    private final TableEvent event;

    public PanelBtnDeleteCellEditor(TableEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelBtnDelete panelButton = new PanelBtnDelete();
        panelButton.setBackground(table.getSelectionBackground());
        panelButton.initEvent(event, row);
        return panelButton;
    }
    
}
