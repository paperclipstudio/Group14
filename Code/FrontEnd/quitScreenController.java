package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Used to control the QuitScreen scene.
 * @author David Langmaid
 */
public class quitScreenController extends StateLoad {

    @FXML
    private Button noButton;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * This method closes the application.
     */
    public void onYes() {
        Platform.exit();
    }

    /**
     * This method returns you to previous scene.
     */
    public void onNo() {
        wl = new WindowLoader(noButton);
        wl.load("MenuScreen", getInitData());
    }
}
