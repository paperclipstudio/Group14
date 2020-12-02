package BackEnd;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Holds information on the number of wins for one level.
 * @author Christian Sanger
 * @version 1.0
 */

public class Leaderboard {
	private static final String FOLDER = "SaveData\\Leaderboards\\";
	private static final String EXT = ".lb";
	private final File file;
	private final String gameBoard;

	PriorityQueue<Score> scores = new PriorityQueue<>((o1, o2) -> o2.getWins() - o1.getWins());

	/**
	 * Creates leaderboard for a board and attempts to load it from file.
	 * @param gameBoard name of the game board that this leaderboard is for
	 * @throws IOException if file can't be found
	 */
	public Leaderboard(String gameBoard) throws IOException {
		file = new File(FOLDER + gameBoard + EXT);
		boolean failed;
		this.gameBoard = gameBoard;
		failed = file.createNewFile();
		if (failed) {
			throw new IOException("Leaderboard failed to create file");
		}
		loadFile();
	}

	/**
	 * Updates/Adds a score to the leaderboard
	 * @param name profiles name.
	 * @param wins number of wins.
	 * @param loss number of losses.
	 */
	public void update(String name, int wins, int loss) {
		Score newScore = new Score(name, wins, loss);
		remove(name);
		scores.add(newScore);
	}

	/**
	 * Removes a profile from the leaderboard.
	 * @param name profile to remove.
	 */
	public void remove(String name) {
		scores.removeIf((score -> score.getName().equals(name)));
	}
	/**
	 * Loads a leaderboard from a file
	 * @throws FileNotFoundException if file can't be found
	 */
	public void loadFile() throws FileNotFoundException {
		Scanner in = new Scanner(file);
		scores.clear();
		while(in.hasNext()) {
			Scanner highScore = new Scanner(in.nextLine());
			String name = highScore.next();
			int wins = highScore.nextInt();
			int loss = highScore.nextInt();
			Score score = new Score(name, wins, loss);
			scores.add(score);
		}
	}

	/**
	 * Saves a leaderboard to a file, overwriting the current one.
	 * @throws IOException if file can't be found
	 */
	public void saveFile() throws IOException {
		String fileData = toString();
		FileWriter writer = new FileWriter(file);
		writer.write(fileData);
		writer.flush();
		writer.close();
	}

	/**
	 * Returns all scores as array.
	 * @return array of scores
	 */
	Score[] getAllScores() {
		return scores.toArray(new Score[0]);
	}

	@Override
	public String toString() {
		StringBuilder fileData = new StringBuilder();
		scores.toArray(new Score[0]);
		for (Score score: scores) {
			fileData.append(score.toString()).append("\n");
		}
		return fileData.toString();
	}


	public String getGameBoard() {
		return gameBoard;
	}

	public ObservableList<Score> getObservableList(){

		SimpleListProperty<Score> allscores = new SimpleListProperty<Score>();
		allscores.addAll(scores);

		return allscores;


	}





}
