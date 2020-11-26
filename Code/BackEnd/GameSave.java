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
    private static File gameSaveFile = new File("SaveData\\GameSave\\" +GameSetupController.getSaveName() + ".txt");

    public static void draw () throws IOException {
        FileWriter writer =  new FileWriter(gameSaveFile);
            writer.write("\ndraw");
    }
    public static void playFloorTile(Coordinate slideLocations, FloorTile tile) throws IOException {
        FileWriter writer =  new FileWriter(gameSaveFile);
            writer.write("\nfloor " + tile.getType() + " " + tile.getRotation() + " " + slideLocations.getX() + " " + slideLocations.getY() + " ");
    }
    public static void playActionTile(Coordinate location, ActionTile tile) throws IOException {
        FileWriter writer =  new FileWriter(gameSaveFile);
            writer.write("\naction " + tile.getType() + " " + location.getX() + " " + location.getY() + " ");
    }
    public static void playBacktrack (int playerNum) throws IOException {
        FileWriter writer =  new FileWriter(gameSaveFile);
        writer.write("\naction BACKTRACK " + playerNum);
    }
    public static void playerMove(Coordinate location) throws IOException {
        FileWriter writer =  new FileWriter(gameSaveFile);
            writer.write("\nmove " + location.getX() + " " + location.getY());
    }
}
