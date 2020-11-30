package FrontEnd;

import MessageOfTheDay.MOTD;
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

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message = "";
        try {
            message = MOTD.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();

        }
        MoTD.setText(message);

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
