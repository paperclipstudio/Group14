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

            if(Main.getNumberOfPlayers() == 2){

                playerList1.getItems().add(player);
                playerList2.getItems().add(player);

            }else if(Main.getNumberOfPlayers() == 3){

                playerList1.getItems().add(player);
                playerList2.getItems().add(player);
                playerList3.getItems().add(player);

            }else if(Main.getNumberOfPlayers() == 4) {


                playerList1.getItems().add(player);
                playerList2.getItems().add(player);
                playerList3.getItems().add(player);
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

                profiles.add(new File(playerList1.getValue()));
                profiles.add(new File(playerList2.getValue()));

            }else if(Main.getNumberOfPlayers() == 3){

                profiles.add(new File(playerList1.getValue()));
                profiles.add(new File(playerList2.getValue()));
                profiles.add(new File(playerList3.getValue()));

            }else if(Main.getNumberOfPlayers() == 4){

                profiles.add(new File(playerList1.getValue()));
                profiles.add(new File(playerList2.getValue()));
                profiles.add(new File(playerList3.getValue()));
                profiles.add(new File(playerList4.getValue()));

            }




            // Create
            // profiles[0] == profile of player 1
            // profiles[1] == profile of player 2

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Profile.getName(profiles);
        wl.load("GameScreen");

    }


    /**
     * return to previous page
     */
    public void onBackButton() {
        WindowLoader wl = new WindowLoader(backButton);
        wl.load("GameSetup");
    }
}

