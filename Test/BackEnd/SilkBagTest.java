package BackEnd;

import org.junit.jupiter.api.Test;
import BackEnd.TileType;

import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class SilkBagTest {

	@Test
	void getTile() {
		SilkBag silkBag = new SilkBag();
		silkBag.insertTile(Tile.createTile(FIRE));
		assertEquals(silkBag.getTile().getType(), FIRE);
	}

	@Test
	void getFloorTile() {
		SilkBag silkBag = new SilkBag();
		silkBag.insertTile(Tile.createTile(FIRE));
		silkBag.insertTile(Tile.createTile(FROZEN));
		silkBag.insertTile(Tile.createTile(BACKTRACK));
	}

	@Test
	void insertTile() {
	}
}