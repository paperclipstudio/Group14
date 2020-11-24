package FrontEnd;

import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/***
 * FrontEnd.Main class for this app, starts the window and opens up the 'Start' window
 * @author Christian Sanger
 *
 */

public class Main extends Application {
    private static double volume = 50;
    private static MediaPlayer mediaPlayer;
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
        String musicFile = "Assets\\music.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(100);
        mediaPlayer.setVolume(volume/100.0);
        mediaPlayer.play();
        primaryStage.show();
    }

    public static void setVolume(double volume) {
        System.out.println("Volume Changed");
        Main.volume = volume;
        mediaPlayer.setVolume(volume);
    }

    /***
     * Only starts javaFX
     * @param args doesn't use any arguments right now.
     */

    public static void main(String[] args) {
        launch(args);
    }
}
