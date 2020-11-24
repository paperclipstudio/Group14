import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***
 * Main class for this app, starts the window and opens up the 'Start' window
 * @author Christian Sanger
 *
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello World");
        //Parent root = null;
        // You can use the line below to test out your own screens
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FrontEnd\\MenuScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("Covid Game?");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /***
     * Only starts javaFX
     * @param args doesn't use any arguments right now.
     */

    public static void main(String[] args) {
        launch(args);
    }
}
