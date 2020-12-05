package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the "Press any button screen"
 * lets you open the main menu screen.
 * @author Christian
 */
public class StartScreenController extends StateLoad {
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
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }

    /**
     * Called when a user clicks the mouse
     * @param m the mouse event of this click
     */
    public void onMousePress(MouseEvent m){
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());

    }
}
