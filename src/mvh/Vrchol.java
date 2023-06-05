/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvh;

import java.util.Arrays;

/**
 *Trieda reprezentujuca vrchol
 * @author rendo
 */
public class Vrchol implements Comparable<Vrchol> {

    //private static int pocetVrcholov = 0;
    //private int id_vrchola;
    protected int id_uzla;
    protected Vrchol predchodca;
    protected int redukovanaCena;
    protected int[][] matrix;
    protected boolean[] navstiveneUzly;
    protected int pocetNavstivenychUzlov;
/**
 * Konštruktor koreňového vrchola
 * @param matrix - matica vzdialenosti
 */
    public Vrchol(int[][] matrix) {
//        System.out.println("#############################################");
//        System.out.println("Riesim: ID: " + id_uzla + " V: " + (pocetVrcholov + 1));

        this.matrix = Arrays.stream(matrix)
                .map(a -> Arrays.copyOf(a, a.length))
                .toArray(int[][]::new);
        
        upravMaticu();
        this.id_uzla = 0;
        //this.pocetVrcholov++;
        //this.id_vrchola = this.pocetVrcholov;
        this.navstiveneUzly = new boolean[matrix.length];
        this.navstiveneUzly[id_uzla] = true;
        this.pocetNavstivenychUzlov = 1;
        this.predchodca = null;
//        MVH.vypisGraf(this.matrix);
//        System.out.println("");
//        System.out.println("ReduceCost: " + this.redukovanaCena);
//        System.out.println("#############################################");
    }
/**
 * Konštruktor vrchola z prechodcu
 * @param id_uzla -identifikator uzla
 * @param predchodca - regerencia na predchadzajuci vrchol
 */
    public Vrchol(int id_uzla, Vrchol predchodca) {
//        System.out.println("#############################################");
//        System.out.println("Riesim: ID: " + id_uzla + " V: " + (pocetVrcholov + 1));

        this.id_uzla = id_uzla;
        this.predchodca = predchodca;
        this.pocetNavstivenychUzlov = predchodca.pocetNavstivenychUzlov + 1;
        this.matrix = Arrays.stream(predchodca.matrix)
                .map(a -> Arrays.copyOf(a, a.length))
                .toArray(int[][]::new);
        if (predchodca.predchodca == null) {
            nastavNekonecnaKedPrechodcaJeRoot();
        } else {
            nastavNekonecna();
        }

        upravMaticu();
//        MVH.vypisGraf(this.matrix);
        this.redukovanaCena = predchodca.matrix[predchodca.id_uzla][id_uzla]
                + predchodca.redukovanaCena + this.redukovanaCena;

        this.navstiveneUzly = new boolean[matrix.length];
        System.arraycopy(predchodca.navstiveneUzly, 0, this.navstiveneUzly, 0, predchodca.navstiveneUzly.length);
        this.navstiveneUzly[id_uzla] = true;

        //this.pocetVrcholov++;
        //this.id_vrchola = this.pocetVrcholov;
//        System.out.println("ReduceCost: " + this.redukovanaCena);
//        System.out.println("#############################################");
//        System.out.println("");
    }

    /**
     * Metoda sluziaca na zredukovanie matice
     */
    public void upravMaticu() {
        int sumRiadku = 0;
        int sumStlpce = 0;
        for (int i = 0; i < matrix.length; i++) {
            int tmpMinr = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] < tmpMinr) {
                    tmpMinr = matrix[i][j];
                    if (tmpMinr == 0) {
                        break;
                    }
                }
            }
            if (tmpMinr == Integer.MAX_VALUE) {
                tmpMinr = 0;
            }
//            System.out.println("Budem odratavat z riadkov: " + tmpMinr);
            if (tmpMinr != 0) {
                for (int j = 0; j < matrix.length; j++) {
                    if (!(matrix[i][j] == Integer.MAX_VALUE || matrix[i][j] == 0)) {
                        matrix[i][j] = matrix[i][j] - tmpMinr;
                    }
                }
            }
            sumRiadku += tmpMinr;
        }
//        System.out.println("CEKUJEM:>>>");
//        MVH.vypisGraf(matrix);
        for (int i = 0; i < matrix.length; i++) {
            int tmpMin = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] < tmpMin) {
                    tmpMin = matrix[j][i];
                    if (tmpMin == 0) {
                        break;
                    }
                }
            }
            if (tmpMin == Integer.MAX_VALUE) {
                tmpMin = 0;
            }
//            System.out.println("Budem odratavat zo stlpcov: " + tmpMin);
            if (tmpMin != 0) {
                for (int j = 0; j < matrix.length; j++) {
                    if (!(matrix[j][i] == 0 || matrix[j][i] == Integer.MAX_VALUE)) {
                        matrix[j][i] = matrix[j][i] - tmpMin;
                    }
                }
            }

            sumStlpce += tmpMin;
        }
        this.redukovanaCena = sumRiadku + sumStlpce;

    }
    /**
     * Pomocna metoda pre redukovanie matice, ked predchocom je koren
     */
    public void nastavNekonecnaKedPrechodcaJeRoot() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[this.predchodca.id_uzla][i] = Integer.MAX_VALUE;
            matrix[i][this.id_uzla] = Integer.MAX_VALUE;
        }
        matrix[this.id_uzla][this.predchodca.id_uzla] = Integer.MAX_VALUE;
    }
    /**
     * Pomocna metoda pre redukovanie matice, ked predchocom nie je koren
     */
    public void nastavNekonecna() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[this.predchodca.id_uzla][i] = Integer.MAX_VALUE;
            matrix[i][this.id_uzla] = Integer.MAX_VALUE;
        }

        Vrchol pom = predchodca.predchodca;
        while (pom.predchodca != null) {
            pom = pom.predchodca;
//            System.out.println("pom: "+getId_uzla());
        }

        matrix[this.id_uzla][pom.id_uzla] = Integer.MAX_VALUE;
    }
/**
 * getter na redukovancu cenu
 * @return 
 */
    public int getRedukovanaCena() {
        return redukovanaCena;
    }
/**
 * getter na maticu
 * @return 
 */
    public int[][] getMatrix() {
        return matrix;
    }
/**
 * Getter na navstivene uzly
 * @return 
 */
    public boolean[] getNavstiveneUzly() {
        return navstiveneUzly;
    }
/**
 * Getter na pocetNavstivenych uzlov
 * @return 
 */
    public int getPocetNavstivenychUzlov() {
        return pocetNavstivenychUzlov;
    }
/**
 * Getter na id_uzla
 * @return 
 */
    public int getId_uzla() {
        return id_uzla;
    }

//    public int getId_vrchola() {
//        return id_vrchola;
//    }
    /**
     * Getter na predchodcu
     * @return 
     */
    public Vrchol getPredchodca() {
        return predchodca;
    }

    /**
     * Porovnanie vrcholov
     * @param vrchol
     * @return 
     */
    @Override
    public int compareTo(Vrchol vrchol) {
        return Integer.compare(this.redukovanaCena, vrchol.getRedukovanaCena());
    }

}
