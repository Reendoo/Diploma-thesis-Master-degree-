/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;

/**
 *  Reprezentacia vozidla
 * @author rendo
 */
public class Vozidlo implements Comparable<Vozidlo> {
    
    String SPZ;
    double kapacita;
    String stav;
    int priorita;
    /**
     * Koštruktor
     * @param SPZ
     * @param kapacita
     * @param priorita 
     */
    public Vozidlo(String SPZ, double kapacita, int priorita) {      
        this.kapacita = kapacita;
        this.SPZ = SPZ;
        this.priorita = priorita;
    }
    /**
     * Konštruktor
     * @param SPZ
     * @param kapacita
     * @param stav 
     */
    public Vozidlo(String SPZ, double kapacita, String stav) {
        this.SPZ = SPZ;
        this.kapacita = kapacita;
        this.stav = stav;
    }
    /**
     * Getter na prioritu
     * @return 
     */
    public int getPriorita() {
        return priorita;
    }
    
    

//    @Override
//    public int compareTo(Vozidlo t) {
//        return Double.compare(t.getKapacita(), this.kapacita);
//    }
    /**
     * Metoda na porovnavanie vozidiel podla priority a kapacity
     * @param t
     * @return 
     */
        @Override
    public int compareTo(Vozidlo t) {
        int pom =Integer.compare(t.getPriorita(),this.priorita)*-1;
        if(pom == 0){
           return Double.compare(t.getKapacita(), this.kapacita); 
        }
        return pom;
    }

  
    /**
     * Setter SPZ
     * @param id_Vozidla 
     */
    public void setSPZ(String id_Vozidla) {
        this.SPZ = id_Vozidla;
    }
    /**
     * Getter na kapacitu
     * @return 
     */
    public double getKapacita() {
        return kapacita;
    }
    /**
     * Setter na kapacitu
     * @param kapacita 
     */
    public void setKapacita(double kapacita) {
        this.kapacita = kapacita;
    }

  
    /**
     * Getter na spz
     * @return 
     */
    public String getSPZ() {
        return SPZ;
    }
    /**
     * Setter na stav vozidla
     * @return 
     */
    public String getStav() {
        return stav;
    }
    
    
    /**
     * Retazcova reprezentacia vozidla
     * @return 
     */
    @Override
    public String toString() {
        return "V:" + SPZ +" K:" + kapacita;
    }
}
