package BackEnd;

/**
 *
 */

import FrontEnd.Main;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static BackEnd.TileType.FIRE;
import static BackEnd.TileType.FROZEN;

public class GameLoad {

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static GameLogic loader(String fileName) throws Exception {
		File loadFile = new File("SaveData\\GameSave\\" + fileName);
		Scanner in = new Scanner(loadFile);
		if (!in.hasNextLine()) {
			throw new IOException("Invalid file format, no game board file");
		}
		String gameBoard = in.nextLine();
		int silkBagSeed = Integer.parseInt(in.nextLine());
		int playerCount = Integer.parseInt(in.nextLine());

		Profile[] profiles = new Profile[playerCount];
		for (int i = 0; i < playerCount; i++) {
			Scanner profileLine = new Scanner(in.nextLine());
			String name = profileLine.next();
			profiles[i] = Profile.readProfile(name);
		}
		Main.setProfiles(profiles);
		GameLogic gameLogic = new GameLogic(silkBagSeed);
		Main.setNumberOfPlayers(playerCount);
		gameLogic.newGame(gameBoard);
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
		return gameLogic;
	}
}
