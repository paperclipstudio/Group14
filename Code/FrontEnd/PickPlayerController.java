package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.ArrayList;

import BackEnd.Profile;


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

    ArrayList<File> profiles = new ArrayList<>();

    /**
     * show player select scene
     *      */
     public void initialize(){

        String[] players;
        File playerLocation = new File("SaveData\\UserData\\");
        players = playerLocation.list();
        label.setText("You decide to start a game with " + Main.getNumberOfPlayers() + " players");

        assert players != null;
        for (String player : players){
            playerList1.getItems().add(player);
            if(Main.getNumberOfPlayers() >= 2){
                playerList2.getItems().add(player);
            }
            if(Main.getNumberOfPlayers() >= 3){
                playerList3.getItems().add(player);
            }
            if(Main.getNumberOfPlayers() >= 4) {
                playerList4.getItems().add(player);
            }

        }

        playerList1.getSelectionModel().selectFirst();
        playerList2.getSelectionModel().selectFirst();
        playerList3.getSelectionModel().selectFirst();
        playerList4.getSelectionModel().selectFirst();

    }

    /**
     * add the chosen player's file to the arraylist and go to the game screen.
     */
    public void savePlayersAndStart() {
        WindowLoader wl = new WindowLoader(backButton);
        try {

            if(Main.getNumberOfPlayers() == 2) {

                if(!playerList1.getValue().equals(playerList2.getValue())){
                    profiles.add(new File(playerList1.getValue()));
                    profiles.add(new File(playerList2.getValue()));

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
                    profiles.add(new File(playerList1.getValue()));
                    profiles.add(new File(playerList2.getValue()));
                    profiles.add(new File(playerList3.getValue()));

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
                    profiles.add(new File(playerList1.getValue()));
                    profiles.add(new File(playerList2.getValue()));
                    profiles.add(new File(playerList3.getValue()));
                    profiles.add(new File(playerList4.getValue()));

                    wl.load("GameScreen");

                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("You can not select same players more than once.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }




            // Create
            // profiles[0] == profile of player 1
            // profiles[1] == profile of player 2

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Profile.getName(profiles);

    }


    /**
     * return to previous page
     */
    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("GameSetup");
    }
}

