package BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class GameboardTest {
	Gameboard gb = new Gameboard(3,3);

	@BeforeEach
	void setUp() {
		gb = new Gameboard(3,3);
		gb.setPlayerPos(0, new Coordinate(1,1));
		gb.setPlayerPos(1, new Coordinate( 2,1));
		gb.setPlayerPos(2, new Coordinate( 2,2));
		gb.setPlayerPos(3, new Coordinate( 2,0));
		gb.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER), Rotation.UP);
		gb.playFloorTile(new Coordinate(-1,0), new FloorTile(T_SHAPE),Rotation.LEFT);
		gb.playFloorTile(new Coordinate(-1,0), new FloorTile(CORNER), Rotation.RIGHT);
		gb.playFloorTile(new Coordinate(-1,1), new FloorTile(STRAIGHT), Rotation.DOWN);
		gb.playFloorTile(new Coordinate(-1,1), new FloorTile(GOAL), Rotation.UP);
		gb.playFloorTile(new Coordinate(-1,1), new FloorTile(STRAIGHT), Rotation.LEFT);
		gb.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER), Rotation.RIGHT);
		gb.playFloorTile(new Coordinate(-1,2), new FloorTile(T_SHAPE), Rotation.DOWN);
		gb.playFloorTile(new Coordinate(-1,2), new FloorTile(CORNER), Rotation.UP);
		gb.getMoveDirections(1);
		gb.getMoveDirections(2);
		gb.getMoveDirections(3);
	}

	@Test
	void getPlayerPos() {
		assertEquals(1, gb.getPlayerPos(0).getX());
		assertEquals(1, gb.getPlayerPos(0).getY());
		assertEquals(2, gb.getPlayerPos(1).getX());
		assertEquals(1, gb.getPlayerPos(1).getY());
		assertEquals(2, gb.getPlayerPos(2).getX());
		assertEquals(2, gb.getPlayerPos(2).getY());
		assertEquals(2, gb.getPlayerPos(3).getX());
		assertEquals(0, gb.getPlayerPos(3).getY());
	}

	@Test
	void setPlayerPos() {
		Coordinate coor1 = new Coordinate(2,2);
		Coordinate coor2 = new Coordinate(0,1);
		gb.setPlayerPos(2, coor1);
	}

	@Test
	void playFloorTile() {
	}

	@Test
	void getWidth() {
		assertEquals(3, gb.getWidth());
		Gameboard gb2 = new Gameboard(0,3);
		assertEquals(0, gb2.getWidth());
		gb2 = new Gameboard(10,3);
		assertEquals(10, gb2.getWidth());

	}

	@Test
	void getHeight() {
		assertEquals(3, gb.getHeight());
		Gameboard gb2 = new Gameboard(3,0);
		assertEquals(0, gb2.getHeight());
		gb2 = new Gameboard(3,10);
		assertEquals(10, gb2.getHeight());
	}

	@Test
	void getMoveDirections() {
	}

	@Test
	void placeFixedTile() {
	}

	@Test
	void getSlideLocations() {
	}


	@Test
	void testGetPlayerPos() {
	}

	@Test
	void testSetPlayerPos() {
	}

	@Test
	void testPlayFloorTile() {
	}

	@Test
	void testGetWidth() {
	}

	@Test
	void testGetHeight() {
	}

	@Test
	void testPlaceFixedTile() {
	}

	@Test
	void setGoalCoor() {
	}

	@Test
	void getGoalCoor() {
	}

	@Test
	void isPlayerOnGoal() {
	}

	@Test
	void testGetSlideLocations() {
	}

	@Test
	void tileAt() {
	}
}