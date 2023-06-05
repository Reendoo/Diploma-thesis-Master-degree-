/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author rendo
 */
public class DvojicaTras {

    private List<GeoPosition> trasa = new ArrayList<>();
    private Set<UzolWaypoint> waypointsIdecka = new HashSet<>();
    private List<GeoPosition> alt_trasa = new ArrayList<>();
    private Set<UzolWaypoint> alt_waypointsIdecka = new HashSet<>();

    public DvojicaTras(List<GeoPosition> trasa, Set<UzolWaypoint> waypointsIdecka) {
        this.trasa = trasa;
        this.waypointsIdecka = waypointsIdecka;
    }

    public void setAlternativ(List<GeoPosition> alt_trasa, Set<UzolWaypoint> alt_waypointsIdecka) {
        this.alt_trasa = alt_trasa;
        this.alt_waypointsIdecka = alt_waypointsIdecka;
    }

    public List<GeoPosition> getTrasa() {
        return trasa;
    }

    public Set<UzolWaypoint> getWaypointsIdecka() {
        return waypointsIdecka;
    }

    public List<GeoPosition> getAlt_trasa() {
        return alt_trasa;
    }

    public Set<UzolWaypoint> getAlt_waypointsIdecka() {
        return alt_waypointsIdecka;
    }

    

}
