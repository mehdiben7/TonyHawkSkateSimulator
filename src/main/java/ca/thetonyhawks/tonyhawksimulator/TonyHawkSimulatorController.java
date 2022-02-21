package ca.thetonyhawks.tonyhawksimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class TonyHawkSimulatorController {

    @FXML
    private MenuItem about, database;

    @FXML
    private void showImportDatabaseWindow(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/ImportDatabse.fxml"));
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
        importDatabaseStage.show();
    }


    @FXML
    private void about(ActionEvent actionEvent) {

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
        aboutStage.setTitle("About this project");
        aboutStage.setScene(scene);
        aboutStage.show();

    }
}
