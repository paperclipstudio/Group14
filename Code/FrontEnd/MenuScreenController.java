package FrontEnd;

import MessageOfTheDay.MessageOfTheDay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Use to control the GameScreen scene.
 *
 * @author David Langmaid
 */
public class MenuScreenController extends StateLoad {

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;
    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message;
        try {
            message = MessageOfTheDay.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();
        }
        MoTD.setText(message);
    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();
    }

    /**
	 * Called when new game is clicked
     * opens game setup
     */
    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
    }

    /**
     * called when load game is clicked
     * opens load game screen
     */
    public void onLoadGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("LoadGame", getInitData());
    }

    /**
     * called when settings button is clicked
     * opens settings window
     */
    public void onSettings() {
        wl = new WindowLoader(newGameButton);
        wl.load("Settings", getInitData());
    }

    /**
     * called when profiles button is clicked
     * opens profile window
     */
    public void onPlayerProfiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("Profiles", getInitData());
    }

    /**
     * called when leaderboard button is clicked
     * opens leaderboard window
     */
    public void onLeaderBoard() {
        wl = new WindowLoader(newGameButton);
        wl.load("/Leaderboards/FXMLDocument", getInitData());

    }

    public void onHowToPlay(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
    }
}
