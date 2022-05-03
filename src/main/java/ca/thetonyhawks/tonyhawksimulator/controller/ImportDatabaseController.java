package ca.thetonyhawks.tonyhawksimulator.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;

/**
 *  The Controller class linked to the import database user interface window of the simulator <br>
 *  whose FXML file is <em>ImportDatabase.fxml</em>
 */
public class ImportDatabaseController
{

    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn name, acceleration;

    @FXML
    private TableView<Planet> table;
    @FXML
    private Button importData;

    private Stage importDatabaseStage;

    String engine="jdbc:sqlite";


    public Connection getConnection(String db)
    {
        //String connectingString;
        String connectingString = engine + ":" + db;
        try
        {
            return DriverManager.getConnection(connectingString);

        } catch(SQLException ex) {
            System.out.println("SQL Error:" + ex.getMessage()); // Error here
            return null;
        }
    }

    public void ImportEventHandler(ActionEvent actionEvent)
    {
        Planet planet=table.getSelectionModel().getSelectedItem();
        TonyHawkSimulatorController controller=new TonyHawkSimulatorController();
        controller.animationModel.getPlanet().getGravitationalAccelerationProperty().set(Double.parseDouble(planet.getAcceleration()));
        System.out.println("new grav const. : " + Double.parseDouble(planet.getAcceleration()));
        controller.planetComboBox.setValue("DataBase");
    }



    public void selectFileEventHandler(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Database Files", "*.db");
        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showOpenDialog(importDatabaseStage);
        if (file != null)
        {
            System.out.println(file.getPath());
            Connection connection = getConnection(file.getPath());
            String query = "SELECT * From TonyHawkSimulator";
            try
            {

                Statement statement = connection.createStatement(); // crash here
                ResultSet   resultSet = statement.executeQuery(query);
                name.setCellValueFactory(new PropertyValueFactory<Planet,String>("name"));
                acceleration.setCellValueFactory(new PropertyValueFactory<Planet,String>("acceleration"));
                ObservableList<Planet> data=FXCollections.observableArrayList();
                while (resultSet.next()) {
                    data.add(new Planet(resultSet.getString("Name"), resultSet.getString("Acceleration")));
                }
                table.setItems(data);
                importData.setDisable(false);

            }catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

        }
    }

    public static class Planet
    {
        private final SimpleStringProperty name;
        private final SimpleStringProperty acceleration;

        private Planet(String name,String acceleration)
        {
            this.name=new SimpleStringProperty(name);
            this.acceleration=new SimpleStringProperty(acceleration);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getAcceleration() {
            return acceleration.get();
        }

        public SimpleStringProperty accelerationProperty() {
            return acceleration;
        }

        public void setAcceleration(String acceleration) {
            this.acceleration.set(acceleration);
        }
    }
}
