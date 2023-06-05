/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CW;


import java.util.ArrayList;
import java.util.Collections;


/**
 * Trieda reprezentujuca Clark Wright algoritmus
 *
 * @author rendo
 */
public class ClarkWright {

    private VRUloha uloha;
    private ArrayList<Dvojica> dvojice;
    private ArrayList<Zakaznik> zakaznici;
    private ArrayList<Trasa> trasy;

    /**
     * Konštrutor
     *
     * @param uloha - uloha okruznych jazd
     */
    public ClarkWright(VRUloha uloha) {
        this.uloha = uloha;
        this.dvojice = new ArrayList<>();
        this.zakaznici = new ArrayList<>();

        for (int i = 0; i < uloha.getPoziadavky().size(); i++) {
            this.zakaznici.add(new Zakaznik(i + 1, uloha.getPoziadavky().get(i)));
        }
        Collections.sort(this.zakaznici);
//        System.out.println("Zakaznici: " + Arrays.toString(zakaznici.toArray()));
        this.trasy = new ArrayList<>();
    }

    /**
     * Algoritmus SW
     *
     * @return - arrylist neobsluzenych zakaznikov
     */
    public ArrayList<Zakaznik> ratajCW() {
        Dvojica d;
        Zakaznik pomZ;

        // 0 Inicializacia
        Collections.sort(uloha.getVozidla());
//        System.out.println("Vozidla: " + Arrays.toString(uloha.getVozidla().toArray()));
//        int p[][] = new int[uloha.getPoziadavky().size()][uloha.getPoziadavky().size()];
//        for (int i = 0; i < uloha.getPoziadavky().size(); i++) {
//            for (int j = 0; j < uloha.getPoziadavky().size(); j++) {
//                if (i == j) {
//                    p[i][j] = 0;
//                } else {
//                    int dis = uloha.getDmatrix()[i + 1][0];
//                    int dsj = uloha.getDmatrix()[0][j + 1];
//                    int dij = uloha.getDmatrix()[i + 1][j + 1];
//                    p[i][j] = (dis + dsj) - dij;
//                }
//
//            }
//        }
//        System.out.println("");
//        for (int i = 0; i < p.length; i++) {
//            System.out.println(Arrays.toString(p[i]));
//        }

        for (int i = 0; i < uloha.getPoziadavky().size(); i++) {
            for (int j = 0; j < uloha.getPoziadavky().size(); j++) { //i+1
                if (i == j) {
                    continue;
                }
                d = new Dvojica(i + 1, j + 1);
                int dis = uloha.getDmatrix()[i + 1][0];
                int dsj = uloha.getDmatrix()[0][j + 1];
                int dij = uloha.getDmatrix()[i + 1][j + 1];

                if (dis + dsj > dij) {
                    d.vyratajUsporu(dis, dsj, dij);
                    dvojice.add(d);
                }
            }
        }

//        System.out.println("Dvojice: " + Arrays.toString(dvojice.toArray()));
        Collections.sort(dvojice);
//        System.out.println("Usporiadane Dvojice: " + Arrays.toString(dvojice.toArray()));
//        System.out.println("");

        // 1 inicializacia novej trasy  
        Trasa aktualnaTrasa = null;
        krok1:
        while (zakaznici.size() > 0) {
            if (uloha.getVozidla().isEmpty()) {
                //System.out.println("Hotovo ale neuspesne nestacili auta");
                //System.out.println("Trasa: " + Arrays.toString(trasy.toArray()));
                return zakaznici;
            }
            Vozidlo vozidlo = uloha.getVozidla().remove(0);
//            System.out.println("Vyberam: " + vozidlo.toString());
            aktualnaTrasa = new Trasa(vozidlo);
            trasy.add(aktualnaTrasa);

            //2 inicializacia novej jazdy  
            pomZ = null;
            for (Zakaznik zakaznik : zakaznici) {
                if (zakaznik.getPoziadavka() <= aktualnaTrasa.getVozidlo().getKapacita()) {
                    pomZ = zakaznik;
                    break;
                }
            }
            if (pomZ != null) {
                zakaznici.remove(pomZ);
                aktualnaTrasa.pridajZakaznikaNaKoniec(pomZ);
            } else {
                trasy.add(aktualnaTrasa);
                continue krok1;
            }

            //krok3
            while (!dvojice.isEmpty()) {
                d = null;
//                System.out.println("Aktualna trasa: " + Arrays.toString(aktualnaTrasa.getZakazniciTrasy().toArray()));
                for (Dvojica dvojica : dvojice) {
//                    System.out.println("Dvojica(" + dvojica.getIndex1() + "," + dvojica.getIndex2() + ")");
                    if (aktualnaTrasa.getZakazniciTrasy().size() == 1) {
                        if (dvojica.index1 == aktualnaTrasa.getZakazniciTrasy().get(0).getId_zakaznika()
                                || dvojica.index2 == aktualnaTrasa.getZakazniciTrasy().get(0).getId_zakaznika()) {
                            d = dvojica;
//                            System.out.println("Vybrana Dvojica(" + d.getIndex1() + "," + d.getIndex2() + ")");
                            break;
                        }
                    } else {
                        if (aktualnaTrasa.getZakazniciTrasy().get(0).getId_zakaznika() == dvojica.index2) {
                            d = dvojica;
//                            System.out.println("Vybrana Dvojica(" + d.getIndex1() + "," + d.getIndex2() + ")");
                            break;
                        }
                        if (aktualnaTrasa.getZakazniciTrasy().get(aktualnaTrasa.getZakazniciTrasy().size() - 1).getId_zakaznika() == dvojica.index1) {
                            d = dvojica;
//                            System.out.println("Vybrana Dvojica(" + d.getIndex1() + "," + d.getIndex2() + ")");
                            break;
                        }
                    }
                }

                pomZ = null;
                if (d == null) { //nie je taky Vij malo by to hned skocin na hotovo uspesne boli obsluzeny vsetci zakaznici
                    continue krok1;
                } else {
                    dvojice.remove(d);
                    for (Zakaznik zakaznik : zakaznici) {
                        if (zakaznik.getId_zakaznika() == d.index1 || zakaznik.getId_zakaznika() == d.index2) {
                            pomZ = zakaznik;
//                            System.out.println("Zakaznik: " + pomZ.toString());
                            break;
                        }
                    }
                    if (pomZ.getPoziadavka() + aktualnaTrasa.getAktualnaKapacitaJazdy() <= aktualnaTrasa.getVozidlo().getKapacita()) {
                        zakaznici.remove(pomZ);
                        boolean spajanieCezPrveho = false;
                        if (d.index2 == aktualnaTrasa.getZakazniciTrasy().get(0).getId_zakaznika()) {
                            //spajam cez prveho
                            aktualnaTrasa.pridajZakaznikaNaZaciatok(pomZ);
                            spajanieCezPrveho = true;
                        } else if (d.index1 == aktualnaTrasa.getZakazniciTrasy().get(aktualnaTrasa.getZakazniciTrasy().size() - 1).getId_zakaznika()) {
                            //spajam cez posledneho
                            aktualnaTrasa.pridajZakaznikaNaKoniec(pomZ);
                        } else {
                            System.out.println("Problem, toto nesmie nastat !");
                        }

                        ArrayList<Dvojica> dvojicePonechane = new ArrayList<>();
                        for (Dvojica dvojica1 : dvojice) {
                            if (dvojica1.index1 == pomZ.getId_zakaznika()) {
                                continue;
                            }
                            if (dvojica1.index2 == d.index2) {
                                continue;
                            }
                            if (dvojica1.index1 == d.index2) {
                                continue;
                            }
                            dvojicePonechane.add(dvojica1);
                        }
                        dvojice = dvojicePonechane;
//                        System.out.println("Po Vyhadzani: " + Arrays.toString(dvojice.toArray()));
//                        System.out.println("");
                    }
                }
            }
            //System.out.println("Dvojice su prazdne");
        }

//        System.out.println("hotovo uspesne boli obsluzeny vsetci zakaznici");
        return null;
    }

    /**
     * Getter vyratanych tras
     *
     * @return
     */
    public ArrayList<Trasa> getTrasy() {
        return trasy;
    }
    /**
     * Vrati hodnotu nakladov vsetkych tras
     * @return 
     */
    public int getNaklady() {
        int sum = 0;
        int sumJednotlivejTrasy = 0;
        for (Trasa trasa : trasy) {
            sumJednotlivejTrasy = 0;
            int index1 = 0;
            int index2 = 0;
            for (int i = 0; i < trasa.getZakazniciTrasy().size(); i++) {
                index2 = trasa.getZakazniciTrasy().get(i).getId_zakaznika();
                sumJednotlivejTrasy += uloha.getDmatrix()[index1][index2];
                index1 = index2;
            }
            sum += (sumJednotlivejTrasy + uloha.getDmatrix()[index1][0]);
        }
        return sum;
    }

}
