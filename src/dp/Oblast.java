/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import Graf.Suradnice;
import java.util.LinkedList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author rendo
 */
public class Oblast {

    int id;
    LinkedList<GeoPosition> body;
    int poziadavka;
    GeoPosition suradnica;

    public Oblast(int id, LinkedList<GeoPosition> body, int poziadavka) {
        this.id = id;
        this.body = body;
        this.poziadavka = poziadavka;
        double x = 0;
        double y = 0;
        int i;
        for (i = 0; i < body.size() - 1; i++) {
            x += body.get(i).getLatitude();
            y += body.get(i).getLongitude();
        }
        this.suradnica = new GeoPosition(x / i, y / i);
    }

    public Oblast(int id,int poziadavka, GeoPosition suradnica,LinkedList<GeoPosition> body) {
        this.id = id;
        this.body = body;
        this.poziadavka = poziadavka;
        this.suradnica = suradnica;
    }

   
    
    

    public int getId() {
        return id;
    }

    public LinkedList<GeoPosition> getBody() {
        return body;
    }

    public int getPoziadavka() {
        return poziadavka;
    }

    public GeoPosition getSuradnica() {
        return suradnica;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

}
