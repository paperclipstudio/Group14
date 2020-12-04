package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;

/***
 * A controller class for profiles.fxml which allows a user to show the player profiles saved, create new player
 * profiles, delete player profiles and view player profiles.
 * It is loaded by clicking from it in the MenuScreen and allows the user to return to the MenuScreen with an action.
 * @author Zhan Zhang
 * @version 1.0
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

    @FXML
    private ListView<String> playerList;

    /**
     * This shows the files we have in the SaveData\\UserData folder as a list when this page is running.
     */
    public void initialize() {
        File file = new File("SaveData\\UserData\\");
        String[] children = file.list();

        assert children != null;
        for (String filename : children) {
            playerList.getItems().addAll(filename.substring(0, filename.length() - 4));
        }
    }

    /**
     * Returns the user to the main menu screen.
     */

    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen");
    }

    /**
     * Creates a user file with name typed, creates one in UserData if there is not a file with
     * that given name and turn text field to red if there is one (i.e, you cannot create a user with the same name
     * as an already created user.)
     *
     * @throws IOException When the FileWriter fails to write the file.
     */
    public void createFile() throws IOException {
        String newName = input.getText();

        if (newName.isEmpty()) {
            playerRecord.setText("Please enter a player name, before creating a new profile!");
        } else {
            File user = new File("SaveData\\UserData\\" + newName + ".txt");
            if (user.exists() && !user.isDirectory()) {
                input.setStyle("-fx-border-color: red");
                playerRecord.setText("That name already exists, please use another name!");
            } else {
                input.setStyle("-fx-border-color: default");
                playerList.getItems().addAll(newName);
                PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
                newUser.write("0 0 icon0");
                newUser.close();
            }
        }
    }

    /**
     * Deletes the file selected in the list, and turns the text field back to white if it is not.
     */
    public void deleteFile() {
        String newName = playerList.getSelectionModel().getSelectedItem();
        File user = new File("SaveData\\UserData\\" + newName + ".txt");
        if (user.delete()) {
            input.setStyle("-fx-border-color: default");
            playerList.getItems().remove(newName);

        } else {
            input.setStyle("-fx-border-color: default");
        }
    }

    /**
     * View the data saved in the file with same name as selected in the view list, when that file doesn't exist,
     * print an error message.
     */
    public void viewData() {
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        String line;
        input.setStyle("-fx-border-color: default");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("SaveData\\UserData\\" + playerPicked + ".txt"));
            int getWin = 0;
            int getLoss = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 3);
                if (parts.length >= 1) {
                    getWin = Integer.parseInt(parts[0]);
                    getLoss = Integer.parseInt(parts[1]);
                }
            }
            reader.close();
            playerRecord.setText("This player has " + getWin + " wins and " + getLoss + " losses.");
        } catch (IOException noPlayerFound) {
            playerRecord.setText("Please select a player.");
        }
    }
}
