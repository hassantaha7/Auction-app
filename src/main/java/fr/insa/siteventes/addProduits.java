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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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
public class addProduits {
    VBox root = new VBox();
    HBox buttonsBox = new HBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    SiteVentes main = SiteVentes.getInstance();
    private int utilisateurCourant;
    int categorie = 0;
    
    Label titleLabel = new Label("Nouveau Produit");
    
    Label titreProduit = new Label("Titre:");
    TextField titreText = new TextField();
    
    Label descProduit = new Label("Description:");
    TextField descText = new TextField();
    
    Label catProduit = new Label("Catégorie:");
    //TextField catText = new TextField();
    ChoiceBox<String> catChoice = new ChoiceBox<>();
    
    Label datedeb = new Label("Date Debut:");
    DatePicker datedebPick = new DatePicker();

    Label datefin = new Label("Date Fin:");
    DatePicker datefinPick = new DatePicker();
    
    Label prixProduit = new Label("Prix base:");
    TextField prixText = new TextField();
    
    Button addButton = new Button("Add");
    Button cancelButton = new Button("Cancel");
    
    private Connection connectBdD;
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        titreProduit.getStyleClass().add("labelForm");
        descProduit.getStyleClass().add("labelForm");
        catProduit.getStyleClass().add("labelForm");
        datedeb.getStyleClass().add("labelForm");
        datefin.getStyleClass().add("labelForm");
        prixProduit.getStyleClass().add("labelForm");
        addButton.getStyleClass().add("Button");
        cancelButton.getStyleClass().add("Button");
        root.getStyleClass().add("loginWindow");
    }
    
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(800);
        window.setTitle("Add Produits");
        window.show();
    }
    
    
    public addProduits(){
        this.utilisateurCourant = main.getUtilisateurCourant();
        if(utilisateurCourant <= 0){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous pouvez pas ajouter un produit!");
                    a.setContentText("Vous etes besoin d'etre connecté!");
                    a.showAndWait();
                    window.close();
                    return;
               }
        initWindow();
        addCss();
        root.getChildren().add(titleLabel);
        root.getChildren().addAll(titreProduit,titreText);
        root.getChildren().addAll(descProduit, descText);
        catChoice.getItems().addAll("Habit","Meuble");
        catChoice.setValue("Habit");
        catChoice.getStyleClass().add("Button");
        root.getChildren().addAll(catProduit,catChoice);    
        root.getChildren().addAll(datedeb,datedebPick);
        root.getChildren().addAll(datefin,datefinPick);
        root.getChildren().addAll(prixProduit,prixText);
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
        
        System.out.println("add " +utilisateurCourant);  
        addButton.setOnAction((t) -> {
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            String titre = this.titreText.getText();
            String description = this.descText.getText();
            String prx = prixText.getText();
            switch(catChoice.getValue()){
                case "Habit":
                    categorie = 1;
                    break;
                case "Meuble":
                    categorie = 2;
                    break;
            }
             if(titre.isEmpty() || description.isEmpty() || prx.isEmpty()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Empty fields!");
                    a.setContentText("Remplissez toutes les champs!");
                    a.showAndWait();
                    return;
            }
            int prix = Integer.parseInt(prx);
            
            System.out.println("value:" +categorie);
            
            if (java.sql.Date.valueOf(datefinPick.getValue()).before(java.sql.Date.valueOf(datedebPick.getValue()))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Date Incorrecte!");
                a.setContentText("Date du fin va etre plus grand que date debut!");
                a.showAndWait();
                return;
            }
            
            PreparedStatement pst = connectBdD.prepareStatement("INSERT INTO objet1 (titre,description,categorie,debut,fin,prixbase,proposepar,show) VALUES (?,?,?,?,?,?,?,'0')");
            pst.setString(1,titre);
            pst.setString(2,description);
            pst.setInt(3,categorie);
            pst.setDate(4,java.sql.Date.valueOf(datedebPick.getValue()));
            pst.setDate(5,java.sql.Date.valueOf(datefinPick.getValue()));
            pst.setInt(6,prix);
            pst.setInt(7,utilisateurCourant);

            int rs = pst.executeUpdate();

                if (rs > 0) {
                        JOptionPane.showMessageDialog(null, "Product added sucess!");
                        window.close();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("bad product");
                        a.setContentText("product incorrect");
                        a.showAndWait();
                } 
            
            System.out.println("j'ajoute le produit " + titre + "(" + description + ")");
            
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
  }
}

