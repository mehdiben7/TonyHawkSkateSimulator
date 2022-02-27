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
import java.io.*;

/**
 *  The Controller class linked to the main user interface window of the simulator <br>
 *   whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {
    // TODO make sure radio buttons can only select one

    /**
     * @since Commit <em>start event</em> by Chanfan on 2022-02-22
     * @// FIXME: 2022-02-26 Create JavaDoc for this property
     */
    private PathTransition pt;

    /**
     *  Menu item buttons that trigger the opening of complementary windows
     */
    @FXML
    private MenuItem about, database;

    /**
     *  Start, pause, and reset buttons
     */
    @FXML
    private Button start, pause, reset;

    /**
     *  Triggers the animation start, putting him on top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void startEventHandler(ActionEvent actionEvent) {
        System.out.println("start");
        start.setDisable(true);

    }

    /**
     *  Triggers the pause of the animation, stopping the "fall" of the skater on the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void pauseEventHandler(ActionEvent actionEvent)
    {
        System.out.println("pause");
    }

    /**
     *  Triggers the reset of the animation, putting the skater back on the top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void resetEventHandler(ActionEvent actionEvent) {
        System.out.println("reset");
        start.setDisable(false);
    }

    /**
     *  Triggers the opening of the <em>import</em> database window
     * @param actionEvent An event representing the click on the about menu item button
     */
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

    /**
     *  Triggers the opening of the <em>about</em> window
     * @param actionEvent An event representing the click on the about menu item button
     */
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
