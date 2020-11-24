package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * Setting Screen controls settings about the game mainly
 * - Resolution
 * - Sound
 * - Full screen
 * @author Christian Sanger
 */
public class SettingsController implements Initializable {
	@FXML
	private Parent root;
	@FXML
	private Button backButton;
	@FXML
	private CheckBox fullscreen;
	@FXML
	private Slider sound;
	@FXML
	private ChoiceBox<String> resolution;
	final static private String[] RESOLUTIONS = new String[] {"800x400", "1600x800"};
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Window ps = root.getScene().getWindow();
		resolution.getItems().add("1600x800");
		resolution.getItems().add("800x400");
		resolution.getSelectionModel().selectFirst();


	}

	String keyHistory = "";
	public void onKeyPress(KeyEvent k) {
		keyHistory += k.getCharacter();
		if (keyHistory.length() >= 8) {
			keyHistory = keyHistory.substring(1, 8);
		}
		if (keyHistory.equals("swansea")) {
			System.out.println("DEBUG MODE ACTIVATED");
		};
		if (keyHistory.equals("ducksea")) {
			System.out.println("DEBUG MODE DEACTIVATED");
		}
	}

	public void onChoiceBox() {

	}


	/***
	 * Returns to menu screen
	 * called by back button
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

}
