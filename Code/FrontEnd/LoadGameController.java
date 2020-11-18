package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.annotation.Generated;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Screen to load a previously saved game.
 * @author Christian Sanger
 */
public class LoadGameController implements Initializable {
	@FXML
	private Button backButton;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/***
	 * Returns to menu screen
	 * Called my backButton
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * Continues to load game.
	 * Called by start button.
	 */
	public void onPlayButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameScreen");
	}


}
