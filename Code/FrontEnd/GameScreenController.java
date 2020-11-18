import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import BackEnd.*;
import javafx.scene.layout.Pane;

/***
 * Use to control the GameScreen scene.
 * @author Chrisitan Sanger
 */
public class GameScreenController implements Initializable {
	@FXML private HBox cards;
	@FXML
	private Pane tiles;

	int tileWidth = 50;
	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startNewGame();
	}

	public void startNewGame() {
		GameLogic gl = new GameLogic();
		int width = gl.getWidth();
		int height = gl.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Tile from the gameboard.
				FloorTile tile = gl.getTileAt(new Coordinate(x, y));
				// What is going to be shown on screen
				ImageView tileView = new ImageView("Error.png");
				// Get correct image
				try {
					tileView.setImage(new Image(tile.getType() + ".png"));
				} catch (Exception e) {
					System.out.println("failed to find " + tile.getType() + ".png");
				}
				// Rotate
				switch (tile.getRotation()) {
					case UP: tileView.setRotate(0); // Not needed but just for consistency
						break;
					case RIGHT: tileView.setRotate(90);
						break;
					case DOWN: tileView.setRotate(180);
						break;
					case LEFT: tileView.setRotate(270);
						break;
				}
				// Translate
				tileView.setTranslateX(x * tileWidth);
				tileView.setTranslateY(y * tileWidth);

				// Scale
				tileView.setFitHeight(tileWidth);
				tileView.setFitWidth(tileWidth);
				// set ID
				tileView.setId("tile" + Integer.toString(x) + ":" + Integer.toString(y));
				tiles.getChildren().add(tileView);

			}
		}
	}

	/***
	 * Used for testing, called when test button is pushed, puts card into players hand.
	 */
	public void onButtonPressed() {
		Node newCard = null;

		try {
			newCard = FXMLLoader.load(getClass().getResource("FrontEnd\\Card.fxml"));
		} catch (IOException e) {
			System.out.println("Fail to load Card class due to:" + e.getCause());
		}
		cards.getChildren().add(newCard);
	}

	/***
	 * Quits the whole application.
	 */
	public void onQuitButton() {
		Platform.exit();
	}

	/***
	 * Starts save game window.
	 */
	@SuppressWarnings("unused")
	public void onSaveButton() {
		System.out.println("Game Saved");
	}

}
