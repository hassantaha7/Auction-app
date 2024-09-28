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
import javafx.scene.control.ComboBox;
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
 * @author Hassan
 */
public class ListeProduits {
    
    VBox root = new VBox();
    HBox totalHbox = new HBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    Label titleLabel = new Label("Listes Des Produits");
    Button acheterButton = new Button("Acheter");
    SiteVentes main = SiteVentes.getInstance();
    private int utilisateurCourant;
    
    //Tableau des produits
    TableColumn<Produits, Integer> id = new TableColumn<>("id");
    TableColumn<Produits, String> Titre = new TableColumn<>("Titre");
    TableColumn<Produits, String> Description = new TableColumn<>("Description");
    TableColumn<Produits, Integer> Categorie = new TableColumn<>("categorie");
    TableColumn<Produits, Timestamp> dateDebut = new TableColumn<>("debut");
    TableColumn<Produits, Timestamp> dateFin = new TableColumn<>("fin");
    TableColumn<Produits, Integer> prix = new TableColumn<>("Prix");
    TableColumn<Produits, Integer> possede = new TableColumn<>("proposepar");

    TableView<Produits> produitTable = new TableView<>();
    private Connection connectBdD;
    
    ObservableList<Produits> produitList = FXCollections.observableArrayList();
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        titleLabel.getStyleClass().add("labelTitle");
        produitTable.getStyleClass().add("table-view");
        acheterButton.getStyleClass().add("Button");
    }
    
    private void addValues(){
        totalHbox.getChildren().addAll(acheterButton);
        root.getChildren().addAll(titleLabel,produitTable,totalHbox);
        root.setSpacing(15);
        totalHbox.setSpacing(20);
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
        produitTable.getColumns().addAll(id,Titre,Description,Categorie,dateDebut,dateFin,prix,possede); 
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("cat"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("debut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prixbase"));
        possede.setCellValueFactory(new PropertyValueFactory<>("proposepar"));
        
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
        window.setWidth(700);
        window.setHeight(600);
        window.setTitle("Produits");
    }
     
    @SuppressWarnings("empty-statement")
    public ListeProduits() {
        this.utilisateurCourant = main.getUtilisateurCourant();
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
            
           acheterButton.setOnAction(event -> {
            Produits selectedProduit = produitTable.getSelectionModel().getSelectedItem();
            if(selectedProduit == null){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur!");
                    a.setContentText("Please Select a product!");
                    a.showAndWait();
                    return;
            }
            if(utilisateurCourant <= 0){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous etes besoin d'etre connecté!");
                    a.setContentText("Vous pouvez pas acheter un produit!");
                    a.showAndWait();
                    window.close();
                    return;
               }
            if(utilisateurCourant == selectedProduit.getProposepar()){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Vous ne pouvez achetez!");
                    a.setContentText("Vous pouvez pas achete votre produits!");
                    a.showAndWait();
                    return;
                }
            try {
                PreparedStatement pst = connectBdD.prepareStatement("UPDATE objet1 SET proposepar = ?, show = ? WHERE id = ?");
                pst.setInt(1, utilisateurCourant);
                pst.setInt(2, 0);
                pst.setInt(3, selectedProduit.getId());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Vous avez achetez le produit!");
                window.close();
            } catch (SQLException ex) {
                Logger.getLogger(ListeProduits.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
           
            PreparedStatement pst = connectBdD.prepareStatement("SELECT * From objet1");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                    int show = rs.getInt("show");
                    if(show == 1){
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
                    }
                    if(show == 1){
                        produitTable.setItems(produitList);
                    }
                }; 
                
            ComboBox<String> categoryComboBox = new ComboBox<>();
            categoryComboBox.getStyleClass().add("Button");
            categoryComboBox.getItems().addAll("All", "Meuble", "Habit");
            categoryComboBox.setValue("All");
            totalHbox.getChildren().add(categoryComboBox);

            categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            String selectedCategory = newValue;
            int habit = 1;
            int meuble =2;
            ObservableList<Produits> filteredList = FXCollections.observableArrayList();
            if (selectedCategory.equals("All")) {
                filteredList.addAll(produitList);
            } else if(selectedCategory.equals("Habit")){
                for (Produits produit : produitList) {
                    if (produit.getCat() == 1) {
                        filteredList.add(produit);
                    }
                }
            }
            else{
                for (Produits produit : produitList) {
                    if (produit.getCat() == 2) {
                        filteredList.add(produit);
                    }
                }
            }
            produitTable.setItems(filteredList);
        });
                
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        window.show();

    }   
}
