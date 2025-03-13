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
 * @author daipc
 */
public class PanelActionCellEditor extends DefaultCellEditor{
    private final TableEvent event;

    public PanelActionCellEditor(TableEvent event) {
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PanelActionButton panelActionButton = new PanelActionButton();
        panelActionButton.setBackground(table.getSelectionBackground());
        panelActionButton.initEvent(event, row);
        return panelActionButton;
    }
    
}
