/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;

import java.awt.Color;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author rendo
 */
public class MapPoint extends DefaultWaypoint {

    private final String label;
    private final Color color;

    public MapPoint(String label, Color color, GeoPosition coord) {
        super(coord);
        this.label = label;
        this.color = color;
    }
        public Color getColor()
    {
        return color;
    }

    public String getLabel() {
        return label;
    }
        

 
    
}
