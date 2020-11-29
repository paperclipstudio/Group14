package FrontEnd;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    @FXML
    private Button newGameButton;
    @FXML
    private HBox allButtons;

    private WindowLoader wl;

    public static String boardChoice = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // For each file in SaveData\\boardsData\\
        // Create new button with the text being the name of that board.
        // button.onAction(() -> {
        //       boardChoice = this filename
        //       which to ViewLeaderboard()
        // });

        String[] filenames = new String[] {"button George!", "don't click me", "What another one?"};
        for (String filename : filenames) {
            Button anotherLevelButton = new Button();
            anotherLevelButton.setText(filename);
            anotherLevelButton.setOnAction((e) -> {
                System.out.println(filename);});
                allButtons.getChildren().add(anotherLevelButton);
        };

    }

    public void onQuitButton() {
        Platform.exit();;
    }

    public void onNewGame() {
        wl = new WindowLoader(newGameButton);
        wl.load("GameSetup");
    }

    public void onBoard1(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board1");
    }

    public void onBoard2(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board2");
    }

    public void onBoard3(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("Leaderboards/Board3");
    }

    public void onBack(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen");
    }
}
