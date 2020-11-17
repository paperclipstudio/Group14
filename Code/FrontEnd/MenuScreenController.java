package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Use to control the GameScreen scene.
 * @author David Langmaid
 */
public class MenuScreenController implements Initializable {
    @FXML
    private Button quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();
    }
}
