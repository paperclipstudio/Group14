package BackEnd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
	@Test
	public void equals() {
		Coordinate coor1 = new Coordinate(2,3);
		Coordinate coor2 = new Coordinate(2,3);
		assertEquals(coor1, coor2);

	}

}