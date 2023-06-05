/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *Trieda reprezentujuca prijatu SMS
 * @author rendo
 */
public class PrijataSprava {

    private String tel;
    private String text;
    private Date datum;

    /**
     * Konštruktor
     * @param tel - odosielatel
     * @param text -text sms
     * @param datum -datum prijatia
     */
    public PrijataSprava(String tel, String text, String datum) {
        this.tel = tel;
        this.text = text;
        this.datum = parseDate(datum);
    }
    /**
     * getter na odosielatela
     * @return 
     */
    public String getTel() {
        return tel;
    }
    /**
     * Getter na datum prijatia
     * @return 
     */
    public Date getDatum() {
        return datum;
    }
    /**
     * Getter na telo spravy
     * @return 
     */
    public String getText() {
        return text;
    }

    /**
     * Metoda na parsovanie Stringu datumu na Date
     * @param d
     * @return 
     */
    public static Date parseDate(String d) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        try {
            Date date = simpleDateFormat.parse(d);
            return date;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }
    /**
     * Pomocna metoda na vypis 
     * @return 
     */
    @Override
    public String toString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
        return "Odosielatel: " + tel + " Datum: " + sdfDate.format(datum)
                + "\nText: " + text;
    }

}
