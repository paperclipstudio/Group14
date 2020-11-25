package BackEnd;


import javafx.util.Pair;
import static BackEnd.Phase.*;
import static BackEnd.TileType.DOUBLE_MOVE;

/***
 * Controls the flow of game, lets the UI know what choices the player has
 * and the state of the game. Also lets the UI tell it the User choices, basically
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
	// Current phase of the game, Draw|Floor|Action|Move
	Phase phase;

	// Special flags
	// True if player gets two moves.
	boolean doubleMove;

	/**
	 * Creates a new game from the given board file
	 * @param boardFile Paths to board file
	 */
	public void newGame(String boardFile) {
		doubleMove = false;
		currentPlayerNo = 0;
		phase = DRAW;
		Pair<Gameboard, Player[]> gameItems = FileReader.gameSetup(boardFile);
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
	public void action(ActionTile tile, Coordinate coordinate) {
		if (tile.getType() == DOUBLE_MOVE) {
			currentPlayer.removeFromInventory(new ActionTile(DOUBLE_MOVE));
			doubleMove = true;
		}
		players[currentPlayerNo].playActionTile(coordinate, tile);
		phase = MOVE;
	}

	/**
	 * Returns all Action tiles the current player can use
	 * @return array of all playable tiles.
	 */
	public ActionTile[] getActionCards() {
		//TODO FIX JUST FOR TESTING
		System.out.println("FAKE GET ACTION CARDS");
		ActionTile[] result = new ActionTile[4];
		result[0] = new ActionTile(DOUBLE_MOVE);
		result[1] = new ActionTile(TileType.BACKTRACK);
		result[2] = new ActionTile(TileType.FIRE);
		result[3] = new ActionTile(TileType.FROZEN);
		return result;
	}

	/**
	 * moves the current player to another location.
	 * @param location where the player wishes to move.
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

	/**
	 * Creates an empty game logic class, must run startNew or load before you
	 * can play.
	 */
	public GameLogic() {
	}

	/**
	 * Returns what floor tile is at a given location on the board.
	 * @param location which tile you want.
	 * @return tile at location.
	 */
	public FloorTile getTileAt(Coordinate location) {
		return gameboard.TileAt(location);
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
	 * Called when User wants to play a backtrack card.
	 * @param playerNumber which player to use backtrack on.
	 */
	public void backtrack(int playerNumber) {
		gameboard.backtrack(playerNumber);
		currentPlayer.removeFromInventory(new ActionTile(TileType.BACKTRACK));
		phase = MOVE;
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
		//todo waiting on gameboard
		Coordinate[] validLocation = new Coordinate[4];
		validLocation[0] = gameboard.getPlayerPos(currentPlayerNo).shift(-1,  0);
		validLocation[1] = gameboard.getPlayerPos(currentPlayerNo).shift( 1,  0);
		validLocation[2] = gameboard.getPlayerPos(currentPlayerNo).shift( 0, -1);
		validLocation[3] = gameboard.getPlayerPos(currentPlayerNo).shift( 0,  1);
		return validLocation;
	}
}
