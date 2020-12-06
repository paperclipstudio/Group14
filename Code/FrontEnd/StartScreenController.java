package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the "Press any button screen"
 * lets you open the main menu screen.
 * @author Christian Sanger
 */

public class StartScreenController extends StateLoad {

    private final String START_SFX = "Assets\\SFX\\start.mp3";
    private final AudioClip START_AUDIO = new AudioClip(new File(START_SFX).toURI().toString());
    private final double SFX_VOLUME = 0.1;

    @FXML
    private Button newGameButton;
    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called when new Game button is pressed
     */
    public void onNewGame(){ //TODO REMOVE
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
}

    /**
     * Called when a keyboard button has been pressed
     * loads main menu
     * @param keyEvent event of that keypress
     */
    public void OnKeyPressed(KeyEvent keyEvent){
        START_AUDIO.play(SFX_VOLUME);
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }

    /**
     * Called when a user clicks the mouse
     * @param m the mouse event of this click
     */
    public void onMousePress(MouseEvent m){
        START_AUDIO.play(SFX_VOLUME);
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }
}
