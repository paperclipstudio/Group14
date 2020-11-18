package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Use to control the QuitScreen scene.
 * @author David Langmaid
 */
public class quitScreenController implements Initializable {
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
        Platform.exit();
    }

}
