package BackEnd;

import FrontEnd.GameSetupController;

/**
 * This class records each players decisions.
 * We can then read this file and put it into gameLogic to load the game.
 * @author David Langmaid
 */
public class GameSave {
    private static String SAVE_NAME = GameSetupController.getSaveName();

    public void savePlayerMove(Coordinate location) {

    }
    public void savePlayFloorTile(Coordinate slideLocations, Rotation rotation) {

    }
    public void savePlayActionTile(Coordinate location, ActionTile tile) {

    }
}
