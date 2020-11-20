package BackEnd;
import javafx.fxml.Initializable;
import javafx.util.Pair;
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
    /**
     The numOfFixedTiles attribute is based on the second line of the given level file format, which is used to
     accurately note how many fixed tiles will be placed on the gameboard.
     The lineCounter and playerCounter are two counters used in the iteration of each line of the file.
     The number of tile types and max number of players are both fixed, and are set to be final.
     */
    private static int numOfFixedTiles;
    private static int lineCounter = 0;
    private static int playerCounter = 0;
    private static final int NUM_OF_TILE_TYPES = 7;
    private static final int NUM_OF_FLOOR_TYPES = 3; // Not including goal tile
    private static final int MAX_NUM_OF_PLAYERS = 4;
    private static Gameboard gameboard;

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
        Gameboard gameboard;
        int width;
        int height;
        Player[] players = new Player[MAX_NUM_OF_PLAYERS];
        SilkBag silkBag = new SilkBag();
        int numberOfFixedTiles;
        Scanner currentLine;

        //// boardConfig
        currentLine = new Scanner (in.nextLine());
        width = currentLine.nextInt();
        height = currentLine.nextInt();
        gameboard = new Gameboard(width, height);

        //// Fixed tiles
        currentLine = new Scanner(in.nextLine());
        numberOfFixedTiles = currentLine.nextInt();

        for (int i= 0; i < numberOfFixedTiles; i++) {
           currentLine = new Scanner(in.nextLine());
           TileType tileType = TileType.valueOf(currentLine.next());
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
        // for each tile type
        for (int tileType=0; tileType < NUM_OF_TILE_TYPES; tileType++) {
            int numberOfThisTile = tileTypeCount[tileType];
            // for each tile that need to be added to silkbag
            for (int i = 0; i < numberOfThisTile; i++) {
                Tile newTile = Tile.createTile(TileType.values()[tileType]);
                silkBag.insertTile(newTile);
            }
        }

        //// Creating players
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
        try {
            File input = new File(filename);
            Scanner in = new Scanner(input);
            return in;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
            //return null is never reached.
            return null;
        }
    }
}