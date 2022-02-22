package ca.thetonyhawks.tonyhawksimulator;
// TODO Source code credits

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

//TODO radiobuttons for animation speed
// TODO JavaDocs for the whole project
public class TonyHawkSimulator extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/UserInterface.fxml"));
        Pane content = loader.load();

        Scene mainScene = new Scene(content, 1000, 900);
        stage.setTitle("Tony Hawk Skate Simulator");
        stage.setScene(mainScene);
        stage.setMinWidth(1000);
        stage.setMinHeight(900);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}