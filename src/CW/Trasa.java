/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;


import java.util.ArrayList;

/**
 * Reprezentacia Trasy vozidla
 * @author rendo
 */
public class Trasa {
    // pre CW algoritmus
    private Vozidlo vozidlo;
    private ArrayList<Zakaznik> zakazniciTrasy;
    protected int aktualnaKapacitaJazdy;
    private int naklady;

    // pre fungovanie OH
    private ArrayList<Integer> alternativnaTrasa;
    private int alternativneNaklady;
    private String druhOdpadu;
    
    /**
     * Getter na druh odpadu 
     * @return 
     */
    public String getDruhOdpadu() {
        return druhOdpadu;
    }

    /**
     * Setter na druhOdpadu
     * @param druhOdpadu 
     */
    public void setDruhOdpadu(String druhOdpadu) {
        this.druhOdpadu = druhOdpadu;
    }
    
    
    /**
     * Konstruktor triedy, pre fungovanie OH
     * @param vozidlo
     * @param zakazniciTrasy
     * @param aktualnaKapacitaJazdy
     * @param naklady 
     */
    public Trasa(Vozidlo vozidlo, ArrayList<Zakaznik> zakazniciTrasy, int aktualnaKapacitaJazdy, int naklady) {
        this.vozidlo = vozidlo;
        this.zakazniciTrasy = zakazniciTrasy;
        this.aktualnaKapacitaJazdy = aktualnaKapacitaJazdy;
        this.naklady = naklady;
    }
    /**
     * Konštruktor triedy pre CW
     * @param vozidlo 
     */
    public Trasa(Vozidlo vozidlo) {
        this.vozidlo = vozidlo;
        this.zakazniciTrasy = new ArrayList<>();
        this.aktualnaKapacitaJazdy = 0;
    }
   
    /**
     * Getter na vozidlo
     * @return 
     */
    public Vozidlo getVozidlo() {
        return vozidlo;
    }

    /**
     * Getter na zakaznikov trasy
     * @return 
     */
    public ArrayList<Zakaznik> getZakazniciTrasy() {
        return zakazniciTrasy;
    }
    /**
     * Setter na zakaznikov trasy
     * @param zakazniciTrasy 
     */
    public void setZakazniciTrasy(ArrayList<Zakaznik> zakazniciTrasy) {
        this.zakazniciTrasy = zakazniciTrasy;
    }
   
    /**
     * Getter na aktualnu kapacitu jazdy
     * @return 
     */
    public int getAktualnaKapacitaJazdy() {
        return aktualnaKapacitaJazdy;
    }
    /**
     * Pridanie zazanika na koniec trasy
     * @param z 
     */
    public void pridajZakaznikaNaKoniec(Zakaznik z) {
        zakazniciTrasy.add(z);
        this.aktualnaKapacitaJazdy += z.getPoziadavka();
        //System.out.println(this.toString());
    }
    /**
     * Pridanie zakaznika na zaciatok trasy
     * @param z 
     */
    public void pridajZakaznikaNaZaciatok(Zakaznik z) {
        ArrayList<Zakaznik> pom = new ArrayList<>();
        pom.add(z);
        pom.addAll(this.zakazniciTrasy);
        this.zakazniciTrasy = pom;
        this.aktualnaKapacitaJazdy += z.getPoziadavka();
        //System.out.println(this.toString());

    }
    /**
     * Setter aktualnej kapacity jazdy
     * @param aktualnaKapacitaJazdy 
     */
    public void setAktualnaKapacitaJazdy(int aktualnaKapacitaJazdy) {
        this.aktualnaKapacitaJazdy = aktualnaKapacitaJazdy;
    }
    /**
     * Pomocna metoda na vypis trasy
     * @return 
     */
    @Override
    public String toString() {
        String pom = "";
        for (Zakaznik zakaznik : zakazniciTrasy) {
            pom += "(Z:" + zakaznik.getId_zakaznika() + " P:" + zakaznik.getPoziadavka() + ") ";
        }

        return "V" + vozidlo.getSPZ() + " MaxKap: " + vozidlo.getKapacita() + " AktualnaKapcita: " + aktualnaKapacitaJazdy + " Zakaznici: " + pom;
    }
    

    /**
     * getter na naklady
     * @return 
     */
    public int getNaklady() {
        return naklady;
    }
    /**
     * Setter na naklady
     * @param naklady 
     */
    public void setNaklady(int naklady) {
        this.naklady = naklady;
    }
    /**
     * Nastavenie alternativnej trasy - ziskanej z dodoptimalizacie
     * @param alternativnaTrasa
     * @param alternativneNaklady 
     */
    public void setAlternativneData(ArrayList<Integer> alternativnaTrasa, int alternativneNaklady) {
        this.alternativnaTrasa = alternativnaTrasa;
        this.alternativneNaklady = alternativneNaklady;
    }
    
    /**
     * Getter alternativnej trasy
     * @return 
     */
    public ArrayList<Integer> getAlternativnaTrasa() {
        return alternativnaTrasa;
    }
    /**
     * Getter hlavnej trasy
     * @return 
     */
    public ArrayList<Integer> getHlavnaTrasa() {
        ArrayList<Integer> idecka = new ArrayList<>();
        for (Zakaznik zakaznik : zakazniciTrasy) {
            idecka.add(zakaznik.getId_zakaznika());
        }
        return idecka;
    }
    /**
     * Nastavenie alternativnej trasy
     * @param alternativnaTrasa 
     */
    public void setAlternativnaTrasa(ArrayList<Integer> alternativnaTrasa) {
        this.alternativnaTrasa = alternativnaTrasa;
    }
    /**
     * getter alterbativnych nakladov
     * @return 
     */
    public int getAlternativneNaklady() {
        return alternativneNaklady;
    }
    /**
     * Setter alternativnych nakladov
     * @param alternativneNaklady 
     */
    public void setAlternativneNaklady(int alternativneNaklady) {
        this.alternativneNaklady = alternativneNaklady;
    }
}
