package BackEnd;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
	@Test
	public void basic() {
		Pair<Gameboard, Player[]> output = FileReader.gameSetup("ExampleFile.txt");
		Gameboard gameboard = output.getKey();
		Player[] players = output.getValue();
		assertEquals(8, gameboard.getWidth());
		assertEquals(10, gameboard.getHeight());

	}

}