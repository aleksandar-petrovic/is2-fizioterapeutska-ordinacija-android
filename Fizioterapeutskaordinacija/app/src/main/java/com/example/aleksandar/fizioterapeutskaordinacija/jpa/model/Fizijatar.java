package com.example.aleksandar.fizioterapeutskaordinacija.jpa.model;

/**
 * Created by Aleksandar on 9.7.2015..
 */
public class Fizijatar {

    int id;
    String ime;
    String prezime;
    String spec;

    public Fizijatar(int id, String ime, String prezime, String spec) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.spec = spec;
    }

    public int getId() {
        return id;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime() + " - " + getSpec();
    }
}
