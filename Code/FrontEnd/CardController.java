import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/***
 * Card is a display of one card. Used in
 * @author Christian
 */
public class CardController implements Initializable {
	// The image on this card.
	@FXML
	private ImageView image;
	// Reference to this whole card.
	@FXML
	private Pane fullCard;
	@FXML
	private ImageView button;
	@FXML
	private ImageView backing;



	/***
	 * Gets all resources for Card
	 * @param url Url for resources
	 * @param rb pack of resources
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		image.setOnMouseClicked((e) -> onRotateRight());
		button.setOnMouseClicked((e) -> onRotateRight());
	}


	/***
	 * adds a drop shadow to card
	 * called when mouse is over card.
	 */
	public void onHover() {
		Bloom bloom = new Bloom();
		bloom.setThreshold(0.1);
		DropShadow shadow = new DropShadow();
		shadow.setWidth(21);
		shadow.setHeight(21);
		shadow.setOffsetX(10);
		shadow.setOffsetY(10);
		shadow.setSpread(0.14);
		fullCard.setEffect(shadow);
	}

	/***
	 * removes Drop shadow from card.
	 */
	public void offHover() {
		fullCard.setEffect(null);
	}

	/***
	 * Rotates the image on the card 90 degrees clockwise
	 */
	public void onRotateRight() {
		double newAngle = image.getRotate() + 90;
		if (newAngle >= 360) {
			newAngle -= 360;
		}
		image.setRotate(newAngle);
	}
}
