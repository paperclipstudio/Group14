
package BackEnd;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static BackEnd.Rotation.*;

public class Gameboard {

	private int width;
	private int height;
	private SilkBag silkbag;
	private ArrayList<Coordinate> goalCoors;
	private Coordinate[][] playerLocations;
	private FloorTile[][] boardTiles;
	private FloorTile[][] fixedTiles;
	private FloorTile removedTile;
	private Coordinate[] slideLocations;


	public Gameboard(int width, int height, SilkBag silkBag) {
		this.width = width;
		this.height = height;
		this.silkbag = silkBag;
		goalCoors = new ArrayList<>();
		slideLocations = new Coordinate[10];
		boardTiles = new FloorTile[100][100];
		fixedTiles = new FloorTile[100][100];
		playerLocations = new Coordinate[4][3];
	}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

	public Coordinate getPlayerPos(int player) {
		return playerLocations[player][0];
	}

	public Coordinate getPrevPlayerPos(int player, int history) {
		return playerLocations[player][history];
	}

	public void setPlayerPos(int player, Coordinate position) {
		playerLocations[player][2] = playerLocations[player][1];
		playerLocations[player][1] = playerLocations[player][0];
		playerLocations[player][0] = position;
	}

	public Tile playFloorTile(Coordinate location, FloorTile insertedTile) {
		// Inserting the new tile from the left.
		if (location.getX() == -1) {
			removedTile = boardTiles[width - 1][location.getY()];
			for (int j = width - 2; j >= 0; j--) {
				boardTiles[j + 1][location.getY()] = boardTiles[j][location.getY()];
			}
			for (int j = 0; j <= width - 1; j++) {
				Coordinate newPlayerPos;
				if (checkTileForPlayerInt(j, location.getY()) == 0) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(0, location.getY());
					} else {
						newPlayerPos = new Coordinate(j + 1, location.getY());
					}
					setPlayerPos(0, newPlayerPos);

				} else if (checkTileForPlayerInt(j, location.getY()) == 1) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(0, location.getY());
					} else {
						newPlayerPos = new Coordinate(j + 1, location.getY());
					}
					setPlayerPos(1, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 2) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(0, location.getY());
					} else {
						newPlayerPos = new Coordinate(j + 1, location.getY());
					}
					setPlayerPos(2, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 3) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(0, location.getY());
					} else {
						newPlayerPos = new Coordinate(j + 1, location.getY());
					}
					setPlayerPos(3, newPlayerPos);
				}
			}
			boardTiles[0][location.getY()] = insertedTile;
			if (removedTile != null) {
				silkbag.insertTile(removedTile);
			}
		}
		// Inserting the new tile from the right.
		else if (location.getX() == width) {
			removedTile = boardTiles[0][location.getY()];
			for (int j = 0; j < width - 1; j++) {
				boardTiles[j][location.getY()] = boardTiles[j + 1][location.getY()];
			}
			for (int j = width - 1; j >= 0; j--) {
				Coordinate newPlayerPos;
				if (checkTileForPlayerInt(j, location.getY()) == 0) {
					if (j == 0) {
						newPlayerPos = new Coordinate(width - 1, location.getY());
					} else {
						newPlayerPos = new Coordinate(j - 1, location.getY());
					}
					setPlayerPos(0, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 1) {
					if (j == 0) {
						newPlayerPos = new Coordinate(width - 1, location.getY());
					} else {
						newPlayerPos = new Coordinate(j - 1, location.getY());
					}
					setPlayerPos(1, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 2) {
					if (j == 0) {
						newPlayerPos = new Coordinate(width - 1, location.getY());
					} else {
						newPlayerPos = new Coordinate(j - 1, location.getY());
					}
					setPlayerPos(2, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 3) {
					if (j == 0) {
						newPlayerPos = new Coordinate(width - 1, location.getY());
					} else {
						newPlayerPos = new Coordinate(j - 1, location.getY());
					}
					setPlayerPos(3, newPlayerPos);
				}
			}
			boardTiles[width - 1][location.getY()] = insertedTile;
			if (removedTile != null) {
				silkbag.insertTile(removedTile);
			}
		}
		// Inserting the new tile from the bottom.
		else if (location.getY() == -1) {
			removedTile = boardTiles[location.getX()][height - 1];
			for (int j = height - 2; j >= 0; j--) {
				boardTiles[location.getX()][j + 1] = boardTiles[location.getX()][j];
			}
			for (int j = 0; j <= height - 1; j++) {
				Coordinate newPlayerPos;
				if (checkTileForPlayerInt(j, location.getY()) == 0) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(location.getX(), 0);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j + 1);
					}
					setPlayerPos(0, newPlayerPos);

				} else if (checkTileForPlayerInt(j, location.getY()) == 1) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(location.getX(), 0);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j + 1);
					}
					setPlayerPos(1, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 2) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(location.getX(), 0);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j + 1);
					}
					setPlayerPos(2, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 3) {
					if (j == width - 1) {
						newPlayerPos = new Coordinate(location.getX(), 0);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j + 1);
					}
					setPlayerPos(3, newPlayerPos);
				}
			}
			boardTiles[location.getX()][0] = insertedTile;
			if (removedTile != null) {
				silkbag.insertTile(removedTile);
			}
		}
		// The last remaining case: Inserting the new tile from the top.
		else {
			removedTile = boardTiles[location.getX()][0];
			for (int j = 0; j < width - 1; j++) {
				boardTiles[location.getX()][j] = boardTiles[location.getX()][j + 1];
			}
			for (int j = height - 1; j >= 0; j--) {
				Coordinate newPlayerPos;
				if (checkTileForPlayerInt(j, location.getY()) == 0) {
					if (j == 0) {
						newPlayerPos = new Coordinate(location.getX(), height - 1);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j - 1);
					}
					setPlayerPos(0, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 1) {
					if (j == 0) {
						newPlayerPos = new Coordinate(location.getX(), height - 1);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j - 1);
					}
					setPlayerPos(1, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 2) {
					if (j == 0) {
						newPlayerPos = new Coordinate(location.getX(), height - 1);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j - 1);
					}
					setPlayerPos(2, newPlayerPos);
				} else if (checkTileForPlayerInt(j, location.getY()) == 3) {
					if (j == 0) {
						newPlayerPos = new Coordinate(location.getX(), height - 1);
					} else {
						newPlayerPos = new Coordinate(location.getX(), j - 1);
					}
					setPlayerPos(3, newPlayerPos);
				}
			}
			boardTiles[location.getX()][height - 1] = insertedTile;
			if (removedTile != null) {
				silkbag.insertTile(removedTile);
			}
		}
		return removedTile;
	}

    public ArrayList<Coordinate> getMoveDirections(int player) {
        Rotation[] directions = new Rotation[]{UP, DOWN, LEFT, RIGHT};
        ArrayList<Rotation> validDirections = new ArrayList<>();
        ArrayList<Coordinate> moveLocations = new ArrayList<>();
        Coordinate location = getPlayerPos(player);
        FloorTile playersTile = boardTiles[location.getX()][location.getY()];

        //Loops through all four directions to see if the tile the player is on connects to the direction.
        for (Rotation direction : directions) {
            if (validMove(playersTile, direction)) {
                validDirections.add(direction);
            }
        }

		//Loops through all connected valid directions, gets the tile in that valid direction to see if it
		//connects with the tile the player is on by flipping the rotation, if yes add it as a coordinate to move locations.
		for (Rotation direction : validDirections) {
			FloorTile tileInDirection;
			Rotation flipDirection = flipDirection(direction);
			Coordinate locationToCheck = shift(location, direction);
			// Check if someone is standing on that tile
			if(checkTileForPlayer(locationToCheck)) {
				continue;
			}
			// check to see if new direction is off the board.
			if ((locationToCheck.getX() < 0 || locationToCheck.getX() >= width) ||
					(locationToCheck.getY() < 0 || locationToCheck.getY() >= height)) {
				continue;
			}
			tileInDirection = boardTiles[locationToCheck.getX()][locationToCheck.getY()];
			// Check if on fire.
			if (tileInDirection.onFire()) {
				continue;
			}
			// If tile passes all tests then it is a valid place to move
			if (validMove(tileInDirection, flipDirection)) {
				moveLocations.add(locationToCheck);
			}
		}
		return moveLocations;
	}

    //Method checks to see if its possible for the player to move in that direction.
    private boolean validMove(FloorTile playerTile, Rotation direction) {
        Rotation rotation = playerTile.getRotation();
        boolean result = false;
        switch (playerTile.getType()) {
            case CORNER:
                result = (rotation == UP && (direction == RIGHT || direction == DOWN)) ||
                        (rotation == RIGHT && (direction == DOWN || direction == LEFT)) ||
                        (rotation == DOWN && (direction == LEFT || direction == UP)) ||
                        (rotation == LEFT && (direction == UP || direction == RIGHT));
                break;
            case T_SHAPE:
                result = rotation != direction;
                break;
            case STRAIGHT:
                result = rotation == direction || rotation == flipDirection(direction);
                break;
            case GOAL:
                result = true;
                break;
        }
        return result;
    }

    private Rotation flipDirection(Rotation rotation) {
        switch (rotation) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
        }
        return UP;
    }

	private Coordinate shift(Coordinate coordinate, Rotation direction) {
		int shiftX = 0;
		int shiftY = 0;
		switch (direction) {
			case UP:
				shiftY = -1;
				break;
			case RIGHT:
				shiftX = 1;
				break;
			case DOWN:
				shiftY = 1;
				break;
			case LEFT:
				shiftX = -1;
				break;
		}
		return new Coordinate(coordinate.getX() + shiftX, coordinate.getY() + shiftY);
	}


    public void playActionTile(Coordinate location, ActionTile tile, int player) {
        if (tile.getType() == TileType.FROZEN) {
            setFreezeCoords(location);
        } else if (tile.getType() == TileType.FIRE) {
            setFireCoords(location);
        } else if (tile.getType() == TileType.BACKTRACK) {
            backtrack(player);
        }
    }


    //places a fixed floor tile in the coordinates specified.
    public void placeFixedTile(FloorTile tile, int x, int y) {
        boardTiles[x][y] = tile;
        fixedTiles[x][y] = tile;
    }

    //Checks the board for goal tiles, sets their Coordinates.
    public ArrayList<Coordinate> checkGoalTiles() {
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (boardTiles[i][j] != null && boardTiles[i][j].getType() == TileType.GOAL) {
                    goalCoors.add(new Coordinate(i, j));
                }
            }
        }
        return goalCoors;
    }

    //checks to see if a player is on goal by going through all the players' locations to see
    //if they match any of the goal coordinates.
    public boolean isPlayerOnGoal() {
        int players = getNumOfPlayers();
        goalCoors = checkGoalTiles();
        for (int i = 0; i < goalCoors.size(); i++) {
            for (int j = 0; j < players; j++) {
                if (getPlayerPos(j).getX() == goalCoors.get(i).getX() && getPlayerPos(j).getY() == goalCoors.get(i).getY()) {
                    return true;
                }
            }
        }
        return false;
    }

	private boolean checkTileForPlayer(Coordinate toCheck) {
		for (Coordinate[] playerLocation : playerLocations) {
			if (playerLocation[0] != null && playerLocation[0].equals(toCheck)) {
				return true;
			}
		}
		return false;
	}

    private int checkTileForPlayerInt(int x, int y) {
        //This is 4, as the 4 players are listed as 0,1,2, and 3, if it returns 4, then there is no player on this tile.
        int playerNumber = 4;
        for (int i = 0; i < playerLocations.length; i++) {
            Coordinate playerPos = playerLocations[i][playerLocations[i].length - 1];
            if (playerPos != null && playerPos.getX() == x && playerPos.getY() == y) {
                playerNumber = i;
                return playerNumber;
            }
        }
        return playerNumber;
    }

    public Tile getPlayerTile(int player) {
        Coordinate location = getPlayerPos(player);
        return boardTiles[location.getX()][location.getY()];
    }

    private void setFireCoords(Coordinate location) {
        int players = getNumOfPlayers();
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == location.getX() && j == location.getY()) {
                    //Assuming 0,0 is bottom left. Sets a 3x3 radius of the tiles on fire.
                    boardTiles[i][j].setFireTic(players); //mid
                    if (i != width) {
                        boardTiles[i + 1][j].setFireTic(players); //right
                    }
                    if (i != 0) {
                        boardTiles[i - 1][j].setFireTic(players); //left
                    }
                    if (j != height) {
                        boardTiles[i][j + 1].setFireTic(players); //up
                    }
                    if (i != width && j != height) {
                        boardTiles[i + 1][j + 1].setFireTic(players); //upper right
                    }
                    if (i != 0 && j != height) {
                        boardTiles[i - 1][j + 1].setFireTic(players); //upper left
                    }
                    if (j != 0) {
                        boardTiles[i][j - 1].setFireTic(players); //down
                    }
                    if (i != width && j != 0) {
                        boardTiles[i + 1][j - 1].setFireTic(players); //down right
                    }
                    if (i != 0 && j != 0) {
                        boardTiles[i - 1][j - 1].setFireTic(players); //down left
                    }
                }
            }
        }
    }

    private void setFreezeCoords(Coordinate location) {
        int players = getNumOfPlayers();
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == location.getX() && j == location.getY()) {
                    //Assuming  0,0 is bottom left. Freezes a 3x3 radius of tiles.
                    boardTiles[i][j].setFrozenTic(players); //mid
                    if (i != width) {
                        boardTiles[i + 1][j].setFrozenTic(players); //right
                    }
                    if (i != 0) {
                        boardTiles[i - 1][j].setFrozenTic(players); //left
                    }
                    if (j != height) {
                        boardTiles[i][j + 1].setFrozenTic(players); //up
                    }
                    if (i != width && j != height) {
                        boardTiles[i + 1][j + 1].setFrozenTic(players); //upper right
                    }
                    if (i != 0 && j != height) {
                        boardTiles[i - 1][j + 1].setFrozenTic(players); //upper left
                    }
                    if (j != 0) {
                        boardTiles[i][j - 1].setFrozenTic(players); //down
                    }
                    if (i != width && j != 0) {
                        boardTiles[i + 1][j - 1].setFrozenTic(players); //down right
                    }
                    if (i != 0 && j != 0) {
                        boardTiles[i - 1][j - 1].setFrozenTic(players); //down left
                    }
                }
            }
        }
    }

    public Coordinate[] getSlideLocations() {
        ArrayList<Coordinate> locations = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            locations.add(new Coordinate(x, -1));
            locations.add(new Coordinate(x, height));
        }
        for (int y = 0; y < width; y++) {
            locations.add(new Coordinate(-1, y));
            locations.add(new Coordinate(width, y));
        }
        return locations.toArray(slideLocations);
    }

	public FloorTile tileAt(Coordinate coordinate) {
		return boardTiles[coordinate.getX()][coordinate.getY()];
	}

	public void backtrack(int player) {
		//gets the players current position.
		Coordinate posOneTurnAgo = getPrevPlayerPos(player, 1);
		FloorTile tileOneTurn = tileAt(posOneTurnAgo);//gets the tile, one and two turns ago that the player was on.
		Coordinate posTwoTurnsAgo = getPrevPlayerPos(player, 2);
		FloorTile tileTwoTurn = tileAt(getPrevPlayerPos(player, 2));

		//checks to see if the tile two turns ago is on fire, if not, sets that as the players position
		if (!tileOneTurn.onFire() && !checkTileForPlayer(posOneTurnAgo)) {
			if (!tileTwoTurn.onFire() && !checkTileForPlayer(posTwoTurnsAgo)) {
				// They can move two steps back
				setPlayerPos(player, posTwoTurnsAgo);
			} else {
				// They can only move one step back
				setPlayerPos(player, posOneTurnAgo);
			}
		}
		// they can't move backward


	}

    public boolean containsNull() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (boardTiles[x][y] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getNumOfPlayers() {
        int numOfPlayers = 0;
        for (int i = 0; i < playerLocations.length; i++) {
            if (playerLocations[i][0] != null) {
                numOfPlayers++;
            }
        }
        return numOfPlayers;
    }
}
