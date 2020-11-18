package BackEnd;
import javafx.util.Pair;

import java.util.Random;

import static BackEnd.Phase.*;

/***
 * Controls the flow of game, lets the UI know what choices the player has
 * and the state of the game. Also lets the UI tell it the User choices
 * @author Christian Sanger
 */
public class GameLogic {

	Gameboard gb;
	Player[] players;
	int numberOfPlayers;
	int currentPlayerNo;
	Player currentPlayer;
	Phase phase;

	// Special flags

	// True if player gets two moves.
	boolean doubleMove = false;

	void newGame(String boardFile) {
		currentPlayerNo = 0;
		phase = DRAW;
		Pair<Gameboard, Player[]> gameItems = FileReader.gameSetup(boardFile);
		currentPlayer = players[currentPlayerNo];
		gb = gameItems.getKey();
		players = gameItems.getValue();
	}

	Phase getGamePhase() {
		return this.phase;
	}

	public void draw() {
		currentPlayer.drawTile();
		if (currentPlayer.isHolding() instanceof FloorTile) {
			phase = FLOOR;
		} else {
			phase = ACTION;
		}
	}

	public Tile drawnCard() {
		return players[currentPlayerNo].isHolding();
	}

	public void floor(FloorTile tile, Coordinate location) {
		players[currentPlayerNo].playFloorTile(location, tile.getRotation());
		phase = ACTION;
	}
/*
	public void action(ActionTile tile, Coordinate coordinate) {
		players[currentPlayerNo].playActionTile(coordinate, tile);
		doubleMove = true;
		phase = MOVE;
	}
*/
	public void move(Coordinate location) {
		gb.setPlayerPos(currentPlayerNo, location);
		if (doubleMove) {
			doubleMove = false;
		} else {
			phase = DRAW;
			currentPlayerNo = (currentPlayerNo + 1) % numberOfPlayers;
			currentPlayer = players[currentPlayerNo];
		}

	}






	// Added by George to start to print game to screen
	Random r = new Random();
	int width = 9;
	int height = 9;
	FloorTile[][] tiles = new FloorTile[width][height];
	public GameLogic() {
		// Filling fake board with values.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String[] tileTypes = {"Straight", "Corner", "Tee", "Goal"};
				Rotation rotation = Rotation.UP;
				switch (r.nextInt(3)) {
					case 0:
						rotation = Rotation.UP;
						break;
					case 1:
						rotation = Rotation.LEFT;
						break;
					case 2:
						rotation = Rotation.DOWN;
						break;
					case 3:
						rotation = Rotation.RIGHT;
						break;
				}
				FloorTile newTile = new FloorTile(tileTypes[r.nextInt(4)], rotation);
				tiles[x][y] = newTile;
			}
		}
	}

	/***
	 * Returns what floor tile is at a given location on the board.
	 * @param location which tile you want.
	 * @return tile at location.
	 */
	public FloorTile getTileAt(Coordinate location) {
		return tiles[location.getX()][location.getY()];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
