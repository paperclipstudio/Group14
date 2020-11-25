package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Scanner;

/***
 * Screen used to create edit and delete profiles.
 */
public class ProfilesController {
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

	public void initialize(){

		File file = new File("SaveData\\UserData\\");
		String[] children = file.list();

		if(children == null){
			Alert alert9 = new Alert(Alert.AlertType.INFORMATION);
			alert9.setTitle("New Player");
			alert9.setContentText("There is no player yet! Go and create one.");
			alert9.setHeaderText(null);
			alert9.showAndWait();

		}else {
			for (String filename : children) {
				playerList.getItems().addAll(filename);
			}
		}

	}

	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	public void createFile() throws IOException {
		String newName = input.getText();

		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.exists() && !user.isDirectory()){

			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("error");
			alert1.setContentText("Player already exists. Please use another name.");
			alert1.setHeaderText(null);
			alert1.showAndWait();

		}else{

			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("Welcome");
			alert2.setContentText("Welcome " + newName + ", have fun!");
			alert2.setHeaderText(null);
			alert2.showAndWait();
			playerList.getItems().addAll(newName + ".txt");

			PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
			newUser.write("Game Played: 0  Game Wins: 0");
			newUser.close();

		}

	}

	public void deleteFile() {
		String newName = input.getText();

		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.delete()){

			Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
			alert3.setTitle("Delete File");
			alert3.setContentText("File successful delete.");
			alert3.setHeaderText(null);
			alert3.showAndWait();
			playerList.getItems().remove(newName + ".txt");

		}else{

			Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
			alert4.setTitle("Delete File");
			alert4.setContentText("File can not be found, please try another name.");
			alert4.setHeaderText(null);
			alert4.showAndWait();

		}

	}

	public void viewData() throws FileNotFoundException {
		String newName = input.getText();
		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.exists() && !user.isDirectory()){

			Scanner scan = new Scanner(user);

			while (scan.hasNextLine()){
				Alert alert7 = new Alert(Alert.AlertType.INFORMATION);
				alert7.setTitle("Player Profile");
				alert7.setContentText("Player " + newName + " has record with " + scan.nextLine());
				alert7.setHeaderText(null);
				alert7.showAndWait();
			}

		}else{
			Alert alert8 = new Alert(Alert.AlertType.INFORMATION);
			alert8.setTitle("Player Profile");
			alert8.setContentText("Player information for " + newName +" is not found");
			alert8.setHeaderText(null);
			alert8.showAndWait();

		}

	}


}
