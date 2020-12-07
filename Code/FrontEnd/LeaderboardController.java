package FrontEnd;

import BackEnd.Leaderboard;
import BackEnd.Score;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the leaderboard window
 * @author Christian Sanger
 */
public class LeaderboardController extends StateLoad {
    private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
    private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
    private final String BOARD_SFX = "Assets\\SFX\\board.mp3";
    private final AudioClip BOARD_AUDIO = new AudioClip(new File(BOARD_SFX).toURI().toString());

    @FXML
    private Button newGameButton;
    @FXML
    private TableView<Score> highScore;
    @FXML
    private HBox boardButtons;

    /**
     * Changes which leaderboard is being shown
     * @param board the leaderboard to show
     * @throws IOException
     */
    public void changeLeaderboard(String board) throws IOException {
        BOARD_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
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

    /**
     * Switches to menu screen
     */
    public void onNewGame() {
        WindowLoader wl = new WindowLoader(newGameButton);
        wl.load("MenuScreen", getInitData());
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
            newButton.setMinWidth(150);
            newButton.setOnAction((e) -> {
                try {
                    changeLeaderboard(boardFile);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            boardButtons.getChildren().add(newButton);

        }
    }
}

