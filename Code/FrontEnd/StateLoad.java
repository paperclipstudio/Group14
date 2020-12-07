package FrontEnd;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Interface to set up each window when switching between scenes.
 * only one method initData to give setup information to that screen.
 *
 * @author Christian Sanger
 */
public abstract class StateLoad implements Initializable {

	private HashMap<String, String> initData;


	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or
	 *                  <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or <tt>null</tt> if
	 */
	@Override
	public abstract void initialize(URL location, ResourceBundle resources);

	/**
	 * Return the initialising data for this controller
	 * @return hashmap where key is a game state and the value is its value
	 */
	public HashMap<String, String> getInitData() {
		return initData;
	}

	/**
	 * Sets the initialising data for the controller
	 * @param initData hashmap where key is a game state and the value is its value
	 */
	public void setInitData(HashMap<String, String> initData) {
		this.initData = initData;
	}
}
