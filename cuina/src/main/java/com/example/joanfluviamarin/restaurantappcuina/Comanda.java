package com.example.joanfluviamarin.restaurantappcuina;

public class Comanda {
    private String nom;
    private String taula;
    private String referencia;
    private String quantitat;
    private boolean fet;

    public Comanda(String nom, String taula, String referencia, String quantitat, boolean fet) {
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

    public String getTaula() {
        return taula;
    }

    public void setTaula(String taula) {
        this.taula = taula;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(String quantitat) {
        this.quantitat = quantitat;
    }

    public boolean isFet() {
        return fet;
    }

    public void setFet(boolean fet) {
        this.fet = fet;
    }
}
