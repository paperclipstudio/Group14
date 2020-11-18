package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Use to control the GameScreen scene.
 * @author David Langmaid
 */
public class MenuScreenController implements Initializable {
    @FXML
    private Button newGameButton;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        wl = new WindowLoader(newGameButton);
        wl.load("QuitScreen");
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
}
