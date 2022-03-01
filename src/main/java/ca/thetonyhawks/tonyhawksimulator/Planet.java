package ca.thetonyhawks.tonyhawksimulator;

import java.util.ArrayList;

/**
 * A class representing a planet on which there is a plane for the skater to ride on
 */
public class Planet {
    /**
     *  The name of the planet
     */
    private String name;
    /**
     *  The gravitational acceleration constant <em>g</em> of the planet
     */
    private double gravitationalAcceleration;
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
    /**
     *  Returns the gravitational acceleration constant <em>g</em> of the planet
     * @return The gravitational acceleration constant <em>g</em> of the planet
     */
    public double getGravitationalAcceleration() {
        return this.gravitationalAcceleration;
    }
    /**
     *  Imports the planets' data provided in the JSON String object to the software
     * @param jsonString A JSON object inside a String object
     */
    public static void importPlanetsFromJSON(String jsonString){

    }

    public Planet(String name, double gravitationalAcceleration) {
        this.name = name;
        this.gravitationalAcceleration = gravitationalAcceleration;
    }
}
