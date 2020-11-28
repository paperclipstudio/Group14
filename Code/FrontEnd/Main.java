package FrontEnd;

import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

/***
 * FrontEnd.Main class for this app, starts the window and opens up the 'Start' window
 * @author Christian Sanger
 *
 */

public class Main extends Application {
    private static boolean fullScreen;
	private static String boardFile;
    private static int numberOfPlayer;
    private static String loadFile;
    private static boolean loadedGameFile = false;
    private static int seed;
    private static double volume = 10;
    private static MediaPlayer mediaPlayer;

    private static int resolution;

    public static int getSeed() {
        return seed;
    }

    public static void setSeed(int seed) {
        Main.seed = seed;
    }

    public static boolean isFullScreen() {
        return fullScreen;
    }

    public static void setFullScreen(boolean fullScreen) {
        Main.fullScreen = fullScreen;
    }

    public static int getResolution() {
        return Main.resolution;
    }

    public static void setResolution(int resolution) {
        Main.resolution = resolution;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello World");
        //Parent root = null;
        // You can use the line below to test out your own screens
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FrontEnd\\FXML\\StartScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("Covid Game?");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public static double getVolumne() {
        return Main.volume;
    }

    /***
     * Only starts javaFX
     * @param args doesn't use any arguments right now.
     */

    public static void main(String[] args) {
        launch(args);
    }

    public static String getBoardFile() {
        return boardFile;
    }

    public static void setBoardFile(String boardFile) {
        Main.boardFile = boardFile;
    }

    public static int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public static void setNumberOfPlayer(int numberOfPlayer) {
        Main.numberOfPlayer = numberOfPlayer;
    }

    public static String getLoadFile() {
        return loadFile;
    }

    public static void setLoadFile(String loadFile) {
        Main.loadFile = loadFile;
    }

    public static boolean isLoadedGameFile() {
        return loadedGameFile;
    }

    public static void setLoadedGameFile(boolean loadedGameFile) {
        Main.loadedGameFile = loadedGameFile;
    }
}
