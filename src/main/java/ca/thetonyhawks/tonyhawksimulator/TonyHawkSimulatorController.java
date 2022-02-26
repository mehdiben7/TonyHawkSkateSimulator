package ca.thetonyhawks.tonyhawksimulator;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.io.*;


public class TonyHawkSimulatorController {
    // TODO make sure radio buttons can only select one

    private PathTransition pt;

    @FXML
    private MenuItem about, database;

    @FXML
    private Button start, pause, reset;

    @FXML
    private void startEventHandler(ActionEvent actionEvent) {
        System.out.println("start");
        start.setDisable(true);

    }
    @FXML
    private void pauseEventHandler(ActionEvent actionEvent)
    {
        System.out.println("pause");
    }
    @FXML
    private void resetEventHandler(ActionEvent actionEvent) {
        System.out.println("reset");
        start.setDisable(false);
    }

    @FXML
    private void showImportDatabaseWindow(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/ImportDatabase.fxml"));
        Parent databaseContent = null;

        try {
            databaseContent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            // TODO catch exception
        }

        Stage importDatabaseStage = new Stage();
        Scene databaseScene = new Scene(databaseContent, 400, 600);
        importDatabaseStage.setTitle("Import planets' acceleration values");
        importDatabaseStage.setScene(databaseScene);
        importDatabaseStage.setResizable(false);
        // TODO Make sure the database import UI is responsive
        importDatabaseStage.show();
    }

    @FXML
    private void showAboutWindow(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/About.fxml"));
        Parent aboutContent = null;
        try {
            aboutContent = loader.load();
        } catch (IOException ex) {
            // TODO Actually catch IOException
            ex.printStackTrace();
        }

        Stage aboutStage = new Stage();
        Scene scene = new Scene(aboutContent, 920, 400);
        // TODO Change minimum size of About window

        aboutStage.setTitle("About this project");
        aboutStage.setScene(scene);
        aboutStage.show();

    }
}
