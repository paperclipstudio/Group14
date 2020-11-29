package FrontEnd;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {
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

    public void OnKeyPressed(KeyEvent keyEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen");
    }

    public void onMousePress(MouseEvent m) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen");
    }

}
