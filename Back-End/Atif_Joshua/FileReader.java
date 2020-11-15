import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    private static int width;
    private static int height;
    private static int numOfFixedTiles;
    private static int counter;
    private static int counter2;
    private static final int NUM_OF_TILE_TYPES = 7;
    private static final int NUM_OF_PLAYERS = 4;

    public static void (String filename) {
        Scanner in = verifyFile(filename);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] lineArray = line.split(" ");
            counter++;
            if (lineArray[0].equals("Corner")) {
                BoardCornerTile boardCorner = constructBoardCornerTile(Integer.parseInt(lineArray[1]),
                        Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
            } else if (lineArray[0].equals("Straight")) {
                BoardStraightTile boardStraight = constructBoardStraightTile(Integer.parseInt(lineArray[1]),
                        Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
            } else if (lineArray[0].equals("T-Shape")) {
                BoardTShapeTile boardTShape = constructBoardTShapeTile(Integer.parseInt(lineArray[1]),
                        Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
            } else if (lineArray[0].equals("Goal")) {
                BoardGoalTile goal = constructGoalTile(Integer.parseInt(lineArray[1]),
                        Integer.parseInt(lineArray[2]), Integer.parseInt(lineArray[3]));
            }
            switch (counter) {
                case counter = 1:
                    width = Integer.parseInt(lineArray[0]);
                    height = Integer.parseInt(lineArray[1]);
                    break;
                case counter = 2:
                    numOfFixedTiles = Integer.parseInt(lineArray[0]);
                    break;
                case counter = (numOfFixedTiles + counter2):
                        if (lineArray[1].equals("Corner")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                CornerTile cornerTile = constructCornerTile();
                            }
                        }
                        else if (lineArray[1].equals("Straight")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                StraightTile straightTile = constructStraightTile();
                            }
                        }
                        else if (lineArray[1].equals("T-Shape")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                TShapeTile tShapeTile = constructTShapeTile();
                            }
                        }
                        else if (lineArray[1].equals("Fire")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                TShapeTile tShapeTile = constructTShapeTile();
                            }
                        }
                        else if (lineArray[1].equals("Ice")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                IceTile iceTile = constructIceTile();
                            }
                        }
                        else if (lineArray[1].equals("Backtrack")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                BacktrackTile backtrackTile = constructBacktrackTile();
                            }
                        }
                        else if (lineArray[1].equals("DoubleMove")) {
                            for (int i = 0; i < lineArray[0]; i++) {
                                DoubleMove doubleMoveTile = constructDoubleMoveTile();
                            }
                        }
                        else if(lineArray[2].equals("Player1")) {
                            Player player1 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                        }
                        else if(lineArray[2].equals("Player2")) {
                            Player player2 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                        }
                        else if(lineArray[2].equals("Player3")) {
                            Player player3 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                        }
                        else if(lineArray[2].equals("Player4")) {
                            Player player4 = constructPlayer(Integer.parseInt([0]), Integer.parseInt([1]));
                        }
                        if (counter2 <= NUM_OF_TILE_TYPES + NUM_OF_PLAYERS) {
                                 counter2++;
                            }
                        break;
                    }
            }
        }
    }

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

    private Gameboard constructGameboard(){

    }

    private CornerTile constructCornerTile() {

    }

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

    private FireTile constructFileTile(){

    }

    private IceTile constructIceTile(){

    }

    private BacktrackTile constructBacktrackTile(){

    }

    private DoubleMoveTile constructDoubleMoveTile(){

    }

    private Player constructPlayer(int x, int y){

    }

}
