/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patient.inventory.desktop;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author agile systems
 */
public class PatientInventoryDesktop extends Application {

    //public static String Base_URL = "https://patient-backlog-api.herokuapp.com/api";
    public static String Base_URL = "https://antiviraltreatementapi.herokuapp.com/api/v1";
    ///login

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root2 = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            Scene scene2 = new Scene(root2);
            primaryStage.setScene(scene2);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(PatientInventoryDesktop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
