/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author rendo
 */
public class UzolWaypoint extends DefaultWaypoint {

    private final Color color;
    private final JButton button;

    private int id_uzla;
    private String celkovePoradie;
    private int poradieStojiska;

    public UzolWaypoint(int id_uzla, int poradieStojiska, Color color, GeoPosition coord) {
        super(coord);
        this.id_uzla = id_uzla;
        this.poradieStojiska = poradieStojiska;
        this.color = color;
        button = new JButton("" + poradieStojiska);
        button.setSize(24, 24);
        button.setPreferredSize(new Dimension(24, 24));
        button.setMaximumSize(new Dimension(24, 24));
        button.addMouseListener(new MyWaypointMouseListener());
        button.setVisible(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

    }

    public UzolWaypoint(Color color, int id_uzla, int celkovePoradie, GeoPosition coord) {
        super(coord);
        this.id_uzla = id_uzla;
        this.celkovePoradie = "" + celkovePoradie;
        this.color = color;
        button = new JButton("" + celkovePoradie);
        poradieStojiska = Integer.MIN_VALUE;
        button.setSize(24, 24);
        button.setPreferredSize(new Dimension(24, 24));
        button.setMaximumSize(new Dimension(24, 24));
        button.addMouseListener(new MyWaypointMouseListener());
        button.setVisible(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public void pridajNovePoradie(int celkovePoradie) {
        if (this.celkovePoradie == null) {
            this.celkovePoradie = "" + celkovePoradie;
        } else {
            this.celkovePoradie += ", " + celkovePoradie;
        }
    }

    public String getLabel() {
        return button.getText();
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    public JButton getButton() {
        return button;
    }

    public int getId_uzla() {
        return id_uzla;
    }

    private class MyWaypointMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            String a = "" + poradieStojiska;
            if (poradieStojiska == Integer.MIN_VALUE) {
                a = "Nie je stojisko";
            }

            String s = "ID_uzla: " + id_uzla
                    + "\n PoradieUzla: " + celkovePoradie
                    + "\n PoradieStojiska: " + a;

            JOptionPane.showMessageDialog(null, s);
        }

        @Override
        public void mousePressed(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent me) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
