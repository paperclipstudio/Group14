package BackEnd;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
	@Test
	public void basic() {
		Pair<Gameboard, Player[]> output = FileReader.gameSetup("Test\\BackEnd\\ExampleInput.txt");
		Gameboard gameboard = output.getKey();
		Player[] players = output.getValue();
		assertEquals(8, gameboard.getWidth());
		assertEquals(10, gameboard.getHeight());
	}
}