/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import java.sql.*;
import java.util.TimeZone;
import javax.swing.JOptionPane;

/**
 * Trieda zabezpečujúca prepojenie s databázou
 *
 * @author rendo
 */
public class DBConection {

    private String USERNAME;
    private String PASSWORD;
    private String CONNECT_STRING;
    private Connection conn;

    /**
     * koštruktor
     *
     * @param username
     * @param password
     * @param adresa
     * @param nazovSchemy
     */
    public DBConection(String username,
            String password,
            String adresa,
            String nazovSchemy) {
        USERNAME = username;
        PASSWORD = password;
        System.out.println(USERNAME);
        System.out.println(PASSWORD);
        System.out.println(adresa);
        System.out.println(nazovSchemy);

        CONNECT_STRING = "jdbc:mysql://" + adresa + "/" + nazovSchemy + "?serverTimezone=" + TimeZone.getDefault().getID();
        try {
            conn = DriverManager.getConnection(CONNECT_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Getter na conn
     *
     * @return
     */
    public Connection getConnection() {
        return conn;
    }
}
