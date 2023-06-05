/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvh;


import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Trieda reprezentujuca MVH
 *
 * @author rendo
 */
public class MVH {

    private PriorityQueue<Vrchol> PQ = new PriorityQueue<>();
    private int[][] maticaVzdialenosti;
    private int najmensieNaklady = Integer.MAX_VALUE;
    private Vrchol poslednyVrchol;

    /**
     * Konstrutor
     *
     * @param maticaVzdialenosti - mtica vzdialenosti
     * @param najmesieNaklady -horna hranica
     */
    public MVH(int[][] maticaVzdialenosti, int najmesieNaklady) {
        for (int i = 0; i < maticaVzdialenosti.length; i++) {
            maticaVzdialenosti[i][i] = Integer.MAX_VALUE;
        }
        this.maticaVzdialenosti = maticaVzdialenosti;
        this.najmensieNaklady = (int) (najmesieNaklady * 1.2);
//        System.out.println("Najmesie naklady: " + this.najmensieNaklady);
//        vypisGraf(this.maticaVzdialenosti);
//        System.out.println("");
    }

    /**
     * Spustenie algoritmu pridanim korena do stromu
     *
     * @return
     */
    public int rataj() {
        PQ.add(new Vrchol(maticaVzdialenosti));
        MetodaVetievHranic();
        return this.najmensieNaklady;
    }

    /**
     * Algoritmus MVH
     */
    private void MetodaVetievHranic() {
//        boolean trebaCistit = true;
        Vrchol vrchol = null;
        Vrchol novy = null;
        while (!PQ.isEmpty()) {
            vrchol = PQ.remove();
            //System.out.println("Vyberam: U:" +(vrchol.getId_uzla()+1) + " V:" + vrchol.getId_vrchola());
            if (najmensieNaklady <= vrchol.redukovanaCena) {
                return;
            }
            if (vrchol.redukovanaCena < najmensieNaklady
                    && vrchol.pocetNavstivenychUzlov == maticaVzdialenosti.length) {
                najmensieNaklady = vrchol.redukovanaCena;
                poslednyVrchol = vrchol;
//                if (trebaCistit) {
//                    vycistiPQ();
//                    trebaCistit = false;
//                }
            }

            for (int j = 0; j < maticaVzdialenosti.length; j++) {
                if (maticaVzdialenosti[vrchol.id_uzla][j] > 0
                        && vrchol.navstiveneUzly[j] == false) {
                    novy = new Vrchol(j, vrchol);
//                    if (novy.redukovanaCena < najmensieNaklady) { // mozno bude treba odobrat
                    //System.out.println("Vkladam: U:" + (novy.getId_uzla()+1)+ " V:" + novy.getId_vrchola());
                    PQ.add(novy);
//                    }
                }
            }
        }
    }

    /**
     * Metoda spatnym prehladanim stromu vrati hladanu najkratsiu cestu
     *
     * @return
     */
    public Stack<Integer> vratNajlepsiuCestu() {
        Stack<Integer> cesta = new Stack<>();
        cesta.push(0);
        while (poslednyVrchol.predchodca != null) {
            cesta.push(poslednyVrchol.id_uzla);
            poslednyVrchol = poslednyVrchol.predchodca;
        }
        cesta.push(poslednyVrchol.id_uzla);
        return cesta;
    }

//    public void vycistiPQ() {
//        LinkedList<Vrchol> naVyhodenie = new LinkedList<>();
//        System.out.println("Bolo: " + PQ.size());
//        for (Vrchol vrchol1 : PQ) {
//            if (vrchol1.getRedukovanaCena() > najmensieNaklady) {
//                naVyhodenie.add(vrchol1);
//            }
//        }
//        PQ.removeAll(naVyhodenie);
//        System.out.println("Vycistil som: " + naVyhodenie.size());
//        System.out.println("Ostalo: " + PQ.size());
//        naVyhodenie = null;
//    }
//    public static void main(String[] args) {
//        int[][] maticaVzdialenosti
//                = {{0, 20, 30, 10, 11},
//                {15, 0, 16, 4, 2},
//                {3, 5, 0, 2, 4},
//                {19, 6, 18, 0, 3},
//                {16, 4, 7, 16, 0}};
//        ACO aco = new ACO(maticaVzdialenosti, 100, 100);
//        aco.rataj();
//        MVH mvh = new MVH(maticaVzdialenosti, aco.getVysledok().getNaklady());
//        System.out.println("Cesta: " + mvh.rataj());
//        Stack<Integer> c = mvh.vratNajlepsiuCestu();
//        System.out.println("Riesenie: " + Arrays.toString(c.toArray()));
//        String s = "";
//        while (c.size() > 0) {
//            s += c.pop() + ",";
//        }
//        System.out.println("Spravne poradie: " + s);
//    }

}
