package com.example.aleksandar.fizioterapeutskaordinacija.jpa.model;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aleksandar on 12.7.2015..
 */
public class Obaveza {

    private int id;
    private Date datum;
    private String naziv;
    private String opis;
    private String vremeOd;
    private String vremeDo;
    private boolean fizijatar;
    private boolean fizioterapeut;
    private boolean pregled;
    private boolean vezbe;

    public boolean isPregled() {
        return pregled;
    }

    public void setPregled(boolean pregled) {
        this.pregled = pregled;
    }

    public boolean isVezbe() {
        return vezbe;
    }

    public void setVezbe(boolean vezbe) {
        this.vezbe = vezbe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(String vremeOd) {
        this.vremeOd = vremeOd;
    }

    public String getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(String vremeDo) {
        this.vremeDo = vremeDo;
    }

    public boolean isFizijatar() {
        return fizijatar;
    }

    public void setFizijatar(boolean fizijatar) {
        this.fizijatar = fizijatar;
    }

    public boolean isFizioterapeut() {
        return fizioterapeut;
    }

    public void setFizioterapeut(boolean fizioterapeut) {
        this.fizioterapeut = fizioterapeut;
    }

    public static Obaveza getObjectFromJSON(JSONObject json) {
        Obaveza obaveza = new Obaveza();
        try {
            obaveza.setId(json.getInt("id"));
            obaveza.setDatum(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("datum")));
            obaveza.setNaziv(json.getString("naziv"));
            obaveza.setOpis(json.getString("opis"));
            obaveza.setVremeOd(json.getString("vremeOd"));
            obaveza.setVremeDo(json.getString("vremeDo"));
            obaveza.setFizijatar(json.getBoolean("fizijatar"));
            obaveza.setFizioterapeut(json.getBoolean("fizioterapeut"));
            obaveza.setPregled(json.getBoolean("pregled"));
            obaveza.setVezbe(json.getBoolean("vezbe"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obaveza;
    }

}
