package ca.thetonyhawks.tonyhawksimulator;
// TODO Source code credits

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

//TODO radiobuttons for animation speed

/**
 *  Application main class
 */
public class TonyHawkSimulator extends Application {
    /**
     *  Starts the application
     * @param stage The application' stage
     * @throws IOException If the FXML file cannot be found
     */
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

    /**
     *  The application's main class's main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}