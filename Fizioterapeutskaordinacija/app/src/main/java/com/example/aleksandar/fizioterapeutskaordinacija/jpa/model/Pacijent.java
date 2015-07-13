package com.example.aleksandar.fizioterapeutskaordinacija.jpa.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aleksandar on 13.7.2015..
 */
public class Pacijent implements Serializable {

    private int id;
    private String ime;
    private String prezime;
    private int idFizijatar;
    private int idFizioterapeut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getIdFizijatar() {
        return idFizijatar;
    }

    public void setIdFizijatar(int idFizijatar) {
        this.idFizijatar = idFizijatar;
    }

    public int getIdFizioterapeut() {
        return idFizioterapeut;
    }

    public void setIdFizioterapeut(int idFizioterapeut) {
        this.idFizioterapeut = idFizioterapeut;
    }

    public static Pacijent getObjectFromJSON(JSONObject json) {
        Pacijent pacijent = new Pacijent();
        try {
            pacijent.setId(json.getInt("id"));
            pacijent.setIme(json.getString("ime"));
            pacijent.setPrezime(json.getString("prezime"));
            pacijent.setIdFizijatar(json.getInt("idFizijatar"));
            pacijent.setIdFizioterapeut(json.getInt("idFizioterapeut"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pacijent;
    }

}
