package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;

/***
 * A controller class for profiles.fxml which shows player profiles saved, create new player profiles, delete player profiles
 * and view player profiles.
 * Load by actions from MenuScreen and return to MenuScreen with an action.
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
     * show file we have in the SaveData\\UserData folder to the listview when this page running.
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
     * the action on the button backButton, back to the menus screen.
     */

    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("MenuScreen");
    }

    /**
     * try to create a user file with name typed, create one in UserData if there is not a file with
     * initialized data and turn text field to red if there is one.
     *
     * @throws IOException when FileWriter failed to write the file.
     */
    public void createFile() throws IOException {
        String newName = input.getText();

        if (newName.isEmpty()) {
            playerRecord.setText("Please enter a player name, before creating a new profile!");
        } else {
            File user = new File("SaveData\\UserData\\" + newName + ".txt");

            if (user.exists() && !user.isDirectory()) {
                input.setStyle("-fx-background-color: red");

            } else {
                input.setStyle("-fx-background-color: white");
                playerList.getItems().addAll(newName);
                PrintWriter newUser = new PrintWriter(new FileWriter("SaveData\\UserData\\" + newName + ".txt"));
                newUser.write("0 0 icon0");
                newUser.close();
            }
        }
    }

    /**
     * Delete the file choose in view list, turn the text field back to white if it is not.
     */
    public void deleteFile() {
        String newName = playerList.getSelectionModel().getSelectedItem();
        File user = new File("SaveData\\UserData\\" + newName + ".txt");
        if (user.delete()) {
            input.setStyle("-fx-background-color: white");
            playerList.getItems().remove(newName);

        } else {
            input.setStyle("-fx-background-color: white");
        }
    }

    /**
     * View the data saved in the file with same name as choose in the view list, no response when there is no such a file.
     */
    public void viewData() {
        String playerPicked = playerList.getSelectionModel().getSelectedItem();
        String line;
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
