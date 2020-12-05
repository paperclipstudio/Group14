package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Screen that shows once someone has won the game.
 * @author David Landmaid
 */
public class WinScreenController extends StateLoad {
    @FXML
    private Button returnButton;

    @FXML
    private Text winner;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (getInitData() != null) {
            winner.setText("Congratulations Player " + getInitData().get("Winner") + "!");
        }
    }

    /**
     * Returns to main menu
     */
    public void onReturnButton() {
        wl = new WindowLoader(returnButton);
        wl.load("MenuScreen", getInitData());
    }



    public void onPlayAgainButton() {
        wl = new WindowLoader(returnButton);
        wl.load("GameSetup", getInitData());
    }
}
