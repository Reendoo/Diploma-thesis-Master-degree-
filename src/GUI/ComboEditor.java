/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author rendo
 */
public class ComboEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    Object selected;
    public ComboEditor() {
    }

    @Override
    public Object getCellEditorValue() {
        return this.selected;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (row == 1 && column > 0) {
            this.selected = value;
            JComboBox<Object> cmb = new JComboBox<>();
            cmb.addItem(1);
            cmb.addItem(2);
            cmb.addItem(3);
            cmb.addItem(4);
            cmb.addItem(5);
            cmb.setSelectedItem(selected);
            cmb.addActionListener(this);
            if (isSelected) {
                cmb.setBackground(table.getSelectionBackground());

            } else {
                cmb.setBackground(table.getSelectionForeground());
            }
            return cmb;
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JComboBox<Object> cmb = (JComboBox<Object>) event.getSource();
        this.selected = (Object) cmb.getSelectedItem();
        Refresher.r.refreshVybraneVozidla();

    }

}
