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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Hassan TAHA
 */
public class Inscription {
    
    //Initialize window
    VBox root = new VBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    
    Label nomLabel = new Label("Nom:");
    TextField nomText = new TextField();
    
    Label prenomLabel = new Label("Prenom:");
    TextField prenomText = new TextField();
    
    Label emailLabel = new Label("Email:");
    TextField emailText = new TextField();
    
    Label passLabel = new Label("Password:");
    TextField passText = new PasswordField();
    
    Button signUpButton = new Button("Sign Up");
    Button cancelButton = new Button("Cancel");
 
    private Connection connectBdD;

    
    //Ajout des élements à la fenetre 
     private void addButtonToWind(){
        root.getChildren().addAll(nomLabel,nomText);        
        root.getChildren().addAll(prenomLabel,prenomText);        
        root.getChildren().addAll(emailLabel,emailText);
        root.getChildren().addAll(passLabel, passText);
        root.getChildren().add(signUpButton);
        root.setSpacing(15);
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
         @Override
        public void handle(ActionEvent event){
            window.close();
            }
        });
    }
    
    //Ajout du Css sur les élements
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        nomLabel.getStyleClass().add("labelForm");      
        prenomLabel.getStyleClass().add("labelForm");
        emailLabel.getStyleClass().add("labelForm");
        passLabel.getStyleClass().add("labelForm");
        root.getStyleClass().add("loginWindow");
        signUpButton.getStyleClass().add("Button");
        cancelButton.getStyleClass().add("Button");
    }
    
    //Dimensions de la fenetre
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(600);
        window.setTitle("Sign Up");
    }
   
    //Constructeur
    public Inscription(){
        addCss();
        initWindow();
        addButtonToWind();
        
        //Si le bouton sign up est cliquer 
        signUpButton.setOnAction((t) -> {
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            //recuperation les textes des champs
            String nom = this.nomText.getText();
            String prenom = this.prenomText.getText();
            String email = this.emailText.getText();
            String pass = this.passText.getText();
            
            //Test si les champs sont vides 
            if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Empty fields!");
                    a.setContentText("Remplissez toutes les champs!");
                    a.showAndWait();
            }
            
            System.out.println("je tente le signup de " + nom + "(" + pass + ")");
            
            //Insertion dans la table des utilisateurs
            PreparedStatement pst = connectBdD.prepareStatement("INSERT INTO utilisateur1 (nom,prenom,email,pass,role) VALUES (?,?,?,?,'0')");
            pst.setString(1,nom);
            pst.setString(2,prenom);
            pst.setString(3,email);
            pst.setString(4,pass);
            
            int rs = pst.executeUpdate();
            if (rs > 0) {
                    JOptionPane.showMessageDialog(null, "Sign Up sucess");
                    window.close();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("bad user");
                    a.setContentText("Insertion échoué");
                    a.showAndWait();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
        window.show();
    }  
}
