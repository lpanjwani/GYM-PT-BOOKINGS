/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author LaveshPanjwani
 */
public class MiddlesexGYM extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClientUIController root = new ClientUIController();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Middlesex GYM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
