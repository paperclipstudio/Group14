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
		silkBag = new SilkBag();
		board = new Gameboard(3,3);
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
		player.playFloorTile(new Coordinate(-1,0), Rotation.UP);
		FloorTile tile = board.TileAt(new Coordinate(0,0));
		assertNotNull(tile);
		assertEquals(CORNER, tile.getType());
		assertEquals(Rotation.UP, tile.getRotation());

	}

	@Test
	void playActionTile() {
		player.drawTile();
		silkBag.insertTile(Tile.createTile(FROZEN));
		player.playActionTile(new Coordinate(0,0), new ActionTile(FIRE));
		assertFalse(board.TileAt(new Coordinate(0,0)).onFire());
		assertFalse(board.TileAt(new Coordinate(0,0)).Frozen());
		player.playActionTile(new Coordinate(0,0), new ActionTile(FROZEN));
		assertFalse(board.TileAt(new Coordinate(0,0)).Frozen());
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