/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger;

import java.text.Normalizer;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *  Trieda, ktora sa pouziva pre vytvorenie spravy na odoslanie
 * @author rendo
 */
public class SMS {

    private String apiKey;
    private String text;
    private String odosielatel;
    private ArrayList<String> prijemcovia;
    

/**
 * Konstruktor triedy
 * @param text
 * @param odosielatel
 * @param prijemcovia 
 */
    public SMS(String text, String odosielatel, ArrayList<String> prijemcovia) {
        try {
            this.text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            this.text = this.text.replace("\n", "");
            System.out.println(this.text);
            this.odosielatel = Normalizer.normalize(odosielatel, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            this.odosielatel = this.odosielatel.replace("\n", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        this.odosielatel = odosielatel;
        this.prijemcovia = prijemcovia;
    }
    /**
     * Nastavenie apiKey
     * @param apiKey 
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    /**
     * Metoda, ktora objekt SMS prepise do Stringu v potrebnom JASON FORMATE
     * @return 
     */
    @Override
    public String toString() {
        String s = "{\n"
                + "\"auth\" : { \"apikey\" : \"" + apiKey + "\"},\n"
                + "\"data\" : {\n"
                + "\"message\" : \"" + text + "\",\n"
                + "\"sender\" : {\n"
                + "\"text\" : \"" + odosielatel + "\"\n"
                + "},\n"
                + "\"recipients\" : [";
        String p = "";
        for (String string : prijemcovia) {
            p += "\n{ \"phonenr\" : \"" + string + "\"},";
        }
        p = p.substring(0, p.lastIndexOf(","));
        s += p;
        s += "]\n}\n}";
        return s;
    }
}
