package FrontEnd;

import MessageOfTheDay.MessageOfTheDay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Use to control the GameScreen scene.
 * @author David Langmaid
 */
public class MenuScreenController implements Initializable {
    private static final double DEFAULT_SOUND_LEVEL = 0.0;
    private static final boolean DEFAULT_FULLSCREEN = false;
    private static final int DEFAULT_RESOLUTION = 0;
    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String message = MessageOfTheDay.shiftLetters();
            MoTD.setText(message);
        } catch (IOException e) {
            MoTD.setText("Error with Motd :)");
        }
        Scanner in = null;
        //  Default settings if no config file.
        double sound;
        boolean fullscreen;
        int resolution;
        try {
            File configFile = new File("SaveData\\config.txt");
            in = new Scanner(configFile);
            if (!in.hasNextDouble()) {
                throw new NumberFormatException("Sound Level");
            }
            sound = in.nextDouble();

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
            sound = DEFAULT_SOUND_LEVEL;
            fullscreen = DEFAULT_FULLSCREEN;
            resolution = DEFAULT_RESOLUTION;
        } catch  (FileNotFoundException e) {
            System.out.println("Config not found, using defaults");
            sound = DEFAULT_SOUND_LEVEL;
            fullscreen = DEFAULT_FULLSCREEN;
            resolution = DEFAULT_RESOLUTION;
        }

        Main.setVolume(sound);
        Main.setFullScreen(fullscreen);
        Main.setResolution(resolution);
    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();;
    }

    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup");
    }

    public void onLoadGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("LoadGame");
    }

    public void onSettings() {
        wl = new WindowLoader(newGameButton);
        wl.load("Settings");
    }

    public void onPlayerProfiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("Profiles");
    }

    public void onLeaderBoard(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboard");
    }
}
