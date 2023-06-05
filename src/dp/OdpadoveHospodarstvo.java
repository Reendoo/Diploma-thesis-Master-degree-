/*
 * To change this license hlavicky, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import Mapy.StojiskoWaypoint;
import Graf.Dijkstra;
import Graf.Graf;
import CW.Trasa;
import CW.Vozidlo;
import CW.Zakaznik;
import Graf.Suradnice;
import Messenger.Messenger;
import Messenger.PrijataDavka;
import Messenger.PrijataSprava;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Hlavna trieda aplikacie
 *
 * @author rendo
 */
public class OdpadoveHospodarstvo {

    private final Connection con;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
    private CallableStatement stored_pro = null;
    private Graf graph;
    private ArrayList<StojiskoWaypoint> pracovneStojiska;
    private final Optimalizacia opt;
    private final Messenger msg;
    private VysledokOptimalizacie VysloOPT;
    private LinkedList<PrijataSprava> nespracovaneSpravy;
    private LinkedList<int[]> vozidla_navrhovane;
    LinkedList<Oblast> oblasti;
    LinkedList<GeoPosition> suradnice;

    /**
     * Konštruktor
     *
     * @param conn - pripojenie k DB
     * @param msg - Messenger
     */
    public OdpadoveHospodarstvo(Connection conn, Messenger msg) {
        this.con = conn;
        this.msg = msg;
        graph = new Graf();
        opt = new Optimalizacia(this);
        nespracovaneSpravy = new LinkedList<>();
        vozidla_navrhovane = new LinkedList<>();
    }

    /**
     * Getter na navrhovane vozidla pole {kapacita, priorita}
     *
     * @return
     */
    public LinkedList<int[]> getVozidla_navrhovane() {
        return vozidla_navrhovane;
    }

    /**
     * Pridanie vozidiel do navrhovanych
     *
     * @param kap
     */
    public void pridajVozidlo(int[] kap) {
        vozidla_navrhovane.add(kap);
    }

    /**
     * Vycistenie navrhovanych vozidiel
     */
    public void vycisticVozidla() {
        vozidla_navrhovane.clear();
    }

    /**
     * Odobranie vozidiel navrhovanych
     *
     * @param index - poradie
     */
    public void odoberVozidlo(int index) {
        vozidla_navrhovane.remove(index);
    }

    /**
     * Sum navrhovanych kapacit vozidiel
     *
     * @return
     */
    public int getSumKap() {
        int sum = 0;
        for (int[] is : vozidla_navrhovane) {
            sum += is[0];
        }
        return sum;
    }

    /**
     *
     * Getter nespracovanych sprav
     *
     * @return
     */
    public LinkedList<PrijataSprava> getNespracovaneSpravy() {
        return nespracovaneSpravy;
    }

    /**
     * Getter na model comboboxu pre druhy odpadu
     *
     * @param str
     * @return
     */
    public DefaultComboBoxModel getDruhOdpadu(String str, DefaultComboBoxModel mod) {
        mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call getDruhyOdpadu (?)}");
            stored_pro.setString(1, str + "%");
            rs = stored_pro.executeQuery();
            while (rs.next()) {
                mod.addElement(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na model comboboxu pre idecka kontajnerov
     *
     * @param str
     * @param mod
     * @return
     */
    public DefaultComboBoxModel getDruhIdecka(String str, DefaultComboBoxModel mod) {
        mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call getIdecka (?)}");
            stored_pro.setString(1, str + "%");
            rs = stored_pro.executeQuery();
            mod.addElement("");
            while (rs.next()) {
                mod.addElement(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na combobobox model pre stav jazdy
     *
     * @param mod
     * @return
     */
    public DefaultComboBoxModel getStavJazdy(DefaultComboBoxModel mod) {
        mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call getStavJazdy ()}");
            rs = stored_pro.executeQuery();
            while (rs.next()) {
                mod.addElement(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na combobobox model pre stav vozidla
     *
     * @return
     */
    public DefaultComboBoxModel getStavyVozidla() {
        DefaultComboBoxModel mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call getStavyVozidla ()}");
            rs = stored_pro.executeQuery();
            while (rs.next()) {
                mod.addElement(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na model tabulky kontajnerov
     *
     * @param nazov_odpadu
     * @param percenta
     * @param datumVyprazdnenia
     * @param datumPosledejAktualizacie
     * @param kontajner
     * @param mod
     * @return
     */
    public DefaultTableModel getKontajnery(String nazov_odpadu, int percenta, Date datumVyprazdnenia, Date datumPosledejAktualizacie, DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getKontajnery (?,?,?,?)}");
            stored_pro.setString(1, nazov_odpadu);
            stored_pro.setInt(2, percenta);

            if (datumVyprazdnenia != null) {
                stored_pro.setDate(3, convertJavaDateToSqlDate(datumVyprazdnenia));
            } else {
                stored_pro.setDate(3, null);
            }
            if (datumPosledejAktualizacie != null) {
                stored_pro.setDate(4, convertJavaDateToSqlDate(datumPosledejAktualizacie));
            } else {
                stored_pro.setDate(4, null);
            }
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == 0 || column == 1 || column == 2 || column == 3) {
                        return Integer.class;
                    } else if (column == (columnCount - 2)) {
                        return Boolean.class;
                    } else if (column == (columnCount - 1) || column == (columnCount)) {
                        return Icon.class;
                    }
                    return Object.class;
                }

                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, true, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };

            Object[] header = new Object[columnCount + 3];
            header = new String[]{"ID_stojiska", "ID_kontajnera", "odpad", "%-naplnenia",
                "maxObjem [L]", "adresa", "Posledné Vyprázdnenie", "Posledná aktualizcia",
                "Vybrať", "Vývoj", "Vývoj", "x", "y"};
//            int j = 0;
//            for (int i = 0; i < header.length; i++) {
//                if (i == (columnCount - 2)) {
//                    header[i] = "Vybrať";
//                    continue;
//                }
//                if (i == (columnCount - 1)) {
//                    header[i] = "Vývoj";
//                    continue;
//                }
//                if (i == (columnCount - 0)) {
//                    header[i] = "Vývoj";
//                    continue;
//                }
//                header[i] = rsmd.getColumnName(j + 1);
//                j++;
//            }

            mod.setColumnIdentifiers(header);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            URL resource = getClass().getResource("statistics.png");
            Icon icon = new ImageIcon(resource);
            URL resource1 = getClass().getResource("bank-1.png");
            Icon icon1 = new ImageIcon(resource1);
            while (rs.next()) {
                Object[] data = new Object[columnCount + 3];
                for (int i = 0; i < data.length; i++) {
                    if (i == columnCount - 2) {
                        data[i] = true;
                        data[i + 1] = icon;
                        data[i + 2] = icon1;
                        data[i + 3] = rs.getObject(i + 1);
                        data[i + 4] = rs.getObject(i + 2);
                        break;
                    }
                    switch (i) {
                        case 6:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = simpleDateFormat1.format(rs.getObject(i + 1));
                            break;
                        case 7:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = simpleDateFormat2.format(rs.getObject(i + 1));
                            break;
                        default:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = rs.getObject(i + 1);
                            break;
                    }
                }
                mod.addRow(data);
            }
            reloadStojiska(mod);
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Metoda pre vytvorenie stojisk na optimalizaciu
     *
     * @param tableModel - model tabulky kontajnerov
     */
    public void reloadStojiska(TableModel tableModel) {
        pracovneStojiska = new ArrayList<>();
        StojiskoWaypoint predchadzajuce = null;
        StojiskoWaypoint aktual = null;
        if (tableModel.getRowCount() > 0) {
//            System.out.println("" + tableModel.getValueAt(0, 0)
//                              +">>>"+Double.parseDouble("" + tableModel.getValueAt(0, 9))
//                              +">>>"+ Double.parseDouble("" + tableModel.getValueAt(0, 10))
//                              +">>>"+tableModel.getValueAt(0, 5)
//                    );

            aktual = new StojiskoWaypoint(Integer.parseInt(
                    "" + tableModel.getValueAt(0, 0)),
                    Double.parseDouble("" + tableModel.getValueAt(0, 11)),
                    Double.parseDouble("" + tableModel.getValueAt(0, 12)),
                    "" + tableModel.getValueAt(0, 5), this);
            aktual.pridajKontajner(
                    new Kontajner(
                            Integer.parseInt("" + tableModel.getValueAt(0, 1)),
                            "" + tableModel.getValueAt(0, 2),
                            Integer.parseInt("" + tableModel.getValueAt(0, 3)),
                            Integer.parseInt("" + tableModel.getValueAt(0, 4)),
                            parseDate("" + tableModel.getValueAt(0, 6)),
                            parseDate("" + tableModel.getValueAt(0, 7))
                    ));
            pracovneStojiska.add(aktual);
            predchadzajuce = aktual;
            if (tableModel.getRowCount() == 1) {
                return;
            }
        }
        for (int i = 1; i < tableModel.getRowCount(); i++) {
            if (predchadzajuce.getId_stojiska() == Integer.parseInt("" + tableModel.getValueAt(i, 0))) {
                aktual.pridajKontajner(new Kontajner(
                        Integer.parseInt("" + tableModel.getValueAt(i, 1)),
                        "" + tableModel.getValueAt(i, 2),
                        Integer.parseInt("" + tableModel.getValueAt(i, 3)),
                        Integer.parseInt("" + tableModel.getValueAt(i, 4)),
                        parseDate("" + tableModel.getValueAt(i, 6)),
                        parseDate("" + tableModel.getValueAt(i, 7))
                ));
            } else {
                aktual = new StojiskoWaypoint(Integer.parseInt("" + tableModel.getValueAt(i, 0)), Double.parseDouble("" + tableModel.getValueAt(i, 11)),
                        Double.parseDouble("" + tableModel.getValueAt(i, 12)), "" + tableModel.getValueAt(i, 5), this);

                aktual.pridajKontajner(new Kontajner(
                        Integer.parseInt("" + tableModel.getValueAt(i, 1)),
                        "" + tableModel.getValueAt(i, 2),
                        Integer.parseInt("" + tableModel.getValueAt(i, 3)),
                        Integer.parseInt("" + tableModel.getValueAt(i, 4)),
                        parseDate("" + tableModel.getValueAt(i, 6)),
                        parseDate("" + tableModel.getValueAt(i, 7))
                ));
                pracovneStojiska.add(aktual);
                predchadzajuce = aktual;
            }
        }
    }

    /**
     * Getter na model tabulkz pre vozidla
     *
     * @param mod
     * @return
     */
    public DefaultTableModel getVozidlaKapacitne(DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getVozidlaKapacitne ()}");
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == columnCount) {
                        return Icon.class;

                    } else {
                        return Object.class;
                    }
                }
                boolean[] canEdit = new boolean[]{
                    false, false, false, false};

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            Object[] header = new Object[columnCount + 1];
            for (int i = 0; i < columnCount; i++) {
                header[i] = rsmd.getColumnName(i + 1);
            }

            header[columnCount] = "+";
            mod.setColumnIdentifiers(header);
            URL resource = getClass().getResource("add(2).png");
            Icon icon = new ImageIcon(resource);

            while (rs.next()) {
                Object[] data = new Object[columnCount + 1];
                for (int i = 0; i < columnCount; i++) {
                    data[i] = rs.getObject(i + 1);

                }
                if (icon != null) {
                    data[columnCount] = icon;
                    mod.addRow(data);
                }
            }
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na model pre tabulky vozidiel
     *
     * @param mod
     * @return
     */
    public DefaultTableModel getVozidla(DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getVozidla ()}");
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {

                    return String.class;

                }
                boolean[] canEdit = new boolean[]{
                    false, false, false, false};

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            };
            Object[] header = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                header[i] = rsmd.getColumnName(i + 1);
            }

            mod.setColumnIdentifiers(header);
            while (rs.next()) {
                Object[] data = new Object[columnCount + 1];
                for (int i = 0; i < columnCount; i++) {
                    if (i == 3) {
                        data[i] = String.format("%.2f", rs.getObject(i + 1));
                        break;
                    }
                    data[i] = rs.getObject(i + 1);
                }
                mod.addRow(data);
            }
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na vozidlo podla spz
     *
     * @param spz
     * @return
     */
    public Vozidlo getVozidlo(String spz) {
        Vozidlo vozidlo = null;
        try {
//            System.out.println(spz);
//            System.out.println(spz.length());
            stored_pro = con.prepareCall("{call getVozidlo (?)}");
            stored_pro.setString(1, spz);
            rs = stored_pro.executeQuery();
            rs.next();
            vozidlo = new Vozidlo(
                    rs.getString(1),
                    rs.getDouble(3),
                    rs.getString(2)
            );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return vozidlo;
    }

    /**
     * metoda na vytvorenie vozidla
     *
     * @param vozidlo
     * @return
     */
    public int createVozidlo(Vozidlo vozidlo) {
        try {
            stored_pro = con.prepareCall("{call createVozidlo (?,?,?,?)}");
            String spz = vozidlo.getSPZ();
            String stav = vozidlo.getStav();
            int pom = (int) vozidlo.getKapacita();
            stored_pro.setString(1, spz);
            stored_pro.setString(2, stav);
            stored_pro.setInt(3, pom);
            stored_pro.registerOutParameter(4, Types.INTEGER);
            stored_pro.execute();
            return stored_pro.getInt(4);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 1;
    }

    /**
     * Metoda na update vozidla
     *
     * @param vozidlo
     * @return
     */
    public boolean updateVozidlo(Vozidlo vozidlo) {
        try {
            stored_pro = con.prepareCall("{call udpdateVozidlo (?,?,?)}");
            String spz = vozidlo.getSPZ();
            String stav = vozidlo.getStav();
            int pom = (int) vozidlo.getKapacita();
            stored_pro.setString(1, spz);
            stored_pro.setString(2, stav);
            stored_pro.setInt(3, pom);
            stored_pro.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    /**
     * Getter na pocet stojisk
     *
     * @return
     */
    public int getPocetStojisk() {
        int i = -1;
        try {
            stored_pro = con.prepareCall("{call getPocetStojisk (?)}");
            stored_pro.registerOutParameter(1, Types.INTEGER);
            stored_pro.execute();
//            System.out.println("I: " + stored_pro.getInt(1));
            return stored_pro.getInt(1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return i;
    }

    /**
     * Getter pre combobox model pre stojiska
     *
     * @return
     */
    public DefaultComboBoxModel getStojiska() {
        DefaultComboBoxModel mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call getStojiska ()}");
            rs = stored_pro.executeQuery();
            while (rs.next()) {
                mod.addElement(rs.getString(1) + ", " + rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return mod;
    }

    /**
     * Getter pre comboboxmodel pre vozidlakapacitne
     *
     * @param kap
     * @param spz
     * @param mod
     * @return
     */
    public DefaultComboBoxModel GetVozidlaMinKapacityVolne(int kap, String spz, DefaultComboBoxModel mod) {
        mod = new DefaultComboBoxModel();
        try {
            stored_pro = con.prepareCall("{call GetVozidlaMinKapacityVolne (?,?)}");
            stored_pro.setInt(1, kap);
            stored_pro.setString(2, spz);
            rs = stored_pro.executeQuery();
            while (rs.next()) {
                mod.addElement(rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return mod;
    }

    /**
     * Getter na kontajner na zaklade id
     *
     * @param id_kontajnera
     * @return
     */
    public Kontajner getKontajner(int id_kontajnera) {
        Kontajner kontajner = null;
        try {
            stored_pro = con.prepareCall("{call getKontajner (?)}");
            stored_pro.setInt(1, id_kontajnera);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            rs.next();
            kontajner = new Kontajner(
                    rs.getObject(1, Integer.class),
                    rs.getObject(2, String.class),
                    rs.getObject(3, String.class),
                    rs.getObject(4, Integer.class),
                    rs.getObject(5, Integer.class),
                    rs.getObject(6, Timestamp.class),
                    rs.getObject(7, Timestamp.class)
            );
            return kontajner;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return kontajner;
    }

    /**
     * Getter na graf
     *
     * @return
     */
    public Graf getGraph() {
        return graph;
    }

    /**
     * Metoda na pripravenei dat pred optimalizaciu
     *
     * @param s zoznam idcok stojisk
     * @param nazov_odpadu - nazov odpadu
     * @return
     */
    public VysledokOptimalizacie pripravDataNaOptimalizacia(LinkedList<Integer> s, String nazov_odpadu) {
        int Sum = 0;
        String idecka = "";
        for (Integer integer : s) {
            idecka += integer + ",";
        }
        idecka = idecka.substring(0, idecka.lastIndexOf(","));
        ArrayList<Integer> stojiska = new ArrayList<>();
        ArrayList<Integer> poziadavky = new ArrayList<>();
        ArrayList<String> adresy = new ArrayList<>();
        stojiska.add(0);
        adresy.add("Zberný dvor");
        poziadavky.add(Integer.MAX_VALUE);
        try {
            stored_pro = con.prepareCall("{call getZakazniciDynamics (?,?)}");
            stored_pro.setString(1, idecka);
            stored_pro.setString(2, nazov_odpadu);
            rs = stored_pro.executeQuery();
            Sum = 0;
            while (rs.next()) {
                Sum += rs.getObject(2, Double.class);
                stojiska.add(rs.getObject(1, Integer.class));
                poziadavky.add(rs.getObject(2, Integer.class));
                adresy.add(rs.getObject(3, String.class));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }

        ArrayList<Vozidlo> vozidla = new ArrayList<>();
        for (int[] is : vozidla_navrhovane) {
            vozidla.add(new Vozidlo("Nevybrane", is[0], is[1]));
        }
        vycisticVozidla();
        opt.setOptimalizacia(stojiska, poziadavky, adresy, vozidla, Sum, graph.getSubMatrixFromID(stojiska)/*, this*/);
        return opt.optimalizuj(nazov_odpadu);
    }

    /**
     * Metoda pre import grafu z GEOJASON
     *
     * @param url
     * @return
     */
    public Object[] importGrafuZGeoJason(String url) {
        graph = new Graf();
        graph.importGrafuZGEOJASON(url, getPocetStojisk());
        graph.upracGraf();
        Dijkstra dijkstra = new Dijkstra(graph);
        graph.setDmatrix(dijkstra.createDistanceMatrix());
        Object[] hlavicky = new Object[graph.getUzly().size() + 1];
        hlavicky[0] = new String("SRC/DEST");
        for (int i = 1; i < hlavicky.length; i++) {
            hlavicky[i] = graph.getUzly().get(i - 1).getId();
        }
        return hlavicky;
    }

    /**
     *
     * Parsovanie retazca na date
     *
     * @param date
     * @return
     */
    public static java.util.Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd.mm.yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Getter na stojiska na optimalizaciu
     *
     * @return
     */
    public ArrayList<StojiskoWaypoint> getPracovneStojiska() {
        return pracovneStojiska;
    }

    /**
     * Update kontajnera
     *
     * @param kontajner
     * @param operacia 0 - základna zmena aktualneho stavu, 1 - celkova zmena
     * parametrov, 2-aktualizacia z SMS
     */
    public void updateKontajner(Kontajner kontajner, int operacia) {
        try {
            stored_pro = con.prepareCall("{call updateKontajner (?,?,?,?,?,?)}");
            stored_pro.setInt(1, operacia);
            stored_pro.setInt(2, kontajner.id_kontajnera);
            stored_pro.setInt(3, Integer.parseInt(kontajner.adresa.substring(0, kontajner.adresa.indexOf(","))));
            stored_pro.setString(4, kontajner.nazovOdpadu);
            stored_pro.setInt(5, kontajner.aktualnyObjem);
            stored_pro.setInt(6, kontajner.maxObjem);
            stored_pro.registerOutParameter(1, Types.INTEGER);
            rs = stored_pro.executeQuery();
//            int i = stored_pro.getInt(1);
//            System.out.println("Operacia: " + i);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Vytvorenie kontajnera
     *
     * @param kontajner
     */
    public void createKontajner(Kontajner kontajner) {
        try {
            stored_pro = con.prepareCall("{call createKontajner (?,?,?,?)}");
            stored_pro.setInt(1, Integer.parseInt(kontajner.adresa.substring(0, kontajner.adresa.indexOf(","))));
            stored_pro.setString(2, kontajner.nazovOdpadu);
            stored_pro.setInt(3, kontajner.aktualnyObjem);
            stored_pro.setInt(4, kontajner.maxObjem);
            rs = stored_pro.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    /**
     * Vybranie z textu sms id_kontajneru
     *
     * @param text
     * @return
     */
    public int getIdKontajnerufromSMS(String text) {
        int index1 = text.indexOf("ID:");
        int index2 = text.indexOf("#");
        try {
//            System.out.println("Hladany kontajner: " + text.substring(index1 + 3, index2));
            return Integer.parseInt(text.substring(index1 + 3, index2));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return -1;
    }

    /**
     * Vyber z textu sms novy objem kontajnera
     *
     * @param text
     * @return
     */
    public int getNovyObjemfromSMS(String text) {
        int index1 = (text.indexOf("#"));
        int index2 = (text.indexOf("%"));
        try {
//            System.out.println("Hladany objem: " + text.substring(index1 + 1, index2));
            return Integer.parseInt(text.substring(index1 + 1, index2));
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * Metoda na spracovanie priajatej davky sprav, aktualizacia kontajnerov,
     * pridanie sprav do nespracovanych
     */
    public void spracujPrijateSpravy() {
        try {
            PrijataDavka davka = getMsg().stiahniSMS();
//            System.out.println(davka.getId_stav());
            if (!davka.getId_stav().substring(0, 2).equalsIgnoreCase("OK")) {
                JOptionPane.showMessageDialog(null, "" + davka.getId_stav());
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Počet prijatých správ: " + davka.getPrijateSpravy().size());
            }
            stored_pro = con.prepareCall("{call updateKontajner (?,?,?,?,?,?)}");
            String txt;
            for (PrijataSprava prijataSprava : davka.getPrijateSpravy()) {
                stored_pro.setInt(1, 2); // operacia2 updateZsms
                txt = prijataSprava.getText();
                txt = txt.toUpperCase();
                int id_kontajnera = getIdKontajnerufromSMS(txt);
                int novyObjem = getNovyObjemfromSMS(txt);
                if (id_kontajnera == -1 || novyObjem < 0 || novyObjem > 100) {
                    nespracovaneSpravy.add(prijataSprava);
                    continue;
                }
                stored_pro.setInt(2, id_kontajnera);
                stored_pro.setInt(3, -1); // adresa
                stored_pro.setString(4, "-"); //druhodpadu
                stored_pro.setInt(5, novyObjem);
                stored_pro.setInt(6, -1); //maxObjem
                stored_pro.registerOutParameter(1, Types.INTEGER);
                stored_pro.execute();
                if (stored_pro.getInt(1) != 2) { // nebolo mozne spracovat nenasiel sa taky sudak
                    nespracovaneSpravy.add(prijataSprava);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Vyprazdnenie kontajnera
     *
     * @param id_kontajner
     */
    public void vyprazdniKontajner(int id_kontajner) {
        try {
            stored_pro = con.prepareCall("{call vysypKontajner (?)}");
            stored_pro.setInt(1, id_kontajner);
            rs = stored_pro.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
    }

    /**
     * Konvertovanie Date na SQL date
     *
     * @param date
     * @return
     */
    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * Getter na messenger
     *
     * @return
     */
    public Messenger getMsg() {
        return msg;
    }

    /**
     * Getter na vysledok optimalizacie
     *
     * @return
     */
    public VysledokOptimalizacie getVysloOPT() {
        return VysloOPT;
    }

    /**
     * Setter na vysledok optimalizacie
     *
     * @param VysloOPT
     */
    public void setVysloOPT(VysledokOptimalizacie VysloOPT) {
        this.VysloOPT = VysloOPT;
    }

    /**
     * Vratenie LinkedListu idecok stojisk z trasy
     *
     * @param trasa
     * @return
     */
    public LinkedList<Integer> vratCestu(Trasa trasa) {
        LinkedList<Integer> zakaznici = new LinkedList<>();
        for (Zakaznik zakaznik : trasa.getZakazniciTrasy()) {
            zakaznici.add(zakaznik.getId_zakaznika());
        }
        return zakaznici;
    }

//    public LinkedList< LinkedList<Hrana>> zobrazTrasy() {
//        LinkedList< LinkedList<Hrana>> hrany = new LinkedList<>();
//        if (VysloOPT.getTrasy() != null && VysloOPT.getTrasy().size() > 0) {
//            LinkedList<Integer> cesta;
//            for (Trasa trasa : VysloOPT.getTrasy()) {
//                cesta = vratCestu(trasa);
//
//                hrany.add(graph.vratHrany(graph.vratKompletneCestu(cesta)));
//            }
//        }
//        return hrany;
//    }
    /**
     * Setter nespracovanych sprav
     *
     * @param nespracovaneSpravy
     */
    public void setNespracovaneSpravy(LinkedList<PrijataSprava> nespracovaneSpravy) {
        this.nespracovaneSpravy = nespracovaneSpravy;
    }

    /**
     * Getter na combobox model pre navrhovane trasy
     *
     * @param mod
     * @return
     */
    public DefaultComboBoxModel getcmbTrasy(DefaultComboBoxModel mod) {
        mod = new DefaultComboBoxModel();
        for (int i = 0; i < VysloOPT.getTrasy().size(); i++) {
            mod.addElement("Trasa: " + i);
            if (VysloOPT.getTrasy().get(i).getAlternativnaTrasa() != null) {
                mod.addElement("Alternatívna: " + i);
            }
        }
        return mod;
    }

    /**
     * Getter na table model pre vybrane vozidla
     *
     * @param mod
     * @param priority
     * @return
     */
    public DefaultTableModel getVybraneVozidla(DefaultTableModel mod, int[] priority) {
        if (priority != null) {
            for (int i = 0; i < priority.length; i++) {
                vozidla_navrhovane.get(i)[1] = priority[i];
            }
        }
//        for (int[] is : vozidla_navrhovane) {
//            System.out.println("Kapacita:" + is[0] + " Priorita:" + is[1]);
//        }
        Object[] hlavicky = new Object[vozidla_navrhovane.size() + 1];
        hlavicky[0] = "";
        for (int i = 1; i < hlavicky.length; i++) {
            hlavicky[i] = "vozidlo" + i;
        }
        Object[][] data = new Object[3][(hlavicky.length + 1)];
        data[0][0] = "Kapacita:";
        data[1][0] = "Priorita:";
        data[2][0] = "Vyhoď";

        for (int i = 0; i < vozidla_navrhovane.size(); i++) {
            data[0][i + 1] = vozidla_navrhovane.get(i)[0];
            data[1][i + 1] = vozidla_navrhovane.get(i)[1];
        }
        mod = new DefaultTableModel(data, hlavicky) {
            public Class<?> getColumnClass(int column) {
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (rowIndex == 1 && columnIndex > 0) {
                    return true;
                }
                return false;
            }

        };
        return mod;
    }

    /**
     * Getter na tablemodel pre tabulku vybranych vozidiel
     *
     * @param novaKapacita
     * @param mod
     * @param priority
     * @return
     */
    public DefaultTableModel getVybraneVozidla(int novaKapacita, DefaultTableModel mod, int[] priority) {
        if (priority != null) {
            for (int i = 0; i < priority.length; i++) {
                vozidla_navrhovane.get(i)[1] = priority[i];
            }
        }
        if (novaKapacita >= 1) {
            pridajVozidlo(new int[]{novaKapacita, 5});
        }
        Object[] hlavicky = new Object[vozidla_navrhovane.size() + 1];
        hlavicky[0] = "";
        for (int i = 1; i < hlavicky.length; i++) {
            hlavicky[i] = "vozidlo" + i;
        }
        Object[][] data = new Object[3][(hlavicky.length + 1)];
        data[0][0] = "Kapacita:";
        data[1][0] = "Priorita:";
        data[2][0] = "Vyhoď";

        for (int i = 0; i < vozidla_navrhovane.size(); i++) {
            data[0][i + 1] = vozidla_navrhovane.get(i)[0];
            data[1][i + 1] = vozidla_navrhovane.get(i)[1];
        }
        mod = new DefaultTableModel(data, hlavicky) {
            public Class<?> getColumnClass(int column) {
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (rowIndex == 1 && columnIndex > 0) {
                    return true;
                }
                return false;
            }

        };
        return mod;
    }

    /**
     * Getter na model tabulky pre vybrane vozidla
     *
     * @param index
     * @param mod
     * @return
     */
    public DefaultTableModel getVybraneVozidla(int index, DefaultTableModel mod) {
        odoberVozidlo(index);
        Object[] hlavicky = new Object[vozidla_navrhovane.size() + 1];
        hlavicky[0] = "";
        for (int i = 1; i < hlavicky.length; i++) {
            hlavicky[i] = "vozidlo" + i;
        }
        Object[][] data = new Object[3][(hlavicky.length + 1)];
        data[0][0] = "Kapacita:";
        data[1][0] = "Priorita:";
        data[2][0] = "Vyhoď";

        for (int i = 0; i < vozidla_navrhovane.size(); i++) {
            data[0][i + 1] = vozidla_navrhovane.get(i)[0];
            data[1][i + 1] = vozidla_navrhovane.get(i)[1];
        }
        mod = new DefaultTableModel(data, hlavicky) {
            public Class<?> getColumnClass(int column) {
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (rowIndex == 1 && columnIndex > 0) {
                    return true;
                }
                return false;
            }

        };
        return mod;
    }

    /**
     * Getter na stojiska jazdy na zaklade id_jazdy
     *
     * @param id
     * @param mod
     * @return
     */
    public DefaultTableModel getStojiskaJazdy(int id, DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getStojiskaJazdy (?)}");
            stored_pro.setInt(1, id);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();

            LinkedList<Object[]> stlpce = new LinkedList<>();

            while (rs.next()) {
                stlpce.add(new Object[]{rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4)});
            }

            Object[][] data = new Object[4][(stlpce.size() + 1)];
            Object[] hlavicky = new Object[stlpce.size() + 1];

            hlavicky[0] = "id_stojika:";
            data[0][0] = "Adresy:";
            data[1][0] = "Poradie:";
            data[2][0] = "Očakávaný obj:";
            data[3][0] = "Zozbieraný obj:";
            for (int i = 0; i < stlpce.size(); i++) {

                hlavicky[i + 1] = stlpce.get(i)[0];
                data[0][i + 1] = stlpce.get(i)[1];
                data[1][i + 1] = stlpce.get(i)[3];
                data[2][i + 1] = stlpce.get(i)[2];
                data[3][i + 1] = stlpce.get(i)[2];
            }

            mod = new DefaultTableModel(data, hlavicky) {
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    if (rowIndex == 3
                            && columnIndex > 1
                            && columnIndex < data[0].length - 1) {
                        return true;
                    }
                    return false;
                }
            };
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na table model tabulku editaciu jazdy
     *
     * @param index
     * @param jeHlavna
     * @param mod
     * @return
     */
    public DefaultTableModel getTableZasah(int index, boolean jeHlavna, DefaultTableModel mod) {
        try {
            Trasa t = VysloOPT.getTrasy().get(index);
//            System.out.println(Arrays.toString(t.getZakazniciTrasy().toArray()));
            ArrayList<Zakaznik> zakAltr = new ArrayList<>();
            if (!jeHlavna) {
                ArrayList<Integer> allt = VysloOPT.getTrasy().get(index).getAlternativnaTrasa();
//                System.out.println(Arrays.toString(allt.toArray()));
                for (int i = 0; i < allt.size(); i++) {
                    int id = allt.get(i);
                    for (Zakaznik zakaznik : t.getZakazniciTrasy()) {
                        if (id == zakaznik.getId_zakaznika()) {
                            zakAltr.add(zakaznik);
                            break;
                        }
                    }
                }
//                System.out.println(Arrays.toString(zakAltr.toArray()));
            }

            int columnCount = t.getZakazniciTrasy().size();
            Object[] hlavicky = new String[columnCount + 1];
            Object[][] data = new Object[4][(hlavicky.length + 1)];
            hlavicky[0] = "Id_stojiska:";
            data[0][0] = "Adresy:";
            data[1][0] = "Kapacita:";
            data[2][0] = "Navrh. poradie:";
            data[3][0] = "Odober";

            if (jeHlavna) {
                for (int i = 1; i < hlavicky.length; i++) {
//                System.out.println("Zakaznik: " + t.getZakazniciTrasy().get(i - 1).toString());
                    hlavicky[i] = "" + t.getZakazniciTrasy().get(i - 1).getId_zakaznika();
                    data[0][i] = t.getZakazniciTrasy().get(i - 1).getAdresa();
                    data[1][i] = t.getZakazniciTrasy().get(i - 1).getPoziadavka();
                    data[2][i] = i - 1;
                }
            } else {
                for (int i = 1; i < hlavicky.length; i++) {
//                System.out.println("Zakaznik: " + t.getZakazniciTrasy().get(i - 1).toString());
                    hlavicky[i] = "" + zakAltr.get(i - 1).getId_zakaznika();
                    data[0][i] = zakAltr.get(i - 1).getAdresa();
                    data[1][i] = zakAltr.get(i - 1).getPoziadavka();
                    data[2][i] = i - 1;
                }
            }

            data[1][1] = "-";
            data[1][hlavicky.length - 1] = "-";
            mod = new DefaultTableModel(data, hlavicky) {
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }

                @Override
//                public boolean isCellEditable(int rowIndex, int columnIndex) {
//                    if (columnIndex == 0 || columnIndex == 1 || columnIndex == hlavicky.length - 1) {
//                        return false;
//                    } else {
//                        return !(rowIndex == 0 || rowIndex == 1 || rowIndex == 2);
//                    }
//                }
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Metoda na vyratanie uspery vymenenim stojiska v jazde
     *
     * @param vsuvaneMiesto_predchodca
     * @param vyberaneMiesto_predchodca
     * @param veberaneMiesto_nasledovnik
     * @param presuvany
     * @param vsuvaneMiesto_Nasledovniky
     * @return
     */
    public int vyratajUspory(int vsuvaneMiesto_predchodca, int vyberaneMiesto_predchodca,
            int veberaneMiesto_nasledovnik, int presuvany, int vsuvaneMiesto_Nasledovniky) {
        return getGraph().vyratajUsporu(vsuvaneMiesto_predchodca, vyberaneMiesto_predchodca,
                veberaneMiesto_nasledovnik, presuvany, vsuvaneMiesto_Nasledovniky);
    }

    public int vyratajUsporuPriZmazani(int predchodca, int mazany, int nasledovnik) {
        return getGraph().vyratajUsporuPriZmazani(predchodca, mazany, nasledovnik);
    }

    /**
     * Vytvorenie jazdy
     *
     * @param s - idecok stojisk
     * @param o - retazec ocakavanzch objemov
     * @param dlzkaTrasy
     * @param potrebneZvozit
     * @param spz
     * @param odpad
     */
    public void createJazda(String s, String o, int dlzkaTrasy, int potrebneZvozit, String spz, String odpad) {
        try {
            stored_pro = con.prepareCall("{call createJazda (?,?,?,?,?,?)}");
            if (spz.length() == 7) {
                stored_pro.setString(1, spz);
            } else {
                stored_pro.setString(1, null);
            }
            stored_pro.setInt(2, dlzkaTrasy);
            stored_pro.setInt(3, potrebneZvozit);
            stored_pro.setString(4, odpad);
            stored_pro.setString(5, s);
            stored_pro.setString(6, o);
            stored_pro.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Getter na model tabulky pre knihu jazd
     *
     * @param SPZ
     * @param datum_od
     * @param datum_do
     * @param stav
     * @param odpad
     * @param mod
     * @return
     */
    public DefaultTableModel getKnihaJazd(String SPZ, Date datum_od, Date datum_do, String stav, String odpad, DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getJazdy (?,?,?,?,?)}");
            if (SPZ.equals("Všetky")) {
                SPZ = null;
                stored_pro.setString(1, SPZ);
                //stored_pro.setString(1, null);
            } else {
                stored_pro.setString(1, SPZ);
            }
            if (datum_od != null) {
                stored_pro.setDate(2, convertJavaDateToSqlDate(datum_od));
            } else {
                stored_pro.setDate(2, null);
            }
            if (datum_do != null) {
                stored_pro.setDate(3, convertJavaDateToSqlDate(datum_do));
            } else {
                stored_pro.setDate(3, null);
            }
            if (stav.equals("Všetky")) {
                stav = null;
                stored_pro.setString(4, stav);
                //stored_pro.setString(4, null);
            } else {
                stored_pro.setString(4, stav);
            }
            System.out.println(odpad);
            if (odpad.equals("Všetky")) {
                odpad = null;
                stored_pro.setString(5, odpad);
//                stored_pro.setString(5, null);
            } else {
                stored_pro.setString(5, odpad);
            }
//            System.out.println("call getJazdy (" + SPZ + "," + datum_od + "," + datum_do + "," + stav + "," + odpad + ")");
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == 13) {
                        return Icon.class;
                    }
                    if (column == 10 || column == 11 || column == 9) {
                        return Double.class;
                    }

                    return Object.class;
                }

//                boolean[] canEdit = new boolean[]{
//                    false, false, false, false, false, false, false, false, false
//                };
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };

            Object[] header = new Object[columnCount + 4];
            header = new String[]{"ID_jazdy", "ŠPZ", "stav", "dátum", "účel",
                "očakávaný Obj [L]", "zozbieraný Obj [L]", "%-porovnanie Obj", "Kap.vozidla", "%-vyťaženie",
                "očakávaná Vzd [Km]", "najazdená Vzd [Km]", "%-rozdiel", "graf"};
//            int j = 0;
//            for (int i = 0; i < header.length; i++) {
//                if (i == 13) {
//                    header[i] = "graf";
//                    System.out.println(header[i]);
//                    continue;
//                }
//                System.out.println(rsmd.getColumnName(j + 1));
//                header[i] = rsmd.getColumnName(j + 1);
//                j++;
//            }

//            System.out.println("Hlavicky: " + Arrays.toString(header));
            mod.setColumnIdentifiers(header);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
            URL resource = getClass().getResource("statistics.png");
            Icon icon = new ImageIcon(resource);
            double ocak;
            double real;
            while (rs.next()) {
                Object[] data = new Object[columnCount + 4];
                int p = 0;
                for (int i = 0; i < data.length; i++) {
                    switch (i) {
                        case 3:
                            data[i] = simpleDateFormat1.format(rs.getObject(i + 1));
                            break;

                        case 7:
                            ocak = Integer.parseInt("" + data[i - 2]);
                            if (ocak == 0) {
                                ocak = 0.0000000001;
                            }
                            real = Integer.parseInt("" + data[i - 1]);
                            data[i] = Math.floor((real / ocak * 100.0) * 100 + .5) / 100;
                            p++;
                            break;
                        case 9:
                            if(Double.parseDouble("" + data[i - 1])==0){
                                 data[i] = 999999.99;
                                p++;
                                break;
                            }
                            data[i] = Math.floor(((Double.parseDouble("" + data[i - 3]) / (Double.parseDouble("" + data[i - 1])) * 100.0) * 100 + .5) / 100);
                            p++;
                            break;

                        case 10:
                            data[i] = Math.floor((Double.parseDouble("" + rs.getObject(i + 1-p)) / 1000) * 100 + .5) / 100;
                          
                            break;
                        case 11:
                            data[i] = Math.floor((Double.parseDouble("" + rs.getObject(i + 1-p)) / 1000) * 100 + .5) / 100;                           
                            break;
                        case 12:
                            ocak = Double.parseDouble("" + data[i - 2]);
                            if (ocak == 0) {
                                data[i] = 999999.99;
                                break;                                
                            }
                            real = Double.parseDouble("" + data[i - 1]);
                            data[i] = Math.floor((real / ocak * 100.0) * 100 + .5) / 100;
                            p++;
                            break;
                        case 13:
                            data[i] = icon;//                          
                            p++;
                            break;

                        default:
                            data[i] = rs.getObject(i + 1 - p);
//                            System.out.println(data[i].toString());
                            break;
                    }
                }
                mod.addRow(data);
            }
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Pomocna metoda na nastavenie table modelu
     *
     * @param mod
     * @return
     * @throws SQLException
     */
    private DefaultTableModel helpGetJazdy(DefaultTableModel mod) throws SQLException {
        int columnCount = rsmd.getColumnCount();
        mod = new DefaultTableModel() {
            Object[][] data;

            public Class<?> getColumnClass(int column) {
                if (column == 4) {
                    return String.class;
                }
                return Object.class;
            }
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        Object[] header = new Object[columnCount];
        for (int i = 1; i <= header.length; i++) {
//            System.out.println(rsmd.getColumnName(i));
            header[i - 1] = rsmd.getColumnName(i);
        }
        mod.setColumnIdentifiers(header);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy");

        while (rs.next()) {
            Object[] data = new Object[columnCount];
            for (int i = 0; i < data.length; i++) {
                switch (i) {
                    case 4:
                        data[i] = simpleDateFormat1.format(rs.getObject(i + 1));
                        break;
                    default:
                        data[i] = rs.getObject(i + 1);
                        break;
                }
            }
            mod.addRow(data);
        }
        return mod;
    }

    /**
     * Getter na tablemodel pre tabulku Aktualnych jazd
     *
     * @param mod
     * @return
     */
    public DefaultTableModel getJazdyAktual(DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getJazdyAktual ()}");
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Altualizacia tabulky aktualnych jazd
     *
     * @param mod
     * @param id
     * @param spz
     * @return
     */
    public TableModel updateAktualneJazdySPZ(DefaultTableModel mod, int id, String spz) {
        try {
            stored_pro = con.prepareCall("{call updateJazdyAktualSPZ (?,?)}");
            stored_pro.setInt(1, id);
            stored_pro.setString(2, spz);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Aktualizovanie tabulky aktualnych jazd zahajenim jazdy
     *
     * @param mod
     * @param id
     * @param spz
     * @return
     */
    public TableModel updateAktualne_zahajenieJazdy(DefaultTableModel mod, int id, String spz) {
        try {
            stored_pro = con.prepareCall("{call updateAktualne_zahajenieJazdy (?,?)}");
            stored_pro.setInt(1, id);
            stored_pro.setString(2, spz);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;

    }

    /**
     * Aktualizovanie tabulky aktualnych jazd ukoncenim jazdy
     *
     * @param mod
     * @param id
     * @return
     */
    public TableModel updateAktualne_ukončenieJazdy(DefaultTableModel mod, int id) {
        try {
            stored_pro = con.prepareCall("{call updateAktualne_ukoncenie (?)}");
            stored_pro.setInt(1, id);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Getter na stav tachometra vozidla na zaklade spz
     *
     * @param spz
     * @return
     */
    public int getStavTachometra(String spz) {
        try {
            stored_pro = con.prepareCall("{call getStavTachometra (?)}");
            stored_pro.setString(1, spz);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            rs.next();
            int stav = rs.getObject(1, Integer.class);
            return stav;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return -1;
    }

    /**
     * Aktualizovanie tabulky aktualnych jazd uzavretim jazdy
     *
     * @param mod
     * @param id
     * @param najazdene
     * @param objem
     * @param s
     * @return
     */
    public TableModel updateAktualne_uzavretieJazdy(DefaultTableModel mod, int id, int najazdene, int objem, String s) {
        try {
            stored_pro = con.prepareCall("{call updateAktualne_uzavretie (?,?,?,?)}");
            stored_pro.setInt(1, id);
            stored_pro.setInt(2, najazdene);
            stored_pro.setInt(3, objem);
            stored_pro.setString(4, s);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Aktualizacia tabulky aktualnych jazd vymazanim jazdy
     *
     * @param mod
     * @param id
     * @return
     */
    public TableModel deleteJazda(DefaultTableModel mod, int id) {
        try {
            stored_pro = con.prepareCall("{call vymazJazdu (?)}");
            stored_pro.setInt(1, id);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Aktualizovanie tabulky aktualnych jazd vytvorenim novej servisovej jazdy
     *
     * @param mod
     * @param spz
     * @param predmet
     * @param najazdene
     * @return
     */
    public TableModel createServisovaJazda(DefaultTableModel mod, String spz, String predmet, int najazdene) {
        try {
            stored_pro = con.prepareCall("{call createServisovaJazda (?,?,?)}");
            stored_pro.setString(1, spz);
            stored_pro.setString(2, predmet);
            stored_pro.setInt(3, najazdene);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }

    /**
     * Pomocna metoda na vynulovanie tabulky
     *
     * @param mod
     * @return
     */
    public TableModel clear(DefaultTableModel mod) {
        mod = new DefaultTableModel();
        return mod;
    }

    /**
     * Update servisovej jazdy
     *
     * @param mod
     * @param id
     * @param predmet
     * @param najazdene
     * @return
     */
    public TableModel updateServisovaJazda(DefaultTableModel mod, int id, String predmet, int najazdene) {
        try {
            stored_pro = con.prepareCall("{call updateServisovaJazda (?,?,?)}");
            stored_pro.setInt(1, id);
            stored_pro.setString(2, predmet);
            stored_pro.setInt(3, najazdene);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            return helpGetJazdy(mod);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;

    }

    /**
     * Odobranie trasy z navrhovanych
     *
     * @param poradie
     * @param mod
     * @return
     */
    public DefaultComboBoxModel odoberNavrhTrasy(int poradie, DefaultComboBoxModel mod) {
        VysloOPT.odoberTrasu(poradie);
        return getcmbTrasy(mod);
    }

    public void zobrazGrafVyvojaOdpaduVNadobe(int id_kontajnera) {
        try {
//            String query = "Select DATE_FORMAT(h.datum, '%d.%m.%Y'), h.stav from historia h\n"
//                    //+ "    where h.id_kontajnera=" + id_kontajnera + " and h.id = (select max(id) from historia ho where  DATE_FORMAT(h.datum, '%d.%m.%Y') = DATE_FORMAT(ho.datum, '%d.%m.%Y'))\n"
//                    //+ "    and h.datum > (NOW() - INTERVAL 2 MONTH)\n"
//                    + "    order by h.datum;";

            String query = "	Select DATE_FORMAT(h.datum, '%d.%m.%Y'), h.stav from historia h\n"
                    + "				where h.id in ( \n"
                    + "                                                   select max(id) from historia ho \n"
                    + "                                                   where id_kontajnera =" + id_kontajnera + " group by DATE_FORMAT(ho.datum, '%d.%m.%Y'))\n"
                    + "				and h.datum > (NOW() - INTERVAL 2 MONTH)\n"
                    + "				order by h.datum";
            JDBCCategoryDataset d = new JDBCCategoryDataset(con, query);
            JFreeChart chart = ChartFactory.createBarChart("Vývoj stavu odpadu v kontajneri: " + id_kontajnera, "Dátum", "% - naplnenosť", d, PlotOrientation.HORIZONTAL, false, false, false);
            ChartFrame frame = new ChartFrame("Graf vývoja objemu kontajnera: " + id_kontajnera, chart);
            frame.setVisible(true);
            Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setMaximumSize(DimMax);
            frame.setSize(DimMax);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, getClass().getName());
        }
    }
    /**
     * Graf vyvoja odpadu na stojisku;
     * @param id_stojiska
     * @param druhOdpadu 
     */
    public void zobrazGrafVyvojaOdpaduNaStojisku(int id_stojiska, String druhOdpadu) {
        try {
            String query = "select DATE_FORMAT(datum, '%d.%m.%Y'), sum(stav*k.maxObjem)/sum(k.maxObjem) from historia\n"
                    + "join kontajner k using(id_kontajnera)\n"
                    + "where id in (select max(id) from historia\n"
                    + "				where id_kontajnera in ( select id_kontajnera from kontajner \n"
                    + "							join stojisko s using(id_stojiska)\n"
                    + "							where id_stojiska = " + id_stojiska + "\n"
                    + "							and id_odpadu = (select id_odpadu from druh_odpadu where nazov_Odpadu ='" + druhOdpadu + "'))\n"
                    + "				group by id_kontajnera,DATE_FORMAT(datum, '%d.%m.%Y'))\n"
                    + "group by DATE_FORMAT(datum, '%d.%m.%Y');";
            JDBCCategoryDataset d = new JDBCCategoryDataset(con, query);
            JFreeChart chart = ChartFactory.createBarChart("Vývoj stavu odpadu v stojisku: " + id_stojiska + " pre " + druhOdpadu, "Dátum", "% - naplnenosť", d, PlotOrientation.HORIZONTAL, false, false, false);
            ChartFrame frame = new ChartFrame("Graf vývoja objemu odpadu na Stojisku: " + id_stojiska, chart);
            frame.setVisible(true);
            Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setMaximumSize(DimMax);
            frame.setSize(DimMax);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, getClass().getName());
        }
    }
/**
 * Zobrazenie grafu pre stojiska jazdy
 * @param id_jazdy
 * @param percenta - ak zozobiera true, zobrazi percenta, ak false este nezozbierane zobrazi objem
 */
    public void zobrazGrafStojiskJazdy(int id_jazdy, boolean percenta) {
        try {
            String query = "select concat(poradie,'>>>', id_stojiska, ':',adresa), odvezenyObjem from stojiska_jazdy  join stojisko using (id_stojiska)               \n"
                    + "               where id_jazdy =" + id_jazdy + " and id_stojiska !=0 order by poradie;";
            JDBCCategoryDataset d = new JDBCCategoryDataset(con, query);
            JFreeChart chart = null;
            if (percenta) {
                chart = ChartFactory.createBarChart("Stojiska jazdy: " + id_jazdy, "Stojiská", "% - zobrania", d, PlotOrientation.HORIZONTAL, false, false, false);
            } else {
                chart = ChartFactory.createBarChart("Stojiska jazdy: " + id_jazdy, "Stojiská", "objem na zozbieranie", d, PlotOrientation.HORIZONTAL, false, false, false);
            }
            ChartFrame frame = new ChartFrame("Graf ", chart);
            frame.setVisible(true);
            Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setMaximumSize(DimMax);
            frame.setSize(DimMax);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, getClass().getName());
        }
    }
    /**
     * Getter na model tabulky kontajnerov
     * @param id_kontajnera
     * @param mod
     * @return 
     */
    public DefaultTableModel getKontajnery(int id_kontajnera, DefaultTableModel mod) {
        try {
            stored_pro = con.prepareCall("{call getKontajnerdoTabulky (?)}");
            stored_pro.setInt(1, id_kontajnera);
            rs = stored_pro.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == 0 || column == 1 || column == 2 || column == 3) {
                        return Integer.class;
                    } else if (column == (columnCount - 2)) {
                        return Boolean.class;
                    } else if (column == (columnCount - 1) || column == (columnCount)) {
                        return Icon.class;
                    }
                    return Object.class;
                }

                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, true, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };

            Object[] header = new Object[columnCount + 3];
            header = new String[]{"ID_stojiska", "ID_kontajnera", "odpad", "%-naplnenia",
                "maxObjem [L]", "adresa", "Posledné Vyprázdnenie", "Posledná aktualizcia",
                "Vybrať", "Vývoj", "Vývoj", "x", "y"};

            mod.setColumnIdentifiers(header);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            URL resource = getClass().getResource("statistics.png");
            Icon icon = new ImageIcon(resource);
            URL resource1 = getClass().getResource("bank-1.png");
            Icon icon1 = new ImageIcon(resource1);
            while (rs.next()) {
                Object[] data = new Object[columnCount + 3];
                for (int i = 0; i < data.length; i++) {
                    if (i == columnCount - 2) {
                        data[i] = true;
                        data[i + 1] = icon;
                        data[i + 2] = icon1;
                        data[i + 3] = rs.getObject(i + 1);
                        data[i + 4] = rs.getObject(i + 2);
                        break;
                    }
                    switch (i) {
                        case 6:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = simpleDateFormat1.format(rs.getObject(i + 1));
                            break;
                        case 7:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = simpleDateFormat2.format(rs.getObject(i + 1));
                            break;

                        default:
//                            System.out.println(rs.getObject(i + 1));
                            data[i] = rs.getObject(i + 1);
                            break;
                    }
                }
                mod.addRow(data);
            }
            reloadStojiska(mod);
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }
    /**
     * nasetovanie oblasti, stojisk
     */
    public void startOPstijiska() {
        oblasti = new LinkedList<>();
        suradnice = new LinkedList<>();
    }
/**
 * Pridane stojiska
 * @param x
 * @param y 
 */
    public void pridajSuradnicu(double x, double y) {
        suradnice.add(new GeoPosition(x, y));
//        System.out.println("suradnice:" + Arrays.toString(suradnice.toArray()));
    }
    /**
     * Prianie oblasti
     * @param s suradnuce krajny bodov
     * @param poziadavka - poziadavka oblasti
     */
    public void pridajOblast(LinkedList<GeoPosition> s, int poziadavka) {
        oblasti.add(new Oblast(oblasti.size(), (LinkedList<GeoPosition>) s.clone(), poziadavka));
    }
    /**
     * getter na model tabulky pre oblasti
     * @param mod
     * @return 
     */
    public DefaultTableModel getOblasti(DefaultTableModel mod) {
        try {
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == 4) {
                        return Icon.class;

                    } else {
                        return Object.class;
                    }
                }
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false};

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
            Object[] header = {"id", "poziadavka", "x", "y", ""};
            mod.setColumnIdentifiers(header);
            URL resource = getClass().getResource("remove.png");
            Icon icon = new ImageIcon(resource);
            if (oblasti != null && oblasti.size() > 0) {

                for (Oblast o : oblasti) {
                    Object[] data = new Object[header.length];
                    data[0] = o.getId();
                    data[1] = o.getPoziadavka();
                    int i = 0;
                    double x = 0.0;
                    double y = 0.0;
                    for (GeoPosition suradnice : o.getBody()) {
                        i++;
                        x += suradnice.getLatitude();
                        y += suradnice.getLongitude();
                    }
                    data[2] = x / i;
                    data[3] = y / i;
                    data[4] = icon;
                    mod.addRow(data);
                }
            }
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }
/**
 * Get na model tabulky pre stojiska
 * @param mod
 * @return 
 */
    public DefaultTableModel getSuradnice(DefaultTableModel mod) {
        try {
            mod = new DefaultTableModel() {
                public Class<?> getColumnClass(int column) {
                    if (column == 3) {
                        return Icon.class;
                    }
                    return Object.class;
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            Object[] header = {"id", "x", "y", "X"};
            mod.setColumnIdentifiers(header);
            URL resource = getClass().getResource("remove.png");
            Icon icon = new ImageIcon(resource);
            if (suradnice != null && suradnice.size() > 0) {
                int i = 0;
                for (GeoPosition s : suradnice) {
                    Object[] data = new Object[header.length];
                    data[0] = i;
                    data[1] = s.getLatitude();
                    data[2] = s.getLongitude();
                    data[3] = icon;
                    i++;
                    mod.addRow(data);
                }
            }
            return mod;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return mod;
    }
    /**
     * Getter na stojiska
     * @return 
     */
    public LinkedList<GeoPosition> getSuradnice() {
        return suradnice;
    }
    /**
     * Getter na oblasti
     * @return 
     */
    public LinkedList<Oblast> getOblasti() {
        return oblasti;
    }
    /**
     * odobratie stojiska
     * @param p 
     */
    public void odoberSuradnicu(int p) {
        suradnice.remove(p);

    }
    /**
     * Odobretie oblasti podla id
     * @param i 
     */
    public void odoberOblast(int i) {
        oblasti.remove(i);
        while (i < oblasti.size()) {
            oblasti.get(i).setId(oblasti.get(i).getId() - 1);
            i++;
        }

    }
    /**
     * Nastavenie a spustenie geneticke algorimtu
     * @param pocetCentier
     * @return  vysledok
     */
    public int[] optimalizujPMedian(int pocetCentier) {
        LinkedList<Suradnice> zakaznici = new LinkedList<>();
        LinkedList<Suradnice> umiestnenia = new LinkedList<>();
        LinkedList<Integer> poziadavky = new LinkedList<>();
        for (Oblast o : oblasti) {
            zakaznici.add(new Suradnice(o.suradnica.getLatitude(), o.suradnica.getLongitude()));
            poziadavky.add(o.poziadavka);
        }

        for (GeoPosition geoPosition : suradnice) {
            umiestnenia.add(new Suradnice(geoPosition.getLatitude(), geoPosition.getLongitude()));
        }
        return opt.optimalizujPMedian(umiestnenia, zakaznici, poziadavky, pocetCentier);
    }
    /**
     * 
     * @param file 
     */
    public void saveMozneUmiestnenia(File file) {
        StringBuilder builder = new StringBuilder();
        for (GeoPosition geoPosition : suradnice) {
            builder.append(geoPosition.getLatitude()).append(" ").append(geoPosition.getLongitude()).append("\n");
        }
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(file));
//            System.out.println(builder.toString());
            wr.write(builder.toString());
            wr.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getClass().getName());
        }
    }

    public void saveOblastiToFile(File file) {
        StringBuilder builder = new StringBuilder();
        for (Oblast o : oblasti) {
            String s = "";
            for (GeoPosition geoPosition : o.getBody()) {
                s += geoPosition.getLatitude() + "," + geoPosition.getLongitude() + ",";
            }
            builder.append(o.poziadavka).append(" ")
                    .append(o.getSuradnica().getLatitude()).append(" ").
                    append(o.getSuradnica().getLongitude()).append(" ").append(s).append("\n");
        }
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(file));
//            System.out.println(builder.toString());
            wr.write(builder.toString());
            wr.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getClass().getName());
        }
    }
    /**
     * Nacitanie stojisk zo suboru
     * @param fileToLoad 
     */
    public void loadMozneStojiska(File fileToLoad) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
            String line = reader.readLine();
            suradnice = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] column = line.split(" ");
                suradnice.add(new GeoPosition(Double.parseDouble(column[0]), Double.parseDouble(column[1])));
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }
/**
 * Nacitanie oblasti zo suboru
 * @param fileToLoad 
 */
    public void loadMozneOblasti(File fileToLoad) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
            oblasti = new LinkedList<>();
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                LinkedList<GeoPosition> body = new LinkedList<>();
//                System.out.println(line);
                String[] column = line.split(" ");
                String s = column[3];
//                System.out.println(s);
                String[] b = s.split(",");
//                System.out.println(Arrays.toString(b));
                for (int j = 0; j < b.length; j = j + 2) {
                    body.add(new GeoPosition(Double.parseDouble(b[j]), Double.parseDouble(b[j + 1])));
                }
                oblasti.add(new Oblast(i,
                        Integer.parseInt(column[0]),
                        new GeoPosition(Double.parseDouble(column[1]), Double.parseDouble(column[2])),
                        body));
                i++;
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }

}
