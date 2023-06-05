package ACO;

import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *Trieda mravec reprezentuje spravanie sa mravca
 * @author rendo
 */
public class Mravec {

    private boolean[] navstivene;
    protected int cesta[];
    protected int naklady;
    private Prostredie prostredie;
    private Random rnd;
    
  /**
   * Konštruktor mravca
   * @param p - prostredie
   */
    public Mravec(Prostredie p) {
        this.prostredie = p;
        this.navstivene = new boolean[p.dMatrix.length];
        this.cesta = new int[prostredie.dMatrix.length + 1];
        Random r = new Random();
        rnd = new Random(r.nextLong());
    }
    /**
     * Presun mravca na najblizsi nezaradeny uzol
     * @param aktualnaPozicia 
     */
    public void chodKNajblizsiemu(int aktualnaPozicia) {
        int aktualIndex = cesta[aktualnaPozicia - 1];
        int minVzd = Integer.MAX_VALUE;
        int dalsiIndex = -1;
        for (int i = 0; i < prostredie.dMatrix.length; i++) {
            if (navstivene[i] == false && minVzd > prostredie.dMatrix[aktualIndex][i]) {
                dalsiIndex = i;
                minVzd = prostredie.dMatrix[aktualIndex][i];
            }
        }
        navstivene[dalsiIndex] = true;
        cesta[aktualnaPozicia] = dalsiIndex;

    }
    /**
     * Vyratanie dlzky cesty, ktoru mravec absolvoval
     */
    public void vyratajCestu() {
        int sum = 0;
//        System.out.println("Cesta: "+Arrays.toString(cesta));
        for (int i = 0; i < cesta.length - 1; i++) {
//           System.out.println("D"+cesta[i]+","+cesta[i+1]+": "+prostredie.getDmatrix()[cesta[i]][cesta[i+1]]);
            sum += prostredie.dMatrix[cesta[i]][cesta[i + 1]];
        }
//        System.out.println(sum + " Cesta: "+Arrays.toString(cesta));
        this.naklady = sum;
    }
    
    /**
     * Uskutocnenie cesty cez najblizsie nezaradene uzly
     * @return 
     */
    public int getCestaNajblizsichSusedov() {
        vycistiNavstivene();
        cesta[0] = prostredie.vygenerujStarovacieId();
        navstivene[cesta[0]] = true;
        int i = 1;
        while (i < prostredie.dMatrix.length) {
            chodKNajblizsiemu(i);
            i++;
//            System.out.println("Cesta: "+Arrays.toString(cesta));
        }
        cesta[cesta.length - 1] = cesta[0];
//        System.out.println("Cesta: " + Arrays.toString(cesta));
        vyratajCestu();
        vycistiNavstivene();
        return naklady;
    }
/**
 * Presun mravca na dalsi uzol podla pravidiel o rozhodovani 
 * @param aktualnaPozicia 
 */
    public void chodKNajlepsiemu(int aktualnaPozicia) {
        int aktualIndex = cesta[aktualnaPozicia - 1];
        int dalsiIndex = -1;
        double najlepsia = Double.MIN_VALUE;
        for (int i = 0; i < prostredie.pocetNajblizsichSusedov; i++) {
            if ((navstivene[prostredie.susedneUzly[aktualIndex][i]] == false) 
                    && (najlepsia < prostredie.rMatrix[aktualIndex][prostredie.susedneUzly[aktualIndex][i]])) {
                najlepsia = prostredie.rMatrix[aktualIndex][prostredie.susedneUzly[aktualIndex][i]];
                dalsiIndex = prostredie.susedneUzly[aktualIndex][i];
            }
        }
//        System.out.println("DalsiIndex: " + dalsiIndex);
        if (dalsiIndex == -1) {
            chodKNajblizsiemu(aktualnaPozicia);
        } else {
            navstivene[aktualnaPozicia] = true;
            cesta[aktualnaPozicia] = dalsiIndex;
        }
    }

/**
 * Algoritmus rozhodovania o vybere dalsich uzlov
 * @param aktualnaPozicia 
 */    
    public void chodPodlaPravidiel(int aktualnaPozicia) {
        int aktualIndex = cesta[aktualnaPozicia - 1];
        double[] pravdepodobnosti = new double[prostredie.pocetNajblizsichSusedov + 1];
        double celkovaPP = 0;
        for (int i = 0; i < prostredie.pocetNajblizsichSusedov; i++) {
            if (navstivene[prostredie.susedneUzly[aktualIndex][i]]) {
                pravdepodobnosti[i] = 0;
            } else {
                pravdepodobnosti[i] = prostredie.rMatrix[aktualIndex][prostredie.susedneUzly[aktualIndex][i]];
                celkovaPP += pravdepodobnosti[i];
            }
        }
        //System.out.println("CelkovaPP: "+celkovaPP);
        if (celkovaPP <= 0) {
            //System.out.println("Idem k najblizsiemu");
            chodKNajblizsiemu(aktualnaPozicia);
        } else {
            //System.out.println("Idem k najlepsiemu");
            int i = 0;
            double r = celkovaPP*rnd.nextDouble();
            double p = pravdepodobnosti[0];
            while (p <= r) {              
                i++;
                p += pravdepodobnosti[i];
            }
            if (i == prostredie.pocetNajblizsichSusedov) {
                chodKNajlepsiemu(aktualnaPozicia);
                return;
            }           
            cesta[aktualnaPozicia] = prostredie.susedneUzly[aktualIndex][i];
            navstivene[cesta[aktualnaPozicia]]=true;
        }
    }

    /**
     * Nasetetovanie navstivenych uzlov na false
     */
    public void vycistiNavstivene() {
        for (int i = 0; i < navstivene.length; i++) {
            navstivene[i] = false;            
        }
        
    }
    /**
     * Nastavenie mravca do pociatocneho uzla
     * @param pozicia 
     */
    public void setZaciatokMravca(int pozicia) {
        cesta[0] = pozicia;
        navstivene[pozicia] = true;
    }
    /**
     * uzavretie cesty mravca, vypocitanie nakladov
     */
    public void setKoniecMravca() {
        cesta[cesta.length - 1] = cesta[0];
        vyratajCestu();
        vycistiNavstivene();
    }
    /**
     * Metoda na vypis cesty mravca
     * @return 
     */
    @Override
    public String toString() {
        return Arrays.toString(cesta);
    }

}
