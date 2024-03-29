package ca.thetonyhawks.tonyhawksimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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


        Scene mainScene = new Scene(content, 1400, 800);
        stage.setTitle("Tony Hawk Skate Simulator");
        stage.setScene(mainScene);
        stage.setMinWidth(1485);
        stage.setMinHeight(1000);

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