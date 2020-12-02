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
 * @author zhan zhang
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

	/**
	 * show file we have in the save data folder to the list when this page is open
	 */
	public void initialize(){

		File file = new File("SaveData\\UserData\\");
		String[] children = file.list();

		if(children == null){
			Alert alert9 = new Alert(Alert.AlertType.INFORMATION);
			alert9.setTitle("New Player");
			alert9.setContentText("There is no player yet! Go and create one.");
			alert9.setHeaderText(null);
			alert9.showAndWait();

		} else {
			for (String filename : children) {
				playerList.getItems().addAll(filename);
			}
		}

	}

	/**
	 * the action on the button back, back to the menus screen.
	 */
	public void onBackButton() throws IOException {
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

			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("error");
			alert1.setContentText("Player already exists. Please use another name.");
			alert1.setHeaderText(null);
			alert1.showAndWait();

		} else {

			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("Welcome");
			alert2.setContentText("Welcome " + newName + ", have fun!");
			alert2.setHeaderText(null);
			alert2.showAndWait();
			playerList.getItems().addAll(newName + ".txt");

			PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
			newUser.write("0 0 icon0");
			newUser.close();

		}

	}

	/**
	 * delete the file with name entered and send alert when there is no file with same name.
	 */
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

	/**
	 * view the data saved in the file with same name as typed, send alert when there is
	 * no such a file.
	 * @throws FileNotFoundException File is not in the path
	 */
	public void viewData() throws FileNotFoundException {
		String newName = input.getText();
		File user = new File("SaveData\\UserData\\" + newName + ".txt");

		if(user.exists() && !user.isDirectory()){

			Scanner scan = new Scanner(user);

			while (scan.hasNextLine()){
				Alert alert7 = new Alert(Alert.AlertType.INFORMATION);
				alert7.setTitle("Player Profile");
				alert7.setContentText("Player " + newName + " has winning losing record and Icon ID with " + scan.nextLine());
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
