/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graf;

/**
 * Pomocna trieda reprezentujuca gps suradnica
 *
 * @author rendo
 */
public class Suradnice {

    double x; //zemepisna sirka, latitude
    double y; //zemepisna dlzka, longtitude
    public final static double POLOMER_ZEME_V_KM = 6371;

    public Suradnice(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Suradnice{" + "x=" + x + ", y=" + y + '}';
    }

    /**
     * Metoda, ktora na zaklade haversinovej formulacie vyrata vzdialenosti
     * medzi 2 bodmi
     *
     * @param s1 - suradnica 1
     * @param s2 -suradnica 2
     * @return
     */
    public static double vyratajVzdialenostKM(Suradnice s1, Suradnice s2) {
        //x=lat, y = long
        double latDistance = Math.toRadians(s2.getX() - s1.getX());
        double lngDistance = Math.toRadians(s2.getY() - s1.getY());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(s1.getX())) * Math.cos(Math.toRadians(s2.getX()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c =  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return POLOMER_ZEME_V_KM * c;
    }

    public static double vyratajVzdialenostKMM(Suradnice s1, Suradnice s2) {
        //x=lat, y = long
        double lat1 = s1.x;
        double lat2 = s2.x;
        double lng1 = s1.y;
        double lng2 = s2.y;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return POLOMER_ZEME_V_KM * c * 1000; // convert to meters

    }

    public static double vyratajVzdialenostKMMM(Suradnice s1, Suradnice s2) {
        //x=lat, y = long
        double lat1 = s1.x;
        double lat2 = s2.x;
        double lng1 = s1.y;
        double lng2 = s2.y;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(lat1)
                * Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    public static double vyratajVzdialenostKMMMM(Suradnice s1, Suradnice s2) {
        //Lat = Y Long = X

        System.out.println("s1: " + s1.toString());
        System.out.println("s1: " + s2.toString());
        double lat1 = s1.x;
        double lat2 = s2.x;
        double lon1 = s1.y;
        double lon2 = s2.y;

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;

    }

    public static void main(String[] args) {
        Suradnice s1 = new Suradnice(18.6365407705307, 49.091756738888016);
        Suradnice s2 = new Suradnice(18.643718361854553, 49.09404708509785);

        Suradnice s4 = new Suradnice(49.091756738888016, 18.6365407705307);
        Suradnice s3 = new Suradnice(49.09404708509785, 18.643718361854553);
//        Suradnice s1 = new Suradnice(  0.1246,51.5007);
//        Suradnice s2 = new Suradnice( 74.0445,40.6892);
        System.out.println("Povodna: " + vyratajVzdialenostKM(s1, s2));
        System.out.println("Nova: " + vyratajVzdialenostKMM(s1, s2));
        System.out.println("Este novsia: " + vyratajVzdialenostKMMM(s1, s2));
        System.out.println("Esteeee novsia: " + vyratajVzdialenostKMMMM(s1, s2));
        System.out.println("*********************************");
        System.out.println("Povodna: " + vyratajVzdialenostKM(s3, s4));
        System.out.println("Nova: " + vyratajVzdialenostKMM(s3, s4));
        System.out.println("Este novsia: " + vyratajVzdialenostKMMM(s3, s4));
        System.out.println("Esteeee novsia: " + vyratajVzdialenostKMMMM(s3, s4));
    }
}
