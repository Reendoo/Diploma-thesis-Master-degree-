/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenAlg;

import Graf.Graf;
import Graf.Suradnice;
import java.util.LinkedList;

/**
 *
 * @author rendo
 */
public class PMedian {

    protected int pocetUmiestnenychCentier;
    protected LinkedList<Integer> poziadavky;
    protected int[][] dmatrix;

    public PMedian(int pocetCentier, LinkedList<Suradnice> mozneUmiestnenia, LinkedList<Suradnice> zakaznici,
            LinkedList<Integer> poziadavky) {
        
       
//        System.out.println("mozne: "+mozneUmiestnenia.size());
//        System.out.println("zakaznici: "+zakaznici.size());
        this.pocetUmiestnenychCentier = pocetCentier;
        this.poziadavky = poziadavky;
//        System.out.println(Arrays.toString(poziadavky.toArray()));
        this.dmatrix = new int[mozneUmiestnenia.size()][zakaznici.size()];
//        System.out.println("mozneUmiestnenia.size()" + mozneUmiestnenia.size());
//        System.out.println("zakaznici.size())" + zakaznici.size());
//          System.out.println("poziadavky:" + poziadavky.size());  

        for (int i = 0; i < mozneUmiestnenia.size(); i++) {
            for (int j = 0; j < zakaznici.size(); j++) {
                double vzd = Suradnice.vyratajVzdialenostKM(mozneUmiestnenia.get(i), zakaznici.get(j)) * 1000;
                this.dmatrix[i][j] = (int) (vzd + 0.5);
            }
        }
//        Graf.printMatrix(dmatrix);
//        System.out.println("PocetRiadkov" +dmatrix.length);
//        System.out.println("PocetStlpcov" +dmatrix[0].length);
//        System.out.println("");
        
    }

    public static void main(String[] args) {
        LinkedList<Suradnice> mozneUmiestnenia = new LinkedList<>();
        mozneUmiestnenia.add(new Suradnice(49.08971, 18.6442));
        mozneUmiestnenia.add(new Suradnice(49.08702, 18.62254));
        mozneUmiestnenia.add(new Suradnice(49.08667, 18.62455));
        mozneUmiestnenia.add(new Suradnice(49.09117, 18.63153));
        mozneUmiestnenia.add(new Suradnice(49.09125, 18.63113));
        mozneUmiestnenia.add(new Suradnice(49.09238, 18.63461));
        mozneUmiestnenia.add(new Suradnice(49.09117, 18.63455));
        mozneUmiestnenia.add(new Suradnice(49.09107, 18.63373));
        mozneUmiestnenia.add(new Suradnice(49.09029, 18.63483));
        mozneUmiestnenia.add(new Suradnice(49.09027, 18.63522));
        mozneUmiestnenia.add(new Suradnice(49.09023, 18.63586));
        mozneUmiestnenia.add(new Suradnice(49.09016, 18.63456));
        mozneUmiestnenia.add(new Suradnice(49.09017, 18.63676));
        mozneUmiestnenia.add(new Suradnice(49.09012, 18.63728));
        mozneUmiestnenia.add(new Suradnice(49.09079, 18.63833));
        mozneUmiestnenia.add(new Suradnice(49.09099, 18.63747));
        mozneUmiestnenia.add(new Suradnice(49.09131, 18.63724));
        mozneUmiestnenia.add(new Suradnice(49.09238, 18.63773));
        mozneUmiestnenia.add(new Suradnice(49.09181, 18.63881));
        mozneUmiestnenia.add(new Suradnice(49.09334, 18.63924));
        mozneUmiestnenia.add(new Suradnice(49.08863, 18.63948));
        mozneUmiestnenia.add(new Suradnice(49.08931, 18.63873));
        mozneUmiestnenia.add(new Suradnice(49.08881, 18.6388));
        mozneUmiestnenia.add(new Suradnice(49.09149, 18.64102));
        mozneUmiestnenia.add(new Suradnice(49.08717, 18.63834));
        mozneUmiestnenia.add(new Suradnice(49.08696, 18.63823));
        mozneUmiestnenia.add(new Suradnice(49.08658, 18.63771));
        mozneUmiestnenia.add(new Suradnice(49.08575, 18.64043));
        mozneUmiestnenia.add(new Suradnice(49.08597, 18.64067));
        mozneUmiestnenia.add(new Suradnice(49.08641, 18.64107));
        mozneUmiestnenia.add(new Suradnice(49.08703, 18.64167));
        mozneUmiestnenia.add(new Suradnice(49.0856, 18.6416));
        mozneUmiestnenia.add(new Suradnice(49.08582, 18.64102));
        mozneUmiestnenia.add(new Suradnice(49.08387, 18.64025));
        mozneUmiestnenia.add(new Suradnice(49.08353, 18.63949));
        mozneUmiestnenia.add(new Suradnice(49.08157, 18.63844));
        mozneUmiestnenia.add(new Suradnice(49.07971, 18.63702));
        mozneUmiestnenia.add(new Suradnice(49.08385, 18.639));
        mozneUmiestnenia.add(new Suradnice(49.08378, 18.63797));
        mozneUmiestnenia.add(new Suradnice(49.08273, 18.63638));
        mozneUmiestnenia.add(new Suradnice(49.08365, 18.63577));
        mozneUmiestnenia.add(new Suradnice(49.08613, 18.63552));
        mozneUmiestnenia.add(new Suradnice(49.08266, 18.63474));
        mozneUmiestnenia.add(new Suradnice(49.081, 18.63408));
        mozneUmiestnenia.add(new Suradnice(49.08129, 18.63456));
        mozneUmiestnenia.add(new Suradnice(49.08122, 18.63278));
        mozneUmiestnenia.add(new Suradnice(49.08016, 18.63437));
        mozneUmiestnenia.add(new Suradnice(49.08493, 18.63449));
        mozneUmiestnenia.add(new Suradnice(49.08411, 18.63408));
        mozneUmiestnenia.add(new Suradnice(49.083, 18.63314));
        mozneUmiestnenia.add(new Suradnice(49.08294, 18.63355));
        mozneUmiestnenia.add(new Suradnice(49.08213, 18.63318));
        mozneUmiestnenia.add(new Suradnice(49.07811, 18.63136));
        mozneUmiestnenia.add(new Suradnice(49.08708, 18.63329));
        mozneUmiestnenia.add(new Suradnice(49.08955, 18.63319));
        mozneUmiestnenia.add(new Suradnice(49.08723, 18.63365));
        mozneUmiestnenia.add(new Suradnice(49.08752, 18.63421));
        mozneUmiestnenia.add(new Suradnice(49.08752, 18.63422));
        mozneUmiestnenia.add(new Suradnice(49.08845, 18.63674));
        mozneUmiestnenia.add(new Suradnice(49.08837, 18.63697));
        mozneUmiestnenia.add(new Suradnice(49.08974, 18.63501));
        mozneUmiestnenia.add(new Suradnice(49.08965, 18.63587));
        mozneUmiestnenia.add(new Suradnice(49.08909, 18.63444));
        mozneUmiestnenia.add(new Suradnice(49.08902, 18.6352));
        mozneUmiestnenia.add(new Suradnice(49.09676, 18.64483));
        mozneUmiestnenia.add(new Suradnice(49.09408, 18.63894));
        mozneUmiestnenia.add(new Suradnice(49.08296, 18.65451));
        mozneUmiestnenia.add(new Suradnice(49.08068, 18.64537));
        mozneUmiestnenia.add(new Suradnice(49.08146, 18.6413));
        mozneUmiestnenia.add(new Suradnice(49.08143, 18.64147));
        LinkedList<Suradnice> zakaznici = new LinkedList<>();
        zakaznici.add(new Suradnice(49.222, 18.333));
        zakaznici.add(new Suradnice(49.333, 18.222));
        zakaznici.add(new Suradnice(49.444, 18.555));
        zakaznici.add(new Suradnice(49.555, 18.444));
        zakaznici.add(new Suradnice(49.666, 18.222));
        zakaznici.add(new Suradnice(49.888, 18.222));
        zakaznici.add(new Suradnice(49.999, 18.222));
        zakaznici.add(new Suradnice(49.158, 18.658));
        zakaznici.add(new Suradnice(49.512, 18.487));
        zakaznici.add(new Suradnice(49.000, 18.666));
        zakaznici.add(new Suradnice(49.111, 18.777));
        zakaznici.add(new Suradnice(49.888, 18.111));
        zakaznici.add(new Suradnice(49.312, 18.888));
        LinkedList<Integer> p = new LinkedList<>();
        p.add(1000);
        p.add(1100);
        p.add(1200);
        p.add(1300);
        p.add(1400);
        p.add(1100);
        p.add(1200);
        p.add(1300);
        p.add(1400);
        p.add(1100);
        p.add(1400);
        p.add(1300);
        p.add(1200);
//        System.out.println("Poziadavky: "+Arrays.toString(p.toArray())); 

        int pocetCentier = 20;

        long best = Long.MAX_VALUE;
        long worst = Long.MIN_VALUE;
        long priemer = 0;

        for (int i = 0; i < 100; i++) {
            if(i%10==0){
                System.out.println("Rep:"+i);
            }
            PMedian median = new PMedian(pocetCentier, mozneUmiestnenia, zakaznici, p);
            if(i==0){
                Graf.printMatrix(median.dmatrix);
            }
            GenAlg alg = new GenAlg(median);
            alg.startGenAlg();
            priemer +=alg.najlepsiaUF;            
            if (best > alg.najlepsiaUF) {
                best = alg.najlepsiaUF;
            }
            if (worst < alg.najlepsiaUF) {
                worst = alg.najlepsiaUF;
            }
        }
        System.out.println("Priemer: "+(priemer/100.0));
        System.out.println("Best: "+(best));
        System.out.println("Worst: "+(worst));

//        PMedian median = new PMedian(pocetCentier, mozneUmiestnenia, zakaznici, p);
//        Graf.printMatrix(median.dmatrix);
//        System.out.println("Poziadavky: " + Arrays.toString(median.poziadavky.toArray()));
//        GenAlg alg = new GenAlg(median);
//        alg.startGenAlg();
//        System.out.println("Najlepsia ucelofka" + alg.najlepsiaUF);
//        System.out.println("Zaradene: " + Arrays.toString(alg.vybraneUzly));

    }

    public int[][] getDmatrix() {
        return dmatrix;
    }

   

}
