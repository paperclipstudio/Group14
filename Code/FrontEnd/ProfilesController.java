package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/***
 * Screen used to create edit and delete profiles.
 */
public class ProfilesController {
	@FXML
	private Button backButton;

	/***
	 * Returns to menu screen
	 * called by back button
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}
}
