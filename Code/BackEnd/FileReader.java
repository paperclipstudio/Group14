package BackEnd;

/**
 * This class is a file reader class which will take in a given level file format text file, verify it
 * and create the gameboard, along with the fixed tiles in their correct locations, the players in their correct
 * locations, and the amount and type of each floor/action tile that will populate the silk bag.
 * @author Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    private static final int MAX_NUM_OF_PLAYERS = 4;
/**
 * This method takes in the given level format file, verifies it using the verifyFile method,
 * before checking each line of the file, creating the gameboard with the correct width and height,
 * with the fixed tiles and players in the correct locations, and populating the silk bag with the correct
 * amount and type of each floor or action tile.
 *
 * @param filename The name of the level file format text file.
 */
    public static void gameSetup (String filename) {
        Scanner in = verifyFile(filename);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] lineArray = line.split(" ");
            lineCounter++;
            switch (lineArray[0]) {
                case "Corner":
                    BoardCornerTile boardCorner = constructBoardCornerTile(Integer.parseInt(lineArray[1]),
                            Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
                    break;
                case "Straight":
                    BoardStraightTile boardStraight = constructBoardStraightTile(Integer.parseInt(lineArray[1]),
                            Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
                    break;
                case "T-Shape":
                    BoardTShapeTile boardTShape = constructBoardTShapeTile(Integer.parseInt(lineArray[1]),
                            Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
                    break;
                case "Goal":
                    BoardGoalTile goal = constructGoalTile(Integer.parseInt(lineArray[1]),
                            Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
                    break;
            }
            switch (lineCounter) {
                case 1:
                    int width = Integer.parseInt(lineArray[0]);
                    int height = Integer.parseInt(lineArray[1]);
                    Gameboard gameboard = constructGameboard();
                    break;
                case 2:
                    numOfFixedTiles = Integer.parseInt(lineArray[0]);
                    break;
                case (numOfFixedTiles + playerCounter):
                    if (lineArray[1].equals("Corner")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            CornerTile cornerTile = constructCornerTile();
                        }
                    } else if (lineArray[1].equals("Straight")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            StraightTile straightTile = constructStraightTile();
                        }
                    } else if (lineArray[1].equals("T-Shape")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            TShapeTile tShapeTile = constructTShapeTile();
                        }
                    } else if (lineArray[1].equals("Fire")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            TShapeTile tShapeTile = constructTShapeTile();
                        }
                    } else if (lineArray[1].equals("Ice")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            IceTile iceTile = constructIceTile();
                        }
                    } else if (lineArray[1].equals("Backtrack")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            BacktrackTile backtrackTile = constructBacktrackTile();
                        }
                    } else if (lineArray[1].equals("DoubleMove")) {
                        for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                            DoubleMove doubleMoveTile = constructDoubleMoveTile();
                        }
                    } else if (lineArray[2].equals("Player1")) {
                        Player player1 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                    } else if (lineArray[2].equals("Player2")) {
                        Player player2 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                    } else if (lineArray[2].equals("Player3")) {
                        Player player3 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                    } else if (lineArray[2].equals("Player4")) {
                        Player player4 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                    }
                    if (playerCounter <= NUM_OF_TILE_TYPES + MAX_NUM_OF_PLAYERS) {
                        playerCounter++;
                    }
                    break;
            }
        }
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

    private Gameboard constructGameboard() {

    }
// This creates the tile for the bag.
    private CornerTile constructCornerTile() {

    }
// This creates the tile for the gameboard, with its relevant location and rotation
    private BoardCornerTile constructBoardCornerTile(int x, int y, int rotation) {

    }

    private StraightTile constructStraightTile() {

    }

    private BoardStraightTile constructBoardStraightTile(int x, int y, int rotation) {

    }

    private TShapeTile constructTShapeTile() {

    }

    private BoardTShapeTile constructBoardTShapeTile(int x, int y, int rotation) {

    }

    private Goal constructGoalTile(int x, int y, int rotation) {

    }

    private FireTile constructFileTile() {

    }

    private IceTile constructIceTile() {

    }

    private BacktrackTile constructBacktrackTile() {

    }

    private DoubleMoveTile constructDoubleMoveTile() {

    }

    private Player constructPlayer(int x, int y) {

    }
}
