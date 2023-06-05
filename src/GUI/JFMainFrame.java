/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Messenger.Messenger;
import Messenger.PrijataSprava;
import Messenger.SMS;
import com.toedter.calendar.JDateChooser;
import Mapy.Mapa;
import dp.OdpadoveHospodarstvo;
import dp.VysledokOptimalizacie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 *
 * @author rendo
 */
public final class JFMainFrame extends javax.swing.JFrame {

    public OdpadoveHospodarstvo OHsystem;
    public Mapa mapa;
    public Mapa mapa2;
    public Mapa mapa3;
    public JFUpdate_Create_Vozidlo jFUpdate_Create_Vozidlo;
    Refresher r;
    public TableColumn col_start;
    public TableColumn col_end;
    int columnValue = -1;
    int columnNewValue = -1;
    boolean dragComplete = false;
    boolean vykreslujTabulku = false;
//    int pomCounter = 0;
    int riadok = -1;
    Object[] ponuka = {"Áno", " Nie"};
    int moznost;
    boolean cmb11aktiv = false;
    boolean zobrazujemJednotlivo = false;
    boolean nezobrazilo = true;
    boolean aktualizuj = false;
    boolean aktiv = true;
    public boolean robimPolygony = false;
    public boolean editujemZasah = false;

    /**
     * Creates new form JFTabulkaJazdy
     *
     * @param conn
     * @param id_uzivatela
     * @param msg
     */
    public JFMainFrame(Connection conn, int id_uzivatela, Messenger msg) {
        OHsystem = new OdpadoveHospodarstvo(conn, msg);
        initComponents();
        mapa = new Mapa(jInternalFrame1, OHsystem);
        mapa2 = new Mapa(jInternalFrame2, OHsystem, OHsystem.getGraph());
        mapa3 = new Mapa(jInternalFrame3, OHsystem, this);
        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMaximumSize(DimMax);
        this.setSize(DimMax);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);
        Refresher.r.setRefresher(this);
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
        jCB_druhOdpadu4.setModel(jCB_druhOdpadu.getModel());
        jCB_percentoZaplnenia4.setModel(jCB_percentoZaplnenia.getModel());

        vykreslujTabulkuKontajnerov();
        naformatujTabulkuKontajnerov();
        loadPodTabulkouKontajnerov();
        nastavDatumy();
        LinkedList<PrijataSprava> nespracovane = new LinkedList<>();
        nespracovane.add(new PrijataSprava("0944000000", "SMS v nespravnom formate", "20:00:00 1.4.2021"));
        nespracovane.add(new PrijataSprava("0944111111", "Treba ocistit stojisko 5", "21:00:00 2.4.2021"));
        nespracovane.add(new PrijataSprava("0944222222", "Na stojisku 63 sa nachadza medved", "22:00:00 3.4.2021"));
        nespracovane.add(new PrijataSprava("0944333333", "Pri cintorine je prevratena nadoba na plast", "23:00:00 4.4.2001"));
        OHsystem.setNespracovaneSpravy(nespracovane);
        jBNespracovaneSpravy.setFont(new Font("Arial", Font.PLAIN, 40));
        refresNespracovaneSpravy();
        vykreslujTabulku = true;
        jProgressBar1.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new JScrollPane();
        jTextPane1 = new JTextPane();
        jScrollPane3 = new JScrollPane();
        jTextPane2 = new JTextPane();
        jFormattedTextField1 = new JFormattedTextField();
        jLabel4 = new JLabel();
        jScrollPane10 = new JScrollPane();
        jTabbedPane1 = new JTabbedPane();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTable_Kontajnery = new JTable(){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                boolean pom = (Boolean) jTable_Kontajnery.getModel().getValueAt(row, 8);

                if(pom){
                    c.setBackground(Color.LIGHT_GRAY);
                }else{
                    c.setBackground(Color.WHITE);
                }
                return c;
            }};
            jPVyhladavanie = new JPanel();
            jLabel1 = new JLabel();
            jLabel2 = new JLabel();
            jCB_percentoZaplnenia = new JComboBox<>();
            jCB_druhOdpadu = new JComboBox<>();
            jCheckBox_Vyprazdnenie = new JCheckBox();
            jCheckBoxAktualizacia = new JCheckBox();
            jDateChooser_Vyprazdnenie = new JDateChooser();
            jDateChooser_Aktualizácia = new JDateChooser();
            jButton1 = new JButton();
            jButton2 = new JButton();
            jButton11 = new JButton();
            jLabel12 = new JLabel();
            jButton12 = new JButton();
            jCB_idecka = new JComboBox<>();
            jBNespracovaneSpravy = new JButton();
            jTabbedPane3 = new JTabbedPane();
            jPanel7 = new JPanel();
            jLabel3 = new JLabel();
            jTextField1 = new JTextField();
            jLabel5 = new JLabel();
            jTextField2 = new JTextField();
            jLabel6 = new JLabel();
            jTextField4 = new JTextField();
            jLabel7 = new JLabel();
            jTextField5 = new JTextField();
            jLabel8 = new JLabel();
            jTextField6 = new JTextField();
            jLabel9 = new JLabel();
            jTextField7 = new JTextField();
            jTabbedPane4 = new JTabbedPane();
            jPanel11 = new JPanel();
            jButton13 = new JButton();
            jButton9 = new JButton();
            jScrollPane7 = new JScrollPane();
            jTextPane3 = new JTextPane();
            jPanel12 = new JPanel();
            jLabel13 = new JLabel();
            cmbEditaciaZvozu = new JComboBox<>();
            jScrollPane9 = new JScrollPane();
            jTable_Zasah = new JTable(){
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                    Component c = super.prepareRenderer(renderer, row, col);
                    if (col == 0) {
                        return this.getTableHeader().getDefaultRenderer()
                        .getTableCellRendererComponent(this, this.getValueAt(
                            row, col), false, false, row, col);
                }else if(row ==3){
                    c.setBackground(new Color(244, 67, 54));
                    return c;
                }else {
                    c.setBackground(jTable_Zasah.getBackground());
                    return c;
                    //return super.prepareRenderer(renderer, row, col);
                }
            }

        };
        jLabel16 = new JLabel();
        jTextField_kapacita = new JTextField();
        jTextField_dlzkaTrasy = new JTextField();
        jLabel17 = new JLabel();
        jButton15 = new JButton();
        jButton16 = new JButton();
        jLabel19 = new JLabel();
        jTextField_dlzkaTrasy1 = new JTextField();
        jTextField_dlzkaTrasy2 = new JTextField();
        jLabel20 = new JLabel();
        jLabel21 = new JLabel();
        jTextField_DruhOdpadu = new JTextField();
        jButton5 = new JButton();
        jButton28 = new JButton();
        jPanel9 = new JPanel();
        jScrollPane13 = new JScrollPane();
        jTable1 = new JTable(OHsystem.getJazdyAktual(new DefaultTableModel()))
        ;
        jButton18 = new JButton();
        jScrollPane4 = new JScrollPane();
        jTable_Vozidla = new JTable();
        jLabel28 = new JLabel();
        jButton19 = new JButton();
        jPanel14 = new JPanel();
        jLabel36 = new JLabel();
        jScrollPane15 = new JScrollPane();
        jTable2 = new JTable(){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (col == 0) {
                    return this.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(this, this.getValueAt(
                        row, col), false, false, row, col);
            }else if(row==3){
                if(col>1 && col<jTable2.getColumnCount()-1){
                    if(!jTable2.getValueAt(row, col).toString().equals(jTable2.getValueAt(row-1, col).toString())){
                        c.setBackground(Color.green);
                        return c;
                    }
                }
                c.setBackground(jTable2.getBackground());
                return c;
            }else {
                c.setBackground(jTable2.getBackground());
                return c;
            }
        }
    };
    jTabbedPane5 = new JTabbedPane();
    jPanel13 = new JPanel();
    jLabel29 = new JLabel();
    jLabel26 = new JLabel();
    jComboBox1 = new JComboBox<>();
    jComboBox2 = new JComboBox<>();
    jLabel30 = new JLabel();
    jButton21 = new JButton();
    jLabel31 = new JLabel();
    jTextField10 = new JTextField();
    jLabel32 = new JLabel();
    jTextField11 = new JTextField();
    jLabel33 = new JLabel();
    jTextField12 = new JTextField();
    jLabel34 = new JLabel();
    jTextField13 = new JTextField();
    jLabel35 = new JLabel();
    jTextField14 = new JTextField();
    jLabel38 = new JLabel();
    jTextField15 = new JTextField();
    jButton22 = new JButton();
    jCheckBox1 = new JCheckBox();
    jButton17 = new JButton();
    jPanel17 = new JPanel();
    jPanel16 = new JPanel();
    jLabel10 = new JLabel();
    jTextField9 = new JTextField();
    jLabel11 = new JLabel();
    jTextField8 = new JTextField();
    jScrollPane8 = new JScrollPane();
    jTextPane4 = new JTextPane();
    jButton8 = new JButton();
    jButton10 = new JButton();
    jScrollPane12 = new JScrollPane();
    jTable_VozidlaKapacitne = new JTable();
    jScrollPane14 = new JScrollPane();
    jTableVybraneVozidla = new JTable(){

        @Override
        public Component prepareRenderer(
            TableCellRenderer renderer, int row, int col) {
            if (col == 0) {
                return this.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(this, this.getValueAt(
                    row, col), false, false, row, col);
        }else {
            return super.prepareRenderer(renderer, row, col);
        }
    }
    };
    jLabel27 = new JLabel();
    jLabel37 = new JLabel();
    jPanel10 = new JPanel();
    jLabel22 = new JLabel();
    jCB_SPZZ = new JComboBox<>();
    jLabel23 = new JLabel();
    jCB_stav = new JComboBox<>();
    jCheckBox_jazda_od = new JCheckBox();
    jDateChooser_jazda_od = new JDateChooser();
    jDateChooser_jazda_do = new JDateChooser();
    jCheckBox_jazda_do = new JCheckBox();
    jScrollPane11 = new JScrollPane();
    jTable_Jazdy = new JTable();
    jLabel24 = new JLabel();
    jCB_druhOdpadu3 = new JComboBox<>();
    jPanel1 = new JPanel();
    jPanel4 = new JPanel();
    jInternalFrame1 = new JInternalFrame();
    jPanel15 = new JPanel();
    jLabel14 = new JLabel();
    jLabel15 = new JLabel();
    jCB_percentoZaplnenia4 = new JComboBox<>();
    jCB_druhOdpadu4 = new JComboBox<>();
    jCheckBox_Vyprazdnenie2 = new JCheckBox();
    jCheckBoxAktualizacia2 = new JCheckBox();
    jDateChooser_Vyprazdnenie2 = new JDateChooser();
    jDateChooser2_Aktualizácia2 = new JDateChooser();
    jButton14 = new JButton();
    jInternalFrame2 = new JInternalFrame();
    jPanel8 = new JPanel();
    jScrollPane16 = new JScrollPane();
    jTable3 = new JTable();
    jScrollPane17 = new JScrollPane();
    jTable4 = new JTable();
    jScrollPane18 = new JScrollPane();
    jTable5 = new JTable();
    jPanel18 = new JPanel();
    jInternalFrame3 = new JInternalFrame();
    jButton20 = new JButton();
    jCheckBox2 = new JCheckBox();
    jButton23 = new JButton();
    jLabel25 = new JLabel();
    jTextField16 = new JTextField();
    jTextField17 = new JTextField();
    jButton24 = new JButton();
    jLabel40 = new JLabel();
    jButton25 = new JButton();
    jButton26 = new JButton();
    jButton27 = new JButton();
    jLabel39 = new JLabel();
    jProgressBar1 = new JProgressBar();
    jPanel3 = new JPanel();
    jButton3 = new JButton();
    jTextField3 = new JTextField();
    jButton4 = new JButton();
    jButton6 = new JButton();
    jButton7 = new JButton();
    jTabbedPane2 = new JTabbedPane();
    jPanel5 = new JPanel();
    jScrollPane5 = new JScrollPane();
    jTable_MaticaVzdialenosti = new JTable(){
        @Override
        public Component prepareRenderer (TableCellRenderer renderer, int row, int column){
            Component c = super.prepareRenderer(renderer, row, column);
            if(!isRowSelected(row)){
                c.setBackground(getBackground());
                Object o =getModel().getValueAt(row, column);
                if(o!=null){
                    String v = ""+o;
                    if((v.equals("-1.0") ||v.equals(""+Integer.MAX_VALUE))){
                        c.setBackground(Color.red);}
                }

            }
            return c;
        }};
        jPanel6 = new JPanel();
        jScrollPane6 = new JScrollPane();
        jTableMaticaUzly = new JTable()
        {
            @Override
            public Component prepareRenderer (TableCellRenderer renderer, int row, int column){
                Component c = super.prepareRenderer(renderer, row, column);
                if(!isRowSelected(row)){
                    c.setBackground(getBackground());
                    Object o =getModel().getValueAt(row, column);
                    if(o!=null){
                        String v = ""+o;
                        if(v.equals("-1.0")){
                            c.setBackground(Color.red);}
                    }

                }
                return c;
            }}
            ;

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Optim");
            setExtendedState(Frame.MAXIMIZED_BOTH);
            setMinimumSize(null);
            setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
            setSize(Toolkit.getDefaultToolkit().getScreenSize());

            jScrollPane2.setViewportView(jTextPane1);

            jScrollPane3.setViewportView(jTextPane2);

            jFormattedTextField1.setText("jFormattedTextField1");

            jTabbedPane1.setAutoscrolls(true);
            jTabbedPane1.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    jTabbedPane1StateChanged(evt);
                }
            });

            jPanel2.setAutoscrolls(true);
            jPanel2.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    jPanel2FocusLost(evt);
                }
            });

            ((DefaultTableCellRenderer)jTable_Kontajnery.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            jTable_Kontajnery.setAutoCreateRowSorter(true);
            jTable_Kontajnery.getTableHeader().setReorderingAllowed(false);
            jTable_Kontajnery.setFont(new Font("Dialog", 1, 14)); // NOI18N
            jTable_Kontajnery.setModel(new DefaultTableModel());
            for (int i = 0; i < jTable_Kontajnery.getColumnCount()-3; i++) {
                jTable_Kontajnery.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
            }
            jTable_Kontajnery.setRowHeight(40);
            jTable_Kontajnery.setRowSelectionAllowed(false);
            jTable_Kontajnery.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable_Kontajnery.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable_KontajneryMouseClicked(evt);
                }
            });
            jScrollPane1.setViewportView(jTable_Kontajnery);

            jLabel1.setText("Druh odpadu:");

            jLabel2.setText("% zaplnenia:");

            jCB_percentoZaplnenia.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_percentoZaplneniaItemStateChanged(evt);
                }
            });

            jCB_druhOdpadu.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent evt) {
                    String pom = jCB_druhOdpadu.getEditor().getItem().toString();
                    if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90
                        || evt.getKeyLocation() > 96 && evt.getKeyCode() <= 105
                        || evt.getKeyCode() == 8) {
                        jCB_druhOdpadu.setModel(OHsystem.getDruhOdpadu(pom,(DefaultComboBoxModel)jCB_druhOdpadu.getModel()));
                        if (jCB_druhOdpadu.getItemCount() > 0) {
                            jCB_druhOdpadu.showPopup();
                            if (evt.getKeyCode() != 8) {
                                ((JTextComponent) jCB_druhOdpadu.getEditor().getEditorComponent())
                                .select(pom.length(), jCB_druhOdpadu.getEditor().getItem().toString().length());
                            } else {
                                jCB_druhOdpadu.getEditor().setItem(pom);
                            }
                        } else {
                            jCB_druhOdpadu.addItem(pom);
                        }
                    }
                }
            });
            jCB_druhOdpadu.setModel(OHsystem.getDruhOdpadu("", new DefaultComboBoxModel()));
            jCB_druhOdpadu.addItem("Všetky");
            jCB_druhOdpadu.setSelectedItem("Všetky");
            jCB_druhOdpadu.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_druhOdpaduItemStateChanged(evt);
                }
            });

            jCheckBox_Vyprazdnenie.setText("použiť Dátum vyprázdnenia pred:");
            jCheckBox_Vyprazdnenie.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jCheckBox_VyprazdnenieActionPerformed(evt);
                }
            });

            jCheckBoxAktualizacia.setText("použiť Dátum aktualizácie pred:");
            jCheckBoxAktualizacia.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jCheckBoxAktualizaciaActionPerformed(evt);
                }
            });

            jDateChooser_Vyprazdnenie.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    jDateChooser_VyprazdneniePropertyChange(evt);
                }
            });

            jDateChooser_Aktualizácia.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    jDateChooser_AktualizáciaPropertyChange(evt);
                }
            });

            jButton1.setText("Zobraz");
            jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jButton2.setText("Planuj zber");
            jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            jButton11.setText("Aktualizuj z SMS");
            jButton11.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton11ActionPerformed(evt);
                }
            });

            jLabel12.setText("ID Kontajneru:");

            jButton12.setText("Pridaj kontajner");
            jButton12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton12ActionPerformed(evt);
                }
            });

            //jCB_idecka.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                //            @Override
                //            public void keyReleased(KeyEvent evt) {
                    //                String pom = jCB_idecka.getEditor().getItem().toString();
                    //                Character.isDigit(evt.getKeyChar());
                    //                if (Character.isDigit(evt.getKeyChar()) || evt.getKeyCode() == 8) {
                        //                    jCB_idecka.setModel(OHsystem.getDruhIdecka(pom,(DefaultComboBoxModel)jCB_idecka.getModel()));
                        //                    if (jCB_idecka.getItemCount() > 0) {
                            //                        jCB_idecka.showPopup();
                            //                        if (evt.getKeyCode() != 8) {
                                //                            ((JTextComponent) jCB_idecka.getEditor().getEditorComponent())
                                //                                    .select(pom.length(), jCB_idecka.getEditor().getItem().toString().length());
                                //                        } else {
                                //                            jCB_idecka.getEditor().setItem(pom);
                                //                        }
                            //                    } else {
                            //                        jCB_idecka.addItem(pom);
                            //                    }
                        //                } else {
                        //                    //JOptionPane.showMessageDialog(rootPane, "Taký znak v id_kontajnera nieje možné použiť");
                        //                    String s = jCB_idecka.getSelectedItem().toString();
                        //                    if (jCB_idecka.getSelectedItem().toString().length() == 0) {
                            //                        jCB_idecka.setModel(OHsystem.getDruhIdecka(s,(DefaultComboBoxModel)jCB_idecka.getModel()));
                            //                        jCB_idecka.addItem("");
                            //                        jCB_idecka.setSelectedItem("");
                            //                    } else {
                            //                        s = s.substring(0, s.length());
                            //                        jCB_idecka.setModel(OHsystem.getDruhIdecka(s,(DefaultComboBoxModel)jCB_idecka.getModel()));
                            //                        jCB_idecka.setSelectedIndex(0);
                            //                    }
                        //                }
                    //            }
                //        });
        jCB_idecka.setModel(OHsystem.getDruhIdecka("",new DefaultComboBoxModel()));
        jCB_idecka.addItem("");
        jCB_idecka.setSelectedItem("");
        jCB_idecka.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                jCB_ideckaItemStateChanged(evt);
            }
        });

        jBNespracovaneSpravy.setText("jButton13");
        jBNespracovaneSpravy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jBNespracovaneSpravyActionPerformed(evt);
            }
        });

        GroupLayout jPVyhladavanieLayout = new GroupLayout(jPVyhladavanie);
        jPVyhladavanie.setLayout(jPVyhladavanieLayout);
        jPVyhladavanieLayout.setHorizontalGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPVyhladavanieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                        .addComponent(jCB_percentoZaplnenia, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxAktualizacia))
                    .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                        .addComponent(jCB_druhOdpadu, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_Vyprazdnenie)))
                .addGap(0, 0, 0)
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser_Aktualizácia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser_Vyprazdnenie, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(jCB_idecka, 0, 1, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 81, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12))
                    .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBNespracovaneSpravy)
                .addGap(104, 104, 104))
        );

        jPVyhladavanieLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jButton1, jButton11, jButton12, jButton2});

        jPVyhladavanieLayout.setVerticalGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPVyhladavanieLayout.createSequentialGroup()
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser_Vyprazdnenie, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jCB_druhOdpadu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox_Vyprazdnenie))
                    .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jButton1)
                        .addComponent(jButton11, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCB_percentoZaplnenia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxAktualizacia))
                    .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                        .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser_Aktualizácia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPVyhladavanieLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCB_idecka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton12, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(jPVyhladavanieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBNespracovaneSpravy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPVyhladavanieLayout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton1, jButton11, jButton12, jButton2});

        jPVyhladavanieLayout.linkSize(SwingConstants.VERTICAL, new Component[] {jCheckBox_Vyprazdnenie, jLabel12});

        jTabbedPane3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jTabbedPane3StateChanged(evt);
            }
        });

        jLabel3.setText("Objem spolu [L]:");

        jLabel5.setText("Objem vybraných [L]: ");

        jLabel6.setText("Počet stojísk:");

        jLabel7.setText("Počet stojísk vybraných:");

        jLabel8.setText("Kapacita vybraných áut:");

        jLabel9.setText("Počet vybraných áut:");

        jButton13.setText("Zobraz odporúčané Trasy");
        jButton13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton9.setText("Zobraz rozmiestnie kontajnerov");
        jButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        StyledDocument doc = jTextPane3.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        String s = OHsystem.getMsg().zistiKredit();
        if(s.equals("problem")){
            jTextPane3.setText("Nepodarilo sa pripojiť k SMS bráne");
        }else{
            //String status = s.substring(0,s.indexOf("@"));
            if (s.substring(0, s.indexOf("@")).equalsIgnoreCase("OK")) {
                jTextPane3.setText("Mesačný poplatok za VLN číslo: 6€\nZostávajúci kredit: "
                    + String.format("%.2f", Double.parseDouble(s.substring(s.indexOf("@") + 1, s.lastIndexOf("@") - 1))) + "€\nPribližný počet zostávajúcich SMS: " + s.substring(s.lastIndexOf("@") + 1));
                //            JOptionPane.showMessageDialog(null, "Mesančný poplatok za VLN číslo: 6€\nZostávajúci kredit: "
                    //                    + String.format("%.2f", Double.parseDouble(s.substring(s.indexOf("@") + 1, s.lastIndexOf("@") - 1))) + "€\nPribližný počet zostávajúcich SMS: " + s.substring(s.lastIndexOf("@") + 1));
            } else {
                jTextPane3.setText(s.substring(0, s.indexOf("@")).replace("null", ""));
                //            JOptionPane.showMessageDialog(null, s.substring(0, s.indexOf("@")).replace("null", ""));
            }}
            jTextPane3.setEditable(false);
            jTextPane3.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jScrollPane7.setViewportView(jTextPane3);

            GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
            jPanel11.setLayout(jPanel11Layout);
            jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jButton9)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton13)
                            .addGap(0, 1463, Short.MAX_VALUE))
                        .addComponent(jScrollPane7, GroupLayout.Alignment.TRAILING))
                    .addContainerGap())
            );
            jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jButton13)
                        .addComponent(jButton9))
                    .addContainerGap())
            );

            jTabbedPane4.addTab("Výpis", jPanel11);

            jLabel13.setText("Navrhované zvozy:");

            cmbEditaciaZvozu.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
            cmbEditaciaZvozu.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    cmbEditaciaZvozuItemStateChanged(evt);
                }
            });

            ((DefaultTableCellRenderer)jTable_Zasah.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);

            jTable_Zasah.getTableHeader().setReorderingAllowed(false);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            jTable_Zasah.setDefaultRenderer(Object.class, new IconRender2());
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            jTable_Zasah.setRowHeight(40);
            //jTable_Zasah.setDefaultRenderer(String.class, centerRenderer);
            jTable_Zasah.setFont(new Font("Dialog", 1, 14)); // NOI18N
            jTable_Zasah.setRowSelectionAllowed(false);
            jTable_Zasah.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable_ZasahMouseClicked(evt);
                }
            });
            jTable_Zasah.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent evt) {
                    jTable_ZasahComponentResized(evt);
                }
            });
            jScrollPane9.setViewportView(jTable_Zasah);

            jLabel16.setText("Potrebné zvoziť [L]:");

            jTextField_kapacita.setEditable(false);

            jTextField_dlzkaTrasy.setEditable(false);

            jLabel17.setText("Dĺžka trasy [Km]:");

            jButton15.setText("Uprav trasu");
            jButton15.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton15ActionPerformed(evt);
                }
            });

            jButton16.setText("Vytvor jazdu");
            jButton16.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton16ActionPerformed(evt);
                }
            });

            jLabel19.setText("Úspora [Km]:");
            jLabel19.setVisible(false);

            jTextField_dlzkaTrasy1.setEditable(false);
            jTextField_dlzkaTrasy1.setVisible(false);
            jTextField_dlzkaTrasy1.setText("0");

            jTextField_dlzkaTrasy2.setEditable(false);
            jTextField_dlzkaTrasy2.setText("0");
            jTextField_dlzkaTrasy2.setVisible(false);

            jLabel20.setText("Nová dĺžka [Km]:");
            jLabel20.setVisible(false);

            jLabel21.setText("Druh odpadu:");

            jTextField_DruhOdpadu.setEditable(false);

            jButton5.setText("Zobraz kompletnú trasu");
            jButton5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton5ActionPerformed(evt);
                }
            });

            jButton28.setText("Vráť do pôvodného stavu");
            jButton28.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton28ActionPerformed(evt);
                }
            });
            jButton28.setVisible(false);

            GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
            jPanel12.setLayout(jPanel12Layout);
            jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane9)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbEditaciaZvozu, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel17, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField_dlzkaTrasy, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel16, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField_kapacita, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel21, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField_DruhOdpadu, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addGap(454, 454, 454))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jButton5)
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButton15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(3, 3, 3)
                                    .addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_dlzkaTrasy1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel20, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_dlzkaTrasy2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton28)))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );

            jPanel12Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jTextField_dlzkaTrasy1, jTextField_dlzkaTrasy2});

            jPanel12Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jButton15, jButton16, jButton5});

            jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(cmbEditaciaZvozu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(jTextField_kapacita, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(jTextField_dlzkaTrasy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(jTextField_DruhOdpadu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane9, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_dlzkaTrasy2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton28))
                        .addComponent(jButton15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_dlzkaTrasy1)
                        .addComponent(jLabel19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton16)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton5)
                    .addContainerGap(119, Short.MAX_VALUE))
            );

            jPanel12Layout.linkSize(SwingConstants.VERTICAL, new Component[] {cmbEditaciaZvozu, jLabel13});

            jPanel12Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton28, jTextField_dlzkaTrasy1, jTextField_dlzkaTrasy2});

            jPanel12Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton15, jButton16, jButton5});

            jTabbedPane4.addTab("Tabuľka - editácia zvozu ", jPanel12);
            jTabbedPane4.setEnabledAt(1, false);

            GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
            jPanel7.setLayout(jPanel7Layout);
            jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane4, GroupLayout.PREFERRED_SIZE, 1867, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField2, GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField4, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                .addComponent(jTextField6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                .addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField5)
                                .addComponent(jTextField7))))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel7Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel3, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

            jPanel7Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jTextField1, jTextField2, jTextField4, jTextField5, jTextField6, jTextField7});

            jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTabbedPane4, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE))
            );

            jPanel7Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jLabel3, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

            jPanel7Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jTextField1, jTextField2, jTextField4, jTextField5, jTextField6, jTextField7});

            jTabbedPane3.addTab("Vytvorenie Zvozu", jPanel7);

            jTable1.setFont(new Font("Dialog", 1, 14)); // NOI18N
            //jTable1.setModel(OHsystem.getJazdyAktual(new DefaultTableModel()));
            //jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(OHsystem.getCMBStavJazdy()));
            jTable1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable1MouseClicked(evt);
                }
            });
            jTable1.getTableHeader().setReorderingAllowed(false);
            jScrollPane13.setViewportView(jTable1);

            jButton18.setText("Vymaž jazdu");
            jButton18.setEnabled(false);
            jButton18.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton18ActionPerformed(evt);
                }
            });

            ((DefaultTableCellRenderer)jTable_Vozidla.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
            jTable_Vozidla.setFont(new Font("Dialog", 1, 14)); // NOI18N
            jTable_Vozidla.getTableHeader().setReorderingAllowed(false);
            jTable_Vozidla.setModel(OHsystem.getVozidla(new DefaultTableModel())

            );
            naformatujTabulkuVozidiel();
            JCheckBox check=new JCheckBox();
            check.setVisible(true);
            jTable_Vozidla.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(check));
            jTable_Vozidla.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            jTable_Vozidla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable_Vozidla.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable_VozidlaMouseClicked(evt);
                }
            });
            jScrollPane4.setViewportView(jTable_Vozidla);
            for (int i = 0; i < jTable_Vozidla.getRowCount(); i++) {
                jCB_SPZZ.addItem("" + jTable_Vozidla.getValueAt(i, 0));
            }
            jCB_SPZZ.addItem("Všetky");
            jCB_SPZZ.setSelectedItem("Všetky");

            jLabel28.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jLabel28.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel28.setText("Vozidlá odpadového hospodárstva");
            jLabel28.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

            jButton19.setText("Pridaj servisovú jazdu");
            jButton19.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton19ActionPerformed(evt);
                }
            });

            jLabel36.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jLabel36.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel36.setText("Stojiska jazdy");
            jLabel36.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

            ((DefaultTableCellRenderer)jTable2.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);

            jTable2.getTableHeader().setReorderingAllowed(false);

            jTable2.setDefaultRenderer(String.class, centerRenderer);
            jTable2.setFont(new Font("Dialog", 1, 14)); // NOI18N

            jTable2.setRowSelectionAllowed(false);
            jTable2.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent evt) {
                    jTable2ComponentResized(evt);
                }
            });
            jScrollPane15.setViewportView(jTable2);

            GroupLayout jPanel14Layout = new GroupLayout(jPanel14);
            jPanel14.setLayout(jPanel14Layout);
            jPanel14Layout.setHorizontalGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane15, GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel36, GroupLayout.PREFERRED_SIZE, 1856, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(16, Short.MAX_VALUE))
            );
            jPanel14Layout.setVerticalGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane15, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel13.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));

            jLabel29.setText("Vozidlo:");

            jLabel26.setText("Nový stav:");

            jComboBox1.setModel(new DefaultComboBoxModel());
            jComboBox1.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jComboBox1ItemStateChanged(evt);
                }
            });

            jLabel30.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jLabel30.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel30.setText("Úprava jazdy");
            jLabel30.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

            jButton21.setText("Ulož zmenu");
            jButton21.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton21ActionPerformed(evt);
                }
            });

            jLabel31.setText("Očakávaný objem:");

            jTextField10.setEditable(false);

            jLabel32.setText("Stav tachometra [km]:");

            jLabel33.setText("Očakávaná vzdialenosť:");

            jLabel34.setText("Najazdené km:");

            jTextField13.setEditable(false);

            jLabel35.setText("Zozbierany objem:");

            jTextField14.setEditable(false);

            jLabel38.setText("Predmet jazdy:");

            jButton22.setText("Zavri zmenu");
            jButton22.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton22ActionPerformed(evt);
                }
            });

            jCheckBox1.setText("poslať Jazdu ako SMS");

            jButton17.setText("Zobraz trasu");
            jButton17.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton17ActionPerformed(evt);
                }
            });
            jButton17.setVisible(false);

            GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
            jPanel13.setLayout(jPanel13Layout);
            jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jButton17)
                            .addGap(237, 237, 237)
                            .addComponent(jCheckBox1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton21)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton22)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel30, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel29, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel26, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel32, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jComboBox1, GroupLayout.Alignment.LEADING, 0, 246, Short.MAX_VALUE)
                                                .addComponent(jComboBox2, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jTextField11, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel33, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                                .addComponent(jLabel34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                    .addComponent(jTextField10, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel31, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel13Layout.createSequentialGroup()
                                                    .addComponent(jTextField13, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel35, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextField12)
                                                .addComponent(jTextField14, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 173, Short.MAX_VALUE))
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                            .addComponent(jLabel38, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextField15)))))
                            .addContainerGap())))
            );

            jPanel13Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel32, jLabel35});

            jPanel13Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jTextField10, jTextField13});

            jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel30)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)
                        .addComponent(jLabel31))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel34)
                                .addComponent(jTextField13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel35))
                            .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel32)
                                .addComponent(jLabel38)
                                .addComponent(jTextField15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jButton17, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton21)
                            .addComponent(jButton22)
                            .addComponent(jCheckBox1)))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel13Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jComboBox1, jComboBox2, jLabel26, jLabel29, jLabel30, jLabel31, jLabel32, jLabel33, jLabel34, jLabel35, jLabel38});

            jPanel13Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jTextField10, jTextField11, jTextField12, jTextField13, jTextField14});

            jPanel13Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton17, jButton22});

            jCheckBox1.setSelected(true);
            jCheckBox1.setVisible(false);

            jTabbedPane5.addTab("Úprava Jazdy", jPanel13);

            jLabel10.setText("Odosielatel:");

            jTextField9.setText("Rendo");

            jLabel11.setText("Prijemcovia");

            jTextField8.setText("+421944339622");

            jTextPane4.setText("Moja prvá správa uz funguje ? 123 1,2,54,6,6");
            jScrollPane8.setViewportView(jTextPane4);

            jButton8.setText("pošli SMS");
            jButton8.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton8ActionPerformed(evt);
                }
            });

            jButton10.setText("zavrieť");
            jButton10.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton10ActionPerformed(evt);
                }
            });

            GroupLayout jPanel16Layout = new GroupLayout(jPanel16);
            jPanel16.setLayout(jPanel16Layout);
            jPanel16Layout.setHorizontalGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField9, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGap(64, 64, 64)
                            .addComponent(jButton8)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton10)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane8, GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addContainerGap())
            );

            jPanel16Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel11, jTextField8, jTextField9});

            jPanel16Layout.setVerticalGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane8, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                            .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jTextField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton10)
                                .addComponent(jButton8))))
                    .addGap(178, 178, 178))
            );

            jPanel16Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jLabel10, jLabel11, jTextField8, jTextField9});

            GroupLayout jPanel17Layout = new GroupLayout(jPanel17);
            jPanel17.setLayout(jPanel17Layout);
            jPanel17Layout.setHorizontalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel17Layout.setVerticalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel16, GroupLayout.PREFERRED_SIZE, 175, Short.MAX_VALUE)
            );

            jTabbedPane5.addTab("Messenger", jPanel17);

            GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
            jPanel9.setLayout(jPanel9Layout);
            jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 1186, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jTabbedPane5, GroupLayout.PREFERRED_SIZE, 1180, GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jButton18, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton19, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                        .addComponent(jLabel28, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(16, 16, 16))
                .addComponent(jPanel14, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            jPanel9Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jButton18, jButton19});

            jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton19)
                                .addComponent(jButton18))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTabbedPane5, GroupLayout.PREFERRED_SIZE, 201, Short.MAX_VALUE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel14, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel9Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton18, jButton19});

            jTabbedPane5.setEnabledAt(1, false);

            jTabbedPane3.addTab("Aktuálne jazdy", jPanel9);

            ((DefaultTableCellRenderer)jTable_VozidlaKapacitne.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
            jTable_VozidlaKapacitne.setFont(new Font("Dialog", 1, 14)); // NOI18N
            jTable_VozidlaKapacitne.setModel(OHsystem.getVozidlaKapacitne(new DefaultTableModel()));
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < jTable_VozidlaKapacitne.getColumnCount()-1; i++) {
                jTable_VozidlaKapacitne.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            jTable_VozidlaKapacitne.getColumnModel().getColumn(jTable_VozidlaKapacitne.getColumnCount() - 1).setMinWidth(32);
            jTable_VozidlaKapacitne.getColumnModel().getColumn(jTable_VozidlaKapacitne.getColumnCount() - 1).setMaxWidth(32);
            jTable_VozidlaKapacitne.setRowHeight(32);
            jTable_VozidlaKapacitne.setRowSelectionAllowed(false);
            jTable_VozidlaKapacitne.getTableHeader().setReorderingAllowed(false);
            jTable_VozidlaKapacitne.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable_VozidlaKapacitneMouseClicked(evt);
                }
            });
            jScrollPane12.setViewportView(jTable_VozidlaKapacitne);

            jTableVybraneVozidla.setDefaultRenderer(Object.class, new IconRender());
            jTableVybraneVozidla.setDefaultEditor(Object.class, new ComboEditor());
            ((DefaultTableCellRenderer)jTableVybraneVozidla.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
            jTableVybraneVozidla.getTableHeader().setReorderingAllowed(false);

            //centerRenderer = new DefaultTableCellRenderer();
            //centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            //jTableVybraneVozidla.setDefaultRenderer(String.class, centerRenderer);
            jTableVybraneVozidla.setFont(new Font("Dialog", 1, 14)); // NOI18N

            jTableVybraneVozidla.setRowSelectionAllowed(false);
            jTableVybraneVozidla.setModel(new DefaultTableModel());
            jTableVybraneVozidla.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTableVybraneVozidlaMouseClicked(evt);
                }
            });
            jTableVybraneVozidla.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent evt) {
                    jTableVybraneVozidlaComponentResized(evt);
                }
            });
            jScrollPane14.setViewportView(jTableVybraneVozidla);

            jLabel27.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jLabel27.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel27.setText("Vybrané kapacity");
            jLabel27.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

            jLabel37.setFont(new Font("Dialog", 1, 18)); // NOI18N
            jLabel37.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel37.setText("Kapacity vozidiel");
            jLabel37.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

            GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane3, GroupLayout.PREFERRED_SIZE, 1874, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 1188, GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jPVyhladavanie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane14)
                                    .addComponent(jLabel27, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel37, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jPVyhladavanie, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37))
                    .addGap(7, 7, 7)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel27)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane14, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTabbedPane3, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(870, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Základné okno", jPanel2);

            jLabel22.setText("Stav jazdy:");

            jCB_SPZZ.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_SPZZItemStateChanged(evt);
                }
            });

            jLabel23.setText("ŠPZ:");

            jCB_stav.setModel(OHsystem.getStavJazdy(new DefaultComboBoxModel()));
            jCB_stav.addItem("Všetky");
            jCB_stav.setSelectedItem("Všetky");
            jCB_stav.setEditable(false);
            jCB_stav.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_stavItemStateChanged(evt);
                }
            });

            jCheckBox_jazda_od.setText("použiť Dátum vykonania jazdy od:");
            jCheckBox_jazda_od.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jCheckBox_jazda_odActionPerformed(evt);
                }
            });

            jDateChooser_jazda_od.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    jDateChooser_jazda_odPropertyChange(evt);
                }
            });

            jDateChooser_jazda_do.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    jDateChooser_jazda_doPropertyChange(evt);
                }
            });

            jCheckBox_jazda_do.setText("použiť Dátum vykonania jazdy do:");
            jCheckBox_jazda_do.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jCheckBox_jazda_doActionPerformed(evt);
                }
            });

            jTable_Jazdy.setAutoCreateRowSorter(true);
            jTable_Jazdy.setFont(new Font("Dialog", 1, 14)); // NOI18N
            jTable_Jazdy.setRowHeight(40);
            jTable_Jazdy.setModel(OHsystem.getKnihaJazd("Všetky", null, null, "Všetky", "Všetky", new DefaultTableModel()));
            jTable_Jazdy.getTableHeader().setReorderingAllowed(false);
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < jTable_Jazdy.getColumnCount()-1; i++) {
                jTable_Jazdy.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            ((DefaultTableCellRenderer)jTable_Jazdy.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            jTable_Jazdy.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    jTable_JazdyMouseClicked(evt);
                }
            });
            jScrollPane11.setViewportView(jTable_Jazdy);

            jLabel24.setText("Druh odpadu:");

            jCB_druhOdpadu3.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent evt) {
                    String pom = jCB_druhOdpadu3.getEditor().getItem().toString();
                    if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90
                        || evt.getKeyLocation() > 96 && evt.getKeyCode() <= 105
                        || evt.getKeyCode() == 8) {
                        jCB_druhOdpadu3.setModel(OHsystem.getDruhOdpadu(pom,(DefaultComboBoxModel)jCB_druhOdpadu3.getModel()));
                        if (jCB_druhOdpadu3.getItemCount() > 0) {
                            jCB_druhOdpadu3.showPopup();
                            if (evt.getKeyCode() != 8) {
                                ((JTextComponent) jCB_druhOdpadu3.getEditor().getEditorComponent())
                                .select(pom.length(), jCB_druhOdpadu3.getEditor().getItem().toString().length());
                            } else {
                                jCB_druhOdpadu3.getEditor().setItem(pom);
                            }
                        } else {
                            jCB_druhOdpadu3.addItem(pom);
                        }
                    }
                }
            });
            jCB_druhOdpadu3.setModel(OHsystem.getDruhOdpadu("",new DefaultComboBoxModel()));
            jCB_druhOdpadu3.addItem("Všetky");
            jCB_druhOdpadu3.setSelectedItem("Všetky");
            jCB_druhOdpadu3.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_druhOdpadu3ItemStateChanged(evt);
                }
            });

            GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
            jPanel10.setLayout(jPanel10Layout);
            jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel23, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCB_SPZZ, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBox_jazda_do)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jDateChooser_jazda_do, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel22, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCB_stav, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBox_jazda_od)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jDateChooser_jazda_od, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel24, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCB_druhOdpadu3, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(1528, Short.MAX_VALUE))
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addComponent(jScrollPane11, GroupLayout.PREFERRED_SIZE, 1864, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
            );

            jPanel10Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jDateChooser_jazda_do, jDateChooser_jazda_od});

            jPanel10Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel22, jLabel23});

            jPanel10Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jCB_SPZZ, jCB_stav});

            jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jCheckBox_jazda_od)
                        .addComponent(jDateChooser_jazda_od, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addComponent(jCB_stav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jDateChooser_jazda_do, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox_jazda_do)
                                .addComponent(jLabel23)
                                .addComponent(jCB_SPZZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCB_druhOdpadu3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane11, GroupLayout.PREFERRED_SIZE, 835, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(877, Short.MAX_VALUE))
            );

            jPanel10Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jDateChooser_jazda_do, jDateChooser_jazda_od});

            jPanel10Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jLabel22, jLabel23});

            jPanel10Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jCB_SPZZ, jCB_stav});

            jTabbedPane1.addTab("Kniha jázd", jPanel10);

            jPanel1.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent evt) {
                    jPanel1FocusGained(evt);
                }
            });

            jInternalFrame1.setMaximumSize(new Dimension(400, 600));
            jInternalFrame1.setMinimumSize(new Dimension(400, 600));
            jInternalFrame1.setPreferredSize(new Dimension(400, 600));
            jInternalFrame1.setVisible(true);

            GroupLayout jInternalFrame1Layout = new GroupLayout(jInternalFrame1.getContentPane());
            jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
            jInternalFrame1Layout.setHorizontalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 1860, Short.MAX_VALUE)
            );
            jInternalFrame1Layout.setVerticalGroup(jInternalFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 852, Short.MAX_VALUE)
            );

            jLabel14.setText("Druh odpadu:");

            jLabel15.setText("% zaplnenia:");

            for (int i = 0; i <= 10; i++) {
                jCB_percentoZaplnenia.addItem("" + i * 10);
            }
            jCB_percentoZaplnenia4.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    jCB_percentoZaplnenia4ItemStateChanged(evt);
                }
            });

            //jCB_druhOdpadu4.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                //            @Override
                //            public void keyReleased(KeyEvent evt) {
                    //                String pom = jCB_druhOdpadu4.getEditor().getItem().toString();
                    //                if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90
                        //                        || evt.getKeyLocation() > 96 && evt.getKeyCode() <= 105
                        //                        || evt.getKeyCode() == 8) {
                        //                    jCB_druhOdpadu4.setModel(OHsystem.getDruhOdpadu(pom,(DefaultComboBoxModel)jCB_druhOdpadu4.getModel()));
                        //                    if (jCB_druhOdpadu4.getItemCount() > 0) {
                            //                        jCB_druhOdpadu4.showPopup();
                            //                        if (evt.getKeyCode() != 8) {
                                //                            ((JTextComponent) jCB_druhOdpadu4.getEditor().getEditorComponent())
                                //                                    .select(pom.length(), jCB_druhOdpadu4.getEditor().getItem().toString().length());
                                //                        } else {
                                //                            jCB_druhOdpadu4.getEditor().setItem(pom);
                                //                        }
                            //                    } else {
                            //                        jCB_druhOdpadu4.addItem(pom);
                            //                    }
                        //                }
                    //            }
                //        });
        jCB_druhOdpadu4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                jCB_druhOdpadu4ItemStateChanged(evt);
            }
        });

        jCheckBox_Vyprazdnenie2.setText("použiť Dátum vyprázdnenia pred:");
        jCheckBox_Vyprazdnenie2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBox_Vyprazdnenie2ActionPerformed(evt);
            }
        });

        jCheckBoxAktualizacia2.setText("použiť Dátum aktualizácie pred:");
        jCheckBoxAktualizacia2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBoxAktualizacia2ActionPerformed(evt);
            }
        });

        jDateChooser_Vyprazdnenie2.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                jDateChooser_Vyprazdnenie2PropertyChange(evt);
            }
        });

        jDateChooser2_Aktualizácia2.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                jDateChooser2_Aktualizácia2PropertyChange(evt);
            }
        });

        jButton14.setText("Zobraz");
        jButton14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        GroupLayout jPanel15Layout = new GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jCB_percentoZaplnenia4, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxAktualizacia2))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jCB_druhOdpadu4, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_Vyprazdnenie2)))
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser2_Aktualizácia2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser_Vyprazdnenie2, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton14, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser_Vyprazdnenie2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel15Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jCB_druhOdpadu4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox_Vyprazdnenie2)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCB_percentoZaplnenia4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxAktualizacia2))
                    .addComponent(jDateChooser2_Aktualizácia2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton14)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1095, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jInternalFrame1, GroupLayout.PREFERRED_SIZE, 1862, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInternalFrame1, GroupLayout.PREFERRED_SIZE, 874, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(873, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 277, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mapa umiestnení kontajnerov", jPanel1);

        jInternalFrame2.setVisible(true);

        GroupLayout jInternalFrame2Layout = new GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(jInternalFrame2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 2149, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(jInternalFrame2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mapa Trás", jInternalFrame2);

        jTable3.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane16.setViewportView(jTable3);

        jTable4.setFont(new Font("Dialog", 1, 14)); // NOI18N
        jTable4.setModel(OHsystem.getOblasti(new DefaultTableModel()));
        jTable4.setEnabled(false);
        jTable4.setRowSelectionAllowed(false);
        jTable4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(jTable4);
        jTable4.setRowHeight(40);
        jTable4.getTableHeader().setReorderingAllowed(false);

        jTable4.getColumnModel().getColumn(0).setMinWidth(30);
        jTable4.getColumnModel().getColumn(0).setMaxWidth(30);

        jTable5.setFont(new Font("Dialog", 1, 14)); // NOI18N
        jTable5.setModel(OHsystem.getOblasti(new DefaultTableModel()));
        jTable5.setEnabled(false);
        jTable5.setRowSelectionAllowed(false);
        jTable5.setRowHeight(40);
        jTable5.getColumnModel().getColumn(0).setMinWidth(30);
        jTable5.getColumnModel().getColumn(0).setMaxWidth(30);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jTable5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(jTable5);

        GroupLayout jPanel18Layout = new GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame3.setMaximumSize(new Dimension(400, 600));
        jInternalFrame3.setMinimumSize(new Dimension(400, 600));
        jInternalFrame3.setPreferredSize(new Dimension(400, 600));
        jInternalFrame3.setVisible(true);

        GroupLayout jInternalFrame3Layout = new GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(jInternalFrame3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1414, Short.MAX_VALUE)
        );
        jInternalFrame3Layout.setVerticalGroup(jInternalFrame3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 841, Short.MAX_VALUE)
        );

        jButton20.setText("Priprav Optimalizáciu");
        jButton20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("vytváram polygónové oblasti");
        jCheckBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jButton23.setText("Optimalizuj");
        jButton23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jLabel25.setText("Počet vytvorených stojísk:");

        jTextField16.setText("10");

        jTextField17.setFont(new Font("Dialog", 1, 18)); // NOI18N

        jButton24.setText("Nacitaj zo súboru stojiská");
        jButton24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel40.setFont(new Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel40.setText("Oblasti:");
        jLabel40.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

        jButton25.setText("Ulož do súboru oblasti");
        jButton25.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("Nacitaj zo súboru oblasti");
        jButton26.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText("Ulož do súboru stojiská");
        jButton27.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel39.setFont(new Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel39.setText("Možné stojiská:");
        jLabel39.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

        jProgressBar1.setValue(1);
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setVerifyInputWhenFocusTarget(false);

        GroupLayout jPanel8Layout = new GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jButton20, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jButton24, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton27, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jButton26, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton25, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField17, GroupLayout.PREFERRED_SIZE, 1257, GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jButton23, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel25, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField16, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
                            .addGap(231, 231, 231)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 651, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane17, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                    .addComponent(jLabel40, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane18, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jInternalFrame3, GroupLayout.PREFERRED_SIZE, 1416, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(276, Short.MAX_VALUE))))
        );

        jPanel8Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jButton20, jButton23, jLabel25});

        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGap(574, 574, 574)
                        .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton20)
                            .addComponent(jTextField17, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton24)
                            .addComponent(jButton27))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox2)
                                .addComponent(jButton23)
                                .addComponent(jButton26)
                                .addComponent(jButton25))
                            .addComponent(jLabel25, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField16)
                            .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel39))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane17, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane18, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jInternalFrame3, GroupLayout.PREFERRED_SIZE, 863, GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 396, Short.MAX_VALUE)
                .addComponent(jScrollPane16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(SwingConstants.VERTICAL, new Component[] {jButton20, jButton23, jLabel25, jTextField16});

        jTabbedPane1.addTab("Optimizér", jPanel8);

        jButton3.setText("vyber GeoJason");
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField3.setText("C:\\Users\\rendo\\OneDrive\\Plocha\\DP last\\QGISLAST\\grafFinalKonecneeee.geojson");

        jButton4.setText("vytvor Maticu");
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("save");
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("load");
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable_MaticaVzdialenosti.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable_MaticaVzdialenosti);

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, GroupLayout.DEFAULT_SIZE, 1877, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 836, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("matica vzdialenost [m]", jPanel5);

        jTableMaticaUzly.setFont(new Font("Dialog", 1, 14)); // NOI18N
        jTableMaticaUzly.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTableMaticaUzly);

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 1865, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, GroupLayout.DEFAULT_SIZE, 1703, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("matica vzdialenosti cesty", jPanel6);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton6)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)))
                .addContainerGap(1476, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, GroupLayout.PREFERRED_SIZE, 1879, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 272, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administrácia", jPanel3);

        jScrollPane10.setViewportView(jTabbedPane1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1894, 1894, 1894)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 993, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadPodTabulkouKontajnerov() {
        double sum = 0;
        int pocetStojisk = 0;
        int id_predchadzajucehoStojiska = -1;
        for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
            if (i == 0) {
                id_predchadzajucehoStojiska = (int) jTable_Kontajnery.getValueAt(i, 0);
                pocetStojisk++;
                sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
            } else {
                if (id_predchadzajucehoStojiska != (int) jTable_Kontajnery.getValueAt(i, 0)) {
                    pocetStojisk++;
                    id_predchadzajucehoStojiska = (int) jTable_Kontajnery.getValueAt(i, 0);
                }
                sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
            }
        }
        jTextField1.setText("" + sum);
        jTextField2.setText("" + sum);
        jTextField4.setText("" + pocetStojisk);
        jTextField5.setText("" + pocetStojisk);
    }


    private void jButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LinkedList<Integer> stojiska = new LinkedList<>();
        ArrayList<String> odpady = new ArrayList<>();
        double Sum = 0;

        for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
            if (odpady.isEmpty()) { // riesim prvy riadok
                if ((boolean) jTable_Kontajnery.getValueAt(i, 8)) {
                    odpady.add("" + jTable_Kontajnery.getValueAt(i, 2));
                    stojiska.add(Integer.parseInt("" + jTable_Kontajnery.getValueAt(i, 0)));
                    Sum += (0.01 * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 3))
                            * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 4)));
                }
            } else if (!odpady.get(0).equals("" + jTable_Kontajnery.getValueAt(i, 2))) { //problem s viacerymi druhmi kontajnerov
                if ((boolean) jTable_Kontajnery.getValueAt(i, 8)) {
                    JOptionPane.showMessageDialog(this, "Nie je možné spustiť optimalizáciu, z dôvodu vybratia kontajnerov s rôznym typom odpadu  ");
                    return;
                }
            } else { // neprvy riadok
                if ((boolean) jTable_Kontajnery.getValueAt(i, 8)) {
                    if (stojiska.getLast() == Integer.parseInt("" + jTable_Kontajnery.getValueAt(i, 0))) { //naslo id
                        Sum += (0.01 * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 3))
                                * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 4)));
                    } else { //nenaslo id
                        stojiska.add(Integer.parseInt("" + jTable_Kontajnery.getValueAt(i, 0)));
                        Sum += (0.01 * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 3))
                                * Double.parseDouble("" + jTable_Kontajnery.getValueAt(i, 4)));
                    }
                }
            }
        }
        if (stojiska.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nie je možné vykonať optimalizáciu, lebo nie sú vybrané žiadne stojiská");
            return;
        }
        if (OHsystem.getVozidla_navrhovane().isEmpty()) { //vozidla.isEmpty()
            JOptionPane.showMessageDialog(null, "Nie je možné vykonať optimalizáciu, lebo nie sú vybrané žiadne vozidlá");
            return;
        }

        String s = "Optimalizačný problém: \n zákaznici: ";
        for (Integer in : stojiska) {
            s += in + ",";
        }
        s = s.substring(0, s.lastIndexOf(","));
        s += "\nOdhadovaná kapacita: " + Sum + "\n vozidlá:";

//        for (String st : vozidla_navrhovane) {
//            s += st + ",";
//        }
//        s = s.substring(0, s.lastIndexOf(","));
//        textArea1.setText(s);
        VysledokOptimalizacie vysledok = OHsystem.pripravDataNaOptimalizacia(stojiska,/* vozidla_navrhovane,*/ odpady.get(0));
        jTableVybraneVozidla.setModel(OHsystem.getVybraneVozidla(-1, (DefaultTableModel) jTableVybraneVozidla.getModel(), null));
        OHsystem.setVysloOPT(vysledok);
        jTextPane3.setText(vysledok.toString());
        cmbEditaciaZvozu.setModel(OHsystem.getcmbTrasy((DefaultComboBoxModel) cmbEditaciaZvozu.getModel()));
        vytvorEditaciuZasahu();
        jTabbedPane4.setEnabledAt(1, true);
        vycisteniePoNavrhuJazd();


    }//GEN-LAST:event_jButton2ActionPerformed
    public void vycisteniePoNavrhuJazd() {
        jTextField7.setText("");
        jTextField6.setText("");
        jCheckBox_Vyprazdnenie.setSelected(false);
        jCheckBoxAktualizacia.setSelected(false);
        nastavDatumy();
        jCB_druhOdpadu.setSelectedItem("Všetky");
        jCB_percentoZaplnenia.setSelectedItem("0");
        refreshKontajnery();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jCB_idecka.getSelectedItem().toString().equals("")) {
            vykreslujTabulkuKontajnerov();
        } else {
            vyhladajKontajnerDoTabulky();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void vyhladajKontajnerDoTabulky() {
        int id_kontajnera = -1;
        if (!jCB_idecka.getSelectedItem().toString().equals("")) {
            id_kontajnera = Integer.parseInt("" + jCB_idecka.getSelectedItem());
//            System.out.println("ID_kontajnera: " + id_kontajnera);
        }
        if (id_kontajnera == -1) {
            //JOptionPane.showMessageDialog(this, "Problem");
            vykreslujTabulku = false;
            jCB_druhOdpadu.setSelectedItem("Všetky");
            jCB_percentoZaplnenia.setSelectedItem("0");
            vykreslujTabulku = true;
            vykreslujTabulkuKontajnerov();
            return;
        }
        jTable_Kontajnery.setModel(OHsystem.getKontajnery(id_kontajnera, (DefaultTableModel) jTable_Kontajnery.getModel()));
        naformatujTabulkuKontajnerov();
        loadPodTabulkouKontajnerov();
        vykreslujTabulku = false;
        jCB_druhOdpadu.setSelectedItem("" + jTable_Kontajnery.getValueAt(0, 2));
        jCB_percentoZaplnenia.setSelectedItem("" + jTable_Kontajnery.getValueAt(0, 3));
        vykreslujTabulku = true;

    }

    public void vykreslujTabulkuKontajnerov() {
//        System.out.println("kva kva kva");
        String odpad = jCB_druhOdpadu.getSelectedItem().toString();
        int percento = Integer.parseInt(jCB_percentoZaplnenia.getSelectedItem().toString());
        Date vyprazdnenie;
        Date aktualizacia;
        if (jCheckBox_Vyprazdnenie.isSelected()) {
            vyprazdnenie = jDateChooser_Vyprazdnenie.getDate();
        } else {
            vyprazdnenie = null;
        }
        if (jCheckBoxAktualizacia.isSelected()) {
            aktualizacia = jDateChooser_Aktualizácia.getDate();
        } else {
            aktualizacia = null;
        }

        jTable_Kontajnery.setModel(OHsystem.getKontajnery(odpad, percento, vyprazdnenie, aktualizacia, (DefaultTableModel) jTable_Kontajnery.getModel()));

        double sum = 0;
        int pocetStojisk = 0;
        int id_predchadzajucehoStojiska = -1;
        for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
            if (i == 0) {
                id_predchadzajucehoStojiska = (int) jTable_Kontajnery.getValueAt(i, 0);
                pocetStojisk++;
                sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
            } else {
                if (id_predchadzajucehoStojiska != (int) jTable_Kontajnery.getValueAt(i, 0)) {
                    pocetStojisk++;
                    id_predchadzajucehoStojiska = (int) jTable_Kontajnery.getValueAt(i, 0);
                }
                sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
            }
        }
        naformatujTabulkuKontajnerov();
        jTextField1.setText("" + sum);
        jTextField2.setText("" + sum);
        jTextField4.setText("" + pocetStojisk);
        jTextField5.setText("" + pocetStojisk);
    }

    private void jButton3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        jTextField3.setText(f.getAbsolutePath());
    }//GEN-LAST:event_jButton3ActionPerformed


    private void jButton4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Object[] hlavicky = OHsystem.importGrafuZGeoJason(jTextField3.getText());
        Object[][] data = new Object[OHsystem.getGraph().getUzly().size()][OHsystem.getGraph().getUzly().size() + 1];
        Object[][] data2 = new Object[OHsystem.getGraph().getUzly().size()][OHsystem.getGraph().getUzly().size() + 1];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = hlavicky[i + 1];
            data2[i][0] = hlavicky[i + 1];
            for (int j = 0; j < OHsystem.getGraph().getUzly().size(); j++) {
                data[i][j + 1] = OHsystem.getGraph().getid_Dmatrix()[i][j];
                data2[i][j + 1] = OHsystem.getGraph().getS_Dmatrix()[i][j];
            }
        }

        DefaultTableModel mod = new DefaultTableModel(data, hlavicky);
        mod.setColumnIdentifiers(hlavicky);
        jTable_MaticaVzdialenosti.setModel(mod);
        jTable_MaticaVzdialenosti.setShowHorizontalLines(true);
        jTable_MaticaVzdialenosti.setShowVerticalLines(true);
        jTable_MaticaVzdialenosti.setAutoResizeMode(jTable_MaticaVzdialenosti.AUTO_RESIZE_OFF);

        DefaultTableModel mod1 = new DefaultTableModel(data2, hlavicky);
        mod.setColumnIdentifiers(hlavicky);
        jTableMaticaUzly.setModel(mod1);
        jTableMaticaUzly.setShowHorizontalLines(true);
        jTableMaticaUzly.setShowVerticalLines(true);
        jTableMaticaUzly.setAutoResizeMode(jTableMaticaUzly.AUTO_RESIZE_OFF);


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jPanel1FocusGained(FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        JXMapViewer mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        tileFactory.setThreadPoolSize(8);
        GeoPosition position = new GeoPosition(49.088550, 18.635149);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(position);// TODO add your handling code here:

        jPanel4.add(mapViewer);
    }//GEN-LAST:event_jPanel1FocusGained

    private void jButton6ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //    System.out.println("UKLADAM");
        OHsystem.getGraph().saveMatrixToFile();

        //    System.out.println("Ukladam uzly");
        OHsystem.getGraph().saveUzly();

        //    System.out.println("Ukladam hrany");
        OHsystem.getGraph().saveHrany();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        OHsystem.getGraph().loadUzlyFromFile();
        OHsystem.getGraph().loadHranyFromFile();
        OHsystem.getGraph().loadMatrixFromFile();
        Object[] hlavicky = new Object[OHsystem.getGraph().getUzly().size() + 1];
        hlavicky[0] = new String("SRC/DEST");
        for (int i = 1; i < hlavicky.length; i++) {
            hlavicky[i] = OHsystem.getGraph().getUzly().get(i - 1).getId();
        }
        Object[][] data = new Object[OHsystem.getGraph().getUzly().size()][OHsystem.getGraph().getUzly().size() + 1];
        Object[][] data2 = new Object[OHsystem.getGraph().getUzly().size()][OHsystem.getGraph().getUzly().size() + 1];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = hlavicky[i + 1];
            data2[i][0] = hlavicky[i + 1];
            for (int j = 0; j < OHsystem.getGraph().getUzly().size(); j++) {
                data[i][j + 1] = OHsystem.getGraph().getid_Dmatrix()[i][j];
                data2[i][j + 1] = OHsystem.getGraph().getS_Dmatrix()[i][j];
            }
        }
        DefaultTableModel mod = new DefaultTableModel(data, hlavicky);
        mod.setColumnIdentifiers(hlavicky);
        jTable_MaticaVzdialenosti.setModel(mod);
        jTable_MaticaVzdialenosti.setShowHorizontalLines(true);
        jTable_MaticaVzdialenosti.setShowVerticalLines(true);
        jTable_MaticaVzdialenosti.setAutoResizeMode(jTable_MaticaVzdialenosti.AUTO_RESIZE_OFF);
        DefaultTableModel mod1 = new DefaultTableModel(data2, hlavicky);
        mod.setColumnIdentifiers(hlavicky);
        jTableMaticaUzly.setModel(mod1);
        jTableMaticaUzly.setShowHorizontalLines(true);
        jTableMaticaUzly.setShowVerticalLines(true);
        jTableMaticaUzly.setAutoResizeMode(jTableMaticaUzly.AUTO_RESIZE_OFF);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel2FocusLost(FocusEvent evt) {//GEN-FIRST:event_jPanel2FocusLost
        String odpad = jCB_druhOdpadu.getSelectedItem().toString();
        int percento = Integer.parseInt(jCB_percentoZaplnenia.getSelectedItem().toString());
        Date vyprazdnenie = jDateChooser_Vyprazdnenie.getDate();
        Date aktualizacia = jDateChooser_Aktualizácia.getDate();
        jTable_Kontajnery.setModel(OHsystem.getKontajnery(odpad, percento, vyprazdnenie, aktualizacia, (DefaultTableModel) jTable_Kontajnery.getModel()));
        double sum = 0;
        for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
            sum += ((int) jTable_Kontajnery.getValueAt(i, 2)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 3));
        }
        jTextField1.setText("" + sum);
        jTextField2.setText("" + sum);
        //this.repaint();
    }//GEN-LAST:event_jPanel2FocusLost

    private void jCheckBox_VyprazdnenieActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox_VyprazdnenieActionPerformed
        if (jCheckBox_Vyprazdnenie.isSelected()) {
            jDateChooser_Vyprazdnenie.setEnabled(true);
            jDateChooser_Vyprazdnenie.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser_Vyprazdnenie.setEnabled(false);
            jDateChooser_Vyprazdnenie.setDate(parseDate("1.1.2021"));
        }
    }//GEN-LAST:event_jCheckBox_VyprazdnenieActionPerformed

    private void jCheckBoxAktualizaciaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAktualizaciaActionPerformed
        if (jCheckBoxAktualizacia.isSelected()) {
            jDateChooser_Aktualizácia.setEnabled(true);
            jDateChooser_Aktualizácia.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser_Aktualizácia.setEnabled(false);
            jDateChooser_Aktualizácia.setDate(parseDate("1.1.2021"));
        }
    }//GEN-LAST:event_jCheckBoxAktualizaciaActionPerformed

    private void jCheckBox_Vyprazdnenie2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox_Vyprazdnenie2ActionPerformed
        if (jCheckBox_Vyprazdnenie2.isSelected()) {
            jDateChooser_Vyprazdnenie2.setEnabled(true);
            jDateChooser_Vyprazdnenie2.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser_Vyprazdnenie2.setEnabled(false);
            jDateChooser_Vyprazdnenie2.setDate(parseDate("1.1.2021"));
        }
    }//GEN-LAST:event_jCheckBox_Vyprazdnenie2ActionPerformed

    private void jCheckBoxAktualizacia2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAktualizacia2ActionPerformed
        if (jCheckBoxAktualizacia2.isSelected()) {
            jDateChooser2_Aktualizácia2.setEnabled(true);
            jDateChooser2_Aktualizácia2.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser2_Aktualizácia2.setEnabled(false);
            jDateChooser2_Aktualizácia2.setDate(parseDate("1.1.2021"));
        }
    }//GEN-LAST:event_jCheckBoxAktualizacia2ActionPerformed

    private void jButton14ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (vykreslujTabulku) {
            vykresliMapu();
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    public void vykresliMapu() {
//        System.out.println("###########");
        String odpad = jCB_druhOdpadu4.getSelectedItem().toString();
        int percento = Integer.parseInt(jCB_percentoZaplnenia4.getSelectedItem().toString());
        Date vyprazdnenie;
        Date aktualizacia;
        if (jCheckBox_Vyprazdnenie2.isSelected()) {
            vyprazdnenie = jDateChooser_Vyprazdnenie2.getDate();
        } else {
            vyprazdnenie = null;
        }
        if (jCheckBoxAktualizacia2.isSelected()) {
            aktualizacia = jDateChooser2_Aktualizácia2.getDate();
        } else {
            aktualizacia = null;
        }
        jTable_Kontajnery.setModel(OHsystem.getKontajnery(odpad, percento, vyprazdnenie, aktualizacia, (DefaultTableModel) jTable_Kontajnery.getModel()));
        double sum = 0;
        for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
            sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable_Kontajnery.getColumnCount(); i++) {
            if (i != (jTable_Kontajnery.getColumnCount() - 3)) {
                jTable_Kontajnery.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 1).setMinWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 1).setMaxWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 2).setMinWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 2).setMaxWidth(0);
        jTextField1.setText("" + sum);
        jTextField2.setText("" + sum);
        mapa.zobrazRozmiestnenieStojisk(jInternalFrame1);
    }


    private void jTable_KontajneryMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable_KontajneryMouseClicked
        int row = jTable_Kontajnery.rowAtPoint(evt.getPoint());
        int col = jTable_Kontajnery.columnAtPoint(evt.getPoint());

        if (col == 8) {
            double sum = 0;
            int pocetVybranychStojisk = 0;
            int id_predchadzajuceho = -1;
            for (int i = 0; i < jTable_Kontajnery.getRowCount(); i++) {
                if ((boolean) jTable_Kontajnery.getValueAt(i, 8) == true) {
                    if (id_predchadzajuceho != (int) jTable_Kontajnery.getValueAt(i, 0)) {
                        id_predchadzajuceho = (int) jTable_Kontajnery.getValueAt(i, 0);
                        pocetVybranychStojisk++;
                    }
                    sum += ((int) jTable_Kontajnery.getValueAt(i, 3)) * 0.01 * ((int) jTable_Kontajnery.getValueAt(i, 4));
                }
            }
            jTextField2.setText("" + sum);
            jTextField5.setText("" + pocetVybranychStojisk);
            jTable_Kontajnery.repaint();
        } else if (col == 9 && evt.getClickCount() == 2) {
            OHsystem.zobrazGrafVyvojaOdpaduVNadobe(Integer.parseInt("" + jTable_Kontajnery.getValueAt(row, 1)));

        } else if (col == 10 && evt.getClickCount() == 2) {
            OHsystem.zobrazGrafVyvojaOdpaduNaStojisku(Integer.parseInt("" + jTable_Kontajnery.getValueAt(row, 0)), "" + jTable_Kontajnery.getValueAt(row, 2));
        } else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            try {
                new JFUpdateKontajner(OHsystem, Integer.parseInt("" + jTable_Kontajnery.getValueAt(row, 1))).setVisible(true);
            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, er.getClass().getName());
            }
        } else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON3) {

            jTable_Kontajnery.setRowSelectionInterval(row, row);

            int id = Integer.parseInt("" + jTable_Kontajnery.getValueAt(row, 1));
            String stoj = "" + jTable_Kontajnery.getValueAt(row, 5);

            //Object[] ponuka = {"Áno", " Nie"};
            moznost = JOptionPane.showOptionDialog(null,
                    "Naozaj chcete vyprázdniť kontajner: " + jTable_Kontajnery.getValueAt(row, 1)
                    + "\n na stojisku: " + stoj,
                    "Vyprázdnenie kontajnera",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //ikona
                    ponuka,
                    ponuka[0]);

            if (moznost == 0) {
                OHsystem.vyprazdniKontajner(id);
            }
        }


    }//GEN-LAST:event_jTable_KontajneryMouseClicked

    private void jButton8ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        ArrayList<String> prijemcovia = new ArrayList<>();
        String s = jTextField8.getText();
        int index;
        while (s.length() > 0) {
            index = s.indexOf(";");
            if (index == -1) {
                prijemcovia.add(s);
                break;
            } else {
                prijemcovia.add(s.substring(0, index));
                s = s.substring(index + 1);
            }
        }
        String text = jTextPane4.getText();
//        System.out.println("Pocet znakov: " + text.length());
//        System.out.println("*****");
//        System.out.println(text);

//        System.out.println(Arrays.toString(prijemcovia.toArray()));
        SMS sms = new SMS(text, jTextField9.getText(), prijemcovia);
        s = OHsystem.getMsg().posliSMS(sms);

        if (s.equals("problem")) {
            jTextPane4.setText("Nepodarilo sa pripojiť k SMS bráne");
        } else {
            //String status = s.substring(0,s.indexOf("@"));
            if (s.substring(0, s.indexOf("@")).equalsIgnoreCase("OK")) {
                jTextPane4.setText("Správa úspešne odoslaná");
                jButton8.setVisible(false);
            } else {
                jTextPane4.setText(s);
            }

        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if (jButton8.isVisible()) {
            moznost = JOptionPane.showOptionDialog(null,
                    "Neodoslali ste SMS pre začatie jazdy, napriek tomu zavrieť ?",
                    "Zavretie Messengera",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //ikona
                    ponuka,
                    ponuka[0]);
            if (moznost == 0) {
                jTextField8.setText("+421944339622");
                jTabbedPane5.setSelectedIndex(0);
                jCheckBox1.setVisible(false);
            }
            return;
        }
        jTextField8.setText("+421944339622");
        jTabbedPane5.setSelectedIndex(0);
        jButton8.setVisible(true);
        jCheckBox1.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        OHsystem.spracujPrijateSpravy();
        refreshKontajnery();
        jTextPane3.setText(Arrays.toString(OHsystem.getNespracovaneSpravy().toArray()));
        jBNespracovaneSpravy.setText("" + OHsystem.getNespracovaneSpravy().size());
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        new JFPridajKontajner(OHsystem).setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        zobrazujemJednotlivo = true;
        LinkedList<Integer> ids = new LinkedList<>();
        for (int i = 1; i < jTable_Zasah.getColumnCount(); i++) {
            ids.add(Integer.parseInt("" + jTable_Zasah.getColumnName(i)));
        }
//        System.out.println(ids.toString());
        mapa2.zobrazTrasu(jInternalFrame2, ids);
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jBNespracovaneSpravyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNespracovaneSpravyActionPerformed
        new JF_Messenger(OHsystem).setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jBNespracovaneSpravyActionPerformed

    private void jButton13ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (OHsystem.getVysloOPT() != null) {
            if (!OHsystem.getVysloOPT().getTrasy().isEmpty()) {
                mapa2.zobrazTrasy(jInternalFrame2, OHsystem.getVysloOPT().getTrasy());
                jTabbedPane1.setSelectedIndex(3);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Nie sú pripravené trasy na zobrazenie");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void cmbEditaciaZvozuItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_cmbEditaciaZvozuItemStateChanged
        vytvorEditaciuZasahu();
    }//GEN-LAST:event_cmbEditaciaZvozuItemStateChanged

    private void jTable_ZasahComponentResized(ComponentEvent evt) {//GEN-FIRST:event_jTable_ZasahComponentResized
        if (jTable_Zasah.getColumnCount() > 10) {
            jTable_Zasah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jTable_Zasah.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        }


    }//GEN-LAST:event_jTable_ZasahComponentResized

    private void jButton15ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if (jButton15.getText().equals("Uprav trasu")) {
            jButton28.setVisible(true);
            this.col_start = jTable_Zasah.getColumnModel().getColumn(1);
            jTable_Zasah.removeColumn(col_start);
            this.col_end = jTable_Zasah.getColumnModel().getColumn(jTable_Zasah.getColumnCount() - 1);
            jTable_Zasah.removeColumn(col_end);
            jTable_Zasah.getTableHeader().setReorderingAllowed(true);
            jButton15.setText("Ulož trasu");
            cmbEditaciaZvozu.setEditable(false);
            cmbEditaciaZvozu.setEnabled(false);
            jTextField_dlzkaTrasy1.setVisible(true);
            jTextField_dlzkaTrasy2.setVisible(true);
            jLabel19.setVisible(true);
            jLabel20.setVisible(true);
            editujemZasah = true;
        } else if (jButton15.getText().equals("Ulož trasu")) {
            if (col_start != null && col_end != null) {
                jTable_Zasah.getColumnModel().addColumn(col_start);
                jTable_Zasah.moveColumn(jTable_Zasah.getColumnCount() - 1, 1);
                jTable_Zasah.getColumnModel().addColumn(col_end);
                jTable_Zasah.getTableHeader().setReorderingAllowed(false);
                col_start = null;
                col_end = null;
                jButton15.setText("Uprav trasu");
                cmbEditaciaZvozu.setEditable(true);
                cmbEditaciaZvozu.setEnabled(true);
                jTextField_dlzkaTrasy1.setText("");
                jTextField_dlzkaTrasy2.setText("");
                jTextField_dlzkaTrasy1.setVisible(false);
                jTextField_dlzkaTrasy2.setVisible(false);
                jLabel19.setVisible(false);
                jLabel20.setVisible(false);
                vykreslujTabulkuKontajnerov();
                naformatujTabulkuKontajnerov();
                editujemZasah = false;
                jButton28.setVisible(false);
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        String trasa = "" + cmbEditaciaZvozu.getSelectedItem();
        int por = Integer.parseInt(trasa.substring(trasa.indexOf(": ") + 2));
        String s = "";
        String o = "";

        //ArrayList<Integer> idecka = new ArrayList<>();
        for (int i = 1; i < jTable_Zasah.getModel().getColumnCount(); i++) {
            //idecka.add(Integer.parseInt(jTable_Zasah.getColumnName(i)));
            s += "" + jTable_Zasah.getColumnName(i) + ",";
            o += "" + jTable_Zasah.getValueAt(1, i) + ",";
        }
        o = o.replaceAll("-", "0");
//        System.out.println("Hotovy s: " + s);
//        System.out.println("Hotovy o: " + o);

        OHsystem.createJazda(s, o, Integer.parseInt(jTextField_dlzkaTrasy.getText()), Integer.parseInt(jTextField_kapacita.getText()), "nevybrane", jTextField_DruhOdpadu.getText());
        cmbEditaciaZvozu.setModel(OHsystem.odoberNavrhTrasy(por, (DefaultComboBoxModel) cmbEditaciaZvozu.getModel()));
        vytvorEditaciuZasahu();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jCB_druhOdpadu4ItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_druhOdpadu4ItemStateChanged
        if (vykreslujTabulku) {
            vykresliMapu();
        }
    }//GEN-LAST:event_jCB_druhOdpadu4ItemStateChanged

    private void jCB_percentoZaplnenia4ItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_percentoZaplnenia4ItemStateChanged
        if (vykreslujTabulku) {
            vykresliMapu();
        }
    }//GEN-LAST:event_jCB_percentoZaplnenia4ItemStateChanged

    private void jDateChooser_Vyprazdnenie2PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_Vyprazdnenie2PropertyChange
        if (jCB_druhOdpadu4.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykresliMapu();
            }
        }
    }//GEN-LAST:event_jDateChooser_Vyprazdnenie2PropertyChange

    private void jDateChooser2_Aktualizácia2PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2_Aktualizácia2PropertyChange
        if (jCB_druhOdpadu4.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykresliMapu();
            }
        }
    }//GEN-LAST:event_jDateChooser2_Aktualizácia2PropertyChange

    private void jCB_druhOdpaduItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_druhOdpaduItemStateChanged
        if (jCB_druhOdpadu.getSelectedItem() != null && jCB_percentoZaplnenia.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykreslujTabulkuKontajnerov();
                aktiv = false;
                jCB_idecka.setSelectedItem("");
                aktiv = true;
            }
        }
    }//GEN-LAST:event_jCB_druhOdpaduItemStateChanged

    private void jCB_percentoZaplneniaItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_percentoZaplneniaItemStateChanged
        if (jCB_druhOdpadu.getSelectedItem() != null && jCB_percentoZaplnenia.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykreslujTabulkuKontajnerov();
                aktiv = false;
                jCB_idecka.setSelectedItem("");
                aktiv = true;
            }
        }
    }//GEN-LAST:event_jCB_percentoZaplneniaItemStateChanged

    private void jDateChooser_VyprazdneniePropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_VyprazdneniePropertyChange
        if (jCB_druhOdpadu.getSelectedItem() != null && jCB_percentoZaplnenia.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykreslujTabulkuKontajnerov();
            }
        }
    }//GEN-LAST:event_jDateChooser_VyprazdneniePropertyChange

    private void jDateChooser_AktualizáciaPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_AktualizáciaPropertyChange
        if (jCB_druhOdpadu.getSelectedItem() != null && jCB_percentoZaplnenia.getSelectedItem() != null) {
            if (vykreslujTabulku) {
                vykreslujTabulkuKontajnerov();
            }
        }
    }//GEN-LAST:event_jDateChooser_AktualizáciaPropertyChange

    private void jCB_ideckaItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_ideckaItemStateChanged
//        System.out.println("EVT: " + evt.getItem().toString());
        if (aktiv) {
            if (aktualizuj) {
                aktualizuj = false;
                vyhladajKontajnerDoTabulky();
                return;
            }
            aktualizuj = true;
        }

    }//GEN-LAST:event_jCB_ideckaItemStateChanged

    private void jTabbedPane1StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        if (jTabbedPane1.getSelectedIndex() == 2) {
            mapa.zobrazRozmiestnenieStojisk(jInternalFrame1);
        }
        if (jTabbedPane1.getSelectedIndex() == 3) {
            if (zobrazujemJednotlivo) {
                zobrazujemJednotlivo = false;
            } else {
                if (OHsystem.getVysloOPT() != null) {
                    mapa2.zobrazTrasy(jInternalFrame2, OHsystem.getVysloOPT().getTrasy());
                } else {
                    jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Nemáte pripravené trasy na zobrazenie");
                }
            }
        }        
      if(jTabbedPane1.getSelectedIndex()==4){
          pripravOptimalizaciuStojisk();
      }  
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jDateChooser_jazda_doPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_jazda_doPropertyChange
        if (vykreslujTabulku) {
            vykreslujTabulkuJazd();
        }
    }//GEN-LAST:event_jDateChooser_jazda_doPropertyChange

    private void jCheckBox_jazda_doActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox_jazda_doActionPerformed
        if (jCheckBox_jazda_do.isSelected()) {
            jDateChooser_jazda_do.setEnabled(true);
            jDateChooser_jazda_do.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser_jazda_do.setEnabled(false);
            jDateChooser_jazda_do.setDate(parseDate("1.1.2021"));
        }

    }//GEN-LAST:event_jCheckBox_jazda_doActionPerformed

    private void jDateChooser_jazda_odPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_jazda_odPropertyChange
        if (vykreslujTabulku) {
            vykreslujTabulkuJazd();
        }
    }//GEN-LAST:event_jDateChooser_jazda_odPropertyChange

    private void jCheckBox_jazda_odActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox_jazda_odActionPerformed
        if (jCheckBox_jazda_od.isSelected()) {
            jDateChooser_jazda_od.setEnabled(true);
            jDateChooser_jazda_od.setDate(new Date(System.currentTimeMillis()));
        } else {
            jDateChooser_jazda_od.setEnabled(false);
            jDateChooser_jazda_od.setDate(parseDate("1.1.2021"));
        }

    }//GEN-LAST:event_jCheckBox_jazda_odActionPerformed

    public void vykreslujTabulkuJazd() {
        String stav = jCB_stav.getSelectedItem().toString();
        String spz = jCB_SPZZ.getSelectedItem().toString();
        String odpad = jCB_druhOdpadu3.getSelectedItem().toString();
        Date od = null;
        Date doo = null;
        if (jCheckBox_jazda_od.isSelected()) {
            od = jDateChooser_jazda_od.getDate();
        }
        if (jCheckBox_jazda_do.isSelected()) {
            doo = jDateChooser_jazda_do.getDate();
        }
        jTable_Jazdy.setModel(OHsystem.getKnihaJazd(spz, od, doo, stav, odpad, (DefaultTableModel) jTable_Jazdy.getModel()));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable_Jazdy.getColumnCount() - 1; i++) {
            jTable_Jazdy.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }


    private void jCB_stavItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_stavItemStateChanged
        if (vykreslujTabulku) {
//            System.out.println("CMB_stav_jazdy");
            vykreslujTabulkuJazd();
//            System.out.println("************");
        }
    }//GEN-LAST:event_jCB_stavItemStateChanged

    private void jCB_SPZZItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_SPZZItemStateChanged
        if (vykreslujTabulku) {
//            System.out.println("CMB_SPZ");
            vykreslujTabulkuJazd();
//            System.out.println("************");
        }
    }//GEN-LAST:event_jCB_SPZZItemStateChanged

    private void jCB_druhOdpadu3ItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jCB_druhOdpadu3ItemStateChanged
        if (vykreslujTabulku) {
//            System.out.println("CMB_DruhOdpadu");
            vykreslujTabulkuJazd();
//            System.out.println("************");
        }
    }//GEN-LAST:event_jCB_druhOdpadu3ItemStateChanged

    private void jTabbedPane3StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane3StateChanged
        if (jTabbedPane3.getSelectedIndex() == 1) {
            zobrazAktualneJazdy();
            vycistiUpravuJazdy(false);

        }
    }//GEN-LAST:event_jTabbedPane3StateChanged

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.rowAtPoint(evt.getPoint());
        vycistiUpravuJazdy(true);
        jLabel30.setText("Úprava jazdy: " + jTable1.getValueAt(row, 0));
        if (jTable1.getValueAt(row, 8) != null) {
            jTextField10.setText(Double.parseDouble("" + jTable1.getValueAt(row, 8)) / 1000 + "Km");
        }

        if (jTable1.getValueAt(row, 5) != null) {
            jTextField14.setText("" + jTable1.getValueAt(row, 5));
        }

        jTable2.setModel(OHsystem.getStojiskaJazdy(Integer.parseInt(jTable1.getValueAt(row, 0).toString()), (DefaultTableModel) jTable2.getModel()));
        refreshEditaciaStojiskaJazdy();
        String stav = "" + jTable1.getValueAt(row, 1);
        riadok = row;

        if (stav.equals("nový")) {
            nastavPanel(jPanel14, false);
            jTable2.setEnabled(false);
            jButton18.setEnabled(true);
            jComboBox1.addItem("nový");
            jComboBox1.addItem("prebiehajúci");
            jButton18.setEnabled(true);
            jTextField11.setEnabled(false);
            jTextField13.setEnabled(false);
            jTextField12.setEnabled(false);
            jTextField15.setText("" + jTable1.getValueAt(row, 3));
            jTextField15.setEditable(false);
            jCheckBox1.setVisible(true);
            jButton17.setVisible(true);

        } else if (stav.equals("prebiehajúci")) {
            jTextField13.setText("" + OHsystem.getStavTachometra(jTable1.getValueAt(row, 2).toString()));
            nastavPanel(jPanel14, false);
            jTable2.setEnabled(false);
            jButton18.setEnabled(true);
            jButton18.setEnabled(true);
            jComboBox2.setEnabled(false);
            jTextField11.setEnabled(false);
            jTextField12.setEnabled(false);
            jTextField15.setText("" + jTable1.getValueAt(row, 3));
            jTextField15.setEditable(false);
            jTextField13.setEditable(false);
            jComboBox1.addItem("prebiehajúci");
            jComboBox1.addItem("ukončený");
            jButton17.setVisible(true);
        } else if (stav.equals("ukončený")) {
            jTextField13.setText("" + OHsystem.getStavTachometra(jTable1.getValueAt(row, 2).toString()));
            jTextField13.setEditable(false);
            nastavPanel(jPanel14, true);
            jTable2.setEnabled(true);
            jComboBox2.setEnabled(false);
            jComboBox1.addItem("ukončený");
            jComboBox1.addItem("uzavretý");
            jTextField15.setText("" + jTable1.getValueAt(row, 3));
            jTextField15.setEditable(false);
            jButton17.setVisible(true);

        } else if (stav.equals("servisová")) {
            jLabel30.setText("Úprava servisovej jazdy: " + jTable1.getValueAt(row, 0));
            jComboBox2.setEnabled(false);
            jComboBox1.addItem("servisová");
            jComboBox1.addItem("servisová uzavretá");
            jTextField10.setEnabled(false);
            jTextField14.setEnabled(false);
            jTextField12.setEnabled(false);
            jButton18.setEnabled(true);
            jTextField13.setText("" + OHsystem.getStavTachometra(jTable1.getValueAt(row, 2).toString()));
            jTextField13.setEditable(false);
            jTextField15.setEditable(true);
            nastavPanel(jPanel14, false);
            jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
        }

        String spz = jTable1.getValueAt(row, 2).toString();
        if (spz.equals("nezadaná")) {
            jComboBox2.addItem(spz);
            jComboBox2.setSelectedItem(spz);
        } else {
            if (!stav.equals("servisová")) {
                jComboBox2.setModel(OHsystem.GetVozidlaMinKapacityVolne(Integer.parseInt("" + jTable1.getValueAt(row, 5)), "" + jTable1.getValueAt(row, 2), (DefaultComboBoxModel) jComboBox2.getModel()));
                jComboBox2.addItem(spz);
                jComboBox2.setSelectedItem(spz);
            } else {
                jComboBox2.addItem(spz);
                jComboBox2.setSelectedItem(spz);
                jComboBox2.setEnabled(false);
                jTextField15.setText("" + jTable1.getValueAt(row, 3));
            }

        }


    }//GEN-LAST:event_jTable1MouseClicked

    private void jTableVybraneVozidlaComponentResized(ComponentEvent evt) {//GEN-FIRST:event_jTableVybraneVozidlaComponentResized
        if (jTableVybraneVozidla.getColumnCount() > 10) {
            jTableVybraneVozidla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jTableVybraneVozidla.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        }
    }//GEN-LAST:event_jTableVybraneVozidlaComponentResized

    private void jTable_VozidlaKapacitneMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable_VozidlaKapacitneMouseClicked
        int row = jTable_VozidlaKapacitne.rowAtPoint(evt.getPoint());
        int col = jTable_VozidlaKapacitne.columnAtPoint(evt.getPoint());
        if (col == jTable_VozidlaKapacitne.getColumnCount() - 1) {
            int kapacita = Integer.parseInt("" + jTable_VozidlaKapacitne.getValueAt(row, 0));

            int priority[] = null;//
            if (jTableVybraneVozidla.getColumnCount() > 1) {
                priority = new int[jTableVybraneVozidla.getColumnCount() - 1];
                for (int i = 1; i < jTableVybraneVozidla.getColumnCount(); i++) {
                    priority[i - 1] = Integer.parseInt(jTableVybraneVozidla.getValueAt(1, i).toString());
                }
            }

            jTableVybraneVozidla.setModel(OHsystem.getVybraneVozidla(kapacita, (DefaultTableModel) jTableVybraneVozidla.getModel(), priority));
            jTextField7.setText("" + OHsystem.getVozidla_navrhovane().size());
            jTextField6.setText("" + OHsystem.getSumKap());
            jTableVybraneVozidla.setRowHeight(2, 33);
            jTableVybraneVozidla.setRowHeight(1, 33);
            jTableVybraneVozidla.setRowHeight(0, 25);
        }
    }//GEN-LAST:event_jTable_VozidlaKapacitneMouseClicked

    private void jTableVybraneVozidlaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTableVybraneVozidlaMouseClicked
        int row = jTableVybraneVozidla.rowAtPoint(evt.getPoint());
        int col = jTableVybraneVozidla.columnAtPoint(evt.getPoint());
        if (row == 2 && col > 0) {
            jTableVybraneVozidla.setModel(OHsystem.getVybraneVozidla(col - 1, (DefaultTableModel) jTableVybraneVozidla.getModel()));
            jTextField7.setText("" + OHsystem.getVozidla_navrhovane().size());
            jTextField6.setText("" + OHsystem.getSumKap());
            jTableVybraneVozidla.setRowHeight(2, 33);
            jTableVybraneVozidla.setRowHeight(1, 33);
            jTableVybraneVozidla.setRowHeight(0, 25);
        }
    }//GEN-LAST:event_jTableVybraneVozidlaMouseClicked

    private void jButton21ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        int staryTachometer;
        int tachometer;
        String predmet;

        String spz = "" + jComboBox2.getSelectedItem();
        String stav = "" + jComboBox1.getSelectedItem();

//        System.out.println("spz:" + spz);
        if (!spz.equals("Nezadaná") && spz.length() != 7) {
//            System.out.println("riadok:" + riadok);
            spz = spz.substring(spz.indexOf("ŠPZ: ") + 5, spz.indexOf(" Stav"));
//            System.out.println(spz);
        }
        if (riadok == -1) {
            switch (stav) {
                case "servisová":
                    predmet = jTextField15.getText();
                    if (predmet.equals("")) {
                        JOptionPane.showMessageDialog(this, "Prosím uveďte predmet servisovej jazdy");
                        return;
                    }
                    moznost = JOptionPane.showOptionDialog(null,
                            "Naozaj chcete vytvoriť servisovú jazdu pre vozidlo: " + spz + " ?",
                            "Vytvorenie servisovej jazdy",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, //ikona
                            ponuka,
                            ponuka[0]);
                    if (moznost == 0) {
                        jTable1.setModel(OHsystem.createServisovaJazda((DefaultTableModel) jTable1.getModel(), spz, predmet, -100));
                        JOptionPane.showMessageDialog(this, "Úspešne vytvorená servisová jazda pre vozidlo: " + spz);
                        refreshVozidla();
                        refreshVozidlaKapacitne();
                        cmb11aktiv = false;

                        if (jCheckBox1.isVisible() == true && jCheckBox1.isSelected() == true) {
                            jTabbedPane5.setSelectedIndex(1);
                            jTextPane4.setText(spz + " " + predmet);
                        }
                        break;
                    }
                    return;
                case "servisová uzavretá":
                    predmet = jTextField15.getText();
                    if (predmet.equals("")) {
                        JOptionPane.showMessageDialog(this, "Prosím uveďte predmet servisovej jazdy");
                        return;
                    }
                    staryTachometer = OHsystem.getStavTachometra(spz);
                    try {
                        tachometer = Integer.parseInt(jTextField11.getText());
                    } catch (Exception e) {
                        if (jTextField11.getText().equals("")) {
                            JOptionPane.showMessageDialog(this, "Prosím zadajte hodnotu z tachometra");
                            return;
                        }
                        JOptionPane.showMessageDialog(this, "Prosím zadajte správny formát (celé kladné čílo) tachometra");
                        jTextField11.setText("");
                        return;
                    }
                    if (tachometer < staryTachometer) {
                        JOptionPane.showMessageDialog(this, "Vami zadaný stav tachometra je menší ako doterajší stav (stočené km)");
                        jTextField11.setText("");
                        return;
                    }
                    jTextField13.setText((tachometer - staryTachometer) + "Km");

                    moznost = JOptionPane.showOptionDialog(null,
                            "Naozaj chcete vytvoriť servisovú- ukončenú jazdu pre vozidlo: " + spz + " ?",
                            "Vytvorenie servisovej - ukončenej jazdy",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, //ikona
                            ponuka,
                            ponuka[0]);
                    if (moznost == 0) {
                        jTable1.setModel(OHsystem.createServisovaJazda((DefaultTableModel) jTable1.getModel(), spz, predmet, (tachometer - staryTachometer)));
                        JOptionPane.showMessageDialog(this, "Úspešne vytvorená servisová - ukončená jazda");
                        cmb11aktiv = false;
                        refreshVozidla();
                    }
                    break;
            }
            vycistiUpravuJazdy(false);
            jTextField11.setText("");
            jTextField15.setText("");
            jTextField13.setText("");
            return;
        }
        switch ("" + jTable1.getValueAt(riadok, 1)) {
            case "nový":
                switch (stav) {
                    case "nový":
                        if (spz.equals("Nezadaná")) {
                            JOptionPane.showMessageDialog(this, "Zmena vozidla neúspešná: Nevybrali ste vozidlo");
                        } else if (spz.equals("" + jTable1.getValueAt(riadok, 2))) {
                            JOptionPane.showMessageDialog(this, "Zmena vozidla neúspešná: Vybrali ste rovnaké vozidlo");
                        } else {
                            moznost = JOptionPane.showOptionDialog(null,
                                    "Naozaj chcete  pre jazdu: " + jTable1.getValueAt(riadok, 0)
                                    + "\n zmeniť vozidlo na: " + spz,
                                    "Začatie jazdy",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null, //ikona
                                    ponuka,
                                    ponuka[0]);
                            if (moznost == 0) {
                                jTable1.setModel(OHsystem.updateAktualneJazdySPZ((DefaultTableModel) jTable1.getModel(),
                                        Integer.parseInt("" + jTable1.getValueAt(riadok, 0)),
                                        spz));
                                JOptionPane.showMessageDialog(this, "Zmena vozidla úspešná");

                                refreshVozidla();
                                refreshVozidlaKapacitne();
                                vycistiUpravuJazdy(false);
                                jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
                            }
                        }
                        break;

                    case "prebiehajúci":
                        if (spz.equals("Nezadaná")) {
                            JOptionPane.showMessageDialog(this, "Zahájenie jazdy neúspešné: Nevybrali ste vozidlo");
                        } else {
                            moznost = JOptionPane.showOptionDialog(null,
                                    "Naozaj chcete nastaviť jazdu: " + jTable1.getValueAt(riadok, 0)
                                    + "\n s vozidlom: " + spz + " na prebiehajúcu",
                                    "Začatie jazdy",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null, //ikona
                                    ponuka,
                                    ponuka[0]);
                            if (moznost == 0) {
                                jTable1.setModel(OHsystem.updateAktualne_zahajenieJazdy((DefaultTableModel) jTable1.getModel(),
                                        Integer.parseInt("" + jTable1.getValueAt(riadok, 0)),
                                        spz));
                                JOptionPane.showMessageDialog(this, "Zmena vozidla úspešná");
                                refreshVozidla();
                                refreshVozidlaKapacitne();
                                if (jCheckBox1.isVisible() == true && jCheckBox1.isSelected() == true) {
                                    jTabbedPane5.setSelectedIndex(1);
                                    String s = spz + " " + jTable1.getValueAt(riadok, 3) + ": ";
                                    for (int i = 0; i < jTable2.getColumnCount(); i++) {
                                        s += jTable2.getValueAt(1, i) + ",";
                                    }
                                    jTextPane4.setText(s);
                                }
                                vycistiUpravuJazdy(false);
                                jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));

                            }
                        }
                        break;
                }
                break;
            case "prebiehajúci":
                switch (stav) {
                    case "prebiehajúci":
                        JOptionPane.showMessageDialog(this, "Ukončenie jazdy neúspešné: Nevybrali ste nový stav");
                        break;
                    case "ukončený":
                        moznost = JOptionPane.showOptionDialog(null,
                                "Naozaj chcete ukončiť jazdu: " + jTable1.getValueAt(riadok, 0)
                                + "\n s vozidlom: " + spz + " ?",
                                "Ukončenie jazdy",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, //ikona
                                ponuka,
                                ponuka[0]);
                        if (moznost == 0) {
                            jTable1.setModel(OHsystem.updateAktualne_ukončenieJazdy((DefaultTableModel) jTable1.getModel(),
                                    Integer.parseInt("" + jTable1.getValueAt(riadok, 0))));
                            JOptionPane.showMessageDialog(this, "Úspešné ukončenie jazdy: " + jTable1.getValueAt(riadok, 0) + " s vozidlom: " + spz);
                            refreshVozidla();
                            refreshVozidlaKapacitne();
                            vycistiUpravuJazdy(false);
                            jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
                        }
                        break;
                }
                break;
            case "ukončený":
                jTable2.editCellAt(0, 0);
                switch (stav) {
                    case "ukončený":
                        JOptionPane.showMessageDialog(this, "Uzavretie jazdy neúspešné: Nevybrali ste nový stav");
                        break;
                    case "uzavretý":
                        staryTachometer = OHsystem.getStavTachometra(spz);
                        try {
                            tachometer = Integer.parseInt(jTextField11.getText());
                        } catch (Exception e) {
                            if (jTextField11.getText().equals("")) {
                                JOptionPane.showMessageDialog(this, "Prosím zadajte hodnotu z tachometra");
                                return;
                            }
                            JOptionPane.showMessageDialog(this, "Prosím zadajte správny formát (celé kladné čílo) tachometra");
                            jTextField11.setText("");
                            return;
                        }
                        if (tachometer < staryTachometer) {
                            JOptionPane.showMessageDialog(this, "Vami zadaný stav tachometra je menší ako doterajší stav (stočené km)");
                            jTextField11.setText("");
                            return;
                        }
                        jTextField13.setText((tachometer - staryTachometer) + "Km");
                        int objem;
                        try {
                            objem = Integer.parseInt(jTextField12.getText());
                        } catch (Exception e) {

                            if (jTextField12.getText().equals("")) {
                                JOptionPane.showMessageDialog(this, "Prosím zadajte zozbieraný objem");
                                return;
                            }

                            JOptionPane.showMessageDialog(this, "Prosím zadajte správny formát (celé kladné čílo) zozbieraného objemu");
                            jTextField12.setText("");
                            return;
                        }
                        String s = "";
                        for (int i = 1; i < jTable2.getColumnCount(); i++) {
                            s += jTable2.getValueAt(3, i) + ",";
                        }
                        moznost = JOptionPane.showOptionDialog(null,
                                "Naozaj chcete uzavrieť jazdu: " + jTable1.getValueAt(riadok, 0)
                                + "\n s vozidlom: " + spz + " ?",
                                "Uzavreie jazdy",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, //ikona
                                ponuka,
                                ponuka[0]);
                        if (moznost == 0) {
                            jTable1.setModel(OHsystem.updateAktualne_uzavretieJazdy((DefaultTableModel) jTable1.getModel(),
                                    Integer.parseInt("" + jTable1.getValueAt(riadok, 0)), (tachometer - staryTachometer), objem, s));
                            vycistiUpravuJazdy(false);
                            refreshVozidla();
                            refreshVozidlaKapacitne();
                            jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
                            break;
                        }
                        break;
                }
                break;

            case "servisová":
                switch (stav) {
                    case "servisová":
                        JOptionPane.showMessageDialog(this, "Ukončenie  servisovej jazdy neúspešné: Nevybrali ste nový stav");
                        break;

                    case "servisová uzavretá":
                        predmet = jTextField15.getText();
                        if (predmet.equals("")) {
                            JOptionPane.showMessageDialog(this, "Prosím uveďte predmet servisovej jazdy");
                            return;
                        }
                        staryTachometer = OHsystem.getStavTachometra(spz);
                        try {
                            tachometer = Integer.parseInt(jTextField11.getText());
                        } catch (Exception e) {
                            if (jTextField11.getText().equals("")) {
                                JOptionPane.showMessageDialog(this, "Prosím zadajte hodnotu z tachometra");
                                return;
                            }
                            JOptionPane.showMessageDialog(this, "Prosím zadajte správny formát (celé kladné čílo) tachometra");
                            jTextField11.setText("");
                            return;
                        }
                        if (tachometer < staryTachometer) {
                            JOptionPane.showMessageDialog(this, "Vami zadaný stav tachometra je menší ako doterajší stav (stočené km)");
                            jTextField11.setText("");
                            return;
                        }
                        jTextField13.setText((tachometer - staryTachometer) + "Km");

                        moznost = JOptionPane.showOptionDialog(null,
                                "Naozaj chcete uzavrieť servisovú jazdu pre vozidlo: " + spz + " ?",
                                "Uzavretie servisovej jazdy",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, //ikona
                                ponuka,
                                ponuka[0]);
                        if (moznost == 0) {
                            jTable1.setModel(OHsystem.updateServisovaJazda((DefaultTableModel) jTable1.getModel(), Integer.parseInt("" + jTable1.getValueAt(riadok, 0)), predmet, (tachometer - staryTachometer)));
                            JOptionPane.showMessageDialog(this, "Úspešne ukončená servisová jazda");
                            cmb11aktiv = false;
                            refreshVozidla();
                            refreshVozidlaKapacitne();
                            vycistiUpravuJazdy(false);
                        }
                        break;
                }
                break;

        }


    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTable_VozidlaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable_VozidlaMouseClicked
        int row = jTable_Vozidla.rowAtPoint(evt.getPoint());
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            try {
                updateCreateVozidlo("" + jTable_Vozidla.getValueAt(row, 0), true);
            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, er.getClass().getName());
            }

        } else if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON3) {
            try {
                updateCreateVozidlo("" + jTable_Vozidla.getValueAt(row, 0), false);
            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, er.getClass().getName());
            }

        }
    }//GEN-LAST:event_jTable_VozidlaMouseClicked

    private void jTable2ComponentResized(ComponentEvent evt) {//GEN-FIRST:event_jTable2ComponentResized
        if (jTable2.getColumnCount() > 10) {
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        }
    }//GEN-LAST:event_jTable2ComponentResized

    private void jButton18ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
//        Object[] ponuka = {"Áno", " Nie"};
        moznost = JOptionPane.showOptionDialog(null,
                "Naozaj chcete zrušiť jazdu: " + jTable1.getValueAt(riadok, 0)
                + "\n s vozidlom: " + jTable1.getValueAt(riadok, 2) + " ?",
                "Zrušenie jazdy",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //ikona
                ponuka,
                ponuka[0]);
        if (moznost == 0) {
            jTable1.setModel(OHsystem.deleteJazda((DefaultTableModel) jTable1.getModel(),
                    Integer.parseInt("" + jTable1.getValueAt(riadok, 0))));
            vycistiUpravuJazdy(false);
            refreshVozidla();
            refreshVozidlaKapacitne();
            jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        vycistiUpravuJazdy(true);
        riadok = -1;
        jLabel30.setText("Vytvorenie servisovej jazdy");
        jComboBox2.setModel(OHsystem.GetVozidlaMinKapacityVolne(0, "NOSPZ", (DefaultComboBoxModel) jComboBox2.getModel()));
        jComboBox1.addItem("servisová");
        jComboBox1.addItem("servisová uzavretá");
        jComboBox1.setSelectedItem("servisová");
        jTextField10.setEnabled(false);
        jTextField14.setEnabled(false);
        jTextField12.setEnabled(false);
        jTextField11.setEnabled(false);
        jTextField13.setEnabled(false);
        cmb11aktiv = true;
        jCheckBox1.setVisible(true);
        jCheckBox1.setSelected(true);
        jTextField15.setEditable(true);

    }//GEN-LAST:event_jButton19ActionPerformed

    private void jComboBox1ItemStateChanged(ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (cmb11aktiv) {
            if (evt.getItem().toString().equals("servisová uzavretá")) {
                jTextField11.setEnabled(true);
                jCheckBox1.setVisible(false);
            }
            if (evt.getItem().toString().equals("servisová")) {
                jTextField11.setText("");
                jTextField11.setEnabled(false);
                jCheckBox1.setVisible(true);
            }
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton22ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        riadok = -1;
        jTable1.getSelectionModel().clearSelection();
        vycistiUpravuJazdy(false);
        nastavPanel(jPanel14, false);
        jTable2.setModel(OHsystem.clear((DefaultTableModel) jTable2.getModel()));
        cmb11aktiv = false;
        jTextField11.setText("");
        jButton17.setVisible(false);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton9ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTabbedPane1.setSelectedIndex(2);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton17ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        zobrazujemJednotlivo = true;
        LinkedList<Integer> ids = new LinkedList<>();
        for (int i = 1; i < jTable2.getColumnCount(); i++) {
            ids.add(Integer.parseInt("" + jTable2.getColumnName(i)));
        }
//        System.out.println(ids.toString());
        mapa2.zobrazTrasu(jInternalFrame2, ids);
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton20ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        pripravOptimalizaciuStojisk();
    }//GEN-LAST:event_jButton20ActionPerformed
    public void pripravOptimalizaciuStojisk() {
        OHsystem.startOPstijiska();
        jTable4.setEnabled(true);
        jTable4.setVisible(true);
        jTable5.setVisible(true);
        jTable5.setEnabled(true);
        mapa3.optimalizaciaStojisk(jInternalFrame3);
    }

    private void jTable_JazdyMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable_JazdyMouseClicked
        int row = jTable_Jazdy.rowAtPoint(evt.getPoint());
        int col = jTable_Jazdy.columnAtPoint(evt.getPoint());

        if (col == jTable_Jazdy.getColumnCount() - 1 && evt.getClickCount() == 2) {
            if (jTable_Jazdy.getValueAt(row, 2).toString().equals("uzavretý")) {
                OHsystem.zobrazGrafStojiskJazdy(Integer.parseInt("" + jTable_Jazdy.getValueAt(row, 0)), true);
            } else {
                OHsystem.zobrazGrafStojiskJazdy(Integer.parseInt("" + jTable_Jazdy.getValueAt(row, 0)), false);
            }

        }
    }//GEN-LAST:event_jTable_JazdyMouseClicked

    private void jCheckBox2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (jCheckBox2.isSelected()) {
            robimPolygony = true;
        } else {
            robimPolygony = false;
        }
//        System.out.println("robimPolygony:" + robimPolygony);
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTable5MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        int row = jTable5.rowAtPoint(evt.getPoint());
        int col = jTable5.columnAtPoint(evt.getPoint());
        if (col == 3) {
            if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                int p = (int) jTable5.getValueAt(row, 0);
                OHsystem.odoberSuradnicu(p);
                refreshSuradnice();
                mapa3.optimalizaciaStojisk(jInternalFrame3);
            }
        }

    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable4MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int row = jTable4.rowAtPoint(evt.getPoint());
        int col = jTable4.columnAtPoint(evt.getPoint());
        if (col == 4) {
            if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                OHsystem.odoberOblast((int) jTable4.getValueAt(row, 0));
                refreshOblasti();
                mapa3.optimalizaciaStojisk(jInternalFrame3);
            }
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton23ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        jProgressBar1.setVisible(true);
        refreshProgresBar(1);
        int pocetCentier = Integer.parseInt("" + jTextField16.getText());
        jTextField17.setText(Arrays.toString(OHsystem.optimalizujPMedian(pocetCentier)));
        jProgressBar1.setVisible(false);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Načítajte možné stojiská");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            OHsystem.loadMozneStojiska(fileToLoad);
            refreshSuradnice();
            mapa3.optimalizaciaStojisk(jInternalFrame3);
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Uložte oblasti");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            OHsystem.saveOblastiToFile(fileToSave);
            refreshOblasti();
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Načítajte oblasti");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            OHsystem.loadMozneOblasti(fileToLoad);
            refreshOblasti();
            mapa3.optimalizaciaStojisk(jInternalFrame3);
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Uložte možné stojiská");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            OHsystem.saveMozneUmiestnenia(fileToSave);
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jTable_ZasahMouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable_ZasahMouseClicked
        if (editujemZasah) {
            int row = jTable_Zasah.rowAtPoint(evt.getPoint());
            int col = jTable_Zasah.columnAtPoint(evt.getPoint());
            if ((row == jTable_Zasah.getModel().getRowCount() - 1 && col > 0) && evt.getClickCount() == 2) {
                JOptionPane.showMessageDialog(this, "Vymazavam col :" + col);
//                System.out.println("");
                //TableColumn col_start;
                //TableColumn col_end;

//                System.out.println("nova ");
                int predchodca = -1;
                if (col == 1) {
                    predchodca = Integer.parseInt("" + col_start.getHeaderValue());
                } else {
                    predchodca = Integer.parseInt("" + jTable_Zasah.getColumnName(col - 1));
                }
                int nasledovnik = -1;
                if (col == jTable_Zasah.getColumnCount() - 1) {
                    nasledovnik = Integer.parseInt("" + col_end.getHeaderValue());
                } else {
                    nasledovnik = Integer.parseInt("" + jTable_Zasah.getColumnName(col + 1));
                }
                int mazany = Integer.parseInt("" + jTable_Zasah.getColumnName(col));
                jTable_Zasah.removeColumn(jTable_Zasah.getColumnModel().getColumn(col));
//                System.out.println("predchodca: " + predchodca);
//                System.out.println("mazany: " + mazany);
//                System.out.println("nasledovnik: " + nasledovnik);
//                System.out.println("Uspora: " + OHsystem.vyratajUsporuPriZmazani(predchodca, mazany, nasledovnik));
//                System.out.println("*************************");

                double aktual = 0.0;
                if (!jTextField_dlzkaTrasy1.getText().equals("")) {
                    aktual = Double.parseDouble(jTextField_dlzkaTrasy1.getText());
                }

                if (jLabel19.getText().equals("Úspora [Km]")) {
                    aktual = -1 * aktual;
                }
//                System.out.println("Aktual: " + aktual);
                double u = OHsystem.vyratajUsporuPriZmazani(predchodca, mazany, nasledovnik) / 1000.0;
//                System.out.println(">>>>>Nové u:" + u);
                double pom = (u + aktual);
                if ((pom) <= 0) {
                    jLabel19.setText("Úspora [Km]");
                    jTextField_dlzkaTrasy1.setText(String.format(Locale.ROOT, "%.2f", Math.abs(pom)));
                } else {
                    jLabel19.setText("Predlženie [Km]");
                    jTextField_dlzkaTrasy1.setText("" + String.format(Locale.ROOT, "%.2f", (pom)));
                }

                double q = (pom + Double.parseDouble(jTextField_dlzkaTrasy.getText()));//.substring(0, jTextField_dlzkaTrasy.getText().indexOf("."))));
//                System.out.println("Nova trasa: " + q);
                if (q <= 0.02 && q >= -0.02) {
                    q = 0;
                }
                jTextField_dlzkaTrasy2.setText("" + String.format(Locale.ROOT, "%.2f", q));
//                System.out.println("********");

            }
        }
    }//GEN-LAST:event_jTable_ZasahMouseClicked

    private void jButton28ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        vytvorEditaciuZasahu();
        col_start = null;
        col_end = null;
        jTable_Zasah.getTableHeader().setReorderingAllowed(false);
        jButton15.setText("Uprav trasu");
        cmbEditaciaZvozu.setEditable(true);
        cmbEditaciaZvozu.setEnabled(true);
        jTextField_dlzkaTrasy1.setText("");
        jTextField_dlzkaTrasy2.setText("");
        jTextField_dlzkaTrasy1.setVisible(false);
        jTextField_dlzkaTrasy2.setVisible(false);
        jLabel19.setVisible(false);
        jLabel20.setVisible(false);
        vykreslujTabulkuKontajnerov();
        naformatujTabulkuKontajnerov();
        editujemZasah = false;
        jButton28.setVisible(false);


    }//GEN-LAST:event_jButton28ActionPerformed

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd.mm.yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void naformatujTabulkuKontajnerov() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable_Kontajnery.getColumnCount(); i++) {
            if (i != (jTable_Kontajnery.getColumnCount() - 5) && i != (jTable_Kontajnery.getColumnCount() - 4) && i != (jTable_Kontajnery.getColumnCount() - 3)) {
                jTable_Kontajnery.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        jTable_Kontajnery.getColumnModel().getColumn(0).setMinWidth(70);
        jTable_Kontajnery.getColumnModel().getColumn(0).setMaxWidth(70);
        jTable_Kontajnery.getColumnModel().getColumn(1).setMinWidth(90);
        jTable_Kontajnery.getColumnModel().getColumn(1).setMaxWidth(90);
        jTable_Kontajnery.getColumnModel().getColumn(3).setMinWidth(130);
        jTable_Kontajnery.getColumnModel().getColumn(3).setMaxWidth(130);
        jTable_Kontajnery.getColumnModel().getColumn(4).setMinWidth(120);
        jTable_Kontajnery.getColumnModel().getColumn(4).setMaxWidth(120);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 1).setMinWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 1).setMaxWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 2).setMinWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 2).setMaxWidth(0);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 3).setMinWidth(50);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 3).setMaxWidth(50);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 4).setMinWidth(50);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 4).setMaxWidth(50);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 5).setMinWidth(50);
        jTable_Kontajnery.getColumnModel().getColumn(jTable_Kontajnery.getColumnCount() - 5).setMaxWidth(50);
    }

    public void naformatujTabulkuVozidiel() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < jTable_Vozidla.getColumnCount(); i++) {
            jTable_Vozidla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void updateCreateVozidlo(String spz, boolean update) {
        new JFUpdate_Create_Vozidlo(OHsystem, spz, update).setVisible(true);
        jTable_Vozidla.setModel(OHsystem.getVozidla((DefaultTableModel) jTable_Vozidla.getModel()));
        naformatujTabulkuVozidiel();
    }

    public void refreshKontajnery() {

        jTable_Kontajnery.setModel(OHsystem.getKontajnery("" + jCB_druhOdpadu.getSelectedItem(), Integer.parseInt("" + jCB_percentoZaplnenia.getSelectedItem()), null, null, (DefaultTableModel) jTable_Kontajnery.getModel()));
        naformatujTabulkuKontajnerov();
        //this.repaint();
    }

    public void refreshVozidla() {
        jTable_Vozidla.setModel(OHsystem.getVozidla((DefaultTableModel) jTable_Vozidla.getModel()));
        naformatujTabulkuVozidiel();
        //this.repaint();
    }

    public void zobrazAktualneJazdy() {
        jTable1.setModel(OHsystem.getJazdyAktual((DefaultTableModel) jTable1.getModel()));
    }

    public void refresNespracovaneSpravy() {
        jBNespracovaneSpravy.setText("" + OHsystem.getNespracovaneSpravy().size());
        if (OHsystem.getNespracovaneSpravy().size() > 0) {
            jBNespracovaneSpravy.setBackground(Color.GREEN);
        } else {
            jBNespracovaneSpravy.setBackground(this.getBackground());
        }

    }

    public void vytvorEditaciuZasahu() {
        if (cmbEditaciaZvozu.getSelectedItem() == null) {
            cleanPoVytvoreniJazd();
            JOptionPane.showMessageDialog(this, "Úspešne ste spracovali návrhy trás na jazdy");
            return;
        }
        String s = "" + cmbEditaciaZvozu.getSelectedItem();
        int i = -1;
        boolean jeTrasa = false;
        if (s.startsWith("Trasa: ")) {
            jeTrasa = true;
            i = Integer.parseInt(s.substring(s.indexOf(":") + 2));
        } else if (s.startsWith("Alternatívna: ")) {
            jeTrasa = false;
            i = Integer.parseInt(s.substring(s.indexOf(":") + 2));
        }
//        System.out.println("String: " + s + "\n" + "Index: " + i);
//        System.out.println("");

        jTable_Zasah.setModel(OHsystem.getTableZasah(i, jeTrasa, (DefaultTableModel) jTable_Zasah.getModel()));
        jTable_Zasah.getColumnModel().addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnAdded(TableColumnModelEvent e) {
            }

            @Override
            public void columnMarginChanged(ChangeEvent e) {
            }

            @Override
            public void columnMoved(TableColumnModelEvent e) {
                if (columnValue == -1) {
                    columnValue = e.getFromIndex();
                    //pom = e.getToIndex();                   
                }
                columnNewValue = e.getToIndex();
                if (columnValue > 0 && columnNewValue != columnValue) {
                    dragComplete = true;
                }
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent e) {
            }
        });

        jTable_Zasah.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (columnValue != -1 && (columnValue == 0 || columnNewValue == 0)) {
                    jTable_Zasah.moveColumn(columnNewValue, columnValue); //vracia do povodneho stavu
                    dragComplete = false;
                }
                if (columnNewValue == columnValue) {
                    dragComplete = false;
                }
                if (dragComplete) {
//                    JOptionPane.showMessageDialog(null, "Drag completed from:" + columnValue + " To:" + columnNewValue);
                    int presuvany;
                    int vsuvaneMiesto_nasledovnik;
                    int vsuvaneMiesto_predchodca;
                    int vyberaneMiesto_predchodca;
                    int veberaneMiesto_nasledovnik;
                    if (columnValue > columnNewValue) { //tahanie z prava do lava
                        presuvany = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue));
                        vsuvaneMiesto_nasledovnik = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue + 1));
//                        System.out.println("presunuty:" + presuvany + " VsuvaneMiesto_nasledovnik:" + vsuvaneMiesto_nasledovnik);
                        if (columnNewValue != 1) {
                            vsuvaneMiesto_predchodca = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue - 1));
                        } else {
                            vsuvaneMiesto_predchodca = 0;
                        }
//                        System.out.println("prvyPredchodca: " + vsuvaneMiesto_predchodca);
                        vyberaneMiesto_predchodca = Integer.parseInt(jTable_Zasah.getColumnName(columnValue));
//                        System.out.println("druhyPredChodca: " + vyberaneMiesto_predchodca);
                        if (columnValue == jTable_Zasah.getColumnCount() - 1) {
                            veberaneMiesto_nasledovnik = 0;
                        } else {
                            veberaneMiesto_nasledovnik = Integer.parseInt(jTable_Zasah.getColumnName(columnValue + 1));
                        }
//                        System.out.println("Nasledovnik: " + veberaneMiesto_nasledovnik);
                    } else { //opacne tahanie                        
                        presuvany = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue));
                        if (columnNewValue == jTable_Zasah.getColumnCount() - 1) {
                            vsuvaneMiesto_nasledovnik = 0;
                        } else {
                            vsuvaneMiesto_nasledovnik = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue + 1));
                        }
//                        System.out.println("presuvany:" + presuvany + " VsuvaneMiesto_nasledovnik::" + vsuvaneMiesto_nasledovnik);
                        if (columnNewValue != 1) {
                            vsuvaneMiesto_predchodca = Integer.parseInt(jTable_Zasah.getColumnName(columnNewValue - 1));
                        } else {
                            vsuvaneMiesto_predchodca = 0;
                        }
//                        System.out.println("vsuvaneMiesto_predchodca: " + vsuvaneMiesto_predchodca);
                        if (columnValue != 1) {
                            vyberaneMiesto_predchodca = Integer.parseInt(jTable_Zasah.getColumnName(columnValue - 1));
                        } else {
                            vyberaneMiesto_predchodca = 0;
                        }
//                        System.out.println("vyberaneMiesto_predchodca: " + vyberaneMiesto_predchodca);
                        veberaneMiesto_nasledovnik = Integer.parseInt(jTable_Zasah.getColumnName(columnValue));
//                        System.out.println("veberaneMiesto_nasledovnik: " + veberaneMiesto_nasledovnik);

                    }
                    double aktual = 0.0;
                    if (!jTextField_dlzkaTrasy1.getText().equals("")) {
                        aktual = Double.parseDouble(jTextField_dlzkaTrasy1.getText());
                    }

                    if (jLabel19.getText().equals("Úspora [Km]")) {
                        aktual = -1 * aktual;
                    }
//                    System.out.println("Aktual: " + aktual);

                    double u = OHsystem.vyratajUspory(vsuvaneMiesto_predchodca, vyberaneMiesto_predchodca,
                            veberaneMiesto_nasledovnik, presuvany, vsuvaneMiesto_nasledovnik) / 1000.0;
//                    System.out.println(">>>>>Nové u:" + u);

                    //pomCounter += u;
                    double pom = (u + aktual);
                    //System.out.println("Hodnota countru:" + pomCounter);

                    if ((pom) <= 0) {
                        jLabel19.setText("Úspora [Km]");
                        jTextField_dlzkaTrasy1.setText(String.format(Locale.ROOT, "%.2f", Math.abs(pom)));
                    } else {
                        jLabel19.setText("Predlženie [Km]");
                        jTextField_dlzkaTrasy1.setText("" + String.format(Locale.ROOT, "%.2f", (pom)));
                    }

                    double q = (pom + Double.parseDouble(jTextField_dlzkaTrasy.getText()));//.substring(0, jTextField_dlzkaTrasy.getText().indexOf("."))));

//                    System.out.println("Nova trasa: " + q);
                    if (q <= 0.02 && q >= -0.02) {
                        q = 0;
                    }
                    jTextField_dlzkaTrasy2.setText("" + String.format(Locale.ROOT, "%.2f", q));
//                    System.out.println("********");

                }
                dragComplete = false;
                columnValue = -1;
                columnNewValue = -1;
            }
        }
        );

        refreshEditaciaZasahu();

        jTextField_kapacita.setText(
                "" + OHsystem.getVysloOPT().getTrasy().get(i).getAktualnaKapacitaJazdy());
        if (jeTrasa) {
            jTextField_dlzkaTrasy.setText("" + String.format(Locale.ROOT, "%.2f", (OHsystem.getVysloOPT().getTrasy().get(i).getNaklady() / 1000.0)));

        } else {
            jTextField_dlzkaTrasy.setText("" + String.format(Locale.ROOT, "%.2f", (OHsystem.getVysloOPT().getTrasy().get(i).getAlternativneNaklady() / 1000.0)));
        }

        jTextField_DruhOdpadu.setText("" + OHsystem.getVysloOPT().getTrasy().get(i).getDruhOdpadu());
    }

    public void refreshEditaciaZasahu() {
        for (int column = 0; column < jTable_Zasah.getColumnCount(); column++) {
            TableColumn tableColumn = jTable_Zasah.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < jTable_Zasah.getRowCount(); row++) {
                TableCellRenderer cellRenderer = jTable_Zasah.getCellRenderer(row, column);
                Component c = jTable_Zasah.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + jTable_Zasah.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);

            if (jTable_Zasah.getColumnCount() > 10) {
                jTable_Zasah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            } else {
                jTable_Zasah.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            }

        }
    }

    public void refreshEditaciaStojiskaJazdy() {
        for (int column = 0; column < jTable2.getColumnCount(); column++) {
            TableColumn tableColumn = jTable2.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < jTable2.getRowCount(); row++) {
                TableCellRenderer cellRenderer = jTable2.getCellRenderer(row, column);
                Component c = jTable2.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + jTable2.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
        if (jTable2.getColumnCount() > 10) {
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        }

    }

    public void vycistiUpravuJazdy(boolean blokuj) {
        jButton18.setEnabled(false);
        riadok = -1;
        jLabel30.setText("Úprava jazdy");
        jTextField15.setText("");
        jTextField10.setText("");
        jTextField14.setText("");
        jComboBox2.removeAllItems();
        jComboBox1.removeAllItems();
        nastavPanel(jPanel13, blokuj);
        riadok = -1;
        jTextField11.setText("");
        jTextField13.setText("");
        jButton17.setVisible(false);

    }

    public void nastavPanel(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                nastavPanel((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox<String> cmbEditaciaZvozu;
    private JButton jBNespracovaneSpravy;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton13;
    private JButton jButton14;
    private JButton jButton15;
    private JButton jButton16;
    private JButton jButton17;
    private JButton jButton18;
    private JButton jButton19;
    private JButton jButton2;
    private JButton jButton20;
    private JButton jButton21;
    private JButton jButton22;
    private JButton jButton23;
    private JButton jButton24;
    private JButton jButton25;
    private JButton jButton26;
    private JButton jButton27;
    private JButton jButton28;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JComboBox<String> jCB_SPZZ;
    private JComboBox<String> jCB_druhOdpadu;
    private JComboBox<String> jCB_druhOdpadu3;
    private JComboBox<String> jCB_druhOdpadu4;
    private JComboBox<String> jCB_idecka;
    private JComboBox<String> jCB_percentoZaplnenia;
    private JComboBox<String> jCB_percentoZaplnenia4;
    private JComboBox<String> jCB_stav;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBoxAktualizacia;
    private JCheckBox jCheckBoxAktualizacia2;
    private JCheckBox jCheckBox_Vyprazdnenie;
    private JCheckBox jCheckBox_Vyprazdnenie2;
    private JCheckBox jCheckBox_jazda_do;
    private JCheckBox jCheckBox_jazda_od;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JDateChooser jDateChooser2_Aktualizácia2;
    private JDateChooser jDateChooser_Aktualizácia;
    private JDateChooser jDateChooser_Vyprazdnenie;
    private JDateChooser jDateChooser_Vyprazdnenie2;
    private JDateChooser jDateChooser_jazda_do;
    private JDateChooser jDateChooser_jazda_od;
    private JFormattedTextField jFormattedTextField1;
    private JInternalFrame jInternalFrame1;
    private JInternalFrame jInternalFrame2;
    private JInternalFrame jInternalFrame3;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel29;
    private JLabel jLabel3;
    private JLabel jLabel30;
    private JLabel jLabel31;
    private JLabel jLabel32;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel35;
    private JLabel jLabel36;
    private JLabel jLabel37;
    private JLabel jLabel38;
    private JLabel jLabel39;
    private JLabel jLabel4;
    private JLabel jLabel40;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPVyhladavanie;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JProgressBar jProgressBar1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane10;
    private JScrollPane jScrollPane11;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JScrollPane jScrollPane14;
    private JScrollPane jScrollPane15;
    private JScrollPane jScrollPane16;
    private JScrollPane jScrollPane17;
    private JScrollPane jScrollPane18;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JScrollPane jScrollPane9;
    private JTabbedPane jTabbedPane1;
    private JTabbedPane jTabbedPane2;
    private JTabbedPane jTabbedPane3;
    private JTabbedPane jTabbedPane4;
    private JTabbedPane jTabbedPane5;
    private JTable jTable1;
    private JTable jTable2;
    private JTable jTable3;
    private JTable jTable4;
    private JTable jTable5;
    private JTable jTableMaticaUzly;
    private JTable jTableVybraneVozidla;
    private JTable jTable_Jazdy;
    private JTable jTable_Kontajnery;
    private JTable jTable_MaticaVzdialenosti;
    private JTable jTable_Vozidla;
    private JTable jTable_VozidlaKapacitne;
    private JTable jTable_Zasah;
    private JTextField jTextField1;
    private JTextField jTextField10;
    private JTextField jTextField11;
    private JTextField jTextField12;
    private JTextField jTextField13;
    private JTextField jTextField14;
    private JTextField jTextField15;
    private JTextField jTextField16;
    private JTextField jTextField17;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextField9;
    private JTextField jTextField_DruhOdpadu;
    private JTextField jTextField_dlzkaTrasy;
    private JTextField jTextField_dlzkaTrasy1;
    private JTextField jTextField_dlzkaTrasy2;
    private JTextField jTextField_kapacita;
    private JTextPane jTextPane1;
    private JTextPane jTextPane2;
    private JTextPane jTextPane3;
    private JTextPane jTextPane4;
    // End of variables declaration//GEN-END:variables

    public void refreshVybraneVozidla() {
        jTableVybraneVozidla.requestFocus();
        jTableVybraneVozidla.editCellAt(0, 0);
        int[] priority = new int[jTableVybraneVozidla.getColumnCount() - 1];
        for (int i = 1; i < jTableVybraneVozidla.getColumnCount(); i++) {
            priority[i - 1] = Integer.parseInt("" + jTableVybraneVozidla.getValueAt(1, i));
            //System.out.println("Priority: " +priority[i - 1]);
        }
        jTableVybraneVozidla.setModel(OHsystem.getVybraneVozidla((DefaultTableModel) jTableVybraneVozidla.getModel(), priority));
        jTableVybraneVozidla.setRowHeight(2, 33);
        jTableVybraneVozidla.setRowHeight(1, 33);
        jTableVybraneVozidla.setRowHeight(0, 25);
    }

    public void refreshVozidlaKapacitne() {
        jTable_VozidlaKapacitne.setModel(OHsystem.getVozidlaKapacitne((DefaultTableModel) /*jTableVybraneVozidla*/ jTable_VozidlaKapacitne.getModel()));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable_VozidlaKapacitne.getColumnCount() - 1; i++) {
            jTable_VozidlaKapacitne.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        jTable_VozidlaKapacitne.getColumnModel().getColumn(jTable_VozidlaKapacitne.getColumnCount() - 1).setMinWidth(32);
        jTable_VozidlaKapacitne.getColumnModel().getColumn(jTable_VozidlaKapacitne.getColumnCount() - 1).setMaxWidth(32);
        jTable_VozidlaKapacitne.setRowHeight(32);

    }

    public void cleanPoVytvoreniJazd() {
        jTextField_dlzkaTrasy.setText("");
        jTextField_kapacita.setText("");
        jTextField_DruhOdpadu.setText("");
        jTable_Zasah.setModel(OHsystem.clear((DefaultTableModel) jTable_Zasah.getModel()));
        jTabbedPane4.setSelectedIndex(0);
        jTabbedPane4.setEnabledAt(1, false);
        jTextPane3.setText("Môžete vyberať stojiská na nové jazdy");
        jCB_druhOdpadu.setSelectedItem("Všetky");
        jCB_percentoZaplnenia.setSelectedItem("0");
        nastavDatumy();
        vykreslujTabulkuKontajnerov();
        naformatujTabulkuKontajnerov();
        loadPodTabulkouKontajnerov();
        jTextField6.setText("");
        jTextField7.setText("");
        jTabbedPane3.setSelectedIndex(1);
    }

    public void nastavDatumy() {
        jDateChooser_Aktualizácia.setDate(parseDate("1.1.2021"));
        jDateChooser_Aktualizácia.setEnabled(false);
        jCheckBoxAktualizacia.setSelected(false);
        jDateChooser2_Aktualizácia2.setDate(parseDate("1.1.2021"));
        jDateChooser2_Aktualizácia2.setEnabled(false);
        jCheckBoxAktualizacia2.setSelected(false);
        jDateChooser_Vyprazdnenie.setDate(parseDate("1.1.2021"));
        jDateChooser_Vyprazdnenie.setEnabled(false);
        jCheckBox_Vyprazdnenie.setSelected(false);

        jDateChooser_Vyprazdnenie2.setDate(parseDate("1.1.2021"));
        jDateChooser_Vyprazdnenie2.setEnabled(false);
        jCheckBox_Vyprazdnenie2.setSelected(false);

        jDateChooser_jazda_od.setDate(parseDate("1.1.2021"));
        jDateChooser_jazda_od.setEnabled(false);
        jCheckBox_jazda_od.setSelected(false);

        jDateChooser_jazda_do.setDate(parseDate("1.1.2021"));
        jDateChooser_jazda_do.setEnabled(false);
        jCheckBox_jazda_do.setSelected(false);
    }

    public void refreshSuradnice() {
        jTable5.setModel(OHsystem.getSuradnice((DefaultTableModel) jTable5.getModel()));

    }

    public void refreshOblasti() {
        jTable4.setModel(OHsystem.getOblasti((DefaultTableModel) jTable4.getModel()));
    }

    public void refreshProgresBar(int value) {
        jProgressBar1.setValue(value);
        jProgressBar1.update(jProgressBar1.getGraphics());
        //jProgressBar1.validate();
        //jProgressBar1.repaint();
    }
}
