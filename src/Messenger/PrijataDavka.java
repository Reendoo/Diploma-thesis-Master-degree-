/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger;

import java.util.LinkedList;

/**
 * Trieda urcena na reprezentaciu prijatej davky sprav
 * @author rendo
 */
public class PrijataDavka {

    private LinkedList<PrijataSprava> prijateSpravy;
    private String id_stav;
    /**
     *  Konstruktor spravy
     * @param id - OK ak prisli sms, ZIADNE_NOVE_SPRAVY - neprisli spravy
     */
    public PrijataDavka(String id) {
        this.prijateSpravy = new LinkedList<>();
        this.id_stav = id;
    }
    /**
     * pridanie spravy do davky
     * @param sprava 
     */
    public void pridajSpravu(PrijataSprava sprava) {
        this.prijateSpravy.add(sprava);
    }
    /**
     * Getter na zoznam priajtych sprav
     * @return 
     */
    public LinkedList<PrijataSprava> getPrijateSpravy() {
        return prijateSpravy;
    }
    /**
     * Geter na stav davky
     * @return 
     */
    public String getId_stav() {
        return id_stav;
    }


//    @Override
//    public String toString() {
//        return "PrijataDavka{" + "prijateSpravy=" + prijateSpravy + ", id_stav=" + id_stav + '}';
//    }
    
}
