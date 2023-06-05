/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;


import GUI.JFUpdateKontajner;
import dp.Kontajner;
import dp.OdpadoveHospodarstvo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jxmapviewer.viewer.DefaultWaypoint;

/**
 *
 * @author rendo
 */
public class StojiskoWaypoint extends DefaultWaypoint {

    private int id_stojiska;
    private String adresa;
    private ArrayList<Kontajner> kontajnery;
    private final JButton button;
    private Color color;
    private OdpadoveHospodarstvo oh;

    public StojiskoWaypoint(int id_stojiska, double x, double y, String adresa, OdpadoveHospodarstvo oh) {
        super(x, y);
        this.id_stojiska = id_stojiska;
        this.kontajnery = new ArrayList<>();
        this.adresa = adresa;
        this.oh = oh;

        button = new JButton("" + id_stojiska);
        button.setSize(24, 24);
        button.setPreferredSize(new Dimension(24, 24));
        button.addMouseListener(new StojiskoWaypoint.StojiskoPointMouseListener());
        button.setVisible(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public void pridajKontajner(Kontajner kontajner) {
        kontajnery.add(kontajner);
        if (kontajnery.size() == 1) {
            if (getKontajnery().get(0).getNazovOdpadu().equals("Komunálny odpad")) {
                this.color = Color.DARK_GRAY;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Kovy")) {
                this.color = Color.RED;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Olej")) {
                this.color = Color.CYAN;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Papier")) {
                this.color = Color.BLUE;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Plast")) {
                this.color = Color.YELLOW;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Sklo")) {
                this.color = Color.GREEN;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Tetra paky")) {
                this.color = Color.MAGENTA;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Textil")) {
                this.color = Color.WHITE;
            } else {
                JOptionPane.showMessageDialog(null, "K danému druhu odpadu nie je pridelená farba");
            }
        } else {
            for (int i = 1; i < kontajnery.size(); i++) {
                if (!kontajnery.get(i - 1).getNazovOdpadu().equals(kontajnery.get(i).getNazovOdpadu())) {
                    this.color = Color.ORANGE;
                    return;
                }
            }
            if (getKontajnery().get(0).getNazovOdpadu().equals("Komunálny odpad")) {
                this.color = Color.DARK_GRAY;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Kovy")) {
                this.color = Color.RED;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Olej")) {
                this.color = Color.CYAN;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Papier")) {
                this.color = Color.BLUE;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Plast")) {
                this.color = Color.YELLOW;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Sklo")) {
                this.color = Color.GREEN;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Tetra paky")) {
                this.color = Color.MAGENTA;
            } else if (getKontajnery().get(0).getNazovOdpadu().equals("Textil")) {
                this.color = Color.WHITE;
            } else {
                JOptionPane.showMessageDialog(null, "K danému druhu odpadu nie je pridelená farba");
            }
        }
    }

    public int getId_stojiska() {
        return id_stojiska;
    }

    public void setId_stojiska(int id_stojiska) {
        this.id_stojiska = id_stojiska;
    }

    public ArrayList<Kontajner> getKontajnery() {
        return kontajnery;
    }

    public void setKontajnery(ArrayList<Kontajner> kontajnery) {
        this.kontajnery = kontajnery;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public JButton getButton() {
        return button;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String s = "Stojisko{" + "id_stojiska=" + id_stojiska + ", x_sur=" + super.getPosition().getLatitude() + ", y_sur=" + super.getPosition().getLongitude() + ", adresa=" + adresa + "\n";
        s += "[";
        for (Kontajner kontajner : kontajnery) {
            s += "\n[" + kontajner.toString() + "]";
        }
        s += "\n]";
        return s;
    }

    private class StojiskoPointMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            if (kontajnery.size() == 1) {
                new JFUpdateKontajner(oh, kontajnery.get(0).getId_kontajnera()).setVisible(true);
            } else if (kontajnery.size() == 2) {
                int i = 0;
                for (Kontajner kontajner : kontajnery) {
                    JFrame f = new JFUpdateKontajner(oh, kontajner.getId_kontajnera());
                    f.setLocation(i + ((dim.width - (2 * f.getSize().width)) / 2), dim.height / 2 - f.getSize().height / 2);
                    f.setVisible(true);
                    i += (10 + f.getSize().width);
                }

            } else if (kontajnery.size() >= 3) {
                JFrame f = new JFUpdateKontajner(oh, kontajnery.get(0).getId_kontajnera());
                Point p1 = new Point(0 + ((dim.width - (2 * f.getSize().width)) / 2), (0 + (dim.height - (2 * f.getSize().height)) / 2));
                Point p2 = new Point(((10 + f.getSize().width) + ((dim.width - (2 * f.getSize().width)) / 2)), (0 + (dim.height - (2 * f.getSize().height)) / 2));
                Point p3 = new Point((0 + ((dim.width - (2 * f.getSize().width)) / 2)), ((10 + f.getSize().height) + (dim.height - (2 * f.getSize().height)) / 2));
                Point p4 = new Point(((10 + f.getSize().width) + ((dim.width - (2 * f.getSize().width)) / 2)), ((10 + f.getSize().height) + (dim.height - (2 * f.getSize().height)) / 2));

                for (int i = 1; i <= kontajnery.size(); i++) {
                    f = new JFUpdateKontajner(oh, kontajnery.get(i - 1).getId_kontajnera());
                    if (i % 4 == 0) {
                        f.setLocation(p1);
                        f.setVisible(true);
                    } else if (i % 3 == 0) {
                        f.setLocation(p2);
                        f.setVisible(true);
                    } else if (i % 2 == 0) {
                        f.setLocation(p3);
                        f.setVisible(true);
                    } else {
                        f.setLocation(p4);
                        f.setVisible(true);
                    }
                }

            }

        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    }
}
