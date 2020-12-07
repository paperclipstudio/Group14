package FrontEnd;

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
 * @author Daniel Jones Ortega and David Landmaid.
 * @version 1.0
 */
public class HowToPlayController extends StateLoad {

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
     * This method is used to make to create a new windowLoader, and load the gameSetup
     * button.
     */
    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
    }

    /**
     * This method is is used to create a new button for tiles. Also has sound effects when
     * pressed.
     */
    public void onTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Tiles", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button for tiles. Also has sound effects when
     * pressed.
     */
    public void onTurns() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Turns", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button for Movement. Also has sound effects when
     * pressed.
     */
    public void onMovement() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Movement", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button for ActionTiles. Also has sound effects when
     * pressed.
     */
    public void onActionTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button for HowToPlay. Also has sound effects when
     * pressed.
     */
    public void onBackTo() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button for Basics. Also has sound effects when
     * pressed.
     */
    public void onBasics() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Basics", getInitData());
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    /**
     * This method is is used to create a new button to go back to the menu screen. Also has sound effects when
     * pressed.
     */
    public void onBackToMenu() {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }
}
