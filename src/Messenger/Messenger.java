/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messenger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Trieda zabezpecujuca komnikaciu s O2 SMS Connector
 *
 * @author rendo
 */
public class Messenger {

    private final String apiKey;
    private final String send = "https://api-tls12.smstools.sk/3/send_batch";
    private final String remaining = "https://api-tls12.smstools.sk/3/credit_remaining";
    private final String receiving = "https://api-tls12.smstools.sk/3/sms_get_received";
    private HttpURLConnection con = null;   
    private final String VLN;
    /**
     * Konstruktor triedy
     *
     * @param apiKey
     * @param vln
     */
    public Messenger(String apiKey, String vln) {
        this.apiKey = apiKey;
        this.VLN = vln;
    }

   /**
    * Pripravenie pripojenia k fukcii API
    * @param surl 
    */
    public void prepareConection(String surl) {
        URL url;
        try {
            url = new URL(surl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName()+ ": "+e.getMessage());
        }
    }




    /**
     * Metoda sluziaca na poslanie davky SMS sprav
     *
     * @param sms
     * @return
     */
    public String posliSMS(SMS sms) {
        try {
            sms.setApiKey(apiKey);
            String JSON_sms = sms.toString(); //vytvorenie JSON formatu 
            prepareConection(send); //privenie požiadavky
            OutputStream os = con.getOutputStream();
            byte[] input = JSON_sms.getBytes("utf-8");
            os.write(input, 0, input.length);//odoslanie poziadavky
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            os.flush(); os.close();
            String s = "";String responseLine = null;//spracovanie odpovede            
            while ((responseLine = br.readLine()) != null) {
                s += responseLine.trim();
            }
            br.close();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONObject jSONObject = (JSONObject) obj;
            return "" + jSONObject.get("id") + " " + jSONObject.get("note");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return "problem";
    }


    /**
     * Metoda sluziaca na zistenie stavu kreditu
     *
     * @return
     */
    public String zistiKredit() {
        try {
            String JSON_kredit = "{\n"
                    + "\"auth\" : { \"apikey\" : \"" + apiKey + "\"},\n"
                    + "\"data\" : {}}";
            //System.out.println(JSON_kredit);
            prepareConection(remaining);
            OutputStream os = con.getOutputStream();
            byte[] input = JSON_kredit.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
//            os.flush();
//            os.close();
            String s = "";
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                s += (responseLine.trim());
            }
            br.close();
            //System.out.println(s);            
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONObject jSONObject = (JSONObject) obj;
            String id = "" + jSONObject.get("id");
            JSONObject o = (JSONObject) jSONObject.get("data");
            JSONObject o1 = (JSONObject) o.get("credit");
            JSONObject oo1 = (JSONObject) o.get("sms");
//            String cena = "Kredit:" + o1.get("amount") + o1.get("currency");
//            String sms = "Počet SMS" + oo1.get("amount");
//            System.out.println(id + "@" + o1.get("amount") + "@" + oo1.get("amount"));
            return id + "@" + o1.get("amount") + "@" + oo1.get("amount");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return "problem";
    }

    /**
     *
     * Metoda sluziaca na stiahnutie davky prijatych SMS
     *
     * @return
     */
    public PrijataDavka stiahniSMS() {
        PrijataDavka pDavka = null;
        try {
            String JSONprijate = "  {\n"
                    + "\"auth\" : { \"apikey\" : \"" + apiKey + "\"},\n"
                    + "\"data\" : {}}";

//          System.out.println(JSONprijate);
            prepareConection(receiving);
            OutputStream os = con.getOutputStream();
            byte[] input = JSONprijate.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));

            String s = "";
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                s += responseLine.trim();
            }
            br.close();
            System.out.println(s);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONObject jSONObject = (JSONObject) obj;

            if (!("" + jSONObject.get("id")).equals("OK")) {
                return pDavka = new PrijataDavka(("" + jSONObject.get("id") + " " + jSONObject.get("note")).replace("null", ""));
            } else {
                pDavka = new PrijataDavka(("" + jSONObject.get("id") + " " + jSONObject.get("note")).replace("null", ""));
                JSONObject o = (JSONObject) jSONObject.get("data");
                JSONArray spravy = (JSONArray) o.get("messages");
                for (Object object : spravy) {
                    JSONObject jop = (JSONObject) object;
                    if (!("" + jop.get("type")).equals("SMS")) {
                        continue; // bolo to volanie
                    } else {
                        pDavka.pridajSpravu(new PrijataSprava("" + jop.get("sender_phonenr"),
                                "" + jop.get("text"),
                                "" + jop.get("datetime")));
                    }
                }
                return pDavka;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
        }
        return pDavka;
    }

//    public static void main(String[] args) {
//        Messenger msg = new Messenger("60d97034-48b1-499d-98d2-f60ed38bfdd2",
//                "https://api-tls12.smstools.sk/3/send_batch",
//                "https://api-tls12.smstools.sk/3/credit_remaining",
//                "https://api-tls12.smstools.sk/3/sms_get_received"/*,
//                "https://api-tls12.smstools.sk/3/sms_get_state"*/);
//
//        ArrayList<String> prijemcovia = new ArrayList<>();
//        prijemcovia.add("+421944339622");
//        prijemcovia.add("+421787");
//        prijemcovia.add("aaaa");
//        prijemcovia.add("+421944546262");
//        SMS sms = new SMS(msg.getApiKey(), "Testujem zase znova", "Rendo", prijemcovia);
//        //msg.posliSMS(sms);
//        //msg.posliSMSsOverenim(sms);
//        //msg.zistiKredit();
//        PrijataDavka d = msg.stiahniSMS();
//        System.out.println(d.toString());
//    }
    /**
     * Parsovacia metoda z stringu datumu spravi Date
     *
     * @param d
     * @return
     */
    public static Date parseDate(String d) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        try {
            Date date = simpleDateFormat.parse(d);
            return date;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage() + ": " + e.getLocalizedMessage());
            
        }
        return null;
    }

}
