package ca.thetonyhawks.tonyhawksimulator;

import java.util.ArrayList;

public class Planet {
    private String name;
    private double gravitationalAcceleration;
    public static ArrayList<Planet> planets;

    public String getName() {
        return this.name;
    }

    public double getGravitationalAcceleration() {
        return this.gravitationalAcceleration;
    }

    public static void importPlanetsFromJSON(String jsonString){

    }
}
