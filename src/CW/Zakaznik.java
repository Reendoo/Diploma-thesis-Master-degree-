/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;

/**
 * Trieda reprezentujuca zakakaznika
 *
 * @author rendo
 */
public class Zakaznik implements Comparable<Zakaznik> {

    private int id_zakaznika;
    private int poziadavka;
    private String adresa;

    /**
     * Konštruktor triedy
     *
     * @param id_zakaznika - id zakaznica
     * @param poziadavka - poziadavka
     */
    public Zakaznik(int id_zakaznika, int poziadavka) {
        this.id_zakaznika = id_zakaznika;
        this.poziadavka = poziadavka;       
    }
    /**
     * Konstruktor, ked je trieda pouzita na spracovanie Query z databazy
     * @param id_zakaznika 
     * @param poziadavka
     * @param adresa 
     */
    public Zakaznik(int id_zakaznika, int poziadavka, String adresa) {
        this.id_zakaznika = id_zakaznika;
        this.poziadavka = poziadavka;
        this.adresa = adresa;
    }

    /**
     * Getter id_zakaznika
     *
     * @return
     */
    public int getId_zakaznika() {
        return id_zakaznika;
    }

    /**
     * Getter poziadvky zakaznika
     *
     * @return
     */
    public int getPoziadavka() {
        return poziadavka;
    }

    /**
     * Porovnanie zakaznika na zaklade poziadaviek
     *
     * @param z - zakaznik
     * @return
     */
    @Override
    public int compareTo(Zakaznik z) {
        return Double.compare(z.getPoziadavka(), this.poziadavka);
    }

    /**
     * Pomocna metoda na vypis zakaznika
     *
     * @return
     */
    @Override
    public String toString() {
        return "Z:" + id_zakaznika + " P:" + poziadavka + " A:" + adresa;
    }

    /**
     * Setter na id_zakaznika
     *
     * @param id_zakaznika
     */
    public void setId_zakaznika(int id_zakaznika) {
        this.id_zakaznika = id_zakaznika;
    }

    /**
     * Getter na adresu zakaznika
     *
     * @return
     */
    public String getAdresa() {
        return adresa;
    }

}
