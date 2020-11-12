import  javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello World");
        // You can use the line below to test out your own screens
        primaryStage.setScene(GameScreen.makeWindow());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
