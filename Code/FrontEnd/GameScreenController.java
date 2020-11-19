import BackEnd.Coordinate;
import BackEnd.FloorTile;
import BackEnd.GameLogic;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML private Pane tiles;
	@FXML private Button drawButton;
	private int width;
	private int height;
	private GameLogic gameLogic;
	int tileWidth = 50;
	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		startNewGame("ExampleInput.txt");
		mainLoop();
	}

	/*
	The flow though this window goes like this
	mainLoop() : will check the current phase and update the visuals
		depending on the phase it will make a selection of controls visible
	onSomeButton() : when a button is pressed i.e. drawButton, it will have an
		onDrawButton called which will send the user choice to gameLogic the
		call back to mainLoop.
	mainLoop() -> show buttons -> wait for call back from button -> mainLoop()
	 */
	private void mainLoop() {
		updateBoard();
		Phase phase = gameLogic.getGamePhase();
		hideAllControls();
		switch (phase) {
			case DRAW:
				drawButton.setVisible(true);
				break;
			case FLOOR:
				break;
			case ACTION:
				break;
			case MOVE:
				break;
		}
	}

	private void updateBoard() {
		// showing the tiles
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.println(x + ":" + y);
				// Tile from the game board.
				FloorTile tile = gameLogic.getTileAt(new Coordinate(x, y));
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
					case UP:
						tileView.setRotate(0); // Not needed but just for consistency
						break;
					case RIGHT:
						tileView.setRotate(90);
						break;
					case DOWN:
						tileView.setRotate(180);
						break;
					case LEFT:
						tileView.setRotate(270);
						break;
				}
				// Translate
				tileView.setTranslateX(x * tileWidth);
				tileView.setTranslateY(y * tileWidth);

				// Scale
				tileView.setFitHeight(tileWidth);
				tileView.setFitWidth(tileWidth);
				// set ID
				tileView.setId("tile" + x + ":" + y);
				tiles.getChildren().add(tileView);

			}

		}
		// showing the player locations
		Image player = new Image("player1.png");
		for (Coordinate location: gameLogic.getPlayerLocations()) {
			ImageView imageview = new ImageView(player);
			imageview.setFitWidth(tileWidth);
			imageview.setFitHeight(tileWidth);
			imageview.setTranslateX(location.getX() * tileWidth);
			imageview.setTranslateY(location.getY() * tileWidth);
			tiles.getChildren().add(imageview);
		}
	}

	/**
	 * Clears the game and starts a new one with given starting board
	 * @param board path to board file
	 */
	public void startNewGame(String board) {
		gameLogic = new GameLogic();
		gameLogic.newGame(board);
		width = gameLogic.getWidth();
		height = gameLogic.getHeight();
		drawButton.setVisible(true);
	}

	private void hideAllControls() {
		drawButton.setVisible(false);
	}

	public void onDrawButton() {
		gameLogic.draw();
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
