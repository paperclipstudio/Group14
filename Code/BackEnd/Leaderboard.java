package BackEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Holds information on the number of wins for one level.
 *
 * @author Christian Sanger
 * @version 1.0
 */

public class Leaderboard {
    private static final String boardFolder = "SaveData\\Leaderboards\\";
    private static final String EXT = ".lb";
    private final File file;
    private final String gameBoard;

    PriorityQueue<Score> scores = new PriorityQueue<>((o1, o2) -> o2.getWins() - o1.getWins());

    /**
     * Creates leaderboard for a board and attempts to load it from file.
     *
     * @param gameBoard name of the game board that this leaderboard is for
     * @throws IOException if file can't be created
     */
    public Leaderboard(String gameBoardFile) throws IOException {
        String gameBoard = gameBoardFile.substring(0, gameBoardFile.length()-4);
        file = new File(boardFolder + gameBoard + EXT);
        this.gameBoard = gameBoard;
        if (!file.exists()) {
            if(!file.createNewFile()) {
                throw new IOException("Leaderboard " + gameBoard + " couldn't be read to");
            }
        }
        loadFile();
    }

    /**
     * Updates/Adds a score to the leaderboard
     *
     * @param name profiles name.
     * @param wins number of wins.
     * @param loss number of losses.
     */
    public void update(String name, int wins, int loss) throws IOException {
        Score newScore = new Score(name, wins, loss);
        remove(name);
        scores.add(newScore);
        saveFile();
    }

    /**
     * This method takes in a name and finds that players score.
     * @param name the player to get the score
     * @return The score of the player.
     */
    private Score find(String name) {
        for(Score score: getAllScores()) {
            if (score.getName().equals(name)) {
                return score;
            }
        }
        return null;
    }

    /**
     * This method increments the win of a player.
     * @param name the name of the player to add a win.
     * @throws IOException
     */
    public void addWin(String name) throws IOException {
        Score toUpdate = find(name);
        if (toUpdate == null) {
            update(name, 1 ,0);
        } else {
            update(toUpdate.getName(), toUpdate.getWins() + 1, toUpdate.getLoss());
        }
    }

    /**
     * This method increments a loss of the player.
     * @param name the player to increment the loss.
     * @throws IOException
     */
    public void addLoss(String name) throws IOException {
        Score toUpdate = find(name);
        if (toUpdate == null) {
            update(name,  0, 1);
        } else {
            update(toUpdate.getName(), toUpdate.getWins(), toUpdate.getLoss() + 1);
        }
    }


    /**
     * Removes a profile from the leaderboard.
     *
     * @param name profile to remove.
     */
    public void remove(String name) {
        scores.removeIf((score -> score.getName().equals(name)));
    }

    /**
     * Loads a leaderboard from a file
     *
     * @throws FileNotFoundException if file can't be found
     */
    public void loadFile() throws FileNotFoundException {
        Scanner in = new Scanner(file);
        scores.clear();
        while (in.hasNext()) {
            Scanner highScore = new Scanner(in.nextLine());
            if (highScore.hasNext()) {
                String name = highScore.next();
                int wins = highScore.nextInt();
                int loss = highScore.nextInt();
                Score score = new Score(name, wins, loss);
                scores.add(score);
            }
        }
    }

    /**
     * Saves a leaderboard to a file, overwriting the current one.
     *
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
     * This method returns all scores as array.
     *
     * @return array of scores
     */
    Score[] getAllScores() {
        return scores.toArray(new Score[0]);
    }

    @Override
    public String toString() {
        StringBuilder fileData = new StringBuilder();
        scores.toArray(new Score[0]);
        for (Score score : scores) {
            fileData.append(score.toString()).append("\n");
        }
        return fileData.toString();
    }


    /**
     * This method returns the gameboard.
     * @return the gameboard to be returned.
     */
    public String getGameBoard() {
        return gameBoard;
    }

    /**
     * This method gets all the scores of the players.
     * @return the scores of the players.
     */
    public ObservableList<Score> getObservableList() {
        return FXCollections.observableArrayList(scores);
    }

}
