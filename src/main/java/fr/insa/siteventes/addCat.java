/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Hassan TAHA
 */
public class addCat {
    VBox root = new VBox();
    HBox buttonsBox = new HBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    SiteVentes main = SiteVentes.getInstance();
    private int utilisateurCourant;
    private int role;
    
    Label titleLabel = new Label("Nouveau Categorie");
    
    Label nomProduit = new Label("Nom:");
    TextField nomText = new TextField();
    
    Button addButton = new Button("Add");
    Button cancelButton = new Button("Cancel");
    
    private Connection connectBdD;
    
    private void addButtonToWind(){
        root.getChildren().add(titleLabel);
        root.getChildren().addAll(nomProduit,nomText);
        buttonsBox.getChildren().addAll(addButton, cancelButton);
        root.getChildren().addAll(buttonsBox);
        root.setSpacing(15);
        buttonsBox.setSpacing(20);
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
         @Override
        public void handle(ActionEvent event){
            window.close();
            }
        });
    }
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        addButton.getStyleClass().add("Button");
        cancelButton.getStyleClass().add("Button");
    }
    
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(600);
        window.setTitle("Add Categorie");
        window.show();
    }
    
    
    public addCat(){
        this.utilisateurCourant = main.getUtilisateurCourant();
        this.role = main.getRole();
        if(role != 2){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous avez pas le droit d'ajouter une categorie");
                    a.setContentText("Vous etes pas administrateur");
                    a.showAndWait();
                    window.close();
                    return;
               }
        initWindow();
        addCss();
        addButtonToWind();
        System.out.println("add Cat role" +role);

        addButton.setOnAction((t) -> {
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            String nom = this.nomText.getText();
            
            if(nom.isEmpty()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Empty fields!");
                    a.setContentText("Remplissez toutes les champs!");
                    a.showAndWait();
            }
            
            System.out.println("j'ajoute le produit " + nom);
            PreparedStatement pst = connectBdD.prepareStatement("INSERT INTO categorie1 (nom) VALUES (?)");
            pst.setString(1,nom);
            
            int rs = pst.executeUpdate();
            if (rs > 0) {
                    JOptionPane.showMessageDialog(null, "Categorie added sucess!");
                    window.close();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("bad categorie");
                    a.setContentText("categorie incorrect");
                    a.showAndWait();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    }
    
}
