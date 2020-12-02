package FrontEnd.FXML.Leaderboards;

import FrontEnd.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class FXMLDocumentController {

    private Label label;
    @FXML
    private Button newGameButton;
    @FXML
    private HBox allButtons;

    private WindowLoader wl;

    @FXML
    private BorderPane mainPane;


    public void handleButton1Action(javafx.event.ActionEvent actionEvent) {
        System.out.println("Yo clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Screen1");
        mainPane.setCenter(view);
    }

    public void handleButton2Action(javafx.event.ActionEvent actionEvent) {
        System.out.println("Yo clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Screen2");
        mainPane.setCenter(view);
    }

    public void handleButton3Action(javafx.event.ActionEvent actionEvent) {
        System.out.println("Yo clicked me!");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Screen3");
        mainPane.setCenter(view);
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
