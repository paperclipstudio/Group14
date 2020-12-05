package FrontEnd;

import BackEnd.Leaderboard;
import BackEnd.Score;
import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDocumentController extends StateLoad {

    @FXML
    private Button newGameButton;
    @FXML
    private TableView<Score> highScore;
    @FXML
    private HBox boardButtons;

    private WindowLoader wl;

    public void changeLeaderboard(String board) throws IOException {
        Leaderboard leaderboard = new Leaderboard(board);
        leaderboard.loadFile();
        highScore.setItems(leaderboard.getObservableList());
        TableColumn<Score, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Score, String> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<Score, String> lossesColumn = new TableColumn<>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("loss"));
        highScore.getColumns().setAll(nameColumn, winsColumn, lossesColumn);
    }

    public void onNewGame(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }


    public void onQuitButton(ActionEvent actionEvent) {
        Platform.exit();
        ;
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File boardFolder = new File("Gameboards");
        boardButtons.getChildren().clear();
        for (String boardFile: boardFolder.list()) {
            String boardName = boardFile.substring(0, boardFile.length()-4);
            System.out.println(boardName);
            Button newButton = new Button(boardName);
            newButton.setOnAction((e) -> {
                try {
                    changeLeaderboard(boardName);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            boardButtons.getChildren().add(newButton);

        }
    }
}
