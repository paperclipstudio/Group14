package FrontEnd;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/***
 * Used to load new windows
 * @author Christian Sanger
 */
public class WindowLoader {
	private static final String fileLocation = "FrontEnd\\";
	private Stage w;

	public WindowLoader(Node window) {
		this.w = (Stage) window.getScene().getWindow();
	}
	public void load(String window) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource(fileLocation + window + ".fxml"));
		} catch (IOException e) {
			System.out.print(e.getLocalizedMessage());
			e.printStackTrace();
		}
		Scene scene = null;
		if (root == null) {
			System.out.print("yeah thats null");
		} else {
			scene = new Scene(root);
		}
		w.setScene(scene);
	}
}
