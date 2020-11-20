package FrontEnd;

import BackEnd.FloorTile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * Used to cache assets and control reading image files.
 * improves loading times as images are loaded only when they
 * are first needed.
 * @author Christian Sanger
 */
public class Assets {
	private static final String EXT = ".png";
	private static final HashMap<String, Image> cache = new HashMap<>();

	/**
	 * Gets a Image with the matching name.
	 * @param name name of image needed.
	 * @return loaded image.
	 */
	private static Image get(String name) {
		System.out.println("Finding " + name + EXT);
		Image image = cache.get(name);
		if (image == null) {
			image = new Image(name + EXT);
			cache.put(name, image);
		}
		return image;
	}

	/**
	 * Creates an ImageView of this tile.
	 * @param tile to create ImageView of
	 * @return view of that tile.
	 */
	public static ImageView getFloorTileImage(FloorTile tile) {
		ImageView tileView = new ImageView(get(tile.getType().toString().toLowerCase()));
		tileView.setFitWidth(GameScreenController.tileWidth);
		tileView.setFitHeight(GameScreenController.tileWidth);
		switch (tile.getRotation()) {
			case UP:
				tileView.setRotate(0); // Not needed but just for consistency
				break;
			case RIGHT:
				tileView.setRotate(90);
				break;
			case DOWN:
				tileView.setRotate(180);
				break;
			case LEFT:
				tileView.setRotate(270);
				break;
		}

		return tileView;
	}

	/**
	 * @return Image view of arrow.
	 */
	public static ImageView makeArrow() {
		ImageView arrow = new ImageView(get("slide_arrow"));
		arrow.setFitHeight(GameScreenController.tileWidth);
		arrow.setFitWidth(GameScreenController.tileWidth);
		return arrow;
	}
}
