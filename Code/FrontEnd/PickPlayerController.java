package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.util.ArrayList;

import BackEnd.Profile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;



/**
 * When game start, show numbers of choiceBox to let players select player profile for different player, set these files
 * to a array list for game board to use.
 * @author zhan zhang
 */
public class PickPlayerController {

    @FXML
    public ChoiceBox<String> playerList1;

    @FXML
    public ChoiceBox<String> playerList2;

    @FXML
    public ChoiceBox<String> playerList3;

    @FXML
    public ChoiceBox<String> playerList4;
    public Label label;

    @FXML
    private Button backButton;

    ArrayList<Profile> profiles = new ArrayList<>();

    /**
     * show player select scene
     *
     */
     public void initialize(){

         String[] players;

         File playerLocation = new File("SaveData\\UserData\\");
         players = playerLocation.list();
         
        label.setText("You decide to start a game with " + Main.getNumberOfPlayers() + " players");

        assert players != null;
        for (String player : players){
            String playerName = player.substring(0, player.length() - 4);

            playerList1.getItems().add(playerName);
            if(Main.getNumberOfPlayers() >= 2){
                playerList2.getItems().add(playerName);
            }
            if(Main.getNumberOfPlayers() >= 3){
                playerList3.getItems().add(playerName);
            }
            if(Main.getNumberOfPlayers() >= 4) {
                playerList4.getItems().add(playerName);
            }

        }

        playerList1.getSelectionModel().selectFirst();
        playerList2.getSelectionModel().selectFirst();
        playerList3.getSelectionModel().selectFirst();
        playerList4.getSelectionModel().selectFirst();
        playerList4.setVisible(false);

    }

    /**
     * @param profileFile read files from UserData and turns them into profiles
     * @return    get the profile output
     * @throws IOException Wrong input
     */
    public Profile readProfile(File profileFile) throws IOException {

         String name = profileFile.getName();

         String playerIcon = null;

         String line;
         int wins = 0 ;
         int losses = 0;
         BufferedReader reader = new BufferedReader(new FileReader(profileFile));
         while((line = reader.readLine()) != null){
             String[] parts = line.split(":",2);

                 if(parts.length >=2){
                 wins = Integer.parseInt(parts[0]);
                 losses = Integer.parseInt(parts[1]);
                 }
         }

        return new Profile(name, playerIcon, wins, losses);

    }


    /**
     * add the chosen player's file to the arraylist and go to the game screen.
     */
    public void savePlayersAndStart() {
        WindowLoader wl = new WindowLoader(backButton);
        try {

            Main.setProfiles(profiles.toArray(new Profile[0]));
            if(Main.getNumberOfPlayers() == 2) {

                if(!playerList1.getValue().equals(playerList2.getValue())){

                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList1.getValue() + ".txt")));

                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList2.getValue() + ".txt")));

                    wl.load("GameScreen");

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("You can not select same players more than once.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }

            }else if(Main.getNumberOfPlayers() == 3){

                if(!playerList1.getValue().equals(playerList2.getValue()) &&
                        !playerList1.getValue().equals(playerList3.getValue()) &&
                        !playerList2.getValue().equals(playerList3.getValue())
                   ){
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList1.getValue() + ".txt")));
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList2.getValue() + ".txt")));
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList3.getValue() + ".txt")));

                    wl.load("GameScreen");

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("You can not select same players more than once.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }

            }else if(Main.getNumberOfPlayers() == 4){

                if(!playerList1.getValue().equals(playerList2.getValue()) &&
                        !playerList1.getValue().equals(playerList3.getValue()) &&
                        !playerList1.getValue().equals(playerList4.getValue()) &&
                        !playerList2.getValue().equals(playerList3.getValue()) &&
                        !playerList2.getValue().equals(playerList4.getValue()) &&
                        !playerList3.getValue().equals(playerList4.getValue())
                   ){

                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList1.getValue() + ".txt")));
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList2.getValue() + ".txt")));
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList3.getValue() + ".txt")));
                    profiles.add(readProfile(
                            new File("SaveData\\UserData\\" + playerList4.getValue() + ".txt")));

                    wl.load("GameScreen");

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("You can not select same players more than once.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * return to previous page
     */
    public void onBackButton() throws IOException {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("GameSetup");
    }
}

