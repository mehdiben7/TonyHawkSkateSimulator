package ca.thetonyhawks.tonyhawksimulator.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class representing a planet on which there is a plane for the skater to ride on
 */
public class Planet {

    public static final String[] PLANETS_NAMES = {"Earth", "Moon", "Mars", "Sun"};
    public static final double[] PLANETS_GRAVITATIONAL_CONSTANTS = {9.806_65, 1.622, 3.711, 274};

    private final ObservableList<String> planetNameObservableList;

    public ObservableList<String> getPlanetNameObservableList() {
        return this.planetNameObservableList;
    }

    /**
     *  The gravitational acceleration constant <em>g</em> of the planet
     */
    private final DoubleProperty gravitationalAccelerationProperty;

    public DoubleProperty getGravitationalAccelerationProperty() {
        return this.gravitationalAccelerationProperty;
    }


    public Planet(double gravitationalAcceleration) {
        this.gravitationalAccelerationProperty = new SimpleDoubleProperty(gravitationalAcceleration);
        this.planetNameObservableList = FXCollections.observableArrayList(PLANETS_NAMES);


    }
}
