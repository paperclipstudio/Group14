package BackEnd;


import javafx.util.Pair;

import java.io.IOException;

import static BackEnd.Phase.*;
import static BackEnd.TileType.DOUBLE_MOVE;

/***
 * Controls the flow of game, lets the UI know what choices the player has
 * and the state of the game. Also lets the UI tell it the User choices, basically
 * acts and an API for the front end.
 * @author Christian Sanger
 */
public class GameLogic {
	private final int seed;
	// Games parts
	Gameboard gameboard;
	Player[] players;
	int numberOfPlayers;
	int currentPlayerNo;
	Player currentPlayer;
	// Current phase of the game, Draw|Floor|Action|Move
	Phase phase;

	// Special flags
	// True if player gets two moves.
	boolean doubleMove;
	private GameSave gameSaver;

	/**
	 * Creates a new game from the given board file
	 * @param boardFile Paths to board file
	 */
	public void newGame(String boardFile) throws Exception {
		gameSaver = new GameSave(boardFile, seed);
		doubleMove = false;
		currentPlayerNo = 0;
		phase = DRAW;
		Pair<Gameboard, Player[]> gameItems = FileReader.gameSetup(boardFile, seed);
		gameboard = gameItems.getKey();
		players = gameItems.getValue();
		numberOfPlayers = players.length;
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
		gameSaver.draw();
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
	public void floor(FloorTile tile, Coordinate location) throws Exception {
		gameSaver.playFloorTile(location, tile);
		assert(tile.getType().equals(currentPlayer.isHolding().getType()));
		players[currentPlayerNo].playFloorTile(location, tile);
		phase = ACTION;
	}

	/**
	 * Tells you which players have already been backtracked
	 * @return array where each [0] is the first player.
	 */
	public boolean[] getPlayersThatCanBeBacktracked() {
		boolean[] result = new boolean[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			result[i] = !players[i].hasBeenBacktracked();
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
		return result;
	}

	/**
	 * Plays an Action tile at location.
	 * @param tile which tile to play
	 * @param coordinate where it would like to be played
	 */
	public void action(ActionTile tile, Coordinate coordinate, int playerNo) {
		// If tile is null then player didn't/can't play an action card.
		gameSaver.playActionTile(coordinate, tile, playerNo);
		if (tile != null) {
			if (tile.getType() == DOUBLE_MOVE) {
				doubleMove = true;
			}
			players[currentPlayerNo].playActionTile(coordinate, tile, playerNo);
		}
		if (gameboard.isPlayerOnGoal()) {
			phase = WIN;
		} else {
		phase = MOVE;
		}
	}

	/**
	 * Returns all Action tiles the current player can use
	 * @return array of all playable tiles.
	 */
	public ActionTile[] getActionCards() {
		return currentPlayer.getInventory().toArray(new ActionTile[0]);
	}

	/**
	 * moves the current player to another location.
	 * @param location where the player wishes to move.
	 */
	public void move(Coordinate location) {
		gameSaver.playerMove(location);
		gameboard.setPlayerPos(currentPlayerNo, location);
		if (gameboard.isPlayerOnGoal()) {
			phase = WIN;
		} else if (doubleMove) {
			doubleMove = false;
		} else {
			phase = DRAW;
			currentPlayerNo = (currentPlayerNo + 1) % numberOfPlayers;
			currentPlayer = players[currentPlayerNo];
		}
	}

	/**
	 * Creates an empty game logic class, must run startNew or load before you
	 * can play.
	 */
	public GameLogic(int seed) {
		this.seed = seed;
	}

	/**
	 * Returns what floor tile is at a given location on the board.
	 * @param location which tile you want.
	 * @return tile at location.
	 */
	public FloorTile getTileAt(Coordinate location) {
		return gameboard.tileAt(location);
	}

	/**
	 * Returns the width of the board
	 * @return the width of the board
	 */
	public int getWidth() {
		return gameboard.getWidth();
	}

	/**
	 * @return the height of the board.
	 */
	public int getHeight() {
		return gameboard.getHeight();
	}

	/**
	 * Gets how many player are currently playing
	 * @return number of players
	 */
	public int getNumberOfPlayers() {
		//TODO create working version.
		return numberOfPlayers;
	}


	/**
	 * Finds out which players turn it is.
	 * @return 0-3 to mean which is the current player
	 */
	public int getPlayersTurn() {
		return currentPlayerNo;
	}

	/**
	 * gets all move locations that it is valid for the current player to move to.
	 * @return all valid move locations
	 */
	public Coordinate[] getMoveLocations() {
		return gameboard.getMoveDirections(currentPlayerNo).toArray(new Coordinate[0]);
	}

	public void saveGame() throws IOException {
		gameSaver.saveToFile();
	}
}
