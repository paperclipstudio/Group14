package FrontEnd.FXML.Leaderboards;

import BackEnd.Leaderboard;
import BackEnd.Score;
import FrontEnd.WindowLoader;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDocumentController {

    private Label label;
    @FXML
    private Button newGameButton;
    @FXML
    private HBox allButtons;
    @FXML
    private TableView highScore;

    private WindowLoader wl;

    @FXML
    private BorderPane mainPane;


    public void handleButton1Action(javafx.event.ActionEvent actionEvent) throws IOException {
        Leaderboard example1 = new Leaderboard("example1");
        example1.loadFile();
        highScore.setItems(example1.getObservableList());
        TableColumn<Score, String> nameColumn = new TableColumn<Score, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Score, String> winsColumn = new TableColumn<Score, String>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory("wins"));
        TableColumn<Score, String> lossesColumn = new TableColumn<Score, String>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory("loss"));

        highScore.getColumns().setAll(nameColumn, winsColumn, lossesColumn);
    }

    public void handleButton2Action(javafx.event.ActionEvent actionEvent) throws IOException {
        Leaderboard example2 = new Leaderboard("example2");
        example2.loadFile();
        highScore.setItems(example2.getObservableList());
        TableColumn<Score, String> nameColumn = new TableColumn<Score, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Score, String> winsColumn = new TableColumn<Score, String>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory("wins"));
        TableColumn<Score, String> lossesColumn = new TableColumn<Score, String>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory("loss"));

        highScore.getColumns().setAll(nameColumn, winsColumn, lossesColumn);
    }

    public void handleButton3Action(javafx.event.ActionEvent actionEvent) throws IOException {
        Leaderboard example3 = new Leaderboard("example3");
        example3.loadFile();
        highScore.setItems(example3.getObservableList());
        TableColumn<Score, String> nameColumn = new TableColumn<Score, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Score, String> winsColumn = new TableColumn<Score, String>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory("wins"));
        TableColumn<Score, String> lossesColumn = new TableColumn<Score, String>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory("loss"));

        highScore.getColumns().setAll(nameColumn, winsColumn, lossesColumn);
    }


    public void onNewGame(ActionEvent actionEvent) {
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen");
    }


    public void onQuitButton(ActionEvent actionEvent) {
        Platform.exit();
        ;
    }




}
