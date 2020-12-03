package BackEnd;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	Player player;
	SilkBag silkBag;
	Gameboard board;
	@BeforeEach
	void setUp() throws Exception {
		silkBag = new SilkBag(234234234);
		Pair<Gameboard, Player[]> pair = FileReader.gameSetup("ExampleInput.txt");
		board = pair.getKey();
		player = pair.getValue()[0];

		silkBag.insertTile(Tile.createTile(FROZEN));
	}

	@Test
	void drawTile() {
		assertNull(player.isHolding());
		player.drawTile();
		assertEquals(FROZEN, player.isHolding().getType());
	}


	@Test
	void playFloorTile() throws Exception {
		silkBag.getTile();
		silkBag.insertTile(Tile.createTile(CORNER));
		player.drawTile();
		player.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER));
		FloorTile tile = board.tileAt(new Coordinate(0,0));
		assertNotNull(tile);
		assertEquals(CORNER, tile.getType());
		assertEquals(Rotation.UP, tile.getRotation());
	}

	@Test
	void playActionTile() throws Exception {
		player.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,1), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,1), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,1), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER));
		player.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER));
		// fill board with floor tiles for testing purposes
		player.drawTile();
		// silk bag empty
		silkBag.insertTile(Tile.createTile(FROZEN));
		// silk bag just has Frozen tile.
		// now play valid tile.
		player.playActionTile(new Coordinate(0,0), new ActionTile(FROZEN), 0);
		// it should now be frozen.
		assertTrue(board.tileAt(new Coordinate(0,0)).isFrozen());
	}

	@Test
	void isHolding() {
		player.drawTile();
		assertEquals(FROZEN, player.isHolding().getType());
	}

	@Test
	void hasBeenBacktracked() {
		player.hasBeenBacktracked();
	}
}