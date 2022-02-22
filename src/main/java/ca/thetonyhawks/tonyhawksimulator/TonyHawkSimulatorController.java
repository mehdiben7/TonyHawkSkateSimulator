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

    private PathTransition pt;

    @FXML
    private MenuItem about, database;

    @FXML
    private Button normalSpeedButton, slowMotionButton,start;

    @FXML
    private void startEventHandler(ActionEvent actionEvent)
    {
        System.out.println("start");
        start.setDisable(true);
    }
    // TODO Check if the two methods of changing animation speed can be united as one
    @FXML
    private void setNormalSpeedAnimation(ActionEvent actionEvent) {
        // TODO Implement setting normal speed animation
        System.out.println("Animation set to normal speed");
        normalSpeedButton.setDisable(true);
        slowMotionButton.setDisable(false);
    }

    @FXML
    private void setSlowMotionAnimation(ActionEvent actionEvent) {
        // TODO Implement setting set slow motion speed animation
        System.out.println("Animation set to slow motion");
        slowMotionButton.setDisable(true);
        normalSpeedButton.setDisable(false);
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
