
package hu.sagi.gpskoord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GPSKoordV extends Application{

    String title ="GPS koordináta átváltás";
    
       @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle(title);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
       launch(args);
    }

 
    
}
