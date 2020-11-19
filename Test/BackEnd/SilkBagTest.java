package BackEnd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SilkBagTest {

	@Test
	void getTile() {
		SilkBag silkBag = new SilkBag();
		silkBag.insertTile(Tile.createTile("Fire"));
		assertEquals(silkBag.getTile().getType(), "Fire");
	}

	@Test
	void getFloorTile() {
		SilkBag silkBag = new SilkBag();
		silkBag.insertTile(Tile.createTile("Fire"));
		silkBag.insertTile(Tile.createTile("Frozen"));
		silkBag.insertTile(Tile.createTile("Backtrack"));
	}

	@Test
	void insertTile() {
	}
}