/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenAlg;

import java.util.Random;

/**
 *
 * @author rendo
 */
class GenDisUnif {

    private int aMin;
    private int aMax;
    Random rnd;

    public GenDisUnif(int paMin, int paMax) {
        aMin = paMin;
        aMax = paMax;
        Random ran = new Random();
        rnd = new Random(ran.nextLong());
    }

    public int Generate() {
        return rnd.nextInt((aMax - aMin) + 1) + aMin;

    }
}
