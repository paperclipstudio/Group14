package BackEnd;

import FrontEnd.GameSetupController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 * @author David Langmaid
 */
public class GameSave {
    //private static File gameSaveFile = new File("SaveData\\GameSave\\" +GameSetupController.getSaveName() + ".txt");
    private File gameSaveFile = new File("SaveData\\GameSave\\TEST.txt");

    private String gameSaveString = "";

    public GameSave() {
    }

    public void draw() {
        gameSaveString = gameSaveString + "\ndraw";
    }
    public void playFloorTile(Coordinate slideLocations, FloorTile tile){
        gameSaveString = gameSaveString + "\nfloor " + tile.getType() + " " + tile.getRotation() + " " + slideLocations.getX() + " " + slideLocations.getY() + " ";
    }
    public void playActionTile(Coordinate location, ActionTile tile) {
        gameSaveString = gameSaveString + "\naction " + tile.getType() + " " + location.getX() + " " + location.getY() + " ";
    }
    public void playBacktrack (int playerNum) {
        gameSaveString = gameSaveString + "\naction BACKTRACK " + playerNum;
    }
    public void playerMove(Coordinate location) {
        gameSaveString = gameSaveString + "\nmove " + location.getX() + " " + location.getY();
    }
    public void saveToFile() throws IOException {
        FileWriter writer = new FileWriter(gameSaveFile, true);
        writer.write(gameSaveString);
        writer.flush();
        writer.close();
    }
}
