/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import ACO.ACO;
import Graf.Graf;
import CW.ClarkWright;
import CW.Trasa;
import CW.VRUloha;
import CW.Vozidlo;
import CW.Zakaznik;
import GUI.Refresher;
import GenAlg.GenAlg;
import GenAlg.PMedian;
import Graf.Suradnice;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import mvh.MVH;

/**
 * Trieda urcena na celkovu optimalizaciu
 *
 * @author rendo
 */
public class Optimalizacia {

    OdpadoveHospodarstvo oh;
    MVH mvh;
    ACO aco;
    ClarkWright cw;
    GenAlg genAlg;
    ArrayList<Integer> stojiska;
    ArrayList<Integer> poziadavky;
    ArrayList<Vozidlo> vozidla;
    ArrayList<String> adresy;
    int sumPoziadaviek;
    int[][] dMatrix;

    /**
     * konstruktor
     */
    public Optimalizacia(OdpadoveHospodarstvo oh) {
        this.oh = oh;

    }

    /**
     * Setter na optimalizaciu
     *
     * @param stojiska
     * @param poziadavky
     * @param adresy
     * @param vozidla
     * @param sum
     * @param dMatrix
     * @param oh
     */
    public void setOptimalizacia(ArrayList<Integer> stojiska, ArrayList<Integer> poziadavky, ArrayList<String> adresy,
            ArrayList<Vozidlo> vozidla, int sum, int[][] dMatrix) {
        this.stojiska = stojiska;
        this.poziadavky = poziadavky;
        this.adresy = adresy;
        this.vozidla = vozidla;
        this.sumPoziadaviek = sum;
        this.dMatrix = dMatrix;

//        Graf.printMatrix(dMatrix);
//        System.out.println("");
    }

    /**
     * Algoritmus optimalizacie
     *
     * @param nazovOdpadu
     * @return
     */
    public VysledokOptimalizacie optimalizuj(String nazovOdpadu) {
//        Graf.printMatrix(this.dMatrix);
//        System.out.println("Stojiska:\n" + Arrays.toString(stojiska.toArray()));
//        System.out.println("Poziadavky:\n" + Arrays.toString(poziadavky.toArray()));
//        System.out.println("Vozidla:\n" + Arrays.toString(vozidla.toArray()));
//        System.out.println("stojiska: "+stojiska.size());
        System.out.println(sumPoziadaviek + "?" + vozidla.get(0).getKapacita());
        if (stojiska.size() <= 14 /*20 12*/ && sumPoziadaviek < vozidla.get(0).getKapacita()) { // pouzi VetvyHranice
//            System.out.println("Pociatocne MVH");
            //mvh = new MetodaVetievAHranic(this.dMatrix);
            this.aco = new ACO(this.dMatrix, 100, 1000);
            aco.spustiACO();
            mvh = new MVH(dMatrix, Integer.MAX_VALUE);
//            long start;
//            long end;
//            float elapsedTimeInSec;
//            System.out.println("Meranie rychlosti: ");
//            start = System.currentTimeMillis();
            //int celkoveNaklady = mvh.startGenAlg();
            int celkoveNaklady = mvh.rataj();
            Stack<Integer> cestaPom = mvh.vratNajlepsiuCestu();
//            System.out.println("Vysledok opacny: " + Arrays.toString(cestaPom.toArray()));
//            System.out.println("UF: " + celkoveNaklady);
//            end = System.currentTimeMillis();
//            elapsedTimeInSec = (end - start) / 1000F;
//            System.out.println(elapsedTimeInSec + " seconds");

            int st;
            Trasa trasa = new Trasa(vozidla.get(0));
            trasa.setDruhOdpadu(nazovOdpadu);
            ArrayList<Zakaznik> z = new ArrayList<>();
            ArrayList<Integer> test = new ArrayList<>();
            while (!cestaPom.isEmpty()) {
                st = cestaPom.pop();
                z.add(new Zakaznik(stojiska.get(st), poziadavky.get(st), adresy.get(st)));
                test.add(st);
            }
//            System.out.println("Vysledok spravny: " + Arrays.toString(test.toArray()));
            int sum = 0;
            for (int i = 1; i < test.size(); i++) {
//                System.out.println("x[" + test.get(i - 1) + "," + test.get(i) + "] " + dMatrix[test.get(i - 1)][test.get(i)]);
                sum += dMatrix[test.get(i - 1)][test.get(i)];
            }
//            System.out.println("otestovany vysl: " + sum);
            trasa.setZakazniciTrasy(z);
            trasa.setNaklady(celkoveNaklady);
            trasa.setAktualnaKapacitaJazdy(sumPoziadaviek);
            ArrayList<Trasa> Vyslednetrasy = new ArrayList<>();
            Vyslednetrasy.add(trasa);
            return new VysledokOptimalizacie("MVaH", Vyslednetrasy, sumPoziadaviek, celkoveNaklady);
        } else if (stojiska.size() > 14 && sumPoziadaviek < vozidla.get(0).getKapacita()) { //pouzi ACO
            Graf.printMatrix(this.dMatrix);
            aco = new ACO(this.dMatrix, 100, 100);
            aco.spustiACO();
            int[] cesta = aco.getVysledok().getSpravnePoradnie(0);
//            System.out.println("Vysledok opacny: " + Arrays.toString(cesta));
            Trasa trasa = new Trasa(vozidla.get(0));
            trasa.setDruhOdpadu(nazovOdpadu);
            ArrayList<Zakaznik> z = new ArrayList<>();
            for (int i : cesta) {
                z.add(new Zakaznik(stojiska.get(i), poziadavky.get(i), adresy.get(i)));
            }
            trasa.setZakazniciTrasy(z);
            trasa.setNaklady(aco.getVysledok().getNaklady());
            trasa.setAktualnaKapacitaJazdy(sumPoziadaviek);
            ArrayList<Trasa> Vyslednetrasy = new ArrayList<>();
            Vyslednetrasy.add(trasa);
            return new VysledokOptimalizacie("ACO", Vyslednetrasy, sumPoziadaviek, aco.getVysledok().getNaklady());
        } else { //pouzi CW
            Stack<Integer> cesta;
            cw = new ClarkWright(new VRUloha(dMatrix, poziadavky, vozidla));
            ArrayList<Zakaznik> neobsluzeny = cw.ratajCW();
            if (neobsluzeny != null) {
                for (Zakaznik zakaznik : neobsluzeny) {
                    zakaznik.setId_zakaznika(this.stojiska.get(zakaznik.getId_zakaznika()));
                }
            }
//          System.out.println("CW zbehol");
            ArrayList<Trasa> vysledneTrasy = new ArrayList<>();
            Zakaznik depo = new Zakaznik(0, Integer.MAX_VALUE, "Zberný dvor");
            for (Trasa trasa : cw.getTrasy()) {
                trasa.setDruhOdpadu(nazovOdpadu);
//                System.out.println("Pocet zakaznikov na trase: " + trasa.getZakazniciTrasy().size());
                if (trasa.getZakazniciTrasy().size() > 14/* || trasa.getZakazniciTrasy().size() < 3*/) { // bez dooptimalizacie
                    ArrayList<Integer> ideckaNaDoOptimalizaciu = new ArrayList<>();
                    ArrayList<Zakaznik> zak = new ArrayList<>();
                    zak.add(depo);
                    ideckaNaDoOptimalizaciu.add(0);
                    for (Zakaznik zakaznik : trasa.getZakazniciTrasy()) {
                        zak.add(new Zakaznik(this.stojiska.get(zakaznik.getId_zakaznika()), this.poziadavky.get(zakaznik.getId_zakaznika()), this.adresy.get(zakaznik.getId_zakaznika())));
                        ideckaNaDoOptimalizaciu.add(this.stojiska.get(zakaznik.getId_zakaznika()));
                    }
                    zak.add(depo);
                    Trasa trasaSOrigosID = new Trasa(trasa.getVozidlo(), zak, trasa.getAktualnaKapacitaJazdy(), nakladyTrasy(zak));
                    trasaSOrigosID.setDruhOdpadu(nazovOdpadu);
//                    System.out.println("Idecka: "+Arrays.toString(ideckaNaDoOptimalizaciu.toArray())
//                            +"size:"+ideckaNaDoOptimalizaciu.size());
                    int[][] matica = oh.getGraph().getSubMatrixFromID(ideckaNaDoOptimalizaciu);
//                    Graf.printMatrix(matica); 
                    aco = new ACO(matica, 100, 100);
                    aco.spustiACO();
                    int hodnota = aco.getVysledok().getNaklady();
                    int[] c = aco.getVysledok().getSpravnePoradnie(0);
//                    System.out.println("C:"+Arrays.toString(c)+"size: "+c.length);                            
                    ArrayList<Integer> zoptimalizovane = new ArrayList<>();
                    for (int i : c) {
                        zoptimalizovane.add(ideckaNaDoOptimalizaciu.get(i));
                    }
                    trasaSOrigosID.setAlternativneData(zoptimalizovane, hodnota);
                    vysledneTrasy.add(trasaSOrigosID);

                } else { //s optimalizaciou MVH
                    //MetodaVetievAHranic pomMVH;
                    ArrayList<Integer> ideckaNaDoOptimalizaciu = new ArrayList<>();
                    ArrayList<Zakaznik> zak = new ArrayList<>();
                    zak.add(depo);
                    ideckaNaDoOptimalizaciu.add(0);
                    for (Zakaznik zakaznik : trasa.getZakazniciTrasy()) {
                        zak.add(new Zakaznik(this.stojiska.get(zakaznik.getId_zakaznika()), this.poziadavky.get(zakaznik.getId_zakaznika()), this.adresy.get(zakaznik.getId_zakaznika())));
                        ideckaNaDoOptimalizaciu.add(this.stojiska.get(zakaznik.getId_zakaznika()));
                    }
                    zak.add(depo);
                    Trasa trasaSOrigosID = new Trasa(trasa.getVozidlo(), zak, trasa.getAktualnaKapacitaJazdy(), nakladyTrasy(zak));
                    trasaSOrigosID.setDruhOdpadu(nazovOdpadu);
                    int[][] matica = oh.getGraph().getSubMatrixFromID(ideckaNaDoOptimalizaciu);
//                    System.out.println("tatoo:");
//                    Graf.printMatrix(matica);
//                    System.out.println("tatoo:");
                    mvh = new MVH(matica, Integer.MAX_VALUE);
                    int hodnota = mvh.rataj();
                    cesta = mvh.vratNajlepsiuCestu();
                    ArrayList<Integer> zoptimalizovane = new ArrayList<>();
                    int st;
                    while (!cesta.isEmpty()) {
                        st = cesta.pop();
                        zoptimalizovane.add(ideckaNaDoOptimalizaciu.get(st));
                    }
                    trasaSOrigosID.setAlternativneData(zoptimalizovane, hodnota);
                    vysledneTrasy.add(trasaSOrigosID);
//                    System.out.println("*****");
//                    System.out.println("*****");
                }
            }
            return new VysledokOptimalizacie("ClarkWright", vysledneTrasy, sumPoziadaviek, cw.getNaklady(), neobsluzeny);
        }
    }

    /**
     * Vyratanie celkovych nakladov
     *
     * @param zakaznici
     * @return
     */
    public int nakladyTrasy(ArrayList<Zakaznik> zakaznici) {
        //System.out.println("TTT:\n" + Arrays.toString(zakaznici.toArray()));
        int sumJednotlivejTrasy = 0;
        int index1 = zakaznici.get(0).getId_zakaznika();
        int index2 = 0;
        for (int i = 1; i < zakaznici.size(); i++) {
            index2 = zakaznici.get(i).getId_zakaznika();
            //System.out.println("[" + index1 + "," + index2 + "]");
            sumJednotlivejTrasy += oh.getGraph().getid_Dmatrix()[index1][index2];
            index1 = index2;
        }
        return sumJednotlivejTrasy;
    }

    public int[] optimalizujPMedian(LinkedList<Suradnice> umiestneniaCentier, LinkedList<Suradnice> zakaznici, LinkedList<Integer> poziadavky, int pocetCentier) {
        int[] bestSol = new int[pocetCentier];
        long best = Long.MAX_VALUE;
//        long worst = Long.MIN_VALUE;
//        long priemer = 0;
        PMedian pm = new PMedian(pocetCentier, umiestneniaCentier, zakaznici, poziadavky);
//        Graf.printMatrix(pm.getDmatrix());
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
//                System.out.println("Rep:" + i);
                Refresher.r.refreshProgresBar(i);
            }
            genAlg = new GenAlg(pm);
            genAlg.startGenAlg();
//            priemer += genAlg.getNajlepsiaUF();
            if (best > genAlg.getNajlepsiaUF()) {
                best = genAlg.getNajlepsiaUF();
                bestSol = genAlg.getVybraneUzly().clone();
            }
//            if (worst < genAlg.getNajlepsiaUF()) {
//                worst = genAlg.getNajlepsiaUF();
//            }
        }
//        System.out.println("Priemer: " + (priemer / 100.0));
//        System.out.println("Best: " + (best));
//        System.out.println("Worst: " + (worst));
        return bestSol;
    }

}
