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
    private Button quitButton;
    @FXML
    private Button newGameButton;

    WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wl = new WindowLoader(newGameButton);
    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();
    }

    public void onNewGame() {
        wl.load("GameSetup");
    }

    public void onLoadGame() {
        wl.load("LoadGame");
    }

    public void onSettings() {
        wl.load("Settings");
    }

    public void onPlayerProfiles() {
        wl.load("Profiles");
    }
}
