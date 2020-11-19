package BackEnd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTileTest {
	@Test
	public void test() {
		ActionTile tile = new ActionTile("Fire");
		assertEquals(tile.getType(), "Fire");
	}

}