package FrontEnd;

import  javafx.application.Application;
import javafx.scene.image.Image;
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

    private static MediaPlayer mediaPlayer;
    private static final double DEFAULT_SOUND_LEVEL = 10.0;
    private int track;

    @Override
    public void start(Stage primaryStage) {
        WindowLoader wl = new WindowLoader(primaryStage);
        HashMap<String, String> initData = new HashMap<>();
        Scanner in;
        //  Default settings if no config file.
        double backgroundVol;
        double sfxVol;
        try {
            File configFile = new File("SaveData\\config.txt");
            in = new Scanner(configFile);
            if (!in.hasNextDouble()) {
                throw new NumberFormatException("background Level");
            }
            backgroundVol = in.nextDouble();
            if (!in.hasNextDouble()) {
                throw new NumberFormatException("sfx Level");
            }
            sfxVol = in.nextDouble();
        } catch (NumberFormatException e) {
            System.out.println("Config file incorrect format:" + e.getMessage());
            backgroundVol = DEFAULT_SOUND_LEVEL;
            sfxVol = DEFAULT_SOUND_LEVEL;
        } catch  (FileNotFoundException e) {
            System.out.println("Config not found, using defaults");
            backgroundVol = DEFAULT_SOUND_LEVEL;
            sfxVol = DEFAULT_SOUND_LEVEL;
        }

        Main.setVolume(backgroundVol);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("The Getaway");
        primaryStage.getIcons().add(new Image("player2.png"));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        String musicFile = "Assets\\music.mp3";     // For example
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
        playMusic(backgroundVol);
        initData.put("BackgroundVol", String.valueOf(backgroundVol));
        initData.put("SFXVol", String.valueOf(sfxVol));
        //setVolume(backgroundVol);



        wl.load("startScreen", initData);
        primaryStage.show();
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume/200);
            mediaPlayer.play();
        }
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
                track = (track + 1) % musicFiles.length ;
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
