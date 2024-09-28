/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
public class MesProduits {
    VBox root = new VBox();
    HBox totalHbox = new HBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    private int utilisateurCourant;
    SiteVentes main = SiteVentes.getInstance();
    Button showButton = new Button("Proposer");
    Button removeButton = new Button("Remove");

    Label titleLabel = new Label("Listes Mes Produits");
    Label totalLabel = new Label("Total");

    TableColumn<Produits, String> Titre = new TableColumn<>("Titre");
    TableColumn<Produits, String> Description = new TableColumn<>("Description");
    TableColumn<Produits, Integer> Categorie = new TableColumn<>("categorie");
    TableColumn<Produits, Timestamp> dateDebut = new TableColumn<>("debut");
    TableColumn<Produits, Timestamp> dateFin = new TableColumn<>("fin");
    TableColumn<Produits, Integer> prix = new TableColumn<>("Prix");
    TableColumn<Produits, Integer> show = new TableColumn<>("show");

    TableView<Produits> produitTable = new TableView<>();
    private Connection connectBdD;
    
    ObservableList<Produits> produitList = FXCollections.observableArrayList();
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        removeButton.getStyleClass().add("Button");
        showButton.getStyleClass().add("Button");
    }
    
    private void addValues(){
        root.getChildren().addAll(titleLabel,produitTable,totalHbox);
        root.setSpacing(15);
        totalHbox.setSpacing(20);
        totalHbox.getChildren().addAll(showButton,removeButton);
    }
    
    private ResultSet executeQuery(String query) throws SQLException{
        Statement stmt = connectBdD.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
    
    private String getCategorieName(Integer categorieId) throws SQLException{
        
        String query = "SELECT nom FROM categorie1 WHERE id =" +categorieId;
        ResultSet rs = executeQuery(query);
        String categorieName = "";
        if(rs.next()){
            categorieName = rs.getString("nom");
        }
        return categorieName;
    }
    
    private void addColumns(){
        produitTable.getColumns().addAll(Titre,Description,Categorie,dateDebut,dateFin,prix,show); 
        Titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("cat"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("debut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prixbase"));
        show.setCellValueFactory(new PropertyValueFactory<>("show"));
        
        Categorie.setCellFactory(cellData -> {
             TableCell<Produits, Integer> cell = new TableCell<>();
             cell.textProperty().bind(Bindings.createStringBinding(()->{
                 Integer catId = cell.getItem();
                 String categorieName = getCategorieName(catId);
                 return categorieName;
             },cell.itemProperty()));
             return cell;
        });
    }
    
     private void initWindow(){
        window.setScene(scene);
        window.setWidth(800);
        window.setHeight(600);
        window.setTitle("Mes Produits");
    }
    
    public MesProduits() {
        this.utilisateurCourant = main.getUtilisateurCourant();
        if(utilisateurCourant <= 0){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous pouvez consulter vos produits!");
                    a.setContentText("Vous etes besoin d'etre connecté!");
                    a.showAndWait();
                    window.close();
                    return;
               }
        addCss();
        initWindow();
        addColumns();
        addValues();
        System.out.println("add " +utilisateurCourant);
        
        try {
            try {
                this.connectBdD = connection.defautConnect();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            removeButton.setOnAction(event -> {
                Produits selectedProduit = produitTable.getSelectionModel().getSelectedItem();
                
                if(selectedProduit == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur!");
                    a.setContentText("Please Select a product!");
                    a.showAndWait();
                    return;
                }

                try {
                    PreparedStatement pst = connectBdD.prepareStatement("DELETE FROM objet1 WHERE id = ?");
                    pst.setInt(1, selectedProduit.getId());
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(ListeProduits.class.getName()).log(Level.SEVERE, null, ex);
                }

                produitList.remove(selectedProduit);
            });
            
            showButton.setOnAction(event -> {
                Produits selectedProduit = produitTable.getSelectionModel().getSelectedItem();
                
                if(selectedProduit == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur!");
                    a.setContentText("Please Select a product!");
                    a.showAndWait();
                    return;
                }

                try {
                    PreparedStatement pst = connectBdD.prepareStatement("UPDATE objet1 SET show = ? WHERE id = ?");
                    pst.setInt(1, 1);
                    pst.setInt(2, selectedProduit.getId());
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Vous avez publier le produit!");
                    window.close();
            } catch (SQLException ex) {
                Logger.getLogger(ListeProduits.class.getName()).log(Level.SEVERE, null, ex);
            }
            });

            PreparedStatement pst = connectBdD.prepareStatement("SELECT * From objet1 where proposepar = ?");
            pst.setInt(1,utilisateurCourant);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                    produitList.add(new Produits(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getInt("categorie"),
                    rs.getTimestamp("debut"),
                    rs.getTimestamp("fin"),
                    rs.getInt("prixbase"),
                    rs.getInt("proposepar"),
                    rs.getInt("show")));
                    produitTable.setItems(produitList);
                   
                }; 
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        window.show();

    }
}
