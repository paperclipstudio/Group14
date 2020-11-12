import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;

import javax.xml.soap.Text;
import java.io.FileNotFoundException;
import java.net.URL;

public class GameScreen {
	static final String fxmlFile = "file:///C:\\Users\\Christian\\Git\\Group14\\Code\\gameScreen.fxml";
	public static Scene makeWindow() throws Exception {
		TitledPane error = new TitledPane();
		error.setContent(new Label("Error Page"));
		Parent root;

		try {
			root = FXMLLoader.load(new URL(fxmlFile));
		} catch (Exception e) {
			throw new FileNotFoundException(fxmlFile + " Not found");
		}
		return new Scene(root, 600, 400);
	}
}
