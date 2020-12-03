package BackEnd;

import FrontEnd.GameSetupController;
import FrontEnd.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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


    public GameSave(int seed) {
        gameSaveString = "\n" + seed;
    }

    public void draw() {
        gameSaveString += "\ndraw";
        isGameSaved = false;
    }
    public void playFloorTile(Coordinate slideLocations, FloorTile tile){
        gameSaveString = gameSaveString + "\nfloor " + tile.getType() + " " + tile.getRotation() + " " + slideLocations.getX() + " " + slideLocations.getY();
        isGameSaved = false;
    }
    public void playActionTile(Coordinate location, ActionTile tile, int playerNo) {
        String type = tile == null ? "null " : tile.getType().toString();
        gameSaveString += "\naction " + type + " " + playerNo;
        if (location != null) {
            gameSaveString += " " + location.toString();
        }
        isGameSaved = false;
    }

    public void playerMove(Coordinate location) {
        gameSaveString = gameSaveString + "\nmove " + location.getX() + " " + location.getY();
        isGameSaved = false;
    }

    public boolean isGameSaved() {
        return isGameSaved;
    }

    public void saveToFile() throws IOException {
        //gameSaveFile = new File("SaveData\\GameSave\\" + System.currentTimeMillis() + ".sav");
        FileWriter writer = new FileWriter(gameSaveFile, true);
        gameSaveFile.delete();
        gameSaveFile.createNewFile();
        writer.write(gameSaveString);
        writer.flush();
        writer.close();
        isGameSaved = true;
    }
    public void emptyGameSaveString() {
        gameSaveString = "";
        isGameSaved = true;
    }
}
