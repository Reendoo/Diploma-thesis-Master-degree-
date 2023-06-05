/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

import java.util.LinkedList;

/**
 * Trieda reprezentujúca hranu grafu
 *
 * @author rendo
 */
public class Hrana {

    protected String id;
    protected Uzol srcUzol;
    protected Uzol destUzol;
    private LinkedList<Suradnice> suradnice;
    protected int dlzka;

    /**
     * Konštruktor hrany
     *
     * @param id - id_hrany
     * @param srcUzol -zaciatocny uzol
     * @param destUzol- koncovy uzol
     * @param dlzka - dlzka hrany v m
     * @param sur - pole suradnic "zaciatok, lomenia cesty, koniec"
     */
    public Hrana(String id, Uzol srcUzol, Uzol destUzol, int dlzka, LinkedList<Suradnice> sur) {
        this.id = id;
        this.srcUzol = srcUzol;
        this.destUzol = destUzol;
        this.dlzka = dlzka;
        this.suradnice = sur;
    }

    /**
     * Konštruktor hrany
     *
     * @param id - id_hrany
     * @param srcUzol -zaciatocny uzol
     * @param destUzol- koncovy uzol
     * @param dlzka - dlzka hrany v m
     */
    public Hrana(String id, Uzol srcUzol, Uzol destUzol, int dlzka) {
        this.id = id;
        this.srcUzol = srcUzol;
        this.destUzol = destUzol;
        this.dlzka = dlzka;
        this.suradnice = new LinkedList<>();
    }
    /**
     * Getter na id hrany
     * @return 
     */
    public String getId() {
        return id;
    }
    /**
     * Getter na suradnice
     * @return 
     */
    public LinkedList<Suradnice> getSuradnice() {
        return suradnice;
    }

    @Override
    public String toString() {
        return "Hrana{" + "id=" + id + ", srcNode=" + srcUzol + ", destNode=" + destUzol + ", length=" + dlzka + '}';
    }

}
