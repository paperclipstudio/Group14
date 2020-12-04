package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Scanner;

/***
 * Screen used to create edit and delete profiles.
 * @author zhan zhang
 */
public class ProfilesController {

	@FXML
	public Button viewButton;

	@FXML
	public Label playerRecord;

	@FXML
	private Button backButton;

	@FXML
	private TextField input;

	/***
	 * Returns to menu screen
	 * called by back button
	 */

	@FXML
	private ListView<String> playerList;

	/**
	 * show file we have in the save data folder to the list when this page is open
	 */
	public void initialize(){

		File file = new File("SaveData\\UserData\\");
		String[] children = file.list();


			for (String filename : children) {

				playerList.getItems().addAll(filename.substring(0, filename.length() - 4));


			}


		}



	/**
	 * the action on the button back, back to the menus screen.
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/**
	 * try to create a user file with name typed, create one in UserData if there is not a file with
	 * initialized data and send alert if there is one.
	 * @throws IOException Wrong input
	 */
	public void createFile() throws IOException {
		String newName = input.getText();

		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.exists() && !user.isDirectory()){

			input.setStyle("-fx-background-color: red");

		} else {

			input.setStyle("-fx-background-color: white");

			playerList.getItems().addAll(newName);

			PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
			newUser.write("0 0 icon0");
			newUser.close();

		}

	}

	/**
	 * delete the file with name entered and send alert when there is no file with same name.
	 */
	public void deleteFile() {
		String newName = playerList.getSelectionModel().getSelectedItem();

		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.delete()){

			input.setStyle("-fx-background-color: white");

			playerList.getItems().remove(newName);

		}else{


			input.setStyle("-fx-background-color: white");

		}

	}

	/**
	 * view the data saved in the file with same name as typed, send alert when there is
	 * no such a file.
	 * @throws FileNotFoundException File is not in the path
	 */
	//public void viewData() throws FileNotFoundException {
		//String newName = input.getText();

		//File user = new File("SaveData\\UserData\\" + newName + ".txt");

		//if(user.exists() && !user.isDirectory()){

			//Scanner scan = new Scanner(user);

			//while (scan.hasNextLine()){
				//Alert alert7 = new Alert(Alert.AlertType.INFORMATION);
				//alert7.setTitle("Player Profile");
				//alert7.setContentText("Player " + newName + " has winning losing record and Icon ID with " + scan.nextLine());
				//alert7.setHeaderText(null);
				//alert7.showAndWait();
			//}

		//}else{
			//Alert alert8 = new Alert(Alert.AlertType.INFORMATION);
			//alert8.setTitle("Player Profile");
			//alert8.setContentText("Player information for " + newName +" is not found");
			//alert8.setHeaderText(null);
			//alert8.showAndWait();

		//}

	//}

	public void viewData() throws IOException{


		String playerPicked = playerList.getSelectionModel().getSelectedItem();



			String line;
			BufferedReader reader = new BufferedReader(new FileReader("SaveData\\UserData\\" + playerPicked + ".txt"));
			int getWin = 0;
			int getLoss = 0;
			while ((line = reader.readLine()) != null) {
			String[] parts = line.split(" ", 3);
			if (parts.length >= 1) {
				getWin = Integer.parseInt(parts[0]);
				getLoss = Integer.parseInt(parts[1]);

			}

		}  reader.close();

		playerRecord.setText("This player has record with winning " + getWin + " times and losing " + getLoss + " times");




	}
}
