package BackEnd;
import javafx.fxml.Initializable;
import javafx.util.Pair;

import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class is a file reader class which will take in a given level file format text file, verify it
 * and create the gameboard, along with the fixed tiles in their correct locations, the players in their correct
 * locations, and the amount and type of each floor/action tile that will populate the silk bag.
 *
 * @author Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 */

public class FileReader {
    private static final int NUM_OF_TILE_TYPES = TileType.values().length;
    private static final int MAX_NUM_OF_PLAYERS = 4;

    /**
     * This method takes in the given level format file, verifies it using the verifyFile method,
     * before checking each line of the file, creating the gameboard with the correct width and height,
     * with the fixed tiles and players in the correct locations, and populating the silk bag with the correct
     * amount and type of each floor or action tile.
     *
     * @param filename The name of the level file format text file.
     * @return pair where first element is the gameboard and second is the players.
     */
    public static Pair<Gameboard, Player[]> gameSetup(String filename) {
        Scanner in = verifyFile(filename);
        Scanner currentLine;

        //// board config
        currentLine = new Scanner (in.nextLine());
        int width = currentLine.nextInt();
        int height = currentLine.nextInt();
        Gameboard gameboard = new Gameboard(width, height);

        //// Fixed tiles
        currentLine = new Scanner(in.nextLine());
        int numberOfFixedTiles = currentLine.nextInt();
        for (int i= 0; i < numberOfFixedTiles; i++) {
           currentLine = new Scanner(in.nextLine());
           TileType tileType = TileType.valueOf(currentLine.next().toUpperCase());
           int x = currentLine.nextInt();
           int y = currentLine.nextInt();
           int rotationInt = currentLine.nextInt();
           Rotation rotation = Rotation.values()[rotationInt];
           FloorTile tile = new FloorTile(tileType, rotation);
           gameboard.placeFixedTile(tile, x, y);
        }

        //// Filling SilkBag
        int[] tileTypeCount = new int[NUM_OF_TILE_TYPES];
        // Reading how many of each tile
        for(int tileType = 0; tileType < NUM_OF_TILE_TYPES; tileType++) {
            currentLine = new Scanner(in.nextLine());
            tileTypeCount[tileType] = currentLine.nextInt();
        }
        // putting them in the bag
        SilkBag silkBag = new SilkBag();
        // for each tile type
        for (int tileType=0; tileType < NUM_OF_TILE_TYPES; tileType++) {
            int numberOfThisTile = tileTypeCount[tileType];
            // for each tile that need to be added to silkbag
            for (int i = 0; i < numberOfThisTile; i++) {
                Tile newTile = Tile.createTile(TileType.values()[tileType]);
                silkBag.insertTile(newTile);
            }
        }
        //// Fill with random tiles
        Random r = new Random(42069);
        Coordinate[] slideLocations = gameboard.getSlideLocations();
        while (gameboard.containsNull()) {
            FloorTile tile = silkBag.getFloorTile();
            tile.setRotation(Rotation.values()[r.nextInt(4)]);
            Coordinate toSlide = slideLocations[r.nextInt(slideLocations.length-1)];
        }
        //// Creating players
        Player[] players = new Player[MAX_NUM_OF_PLAYERS];
        for (int i = 0; i < MAX_NUM_OF_PLAYERS; i++) {
            currentLine = new Scanner(in.nextLine());
            int x = currentLine.nextInt();
            int y = currentLine.nextInt();
            gameboard.setPlayerPos(i, new Coordinate(x, y));
            players[i] = new Player(i, silkBag, gameboard);
        }
        return new Pair<>(gameboard, players);
    }

    /**
     * This method takes in the given level format file, and checks to see that the file exists.
     * @param filename The name of the level file format text file.
     * @return in The scanner that iterates through the file.
     */
    public static Scanner verifyFile(String filename) {
        Scanner in = null;
        try {
            File input = new File(filename);
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return in;
    }
}