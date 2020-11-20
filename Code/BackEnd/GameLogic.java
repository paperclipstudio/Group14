package BackEnd;
import javafx.util.Pair;

import java.nio.charset.CoderResult;
import java.util.Random;

import static BackEnd.Phase.*;

/***
 * Controls the flow of game, lets the UI know what choices the player has
 * and the state of the game. Also lets the UI tell it the User choices, basicly
 * acts and an API for the front end.
 * @author Christian Sanger
 */
public class GameLogic {

	// Games parts
	Gameboard gameboard;
	Player[] players;
	int numberOfPlayers;
	int currentPlayerNo;
	Player currentPlayer;
	Phase phase;

	// Special flags
	// True if player gets two moves.
	boolean doubleMove = false;

	/**
	 * Creates a new game from the given board file
	 * @param boardFile Paths to board file
	 */
	public void newGame(String boardFile) {
		// Set up
		currentPlayerNo = 0;
		phase = DRAW;
		//Pair<Gameboard, Player[]> gameItems = FileReader.gameSetup(boardFile);
		//TODO undo when FileReader Works
		gameboard = new Gameboard(9,9);//gameItems.getKey();
		players = new Player[4]; //gameItems.getValue();
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i, i);
		}
		currentPlayer = players[currentPlayerNo];
	}

	/**
	 * Returns the current phase of the game
 	 * @return current Phase
	 * @see Phase
	 */
	public Phase getGamePhase() {
		return this.phase;
	}

	/**
	 * Lets the current player draw a card
	 */
	public void draw() {
		currentPlayer.drawTile();
		if (currentPlayer.isHolding() instanceof FloorTile) {
			// If floorTile you may play it
			phase = FLOOR;
		} else {
			// Else skip to action phase
			phase = ACTION;
		}
	}

	/**
	 * Returns the last drawn card of the current player.
	 * @return the tile the player is holding.
	 */
	public Tile drawnCard() {
		return players[currentPlayerNo].isHolding();
	}

	/**
	 * gets the current slide locations that the player
	 * can push a tile in. All slide locations will be outside
	 * of board.
	 * ie. to push in from the left to the right on the top row
	 * location (-1,0) would be used.
	 * @return list of every allowed slide location
	 */
	public Coordinate[] getSlideLocations() {
		return gameboard.getSlideLocations();
	}

	/**
	 * Used to tell the game where the user would like to slide
	 * a tile in.
	 * @param tile tile to be played
	 * @param location where the tile should be played.
	 */
	public void floor(FloorTile tile, Coordinate location) {
		assert(tile.getType().equals(currentPlayer.isHolding().getType()));
		players[currentPlayerNo].playFloorTile(location, tile.getRotation());
		phase = ACTION;
	}

	/**
	 * Tells you which players have already been backtracked
	 * @return array where each [0] is the first player.
	 */
	public boolean[] getPlayersThatCanBeBacktracked() {
		boolean[] result = new boolean[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			result[i] = players[i].hasBeenBacktracked();
		}
		return result;
	}

	/**
	 * array where each player is location, as a Coordinate.
	 * @return the current locations of the players
	 */
	public Coordinate[] getPlayerLocations() {
		Coordinate[] result = new Coordinate[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			result[i] = gameboard.getPlayerPos(i);
		}

		//TODO Change when gameboard working correctly
		result = new Coordinate[] {
				new Coordinate(1,1),
				new Coordinate(7,7),
				new Coordinate(1 , 7),
				new Coordinate( 7, 1)
		};
		return result;
	}

	/**
	 * Plays an Action tile at location.
	 * @param tile which tile to play
	 * @param coordinate where it would like to be played
	 */
	public void action(ActionTile tile, Coordinate coordinate) {
		players[currentPlayerNo].playActionTile(coordinate, tile);
		doubleMove = true;
		phase = MOVE;
	}

	/**
	 * moves the current player to another location.
	 * @param location where the player wishs to move.
	 */
	public void move(Coordinate location) {
		gameboard.setPlayerPos(currentPlayerNo, location);
		if (doubleMove) {
			doubleMove = false;
		} else {
			phase = DRAW;
			currentPlayerNo = (currentPlayerNo + 1) % numberOfPlayers;
			currentPlayer = players[currentPlayerNo];
		}

	}

	// Added by George to start to print game to screen
	Random r = new Random(123456);
	int width = 9;
	int height = 9;
	FloorTile[][] tiles = new FloorTile[width][height];

	/**
	 * Creates an empty game logic class, must run startNew or load before you
	 * can play.
	 */
	public GameLogic() {
		// Filling fake board with values.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
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
				FloorTile newTile = new FloorTile(TileType.values()[r.nextInt(3)], rotation);
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

	/**
	 * Returns the width of the board
	 * @return the width of the board
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height of the board.
	 */
	public int getHeight() {
		return height;
	}
}
