package FrontEnd;

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
public class HowToPlayController extends StateLoad {

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;
    private WindowLoader wl;


    /**
     * Used to exit the application
     */

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


    /**
     * called when settings button is clicked
     * opens settings window
     */


    /**
     * called when profiles button is clicked
     * opens profile window
     */


    /**
     * called when leaderboard button is clicked
     * opens leaderboard window
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Tiles", getInitData());
    }

    public void onTurns() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Turns", getInitData());
    }

    public void onMovement() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Movement", getInitData());
    }

    public void onActionTiles() {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());
    }

    public void onBackTo(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
    }

    public void onBackToMenu(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }

    public void onBasics(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Basics", getInitData());
    }

}
