/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author rendo
 */
public class StojiskoOverlayPainter extends WaypointPainter<StojiskoWaypoint> {
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer jxMapViewer, int width, int height) {
        for (StojiskoWaypoint swingWaypoint : getWaypoints()) {
            Point2D point = jxMapViewer.getTileFactory().geoToPixel(
                    swingWaypoint.getPosition(), jxMapViewer.getZoom());
            Rectangle rectangle = jxMapViewer.getViewportBounds();
            int buttonX = (int)(point.getX() - rectangle.getX());
            int buttonY = (int)(point.getY() - rectangle.getY());
            JButton button = swingWaypoint.getButton();
            button.setLocation(buttonX - button.getWidth() / 2, (buttonY - button.getHeight() / 2)-23);
        }
    }
}


    

