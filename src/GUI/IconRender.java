/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rendo
 */
public class IconRender extends DefaultTableCellRenderer {

    Icon icon;

    public IconRender() {
        URL resource = getClass().getResource("remove.png");
        icon = new ImageIcon(resource);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        setHorizontalAlignment(JLabel.CENTER);
        if (column != 0 && row >= 2) {
            setIcon(icon);
        } else {
            setIcon(null);
        }
//        if (isSelected) {
//            setBackground(table.getSelectionBackground());
//        } else {
//            setBackground(table.getSelectionForeground());
//        }

        return this;
    }
}
