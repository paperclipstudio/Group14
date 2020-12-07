package FrontEnd.FXML.HowToPlay;

import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used to display the different action tiles and their uses.
 *
 * @author Daniel Jones Ortega
 * @version 1.0.
 */
public class ActionTilesController extends StateLoad {

    /* These final variables are used for the game's Sound Effects (SFX) */

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());

    /*
    These variables are used to help with the display of the how to play page.
     */
    @FXML
    private Button newGameButton;
    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method is used to create a new button.
     */
    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
    }

    /**
     * This method is used to create a new button Ice. Also has sound effects when pressed.
     */
    public void onIce() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Ice", getInitData());
    }

    /**
     * This method is used to create a new button Fire. Also has sound effects when pressed.
     */
    public void onFire() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Fire", getInitData());
    }

    /**
     * This method is used to create a new button Double Move. Also has sound effects when pressed.
     */
    public void onDoubleMove() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Double Move", getInitData());
    }

    /**
     * This method is used to create a new button Backtrack. Also has sound effects when pressed.
     */
    public void onBacktrack() {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Backtrack", getInitData());

    }

    /**
     * This method is used to create a new button ActionTiles. Also has sound effects when pressed.
     */
    public void onBack() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is used to create a new button HowToPlay. Also has sound effects when pressed.
     */
    public void onBackToMenu() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }
}
