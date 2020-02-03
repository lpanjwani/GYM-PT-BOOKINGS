/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class initializes the Client-Side Graphical User Interface
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class GymClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClientController root = new ClientController();

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
