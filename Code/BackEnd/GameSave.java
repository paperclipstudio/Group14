package BackEnd;

import FrontEnd.GameSetupController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static BackEnd.TileType.BACKTRACK;

/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 * @author David Langmaid
 */
public class GameSave {
    //private static File GAME_SAVE_FILE = new File("SaveData\\GameSave\\" +GameSetupController.getSaveName() + ".txt");
    private static File GAME_SAVE_FILE = new File("SaveData\\GameSave\\TEST.txt");

    public static void draw () throws IOException {
        FileWriter writer =  new FileWriter(GAME_SAVE_FILE);
            writer.write("\ndraw");
    }
    public static void playFloorTile(Coordinate slideLocations, FloorTile tile) throws IOException {
        FileWriter writer =  new FileWriter(GAME_SAVE_FILE);
            writer.write("\nfloor "+ slideLocations.getX() + " " + slideLocations.getY() + " " + tile.getType() + " " + tile.getRotation() + " ");
    }
    public static void playActionTile(Coordinate location, ActionTile tile) throws IOException {
        FileWriter writer =  new FileWriter(GAME_SAVE_FILE);
            writer.write("\naction " + location.getX() + " " + location.getY() + " " + tile.getType() + " ");

    }
    public static void playBacktrack (int playerNum) throws IOException {
        FileWriter writer =  new FileWriter(GAME_SAVE_FILE);
        writer.write("\naction BACKTRACK " + playerNum);
    }
    public static void playerMove(Coordinate location) throws IOException {
        FileWriter writer =  new FileWriter(GAME_SAVE_FILE);
            writer.write("\nmove " + location.getX() + " " + location.getY());
    }
}
