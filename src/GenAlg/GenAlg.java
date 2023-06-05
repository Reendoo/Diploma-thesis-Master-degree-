/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenAlg;


import java.util.HashSet;
import java.util.Random;


/**
 *
 * @author rendo
 */
public class GenAlg {

    PMedian pMedian;
    int[] vybraneUzly;
    int[] nevybraneUzly;
    int velkostPopulacie;
    long[] hodnotyUceloviek;
    int[][] populacia;
    int[] poradie;
    int pocetCentier;
    
    Random rnd_prePrveriesenie;
    Random rnd_preGenerovanieJedinca;
    Random rnd_naPredavanieGenovPriKrizeni;
    Random rnd_budeMutacia;
    Random rnd_pocetMutovanychGenov;
    Random rnd_nagen;

    long najlepsiaUF;
    int dlzkaTrvaniavMin = 1;
    int pocetPopulacnychVymen = 10000;
    int pocetJedincovPreKrizenie = 100;

    double pravdepodobnostMutacie = 0.05;

    public GenAlg(PMedian pMedian) {
        Random rnd = new Random();
        rnd_prePrveriesenie = new Random(rnd.nextLong());
        rnd_preGenerovanieJedinca = new Random(rnd.nextLong());
        rnd_naPredavanieGenovPriKrizeni = new Random(rnd.nextLong());
        rnd_budeMutacia = new Random(rnd.nextLong());
        rnd_pocetMutovanychGenov = new Random(rnd.nextLong());
        rnd_nagen = new Random(rnd.nextLong());

        this.pMedian = pMedian;
        pocetCentier = pMedian.dmatrix.length;
        vybraneUzly = new int[pMedian.pocetUmiestnenychCentier];
        nevybraneUzly = new int[pocetCentier - pMedian.pocetUmiestnenychCentier];
        int[] idecka = new int[pocetCentier];
        for (int i = 0; i < idecka.length; i++) {
            idecka[i] = i;
        }
        int poslednyIndex = pocetCentier - 1;
        int vybranaHodnota;
        int vybranyIndex;
        for (int i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
            vybranyIndex = rnd_prePrveriesenie.nextInt(poslednyIndex);
            vybranaHodnota = idecka[vybranyIndex];
            idecka[vybranyIndex] = idecka[poslednyIndex];
            vybraneUzly[i] = idecka[poslednyIndex--] = vybranaHodnota;
        }
        System.arraycopy(idecka, 0, nevybraneUzly, 0, ++poslednyIndex);
//        System.out.println("Vybrane: " + Arrays.toString(vybraneUzly));
//        System.out.println("Nevybrane: " + Arrays.toString(nevybraneUzly));

        velkostPopulacie = 100;
        populacia = new int[velkostPopulacie][pocetCentier];
        hodnotyUceloviek = new long[velkostPopulacie];
        poradie = new int[velkostPopulacie];
        najlepsiaUF = Integer.MAX_VALUE;

//        System.out.println("VsetkyCentra: " + pocetCentier);
//        System.out.println("UmiestneneCentra: " + this.pMedian.pocetUmiestnenychCentier);
//        System.out.println("");

    }

    public void startGenAlg() {
        int i;
        vytvorPopulaciu();
        int[][] CanList = new int[velkostPopulacie][pocetCentier];
        long[] CanFit = new long[velkostPopulacie];
        int[] CanU = new int[velkostPopulacie];
        int pocetAktualnychRieseni;

        long end = (System.currentTimeMillis() + 60000);
        if (najlepsiaUF > hodnotyUceloviek[poradie[0]]) {
            najlepsiaUF = hodnotyUceloviek[poradie[0]];
            System.arraycopy(populacia[poradie[0]], 0, vybraneUzly, 0, pMedian.pocetUmiestnenychCentier);
        }
        int pocetVymen = 0;
        while (System.currentTimeMillis() < end && pocetVymen++ < pocetPopulacnychVymen) {

            int pocetVybratych = 0;
            while (pocetVybratych + 1 < pocetJedincovPreKrizenie) {
                krizenie(populacia[poradie[getRank()]], populacia[poradie[getRank()]], CanList[pocetVybratych], CanList[pocetVybratych + 1]
                );
                pocetVybratych += 2;
            }

            for (i = 0; i < pocetVybratych; i++) {
                mutaciacia(CanList[i]);
                CanFit[i] = vyratajUF(CanList[i]);
            }
            usporiadajPoradia(pocetVybratych, CanU, CanFit);

            pocetAktualnychRieseni = 0;
            i = 1;
            System.arraycopy(CanList[CanU[i]], 0, populacia[pocetAktualnychRieseni], 0, pMedian.pocetUmiestnenychCentier);
            hodnotyUceloviek[pocetAktualnychRieseni] = CanFit[CanU[i]];
            poradie[pocetAktualnychRieseni] = pocetAktualnychRieseni;
            pocetAktualnychRieseni++;
            i++;
            while ((pocetAktualnychRieseni < velkostPopulacie) && (i < pocetVybratych)) {
                if (hodnotyUceloviek[pocetAktualnychRieseni - 1] < CanFit[CanU[i]]) {                     
                    System.arraycopy(CanList[CanU[i]], 0, populacia[pocetAktualnychRieseni], 0, pMedian.pocetUmiestnenychCentier);
                    hodnotyUceloviek[pocetAktualnychRieseni] = CanFit[CanU[i]];
                    poradie[pocetAktualnychRieseni] = pocetAktualnychRieseni;
                    pocetAktualnychRieseni++;
                }
                i++;
            }
            if (pocetAktualnychRieseni < velkostPopulacie) {
                for (i = pocetAktualnychRieseni; i < velkostPopulacie; i++) {
                    vygenerujJedinca(populacia[i]);
                    hodnotyUceloviek[i] = vyratajUF(populacia[i]);
                }
                aktualizujPoradia();
            }
            if (najlepsiaUF > hodnotyUceloviek[poradie[0]]) {
                najlepsiaUF = hodnotyUceloviek[poradie[0]];
                System.arraycopy(populacia[poradie[0]], 0, vybraneUzly, 0, pMedian.pocetUmiestnenychCentier);
//                System.out.println(" The inspFit  " + najlepsiaUF);
//                System.out.println(" noPop  " + pocetVymen);
            }
        }
    }

    private void vytvorPopulaciu() {
        int i;
        for (i = 0; i < velkostPopulacie; i++) {
            vygenerujJedinca(populacia[i]);
            hodnotyUceloviek[i] = vyratajUF(populacia[i]);
        }
        aktualizujPoradia();
        boolean zhoda = false;
//        int pocetZhod = 0;
        for (i = 1; i < velkostPopulacie; i++) {
            if (hodnotyUceloviek[poradie[i]] == hodnotyUceloviek[poradie[i - 1]]) {
                zhoda = true;
                vygenerujJedinca(populacia[poradie[i - 1]]);
                hodnotyUceloviek[poradie[i - 1]] = vyratajUF(populacia[poradie[i - 1]]);
//                pocetZhod++;
            }
        }
        if (zhoda) {
            aktualizujPoradia();
        }
//        System.out.println("Bola zhoda: " + zhoda + " pocetZhod" + pocetZhod + "x");
//
//        System.out.println("****");
    }

    public void vygenerujJedinca(int[] jedinec) {
        int i;
        int[] idecka = new int[pocetCentier];
        for (i = 0; i < idecka.length; i++) {
            idecka[i] = i;
        }
        int index;
        for (i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
            index = rnd_preGenerovanieJedinca.nextInt(pocetCentier - i);
            jedinec[i] = idecka[index];
            idecka[index] = idecka[pocetCentier - i - 1];
        }

//        System.out.println("idecka:" + Arrays.toString(idecka));
//        System.out.println("Jedinec" + Arrays.toString(jedinec));
    }

    private long vyratajUF(int[] jedinec) {
        int uf = 0;
//        System.out.println("Jedinec: " + Arrays.toString(jedinec));
//        System.out.println("pMedian.poziadavky.size():" + pMedian.poziadavky.size());
//        System.out.println("pMedian.pocetUmiestnenychCentier:" + pMedian.pocetUmiestnenychCentier);

        for (int j = 0; j < pMedian.poziadavky.size(); j++) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
                if (min > pMedian.dmatrix[jedinec[i]][j]) {
                    min = pMedian.dmatrix[jedinec[i]][j];
                }
            }
//            System.out.println( "j:"+j +"min:" + min);
            uf += pMedian.poziadavky.get(j) * min;            
        }

//        for (int i = 0; i < pMedian.poziadavky.size(); i++) {
//            int min = Integer.MAX_VALUE;
//            for (int j = 0; j < pMedian.pocetUmiestnenychCentier; j++) {
//                if (min > pMedian.dmatrix[jedinec[j]][i]) {
//                    min = pMedian.dmatrix[jedinec[j]][i];
//                }
//            }
//            System.out.println("min:" + min);
//            uf += pMedian.poziadavky.get(i) * min;
//            System.out.println("********************");
//        }
//        System.out.println("UF:" + uf);
        return uf;
    }

    private void aktualizujPoradia() {
        int n = velkostPopulacie; // pocet neusporiadanych
//        System.out.println("U" + Arrays.toString(poradie));
//        System.out.println("K" + Arrays.toString(hodnotyUceloviek));
        int k, i, j;
        for (i = 0; i < n; i++) {
            poradie[i] = i;
        }
        if (n > 1) {
            for (i = 1; i < n; i++) {
                k = poradie[i];
                j = i - 1;
                while ((j > -1) && (hodnotyUceloviek[poradie[j]] > hodnotyUceloviek[k])) {
                    poradie[j + 1] = poradie[j];
                    j--;
                }
                poradie[j + 1] = k;
            }
        }
//        System.out.println("U" + Arrays.toString(poradie));
//        System.out.println("K" + Arrays.toString(hodnotyUceloviek));
//        System.out.println("");
    }

    public int getRank() {
        double fi = 0.5;// pressure of selection- parameter of ranking method
        int r = 2;// number of repeating the random generation for ranking
        double den = fi + velkostPopulacie - 1;
        double alp = (2 - fi) / den;
        double bet = 2 * (fi - 1) / (velkostPopulacie * den);
        den = 2 * alp + bet;
        double maxR = 0;
        double R;
        for (int i = 0; i < r; i++) { // the biggest of the rep trials is the result
            if (maxR < (R = Math.random())) {
                maxR = R;
            }
        }
        int k = (int) Math.ceil((-den + Math.sqrt(den * den + 8 * bet * maxR)) / (2 * bet));
        return velkostPopulacie - k;
    }

    private void krizenie(int[] rodic1, int[] rodic2, int[] potomok1, int[] potomok2) {
        int[] vsetkyGeny = new int[pocetCentier];
        int index = 0;
        int predanyGen;
        for (int i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
            vsetkyGeny[rodic1[i]] = 1;
        }
        for (int i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
            predanyGen = rodic2[i];
            vsetkyGeny[predanyGen] += 1;
            if (vsetkyGeny[predanyGen] == 2) {
                potomok1[index] = predanyGen;
                potomok2[index++] = predanyGen;
            }
        }
        int[] nepredaneGeny = new int[pMedian.pocetUmiestnenychCentier];
        int indexUn = 0;
        for (int i = 0; i < pMedian.pocetUmiestnenychCentier; i++) {
            if (vsetkyGeny[i] == 1) {
                nepredaneGeny[indexUn++] = i;
            }
        }
        int split = indexUn / 2;
        int temp;
        for (int i = 0; i < split; i++) {
            double rand = rnd_naPredavanieGenovPriKrizeni.nextDouble();
            if (rand < 0.5) {
                temp = nepredaneGeny[i];
                nepredaneGeny[i] = nepredaneGeny[i + split];
                nepredaneGeny[i + split] = temp;
            }
        }

        for (int i = 0; i < split; i++) {
            potomok1[index] = nepredaneGeny[i];
            potomok2[index++] = nepredaneGeny[i + split];
        }

//        int[] p1 = new int[pMedian.pocetUmiestnenychCentier];
//        int[] p2 = new int[pMedian.pocetUmiestnenychCentier];
//        System.arraycopy(potomok1, 0, p1, 0, pMedian.pocetUmiestnenychCentier);
//        System.arraycopy(potomok2, 0, p2, 0, pMedian.pocetUmiestnenychCentier);
//        Arrays.sort(p1);
//        Arrays.sort(p2);
//
//        for (int i = 1; i < p2.length; i++) {
//            if (p1[i - 1] == p1[i] || p2[i - 1] == p2[i]) {
//                System.out.println("Vznikla duplicita po krizeni");
//                return;
//            }
//        }

    }

    private void mutaciacia(int[] jedinec) {
        double g = rnd_budeMutacia.nextDouble();
        int[] geny = new int[pocetCentier];
        if (g < pravdepodobnostMutacie) { // robim mutaciu
            System.arraycopy(jedinec, 0, geny, 0, pMedian.pocetUmiestnenychCentier);
//            System.out.println("geny:" +Arrays.toString(geny));
            HashSet<Integer> uzPouziteCentra = new HashSet<>();
            for (int i = 0; i < geny.length; i++) {
                uzPouziteCentra.add(geny[i]);
            }
            GenDisUnif rnd_intMutacia = new GenDisUnif(0, pMedian.pocetUmiestnenychCentier - 1);
            GenDisUnif rnd_celyRozsah = new GenDisUnif(0, jedinec.length - 1);

//            System.out.println("pocetMutovanychGenov: "+pocetMutovanychGenov);
            HashSet<Integer> pouziteIndexyKdeUzBoliNahrady = new HashSet<>();
            int index;
            int indexcelyrozsah;
            int i = 0;
            int pocetMutovanychGenov = (int) rnd_intMutacia.Generate();  // chapem ako pocetCentier ktore mam zmenit na rovnaky pocet inych centier
            while (i < pocetMutovanychGenov) {
                index = rnd_nagen.nextInt(geny.length);   // index pre centrum ktore bude nahradene
                if (!pouziteIndexyKdeUzBoliNahrady.contains(index)) { // zabezpecie aby v jednej mutacii, nebolo viac krat mutovanie jedno miesto
                    pouziteIndexyKdeUzBoliNahrady.add(index);
                    while (true) {
                        indexcelyrozsah = (int) rnd_celyRozsah.Generate(); // snazim sa vygenerovat id centra, ktore nebolo este pouzite
                        if (!uzPouziteCentra.contains(indexcelyrozsah)) {
                            uzPouziteCentra.add(indexcelyrozsah);
                            geny[index] = indexcelyrozsah;  // robim vymenu
                            break;
                        }
                    }
                    i++;
                }
            }
//            int[] p1 = new int[pMedian.pocetUmiestnenychCentier];
//            System.arraycopy(jedinec, 0, p1, 0, pMedian.pocetUmiestnenychCentier);
//            Arrays.sort(p1);
//            for (i = 1; i < p1.length; i++) {
//                if (p1[i - 1] == p1[i]) {
//                    System.out.println("Vznikla duplicita po mutacii");
//                    return;
//                }
//            }

//            System.out.println("geny:" + Arrays.toString(geny));
//            System.arraycopy(geny, 0, jedinec, 0, pMedian.pocetUmiestnenychCentier);
//            System.out.println("**************************");
        }
    }

    public void usporiadajPoradia(int n, // Number of ordered elements
            int[] U, // Output array of subscripts - pointers
            long[] K // Input array filled with ordered elements
    ) {
//        System.out.println("U" + Arrays.toString(U));
//        System.out.println("K" + Arrays.toString(K));
        int k, i, j;
        for (i = 0; i < n; i++) {
            U[i] = i;
        }
        if (n > 1) {
            for (i = 1; i < n; i++) {
                k = U[i];
                j = i - 1;
                while ((j > -1) && (K[U[j]] > K[k])) {
                    U[j + 1] = U[j];
                    j--;
                } // while j
                U[j + 1] = k;
            } // for i
        } // if n          
//        System.out.println("U" + Arrays.toString(U));
//        System.out.println("K" + Arrays.toString(K));
//        System.out.println("");
    } // End of getRank  

    public int[] getVybraneUzly() {
        return vybraneUzly;
    }

    public long getNajlepsiaUF() {
        return najlepsiaUF;
    }
    
    

}
