/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.customTable;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author DaiPc
 */
public class QtySpinnerCellEditor extends DefaultCellEditor {

    private final JSpinner spinner;
    private JFormattedTextField formatTextField;
    private final TableEvent event;

    public QtySpinnerCellEditor(TableEvent event, JTable table) {
        super(new JCheckBox());
        spinner = new JSpinner();
        this.event = event;
        SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinner.getModel();
        spinnerModel.setMinimum(1);
        JSpinner.NumberEditor numEditor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField formatTextField = numEditor.getTextField();
        formatTextField.setHorizontalAlignment(SwingConstants.LEFT);
        DefaultFormatter f = (DefaultFormatter) formatTextField.getFormatter();
        f.setCommitsOnValidEdit(true);

        formatTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int selectedRow = table.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if (commitEdit(formatTextField)) {
                        stopEditing(table, selectedRow);
                    }
                    
                }
            }
        });

        formatTextField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return commitEdit((JFormattedTextField) input);
            }
        });   
    }
    
    private void stopEditing(JTable table, int selectedRow) {
        stopCellEditing();
        table.setRowSelectionInterval(selectedRow, selectedRow);
        table.getParent().repaint();
    }

    private boolean commitEdit(JFormattedTextField textField) {
        try {
            if(Integer.parseInt(textField.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên lớn hơn 0 !", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            textField.commitEdit();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập một số nguyên!", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component com = super.getTableCellEditorComponent(table, value, isSelected, row, column);
        int qty = Integer.parseInt(value.toString());
        spinner.setValue(qty);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = (int) spinner.getValue();
            }
        });
        
        return spinner;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
