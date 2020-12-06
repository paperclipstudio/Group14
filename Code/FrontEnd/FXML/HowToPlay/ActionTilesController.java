package FrontEnd.FXML.HowToPlay;

import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
import javafx.event.ActionEvent;
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
 */
public class ActionTilesController extends StateLoad {

    /* These final variables are used for the game's Sound Effects (SFX) */

    private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
    private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;
    private WindowLoader wl;

    /**
     * Called when new game is clicked
     * opens game setup
     */

    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup", getInitData());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onIce(ActionEvent actionEvent) {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Ice", getInitData());
    }

    public void onFire(ActionEvent actionEvent) {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Fire", getInitData());
    }

    public void onDoubleMove(ActionEvent actionEvent) {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Double Move", getInitData());
    }

    public void onBacktrack(ActionEvent actionEvent) {
        MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Backtrack", getInitData());

    }

    public void onBack(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }

    public void onBackToMenu(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
        RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
    }
}
