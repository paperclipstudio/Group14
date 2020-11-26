package FrontEnd;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	final static private String[] RESOLUTIONS = new String[]{"600x400", "1200x800"};
	private Stage ps;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		fullscreen.setSelected(WindowLoader.getIsFullScreen());
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
		ChangeListener<Number> soundListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				onSoundChange();
			}
		};
		sound.valueProperty().addListener(soundListener);
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
			WindowLoader.updateResolution(600, 400);
		} else if (newResolution.equals(RESOLUTIONS[1])) {
			WindowLoader.updateResolution(1200, 800);
		}
	}

	public void onSoundChange() {
		Main.setVolume(sound.getValue() / 200.0);

	}
	public void onFullScreenChange() {
		Main.setFullScreen(fullscreen.isSelected());
		WindowLoader.setFullScreen(Main.isFullScreen());
	}


	/***
	 * Returns to menu screen
	 * called by back button
	 */
	public void onBackButton() throws IOException {
		String config = "";
		config += Main.getVolumne() + "\n";
		config += Main.isFullScreen() + "\n";
		config += Main.getResolution() + "\n";
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
		File configFile = new File("SaveData\\config.txt");
		if (!configFile.exists()) {
			configFile.createNewFile();
		}
		FileWriter configWriter = new FileWriter(configFile);
		System.out.println(config);
		configWriter.write(config);
		configWriter.flush();
		configWriter.close();


	}

}
