package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class WinScreenController implements Initializable {
    @FXML
    private Button returnButton;

    @FXML
    private Text winner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winner.setText("Congratulations" +  "!");
    }

    public void onReturnButton() {
        WindowLoader wl = new WindowLoader(returnButton);
        wl.load("MenuScreen");
    }
}
