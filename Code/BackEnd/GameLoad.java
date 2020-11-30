package BackEnd;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertPathParameters;
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
		System.out.println("Loading:" + fileName);
		Scanner in = new Scanner(loadFile);
		if (!in.hasNextLine()) {
			throw new IOException("Invalid file format, no game board file");
		}
		String gameBoard = in.nextLine();
		System.out.println("Loading board " + gameBoard);
		int silkBagSeed = Integer.parseInt(in.nextLine());
		GameLogic gameLogic = new GameLogic(silkBagSeed);
		gameLogic.newGame(gameBoard);

		while (in.hasNextLine()) {
			int x;
			int y;
			Scanner lineReader = new Scanner(in.nextLine());
			String choiceType = lineReader.next();
			switch (choiceType) {
				case "draw":
					System.out.println("Draw action");
					gameLogic.draw();
					break;
				case "floor":
					System.out.println("Floor Action");
					FloorTile tile = new FloorTile(TileType.valueOf(lineReader.next()));
					tile.setRotation(Rotation.valueOf(lineReader.next()));
					x = lineReader.nextInt();
					y = lineReader.nextInt();
					gameLogic.floor(tile, new Coordinate(x, y));
					break;
				case "action":
					TileType tileType = TileType.valueOf(lineReader.next());
					ActionTile actionTile = new ActionTile(tileType);
					if (tileType == FIRE || tileType == FROZEN) {
						x = lineReader.nextInt();
						y = lineReader.nextInt();
						gameLogic.action(actionTile, new Coordinate(x, y), -1);
					} else {
						gameLogic.action(actionTile, null, -1);
					}
					System.out.println("Action Card");
					break;
				case "move":
					System.out.println("Move Action");
					x = lineReader.nextInt();
					y = lineReader.nextInt();
					gameLogic.move(new Coordinate(x, y));

			}
		}
		return gameLogic;
	}
}
