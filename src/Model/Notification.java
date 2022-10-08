/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author agile systems
 */
public class Notification extends Thread
{
     private int seconds =5;
     private String title;
     private String text;
     private String type= "nothing";
     
   
     
     public Notification(int seconds,String title,String text){
        this.title = title;
        this.text = text;
        this.seconds = seconds;
    }
    
      public Notification(int seconds,String title,String text,String type){
        this.title = title;
        this.text = text;
        this.seconds = seconds;
    }

    Notification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
     @Override
    public void run()
    {
            try {
                Thread.sleep(seconds);
            } catch (InterruptedException ex) {
                Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Notifications notificationBuilder =Notifications.create()
                 .title(title)
                 .text(text)
                 //.position(Pos.TOP_LEFT)
                 .hideAfter(Duration.seconds(seconds));
                 //.position(Pos.BOTTOM_RIGHT);
           // notificationBuilder.darkStyle();
            if(type.equalsIgnoreCase("error")){
                notificationBuilder.showError();
                notificationBuilder.position(Pos.CENTER);
            }
            
            Platform.runLater(new Runnable(){
               @Override
               public void run(){
                 notificationBuilder.showInformation();
                 
               }
            });    
    }
    
}

