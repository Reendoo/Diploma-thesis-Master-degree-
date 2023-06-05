/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Trieda reprezentujuca graf
 *
 * @author rendo
 */
public class Graf {

    private int maxIDhrany;
    private int maxIDuzla;
    private List<Hrana> hrany;
    private List<Uzol> uzly;
    static private int[][] id_Dmatrix; //matica vzdialenosti
    static private String[][] s_Dmatrix; // podobne ako maticaVzdialenosti, ale  retazec bunky v matici
    //reprezentuje postupnost uzlov pre najkratsiu cestu z i do j

    /**
     * Konštruktor triedy graf
     */
    public Graf() {
        hrany = new ArrayList<>();
        uzly = new ArrayList<>();
    }

    /**
     * Konštruktor triedy graf
     *
     * @param hrany - list hran
     * @param uzly -list uzlov
     */
    public Graf(List<Hrana> hrany, List<Uzol> uzly) {
        this.hrany = hrany;
        this.uzly = uzly;
    }

    /**
     * Getter na hrany grafu
     *
     * @return
     */
    public List<Hrana> getHrany() {
        return hrany;
    }

    /**
     * Getter na uzly grafu
     *
     * @return
     */
    public List<Uzol> getUzly() {
        return uzly;
    }

    /**
     * Metóda na hladanie uzla podla id
     *
     * @param id - id uzla
     * @return null ak taky uzol nie je inak uzol
     */
    public Uzol getuzol(int id) {
        for (Uzol uzol : uzly) {
            if (uzol.id == id) {
                return uzol;
            }
        }
        return null;
    }

    /**
     * Metoda sluziaca na vlozenie uzla do grafu
     *
     * @param x_sur - suradnica x
     * @param y_sur - suradnice y
     * @param noveId - noveID uzla, ak Integer.MAX_VALUE tak vkladam
     * nestojiskovyUzol inak stojiskovy
     * @return novyUzol
     */
    public Uzol vlozUzolDoGrafu(double x_sur, double y_sur, int noveId) {
        if (noveId == Integer.MAX_VALUE) { //import obycajnehoUzla
            for (Uzol uzol : uzly) {
//                System.out.println(uzol.toString());
//                System.out.println(x_sur + "==" + uzol.getX_sur() + " ???");
//                System.out.println(y_sur + "==" + uzol.getY_sur() + " ???");
                if (uzol.getX_sur() == x_sur && uzol.getY_sur() == y_sur) {
                    return uzol;
                }
            }
            maxIDuzla++;
//            System.out.println("id_vkladaneho" + maxIDuzla);
            Uzol novyUzol = new Uzol(maxIDuzla, x_sur, y_sur);
            uzly.add(novyUzol);
            return novyUzol;
        } else { //import stojiskovehoUzla
            for (Uzol uzol : uzly) {
//                System.out.println(uzol.toString());
//                System.out.println(x_sur + "==" + uzol.getX_sur() + " ???");
//                System.out.println(y_sur + "==" + uzol.getY_sur() + " ???");
                if (uzol.id == noveId) {
                    return uzol;
                }
            }
            Uzol novyUzol = new Uzol(noveId, x_sur, y_sur);
            //maxIDuzla++;
            uzly.add(novyUzol);
            return novyUzol;
        }
    }

    /**
     * Metóda vlozenia hrany do grafu
     *
     * @param srcUzol - zaciatocny uzol hrany
     * @param destUzol - koncovy uzol hrany
     * @param dlzka - dlzka hrany
     * @param jednosmerka - true ak je jednosmerna cesta
     * @param suradnice - list suradnica (zaciatok, zlomy hrany, koniec)
     */
    public void vlozHranuDoGrafu(Uzol srcUzol, Uzol destUzol, int dlzka, boolean jednosmerka, LinkedList<Suradnice> suradnice) {
        for (Hrana hrana : hrany) {
            if (hrana.srcUzol == srcUzol && hrana.destUzol == destUzol) {
                if (hrana.dlzka == dlzka) {
//                    System.out.println("SRC_uzol: " + srcUzol.toString());
//                    System.out.println("DEST_uzol: " + destUzol.toString());
//                    System.out.println("Dlzka: " + dlzka);
//                    System.out.println("UzozenaHrana: " + hrana.toString());
                    JOptionPane.showMessageDialog(null, "Pruser taka hrana existuje");
                    return;
                } else {
                    maxIDhrany++;
                    Hrana h = new Hrana("" + maxIDhrany, srcUzol, destUzol, dlzka, suradnice);
                    //System.out.println("Povodne:" + Arrays.toString(suradnice.toArray()));
                    hrany.add(h);
                    if (jednosmerka) {
//            System.out.println("Uspesne vlozena jednosmerna hrana");
                    } else {
                        maxIDhrany++;
                        LinkedList<Suradnice> revers = (LinkedList<Suradnice>) suradnice.clone();
                        Collections.reverse(revers);
                        //System.out.println("Reverzne:" + Arrays.toString(revers.toArray()));
                        h = new Hrana("" + maxIDhrany, destUzol, srcUzol, dlzka, revers);
                        hrany.add(h);
//            System.out.println("Uspesne vlozena obojsmerna hrana");
                    }
                }
                return;
            }
        }
        maxIDhrany++;
        Hrana hrana = new Hrana("" + maxIDhrany, srcUzol, destUzol, dlzka, suradnice);
        hrany.add(hrana);
        if (jednosmerka) {
//            System.out.println("Uspesne vlozena jednosmerna hrana");
        } else {
            maxIDhrany++;
            LinkedList<Suradnice> revers = (LinkedList<Suradnice>) suradnice.clone();
            Collections.reverse(revers);
            hrana = new Hrana("" + maxIDhrany, destUzol, srcUzol, dlzka, revers);
            hrany.add(hrana);
//            System.out.println("Uspesne vlozena obojsmerna hrana");
        }

    }

    /**
     * Metóda sluziaca na importovanie graf z GEOJASON suboru
     *
     * @param urlPath - cesta k suboru
     * @param pocetStojisk - pocet stojisk
     */
    public void importGrafuZGEOJASON(String urlPath, int pocetStojisk) {
//        int nullnull = 0;
//        int cislonull = 0;
//        int nullcislo = 0;
//        int cislocislo = 0;
        this.maxIDuzla = (pocetStojisk - 1); //0 je stojisko
        JOptionPane.showMessageDialog(null, "Pocet Stojisk: " + pocetStojisk);
//        int pocetobojsmernych = 0;
//        int pocetJednosmernych = 0;
        JSONParser parser = new JSONParser();
//        int pocetRiadkov = 0;
        try {
            Object obj = parser.parse(new FileReader(urlPath));
            JSONObject jSONObject = (JSONObject) obj;
            JSONArray courseArray = (JSONArray) jSONObject.get("features");
            for (Object object : courseArray) {
//                pocetRiadkov++;
                JSONObject o = (JSONObject) ((JSONObject) object).get("properties");
                JSONObject o2 = (JSONObject) ((JSONObject) object).get("geometry");
                JSONArray arrayGPS = (JSONArray) o2.get("coordinates");
                arrayGPS = (JSONArray) arrayGPS.get(0);
                LinkedList<Suradnice> suradnice = new LinkedList<>();
                String qqq;
                double x, y;
                for (Object object1 : arrayGPS) {
                    qqq = object1.toString();
                    qqq = qqq.substring(1, qqq.length() - 1);
                    y = (double) Math.round((Double.parseDouble(qqq.substring(0, qqq.indexOf(",")))) * 100000d) / 100000d;
                    x = (double) Math.round((Double.parseDouble(qqq.substring(qqq.indexOf(",") + 1))) * 100000d) / 100000d;
                    //System.out.println(x+","+y);
                    suradnice.add(new Suradnice(x, y));
                }
//                System.out.println("First: "+hrany.getFirst().toString());
//                System.out.println("Last: "+hrany.getLast().toString());
                boolean jednosmerka;
                String s;
                s = (String) o.get("oneway");
                if (null == s) {
                    jednosmerka = false;
//                    pocetobojsmernych++;
                } else {
                    switch (s) {
                        case "yes":
                            jednosmerka = true;
//                            pocetJednosmernych++;
                            break;
                        default:
                            System.out.println("problem!");
                            return;
                    }
                }

                int dlzka = (int) (Double.parseDouble("" + o.get("dlzka")) + 0.5);
//                System.out.println("FID: " + fid
//                        + " src[" + src_x
//                        + "," + src_y + "]"
//                        + " dest[" + dest_x
//                        + "," + dest_y + "]"
//                        + " dlzka: " + dlzka + "m "
//                        + jednosmerka
//                );
                String start = "" + o.get("START_ID_adresy");
                String end = "" + o.get("end_ID_adresy");
//                if (start.compareTo("null") == 0 & end.compareTo("null") == 0) {
//                    nullnull++;
//                } else if (start.compareTo("null") != 0 & end.compareTo("null") == 0) {
//                    cislonull++;
//                } else if (start.compareTo("null") == 0 & end.compareTo("null") != 0) {
//                    nullcislo++;
//                } else if (start.compareTo("null") != 0 & end.compareTo("null") != 0) {
//                    cislocislo++;
//                } else {
//                    JOptionPane.showMessageDialog(null, "problem!!!!");
//                }

                Uzol src;
                if (start.compareTo("null") == 0) { // ak hrana nezacina v stojiskovom uzle
                    src = vlozUzolDoGrafu(suradnice.getFirst().getX(), suradnice.getFirst().getY(), Integer.MAX_VALUE);
                } else {//hrana zacina v stojiskovom uzle
                    src = vlozUzolDoGrafu(suradnice.getFirst().getX(), suradnice.getFirst().getY(), Integer.parseInt(start));
                }
                Uzol dest;
                if (end.compareTo("null") == 0) {//ak hrana nekonci v stojiskovom uzle
                    dest = vlozUzolDoGrafu(suradnice.getLast().getX(), suradnice.getLast().getY(), Integer.MAX_VALUE);
                } else {//ak hrana konci v stojiskovom uzle                    
                    dest = vlozUzolDoGrafu(suradnice.getLast().getX(), suradnice.getLast().getY(), Integer.parseInt(end));
                }
                vlozHranuDoGrafu(src, dest, dlzka, jednosmerka, suradnice);
            }
//            System.out.println("PocetRiadkov: " + pocetRiadkov);
//            System.out.println("Nullnull: " + nullnull);
//            System.out.println("nullcislo: " + nullcislo);
//            System.out.println("Cislonull: " + cislonull);
//            System.out.println("Cislocislo: " + cislocislo);
//            System.out.println("PocetJednosmeriek: " + pocetJednosmernych);
//            System.out.println("PocetObojsmeriek: " + pocetobojsmernych);
//            System.out.println("Spolu hran: " + (pocetJednosmernych + (2 * pocetobojsmernych)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Getter na maticuVzdialenosti
     *
     * @return
     */
    public int[][] getid_Dmatrix() {
        return id_Dmatrix;
    }

    /**
     * Setter na Maticu vzdialenosti, Maticu najkratsich ciest
     *
     * @param Dmatrix
     */
    public void setDmatrix(Matice Dmatrix) {
        this.id_Dmatrix = Dmatrix.getiDmatrix();
        this.s_Dmatrix = Dmatrix.getsDmatrix();
    }

//    public double[][] getSubMatrix(ArrayList<Uzol> uzlyNaSubMaticu) {
//        double[][] subDMatrix = new double[uzlyNaSubMaticu.size()][uzlyNaSubMaticu.size()];
//        for (int i = 0; i < uzlyNaSubMaticu.size(); i++) {
//            for (int j = 0; j < subDMatrix.length; j++) {
//                subDMatrix[i][j] = id_Dmatrix[uzlyNaSubMaticu.get(i).id - 1][uzlyNaSubMaticu.get(j).id - 1];
//            }
//        }
//        return subDMatrix;
//    }
    /**
     * Metóda, ktorá vracia na základe idiecek sub maticu vzdielenosti
     *
     * @param idecka - idecka uzlov
     * @return
     */
    public int[][] getSubMatrixFromID(ArrayList<Integer> idecka) {
        int[][] subDMatrix = new int[idecka.size()][idecka.size()];
        for (int i = 0; i < idecka.size(); i++) {
            for (int j = 0; j < subDMatrix.length; j++) {
                subDMatrix[i][j] = id_Dmatrix[idecka.get(i)][idecka.get(j)];
            }
        }
        return subDMatrix;
    }

    /**
     * Pomocna metoda na vypisanie matice na konzolu
     *
     * @param matrix matica
     * @return
     */
    public static String printMatrix(int matrix[][]) {
        String s = "{";
        //double v;
        for (int i = 0; i < matrix.length; i++) {
            //String q = ""+(i+1)+">>>  ";
            String p = "{";
            for (int j = 0; j < matrix[i].length; j++) {
                //q+= matrix[i][j] + " ";                
                //v = matrix[i][j];
                p += matrix[i][j] + ",";
            }
            p = p.substring(0, p.length() - 1);
            p += "},";
            //System.out.println(q);
            s += p + "\n";
        }
        s += "};";
        System.out.println(s);
        return s;
    }

    /**
     * Pomocna metoda na vypisanie matice na konzolu
     *
     * @param matrix matica
     * @return
     */
    public static String printMatrixa(String matrixa[][]) {
        String s = "";
        String v;
        for (int i = 0; i < matrixa.length; i++) {
            //String q = ""+(i+1)+">>>  ";
            for (int j = 0; j < matrixa[i].length; j++) {
                //q+= matrix[i][j] + " ";                
                v = matrixa[i][j];
                s += v + " ";
            }
            //System.out.println(q);
            s += "\n";
        }
        System.out.println(s);
        return s;
    }

    /**
     * Metóda slúžiaca na uloženie matic do súboru
     */
    public void saveMatrixToFile() {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        builder.append("").append(id_Dmatrix.length).append("\n");
        builder2.append("").append(id_Dmatrix.length).append("\n");
        for (int i = 0; i < id_Dmatrix.length; i++) {
            for (int j = 0; j < id_Dmatrix.length; j++) {
                builder.append(id_Dmatrix[i][j]).append(" ");
                builder2.append(s_Dmatrix[i][j] + " ");
            }
            builder.append("\n");
            builder2.append("\n");
        }
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(".//idDmatrix.txt"));
            wr.write(builder.toString());
            wr.close();
            wr = new BufferedWriter(new FileWriter(".//sDmatrix.txt"));
            wr.write(builder2.toString());
            wr.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * Metóda služiaca na načítanie matice zo súboru
     */
    public void loadMatrixFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".//idDmatrix.txt"));
            String line = reader.readLine();
            id_Dmatrix = new int[Integer.parseInt(line)][Integer.parseInt(line)];
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] column = line.split(" ");
                int col = 0;
                for (String string : column) {
                    //Dmatrix[row][col] = Double.parseDouble(string);  int a = (int) (doubleVar + 0.5);
                    id_Dmatrix[row][col] = Integer.parseInt(string); //(int) (Double.parseDouble(string) + 0.5);
                    col++;
                }
                row++;
            }

            reader = new BufferedReader(new FileReader(".//sDmatrix.txt"));
            line = reader.readLine();
            s_Dmatrix = new String[Integer.parseInt(line)][Integer.parseInt(line)];

            row = 0;
            while ((line = reader.readLine()) != null) {
                String[] column = line.split(" ");
                int col = 0;
                for (String string : column) {
                    s_Dmatrix[row][col] = string;
                    col++;
                }
                row++;
            }

            reader.close();
            //System.out.println("uzavrel reader");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
//        System.out.println("!!!! Ukončené načítanie matice !!!!");
    }

    /**
     * Metóda sluziaca na ulozenie uzlov do suboru
     */
    public void saveUzly() {
        StringBuilder builder = new StringBuilder();
        builder.append("").append(uzly.size()).append("\n");
        for (Uzol uzol : uzly) {
            builder.append(uzol.id).append(" ").append(uzol.getX_sur()).append(" ").append(uzol.getY_sur()).append("\n");
        }
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(".//uzly.txt"));
//            System.out.println(builder.toString());
            wr.write(builder.toString());
            wr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }

    }

    /**
     * Metoda sluziaca na nacitanie uzlov zo suboru
     */
    public void loadUzlyFromFile() {
        uzly = new ArrayList<>();
        int pocetZaznamov;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".//uzly.txt"));
            String line = reader.readLine();
            pocetZaznamov = Integer.parseInt(line);

            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] column = line.split(" ");
                uzly.add(new Uzol(
                        Integer.parseInt(column[0]),
                        Double.parseDouble(column[2]),
                        Double.parseDouble(column[3])));
            }
            if (i != pocetZaznamov) {
                JOptionPane.showMessageDialog(null, "Problem pocet zaznamov: " + pocetZaznamov + " nacitanych zaznamov: " + i);
                return;
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        //System.out.println("Úspešne načitané uzly zo súboru");
    }

    /**
     * Metoda sluziaca na ulozenie hran do suboru
     */
    public void saveHrany() {
        StringBuilder builder = new StringBuilder();
        builder.append("").append(hrany.size()).append("\n");
        for (Hrana hrana : hrany) {
            String suradnice = "";
            for (Suradnice sur : hrana.getSuradnice()) {
                suradnice += "" + sur.getX() + "," + sur.getY() + " ";
            }
            builder.append(hrana.id + " "
                    + hrana.srcUzol.id + " "
                    + hrana.destUzol.id + " "
                    + hrana.dlzka + " "
                    + suradnice + " \n");
        }
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(".//hrany.txt"));
            //System.out.println(builder.toString());
            wr.write(builder.toString());
            wr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }

    }

    /**
     * Metóda sluziaca na nacitanie hran zo suboru
     */
    public void loadHranyFromFile() {
        hrany = new ArrayList<>();
        int pocetZaznamov;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".//hrany.txt"));
            String line = reader.readLine();
            pocetZaznamov = Integer.parseInt(line);
            int i = 0;
            Uzol src = null;
            Uzol dest = null;
            LinkedList<Suradnice> suradnice = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                i++;
                String[] column = line.split(" ");
                suradnice = new LinkedList<>();
                for (int j = 4; j < column.length; j++) {
                    suradnice.add(new Suradnice(
                            Double.parseDouble(column[j].substring(0, column[j].indexOf(","))),
                            Double.parseDouble(column[j].substring(column[j].indexOf(",") + 1))));
                }
                int p = 0;
                for (Uzol uzol : uzly) {
                    if (uzol.id == Integer.parseInt(column[1])) {
                        src = uzol;
                        p++;
                    }
                    if (uzol.id == Integer.parseInt(column[2])) {
                        dest = uzol;
                        p++;
                    }
                }
                if (p < 2) {
                    JOptionPane.showMessageDialog(null, "Problem stratene uzly");
                    return;
                } else if (p > 2) {
                    JOptionPane.showMessageDialog(null, "Zduplikovane uzly");
                    return;
                } else {
                    //System.out.println("Uspesne pridana hrana !");
                    hrany.add(new Hrana(column[0], src, dest, Integer.parseInt(column[3]), suradnice));
                }

            }
            if (i != pocetZaznamov) {
                JOptionPane.showMessageDialog(null, "problem pocet zaznamov: " + pocetZaznamov + " nacitanych zaznamov: " + i);
                return;
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        //System.out.println("Úspešne načítané hrany zo súboru");
    }

    /**
     * Metoda sluziaca na celkove nacitanie grafu zo suborov
     */
    public void kompletnyLoad() {
        loadUzlyFromFile();
        loadHranyFromFile();
        loadMatrixFromFile();
    }

    /**
     * Metóda sluziaca na usporiadanie zoznamu uzlov podla id
     */
    public void upracGraf() {
        Collections.sort(uzly);
    }

    /**
     * Getter na maticu najkratsich ciest
     *
     * @return
     */
    public String[][] getS_Dmatrix() {
        return s_Dmatrix;
    }

    /**
     * Metóda sluziaca na vratenie vsetkych uzlov na ceste
     *
     * @param subCesta idecka - idcka hlavnych uzlov
     * @return vsetkych idecka uzlov na ceste
     */
    public LinkedList<Integer> vratKompletneCestu(LinkedList<Integer> subCesta) {
        LinkedList<Integer> cesta = new LinkedList<>();
        int startUzol;
        int destUzol;
        while (subCesta.size() > 2) {
            startUzol = subCesta.pop();
            destUzol = subCesta.peek();
            cesta.addAll(vratIdecka(s_Dmatrix[startUzol][destUzol], false));
            //System.out.println("D"+startUzol+","+destUzol+": "+getS_Dmatrix()[startUzol][destUzol]);
        }

        startUzol = subCesta.pop();
        destUzol = subCesta.peek();
        //System.out.println("D"+startUzol+","+destUzol+": "+getS_Dmatrix()[startUzol][destUzol]);
        cesta.addAll(vratIdecka(s_Dmatrix[startUzol][destUzol], true));
        return cesta;
    }

    /**
     * Metoda sluziaca na vratenie vsetkych hran cesty
     *
     * @param cesta idecka uzlov
     * @return vsetky hrany cesty
     */
    public LinkedList<Hrana> vratHrany(LinkedList<Integer> cesta) {
        LinkedList<Hrana> hrany = new LinkedList<>();
        int src, dest;
        while (cesta.size() > 2) {
            src = cesta.pollFirst();
            dest = cesta.peekFirst();
            for (Hrana hrana : getHrany()) {
                if (hrana.srcUzol.id == src && hrana.destUzol.id == dest) {
                    hrany.add(hrana);
                    break;
                }
            }
        }
        src = cesta.pollFirst();
        dest = cesta.pollFirst();
        for (Hrana hrana : getHrany()) {
            if (hrana.srcUzol.id == src && hrana.destUzol.id == dest) {
                hrany.add(hrana);
                break;
            }
        }
        return hrany;
    }

    /**
     * Metoda sluziaca ktora z ratazca, ktory reprezentuje cestu vytvori
     * Linkedlist idecok uzlov
     *
     * @param stringCesta retazec cesty
     * @param zaradPosledny true ked do cesty sa nema zaradit posleny uzol
     * retazca
     * @return postunost idecok uzlov
     */
    public LinkedList<Integer> vratIdecka(String stringCesta, boolean zaradPosledny) {
        LinkedList<Integer> cesta = new LinkedList<>();
        int indexCiarky, idUzla;
        while (stringCesta.length() > 0) {
            indexCiarky = stringCesta.indexOf(",");
            if (indexCiarky != -1) {
                idUzla = Integer.parseInt(stringCesta.substring(0, indexCiarky));
                cesta.add(idUzla);
                stringCesta = stringCesta.substring(indexCiarky + 1);
            } else {
                if (zaradPosledny) {
                    idUzla = Integer.parseInt(stringCesta);
                    cesta.add(idUzla);
                }
                break;
            }
        }
        return cesta;
    }

//    public Hrana getHrana(int src, int dest) {
//        for (Hrana hrana : getHrany()) {
//            if (hrana.srcUzol.id == src && hrana.destUzol.id == dest) {
//                return hrana;
//            }
//        }
//        return null;
//    }
    /**
     * Pomocna metoda ked som chcel updatnut v celej databaze gps suradnice
     * vsetkym stojiska
     *
     * @param pocetStojisk
     * @return
     */
    public String generateUpdateForDB(int pocetStojisk) {
        System.out.println(this.maxIDuzla);
        String s = "";
        for (Uzol uzol : uzly) {
            if (uzol.id <= pocetStojisk) {
                s += "update stojisko "
                        + "set GPS = Point("
                        + uzol.getX_sur()
                        + ","
                        + uzol.getY_sur()
                        + ")"
                        + " where id_stojiska="
                        + uzol.id + ";\n";
            }
        }
        return s;
    }

    /**
     * Metoda na zaklade ideciek uzlov vrati uzly
     *
     * @param idecka
     * @return
     */
    public LinkedList<Uzol> getUzly(LinkedList<Integer> idecka) {
        LinkedList<Uzol> uzly = new LinkedList<>();
        for (Integer i : idecka) {
            uzly.add(getuzol(i));
        }
        return uzly;
    }

    /**
     * Pomocna metoda, ktorej ulohov je vyratat usporu respektive predlzenia ak
     * sa vymenilo poradia hran
     *
     * @param vsuvaneMiesto_predchodca
     * @param vyberaneMiesto_predchodca
     * @param veberaneMiesto_nasledovnik
     * @param presuvany
     * @param vsuvaneMiesto_Nasledovnik
     * @return
     */
    public int vyratajUsporu(int vsuvaneMiesto_predchodca, int vyberaneMiesto_predchodca,
            int veberaneMiesto_nasledovnik, int presuvany, int vsuvaneMiesto_Nasledovnik) {

//        System.out.println("MINUS:");
//        System.out.println("(" + vsuvaneMiesto_predchodca + "," + vsuvaneMiesto_Nasledovnik + "): "+(-id_Dmatrix[vsuvaneMiesto_predchodca][vsuvaneMiesto_Nasledovnik]));
//        System.out.println("(" + vyberaneMiesto_predchodca + "," + presuvany + "): "+(- id_Dmatrix[vyberaneMiesto_predchodca][presuvany]));
//        System.out.println("(" + presuvany + "," + veberaneMiesto_nasledovnik + "): "+(- id_Dmatrix[presuvany][veberaneMiesto_nasledovnik]));
        int minus = -id_Dmatrix[vsuvaneMiesto_predchodca][vsuvaneMiesto_Nasledovnik]
                - id_Dmatrix[vyberaneMiesto_predchodca][presuvany]
                - id_Dmatrix[presuvany][veberaneMiesto_nasledovnik];

//        System.out.println("PLUS:");
//        System.out.println("(" + vsuvaneMiesto_predchodca + "," + presuvany + ")"+(id_Dmatrix[vsuvaneMiesto_predchodca][presuvany]));
//        System.out.println("(" + presuvany + "," + vsuvaneMiesto_Nasledovnik + ")"+(id_Dmatrix[presuvany][vsuvaneMiesto_Nasledovnik]));
//        System.out.println("(" + vyberaneMiesto_predchodca + "," + veberaneMiesto_nasledovnik + ")"+(id_Dmatrix[vyberaneMiesto_predchodca][veberaneMiesto_nasledovnik]));
        int plus = id_Dmatrix[vsuvaneMiesto_predchodca][presuvany]
                + id_Dmatrix[presuvany][vsuvaneMiesto_Nasledovnik]
                + id_Dmatrix[vyberaneMiesto_predchodca][veberaneMiesto_nasledovnik];
        return minus + plus;

    }

    public int vyratajUsporuPriZmazani(int predchodca, int mazany, int nasledovnik) {
        return -id_Dmatrix[predchodca][mazany]
                - id_Dmatrix[mazany][nasledovnik]
                + id_Dmatrix[predchodca][nasledovnik];

    }

}
