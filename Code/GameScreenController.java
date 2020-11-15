import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
	@FXML private HBox cards;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		System.out.println("ID" + cards);
		cards.getChildren();

	}

	public void onButtonPressed() {
		System.out.println("button Pressed");
		Node newCard = null;

		try {
			newCard = FXMLLoader.load(getClass().getResource("Card.fxml"));
		} catch (IOException e) {
			System.out.println("Fail to load Card class due to:" + e.getCause());
		}
		cards.getChildren().add(newCard);
	}

	public void onQuitButton() {
		Platform.exit();
	}

	public void onSaveButton() {
		System.out.println("Game Saved");
	}


}
