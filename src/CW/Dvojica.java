/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;

/**
 *Trieda reprezentujuca dvojicu zapajania zakaznika
 * @author rendo
 */
public class Dvojica implements Comparable<Dvojica> {
    protected int index1;
    protected int index2;
    protected int uspora;
    /**
     * Konstrutor
     * @param index1 - zakaznika i
     * @param index2  - zakaznika j
     */
    public Dvojica(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
        this.uspora = Integer.MIN_VALUE;
    }
    /**
     * Porovnavanie dvojic, na zaklade uspory
     * @param d
     * @return 
     */
     @Override
    public int compareTo(Dvojica d) {
        return Integer.compare(d.getUspora(), this.uspora);
    }
    /**
     * Getter na usporu
     * @return 
     */
    public int getUspora() {
        return uspora;
    }
    /**
     * Metoda ratajuca uspory zaradenim zakaznika do riesenia
     * @param dis
     * @param dsj
     * @param dij 
     */
    public void vyratajUsporu(int dis, int dsj, int dij){
        uspora = dis+dsj-dij;
    }

    /**
     * Pomocna metoda na vypis dvojice
     * @return 
     */
    @Override
    public String toString() {
        return "I1:" + index1 + " I2:" + index2 + " U:" + uspora;
    }
    
}
