package com.example.annacamero.restaurantapp;

public class InfoPlat {
    private String id;
    private String nom;
    private String ingredients;
    private String tipus;
    private String preu;
    private Boolean marcat;

    public InfoPlat(String id, String nom, String ingredients, String tipus, String preu) {
        this.id = id;
        this.nom = nom;
        this.ingredients = ingredients;
        this.tipus=tipus;
        this.preu = preu;
        this.marcat=false;
    }

    public String getId(){return id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getTipus(){return tipus;}

    public String getPreu() {
        return preu;
    }

    public void setPreu(String preu) {
        this.preu = preu;
    }

    public Boolean getMarcat() {
        return marcat;
    }

    public void setMarcat(Boolean marcat) {
        this.marcat = marcat;
    }
}
