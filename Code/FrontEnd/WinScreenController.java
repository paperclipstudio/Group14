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
    private static Text winner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static void setPlayerNum(int playerNum){
        //change to profile name
        winner.setText("Congratulations Player " + playerNum + "!");
    }

    public void onReturnButton() {
        WindowLoader wl = new WindowLoader(returnButton);
        wl.load("MenuScreen");
    }
}
