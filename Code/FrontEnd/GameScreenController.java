package FrontEnd;

import BackEnd.Coordinate;
import BackEnd.FloorTile;
import BackEnd.GameLogic;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import BackEnd.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static BackEnd.Phase.MOVE;
import static BackEnd.Rotation.*;
import static BackEnd.TileType.DOUBLE_MOVE;

/***
 * Use to control the GameScreen scene.
 * @author Chrisitan Sanger
 */
public class GameScreenController implements Initializable {
	@FXML private HBox cards;
	@FXML private Pane tiles;
	@FXML private Button drawButton;
	@FXML private Label phaseText;
	private int width;
	private int height;
	public Phase phase;
	private GameLogic gameLogic;
	public static int tileWidth = 50;
	private ImageView[] players;
	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		tiles.setTranslateZ(20);
		tiles.setRotationAxis(new Point3D(10,0,0));
		tiles.setRotate(10);
		startNewGame("ExampleInput.txt");
		updateBoard();
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

		//tiles.setRotate(tiles.getRotate() + 10);
		phase = gameLogic.getGamePhase();
		//phase = Phase.FLOOR;
		phaseText.setText(phase.toString() + ":" + gameLogic.getPlayersTurn() + ":Debug" );
		hideAllControls();
		switch (phase) {
			case DRAW:
				drawButton.setVisible(true);
				break;
			case FLOOR:
				setupFloorPhase();
				break;
			case ACTION:
				setupActionPhase();
				break;
			case MOVE:
				setupMovePhase();
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
				direction = UP;
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
				FloorTile playerTileChoice = (FloorTile) gameLogic.drawnCard();
				Node choiceCard = cards.getChildren().get(0).lookup("#image");
				double rotation = choiceCard.getRotate();
				if (rotation >= 0) {
					playerTileChoice.setRotation(UP);
				}
				if (rotation >= 90) {
					playerTileChoice.setRotation(RIGHT);
				}
				if (rotation >= 180) {
					playerTileChoice.setRotation(DOWN);
				}
				if (rotation >= 270) {
						playerTileChoice.setRotation(LEFT);
				}
				shiftTiles(direction, where, playerTileChoice);
				gameLogic.floor(playerTileChoice, coor);
				mainLoop();

			});
			tiles.getChildren().add(arrow);
		}
	}

	private void shiftTiles(Rotation direction, int location, FloorTile newTile) {
		// Add in new tile.
		Coordinate newTileLocation;
		switch (direction) {
			case UP:
				newTileLocation = new Coordinate(location, height);
				break;
			case LEFT:
				newTileLocation = new Coordinate(width, location);
				break;
			case DOWN:
				newTileLocation = new Coordinate(location, -1);
				break;
			case RIGHT:
				newTileLocation = new Coordinate(-1, location);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + direction);
		}
		ImageView tileView = Assets.getFloorTileImage(newTile, newTileLocation);
		tiles.getChildren().add(tileView);

		// Shift all tiles
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
				FadeTransition smoothVanish = new FadeTransition(new Duration(200));
				smoothVanish.setFromValue(100);
				smoothVanish.setToValue(0);
				switch (direction) {
					case RIGHT:
						if (y == location) {
							smooth.setByX(tileWidth);
							//smoothVanish.setNode(tile);
							if (x == width-1) {
								smoothVanish.setNode(tile);
							}
						}
						break;
					case DOWN:
						if (x == location) {
							smooth.setByY(tileWidth);
							if (y == height - 1) {
								smoothVanish.setNode(tile);
							}
						}
						break;
				}

				smooth.play();
				smoothVanish.play();
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
				tileView = Assets.getFloorTileImage(tile, x, y);

				// Scale
				tileView.setFitHeight(tileWidth);
				tileView.setFitWidth(tileWidth);
				// set ID

				tiles.getChildren().add(tileView);

			}

		}

		// showing the player locations
		players = new ImageView[gameLogic.getNumberOfPlayers()];
		for (int i = 0; i < gameLogic.getPlayerLocations().length; i++) {
			Coordinate location = gameLogic.getPlayerLocations()[i];
			ImageView playerView = Assets.getPlayer(i);
			playerView.setTranslateX(location.getX() * tileWidth);
			playerView.setTranslateY(location.getY() * tileWidth);
			players[i] = playerView;
			tiles.getChildren().add(playerView);
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
		// Draw controls
		drawButton.setVisible(false);
		//cards.getChildren().clear();
		// Floor controls
		for (int i = 0; i < tiles.getChildren().size(); i++) {

			ImageView image = (ImageView) tiles.getChildren().get(i);
			if (image == null) {
				continue;
			}
			if (image.getId() == null) {
				continue;
			}
			if (image.getId().equals("slide")) {
				//Todo this is gross but works for now
				tiles.getChildren().remove(i);
				i = 0;
			}
		}
	}

	private void setupActionPhase() {
		cards.getChildren().clear();
		ActionTile[] tiles = gameLogic.getActionCards();
		for (ActionTile tile : tiles) {
			final Node vCard = Assets.createCard(tile);
			vCard.setOnMouseClicked((e) -> {
			});
			cards.getChildren().add(vCard);
			switch (tile.getType()) {
				case DOUBLE_MOVE:
					vCard.setOnMouseClicked((e) -> {
						vCard.setEffect(new Bloom(0.03));
						vCard.setOnMouseClicked((e2) -> {
							gameLogic.action(new ActionTile(DOUBLE_MOVE), null);
							for (ImageView player:players) {
								player.setEffect(new Bloom(999));
								player.setOnMouseClicked(e3 -> {});
							}
							mainLoop();
						});
					});
					break;
				case BACKTRACK:
					vCard.setOnMouseClicked((e) -> {
						hideAllControls();
						// Get all players that can be back tracked
						boolean[] validPlayers = gameLogic.getPlayersThatCanBeBacktracked();
						for (int i = 0; i < gameLogic.getNumberOfPlayers(); i++) {
							if (validPlayers[i]) {
								// For each valid player
								final int playerNumber = i;
								// make them glow
								players[i].setEffect(new Bloom(0.05));
								// Set them into an active button
								players[i].setOnMouseClicked(e2 -> {
									// When they are clicked remove bloom from players
									for(ImageView player : players) {
										//TODO don't like this nesting fix later OwO.
										player.setEffect(new Bloom(999));
										// Deactivate their click.
										player.setOnMouseClicked(e3 -> {});
									}

									gameLogic.backtrack(playerNumber);
									mainLoop();
								});
							}

						}
					});

					break;
				case FIRE:
					break;
				case FROZEN:
					break;
			}
		}
	}

	private void setupMovePhase() {
		Coordinate[] validLocations = gameLogic.getMoveLocations();
		for (Coordinate coordinate: validLocations) {
			// Create pointer.
			final ImageView pointer = Assets.getLocationArrow();
			// Move to correct location.
			pointer.setTranslateX(coordinate.getX() * tileWidth);
			pointer.setTranslateY(coordinate.getY() * tileWidth);
			pointer.setOnMouseClicked(e -> {
				gameLogic.move(coordinate);
				Node currentPlayer = players[gameLogic.getPlayersTurn()];
				TranslateTransition walk = new TranslateTransition();
				walk.setToX(coordinate.getX() * tileWidth);
				walk.setToY(coordinate.getY());
				walk.setNode(currentPlayer);
				walk.setDuration(new Duration(500));
			});
			tiles.getChildren().add(pointer);

		}
	}
	/**
	 * Called when Draw button is pressed
	 */
	public void onDrawButton() {
		gameLogic.draw();

		cards.getChildren().add(Assets.createCard(gameLogic.drawnCard()));

		mainLoop();
	}

	/***
	 * Used for testing, called when test button is pushed, puts card into players hand.
	 */
	public void onButtonPressed() {
		Node newCard = null;
		System.out.println("Test Button");
		try {
			newCard = FXMLLoader.load(getClass().getClassLoader().getResource("FrontEnd\\Card.fxml"));
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

	/**
	 * Takes an id to match and a onClickFunction
	 * puts that on all matching node with id starting with 'id'
	 * @param id node to look for
	 * @param func function to apply to onClick
	 */
	private void applyOnClick(String id, EventHandler<MouseEvent> func) {
		for (Object o : tiles.getChildren().toArray()) {
			if (o == null) {
				continue;
			}
			if (!(o instanceof Node)) {
				continue;
			}
			Node tile = (Node) o;
			if (tile.getId().contains(id)) {
				tile.setOnMouseClicked(func);
			}
		}
	}

	/**
	 * Takes an id to match and a function
	 * puts that on all matching node with id starting with 'id'
	 * @param id node to look for
	 * @param func function to apply to onHover
	 */
	private void applyOnHover(String id, EventHandler<MouseEvent> func) {
		for (Object o : tiles.getChildren().toArray()) {
			if (o == null) {
				continue;
			}
			if (!(o instanceof Node)) {
				continue;
			}
			Node tile = (Node) o;
			if (tile.getId().contains(id)) {
				tile.setOnMouseEntered(func);
			}
		}
	}

	/**
	 * Takes an id to match and a function
	 * puts that on all matching node with id starting with 'id'
	 * @param id node to look for
	 * @param func function to apply to offHover
	 */
	private void applyOffHover(String id, EventHandler<MouseEvent> func) {
		for (Object o : tiles.getChildren().toArray()) {
			if (o == null) {
				continue;
			}
			if (!(o instanceof Node)) {
				continue;
			}
			Node tile = (Node) o;
			if (tile.getId().contains(id)) {
				tile.setOnMouseExited(func);
			}
		}
	}
}
