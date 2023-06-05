package ACO;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  Trieda na ukladanie vysledku optimalizacie
 * @author rendo
 */
public class Vysledok {

    private int[] cesta;
    private int naklady;
    /**
     * Konstruktor triedy
     */
    public Vysledok() {
        naklady = Integer.MAX_VALUE;
    }
    /**
     * Nastavenie noveho vyskledku
     * @param naklady - cena cesty
     * @param cesta  - cesta
     */
    public void setVysledok(int naklady, int[] cesta) {
        this.naklady = naklady;
        this.cesta = cesta.clone();

    }
    /**
     * Preusporiadanie cesta, aby sa zacinalo podla id
     * @param id - id pociatocneho uzola
     * @return 
     */
    public int[] getSpravnePoradnie(int id) {
//        System.out.println("Cesta: "+Arrays.toString(cesta));
        if (id == cesta[0]) {
            return cesta;
        }

        int[] c = new int[cesta.length];
//        System.out.println("C: "+Arrays.toString(c));
        int index = -1;
        for (int i = 0; i < cesta.length; i++) {
            if (cesta[i] == id) {
                index = i;
//                System.out.println("Index: "+index);
                break;
            }
        }

        //System.arraycopy(source_arr, sourcePos, dest_arr,destPos, len); 
        System.arraycopy(cesta, index, c, 0, cesta.length - index);
//        System.out.println("C: "+Arrays.toString(c));

        System.arraycopy(cesta, 1, c, cesta.length - index, (cesta.length - (cesta.length - index)));
//        System.out.println("C: "+Arrays.toString(c));

        return c;
    }

    /**
     * Geter na naklady cesty
     * @return 
     */
    public int getNaklady() {
        return naklady;
    }

}
