package FrontEnd;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	final static private String[] RESOLUTIONS = new String[]{"800x400", "1600x800", "10x10"};
	private Stage ps;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (String setting : RESOLUTIONS) {
			resolution.getItems().add(setting);
		}
		ChangeListener<String> resolutionListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateResolution(newValue);
			}
		};
		resolution.getSelectionModel().selectedItemProperty().addListener(resolutionListener);
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
		}
		;
		if (keyHistory.equals("ducksea")) {
			System.out.println("DEBUG MODE DEACTIVATED");
		}
	}

	public void updateResolution(String newResolution) {
		if (newResolution.equals(RESOLUTIONS[0])) {
			WindowLoader.updateResolution(800, 400);
		} else if (newResolution.equals(RESOLUTIONS[1])) {
			WindowLoader.updateResolution(1600, 800);
		} else if (newResolution.equals(RESOLUTIONS[2])) {
			WindowLoader.updateResolution(400, 200);
		}
		ps.centerOnScreen();
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
