/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

/**
 * Trieda reprezentujuca uzol grafu
 *
 * @author rendo
 */
public class Uzol implements Comparable {

    protected int id;
    private Suradnice sur;
    //private double x_sur;
    //private double y_sur;

    /**
     * Konštruktor uzla
     *
     * @param id - id_uzla
     * @param x_sur - suradnica x
     * @param y_sur - suradnica y
     */
    public Uzol(int id, double x_sur, double y_sur) {
        this.id = id;
        //this.x_sur = x_sur;
        //this.y_sur = y_sur;
        this.sur = new Suradnice(x_sur, y_sur);
    }

    public int getId() {
        return id;
    }

/**
 * Getter
 * @return 
 */
    public double getX_sur() {
        return sur.x;//x_sur;
    }

/**
 * Getter
 * @return 
 */
    public double getY_sur() {
        return sur.y;//y_sur;
    }

    /**
     * Pomocná metóda na porovnanie id_uzla
     *
     * @param t - uzol
     * @return
     */
    @Override
    public int compareTo(Object t) {
        int id_uzla = ((Uzol) t).getId();
        return this.getId() - id_uzla;
    }

}
