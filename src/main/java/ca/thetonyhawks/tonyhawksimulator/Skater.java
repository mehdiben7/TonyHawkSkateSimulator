package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;

public class Skater {
    private double mass;
    private DoubleProperty heightProperty;
    private DoubleProperty velocityProperty;
    private DoubleProperty accelerationProperty;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;


    public DoubleProperty heightProperty(){
        return null;
    }

    public DoubleProperty velocityProperty(){
        return null;
    }

    public DoubleProperty accelerationProperty(){
        return null;
    }

    public DoubleProperty xProperty(){
        return null;
    }

    public DoubleProperty yProperty(){
        return null;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setHeightProperty(double heightProperty) {
        this.heightProperty.set(heightProperty);
    }

    public void setVelocityProperty(double velocityProperty) {
        this.velocityProperty.set(velocityProperty);
    }

    public void setAccelerationProperty(double accelerationProperty) {
        this.accelerationProperty.set(accelerationProperty);
    }

    public void setxProperty(double xProperty) {
        this.xProperty.set(xProperty);
    }

    public void setyProperty(double yProperty) {
        this.yProperty.set(yProperty);
    }
}
