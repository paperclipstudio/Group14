/**
 * This class is a file reader class which will take in a given level file format text file, verify it
 * and create the gameboard, along with the fixed tiles in their correct locations, the players in their correct
 * locations, and the amount and type of each floor/action tile that will populate the silk bag.
 *
 * @author Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 */


import javafx.fxml.Initializable;

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
    private static Gameboard gameboard;

    /**
     * This method takes in the given level format file, verifies it using the verifyFile method,
     * before checking each line of the file, creating the gameboard with the correct width and height,
     * with the fixed tiles and players in the correct locations, and populating the silk bag with the correct
     * amount and type of each floor or action tile.
     *
     * @param filename The name of the level file format text file.
     */
    public static void gameSetup(String filename) {
        Scanner in = verifyFile(filename);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] lineArray = line.split(" ");
            lineCounter++;
            Rotation rotation = Rotation.values()[Integer.parseInt(lineArray[3])];
            switch (lineArray[0]) {
                case "Corner":
                    FloorTile boardCornerTile = new FloorTile("Corner", rotation);
                    gameboard.placeFixedTile(boardCornerTile, Integer.parseInt(lineArray[1]), Integer.parseInt(lineArray[2]));
                    break;
                case "Straight":
                    FloorTile boardStraightTile = new FloorTile("Straight", rotation);
                    gameboard.placeFixedTile(boardStraightTile, Integer.parseInt(lineArray[1]), Integer.parseInt(lineArray[2]));
                    break;
                case "T-Shape":
                    FloorTile boardTShapeTile = new FloorTile("T-Shape", rotation);
                    gameboard.placeFixedTile(boardTShapeTile, Integer.parseInt(lineArray[1]), Integer.parseInt(lineArray[2]));
                    break;
                case "Goal":
                    FloorTile goalTile = new FloorTile("Goal", rotation);
                    gameboard.placeFixedTile(goalTile, Integer.parseInt(lineArray[1]), Integer.parseInt(lineArray[2]));
            }
            if (lineCounter == 1) {
                int width = Integer.parseInt(lineArray[0]);
                int height = Integer.parseInt(lineArray[1]);
                gameboard = new Gameboard(width, height);
            } else if (lineCounter == 2) {
                numOfFixedTiles = Integer.parseInt(lineArray[0]);
            } else if (lineCounter == (numOfFixedTiles + playerCounter)) {
                if (lineArray[1].equals("Corner")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        FloorTile cornerTile = new FloorTile("Corner");
                    }
                } else if (lineArray[1].equals("Straight")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        FloorTile straightTile = new FloorTile("Straight");
                    }
                } else if (lineArray[1].equals("T-Shape")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        FloorTile tShapeTile = new FloorTile("T-Shape");
                    }
                } else if (lineArray[1].equals("Fire")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        FloorTile cornerTile = new FloorTile("Corner");
                    }
                } else if (lineArray[1].equals("Ice")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        ActionTile iceTile = new ActionTile("Ice");
                    }
                } else if (lineArray[1].equals("Backtrack")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        ActionTile backtrackTile = new ActionTile("Backtrack");
                    }
                } else if (lineArray[1].equals("DoubleMove")) {
                    for (int i = 0; i < Integer.parseInt(lineArray[0]); i++) {
                        ActionTile doubleMoveTile = new ActionTile("DoubleMove");
                    }
                } else if (lineArray[2].equals("Player1")) {
                    Player player1 = new Player(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
                } else if (lineArray[2].equals("Player2")) {
                    Player player2 = new Player(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
                } else if (lineArray[2].equals("Player3")) {
                    Player player3 = new Player(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
                } else if (lineArray[2].equals("Player4")) {
                    Player player4 = new Player(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
                }
                if (playerCounter <= NUM_OF_TILE_TYPES + MAX_NUM_OF_PLAYERS) {
                    playerCounter++;
                }
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
}