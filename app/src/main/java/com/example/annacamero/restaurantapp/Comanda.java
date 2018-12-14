package com.example.annacamero.restaurantapp;

public class Comanda {
    private String nom;
    private int taula;
    private String referencia;
    private int quantitat;
    private boolean fet;

    public Comanda() {
    }

    public Comanda(String nom, int taula, String referencia, int quantitat, boolean fet) {
        this.nom = nom;
        this.taula = taula;
        this.referencia = referencia;
        this.quantitat = quantitat;
        this.fet = fet;
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