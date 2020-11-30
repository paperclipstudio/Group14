package BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	Player player;
	SilkBag silkBag;
	Gameboard board;
	@BeforeEach
	void setUp() {
		silkBag = new SilkBag(234234234);
		board = new Gameboard(3,3, silkBag);
		player = new Player(2, silkBag, board);
		silkBag.insertTile(Tile.createTile(FROZEN));
	}

	@Test
	void drawTile() {
		assertNull(player.isHolding());
		player.drawTile();
		assertEquals(FROZEN, player.isHolding().getType());
	}

	@Test
	void playFloorTile() {
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
	void playActionTile() {
		player.drawTile();
		//Silk bag empty
		silkBag.insertTile(Tile.createTile(FROZEN));
		//silk bag just has Frozen tile.
		// now play valid tile.
		player.playActionTile(new Coordinate(0,0), new ActionTile(FROZEN), 0);
		// it should now be frozen.
		assertTrue(board.tileAt(new Coordinate(0,0)).isFrozen());
	}

	@Test
	void playInvalidActionTile() {
		player.drawTile();
		//Silk bag empty
		silkBag.insertTile(Tile.createTile(FROZEN));
		//silk bag just has Frozen tile.
		player.playActionTile(new Coordinate(0,0), new ActionTile(FIRE), 0);
		// Player plays invalid card.
		assertFalse(board.tileAt(new Coordinate(0,0)).onFire());
		// check that its not frozen.
		assertFalse(board.tileAt(new Coordinate(0,0)).isFrozen());
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