/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import java.sql.Timestamp;

/**
 *
 * @author Hassan TAHA
 */
public class Produits {
    
    private int id;
    private String titre;
    private String description;
    private int categorie;
    private String categorieNom;
    private Timestamp debut;
    private Timestamp fin;
    private int prixbase;
    private int proposepar;
    private int show;


    public Produits(int id, String titre, String description, int categorie, Timestamp debut, Timestamp fin, int prixbase, int proposepar,int show) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.debut = debut;
        this.fin = fin;
        this.prixbase = prixbase;
        this.proposepar = proposepar;
        this.show = show;
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
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     * @return categorie 
     */
    public int getCat() {
        return categorie;
    }

    /**
     * @param cat the id to set
     */
    public void setCat(int cat) {
        this.categorie = cat;
    }

    /**
     * @return the debut
     */
    public Timestamp getDebut() {
        return debut;
    }

    /**
     * @param debut the debut to set
     */
    public void setDebut(Timestamp debut) {
        this.debut = debut;
    }

    /**
     * @return the fin
     */
    public Timestamp getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    /**
     * @return the prixbase
     */
    public int getPrixbase() {
        return prixbase;
    }

    /**
     * @param prixbase the prixbase to set
     */
    public void setPrixbase(int prixbase) {
        this.prixbase = prixbase;
    }
    
    /**
     * 
     * @return the proposepar 
     */
    public int getProposepar(){
        return proposepar;
    }
    
    /**
     * 
     * @param proposepar the proposepar to set
     */
    public void setProposepar(int proposepar){
        this.proposepar = proposepar;
    }
    
    public int getShow(){
        return show;
    }
    
    public void setShow(int show){
        this.show = show;
    }
}

            
    
    

