package com.example.annacamero.restaurantapp;

public class Comanda {
    private String nom;
    private int taula;
    private String referencia;
    private int quantitat;
    private Double preu;
    private boolean fet;

    public Comanda() {
    }



    public Comanda(String nom, int taula, String referencia, int quantitat,Double preu, boolean fet) {
        this.nom = nom;
        this.taula = taula;
        this.referencia = referencia;
        this.quantitat = quantitat;
        this.preu = preu;
        this.fet = fet;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaula() {
        return taula;
    }

    public void setTaula(int taula) {
        this.taula = taula;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public boolean isFet() {
        return fet;
    }

    public void setFet(boolean fet) {
        this.fet = fet;
    }
}