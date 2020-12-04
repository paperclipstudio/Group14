package BackEnd;

import FrontEnd.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 * @author David Langmaid
 * @version 1.0
 */
public class GameSave {

    private static File gameSaveFile = new File("SaveData\\GameSave\\" + Main.getSaveFile() + ".sav");
    private boolean isGameSaved = false;
    private String gameSaveString;

    /**
     * This is the constructor for the game save instance, it adds the seed, the number of players and each player
     * profile to gameSaveString.
     * @param seed - takes the seed for silk bag
     */
    public GameSave(int seed) {
        gameSaveString = "\n" + seed;
        gameSaveString +=  "\n" + Main.getNumberOfPlayers();
        for (Profile profile: Main.getProfiles()) {
            gameSaveString += "\n" + profile.getName();
        }
    }

    /**
     * This saves the draw sate and sets isGameSaved to false.
     */
    public void draw() {
        gameSaveString += "\ndraw";
        isGameSaved = false;
    }

    /**
     * This method saves the tile type and the slide locations for the played floor tile.
     * It also sets isGameSaved to false.
     * @param slideLocations - The coordinates of the slide location.
     * @param tile - The floor tile that was played.
     */
    public void playFloorTile(Coordinate slideLocations, FloorTile tile) {
        gameSaveString += "\nfloor " + tile.getType() + " " + tile.getRotation() + " " + slideLocations.getX() + " " + slideLocations.getY();
        isGameSaved = false;
    }

    /**
     * This method saves the tile type and the slide locations for the played action tile.
     * It also sets isGameSaved to false.
     * @param location - The coordinates of the location the action tile was played.
     * @param tile - The action tile that was played.
     * @param playerNo - The player number the card effects (if it does).
     */
    public void playActionTile(Coordinate location, ActionTile tile, int playerNo) {
        String type = tile == null ? "null " : tile.getType().toString();
        gameSaveString += "\naction " + type + " " + playerNo;
        if (location != null) {
            gameSaveString += " " + location.toString();
        }
        isGameSaved = false;
    }

    /**
     * This methods saves the location the player moved to.
     * It also sets isGameSaved to false.
     * @param location - The coordinates of the location the player moved to.
     */
    public void playerMove(Coordinate location) {
        gameSaveString += "\nmove " + location.getX() + " " + location.getY();
        isGameSaved = false;
    }

    /**
     * This method checks whether the game is saved or not.
     * @return returns true or false from the local variable isGameSaved
     */
    public boolean isGameSaved() {
        return isGameSaved;
    }

    /**
     * This saves the game by appending the gameSaveString to the end of the save file.
     * It also sets the game saved state to true.
     * @throws IOException if the gameSaveFile cannot be found.
     */
    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter(gameSaveFile, true);
        writer.write(gameSaveString);
        writer.flush();
        writer.close();
        isGameSaved = true;
    }

    /**
     * This emptys the game save string. It also sets the game saved state to true.
     */
    public void emptyGameSaveString() {
        gameSaveString = "";
        isGameSaved = true;
    }
}

