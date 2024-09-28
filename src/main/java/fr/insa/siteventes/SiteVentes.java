/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package fr.insa.siteventes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 *
 * @author Hassan TAHA
 */
public class SiteVentes extends Application{
    
    BorderPane root = new BorderPane();
    private Scene scene = new Scene (root);
   
    private static Connection connectBdD;
    private static connection cn;
    int utilisateurCourant;
    int role;
    private static SiteVentes instance;
    MenuBar menu= new MenuBar();
    
    public SiteVentes(){
        utilisateurCourant = 0;
        try {
            connectBdD = connection.defautConnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SiteVentes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SiteVentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        root.getStyleClass().add("mainWindow");
        menu.getStyleClass().add("menu-bar");
    }
    
    public static SiteVentes getInstance(){
        if(instance == null){
            instance = new SiteVentes();
        }
        return instance;
    }
    
    public void createMenu(){
        Menu login = new Menu("Login");
        Menu users = new Menu("Users");
        Menu categorie = new Menu("Categorie");
        Menu produits = new Menu("Produits");
        Menu about = new Menu("About us");
        
        MenuItem seConn = new MenuItem("Se Connecter");
        MenuItem about1 = new MenuItem("About us");
        MenuItem Inscription = new MenuItem("Sign Up");
        MenuItem listePItem = new MenuItem("Lister Les Produits");
        MenuItem listeUser = new MenuItem("Lister Les Users");
        MenuItem seDeconn = new MenuItem("Se deconnecter");
        MenuItem pItem = new MenuItem("Nouveau Produit");        
        MenuItem listeMesProduits = new MenuItem("Lister Mes Produits");
        MenuItem addCat = new MenuItem("add Categorie");
        MenuItem listCat = new MenuItem("liste des categories");

        categorie.getItems().addAll(addCat, listCat);
        about.getItems().add(about1);
        login.getItems().addAll(seConn,Inscription);
        menu.getMenus().addAll(login,users,categorie,produits,about);
        
        login.getItems().addAll(seDeconn);
        produits.getItems().addAll(pItem);
         
        produits.getItems().addAll(listePItem,listeMesProduits);
        users.getItems().add(listeUser);
        
        root.setTop(menu);
        
        
        addCat.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new addCat();
                }
            });
        
         listCat.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new listCat();
                }
            });
         
         about.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new aboutUs();
                }
            });
        
        seConn.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                login login1 = new login();
                }
            });
        
            Inscription.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new Inscription();
                }
            }); 
            
        seDeconn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
           public void handle(ActionEvent event){
               new deconnexion(utilisateurCourant, role);
               }
           });
            
            pItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
           public void handle(ActionEvent event){ 
               new addProduits();
                }
           });
            
            listePItem.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new ListeProduits();
                }
            });

            listeUser.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new ListeUtilisateurs();
                }
            });
            
            
            listeMesProduits.setOnAction(new EventHandler<ActionEvent>(){
             @Override
            public void handle(ActionEvent event){
                new MesProduits();
                }
            });
             
        }
    
     public Connection getConnectBdD() {
        return connectBdD;
    }
     
    public int getUtilisateurCourant() {
        return utilisateurCourant;
    }
    
    public void setUtilisateurCourant(int utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
    }
    
    public int getRole() {
        return role;
    }
    
    public void setRole(int role) {
        this.role = role;
    }
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Application.launch(args);
    }
    
    @Override 
    public void start(Stage window) throws Exception{
        addCss();
        setUtilisateurCourant(utilisateurCourant);
        createMenu();
        window.setScene(scene);
        window.setWidth(1100);
        window.setHeight(700);
        window.setTitle("Site de Ventes");
        window.show();
    }
}
