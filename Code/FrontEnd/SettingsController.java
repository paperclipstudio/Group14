package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;

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
public class SettingsController extends StateLoad {

	/* These final variables are used for the game's Sound Effects (SFX) */

	private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
	private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
	private final String TEST_SFX = "Assets\\SFX\\skip.mp3";
	private final AudioClip TEST_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());


	@FXML
	private Button backButton;
	@FXML
	private Slider background;
	@FXML
	private Slider sfx;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (getInitData() != null) {
			sfx.setValue(Double.parseDouble(getInitData().get("SFXVol")));
			background.setValue(Double.parseDouble(getInitData().get("BackgroundVol")));
		}
	}
	/**
	 * updated the current sound level
	 */
	public void onBackgroundChange() {
		System.out.println(background.getValue());
		Main.setVolume(background.getValue());
		getInitData().put("BackgroundVol", ((int) background.getValue()) + "");
	}

	/**
	 * updated the current SFX sound level
	 */
	public void onSFXChange() {
		TEST_AUDIO.setVolume(sfx.getValue());
		if (!TEST_AUDIO.isPlaying()) {
			TEST_AUDIO.play();
		}
		getInitData().put("SFXVol", ((int) sfx.getValue()) + "");
	}

	/**
	 * Returns to menu screen and saves config to file
	 * @throws IOException if can't write to config
	 */
	public void onBackButton() throws IOException {
		String config = String.format("%f %f" ,
				background.getValue(),
				sfx.getValue());
		File configFile = new File("SaveData\\config.txt");
		FileWriter configWriter = new FileWriter(configFile);
		configWriter.write(config);
		configWriter.flush();
		configWriter.close();
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen", getInitData());
		RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
	}
}



