package FrontEnd;

import MessageOfTheDay.MessageOfTheDay;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Use to control the GameScreen scene.
 *
 * @author David Langmaid
 */
public class MenuScreenController extends StateLoad {

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final double SFX_VOLUME = 0.2;

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
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    /**
     * called when load game is clicked
     * opens load game screen
     */
    public void onLoadGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("LoadGame", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    /**
     * called when settings button is clicked
     * opens settings window
     */
    public void onSettings() {
        wl = new WindowLoader(newGameButton);
        wl.load("Settings", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    /**
     * called when profiles button is clicked
     * opens profile window
     */
    public void onPlayerProfiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("Profiles", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    /**
     * called when leaderboard button is clicked
     * opens leaderboard window
     */
    public void onLeaderBoard() {
        wl = new WindowLoader(newGameButton);
        wl.load("/Leaderboards/FXMLDocument", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onHowToPlay() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }
}
