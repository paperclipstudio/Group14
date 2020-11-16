package FrontEnd;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * Use to control the devScreen scene.
 * @author Chrisitan Sanger
 */
public class DevScreenController implements Initializable {

	@FXML
	private Button counterButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Dev Screen created");
	}

	public void incCounter() {
		String text = counterButton.getText();
		if (text.equals("Add one to count")) {
			counterButton.setText("1");
		} else {
			int number = Integer.parseInt(text);
			number++;
			counterButton.setText(Integer.toString(number));
		}

	}
}
