package com.esiea.mysuperhero.presentation.view;

public class Item {

    private String nom;

    private String prenom;

    private String diplome;

    private String ville;

    public Item(String nom, String prenom, String diplome, String ville){
        this.nom = nom;
        this.prenom = prenom;
        this.diplome = diplome;
        this.ville = ville;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDiplome() {
        return diplome;
    }

    public String getVille() {
        return ville;
    }
}
