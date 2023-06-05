/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;


import java.util.ArrayList;


/**
 * Trieda reprezentujuca ulohu okruznych jazd
 * @author rendo
 */
public class VRUloha {
    private int [][]Dmatrix;
    private ArrayList<Integer>poziadavky;
    private ArrayList<Vozidlo>vozidla;

    public VRUloha(int[][] Dmatrix, ArrayList<Integer> poziadavky, ArrayList<Vozidlo> vozidla) {
        this.Dmatrix = Dmatrix;
        this.poziadavky = (ArrayList<Integer>)poziadavky.clone();
        this.poziadavky.remove(0);
        
        //System.out.println("poziadavka v ulohe:\n" + Arrays.toString(this.poziadavky.toArray()));
        this.vozidla = vozidla;
    }

    public int[][] getDmatrix() {
        return Dmatrix;
    }

    public void setDmatrix(int[][] Dmatrix) {
        this.Dmatrix = Dmatrix;
    }

    public ArrayList<Integer> getPoziadavky() {
        return poziadavky;
    }

    public void setPoziadavky(ArrayList<Integer> poziadavky) {
        this.poziadavky = poziadavky;
    }

    public ArrayList<Vozidlo> getVozidla() {
        return vozidla;
    }

    public void setVozidla(ArrayList<Vozidlo> vozidla) {
        this.vozidla = vozidla;
    }
    
    
    
 
    
    
    
    
    
    
            
}
