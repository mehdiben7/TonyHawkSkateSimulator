package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * A class representing a planet on which there is a plane for the skater to ride on
 */
public class Planet {

    public static final String[] PLANETS_NAMES = {"Earth", "Moon", "Mars"};
    public static final double[] PLANETS_GRAVITATIONAL_CONSTANTS = {9.806_65, 1.622, 3.711};

    private ObservableList<String> planetNameObservableList;

    public ObservableList<String> getPlanetNameObservableList() {
        return this.planetNameObservableList;
    }

    /**
     *  The name of the planet
     */
    private String name;
    /**
     *  The gravitational acceleration constant <em>g</em> of the planet
     */
    private DoubleProperty gravitationalAccelerationProperty;
    /**
     *  The list of planets that have been imported in the software
     */
    public static ArrayList<Planet> planets;
    /**
     *  Returns the name of the planet
     * @return The name of the planet
     */
    public String getName() {
        return this.name;
    }

    public DoubleProperty getGravitationalAccelerationProperty() {
        return this.gravitationalAccelerationProperty;
    }




    /**
     *  Imports the planets' data provided in the JSON String object to the software
     * @param jsonString A JSON object inside a String object
     */
    public static void importPlanetsFromJSON(String jsonString){

    }

    public Planet(String name, double gravitationalAcceleration) {
        this.name = name;
        this.gravitationalAccelerationProperty = new SimpleDoubleProperty(gravitationalAcceleration);
        this.planetNameObservableList = FXCollections.observableArrayList(PLANETS_NAMES);


    }
}
