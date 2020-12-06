package FrontEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to display instructions on how to play the game.
 *
 * @author Daniel Jones Ortega
 */
public class HowToPlayController extends StateLoad {

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private final double SFX_VOLUME = 0.2;

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;
    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
    }

    public void onTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Tiles", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onTurns() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Turns", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onMovement() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Movement", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onActionTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onBackTo() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        RETURN_AUDIO.play(SFX_VOLUME);
    }

    public void onBasics() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Basics", getInitData());
        MAIN_MENU_AUDIO.play(SFX_VOLUME);
    }

    public void onBackToMenu() {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
        RETURN_AUDIO.play(SFX_VOLUME);
    }
}
