package ca.thetonyhawks.tonyhawksimulator.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn name,acceleration;

    @FXML
    private TableView table;

    private Stage importDatabaseStage;

    String engine="jdbc:sqlite";


    public Connection getConnection(String db)
    {   String connectingString;
//        String connectingString=engine+":"+db;
        try
        {
            connectingString="jdbc:sqlite:C:/Users/2067309/Documents/TonyHawkSimulator.db";
            Connection dbConnection=DriverManager.getConnection(connectingString);
            return dbConnection;
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void ImportEventHandler(ActionEvent actionEvent)
    {

    }



    public void selectFileEventHandler(ActionEvent actionEvent)
    {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Database Files", "*.db");
        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showOpenDialog(importDatabaseStage);
        if (file != null)
        {
            Connection connection = getConnection(file.getPath());
            String query="SELECT * From TonyHawkSimulator";
            try
            {
                ObservableList<String> data = null;
                Statement statement=connection.createStatement();
                ResultSet   resultSet=statement.executeQuery(query);
                data.add(resultSet.getString("Name"));
                name.setText(resultSet.getString("Name"));
                table.setItems(data);

            }catch(SQLException ex)
            {
                System.out.println(ex.getMessage());
            }

        }
    }
}
