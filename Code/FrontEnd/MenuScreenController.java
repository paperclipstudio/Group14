package FrontEnd;

import MessageOfTheDay.MessageOfTheDay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Use to control the GameScreen scene.
 * @author David Langmaid
 */
public class MenuScreenController implements Initializable {

    @FXML
    private Button newGameButton;
    @FXML
    private Label MoTD;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String message = "";
        try {
            message = MessageOfTheDay.puzzle();
        } catch (Exception e) {
            message = "Error with Server" + e.getCause();

        }
        MoTD.setText(message);

    }

    /**
     * Used to exit the application
     */
    public void onQuitButton() {
        Platform.exit();;
    }

    public void onNewGame() throws IOException {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup");
    }

    public void onLoadGame() throws IOException {
        wl = new WindowLoader(newGameButton);
        wl.load("LoadGame");
    }

    public void onSettings() throws IOException {
        wl = new WindowLoader(newGameButton);
        wl.load("Settings");
    }

    public void onPlayerProfiles() throws IOException {
        wl = new WindowLoader(newGameButton);
        wl.load("Profiles");
    }

    public void onLeaderBoard(ActionEvent actionEvent) throws IOException {
        wl = new WindowLoader(newGameButton);
        wl.load("/Leaderboards/FXMLDocument");

    }
}
