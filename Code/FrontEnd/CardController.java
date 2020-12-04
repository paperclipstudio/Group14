import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/***
 * This class is a display of a single card. This deals with what occurs when a card is hovered over, or
 * rotated.
 * @author Christian Sanger
 * @version 1.0
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
     * Gets all the resources necessary for the Card.
     * @param url The Url for resources.
     * @param rb The bundle of resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image.setOnMouseClicked((e) -> onRotateRight());
        button.setOnMouseClicked((e) -> onRotateRight());
    }

    /***
     * Adds a drop shadow to a card when the mouse hovers over it.
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
     * Removes the drop shadow from a card.
     */
    public void offHover() {
        fullCard.setEffect(null);
    }

    /***
     * Rotates the image on the card 90 degrees clockwise.
     */
    public void onRotateRight() {
        double newAngle = image.getRotate() + 90;
        if (newAngle >= 360) {
            newAngle -= 360;
        }
        image.setRotate(newAngle);
    }
}
