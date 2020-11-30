package FrontEnd;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/***
 * Used to load new windows
 * @author Christian Sanger
 */
public class WindowLoader {
	private static final String fileLocation = "FrontEnd\\FXML\\";
	// Reference to the primary stage
	private static Stage w;
	private static boolean isFullscreen;

	/***
	 * Creates a window loader that changes the scene shown to the user.
	 * @param window any Node object on the stage that you wish control.
	 */
	public WindowLoader(Node window) {
		w = (Stage) window.getScene().getWindow();
	}

	/**
	 * Set if the window should be fullscreen or not
	 *
	 * @param fullscreen true for fullscreen, false for not
	 */
	public static void setFullScreen(boolean fullscreen) {
		isFullscreen = fullscreen;
		w.setFullScreen(isFullscreen);
	}

	/**
	 * Returns if the screen is set to be fullscreen
	 *
	 * @return true for fullscreen false for not
	 */
	public static boolean getIsFullScreen() {
		return isFullscreen;
	}

	/***
	 * swaps the scene for the given scene. Window should be the scene file name
	 * i.e. to swap to MenuScreen.fxml use "MenuScreen"
	 *
	 * @param window scene name
	 */
	public void load(String window) {
		Parent root = null;
		try {
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fileLocation + window + ".fxml")));
		} catch (IOException e) {
			System.out.print(e.getLocalizedMessage());
			e.printStackTrace();
		}
		if (root == null) {
			System.out.print("Scene loading failed, " + window + " could not be loaded");
		} else {
			w.getScene().setRoot(root);
		}
		w.setFullScreen(Main.isFullScreen());
	}

	/**
	 * Set a new resolution for the window
	 *
	 * @param width  the new width the window should be in pixels
	 * @param height the new height the window should be in pixels
	 */
	public static void updateResolution(int width, int height) {
		w.setWidth(width);
		w.setHeight(height);
		w.centerOnScreen();
	}
}
