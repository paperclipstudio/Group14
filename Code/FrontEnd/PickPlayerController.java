package FrontEnd;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PickPlayerController {

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
        playerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void returnPlayers() throws IOException {

        ObservableList<String> listOfPlayers = playerList.getSelectionModel().getSelectedItems();
        Map<String, String> playerProfileLoaded = new HashMap<>();

        for (Object players : listOfPlayers){

            Alert alert9 = new Alert(Alert.AlertType.INFORMATION);
            alert9.setTitle("New Player");
            alert9.setContentText("Add player with profile " + players);
            alert9.setHeaderText(null);
            alert9.showAndWait();

            String line;
            BufferedReader reader = new BufferedReader(new FileReader ("SaveData\\UserData\\" + players));

            while((line = reader.readLine()) != null){
                String[] parts = line.split(":", 2);
                if(parts.length >= 2){
                    String getWins = parts[0];
                    String getLosses = parts[1];
                    playerProfileLoaded.put(getWins, getLosses);


                    Alert alert10 = new Alert(Alert.AlertType.INFORMATION);
                    alert10.setTitle("New Player");
                    alert10.setContentText("This player has record " + playerProfileLoaded);
                    alert10.setHeaderText(null);
                    alert10.showAndWait();


                }else {
                    Alert alert10 = new Alert(Alert.AlertType.INFORMATION);
                    alert10.setTitle("New Player");
                    alert10.setContentText("This profile has wrong pattern");
                    alert10.setHeaderText(null);
                    alert10.showAndWait();
                }
            }
            reader.close();
    }
}}
