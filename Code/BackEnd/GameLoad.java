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

	public static GameLogic loader(String fileName) throws IOException {
		Scanner in = verifyFile(fileName);
		String gameboard = in.nextLine();
		int silkBagSeed = Integer.parseInt(in.nextLine());
		GameLogic gameLogic = new GameLogic(silkBagSeed);
		gameLogic.newGame(gameboard);

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
						gameLogic.action(actionTile, new Coordinate(x, y));
					} else {
						gameLogic.action(actionTile, null);
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

	private static Scanner verifyFile(String fileName) {
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
