package FrontEnd;

import  javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/***
 * FrontEnd.Main class for this app, starts the window and opens up the 'Start' window
 * @author Christian Sanger
 *
 */

public class Main extends Application {

    private HashMap<String, String> initData;
    private static MediaPlayer mediaPlayer;
    private static final double DEFAULT_SOUND_LEVEL = 0.0;
    private static final boolean DEFAULT_FULLSCREEN = false;
    private static final int DEFAULT_RESOLUTION = 0;

    private static RESOLUTION resolution = RESOLUTION.SIX_BY_FOUR;



    @Override
    public void start(Stage primaryStage) {
        WindowLoader wl = new WindowLoader(primaryStage);
        initData = new HashMap<>();

        Scanner in;
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
       // primaryStage.setWidth(SettingsController.getWidth(RESOLUTION.values()[resolution]));
       // primaryStage.setHeight(SettingsController.getHeight(RESOLUTION.values()[resolution]));
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Covid Game?");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        String musicFile = "Assets\\music.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(Integer.MAX_VALUE);

        mediaPlayer.setVolume(soundLevel);
        mediaPlayer.play();

        initData.put("Volume", String.valueOf(soundLevel));
        initData.put("FullScreen", "true");
        initData.put("Resolution", resolution + "");
        wl.load("startScreen", initData);
        primaryStage.show();
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume/200);
        }
    }

    public static double getVolume() {
        return mediaPlayer.getVolume() * 200;
    }

    /***
     * Only starts javaFX
     * @param args doesn't use any arguments right now.
     */

    public static void main(String[] args) {
        launch(args);
    }

}
