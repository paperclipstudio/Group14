package FrontEnd;

import BackEnd.Coordinate;
import BackEnd.FloorTile;
import BackEnd.GameLogic;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

import BackEnd.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static BackEnd.Phase.MOVE;
import static BackEnd.Rotation.*;
import static BackEnd.TileType.DOUBLE_MOVE;
import static BackEnd.TileType.FIRE;

/***
 * Use to control the GameScreen scene.
 * @author Chrisitan Sanger
 */
public class GameScreenController implements Initializable {
	@FXML
	private HBox cards;
	@FXML
	private Pane tiles;
	@FXML
	private Pane players;
	@FXML
	private Pane controls;
	@FXML
	private Button drawButton;
	@FXML
	private Label phaseText;


	private int width;
	private int height;
	public Phase phase;
	private GameLogic gameLogic;
	public static int tileWidth = 25;
	//private ImageView[] players;

	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
		phaseText.setText(phase.toString() + ":" + gameLogic.getPlayersTurn() + ":Debug");
		hideAllControls();
		switch (phase) {
			case DRAW:
				drawButton.setVisible(true);
				break;
			case FLOOR:
				try {
					setupFloorPhase();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case ACTION:
				try {
					setupActionPhase();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case MOVE:
				setupMovePhase();
				break;
		}
	}

	private void setupFloorPhase() throws IOException {
		Coordinate[] locations = gameLogic.getSlideLocations();
		for (Coordinate coordinate : locations) {
			ImageView arrow = Assets.makeArrow();
			final Rotation direction;
			final int where;
			if (coordinate.getX() == -1) {
				arrow.setRotate(0);
				direction = Rotation.RIGHT;
				where = coordinate.getY();
			} else if (coordinate.getY() == -1) {
				arrow.setRotate(90);
				direction = Rotation.DOWN;
				where = coordinate.getX();
			} else if (coordinate.getX() == width) {
				arrow.setRotate(180);
				direction = Rotation.LEFT;
				where = coordinate.getX();
			} else if (coordinate.getY() == height) {
				arrow.setRotate(270);
				direction = UP;
				where = coordinate.getY();
			} else {
				direction = Rotation.DOWN;
				where = 2;
				// Invalid Push location
				assert (false);
			}
			arrow.setFitWidth(tileWidth);
			arrow.setFitHeight(tileWidth);
			arrow.setTranslateX(coordinate.getX() * tileWidth);
			arrow.setTranslateY(coordinate.getY() * tileWidth);

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
				gameLogic.floor(playerTileChoice, coordinate);
				mainLoop();

			});
			controls.getChildren().add(arrow);
		}
		cards.getChildren().removeIf(Objects::isNull);
		if (gameLogic.drawnCard() != null) {
			// TODO  remove this as its for testing.
			cards.getChildren().add(Assets.createCard(gameLogic.drawnCard()));
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

		// Shift players
		applyToAll(players,  player -> {
			int x = ((int) player.getTranslateX()) / tileWidth;
			int y = ((int) player.getTranslateY()) / tileWidth;
			TranslateTransition smooth = new TranslateTransition();
			smooth.setNode(player);
			smooth.setDuration(new Duration(200));
			switch (direction) {
				case RIGHT:
					if (y == location) {
						if (x >= width - 1) {
							smooth.setToX(0);
							smooth.setDuration(new Duration(600));
							//tiles.getChildren().remove(player);
							//tiles.getChildren().add(player);
						} else {
							smooth.setByX(tileWidth);
						}
					}
					break;
				case DOWN:
					if (x == location) {
						if (y == height - 1) {
							smooth.setToY(0);
							smooth.setDuration(new Duration(600));

						} else {
							smooth.setByY(tileWidth);
						}
					}
					break;
			}
			smooth.play();
			return 0;
		});
		// Shift all tiles

		applyToAll(tiles, tile -> {
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
						if (x >= width - 1) {
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
			return 0;

		});
	}

	private void updateBoard() {
		tiles.setPrefWidth((width + 4) * tileWidth);
		tiles.setPrefHeight((height + 4) * tileWidth);
		controls.setPrefWidth((width + 4) * tileWidth);
		controls.setPrefHeight((height + 4) * tileWidth);
		players.setPrefHeight((height + 4) * tileWidth);
		players.setPrefWidth((width + 4) * tileWidth);
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
		//players = new ImageView[gameLogic.getNumberOfPlayers()];
		for (int i = 0; i < gameLogic.getPlayerLocations().length; i++) {
			Coordinate location = gameLogic.getPlayerLocations()[i];
			ImageView playerView = Assets.getPlayer(i);
			playerView.setTranslateX(location.getX() * tileWidth);
			playerView.setTranslateY(location.getY() * tileWidth);
			players.getChildren().add(playerView);
		}
	}

	/**
	 * Clears the game and starts a new one with given starting board
	 *
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
		cards.getChildren().clear();
		controls.getChildren().clear();
	}

	private void setupActionPhase() throws IOException {
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
							for (Node player : players.getChildren()) {
								player.setEffect(new Bloom(999));
								player.setOnMouseClicked(e3 -> {
								});
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
						Node[] playerSelectControls = new Node[validPlayers.length];
						for (int i = 0; i < gameLogic.getNumberOfPlayers(); i++) {
							if (validPlayers[i]) {
								// For each valid player
								final int playerNumber = i;
								// make them glow
								Node fakePlayer = Assets.getPlayer(i);
								fakePlayer.setTranslateX(gameLogic.getPlayerLocations()[i].getX() * tileWidth);
								fakePlayer.setTranslateY(gameLogic.getPlayerLocations()[i].getY() * tileWidth);
								fakePlayer.setEffect(new Bloom(0.1));
								controls.getChildren().add(fakePlayer);
								// Set them into an active button
								fakePlayer.setOnMouseClicked(e2 -> {
									hideAllControls();
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
		if (validLocations.length == 0) {
			// No where to move;
			gameLogic.move(gameLogic.getPlayerLocations()[gameLogic.getPlayersTurn()]);
			mainLoop();
		}
		for (Coordinate coordinate : validLocations) {
			// Create pointer.
			final ImageView pointer = Assets.getLocationArrow();
			// Move to correct location.
			pointer.setTranslateX(coordinate.getX() * tileWidth);
			pointer.setTranslateY(coordinate.getY() * tileWidth);
			pointer.setOnMouseClicked(e -> {
				Node currentPlayer = players.getChildren().get(gameLogic.getPlayersTurn());
				gameLogic.move(coordinate);
				TranslateTransition walk = new TranslateTransition();
				walk.setToX(coordinate.getX() * tileWidth);
				walk.setToY(coordinate.getY() * tileWidth);
				walk.setNode(currentPlayer);
				walk.setDuration(new Duration(500));
				walk.play();
				removeAll("locationarrow");
				walk.setOnFinished((e2) -> mainLoop());
			});
			controls.getChildren().add(pointer);

		}
	}

	/**
	 * Called when Draw button is pressed
	 */
	public void onDrawButton() throws IOException{
		gameLogic.draw();
		//TODO just show drawn card.
		cards.getChildren().add(Assets.createCard(gameLogic.drawnCard()));

		mainLoop();
	}

	/***
	 * Used for testing, called when test button is pushed, puts card into players hand.
	 */
	public void onButtonPressed() {
		System.out.println("Test Button");
		applyToAll(tiles, (v) -> {
			v.setRotate(v.getRotate() + 20);
			return 0;
		});
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


	private void applyToAll(Pane group, Function<Node, Integer> f) {
		for (Object o : group.getChildren()) {
			if (o == null) {
				continue;
			}
			Node n = (Node) o;
			if (n.getId() == null) {
				continue;
			}
			f.apply(n);
		}
	}

	/**
	 * Takes an id to match and a onClickFunction
	 * puts that on all matching node with id starting with 'id'
	 *
	 * @param id   node to look for
	 * @param func function to apply to onClick
	 */
	private void applyOnClick(Pane group, EventHandler<MouseEvent> func) {
		applyToAll(group, v -> {
			v.setOnMouseClicked(func);
			return 0;
		});
	}

	/**
	 * Takes an id to match and a function
	 * puts that on all matching node with id starting with 'id'
	 *
	 * @param id   node to look for
	 * @param func function to apply to onHover
	 */
	private void applyOnHover(Pane group, EventHandler<MouseEvent> func) {
		applyToAll(group, (n) -> {
			n.setOnMouseEntered(func);
			return 0;
		});
	}

	/**
	 * Takes an id to match and a function
	 * puts that on all matching node with id starting with 'id'
	 *
	 * @param id   node to look for
	 * @param func function to apply to offHover
	 */
	private void applyOffHover(Pane group, EventHandler<MouseEvent> func) {
		applyToAll(group, n -> {
			n.setOnMouseExited(func);
			return 0;
		});

	}

	private void removeAll(String id) {
		tiles.getChildren().removeIf(n -> n == null);
		tiles.getChildren().removeIf(n -> {
			if (n.getId() == null) {
				return false;
			} else {
				return n.getId().contains(id);
			}
		});
	}
}
