/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Hassan TAHA
 */
public class login {
    
    VBox root = new VBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    
    Label nomLabel = new Label("Nom:");
    TextField nomText = new TextField();
    
    Label passLabel = new Label("Password:");
    TextField passText = new PasswordField();
    
    Button logButton = new Button("Login");
 
    private Connection connectBdD;
    Integer id;
    Integer role;
    SiteVentes main1 = SiteVentes.getInstance();
    
    public login() {
        addCss();
        initWindow();
        addButtonToWind();
        logButton.setOnAction((ActionEvent t) -> {
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String nom = this.nomText.getText();
            String pass = this.passText.getText();
            
            if(nom.isEmpty() || pass.isEmpty()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Champs Vide!");
                    a.setContentText("Remplissez tous les champs!");
                    a.showAndWait();
                    return;
            }
            
            System.out.println("je tente le login de " + nom + "(" + pass + ")");
            
            PreparedStatement pst = connectBdD.prepareStatement("SELECT * From utilisateur1 WHERE nom = ? AND pass = ?");
            pst.setString(1,nom);
            pst.setString(2,pass);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login sucess");
                    id = rs.getInt("id");
                    role = rs.getInt("role");
                    this.main1.setUtilisateurCourant(id);
                    this.main1.setRole(role);
                    System.out.println("utlisateur Courant:" +this.main1.getUtilisateurCourant());
                    window.close();
                } else {
                    this.main1.setUtilisateurCourant(0);
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("bad user");
                    a.setContentText("nom d'utilisateur ou mot de passe invalide");
                    a.showAndWait();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
        window.show();
    } 
    
     private void addButtonToWind(){
        root.getChildren().addAll(nomLabel,nomText);
        root.getChildren().addAll(passLabel, passText);
        root.getChildren().add(logButton);
        root.setSpacing(15);
    }
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");   
        nomLabel.getStyleClass().add("labelForm");
        passLabel.getStyleClass().add("labelForm");
        root.getStyleClass().add("loginWindow");
        logButton.getStyleClass().add("Button");
    }
    
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(400);
        window.setTitle("Login");
        window.show();
    }
}

