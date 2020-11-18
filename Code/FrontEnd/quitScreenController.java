package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Use to control the QuitScreen scene.
 * @author David Langmaid
 */
public class quitScreenController implements Initializable {
    @FXML
    private Button noButton;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}


    /**
     * Closes the application
     */
    public void onYes() {
        Platform.exit();
    }

    /**
     * Returns you to previous scene
     */
    public void onNo() {
        wl = new WindowLoader(noButton);
        wl.load("MenuScreen");
    }

}
