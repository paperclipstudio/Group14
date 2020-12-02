package FrontEnd;

import BackEnd.Coordinate;
import BackEnd.FloorTile;
import BackEnd.Tile;
import BackEnd.TileType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static BackEnd.TileType.*;

/**
 * Used to cache assets and control reading image files.
 * improves loading times as images are loaded only when they
 * are first needed.
 * @author Christian Sanger
 */
public class Assets {
	private static final String EXT = ".png";
	private static final HashMap<String, Image> cache = new HashMap<>();
	private static final int FLOOR_HEIGHT = 0;
	private static final int PLAYER_HEIGHT = 20;
	private static final int EFFECTS_HEIGHT = 40;


	/**
	 * Gets a Image with the matching name.
	 * @param name name of image needed.
	 * @return loaded image.
	 */
	private static Image get(String name) {
		Image image = cache.get(name.toLowerCase());
		if (image == null) {
			image = new Image(name + EXT);
			cache.put(name.toLowerCase(), image);
		}
		return image;
	}

	/**
	 * Creates an ImageView of this tile.
	 * @param tile to create ImageView of
	 * @param x the X coordinate of this tile
	 * @param y the Y coordinate of this tile
	 * @return view of that tile.
	 */
	public static Pane getFloorTileImage(FloorTile tile, int x, int y) {
		Pane tileView = new Pane();
		ImageView tileImage = new ImageView(get(tile.getType().toString()));
		tileImage.setFitWidth(GameScreenController.tileWidth);
		tileImage.setFitHeight(GameScreenController.tileWidth);
		tileView.getChildren().add(tileImage);
		tileView.setTranslateX(x * GameScreenController.tileWidth);
		tileView.setTranslateY(y * GameScreenController.tileWidth);
		if (tile.onFire()) {
			ImageView fireImage = new ImageView(get("tilefire"));
			fireImage.setFitHeight(GameScreenController.tileWidth);
			fireImage.setFitWidth(GameScreenController.tileWidth);
			tileView.getChildren().add(fireImage);
		}
		// TODO when we find out how things are fixed?
		if (false) {
			ImageView lockImage = new ImageView(get("fixed"));
			lockImage.setFitWidth(GameScreenController.tileWidth);
			lockImage.setFitHeight(GameScreenController.tileWidth);
			tileView.getChildren().add(lockImage);
		};

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
		if (tile.getType() == TileType.GOAL) {
			tileView.setRotate(0);
		}
		tileView.setId("tile " + x + " " + y);
		return tileView;
	}
	/**
	 * Creates an ImageView of this tile.
	 * @param tile to create ImageView of
	 * @param coordinate the coordinate of this tile
	 * @return view of that tile.
	 */
	public static Pane getFloorTileImage(FloorTile tile, Coordinate coordinate) {
		return getFloorTileImage(tile, coordinate.getX(), coordinate.getY());
	}

	/**
	 * Creates an ImageView of this tile.
	 * @param tile to create ImageView of
	 * @return view of that tile.
	 */
	public static Pane getFloorTileImage(FloorTile tile) {
		return getFloorTileImage(tile, 0,0);
	}
	/**
	 * @return Image view of arrow.
	 */
	public static ImageView makeArrow() {
		ImageView arrow = new ImageView(get("slide_arrow"));
		arrow.setId("slide_arrow");
		arrow.setFitHeight(GameScreenController.tileWidth);
		arrow.setFitWidth(GameScreenController.tileWidth);
		arrow.setOnMouseEntered(e -> arrow.setEffect(new Bloom(0.1)));
		arrow.setOnMouseExited(e  -> arrow.setEffect(new Bloom(999)));
		return arrow;
	}

	/**
	 * Creates a Card which the user is holding
	 * @param tile the tile that should be on the card
	 * @return Card.fxml Object
	 */
	public static Node createCard(Tile tile) throws IOException {
		final Node newCard;
		if (tile == null) {
			throw new NullPointerException("Cannot create null tile");
		}

		HashMap<TileType, String> labels = new HashMap<>();
		labels.put(FIRE, "Police Lookout");
		labels.put(FROZEN, "Police Block");
		labels.put(DOUBLE_MOVE, "NITRO");
		labels.put(BACKTRACK, "Misdirection");
		labels.put(CORNER, "");
		labels.put(GOAL, "SafeHouse");
		labels.put(STRAIGHT, "Bride Mayor");
		labels.put(T_SHAPE, "You know a guy");
		newCard = FXMLLoader.load(Objects.requireNonNull(GameScreenController.class.getClassLoader().getResource("FrontEnd\\FXML\\Card.fxml")));
		ImageView newCardImage = ((ImageView)newCard.lookup("#image"));
		newCardImage.setImage(Assets.get(tile.getType().toString()));
		ImageView backing = ((ImageView)newCard.lookup("#backing"));
		Label text = ((Label)newCard.lookup("#text"));
		text.setText(labels.get(tile.getType()));
		backing.setImage(Assets.get("CardBack"));
		newCard.setOnMouseEntered(
				e -> newCard.setEffect(new DropShadow(20, 0, 20, Color.BLACK)));
		newCard.setOnMouseExited(e -> newCard.setEffect(null));
		return newCard;
	}

	/**
	 * Get a imageView of that player
	 * @param playerNumber which player is needed
	 * @return
	 */
	public static ImageView getPlayer(int playerNumber) {
		Image playerModel = get("player" + (playerNumber+1));
		ImageView player = new ImageView(playerModel);
		player.setFitHeight(GameScreenController.tileWidth);
		player.setFitWidth(GameScreenController.tileWidth);
		player.setId("player " + playerNumber);
		return player;
	}

	public static ImageView getLocationArrow() {
		Image arrow = get("location_arrow");
		ImageView locationArrow = new ImageView(arrow);
		locationArrow.setFitWidth(GameScreenController.tileWidth);
		locationArrow.setFitHeight(GameScreenController.tileWidth);
		locationArrow.setId("locationarrow");
		return locationArrow;
	}

	public static Node getFireEffect() {
		Image fire = get("fireEffect");
		ImageView fireEffect = new ImageView(fire);
		fireEffect.setFitWidth(GameScreenController.tileWidth * 3);
		fireEffect.setFitHeight(GameScreenController.tileWidth * 3);
		return fireEffect;
	}

	public static Node getFrozenEffect() {
		Image frozen = get("frozenEffect");
		ImageView frozenEffect = new ImageView(frozen);
		frozenEffect.setFitWidth(GameScreenController.tileWidth * 3);
		frozenEffect.setFitHeight(GameScreenController.tileWidth * 3);
		return frozenEffect;
	}

	public static Node getProfile(int i) {
		ImageView profile = new ImageView(get(Main.getProfiles()[i].getIcon()));
		profile.setFitWidth(100);
		profile.setFitHeight(100);
		return (Node) profile;
	}
}
