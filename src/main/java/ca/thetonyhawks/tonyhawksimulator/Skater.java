package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;

/**
 *  A class representing the skater going down the plane
 */
public class Skater {

    /**
     *  The default skater mass, in kg, which is 70.00 kg
     */
    public static final double DEFAULT_SKATER_MASS = 70.0;

    /**
     *  The skater's mass, in kg
     * @// FIXME: 2022-02-26 Should this be a DoubleProperty?
     */
    private double mass;
    private DoubleProperty heightProperty;
    private DoubleProperty velocityProperty;
    private DoubleProperty accelerationProperty;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;


    // MARK - Properties getter
    public DoubleProperty heightProperty() {
        return this.heightProperty;
    }
    public DoubleProperty velocityProperty() {
        return this.velocityProperty;
    }
    public DoubleProperty accelerationProperty() {
        return this.accelerationProperty;
    }
    public DoubleProperty xProperty(){
        return this.xProperty;
    }
    public DoubleProperty yProperty(){
        return this.yProperty;
    }

    /**
     * Returns the mass of the skater
     * @return The skater's mass, in kg
     */
    public double getMass() {
        return this.mass;
    }

    /**
     *  Sets the mass of the skater
     * @param mass The new skater's mass, in kg
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     *  Sets a new height value for the skater
     * @param heightProperty The skater's new height value, in m
     */
    public void setHeightProperty(double heightProperty) {
        this.heightProperty.set(heightProperty);
    }

    /**
     *  Sets the skater's velocity value
     * @param velocityProperty The skater's new velocity value, in m/s
     */
    public void setVelocityProperty(double velocityProperty) {
        this.velocityProperty.set(velocityProperty);
    }

    /**
     *  Modifies the skater's acceleration value
     * @param accelerationProperty The skater's new acceleration value, in m/s^2
     */
    public void setAccelerationProperty(double accelerationProperty) {
        this.accelerationProperty.set(accelerationProperty);
    }

    /**
     *  Puts the skater on a new horizontal coordinate
     * @param xProperty The new horizontal coordinate <em>x</em>
     */
    public void setXProperty(double xProperty) {
        this.xProperty.set(xProperty);
    }

    /**
     *  Puts the skater on a new vertical coordinate
     * @param yProperty The new vertical coordinate <em>y</em>
     */
    public void setYProperty(double yProperty) {
        this.yProperty.set(yProperty);
    }

    public Skater(double mass) {
        this.mass = mass;
    }
}
