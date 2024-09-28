/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import javax.swing.JOptionPane;

/**
 *
 * @author Hassan TAHA
 */
public class deconnexion {
    int utilisateurCourant;
    int role;
    SiteVentes main1 = SiteVentes.getInstance();
    
    public deconnexion(int utilisateurCourant,int role){
        this.utilisateurCourant = utilisateurCourant;
        JOptionPane.showMessageDialog(null, "Vous etes bien deconnecté!");
        this.role = role;
        main1.utilisateurCourant = 0;
        main1.role = 0;

    }
}
