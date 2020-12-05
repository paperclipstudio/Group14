package BackEnd;

import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import static BackEnd.TileType.FIRE;
import static BackEnd.TileType.FROZEN;

/**
 * This class loads a game by reading the save file and running the inputs of the save file
 * through the gameLogic to re-create the game.
 *
 * @author David Langmaid & George Sanger
 * @version 1.0
 */
public class GameLoad {

	/**
	 * This is the only method in the class, it handles the file reading and loading each player choice
	 * into the game logic to get it to its loaded state.
	 *
	 * @param initData information about the gameState
	 * @return pair where key is gameLogic for new game and value is all profiles
	 * @throws IOException if the save file cannot be found.
	 */
	public static Pair<GameLogic, Profile[]> loader(HashMap<String, String> initData) throws Exception {
		File loadFile = new File("SaveData\\GameSave\\" + initData.get("LoadFile"));
		Scanner in = new Scanner(loadFile);
		if (!in.hasNextLine()) {
			throw new IOException("Invalid file format, no game board file");
		}
		String gameBoard = in.nextLine();
		initData.put("Board", gameBoard);
		int silkBagSeed = Integer.parseInt(in.nextLine());
		int playerCount = Integer.parseInt(in.nextLine());

		initData.put("PlayerCount", playerCount + "");

		Profile[] profiles = new Profile[playerCount];
		for (int i = 0; i < playerCount; i++) {
			Scanner profileLine = new Scanner(in.nextLine());
			String name = profileLine.next();
			profiles[i] = Profile.readProfile(name);
			initData.put("Profile" + i, name);
		}
		GameLogic gameLogic = new GameLogic(silkBagSeed);
		GameSave gameSave = new GameSave(initData);
		gameLogic.newGame(gameBoard, gameSave);
		gameLogic.setNumberOfPlayers(playerCount);
		while (in.hasNextLine()) {
			int x;
			int y;
			Scanner lineReader = new Scanner(in.nextLine());
			String choiceType = lineReader.next();
			switch (choiceType) {
				case "draw":
					gameLogic.draw();
					break;
				case "floor":
					FloorTile tile = new FloorTile(TileType.valueOf(lineReader.next()));
					tile.setRotation(Rotation.valueOf(lineReader.next()));
					x = lineReader.nextInt();
					y = lineReader.nextInt();
					gameLogic.floor(tile, new Coordinate(x, y));
					break;
				case "action":
					String type = lineReader.next();
					if (type.equals("null")) {
						gameLogic.action(null, null, -1);
					} else {
						TileType tileType = TileType.valueOf(type);
						ActionTile actionTile = new ActionTile(tileType);
						if (tileType == FIRE || tileType == FROZEN) {
							x = lineReader.nextInt();
							y = lineReader.nextInt();
							gameLogic.action(actionTile, new Coordinate(x, y), -1);
						} else {
							int playerNum = lineReader.nextInt();
							gameLogic.action(actionTile, null, playerNum);
						}
					}
					break;
				case "move":
					x = lineReader.nextInt();
					y = lineReader.nextInt();
					gameLogic.move(new Coordinate(x, y));
			}
		}
		return new Pair<>(gameLogic, profiles);
	}
}