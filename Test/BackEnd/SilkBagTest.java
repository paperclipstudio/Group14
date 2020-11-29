package BackEnd;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import BackEnd.TileType;

import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class SilkBagTest {

	@Test
	void getTile() {
		SilkBag silkBag = new SilkBag(91284098);
		silkBag.insertTile(Tile.createTile(FIRE));
		assertEquals(silkBag.getTile().getType(), FIRE);
	}

	@RepeatedTest(10)
	public void testRandom() {
		SilkBag silkBag1 = new SilkBag(123456);
		SilkBag silkBag2 = new SilkBag(789101);
		for (int i = 0; i < values().length; i++) {
			silkBag1.insertTile(Tile.createTile(values()[i]));
			silkBag1.insertTile(Tile.createTile(values()[i]));
			silkBag2.insertTile(Tile.createTile(values()[i]));
			silkBag2.insertTile(Tile.createTile(values()[i]));
		}
		int matchCount = 0;
		for (int i = 0; i < values().length; i++) {
			if (silkBag1.getTile().getType() == silkBag2.getTile().getType()) {
				matchCount++;
			}
		}
		assertTrue(matchCount < 3);

	}

	@Test
	void getFloorTile() {
		SilkBag silkBag = new SilkBag(24423);
		silkBag.insertTile(Tile.createTile(FIRE));
		silkBag.insertTile(Tile.createTile(FROZEN));
		silkBag.insertTile(Tile.createTile(CORNER));
		silkBag.insertTile(Tile.createTile(BACKTRACK));
		try {
			assertEquals(silkBag.getFloorTile().getType(), CORNER);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}