package FrontEnd;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    @FXML
    private Button newGameButton;

    private WindowLoader wl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onQuitButton() {
        Platform.exit();;
    }

    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup");
    }

    public void onBoard1(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board1");
    }

    public void onBoard2(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board2");
    }

    public void onBoard3(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board3");
    }

    public void onBack(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen");
    }
}
