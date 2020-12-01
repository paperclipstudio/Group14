package BackEnd;

import org.junit.jupiter.api.Test;

import static BackEnd.TileType.FIRE;
import static org.junit.jupiter.api.Assertions.*;

class ActionTileTest {
	@Test
	public void test() {
		ActionTile tile = new ActionTile(FIRE);
		assertEquals(tile.getType(), FIRE);
	}
}