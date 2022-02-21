package ca.thetonyhawks.tonyhawksimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TonyHawkSimulatorController {

    @FXML
    private MenuItem about, database;


    @FXML
    private void about() {
        about.setOnAction(e -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/About.fxml"));
            Parent aboutContent = null;
            try {
                aboutContent = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Stage aboutStage = new Stage();
            Scene scene = new Scene(aboutContent, 920, 400);
            aboutStage.setTitle("Snell's Law GUI");
            aboutStage.setScene(scene);
            aboutStage.show();
        });
    }
}
