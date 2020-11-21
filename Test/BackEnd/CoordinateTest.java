package BackEnd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
	@Test
	public void equals() {
		Coordinate coor1 = new Coordinate(2,3);
		Coordinate coor2 = new Coordinate(2,3);
		assertTrue(coor1.equals(coor2));
		assertTrue(coor2.equals(coor1));

	}

}