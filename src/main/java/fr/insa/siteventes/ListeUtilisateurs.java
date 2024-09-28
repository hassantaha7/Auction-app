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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Hassan TAHA
 */
public class ListeUtilisateurs extends VBox{
   
    VBox root = new VBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    Label titleLabel = new Label("Listes Des Users");
    Button removeButton = new Button("Remove");
    SiteVentes main = SiteVentes.getInstance();
    private int role1;
    
    
    TableColumn<utilisateur, String> nom = new TableColumn<>("nom");
    TableColumn<utilisateur, String> email = new TableColumn<>("email");
    TableColumn<utilisateur, String> pass = new TableColumn<>("pass");
    TableColumn<utilisateur, Integer> role = new TableColumn<>("role");
    TableView<utilisateur> userTable = new TableView<>();
    
    private Connection connectBdD;
    
    ObservableList<utilisateur> userList = FXCollections.observableArrayList();

     private void addValues(){
        root.getChildren().addAll(titleLabel,userTable,removeButton);
    }
     
    private void addColumns(){
        userTable.getColumns().addAll(nom,pass,email,role); 
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

    }
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        removeButton.getStyleClass().add("Button");
    }
        
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(600);
        window.setTitle("Users");
    }
     
    public ListeUtilisateurs() {
        this.role1 = main.getRole();
        if(role1 != 1){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous etes pas administrateur");
                    a.setContentText("Vous avez pas le droit de voir la liste des utilisateurs");
                    a.showAndWait();
                    window.close();
                    return;
               }
        
        addCss();
        initWindow();
        addColumns();
        addValues();
        removeButton.setOnAction(event -> {
                utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
                
                if(selectedUser == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur!");
                    a.setContentText("Please Select a user!");
                    a.showAndWait();
                    return;
                }
                
                try {
                    PreparedStatement pst = connectBdD.prepareStatement("DELETE FROM utilisateur1 WHERE id = ?");
                    pst.setInt(1, selectedUser.getId());
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(ListeProduits.class.getName()).log(Level.SEVERE, null, ex);
                }
                userList.remove(selectedUser);
            });
        
        
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            PreparedStatement pst = connectBdD.prepareStatement("SELECT * From utilisateur1");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                    userList.add(new utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("pass"),
                    rs.getInt("role")));
                    userTable.setItems(userList);
                }; 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        window.show();
    }
    
}