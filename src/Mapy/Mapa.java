/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;

import Graf.Graf;
import Graf.Hrana;
import Graf.Suradnice;
import Graf.Uzol;
import CW.Trasa;
import CW.Zakaznik;
import GUI.JFMainFrame;
import dp.Oblast;
import dp.OdpadoveHospodarstvo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author rendo
 */
public class Mapa {

    final List<TileFactory> factories = new ArrayList<>();
    final JComboBox comboFactories;
    OdpadoveHospodarstvo oh;
    JLabel label;
    Graf graf;
    JFMainFrame f;
    LinkedList<GeoPosition> surky;

    public Mapa(JInternalFrame frame, OdpadoveHospodarstvo oh) {
        this.oh = oh;
        factories.add(new DefaultTileFactory(new OSMTileFactoryInfo()));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID)));
        label = new JLabel("Typ mapy ");
        String[] tfLabels = new String[factories.size()];
        for (int i = 0; i < factories.size(); i++) {
            tfLabels[i] = factories.get(i).getInfo().getName() + i;
        }
        comboFactories = new JComboBox(tfLabels);
    }

    public Mapa(JInternalFrame frame, OdpadoveHospodarstvo oh, Graf graf) {
        this.oh = oh;
        this.graf = graf;
        factories.add(new DefaultTileFactory(new OSMTileFactoryInfo()));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID)));
        label = new JLabel("Typ mapy ");
        String[] tfLabels = new String[factories.size()];
        for (int i = 0; i < factories.size(); i++) {
            tfLabels[i] = factories.get(i).getInfo().getName() + i;
        }
        comboFactories = new JComboBox(tfLabels);
    }

    public Mapa(JInternalFrame frame, OdpadoveHospodarstvo oh, JFMainFrame f) {
        this.f = f;
        this.oh = oh;

        factories.add(new DefaultTileFactory(new OSMTileFactoryInfo()));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP)));
        factories.add(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID)));
        label = new JLabel("Typ mapy ");
        String[] tfLabels = new String[factories.size()];
        for (int i = 0; i < factories.size(); i++) {
            tfLabels[i] = factories.get(i).getInfo().getName() + i;
        }
        comboFactories = new JComboBox(tfLabels);
        surky = new LinkedList<GeoPosition>();
    }

    public void zobrazRozmiestnenieStojisk(JInternalFrame frame) {
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(factories.get(0));
        comboFactories.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                TileFactory factory = factories.get(comboFactories.getSelectedIndex());
                TileFactoryInfo info = factory.getInfo();
                mapViewer.setTileFactory(factory);

            }
        });
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(comboFactories);
        mapViewer.add(panel, BorderLayout.NORTH);
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
//        SelectionAdapter sa = new SelectionAdapter(mapViewer);
//        SelectionPainter sp = new SelectionPainter(sa);
//        mapViewer.addMouseListener(sa);
//        mapViewer.addMouseMotionListener(sa);
//        painters.add(sp);

        Set<StojiskoWaypoint> swingWaypoints = new HashSet<StojiskoWaypoint>(oh.getPracovneStojiska());

        List<GeoPosition> track = new ArrayList<>();
        for (StojiskoWaypoint swingWaypoint : swingWaypoints) {
            track.add(swingWaypoint.getPosition());
        }

        if (track.size() > 1) {
            mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        } else {
            //mapViewer.setAddressLocation(oh.getPracovneStojiska().get(0).getPosition()); 
            mapViewer.setAddressLocation(new GeoPosition(49.08899, 18.64007));
        }
        mapViewer.setZoom(3);

//        RoutePainter routePainter = new RoutePainter(track);
//        painters.add(routePainter);
        WaypointPainter<StojiskoWaypoint> swingWaypointPainter = new StojiskoOverlayPainter();
        swingWaypointPainter.setWaypoints(swingWaypoints);
        painters.add(swingWaypointPainter);

        WaypointPainter<StojiskoWaypoint> mywaypointPainter = new WaypointPainter<StojiskoWaypoint>();
        mywaypointPainter.setRenderer(new StojiskoRender());
        mywaypointPainter.setWaypoints(swingWaypoints);
        painters.add(mywaypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);

        for (StojiskoWaypoint w : swingWaypoints) {
            mapViewer.add(w.getButton());
        }

        frame.setContentPane(mapViewer);
        frame.setTitle("Mapa");
    }

    public void zobrazTrasu(JInternalFrame frame, LinkedList<Integer> idecka) {
        if (idecka.size() > 0) {
            List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
            JXMapViewer mapViewer = new JXMapViewer();
            mapViewer.setTileFactory(factories.get(0));
            comboFactories.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    TileFactory factory = factories.get(comboFactories.getSelectedIndex());
                    TileFactoryInfo info = factory.getInfo();
                    mapViewer.setTileFactory(factory);

                }
            });
            JPanel panel = new JPanel();
            panel.add(label);
            panel.add(comboFactories);
            mapViewer.add(panel, BorderLayout.NORTH);
            MouseInputListener mia = new PanMouseInputListener(mapViewer);
            mapViewer.addMouseListener(mia);
            mapViewer.addMouseMotionListener(mia);
            mapViewer.addMouseListener(new CenterMapListener(mapViewer));
            mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
            mapViewer.addKeyListener(new PanKeyListener(mapViewer));
//            SelectionAdapter sa = new SelectionAdapter(mapViewer);
//            SelectionPainter sp = new SelectionPainter(sa);
//            mapViewer.addMouseListener(sa);
//            mapViewer.addMouseMotionListener(sa);
//            painters.add(sp);
            LinkedList<Integer> stojiskaIdecka = idecka;
            LinkedList<Integer> ideckaKomplet = graf.vratKompletneCestu((LinkedList<Integer>) stojiskaIdecka.clone());
            LinkedList<Hrana> hrany = graf.vratHrany((LinkedList<Integer>) ideckaKomplet.clone());
            List<GeoPosition> trasa = new ArrayList<>();
            for (Hrana hrana : hrany) {
                for (Suradnice suradnice1 : hrana.getSuradnice()) {
                    trasa.add(new GeoPosition(suradnice1.getX(), suradnice1.getY()));
                }
            }
            LinkedList<Uzol> uzolHlavne = graf.getUzly(stojiskaIdecka);
            LinkedList<Uzol> uzlyVsetky = graf.getUzly(ideckaKomplet);
            Set<UzolWaypoint> waypointsIdecka = new HashSet<>();
            Set<Integer> ids = new HashSet<>();
            for (int i = 0; i < uzolHlavne.size() - 1; i++) {
                waypointsIdecka.add(new UzolWaypoint(uzolHlavne.get(i).getId(), i,
                        Color.yellow,
                        new GeoPosition(uzolHlavne.get(i).getX_sur(),
                                uzolHlavne.get(i).getY_sur())));
                ids.add(uzolHlavne.get(i).getId());
            }
            for (int i = 0; i < uzlyVsetky.size(); i++) {
                if (!ids.contains(uzlyVsetky.get(i).getId())) { //nezadene
                    waypointsIdecka.add(new UzolWaypoint(
                            Color.GREEN,
                            uzlyVsetky.get(i).getId(),
                            i,
                            new GeoPosition(uzlyVsetky.get(i).getX_sur(), uzlyVsetky.get(i).getY_sur())));
                    ids.add(uzlyVsetky.get(i).getId());
                } else {//zaradene
                    for (UzolWaypoint point : waypointsIdecka) {
                        if (point.getId_uzla() == uzlyVsetky.get(i).getId()) {
                            point.pridajNovePoradie(i);
                            break;
                        }
                    }
                }
            }

            if (trasa.size() > 1) {
                mapViewer.zoomToBestFit(new HashSet<>(trasa), 0.7);
            } else {
                //mapViewer.setAddressLocation(oh.getPracovneStojiska().get(0).getPosition()); 
                mapViewer.setAddressLocation(new GeoPosition(49.08899, 18.64007));
            }
            mapViewer.setZoom(3);
            RoutePainter routePainter = new RoutePainter(trasa);
            painters.add(routePainter);
            WaypointPainter<UzolWaypoint> swingWaypointPainter = new UzolOverlayPainter();
            swingWaypointPainter.setWaypoints(waypointsIdecka);
            painters.add(swingWaypointPainter);
            WaypointPainter<UzolWaypoint> waypointPainter = new WaypointPainter<>();
            waypointPainter.setWaypoints(waypointsIdecka);
            waypointPainter.setRenderer(new UzolRender());
            painters.add(waypointPainter);
            CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
            mapViewer.setOverlayPainter(painter);
            for (UzolWaypoint w : waypointsIdecka) {
                mapViewer.add(w.getButton());
            }
            frame.setContentPane(mapViewer);
            frame.setTitle("Mapa");
        }
    }

    public void zobrazTrasy(JInternalFrame frame, ArrayList<Trasa> trasy) {
        ArrayList<DvojicaTras> mapy = spracujTrasy(trasy);
        JLabel label = new JLabel("Vyber trasu: ");
        String[] tfLabels = new String[mapy.size()];
        for (int i = 0; i < mapy.size(); i++) {
            tfLabels[i] = "Trasa[" + i + "]";
        }
        JComboBox cmb = new JComboBox(tfLabels);
        cmb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                int i = cmb.getSelectedIndex();
                DvojicaTras mapa = mapy.get(i);
                List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
                JXMapViewer mapViewer = new JXMapViewer();
                mapViewer.setTileFactory(factories.get(0));
                comboFactories.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        TileFactory factory = factories.get(comboFactories.getSelectedIndex());
                        TileFactoryInfo info = factory.getInfo();
                        mapViewer.setTileFactory(factory);

                    }
                });
                JPanel panel = new JPanel();
                panel.add(getLabel());
                panel.add(comboFactories);
                panel.add(label);
                panel.add(cmb);
                mapViewer.add(panel, BorderLayout.NORTH);
                MouseInputListener mia = new PanMouseInputListener(mapViewer);
                mapViewer.addMouseListener(mia);
                mapViewer.addMouseMotionListener(mia);
                mapViewer.addMouseListener(new CenterMapListener(mapViewer));
                mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
                mapViewer.addKeyListener(new PanKeyListener(mapViewer));
//                SelectionAdapter sa = new SelectionAdapter(mapViewer);
//                SelectionPainter sp = new SelectionPainter(sa);
//                mapViewer.addMouseListener(sa);
//                mapViewer.addMouseMotionListener(sa);
//                painters.add(sp);

                mapViewer.setAddressLocation(new GeoPosition(49.08899, 18.64007));
                mapViewer.setZoom(4);

//                if (mapa.getTrasa().size() > 1) {
//                    mapViewer.zoomToBestFit(new HashSet<>(mapa.getTrasa()), 0.7);
//                } else {
//                    //mapViewer.setAddressLocation(oh.getPracovneStojiska().get(0).getPosition()); 
//                    mapViewer.setAddressLocation(new GeoPosition(49.08899, 18.64007));
//                }
//                mapViewer.setZoom(3);
                RoutePainter routePainter = new RoutePainter(mapa.getTrasa());
                painters.add(routePainter);
                WaypointPainter<UzolWaypoint> swingWaypointPainter = new UzolOverlayPainter();
                swingWaypointPainter.setWaypoints(mapa.getWaypointsIdecka());
                painters.add(swingWaypointPainter);
                WaypointPainter<UzolWaypoint> waypointPainter = new WaypointPainter<>();
                waypointPainter.setWaypoints(mapa.getWaypointsIdecka());
                waypointPainter.setRenderer(new UzolRender());
                painters.add(waypointPainter);
                CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
                mapViewer.setOverlayPainter(painter);
                for (UzolWaypoint w : mapa.getWaypointsIdecka()) {
                    mapViewer.add(w.getButton());
                }
                frame.setContentPane(mapViewer);
                frame.setTitle("Mapa");

            }
        });

        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(factories.get(0));
        comboFactories.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                TileFactory factory = factories.get(comboFactories.getSelectedIndex());
                TileFactoryInfo info = factory.getInfo();
                mapViewer.setTileFactory(factory);

            }
        });
        JPanel panel = new JPanel();
        panel.add(this.label);
        panel.add(comboFactories);
        panel.add(label);
        panel.add(cmb);
        mapViewer.add(panel, BorderLayout.NORTH);
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
//        SelectionAdapter sa = new SelectionAdapter(mapViewer);
//        SelectionPainter sp = new SelectionPainter(sa);
//        mapViewer.addMouseListener(sa);
//        mapViewer.addMouseMotionListener(sa);
//        painters.add(sp);

        mapViewer.zoomToBestFit(new HashSet<>(mapy.get(0).getTrasa()), 0.7);
        mapViewer.setZoom(7);
        RoutePainter routePainter = new RoutePainter(mapy.get(0).getTrasa());
        painters.add(routePainter);
        WaypointPainter<UzolWaypoint> swingWaypointPainter = new UzolOverlayPainter();
        swingWaypointPainter.setWaypoints(mapy.get(0).getWaypointsIdecka());
        painters.add(swingWaypointPainter);
        WaypointPainter<UzolWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(mapy.get(0).getWaypointsIdecka());
        waypointPainter.setRenderer(new UzolRender());
        painters.add(waypointPainter);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
        for (UzolWaypoint w : mapy.get(0).getWaypointsIdecka()) {
            mapViewer.add(w.getButton());
        }
        frame.setContentPane(mapViewer);
        frame.setTitle("Mapa");

    }

    public JLabel getLabel() {
        return label;
    }

    public DvojicaTras zobrazTrasu(Trasa t) {
        LinkedList<Integer> stojiskaIdecka;
        if (t.getAlternativnaTrasa() == null) {
            stojiskaIdecka = getStojiskaIdecka(t);
        } else {
            stojiskaIdecka = new LinkedList<>(t.getAlternativnaTrasa());
        }
        LinkedList<Integer> ideckaKomplet = graf.vratKompletneCestu((LinkedList<Integer>) stojiskaIdecka.clone());
        LinkedList<Hrana> hrany = graf.vratHrany((LinkedList<Integer>) ideckaKomplet.clone());
        List<GeoPosition> trasa = new ArrayList<>();
        for (Hrana hrana : hrany) {
            for (Suradnice suradnice1 : hrana.getSuradnice()) {
                trasa.add(new GeoPosition(suradnice1.getX(), suradnice1.getY()));
            }
        }
        LinkedList<Uzol> uzolHlavne = graf.getUzly(stojiskaIdecka);
        LinkedList<Uzol> uzlyVsetky = graf.getUzly(ideckaKomplet);
        Set<UzolWaypoint> waypointsIdecka = new HashSet<>();
        Set<Integer> ids = new HashSet<>();
        for (int i = 0; i < uzolHlavne.size() - 1; i++) {
            waypointsIdecka.add(new UzolWaypoint(uzolHlavne.get(i).getId(), i,
                    Color.yellow,
                    new GeoPosition(uzolHlavne.get(i).getX_sur(),
                            uzolHlavne.get(i).getY_sur())));
            ids.add(uzolHlavne.get(i).getId());
        }
        for (int i = 0; i < uzlyVsetky.size(); i++) {
            if (!ids.contains(uzlyVsetky.get(i).getId())) { //nezadene
                waypointsIdecka.add(new UzolWaypoint(
                        Color.GREEN,
                        uzlyVsetky.get(i).getId(),
                        i,
                        new GeoPosition(uzlyVsetky.get(i).getX_sur(), uzlyVsetky.get(i).getY_sur())));
                ids.add(uzlyVsetky.get(i).getId());
            } else {//zaradene
                for (UzolWaypoint point : waypointsIdecka) {
                    if (point.getId_uzla() == uzlyVsetky.get(i).getId()) {
                        point.pridajNovePoradie(i);
                        break;
                    }
                }
            }
        }
        return new DvojicaTras(trasa, waypointsIdecka);
    }

    public ArrayList<DvojicaTras> spracujTrasy(ArrayList<Trasa> trasy) {
        ArrayList<DvojicaTras> mapy = new ArrayList<>();
        for (Trasa trasa : trasy) {
            mapy.add(zobrazTrasu(trasa));
        }
        return mapy;
    }

    public LinkedList<Integer> getStojiskaIdecka(Trasa t) {
        LinkedList<Integer> stojiska = new LinkedList<>();
        for (Zakaznik zakaznik : t.getZakazniciTrasy()) {
            stojiska.add(zakaznik.getId_zakaznika());
        }
        return stojiska;
    }

    public void optimalizaciaStojisk(JInternalFrame frame) {
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(factories.get(0));
        comboFactories.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                TileFactory factory = factories.get(comboFactories.getSelectedIndex());
                mapViewer.setTileFactory(factory);
            }
        });
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(comboFactories);
        mapViewer.add(panel, BorderLayout.NORTH);
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
        mapViewer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                WaypointPainter<MapPoint> o = new WaypointPainter<>();
                if (f.robimPolygony) {
                    if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON3) {    //ukoncenie polygonu
                        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
                        int pom = Integer.parseInt(JOptionPane.showInputDialog("Zadajte požiadavku oblasti [celé kladné číslo]"));
                        surky.add(surky.getFirst());
                        f.OHsystem.pridajOblast(surky, pom);
                        f.refreshOblasti();
                        surky = new LinkedList<>();
                        LinkedList<LinkedList<GeoPosition>> oblasti = vrat();
                        for (LinkedList<GeoPosition> linkedList : oblasti) {
                            painters.add(new RoutePainter(linkedList, Color.GREEN));
                        }
                        WaypointPainter<MapPoint> stredy = new WaypointPainter<>();
                        stredy.setRenderer(new MapPointRender());
                        stredy.setWaypoints(new HashSet<MapPoint>(vratStredy()));
                        painters.add(stredy);
                        painters.add(o);
                        Set<MapPoint> points = ukaz(f.OHsystem.getSuradnice(), Color.red);
                        WaypointPainter<MapPoint> r = new WaypointPainter<>();
                        r.setRenderer(new MapPointRender());
                        r.setWaypoints(points);
                        painters.add(r);
                        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                        mapViewer.setOverlayPainter(painter);

                    } else if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) { // robenie polygonu
                        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
                        java.awt.Point p = e.getPoint();
                        GeoPosition geo = mapViewer.convertPointToGeoPosition(p);
//                        JOptionPane.showMessageDialog(null, " Stojisko X:" + geo.getLatitude() + ",Y:" + geo.getLongitude());
                        LinkedList<LinkedList<GeoPosition>> oblasti = vrat();
                        for (LinkedList<GeoPosition> linkedList : oblasti) {
                            painters.add(new RoutePainter(linkedList, Color.GREEN));
                        }
                        WaypointPainter<MapPoint> stredy = new WaypointPainter<>();
                        stredy.setRenderer(new MapPointRender());
                        stredy.setWaypoints(new HashSet<MapPoint>(vratStredy()));
                        painters.add(stredy);
                        surky.add(new GeoPosition(geo.getLatitude(), geo.getLongitude()));
                        System.out.println(Arrays.toString(surky.toArray()));
                        Set<MapPoint> oblast = ukaz(surky, Color.YELLOW);
                        RoutePainter rp = new RoutePainter(surky, Color.YELLOW);
                        painters.add(rp);
                        o.setRenderer(new MapPointRender());
                        o.setWaypoints(oblast);
                        painters.add(o);
                        Set<MapPoint> points = ukaz(f.OHsystem.getSuradnice(), Color.red);
                        WaypointPainter<MapPoint> r = new WaypointPainter<>();
                        r.setRenderer(new MapPointRender());
                        r.setWaypoints(points);
                        painters.add(r);
                        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                        mapViewer.setOverlayPainter(painter);

                    }
//                    JOptionPane.showMessageDialog(null, " Polygon X:" + geo.getLatitude() + ",Y:" + geo.getLongitude());
                } else {
                    if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
                        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
                        java.awt.Point p = e.getPoint();
                        GeoPosition geo = mapViewer.convertPointToGeoPosition(p);
//                        JOptionPane.showMessageDialog(null, " Stojisko X:" + geo.getLatitude() + ",Y:" + geo.getLongitude());
                        LinkedList<LinkedList<GeoPosition>> oblasti = vrat();
                        for (LinkedList<GeoPosition> linkedList : oblasti) {
                            painters.add(new RoutePainter(linkedList, Color.GREEN));
                        }

                        WaypointPainter<MapPoint> stredy = new WaypointPainter<>();
                        stredy.setRenderer(new MapPointRender());
                        stredy.setWaypoints(new HashSet<MapPoint>(vratStredy()));
                        painters.add(stredy);

                        f.OHsystem.pridajSuradnicu(geo.getLatitude(), geo.getLongitude());
                        f.refreshSuradnice();
                        Set<MapPoint> points = ukaz(f.OHsystem.getSuradnice(), Color.red);

                        WaypointPainter<MapPoint> r = new WaypointPainter<>();
                        r.setRenderer(new MapPointRender());
                        r.setWaypoints(points);
                        painters.add(r);
                        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                        mapViewer.setOverlayPainter(painter);
                    }
                }
            }
        }
        );

        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        LinkedList<LinkedList<GeoPosition>> oblasti = vrat();
        for (LinkedList<GeoPosition> linkedList : oblasti) {
            painters.add(new RoutePainter(linkedList, Color.GREEN));
        }
        WaypointPainter<MapPoint> stredy = new WaypointPainter<>();
        stredy.setRenderer(new MapPointRender());
        stredy.setWaypoints(new HashSet<MapPoint>(vratStredy()));
        painters.add(stredy);
        //painters.add(o);
        Set<MapPoint> points = ukaz(f.OHsystem.getSuradnice(), Color.red);
        WaypointPainter<MapPoint> r = new WaypointPainter<>();
        r.setRenderer(new MapPointRender());
        r.setWaypoints(points);
        painters.add(r);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.setAddressLocation(new GeoPosition(49.08899, 18.64007));
        mapViewer.setZoom(3);
        frame.setContentPane(mapViewer);
        frame.setTitle("Mapa");
    }

    public static Set<MapPoint> ukaz(LinkedList<GeoPosition> sur, Color color) {
        Set<MapPoint> points = new HashSet<>();
        int i = 0;
        for (GeoPosition s : sur) {
            points.add(new MapPoint("" + i, color, new GeoPosition(s.getLatitude(), s.getLongitude())));
            i++;
        }
        return points;
//        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
//        WaypointPainter<MapPoint> p = new WaypointPainter<>();
//        p.setRenderer(new MapPointRender());
//        painters.add(p);
//          CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
//        mapViewer.setOverlayPainter(painter);
    }

    public LinkedList<LinkedList<GeoPosition>> vrat() {
        LinkedList<LinkedList<GeoPosition>> oblasti = new LinkedList<>();
        for (Oblast oblast : f.OHsystem.getOblasti()) {
            oblasti.add(oblast.getBody());
        }
        return oblasti;
    }

    public LinkedList<MapPoint> vratStredy() {
        LinkedList<MapPoint> stredy = new LinkedList<>();

        for (Oblast oblast : f.OHsystem.getOblasti()) {
            stredy.add(new MapPoint("" + oblast.getId(), Color.GREEN, oblast.getSuradnica()));
        }
        return stredy;
    }

    public void zobraz() {

    }

}
