package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Use to control the GameSetup scene.
 *
 * @author Chrisitan Sanger
 */

public class GameSetupController implements Initializable {
	@FXML
	private Button backButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/***
	 * Returns to main menu
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * Continues to GameScreen
	 */
	public void onStartButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameScreen");
	}

}
