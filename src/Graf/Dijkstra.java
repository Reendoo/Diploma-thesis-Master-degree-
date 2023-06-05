/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *Trieda sluziaca na vyratania matice vzdialenosti 
 * je zalozena na Dijstrovom algoritme
 * @author rendo
 */
public class Dijkstra {

    private final List<Uzol> uzly;
    private final List<Hrana> hrany;
    private Set<Uzol> vlozeneUzly;
    private Set<Uzol> nevlozeneUzly;
    private Map<Uzol, Uzol> predchodcovia;
    private Map<Uzol, Integer> vzdialenosti;
    
    /**
     * Konštruktor triedy
     * @param graph 
     */
    public Dijkstra(Graf graph) {
        this.uzly = new ArrayList<Uzol>(graph.getUzly());
        this.hrany = new ArrayList<Hrana>(graph.getHrany());
    }

    /**
     * Vrat najblizsi uzol z mnoziny uzlov
     * @param uzlySet
     * @return
     */
    public Uzol vyberNajblizsi(Set<Uzol> uzlySet) {
        Uzol min = null;
        for (Uzol uzol : uzlySet) {
            if (min == null) {
                min = uzol;
            } else {
                if (getNajkrajtsiuVzdialenostKDestUzlu(uzol) < getNajkrajtsiuVzdialenostKDestUzlu(min)) {
                    min = uzol;
                }
            }
        }
        return min;
    }

    /**
     * Metóda vracia dlzku najkrajtsej cesty k dest uzlu
     *
     * @param destUzol
     * @return
     */
    public int getNajkrajtsiuVzdialenostKDestUzlu(Uzol destUzol) {
        Integer v = vzdialenosti.get(destUzol);
        if (v == null) {
            return Integer.MAX_VALUE;
        } else {
            return v;
        }
    }

    /**
     * Najdi a zapis najkrajsiu cestu uzlu
     *
     * @param uzol
     */
    public void najdiMinVzdialenost(Uzol uzol) {
        List<Uzol> susedia = getSusedneUzly(uzol);
        for (Uzol destUzol : susedia) {
            if (getNajkrajtsiuVzdialenostKDestUzlu(destUzol) > getNajkrajtsiuVzdialenostKDestUzlu(uzol) + getDlzkuHrany(uzol, destUzol)) {
                vzdialenosti.put(destUzol, getNajkrajtsiuVzdialenostKDestUzlu(uzol) + getDlzkuHrany(uzol, destUzol));
                predchodcovia.put(destUzol, uzol);
                nevlozeneUzly.add(destUzol);
            }
        }
    }

    /**
     * Metóda vracia susedne(incidentne) uzly k uzlu
     *
     * @param uzol
     * @return List susednych (incidentnych) uzlov
     */
    private List getSusedneUzly(Uzol uzol) {
        List<Uzol> susedia = new ArrayList<Uzol>();
        for (Hrana hrana : hrany) {
            if (hrana.srcUzol == uzol && !vlozeneUzly.contains(hrana.destUzol)) {
                susedia.add(hrana.destUzol);
            }
        }
        return susedia;
    }

    /**
     * Metoda vracia dlzku hrany, ktora zacina srcUzlom a konci destUzlom
     *
     * @param srcUzol
     * @param destUzol
     * @return dlzka hrany
     */
    public int getDlzkuHrany(Uzol srcUzol, Uzol destUzol) {
        for (Hrana hrana : hrany) {
            if (hrana.srcUzol == srcUzol && hrana.destUzol == destUzol) {
                return hrana.dlzka;
            }
        }
        throw new RuntimeException("nemoze nastatat");
    }

//    public boolean jeVlozeny(Uzol uzol) {
//        return vlozeneUzly.contains(uzol);
//    }

    public void vyrataj(Uzol srcUzol) {
        vlozeneUzly = new HashSet<Uzol>();
        nevlozeneUzly = new HashSet<Uzol>();
        predchodcovia = new HashMap<Uzol, Uzol>();
        vzdialenosti = new HashMap<Uzol, Integer>();
        vzdialenosti.put(srcUzol, 0);
        nevlozeneUzly.add(srcUzol);
        while (nevlozeneUzly.size() > 0) {
            Uzol tmpUzol = vyberNajblizsi(nevlozeneUzly);
            vlozeneUzly.add(tmpUzol);
            nevlozeneUzly.remove(tmpUzol);
            najdiMinVzdialenost(tmpUzol);
        }
    }

    public LinkedList<Uzol> cesta(Uzol destUzol) {
        LinkedList<Uzol> cesta = new LinkedList<Uzol>();
        Uzol tmpUzol = destUzol;
        if (predchodcovia.get(tmpUzol) == null) {
            return null;
        }
        cesta.add(tmpUzol);
        while (predchodcovia.get(tmpUzol) != null) {
            tmpUzol = predchodcovia.get(tmpUzol);
            cesta.add(tmpUzol);
        }
        Collections.reverse(cesta);
        return cesta;

    }

    public Matice createDistanceMatrix() {
        System.out.println("Robim cosi");
        int[][] dmatrix = new int[uzly.size()][uzly.size()];
        String[][] sdmatrixa = new String[uzly.size()][uzly.size()];
        int pom;
        for (int i = 0; i < dmatrix.length; i++) {
            vyrataj(uzly.get(i));
            for (int j = 0; j < dmatrix.length; j++) {
                if (i == j) {
                    dmatrix[i][j] = 0;
                    //System.out.println("siNaMieste");
                    sdmatrixa[i][j] = "siNaMieste";
                } else {
                    pom = getNajkrajtsiuVzdialenostKDestUzlu(uzly.get(j));
                    if (pom == Double.MAX_VALUE) {
                        dmatrix[i][j] = -1;
                        sdmatrixa[i][j] = "nedosiahnutelne";
                        //System.out.println("nedosiahnutelne");
                    } else {
                        String cesta = "";
                        dmatrix[i][j] = pom;

                        if (cesta(uzly.get(j)) !=null) {
                            for (Uzol uzol : cesta(uzly.get(j))) {
                                cesta += uzol.getId() + ",";
                            }
                            cesta = cesta.substring(0, cesta.length() - 1);
                            //System.out.println(cesta);
                            sdmatrixa[i][j] = new String(cesta);
                        }else{
                            sdmatrixa[i][j] = "cekni";
                        }
                    }
                }
            }

        }
        System.out.println("Hotovo");
        return new Matice(dmatrix, sdmatrixa);
    }
}
