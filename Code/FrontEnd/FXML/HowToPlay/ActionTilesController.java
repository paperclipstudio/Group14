package FrontEnd.FXML.HowToPlay;

import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
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
public class ActionTilesController extends StateLoad {

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

    public void onIce(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Ice", getInitData());
    }

    public void onFire(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Fire", getInitData());
    }

    public void onDoubleMove(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Double Move", getInitData());
    }

    public void onBack(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/ActionTiles", getInitData());

    }

    public void onBacktrack(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/Backtrack", getInitData());

    }

    public void onBackToMenu(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("/HowToPlay/HowToPlay", getInitData());
    }
}
