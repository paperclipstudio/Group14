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
	static final String fxmlFile = "file:///C:\\Users\\Christian\\Git\\Group14\\Code\\GameScreen.fxml";

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		CardController newCardController = new CardController();

		Node newCard = null;
		try {
			newCard = FXMLLoader.load(getClass().getResource("Card.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ID" + cards.getChildren().get(0).getId());
		cards.getChildren().add(newCard);
	}
}
