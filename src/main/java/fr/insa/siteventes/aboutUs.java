/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.siteventes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Hassan TAHA
 */
public class aboutUs {
    VBox root = new VBox();
    Scene scene = new Scene(root);
    Stage window = new Stage();
    Label parag = new Label("");
    
    private void addValues(){
        root.setSpacing(20);
        root.getChildren().addAll(parag);
    }
    
    public void addCss(){
        scene.getStylesheets().add("file:/"+System.getProperty("user.dir").replace("\\", "/")+"/src/main/java/fr/insa/siteventes/css/styles.css");
        parag.getStyleClass().add("parag");
        root.getStyleClass().add("loginWindow");
    }
        
    private void initWindow(){
        window.setScene(scene);
        window.setWidth(700);
        window.setHeight(300);
        window.setTitle("About Us");
    }
    
    public aboutUs(){
        addCss();
        addValues();
        initWindow();
        window.show();
    }

    
}
