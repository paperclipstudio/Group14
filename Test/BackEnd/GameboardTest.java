package BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static BackEnd.Rotation.UP;
import static BackEnd.TileType.*;
import static org.junit.jupiter.api.Assertions.*;

class GameboardTest {
	SilkBag sb = new SilkBag(234234234);
	Gameboard gb = new Gameboard(3,3, sb);
	Gameboard gb2 = new Gameboard(3,3, sb);
	Gameboard gb3 = new Gameboard(3,3, sb);
	Gameboard gb4 = new Gameboard(3,3, sb);

	@BeforeEach
	void setUp() {

		gb.setPlayerPos(0, new Coordinate(1,1));
		gb.setPlayerPos(1, new Coordinate( 2,1));
		gb.setPlayerPos(2, new Coordinate( 2,2));
		gb.setPlayerPos(3, new Coordinate( 2,0));
		//Inserting from the left.
		gb.playFloorTile(new Coordinate(-1, 0), new FloorTile(CORNER));
		gb.playFloorTile(new Coordinate(-1, 0), new FloorTile(T_SHAPE));
		gb.playFloorTile(new Coordinate(-1, 0), new FloorTile(CORNER));
		gb.playFloorTile(new Coordinate(-1, 1), new FloorTile(STRAIGHT));
		gb.playFloorTile(new Coordinate(-1, 1), new FloorTile(GOAL));
		gb.playFloorTile(new Coordinate(-1, 1), new FloorTile(STRAIGHT));
		gb.playFloorTile(new Coordinate(-1, 2), new FloorTile(CORNER));
		gb.playFloorTile(new Coordinate(-1, 2), new FloorTile(T_SHAPE));
		gb.playFloorTile(new Coordinate(-1, 2), new FloorTile(CORNER));
		//Inserting from the right.
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 0), new FloorTile(CORNER));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 0), new FloorTile(T_SHAPE));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 0), new FloorTile(CORNER));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 1), new FloorTile(STRAIGHT));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 1), new FloorTile(GOAL));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 1), new FloorTile(STRAIGHT));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 2), new FloorTile(CORNER));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 2), new FloorTile(T_SHAPE));
		gb2.playFloorTile(new Coordinate(gb2.getWidth(), 2), new FloorTile(CORNER));
		//Inserting from the bottom.
		gb3.playFloorTile(new Coordinate(0, -1), new FloorTile(CORNER));
		gb3.playFloorTile(new Coordinate(0, -1), new FloorTile(T_SHAPE));
		gb3.playFloorTile(new Coordinate(0, -1), new FloorTile(CORNER));
		gb3.playFloorTile(new Coordinate(1, -1), new FloorTile(STRAIGHT));
		gb3.playFloorTile(new Coordinate(1, -1), new FloorTile(GOAL));
		gb3.playFloorTile(new Coordinate(1, -1), new FloorTile(STRAIGHT));
		gb3.playFloorTile(new Coordinate(2, -1), new FloorTile(CORNER));
		gb3.playFloorTile(new Coordinate(2, -1), new FloorTile(T_SHAPE));
		gb3.playFloorTile(new Coordinate(2, -1), new FloorTile(CORNER));
		//Inserting from the top.
		gb4.playFloorTile(new Coordinate(0, gb4.getHeight()), new FloorTile(CORNER));
		gb4.playFloorTile(new Coordinate(0, gb4.getHeight()), new FloorTile(T_SHAPE));
		gb4.playFloorTile(new Coordinate(0, gb4.getHeight()), new FloorTile(CORNER));
		gb4.playFloorTile(new Coordinate(1, gb4.getHeight()), new FloorTile(STRAIGHT));
		gb4.playFloorTile(new Coordinate(1, gb4.getHeight()), new FloorTile(GOAL));
		gb4.playFloorTile(new Coordinate(1, gb4.getHeight()), new FloorTile(STRAIGHT));
		gb4.playFloorTile(new Coordinate(2, gb4.getHeight()), new FloorTile(CORNER));
		gb4.playFloorTile(new Coordinate(2, gb4.getHeight()), new FloorTile(T_SHAPE));
		gb4.playFloorTile(new Coordinate(2, gb4.getHeight()), new FloorTile(CORNER));
	}

	@Test
	void setPlayerPos() {
		Coordinate coor1 = new Coordinate(2,2);
		Coordinate coor2 = new Coordinate(0,1);
		gb.setPlayerPos(2, coor1);
	}

	@Test
	void playBackTrack() {
		Coordinate startPos = new Coordinate(1,1);
		Coordinate move1 = new Coordinate(2,1);
		Coordinate move2 = new Coordinate(2, 2);
		Coordinate fireLocation = new Coordinate(0, 0);
		assertEquals(startPos, gb.getPlayerPos(0));
		gb.backtrack(0);
		assertEquals(startPos, gb.getPlayerPos(0));
		gb.setPlayerPos(0, move1);
		assertEquals(move1, gb.getPlayerPos(0));
		gb.backtrack(0);
		assertEquals(startPos, gb.getPlayerPos(0));
		gb.setPlayerPos(0, move1);
		gb.setPlayerPos(0, move2);
		assertEquals(move2, gb.getPlayerPos(0));
		gb.backtrack(0);
		assertEquals(startPos, gb.getPlayerPos(0));
		gb.setPlayerPos(0, move1);
		gb.setPlayerPos(0, move2);
		// Checking that it doesn't move a player onto a tile thats on fire
		gb.playActionTile(fireLocation, new ActionTile(FIRE), 0);
		gb.backtrack(0);
		assertEquals(move1, gb.getPlayerPos(0));

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
	void placeFixedTile() {
		gb.placeFixedTile(new FloorTile(TileType.CORNER, Rotation.LEFT), 2, 3);
		assertEquals(CORNER, gb.TileAt(new Coordinate(2, 3)).getType());
		gb.placeFixedTile(new FloorTile(STRAIGHT, Rotation.RIGHT), 3, 3);
		assertEquals(STRAIGHT, gb.TileAt(new Coordinate(3, 3)).getType());
		gb.placeFixedTile(new FloorTile(T_SHAPE, Rotation.LEFT), 2, 3);
		assertEquals(T_SHAPE, gb.TileAt(new Coordinate(2, 3)).getType());
	}

	@Test
	void playFloorTile() {
		Coordinate[] locations = gb.getSlideLocations();

        // Testing inserting from the left.
		assertEquals(CORNER,  gb.TileAt(new Coordinate(0,0)).getType());
		assertEquals(T_SHAPE, gb.TileAt(new Coordinate(1,0)).getType());
		assertEquals(CORNER,  gb.TileAt(new Coordinate(2,0)).getType());
		assertEquals(STRAIGHT, gb.TileAt(new Coordinate(0,1)).getType());
		assertEquals(GOAL,    gb.TileAt(new Coordinate(1,1)).getType());
		assertEquals(STRAIGHT,gb.TileAt(new Coordinate(2,1)).getType());
		assertEquals(CORNER,  gb.TileAt(new Coordinate(0,2)).getType());
		assertEquals(T_SHAPE, gb.TileAt(new Coordinate(1,2)).getType());
		assertEquals(CORNER,  gb.TileAt(new Coordinate(2,2)).getType());
		// Testing inserting from the left.
		assertEquals(CORNER,  gb2.TileAt(new Coordinate(0,0)).getType());
		assertEquals(T_SHAPE, gb2.TileAt(new Coordinate(1,0)).getType());
		assertEquals(CORNER,  gb2.TileAt(new Coordinate(2,0)).getType());
		assertEquals(STRAIGHT, gb2.TileAt(new Coordinate(0,1)).getType());
		assertEquals(GOAL,    gb2.TileAt(new Coordinate(1,1)).getType());
		assertEquals(STRAIGHT, gb2.TileAt(new Coordinate(2,1)).getType());
		assertEquals(CORNER,  gb2.TileAt(new Coordinate(0,2)).getType());
		assertEquals(T_SHAPE, gb2.TileAt(new Coordinate(1,2)).getType());
		assertEquals(CORNER,  gb2.TileAt(new Coordinate(2,2)).getType());
		// Testing inserting from the left.
		assertEquals(CORNER,  gb3.TileAt(new Coordinate(0,0)).getType());
		assertEquals(STRAIGHT, gb3.TileAt(new Coordinate(1,0)).getType());
		assertEquals(CORNER,  gb3.TileAt(new Coordinate(2,0)).getType());
		assertEquals(T_SHAPE, gb3.TileAt(new Coordinate(0,1)).getType());
		assertEquals(GOAL,    gb3.TileAt(new Coordinate(1,1)).getType());
		assertEquals(T_SHAPE, gb3.TileAt(new Coordinate(2,1)).getType());
		assertEquals(CORNER,  gb3.TileAt(new Coordinate(0,2)).getType());
		assertEquals(STRAIGHT, gb3.TileAt(new Coordinate(1,2)).getType());
		assertEquals(CORNER,  gb3.TileAt(new Coordinate(2,2)).getType());
		// Testing inserting from the right.
		assertEquals(CORNER,  gb4.TileAt(new Coordinate(0,0)).getType());
		assertEquals(STRAIGHT, gb4.TileAt(new Coordinate(1,0)).getType());
		assertEquals(CORNER,  gb4.TileAt(new Coordinate(2,0)).getType());
		assertEquals(T_SHAPE, gb4.TileAt(new Coordinate(0,1)).getType());
		assertEquals(GOAL,    gb4.TileAt(new Coordinate(1,1)).getType());
		assertEquals(T_SHAPE,gb4.TileAt(new Coordinate(2,1)).getType());
		assertEquals(CORNER,  gb4.TileAt(new Coordinate(0,2)).getType());
		assertEquals(STRAIGHT, gb4.TileAt(new Coordinate(1,2)).getType());
		assertEquals(CORNER,  gb4.TileAt(new Coordinate(2,2)).getType());
	}

	@Test
	void getWidth() {
		assertEquals(3, gb.getWidth());
		Gameboard gb2 = new Gameboard(0,3, sb);
		assertEquals(0, gb2.getWidth());
		gb2 = new Gameboard(10,3, sb);
		assertEquals(10, gb2.getWidth());

	}

	@Test
	void getHeight() {
		assertEquals(3, gb.getHeight());
		Gameboard gb2 = new Gameboard(3,0, sb);
		assertEquals(0, gb2.getHeight());
		gb2 = new Gameboard(3,10, sb);
		assertEquals(10, gb2.getHeight());
	}

	@Test
	void getMoveDirections() {
		gb.getMoveDirections(0);
		gb.getMoveDirections(1);
		gb.getMoveDirections(2);
		gb.getMoveDirections(3);

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