package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProfilesController {

	@FXML
	private Button backButton;

	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}
}
