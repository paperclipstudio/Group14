package BackEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 *
 * @author David Langmaid & George Sanger
 * @version 1.0
 */
public class GameSave {
    // Tracks if a change has happened that hasn't been saved to file
    private boolean isGameSaved = false;
    // All information needed to restore game
    private StringBuilder gameState;
    // File that game will be saved too.
    private final File gameSaveFile;

    /**
     * Constructs a GameSave that tracks all player choices for saving state of the game
     *
     * @param initData - data about the game state to save with
     */
    public GameSave(HashMap<String, String> initData) throws IOException {
        gameSaveFile = new File("SaveData\\GameSave\\" + initData.get("LoadFile"));
        gameState = new StringBuilder();
        gameState.append(initData.get("Board")).append("\n");
        gameState.append(initData.get("Seed")).append("\n");
        gameState.append(initData.get("PlayerCount")).append("\n");
        int playerCount = Integer.parseInt(initData.get("PlayerCount"));
        Profile[] profiles = new Profile[playerCount];
        for(int i = 0; i < playerCount; i++) {
            profiles[i] = Profile.readProfile(initData.get("Profile" + i));
        }
        for (Profile profile: profiles) {
            gameState.append(profile.getName()).append("\n");
        }
    }
    /**
     * Keeps note that a player has drawn a card.
     */
    public void draw() {
        gameState.append("draw \n");
        isGameSaved = false;
    }
    /**
     * Keeps not that a player has played a floor tile.
     *
     * @param slideLocations where it was slid
     * @param tile what tile was played
     */
    public void playFloorTile(Coordinate slideLocations, FloorTile tile){
        String floor = String.format("floor %s %s %s\n",
                tile.getType().toString(),
                tile.getRotation().toString(),
                slideLocations.toString());
        gameState.append(floor);
        isGameSaved = false;
    }

    /**
     * Keeps note that a player has played a action card.
     *
     * @param location where the card was played (null if NA)
     * @param tile what tile was played
     * @param playerNo what player the tile was played on (unimportant if NA)
     */
    public void playActionTile(Coordinate location, ActionTile tile, int playerNo) {
        String type = tile == null ? "null " : tile.getType().toString();
        String locationAsString = location == null ? "null" : location.toString();
        String action = String.format("action %s %d %s \n", type, playerNo, locationAsString);
        gameState.append(action);
        isGameSaved = false;
    }
    /**
     * Keeps note that player moved.
     *
     * @param location where a player moved to.
     */
    public void playerMove(Coordinate location) {
        String move = String.format("move %s\n", location.toString());
        gameState.append(move);
        isGameSaved = false;
    }
    /**
     * Checks if latest changes has been saved to file.
     *
     * @return true is every change is saved to file
     */
    public boolean isGameSaved() {
        return isGameSaved;
    }
    /**
     * Saves user choices to file.
     * @throws IOException if error with file.
     */
    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter(gameSaveFile, true);
        writer.write(gameState.toString());
        writer.flush();
        writer.close();
        isGameSaved = true;
    }
    /**
     * Clears the game save string.
     */
    public void emptyGameSaveString() {
        gameState = new StringBuilder();
        isGameSaved = true;
    }
}

