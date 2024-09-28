/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

/**
 *
 * @author Hassan TAHA 
 */

// Création de la classe catégorie : id, Nom
public class Categorie {
    private int id;
    private String nom;
    
    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the titre
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param titre the titre to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
}
