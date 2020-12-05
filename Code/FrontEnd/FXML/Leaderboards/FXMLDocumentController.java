package FrontEnd.FXML.Leaderboards;

import BackEnd.Leaderboard;
import BackEnd.Score;
import FrontEnd.StateLoad;
import FrontEnd.WindowLoader;
import javafx.beans.property.Property;
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
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDocumentController extends StateLoad {

    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private final String BOARD_SFX = "Assets\\SFX\\board.mp3";
    private final AudioClip BOARD_AUDIO = new AudioClip(new File(BOARD_SFX).toURI().toString());
    private final double SFX_VOLUME = 0.2;

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


    public void handleButton1Action() throws IOException {
        BOARD_AUDIO.play(SFX_VOLUME);
        Leaderboard example1 = new Leaderboard("example1");
        example1.loadFile();
        highScore.setItems(example1.getObservableList());
        TableColumn<Score, String> nameColumn = new TableColumn<Score, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Score, String> winsColumn = new TableColumn<Score, String>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<Score, String> lossesColumn = new TableColumn<Score, String>("Losses");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("loss"));
        highScore.getColumns().setAll(nameColumn, winsColumn, lossesColumn);
    }

    public void handleButton2Action() throws IOException {
        BOARD_AUDIO.play(SFX_VOLUME);
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

    public void handleButton3Action() throws IOException {
        BOARD_AUDIO.play(SFX_VOLUME);
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


    public void onNewGame() {
        RETURN_AUDIO.play(SFX_VOLUME);
        wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
    }


    public void onQuitButton() {
        Platform.exit();
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

    }
}
