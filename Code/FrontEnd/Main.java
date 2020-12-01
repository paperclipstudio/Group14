package FrontEnd;

import BackEnd.Profile;
import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/***
 * FrontEnd.Main class for this app, starts the window and opens up the 'Start' window
 * @author Christian Sanger
 *
 */

public class Main extends Application {
    private static Profile[] profiles;
    private static boolean fullScreen;
	private static String boardFile;
    private static int numberOfPlayers;
    private static String loadFile;
    private static boolean loadedGameFile = false;
    private static int seed;
    private static double volume = 10;
    private static MediaPlayer mediaPlayer;
    private static final double DEFAULT_SOUND_LEVEL = 0.0;
    private static final boolean DEFAULT_FULLSCREEN = false;
    private static final int DEFAULT_RESOLUTION = 0;

    private static RESOLUTION resolution = RESOLUTION.SIX_BY_FOUR;

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

    public static RESOLUTION getResolution() {
        return Main.resolution;
    }

    public static void setResolution(RESOLUTION res) {
        Main.resolution = res;
    }

    public static Profile[] getProfiles() {
        return profiles;
    }

    public static void setProfiles(Profile[] profiles) {
        Main.profiles = profiles;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello World");
        //Parent root = null;
        // You can use the line below to test out your own screens
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FrontEnd\\FXML\\StartScreen.fxml")));
        Scene scene = new Scene(root);
        Scanner in = null;
        //  Default settings if no config file.
        double soundLevel;
        boolean fullscreen;
        int resolution;
        try {
            File configFile = new File("SaveData\\config.txt");
            in = new Scanner(configFile);
            if (!in.hasNextDouble()) {
                throw new NumberFormatException("Sound Level");
            }
            soundLevel = in.nextDouble();

            if (!in.hasNextBoolean()) {
                throw new NumberFormatException("Fullscreen");
            }
            fullscreen = in.nextBoolean();
            if (!in.hasNextInt()) {
                throw new NumberFormatException("Resolution");
            }
            resolution = in.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Config file incorrect format:" + e.getMessage());
            soundLevel = DEFAULT_SOUND_LEVEL;
            fullscreen = DEFAULT_FULLSCREEN;
            resolution = DEFAULT_RESOLUTION;
        } catch  (FileNotFoundException e) {
            System.out.println("Config not found, using defaults");
            soundLevel = DEFAULT_SOUND_LEVEL;
            fullscreen = DEFAULT_FULLSCREEN;
            resolution = DEFAULT_RESOLUTION;
        }

        Main.setVolume(soundLevel);
        Main.setFullScreen(fullscreen);
        Main.setResolution(RESOLUTION.values()[resolution]);
        primaryStage.setWidth(SettingsController.getWidth(RESOLUTION.values()[resolution]));
        primaryStage.setHeight(SettingsController.getHeight(RESOLUTION.values()[resolution]));
        primaryStage.setFullScreen(fullScreen);
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

    public static double getVolume() {
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

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        Main.numberOfPlayers = numberOfPlayers;
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
