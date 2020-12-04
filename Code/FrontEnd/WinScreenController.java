package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winner.setText("Congratulations Player " + getInitData().get("Winner") + "!");
    }

    /**
     * Returns to main menu
     */
    public void onReturnButton() {
        WindowLoader wl = new WindowLoader(returnButton);
        wl.load("MenuScreen", getInitData());
    }
}
