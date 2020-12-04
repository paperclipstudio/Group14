package FrontEnd;

import BackEnd.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

import static BackEnd.Rotation.*;
import static BackEnd.TileType.*;

/***
 * Use to control the GameScreen scene.
 * @author Chrisitan Sanger
 */
public class GameScreenController extends StateLoad {
	@FXML
	private VBox cards;
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
	@FXML
	private Pane fixed;
	@FXML
	private Pane profile;
	@FXML
	private HBox confirmation;


    private int width;
    private int height;
    public Phase phase;
    private GameLogic gameLogic;
    public static int tileWidth = 50;

	/***
	 * Gets all resources for gameScreen
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (getInitData() != null) {
			try {
				if (getInitData().get("isLoadedFile").equals("true")) {
					loadGame();
				} else {
					startNewGame();
				}
				int rotate = 0;
				tiles.setRotationAxis(new Point3D(10, 0, 10));
				tiles.setRotate(rotate);
				players.setRotationAxis(new Point3D(10, 0, 10));
				players.setRotate(rotate);
				controls.setRotationAxis(new Point3D(10, 0, 10));
				controls.setRotate(rotate);
				tileWidth = 30;//(int) (ps.getHeight() / gameLogic.getHeight()) + 50;
				updateBoard();
				mainLoop();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			confirmation.setVisible(false);
		}
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
	private void mainLoop() throws IOException {
		// Update current player
		Profile[] profiles = new Profile[4];
		for (int i=0; i < gameLogic.getNumberOfPlayers(); i++) {
			profiles[i] = Profile.readProfile(getInitData().get("Profile" + (i)));
		}
		profile.getChildren().add(Assets.getProfile(profiles[gameLogic.getPlayersTurn()]));
		try {
			updateBoard();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		phase = gameLogic.getGamePhase();
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ACTION:
				try {
					setupActionPhase();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case MOVE:
				try {
					setupMovePhase();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case WIN:
				try {
					setupWinScreen();
				} catch (IOException e) {
					phaseText.setText(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	private void setupWinScreen() throws Exception {
		WindowLoader wl = new WindowLoader(drawButton);
		getInitData().put("Winner", gameLogic.getWinner() + "");
		wl.load("WinScreen", getInitData());
	}

    private void setupFloorPhase() throws Exception {
        ArrayList<Coordinate> locations = gameLogic.getSlideLocations();
        if (locations.size() == 0) {
            throw new Exception("No slide locations");
        }
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
                where = coordinate.getY();
            } else if (coordinate.getY() == height) {
                arrow.setRotate(270);
                direction = UP;
                where = coordinate.getX();
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
                try {
                    gameLogic.floor(playerTileChoice, coordinate);
                    mainLoop();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

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
        Pane tileView = Assets.getFloorTileImage(newTile, newTileLocation);
        tiles.getChildren().add(tileView);

        // Shift players
        applyToAll(players, player -> {
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
                case LEFT:
                    if (y == location) {
                        if (x <= 0) {
                            smooth.setToX(tileWidth * (width - 1));
                            smooth.setDuration(new Duration(600));
                        } else {
                            smooth.setByX(-tileWidth);
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
                case UP:
                    if (x == location) {
                        if (y == 0) {
                            smooth.setToY(tileWidth * (height - 1));
                            smooth.setDuration(new Duration(600));
                        } else {
                            smooth.setByY(-tileWidth);
                        }
                    }
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
                case LEFT:
                    if (y == location) {
                        smooth.setByX(-tileWidth);
                        if (x <= 0) {
                            smoothVanish.setNode(tile);
                        }
                    }
                    break;
                case UP:
                    if (x == location) {
                        smooth.setByY(-tileWidth);
                        if (y <= 0) {
                            smoothVanish.setNode(tile);
                        }
                    }
            }

            smooth.play();
            smoothVanish.play();
            return 0;

        });
    }

    private void updateBoard() throws Exception {
        tiles.setPrefWidth((width + 4) * tileWidth);
        tiles.setPrefHeight((height + 4) * tileWidth);
        controls.setPrefWidth((width + 4) * tileWidth);
        controls.setPrefHeight((height + 4) * tileWidth);
        players.setPrefHeight((height + 4) * tileWidth);
        players.setPrefWidth((width + 4) * tileWidth);
        fixed.setPrefWidth((width + 4) * tileWidth);
        fixed.setPrefHeight((width + 4) * tileWidth);

        tiles.getChildren().clear();
        fixed.getChildren().clear();
        players.getChildren().clear();
        controls.getChildren().clear();

        // showing the tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                // Tile from the game board.
                FloorTile tile = gameLogic.getTileAt(new Coordinate(x, y));

                // What is going to be shown on screen
                // Get correct image
                Node tileView = Assets.getFloorTileImage(tile, x, y);
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
	 * @throws Exception if issue with board file.
	 */
	public void startNewGame() throws Exception {
		gameLogic = new GameLogic(Integer.parseInt(getInitData().get("Seed")));
		GameSave gameSave = new GameSave(getInitData());
		gameLogic.newGame(getInitData().get("Board"), gameSave);
		gameLogic.setNumberOfPlayers(Integer.parseInt(getInitData().get("PlayerCount")));
		width = gameLogic.getWidth();
		height = gameLogic.getHeight();
		mainLoop();
	}

	private void loadGame() throws Exception {
		Pair<GameLogic, Profile[]> result = GameLoad.loader(getInitData());
		gameLogic = result.getKey();
		Profile[] profiles = result.getValue();
		for (int i = 0; i < profiles.length; i++) {
			getInitData().put("Profile" + i, profiles[i].getName());
		}
		width = gameLogic.getWidth();
		height = gameLogic.getHeight();
		gameLogic.setNumberOfPlayers(Integer.parseInt(getInitData().get("PlayerCount")));
		gameLogic.emptyGameSaver();
		mainLoop();
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

    private void setupActionPhase() throws Exception {
        cards.getChildren().clear();
        ActionTile[] tiles = gameLogic.getActionCards();
        // Add a skip button
        Button skip = new Button();
        skip.setText("skip");
        cards.getChildren().add(skip);
        skip.setOnMouseClicked(e1 -> {
                    try {
                        skipActionOnCLick(e1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        if (gameLogic.drawnCard() != null) {
            Node drawnCard = Assets.createCard(gameLogic.drawnCard());
            ColorAdjust dim = new ColorAdjust();
            dim.setBrightness(0.6);
            dim.setSaturation(0.5);
            drawnCard.setEffect(dim);
            drawnCard.setOnMouseClicked((e) -> {
            });
            drawnCard.setOnMouseEntered((e) -> {
            });
            drawnCard.setOnMouseExited((e) -> {
            });
            cards.getChildren().add(drawnCard);
        }



		for (ActionTile tile : tiles) {
			final Node vCard = Assets.createCard(tile);
			vCard.setOnMouseClicked((e) -> {
			});
			cards.getChildren().add(vCard);
			switch (tile.getType()) {
				case DOUBLE_MOVE:
					vCard.setOnMouseClicked((e) -> {
						vCard.setEffect(new Bloom(0.03));
						vCard.setOnMouseClicked(e2 -> {
							try {
								doubleMoveAction(e2);
							} catch (Exception exception) {
								exception.printStackTrace();
							}
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
									try {
										gameLogic.action(new ActionTile(BACKTRACK), null, playerNumber);
									} catch (Exception exception) {
										exception.printStackTrace();
									}
									try {
										updateBoard();
									} catch (Exception exception) {
										exception.printStackTrace();
									}
									try {
										mainLoop();
									} catch (IOException ioException) {
										ioException.printStackTrace();
									}
								});
							}

                        }
                    });

                    break;
                case FIRE:
                    vCard.setOnMouseClicked((e) -> {
                        hideAllControls();
                        Node fire = Assets.getFireEffect();
                        controls.getChildren().add(fire);
                        controls.setOnMouseMoved((e2) -> {
                            LocationSelectOnClick(fire, e2, FIRE);
                        });

                    });
                    break;
                case FROZEN:
                    vCard.setOnMouseClicked((e) -> {
                        hideAllControls();
                        Node frozen = Assets.getFrozenEffect();
                        controls.getChildren().add(frozen);
                        controls.setOnMouseMoved((e2) -> {
                            LocationSelectOnClick(frozen, e2, FROZEN);
                        });
                    });
                    break;
            }
        }
    }



	/**
	 * Sets up what happens when a fire / frozen card is clicked
	 * so that now you can choose a location on the board
	 *
	 * @param card     the card that has been clicked on
	 * @param e2       the mouse event
	 * @param tileType what tile type it is.
	 */
	private void LocationSelectOnClick(Node card, MouseEvent e2, TileType tileType) {
		int getX = (int) (e2.getX() / tileWidth);
		int getY = (int) (e2.getY() / tileWidth);
		if (getX < 1) {
			getX = 1;
		}
		if (getX > width - 2) {
			getX = width - 2;
		}
		if (getY < 1) {
			getY = 1;
		}
		if (getY > height - 2) {
			getY = height - 2;
		}
		final int x = getX;
		final int y = getY;
		card.setTranslateX((getX - 1) * tileWidth);
		card.setTranslateY((getY - 1) * tileWidth);
		card.setOnMouseClicked((e3) -> {
			try {
				gameLogic.action(new ActionTile(tileType), new Coordinate(x, y), -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				mainLoop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void setupMovePhase() throws Exception {
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
				try {
					gameLogic.move(coordinate);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				controls.getChildren().clear();
				TranslateTransition walk = new TranslateTransition();
				walk.setToX(coordinate.getX() * tileWidth);
				walk.setToY(coordinate.getY() * tileWidth);
				walk.setNode(currentPlayer);
				walk.setDuration(new Duration(500));
				walk.play();
				removeAll("locationarrow");
				walk.setOnFinished((e2) -> {
					try {
						mainLoop();
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				});
			});
			controls.getChildren().add(pointer);
		}
	}

    /**
     * Called when Draw button is pressed
     */
    public void onDrawButton() throws IOException {
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
	 * Quits to main menu unless the game is unsaved.
	 */
	public void onQuitButton() {
		if(gameLogic.isGameSaved()) {
			WindowLoader wl = new WindowLoader(drawButton);
			wl.load("MenuScreen", getInitData());
		} else {
			confirmation.setVisible(true);
		}
	}

	/**
	 * Saves the game and quits to main menu
	 */
	public void onYes() {
		try {
			gameLogic.saveGame();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Game NOT saved");
		}
		System.out.println("Game Saved");
		WindowLoader wl = new WindowLoader(drawButton);
		wl.load("MenuScreen", getInitData());
	}

	/**
	 * quits to main menu without saving
	 */
	public void onNo() {
		WindowLoader wl = new WindowLoader(drawButton);
		wl.load("MenuScreen", getInitData());
	}
	/***
	 * Starts save game window.
	 */
	public void onSaveButton() {
		try {
			gameLogic.saveGame();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Game NOT saved");
		}
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
     * @param group node to look for
     * @param func  function to apply to onClick
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
     * @param group node to look for
     * @param func  function to apply to onHover
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
     * @param group node to look for
     * @param func  function to apply to offHover
     */
    private void applyOffHover(Pane group, EventHandler<MouseEvent> func) {
        applyToAll(group, n -> {
            n.setOnMouseExited(func);
            return 0;
        });

    }

    private void removeAll(String id) {
        tiles.getChildren().removeIf(Objects::isNull);
        tiles.getChildren().removeIf(n -> {
            if (n.getId() == null) {
                return false;
            } else {
                return n.getId().contains(id);
            }
        });
    }

    private void skipActionOnCLick(MouseEvent e) throws Exception {
        gameLogic.action(null, null, 0);
        mainLoop();
    }

    private void doubleMoveAction(MouseEvent e2) throws Exception {
        gameLogic.action(new ActionTile(DOUBLE_MOVE), null, 0);
        for (Node player : players.getChildren()) {
            player.setEffect(new Bloom(999));
            player.setOnMouseClicked(e3 -> {
            });
        }
        mainLoop();
    }
}
