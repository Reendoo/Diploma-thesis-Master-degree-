/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author rendo
 */
public class Refresher {

    public static Refresher r = new Refresher();

    public Refresher() {
    }
    JFMainFrame mainframe;

    public void setRefresher(JFMainFrame mainframe) {
        this.mainframe = mainframe;
    }

    public void refreshKontajnery() {
        mainframe.refreshKontajnery();
    }

    public void refreshVozidla() {
        mainframe.refreshVozidla();
        mainframe.refreshVozidlaKapacitne();        
    }

    public void refresNespracovaneSpravy() {
        mainframe.refresNespracovaneSpravy();
    }

    public void refreshVybraneVozidla() {
        mainframe.refreshVybraneVozidla();
    }
    public void refreshProgresBar(int value){
        mainframe.refreshProgresBar(value);
        mainframe.validate();        
        mainframe.repaint();
    }

}
