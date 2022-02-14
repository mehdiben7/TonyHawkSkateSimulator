package ca.thetonyhawks.tonyhawksimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TonyHawkSimulator extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("/main.fxml"));
        //Parent content = loader.load();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/UserInterface.fxml"));
        Parent content = loader.load();

        Scene scene = new Scene(content, 1000, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}