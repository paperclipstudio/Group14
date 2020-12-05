package FrontEnd;

import  javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private int track = 0;


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
        primaryStage.setWidth(SettingsController.getWidth(RESOLUTION.values()[resolution]));
        primaryStage.setHeight(SettingsController.getHeight(RESOLUTION.values()[resolution]));
        primaryStage.setFullScreen(fullscreen);
        primaryStage.setTitle("Covid Game?");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        String musicFile = "Assets\\music.mp3";     // For example

        playMusic(soundLevel);

        initData.put("Volume", String.valueOf(soundLevel));
        initData.put("FullScreen", "true");
        initData.put("Resolution", resolution + "");
        primaryStage.show();
        wl.load("startScreen", initData);
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume/200);
        }
    }

    public static double getVolume() {
        return mediaPlayer.getVolume() * 200;
    }

    public void playMusic(double soundLevel) {
        String [] musicFiles = {"Assets\\music.mp3","Assets\\music2.mp3","Assets\\music3.mp3", "Assets\\music4.mp3","Assets\\music5.mp3"};
        Media sound = new Media(new File(musicFiles[track]).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(0);

        mediaPlayer.setVolume(soundLevel);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                track = (track +1) % musicFiles.length ;
                playMusic(soundLevel);

                if (track > musicFiles.length){
                    track = 0;
                    playMusic(soundLevel);
                    }
                }



        });
    }

    /***
     * Only starts javaFX
     * @param args doesn't use any arguments right now.
     */

    public static void main(String[] args) {
        launch(args);
    }

}
