package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameLoad {

    public static GameLogic loader (String fileName) throws FileNotFoundException {
        Scanner in = verifyFile(fileName);
        String gameboard = in.nextLine();
        int silkBagSeed = Integer.parseInt(in.nextLine());
        GameLogic gameLogic = new GameLogic(silkBagSeed);
        gameLogic.newGame(gameboard);

        while (in.hasNextLine()) {
           Scanner lineReader = new Scanner(in.nextLine());
           String choiceType = lineReader.next();
            switch (choiceType) {
                case "draw":
                    System.out.println("Draw action");
                    gameLogic.draw();
                    break;
                case "floor":
                    System.out.println("Floor Action");
                    break;
                case "action":
                    System.out.println("Action Card");
                    break;
                case "move":
                    System.out.println("Move Action");
            }
        }
        return gameLogic;
    }
    private static Scanner verifyFile (String fileName) {
        Scanner in = null;
        try {
            File loadFile = new File("SaveData\\GameSave\\" + fileName);
            in = new Scanner(loadFile);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + fileName + " could not be found");
        }
        return in;
    }

}
