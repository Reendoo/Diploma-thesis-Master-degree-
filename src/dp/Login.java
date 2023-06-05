/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JOptionPane;


/**
 * Trieda na ukladanie inicializacnych dat aplikacie
 * @author rendo
 */
public class Login implements Serializable {
    private String url;
    private String schema;
    private String login;
    private String password;
    private String apiKey;
    private String VLN;
    private static final long serialVersionUID = 1L;
    /**
     * koštruktor
     */
    public Login() {
    }
    /**
     * Konštruktor
     * @param url
     * @param schema
     * @param login
     * @param password
     * @param apiKey
     * @param VLN 
     */
    public Login(String url, String schema, String login, String password, String apiKey, String VLN) {
        this.url = url;
        this.schema = schema;
        this.login = login;
        this.password = password;
        this.apiKey = apiKey;
        this.VLN = VLN;
    }
    /**
     * Getter na url k DB
     * @return 
     */
    public String getUrl() {
        return url;
    }
    /**
     * Setter url k DB
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Getter na nazovSchemy
     * @return 
     */
    public String getSchema() {
        return schema;
    }
    /**
     * Setter na nazov schemy
     * @param schema 
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }
    /**
     * Getter na login do db
     * @return 
     */
    public String getLogin() {
        return login;
    }
    /**
     * Setter na login do db
     * @param login 
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Getter na heslo do db
     * @return 
     */
    public String getPassword() {
        return password;
    }
    /**
     * Setter na heslo do db
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * getter na api kluc k sms brane
     * @return 
     */
    public String getApiKey() {
        return apiKey;
    }
    /**
     * setter na api key k sms brane
     * @param apiKey 
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    /**
     * getter na vln cislo do sms brany
     * @return 
     */
    public String getVLN() {
        return VLN;
    }
    /**
     * setter na vln cislo
     * @param VLN 
     */
    public void setVLN(String VLN) {
        this.VLN = VLN;
    }
    /**
     * Zapisanie inicializacnych dat pre applikaciu do suboru
     * @param login
     * @param url 
     */
    public static void zapisDoSuboru(Login login, String url) {
        try {
            FileOutputStream f = new FileOutputStream(new File(url));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(login);
            o.close();
            f.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
    }
    /**
     * Nacitanie inicializacnych dat pre appku
     * @param url
     * @return 
     */
    public static Login readZoSuboru(String url) {
        try {            
            FileInputStream fi = new FileInputStream(new File(url));
            ObjectInputStream oi = new ObjectInputStream(fi);            
            Login l= (Login) oi.readObject();
            oi.close();
            fi.close();
            return l;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return null;
    }
    /**
     * Retezcova reprezenacia login
     * @return 
     */
    @Override
    public String toString() {
        return "Login{" + "url=" + url + ", schema=" + schema + ", login=" + login + ", password=" + password + ", apiKey=" + apiKey + ", VLN=" + VLN + '}';
    }

}
