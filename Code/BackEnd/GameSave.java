package BackEnd;

import FrontEnd.GameSetupController;
import jdk.internal.dynalink.beans.StaticClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 * @author David Langmaid
 */
public class GameSave {
    //private static File GAME_SAVE_FILE = new File("SaveData\\GameSave\\" +GameSetupController.getSaveName() + ".txt");
    private static File GAME_SAVE_FILE = new File("SaveData\\GameSave\\TEST.txt");

    public static void savePlayerMove(Coordinate location) throws IOException {
        Files.write(GAME_SAVE_FILE.toPath(),
                (" " + location.getX() + " " + location.getY()).getBytes(), StandardOpenOption.APPEND);
    }
    public static void savePlayFloorTile(Coordinate slideLocations, FloorTile tile, Rotation rotation) throws IOException {
        Files.write(GAME_SAVE_FILE.toPath(),
                ("\n" + slideLocations.getX() + " " + slideLocations.getY() + " "
                        + tile.getType() + " " + rotation).getBytes(), StandardOpenOption.APPEND);
    }
    public static void savePlayActionTile(Coordinate location, ActionTile tile) throws IOException {
        Files.write(GAME_SAVE_FILE.toPath(),
                (" " + location.getX() + " " + location.getY() + " " + tile.getType()).getBytes(), StandardOpenOption.APPEND);
    }
}
