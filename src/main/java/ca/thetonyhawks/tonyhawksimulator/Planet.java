package ca.thetonyhawks.tonyhawksimulator;

import java.util.ArrayList;

public class Planet {
    private String Name;
    private double gravitationalAcceleration;
    ArrayList<Planet> planets;

    public String getName() {
        return Name;
    }

    public double getGravitationalAcceleration() {
        return gravitationalAcceleration;
    }

    public void importPlanetsFromJSON(String jsonString){

    }
}
