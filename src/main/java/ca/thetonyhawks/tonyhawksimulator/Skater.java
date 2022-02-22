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
        return this.heightProperty;
    }

    public DoubleProperty velocityProperty(){
        return this.velocityProperty;
    }

    public DoubleProperty accelerationProperty(){
        return this.accelerationProperty;
    }

    public DoubleProperty xProperty(){
        return this.xProperty;
    }

    public DoubleProperty yProperty(){
        return this.yProperty;
    }

    public double getMass() {
        return this.mass;
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
