package FrontEnd;

import BackEnd.Coordinate;
import BackEnd.FloorTile;
import BackEnd.GameLogic;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import BackEnd.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/***
 * Use to control the GameScreen scene.
 * @author Chrisitan Sanger
 */
public class GameScreenController implements Initializable {
	//TODO Fix the graphical output of tiles.
	@FXML private HBox cards;
	@FXML private Pane tiles;
	@FXML private Button drawButton;
	@FXML private Label phaseText;
	private int width;
	private int height;
	private GameLogic gameLogic;
	public static int tileWidth = 50;
	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//load assets
		/*
		URL cardFile = getClass().getResource("FrontEnd\\Card.fxml");
		assets = new HashMap<>();
		String[] listOfAssets = new String[] {
			"Corner",
			"Error",
			"Goal",
			"player1",
			"Straight",
				"Tee",
				"SlideArrow"
		};
		for (String asset: listOfAssets) {
			assets.put(asset, new Image(asset + ".png"));
		}

		 */

		tiles.setTranslateZ(20);
		tiles.setRotationAxis(new Point3D(10,0,0));
		tiles.setRotate(10);
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

		tiles.setRotate(tiles.getRotate() + 10);
		updateBoard();
		Phase phase = gameLogic.getGamePhase();
		phase = Phase.FLOOR;
		phaseText.setText(phase.toString());
		hideAllControls();
		switch (phase) {
			case DRAW:
				drawButton.setVisible(true);
				break;
			case FLOOR:
				setupFloorPhase();
				break;
			case ACTION:
				break;
			case MOVE:
				break;
		}
	}

	private void setupFloorPhase() {
		Coordinate[] locations = gameLogic.getSlideLocations();
		for (Coordinate coor: locations) {
			ImageView arrow = Assets.makeArrow();
			final Rotation direction;
			final int where;
			if (coor.getX() == -1){
				arrow.setRotate(0);
				direction = Rotation.RIGHT;
				where = coor.getY();
			} else if (coor.getY() == -1) {
				arrow.setRotate(90);
				direction = Rotation.DOWN;
				where = coor.getX();
			} else if (coor.getX() == width) {
				arrow.setRotate(180);
				direction = Rotation.LEFT;
				where = coor.getX();
			} else if (coor.getY() == height) {
				arrow.setRotate(270);
				direction = Rotation.UP;
				where = coor.getY();
			} else {
				direction = Rotation.DOWN;
				where = 2;
				// Invalid Push location
				assert(false);
			}
			arrow.setFitWidth(tileWidth);
			arrow.setFitHeight(tileWidth);
			arrow.setTranslateX(coor.getX() * tileWidth);
			arrow.setTranslateY(coor.getY() * tileWidth);
			arrow.setOnMouseClicked((e) -> {
				arrow.setEffect(new Bloom(0.1));
				shiftTiles(tiles, direction, where);

			});
			tiles.getChildren().add(arrow);
		}
	}

	private void shiftTiles(Node node, Rotation direction, int location) {

		for (Object o : tiles.getChildren().toArray()) {
			// Skips if the object is null;
			if (o == null) {
				continue;
			}
			Node tile = (Node) o;
			// Skips if tile has no ID.
			if (tile.getId() == null) {
				continue;
			}
			if (tile.getId().split(" ")[0].equals("tile")) {
				int x = ((int) tile.getTranslateX()) / tileWidth;
				int y = ((int) tile.getTranslateY()) / tileWidth;
				TranslateTransition smooth = new TranslateTransition();
				smooth.setNode(tile);
				smooth.setDuration(new Duration(200));
				switch (direction) {
					case RIGHT:
						if (y == location) {
							smooth.setByX(tileWidth);
						}
						break;
					case DOWN:
						if (x == location) {
							smooth.setByY(tileWidth);
						}
						break;
				}
				smooth.play();
			}
		}
	}
	private void updateBoard() {
		tiles.getChildren().clear();
		// showing the tiles
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Tile from the game board.
				FloorTile tile = gameLogic.getTileAt(new Coordinate(x, y));
				// What is going to be shown on screen
				ImageView tileView;
				// Get correct image
				//tileView.setImage(assets.get(tile.getType().toString()));
				tileView = Assets.getFloorTileImage(tile);

				// Translate
				tileView.setTranslateX(x * tileWidth);
				tileView.setTranslateY(y * tileWidth);

				// Scale
				tileView.setFitHeight(tileWidth);
				tileView.setFitWidth(tileWidth);
				// set ID
				tileView.setId("tile " + x + " " + y);
				tiles.getChildren().add(tileView);

			}

		}

		for(Object i : tiles.getChildren().toArray()) {
			ImageView j = (ImageView) i;
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

	/**
	 * hides all controls ready for when mainloop shows the correct ones.
	 */
	private void hideAllControls() {
		drawButton.setVisible(false);
	}

	/**
	 * Called when Draw button is pressed
	 */
	public void onDrawButton() {
		gameLogic.draw();
		Node newCard = null;
		try {
			newCard = FXMLLoader.load(getClass().getResource("FrontEnd\\Card.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cards.getChildren().add(newCard);

		mainLoop();
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
