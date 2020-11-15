import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CardController implements Initializable {
	@FXML
	private ImageView image;
	@FXML
	private Pane fullCard;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("Card has been created");
		Image newCard = new Image("Tee.png");
		if ((new Random()).nextBoolean()) {
			newCard = new Image("Goal.png");
		}
		if (image == null) {
			System.out.println("Image is NULL");
		}
		image.setImage(newCard);
	}

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

	public void offHover() {
		fullCard.setEffect(null);
	}

	public void onRotateLeft() {

		image.setRotate(image.getRotate() - 90);
	}

	public void onRotateRight() {
		image.setRotate(image.getRotate() + 90);
	}
}
