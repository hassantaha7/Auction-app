/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Hassan TAHA
 */
public class listCat {
    
    VBox root = new VBox();
    HBox totalHbox = new HBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    Label titleLabel = new Label("Listes Des Categories");
    private int role;
    SiteVentes main = SiteVentes.getInstance();
    Button removeButton = new Button("Remove");

    
    TableColumn<Categorie, Integer> id = new TableColumn<>("id");
    TableColumn<Categorie, String> nom = new TableColumn<>("Nom");
   
    TableView<Categorie> CatTable = new TableView<>();
    private Connection connectBdD;
    
    ObservableList<Categorie> categorieList = FXCollections.observableArrayList();
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        removeButton.getStyleClass().add("Button");
    }
    
    private void addValues(){
        root.getChildren().addAll(titleLabel,CatTable,totalHbox,removeButton);
    }
    
    private void addColumns(){
        CatTable.getColumns().addAll(id,nom); 
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    }
    
     private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(600);
        window.setTitle("Categories");
    }
     
    @SuppressWarnings("empty-statement")
    public listCat() {
        this.role = main.getRole();
        addCss();
        initWindow();
        addColumns();
        addValues();
        
            removeButton.setOnAction(event -> {
                if(role == 2){
                    Categorie selectedCat = CatTable.getSelectionModel().getSelectedItem();

                    if(selectedCat == null){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Erreur!");
                        a.setContentText("Please Select a categorie!");
                        a.showAndWait();
                        return;
                    }

                    try {
                        PreparedStatement pst = connectBdD.prepareStatement("DELETE FROM categorie1 WHERE id = ?");
                        pst.setInt(1, selectedCat.getId());
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Categorie removed!");
                    } catch (SQLException ex) {
                        Logger.getLogger(ListeProduits.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    categorieList.remove(selectedCat);
                }
                else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous avez pas le droit de supprimer une categorie");
                    a.setContentText("Vous etes pas administrateur");
                    a.showAndWait();
                    window.close();
                    return;
                }
           });
       
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
            PreparedStatement pst = connectBdD.prepareStatement("SELECT * From categorie1");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                    //this.main.setUtilisateurCourant(id);
                    categorieList.add(new Categorie(
                    rs.getInt("id"),
                    rs.getString("nom")));
                    CatTable.setItems(categorieList);
                   
                }; 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        window.show();
    }       
}
