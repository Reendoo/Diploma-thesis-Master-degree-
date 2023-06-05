package ACO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Trieda reprezentujuca prostredie ulohy
 * @author rendo
 */
public class Prostredie {

    class Id implements Comparable<Id> {

        private int id;
        private int d;

        public Id(int id, int d) {
            this.id = id;
            this.d = d;
        }

        public int getId() {
            return id;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        @Override
        public int compareTo(Id t) {
            return Integer.compare(this.getD(), t.getD());
        }

        @Override
        public String toString() {
            return "Id{" + "id=" + id + ", d=" + d + '}';
        }

    }
    // konstanty
    public static double ro = 0.5;
    public static double alfa = 1.0;
    public static double beta = 2.0;
    
    //matice
    protected final int[][] dMatrix;   
    private double[][] fMatrix;
    protected double[][] rMatrix;
    protected int[][] susedneUzly;
    
    private Mravec[] mravce;
    private final Random rnd;    
    protected int pocetNajblizsichSusedov;
    
    /**
     * Konštruktor prostredia
     * @param dMatrix - matica vzdialenosti
     * @param seed - nasade pre generator
     * @param pocetMravcov 
     */
    public Prostredie(int[][] dMatrix, long seed, int pocetMravcov) {
        rnd = new Random(seed);
        this.dMatrix = dMatrix;
//         for (int[] arr : dMatrix) {
//            System.out.println(Arrays.toString(arr));
//        }
        pripravMravce(pocetMravcov);
        pripravProstredie();
        preratajRMatrix();
        pocetNajblizsichSusedov = (int)dMatrix.length/2;
        pripravSusedov();

    }
    /**
     * Inicializacia prostredia matice feromonov, a matice pre rozhodovanie
     */
    public void pripravProstredie() {
        fMatrix = new double[dMatrix.length][dMatrix.length];
        rMatrix = new double[dMatrix.length][dMatrix.length];
        double pociatocnaHladinaF = (1.0 / (ro * mravce[0].getCestaNajblizsichSusedov()));
        for (int i = 0; i < dMatrix.length; i++) {
            for (int j = 0; j < dMatrix.length; j++) {
                fMatrix[i][j] = pociatocnaHladinaF;
                rMatrix[i][j] = pociatocnaHladinaF;
            }
        }
//        System.out.println("RMatrix: ");
//        for (double[] arr : rMatrix) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println("FMatrix: ");
//        for (double[] arr : fMatrix) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println("");
    }
    /**
     * Preratanie hodnot matice pre rozhodovanie
     */
    public void preratajRMatrix() {
        for (int i = 0; i < dMatrix.length; i++) {
            for (int j = 0; j < dMatrix.length; j++) {
                rMatrix[i][j] = Math.pow(fMatrix[i][j], alfa) * 
                        Math.pow((1.0 / (dMatrix[i][j] + 0.1)), beta);
            }
        }
//        System.out.println("RMatrix: ");
//        for (double[] arr : rMatrix) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println("");
    }
    /**
     * Vytvorenie mravcov
     * @param pocetMravcov 
     */
    public void pripravMravce(int pocetMravcov) {
        mravce = new Mravec[pocetMravcov];
        for (int i = 0; i < pocetMravcov; i++) {
            mravce[i] = new Mravec(this);
        }
    }
    
    /**
     * Inicializacia matice susedov, pre uzly sa v matici az do vycerpania sa pouziva pravidlo pre prechody.... ak sa vycerpaju pouziva sa prechod k najblizsiemu nezaradenemu uzlu
     */
    public void pripravSusedov() {
        susedneUzly = new int[dMatrix.length][pocetNajblizsichSusedov];
        ArrayList<Id> id;
        for (int i = 0; i < dMatrix.length; i++) {
            id = new ArrayList<>();
            for (int j = 0; j < dMatrix.length; j++) {
                id.add(new Id(j, dMatrix[i][j]));
            }
            Collections.sort(id);
//            System.out.println(Arrays.toString(id.toArray()));

            for (int j = 0; j < pocetNajblizsichSusedov; j++) {
                susedneUzly[i][j] = id.get(j + 1).getId();
            }
        }
//        System.out.println("Susedne: ");
//        for (int[] arr : susedneUzly) {
//            System.out.println(Arrays.toString(arr));
//        }
//        System.out.println("");
    }

/**
 * Aktualizacia matice feromonov
 */
    public void updateFMatrix() {
        vyprchanie();
        for (Mravec m : mravce) {
            aplijkujFeromony(m);
        }
        preratajRMatrix();
    }
/**
 * Vyprchanie fermonov
 */
    public void vyprchanie() {
        for (int i = 0; i < dMatrix.length; i++) {
            for (int j = 0; j < dMatrix.length; j++) {
                fMatrix[i][j] = ((1 - ro) * fMatrix[i][j]);
            }
        }
    }
/**
 * Aplikacia femeromonovej stopy mravca
 * @param mravec 
 */
    public void aplijkujFeromony(Mravec mravec) {
        double f = 1.0 / mravec.naklady;
//        System.out.println("Cesta: "+Arrays.toString(mravec.getCesta()));
        for (int i = 0; i < mravec.cesta.length-1; i++) {
//            System.out.println("D"+mravec.getCesta()[i]+","+mravec.getCesta()[i+1]);
            int i1 = mravec.cesta[i];
            int i2 = mravec.cesta[i + 1];
            fMatrix[i1][i2] = fMatrix[i1][i2] + f;
        }
    }
/**
 * Spravenie jednej replikacie ACO
 */
    public void urobReplikaciu() {
//        int q = 1;
//        System.out.println("Inicializačný: ");
//        System.out.println("M[" + q + "]:" + mravce[0].toString());        
//        System.out.println("Zaciatok: ");
        for (Mravec m : mravce) {
            m.vycistiNavstivene();
            m.setZaciatokMravca(vygenerujStarovacieId());
//            System.out.println("M[" + q + "]:" + m.toString());
//            q++;
        }
//        System.out.println("******************");

        for (int i = 1; i < dMatrix.length; i++) {
//            int w = 1;
//            System.out.println("Pozicia: "+i);
            for (Mravec m : mravce) {
                m.chodPodlaPravidiel(i);
//                System.out.println("M[" + w + "]:" + m.toString());
//                w++;
            }
        }
//        System.out.println("******************");
        for (Mravec m : mravce) {
            m.setKoniecMravca();
        }
//System.out.println("*****KONIEC RIESENIA********");
    }
/**
 * Generacia id startovacie uzla
 * @return 
 */
    public int vygenerujStarovacieId() {
        return rnd.nextInt(dMatrix.length);
    }

/**
 * Vyber mravvca s najlepsim riesenim
 * @return 
 */
    public Mravec getNajlepsi(){
        int naklady= Integer.MAX_VALUE;
        Mravec m = null;
        for (Mravec mravec : mravce) {
            if(mravec.naklady<naklady){
                naklady=mravec.naklady;
                m=mravec;
            }
        }
        return m;
    }
  

}
