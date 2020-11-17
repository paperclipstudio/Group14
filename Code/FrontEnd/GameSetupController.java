package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSetupController implements Initializable {
	@FXML
	private Button backButton;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	public void onStartButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameScreen");
	}

}
