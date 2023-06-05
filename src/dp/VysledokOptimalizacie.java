/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;
import CW.Trasa;
import CW.Zakaznik;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trieda pre ukladanie vysledokov optimalizacie
 * @author rendo
 */
public class VysledokOptimalizacie {

    String nazovRiesenia;
    ArrayList<Trasa> trasy;
    ArrayList<Zakaznik> neobsluzeny;

    double sumPoziadaviek;
    double naklady;
    /**
     * Konstruktor
     */
    public VysledokOptimalizacie() {
    }
   /**
    * 
    * @param nazovRiesenia MVH, ACO, CW
    * @param trasy zoznam tras
    * @param sumPoziadaviek celkove poziadavky
    * @param naklady  celkove naklady
    */
    public VysledokOptimalizacie(String nazovRiesenia, ArrayList<Trasa> trasy, double sumPoziadaviek, double naklady) {
        this.nazovRiesenia = nazovRiesenia;
        this.trasy = trasy;
        this.sumPoziadaviek = sumPoziadaviek;
        this.naklady = naklady;
    }
    /**
     * 
     * @param nazovRiesenia- MVH, ACO, CW
     * @param trasy -zoznam tras
     * @param sumPoziadaviek -celkove poziadavky
     * @param naklady-celkove naklady
     * @param neobsluzeny - zoznam neoblsluzenych zakaznikov
     */
    public VysledokOptimalizacie(String nazovRiesenia, ArrayList<Trasa> trasy, double sumPoziadaviek, double naklady, ArrayList<Zakaznik> neobsluzeny) {
        this.nazovRiesenia = nazovRiesenia;
        this.trasy = trasy;
        this.sumPoziadaviek = sumPoziadaviek;
        this.naklady = naklady;
        this.neobsluzeny =neobsluzeny;
    }
    /**
     * vypis riesenia
     * @return 
     */
    @Override
    public String toString() {
        String s = "Použitá optimalizácia: " + nazovRiesenia;
        int i = 0;
        String a;
        String p;
        int cenaSuboptimalnej = 0;
        boolean bolaAlternativna =false;
        for (Trasa trasa : trasy) {
            i++;
            String pom = "\nTrasa[" + i + "] s vozidlom s nosnostou: " + trasa.getVozidlo().getKapacita()
                    + " vyzbiera:" + trasa.getAktualnaKapacitaJazdy();
            a = "Alternativna optimalna trasa: ";
            p = "\nPôvodná suboptimálna trasa: ";
            if (trasa.getAlternativnaTrasa() == null) {                
                if(nazovRiesenia.equals("MVaH")){
                   p = "\nOptimálna trasa: "; 
                }else if(nazovRiesenia.equals("ACO")){
                    p = "Suboptimálna trasa: "; 
                }                
                a = "x";
                for (Zakaznik zakaznik : trasa.getZakazniciTrasy()) {
                    p += zakaznik.getId_zakaznika() + ">";
                }
            } else {
                bolaAlternativna= true;
                cenaSuboptimalnej+=trasa.getAlternativneNaklady();
//                System.out.println(trasa.getAlternativnaTrasa().size());
//                System.out.println(Arrays.toString(trasa.getAlternativnaTrasa().toArray()));
                if(trasa.getAlternativnaTrasa().size()-2>14){ //-2 lebo obsahuje aj depo
                    a = "Alternativna subOptimalna trasa: ";
                }
                for (int j = 0; j < trasa.getZakazniciTrasy().size(); j++) {
                    p += trasa.getZakazniciTrasy().get(j).getId_zakaznika() + ">";
                    a += trasa.getAlternativnaTrasa().get(j) + ">";
                }
                a = a.substring(0, a.lastIndexOf(">"));
            }
            p = p.substring(0, p.lastIndexOf(">"));
            p += " Náklady: " +String.format("%.2f", (trasa.getNaklady()/ 1000.0))  + "Km";
            if (a.equals("x")) {

            } else if (!a.equals("")) {
                p += "\n" + a + " Náklady: " + String.format("%.2f", (trasa.getAlternativneNaklady() / 1000.0)) + "Km";
            } else {
                p += "\n Nie je možné zOptimalivať s MVaH";
            }
            s += pom + p + "\n***************************";
        }
        s += "\n Celkovo sa vyzbiera: " + sumPoziadaviek + "L \n Celkové pôvodné náklady: " + String.format("%.2f",naklady / 1000) + "Km";
        if(bolaAlternativna){
            s +=" Celkové optimalizované náklady: "+String.format("%.2f",cenaSuboptimalnej/ 1000.0) + "Km" ; 
        }
        String n = "Neobslúžené stojiská: ";
        if(neobsluzeny!=null){
            int potrebnaKapacita =0;
            for (Zakaznik zakaznik : neobsluzeny) {
                potrebnaKapacita += zakaznik.getPoziadavka();
             n += zakaznik.getId_zakaznika()+",";
            }
             n = n.substring(0, n.lastIndexOf(","));
             s += "\n"+n+" Potrebná kapacita: "+potrebnaKapacita;
        }
        
        return s;
    }

    /**
     * Vypis tras
     * @return 
     */
    public String vratLenTrasy() {
        String s = "";
        int i = 1;
        String trs = "";
        for (Trasa trasa : trasy) {
            trs = "trasa" + i + ": ";
            for (Zakaznik zak : trasa.getZakazniciTrasy()) {
                trs += zak.getId_zakaznika() + ",";
            }
            i++;
            s += trs + "\n";
        }
        return s;
    }
    /**
     * Getter na trasy
     * @return 
     */
    public ArrayList<Trasa> getTrasy() {
        return trasy;
    }
    /**
     * odobrenie trasy
     * @param poradie 
     */
    public void odoberTrasu(int poradie){
        trasy.remove(poradie);
    }
}
