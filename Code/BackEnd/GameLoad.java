package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameLoad {

    public static void loader (String fileName) {
        Scanner in = verifyFile(fileName);
        String gameboard = in.nextLine();
        FileReader.gameSetup(gameboard);

        try {
            int silkBagSeed = in.nextInt(); //TODO put the silk bag seed in the right place
        } catch (InputMismatchException e) {
            System.out.println("ERROR : " + fileName + " is in incorrect format");
        }

        while (in.hasNextLine()) {
            Scanner lineReader = new Scanner(in.nextLine()).useDelimiter("\\s");
            switch (lineReader.next()) {
                case "draw":

                case "floor":

                case "action":

                case "move":
            }
        }
    }
    private static Scanner verifyFile (String fileName) {
        Scanner in = null;
        try {
            File loadFile = new File("SaveData\\GameSave\\" + fileName);
            in = new Scanner(loadFile);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR : " + fileName + " could not be found");
        }
        return in;
    }

}
