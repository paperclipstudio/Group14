package BackEnd;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
	@Test
	public void basic() {
		Pair<Gameboard, Player[]> output = null;

		try {
			output = FileReader.gameSetup("ExampleInput.txt");
		} catch (FileNotFoundException e) {
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}


		Gameboard gameboard = output.getKey();
		Player[] players = output.getValue();
		assertEquals(8, gameboard.getWidth());
		assertEquals(10, gameboard.getHeight());

		assertEquals(new Coordinate(0,0), gameboard.getPlayerPos(0));
		assertEquals(new Coordinate(7, 0), gameboard.getPlayerPos(1));
		assertEquals(new Coordinate(0, 9), gameboard.getPlayerPos(2));
		assertEquals(new Coordinate(7, 9), gameboard.getPlayerPos(3));
	}
}