/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

/**
 * Pomocna trieda na rozparsovanie vysledku generovanie matice vzdialenosti
 *
 * @author rendo
 */
public class Matice {

    private int iDmatrix[][];
    private String sDmatrix[][];

    /**
     * Konstruktor
     *
     * @param iDmatrix
     * @param sDmatrix
     */
    public Matice(int iDmatrix[][], String sDmatrix[][]) {
        this.iDmatrix = iDmatrix;
        this.sDmatrix = sDmatrix;
    }

    /**
     * Geter na maticu vzdialenosti
     *
     * @return
     */
    public int[][] getiDmatrix() {
        return iDmatrix;
    }
    /**
     * Getter na maticu najkratsich ciest medzi uzlami
     * @return 
     */
    public String[][] getsDmatrix() {
        return sDmatrix;
    }

}
