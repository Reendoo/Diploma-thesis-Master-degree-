/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapy;


import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

/**
 *
 * @author rendo
 */
public class StojiskoRender implements WaypointRenderer<StojiskoWaypoint> {    
    private final Map<Color, BufferedImage> map = new HashMap<>();   
    private BufferedImage origImage;
    private BufferedImage multicolor;

   /**
    * Konštruktor, na prefarbenie icony
    */
    public StojiskoRender() {
        try {
           URL resource = getClass().getResource("waypoint_white.png"); 
           origImage = ImageIO.read(resource);           
           URL resourcemulti = getClass().getResource("multicolorwaypoint.png"); 
           multicolor = ImageIO.read(resourcemulti);           
           
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nenašla sa icona waypoint_white.png");
        }      
    }

    /**
     * Metóda slúžiaca na zafarbenie icony
     * @param loadImg - ikona
     * @param newColor - nova farba
     * @return 
     */
    private BufferedImage convert(BufferedImage loadImg, Color newColor) {
        int w = loadImg.getWidth();
        int h = loadImg.getHeight();
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        BufferedImage imgColor = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imgColor.createGraphics();
        g.setColor(newColor);
        g.fillRect(0, 0, w + 1, h + 1);
        g.dispose();
        Graphics2D graphics = imgOut.createGraphics();
        graphics.drawImage(loadImg, 0, 0, null);
        graphics.setComposite(MultiplyComposite.Default);
        graphics.drawImage(imgColor, 0, 0, null);
        graphics.dispose();
        return imgOut;
    }

    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer viewer, StojiskoWaypoint w) {
        g = (Graphics2D) g.create();
        if (origImage == null ||multicolor==null) {
            return;
        }
        BufferedImage myImg = map.get(w.getColor());
        
        if (myImg == null) {
            if(w.getColor()!=Color.ORANGE){
               myImg = convert(origImage, w.getColor());               
            }else{
                myImg = multicolor;
            }            
            map.put(w.getColor(), myImg);
        }
        Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(), viewer.getZoom());
        int x = (int) point.getX();
        int y = (int) point.getY();
        g.drawImage(myImg, x - myImg.getWidth() / 2, y - myImg.getHeight(), null);
        String label = ""+w.getId_stojiska();
        FontMetrics metrics = g.getFontMetrics();
        int tw = metrics.stringWidth(label);
        int th = 1 + metrics.getAscent();
        g.drawString(label, x - tw / 2, y + th - myImg.getHeight());
        g.dispose();
    }
}
    
    
    
    

