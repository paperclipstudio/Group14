package BackEnd;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardTest {
	Leaderboard lb;
	@BeforeEach
	void setUp() {
		File testFile = new File("SaveData\\Leaderboards\\unitTest.lb");
		testFile.delete();
		try {
			testFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			lb = new Leaderboard("unitTest");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail(e);
		}
	}

	@AfterAll
	static void cleanDown() {
		File testFile = new File("SaveData\\Leaderboards\\unitTest.lb");
		testFile.delete();
	}

	@Test
	void update() throws IOException {
		lb.update("george", 30, 5);
		String result = "george 30 5\n";
		assertEquals(result, lb.toString());

		lb.update("george", 40, 10);
		result = "george 40 10\n";
		assertEquals(result, lb.toString());

		lb.update("Max", 30, 5);
		result = "george 40 10\nMax 30 5\n";
		assertEquals(result, lb.toString());

		lb.update("Winner", 50, 5);
		result = "Winner 50 5\nMax 30 5\ngeorge 40 10\n";
		assertEquals(result, lb.toString());

		lb.update("Winner", 60, 5);
		result = "Winner 60 5\nMax 30 5\ngeorge 40 10\n";
		assertEquals(result, lb.toString());

		lb.remove("Max");
		result = "Winner 60 5\ngeorge 40 10\n";
		assertEquals(result, lb.toString());

		try {
			lb.saveFile();
		} catch (IOException e) {
			fail(e);
		}

		lb.remove("george");
		try {
			lb.loadFile();
		} catch (FileNotFoundException e) {
			fail(e);
		}

		result = "Winner 60 5\ngeorge 40 10\n";
		assertEquals(result, lb.toString());
	}

	@Test
	void getGameBoard() {
		assertEquals("unitTest", lb.getGameBoard());
	}
}