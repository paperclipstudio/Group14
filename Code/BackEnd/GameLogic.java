package BackEnd;
import java.util.Random;
import java.util.StringTokenizer;

import static BackEnd.Phase.DRAW;
import static BackEnd.Phase.FLOOR;

/***
 * Controls the flow of game, lets the UI know what choices the player has
 * and the state of the game. Also lets the UI tell it the User choices
 * @author Christian Sanger
 */
public class GameLogic {

	Object[] gameItems;
	Gameboard gb;
	Player[] players;
	int currentPlayer;
	Phase phase;

	void newGame(String boardFile) {
		currentPlayer = 0;
		phase = DRAW;
		gameItems = FileReader.gameSetup(boardFile);
		gb = (Gameboard) gameItems[0];
		players = (Player[]) gameItems[1];

	}

	Phase getGamePhase() {
		return this.phase;
	}

	public void draw() {
		players[currentPlayer].drawTile();
		phase = FLOOR;
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
				FloorTile newTile = new FloorTile(tileTypes[r.nextInt(3)], rotation);
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
