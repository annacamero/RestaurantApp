package com.example.annacamero.restaurantapp;

public class InfoPlat {
    private String nom;
    private String ingredients;
    private String preu;

    public InfoPlat(String nom, String ingredients, String preu) {
        this.nom = nom;
        this.ingredients = ingredients;
        this.preu = preu;
    }

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

    public String getPreu() {
        return preu;
    }

    public void setPreu(String preu) {
        this.preu = preu;
    }
}
