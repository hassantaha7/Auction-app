/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

/**
 *
 * @author Hassan TAHA
 */
public class utilisateur {

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    private final int id;
    private String nom;
    private String email;
    private String pass;
    private String codepostal;
    private int role;

    public utilisateur(int id, String nom, String email, String pass, int role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.pass = pass;
        this.role = role;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getRole(){
        return role;
    }
    
    public void setRole(int role){
        this.role = role;
    }
}
