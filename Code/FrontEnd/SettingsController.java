package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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

	private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
	private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
	private final double SFX_VOLUME = 0.2;

	@FXML
	private Button backButton;
	@FXML
	private CheckBox fullscreen;
	@FXML
	private Slider sound;
	@FXML
	private ChoiceBox<String> resolution;
	final static private String[] RESOLUTIONS = new String[]{"600x400", "900x600", "1200x800"};

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (getInitData() != null) {
			for (String setting : RESOLUTIONS) {
				resolution.getItems().add(setting);
			}
			resolution.setValue(String.valueOf(getInitData().get("Resolution")));
			resolution.getSelectionModel().selectFirst();
		}
	}

	/**
	 * Called when user changes the resolution
	 * changes the resloution
	 */
	public void updateResolution() {
		String newResolution = resolution.getValue();
		if (newResolution.equals(RESOLUTIONS[0])) {
			WindowLoader.updateResolution(600, 400);
			getInitData().put("Resolution", "0");
		} else if (newResolution.equals(RESOLUTIONS[1])) {
			WindowLoader.updateResolution(900, 600);
			getInitData().put("Resolution", "1");
		} else if (newResolution.equals(RESOLUTIONS[2])) {
			WindowLoader.updateResolution(1200, 800);
			getInitData().put("Resolution", "2");
		}
	}

	/**
	 * updated the current sound level
	 */
	public void onSoundChange() {
		Main.setVolume(sound.getValue() / 200.0);
		getInitData().put("Volume", ((int) sound.getValue()) + "");
	}

	/**
	 * Called when full screen button is called
	 * updated the status of fullscreen
	 */
	public void onFullScreenChange() {
		getInitData().put("Fullscreen", fullscreen.isSelected() ? "true" : "false");
	}

	/**
	 * Returns to menu screen
	 * called by back button
	 *
	 * @throws IOException if can't write to config
	 */
	public void onBackButton() throws IOException {
		String config = "";
		config += getInitData().get("Volume") + " ";
		config += getInitData().get("Fullscreen") + " ";
		config += getInitData().get("Resolution");
		File configFile = new File("SaveData\\config.txt");
		if (!configFile.createNewFile()) {
			if (!configFile.delete()) {
				throw new IOException("Couldn't save file to " + configFile.toString());
			}
		}
		FileWriter configWriter = new FileWriter(configFile);
		System.out.println(config);
		configWriter.write(config);
		configWriter.flush();
		configWriter.close();
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen", getInitData());
		RETURN_AUDIO.play(SFX_VOLUME);
	}

	/**
	 * Returns the width of this resolution
	 *
	 * @param res the enum of this resolution
	 * @return the width.
	 */
	public static int getWidth(RESOLUTION res) {
		int width = 300;
		switch (res) {
			case SIX_BY_FOUR:
				width = 600;
				break;
			case NINE_BY_SIX:
				width = 900;
				break;
			case TWELVE_BY_EIGHT:
				width = 1200;
				break;
		}
		return width;
	}

	/**
	 * Returns the height of this resolution
	 *
	 * @param res the enum of this resolution
	 * @return the height.
	 **/
	public static int getHeight(RESOLUTION res) {
		int height = 300;
		switch (res) {
			case SIX_BY_FOUR:
				height = 400;
				break;
			case NINE_BY_SIX:
				height = 600;
				break;
			case TWELVE_BY_EIGHT:
				height = 800;
				break;
		}
		return height;
	}
}

enum RESOLUTION {
	SIX_BY_FOUR,
	NINE_BY_SIX,
	TWELVE_BY_EIGHT
}



