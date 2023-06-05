/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import java.util.Date;

/**
 *
 * @author rendo
 */
public class Kontajner {

    protected int id_kontajnera;
    protected String nazovOdpadu;
    protected String adresa;
    protected int aktualnyObjem;
    protected int maxObjem;
    protected Date Datum_poslednehoVyprazdnenia;
    protected Date Datum_poslednejAktualiziacie;
    /**
     * konstruktor
     * @param id_kontajnera
     * @param adresa
     * @param nazovOdpadu
     * @param aktualnyObjem
     * @param maxObjem
     * @param Datum_poslednejAktualiziacie
     * @param Datum_poslednehoVyprazdnenia 
     */
    public Kontajner(int id_kontajnera, String adresa, String nazovOdpadu,
            int aktualnyObjem, int maxObjem, Date Datum_poslednejAktualiziacie,
            Date Datum_poslednehoVyprazdnenia) {
        this.id_kontajnera = id_kontajnera;
        this.nazovOdpadu = nazovOdpadu;
        this.aktualnyObjem = aktualnyObjem;
        this.maxObjem = maxObjem;
        this.adresa = adresa;
        this.Datum_poslednehoVyprazdnenia = Datum_poslednehoVyprazdnenia;
        this.Datum_poslednejAktualiziacie = Datum_poslednejAktualiziacie;
    }
    /**
     * konstruktor
     * @param id_kontajnera
     * @param nazovOdpadu
     * @param aktualnyObjem
     * @param maxObjem
     * @param Datum_poslednehoVyprazdnenia
     * @param Datum_poslednejAktualiziacie 
     */
    public Kontajner(int id_kontajnera, String nazovOdpadu, int aktualnyObjem, int maxObjem, Date Datum_poslednehoVyprazdnenia, Date Datum_poslednejAktualiziacie) {
        this.id_kontajnera = id_kontajnera;
        this.nazovOdpadu = nazovOdpadu;
        this.aktualnyObjem = aktualnyObjem;
        this.maxObjem = maxObjem;
        this.Datum_poslednehoVyprazdnenia = Datum_poslednehoVyprazdnenia;
        this.Datum_poslednejAktualiziacie = Datum_poslednejAktualiziacie;
    }
    /**
     * konstruktor
     * @param nazovOdpadu
     * @param adresa
     * @param aktualnyObjem
     * @param maxObjem 
     */
    public Kontajner(String nazovOdpadu, String adresa, int aktualnyObjem, int maxObjem) {
        this.nazovOdpadu = nazovOdpadu;
        this.adresa = adresa;
        this.aktualnyObjem = aktualnyObjem;
        this.maxObjem = maxObjem;
    }
    /**
     * Getter na id kontajnera
     * @return 
     */
    public int getId_kontajnera() {
        return id_kontajnera;
    }


    /**
     * getter na nazov odpadu
     * @return 
     */
    public String getNazovOdpadu() {
        return nazovOdpadu;
    }
    /**
     * Setter na nazov odpadu
     * @param nazovOdpadu 
     */
    public void setNazovOdpadu(String nazovOdpadu) {
        this.nazovOdpadu = nazovOdpadu;
    }
    /**
     * getter na aktualny objem
     * @return 
     */
    public int getAktualnyObjem() {
        return aktualnyObjem;
    }
    /**
     * setter na aktualny objem
     * @param aktualnyObjem 
     */
    public void setAktualnyObjem(int aktualnyObjem) {
        this.aktualnyObjem = aktualnyObjem;
    }
    /**
     * getter na maximalny objem
     * @return 
     */
    public int getMaxObjem() {
        return maxObjem;
    }
    //setter maximalne objmemu
    public void setMaxObjem(int maxObjem) {
        this.maxObjem = maxObjem;
    }
    /**
     * getter na  datum posledne vyprazdnenia
     * @return 
     */
    public Date getDatum_poslednehoVyprazdnenia() {
        return Datum_poslednehoVyprazdnenia;
    }
    

    /**
     * Getter na datum poslenej aktualizacie
     * @return 
     */
    public Date getDatum_poslednejAktualiziacie() {
        return Datum_poslednejAktualiziacie;
    }

    /**
     * pomocna metoda na vypis
     * @return 
     */
    @Override
    public String toString() {
        return "Kontajner{" + "id_kontajnera=" + id_kontajnera + ", nazovOdpadu=[" + nazovOdpadu + "], adresa=[" + adresa + "], aktualnyObjem=" + aktualnyObjem + ", maxObjem=" + maxObjem + ", Datum_poslednehoVyprazdnenia=" + Datum_poslednehoVyprazdnenia + ", Datum_poslednejAktualiziacie=" + Datum_poslednejAktualiziacie + '}';
    }
    /**
     * getter na adresu
     * @return 
     */
    public String getAdresa() {
        return adresa;
    }
    /**
     * setter na adresu
     * @param adresa 
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

}
