/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Notification;
import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONException;

/**
 * FXML Controller class
 *
 * @author agile systems
 */



public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sign_in(ActionEvent event) {
        
    }

    @FXML
    private void login(ActionEvent event) {
        User user = new User();
        String username = this.username.getText();
        user.setUsername(username);
        String password = this.password.getText();
        user.setPassword(password);
        if(username.isEmpty() || password.isEmpty()){
            Notification notify = new Notification(2, "Login Form", "");
            notify.start();
        }else{
            try {
                user.sign_in();
            } catch (JSONException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
           
        
    }
    
}
