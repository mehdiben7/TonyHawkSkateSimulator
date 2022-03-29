package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *  The animation's model
 */
public class AnimationModel {

    public static final DecimalFormat TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT = new DecimalFormat("#.##");


    /**
     *
     * @param gravitationalAcceleration The gravitational acceleration of the planet the skater is skating on (in m/s^2)
     * @param dynamicFrictionCoefficient The friction coefficient between the skater and the plane if any, otherwise 0
     * @param planeAngle The angle of the plane (in degrees)
     * @param skaterMass The skater mass (in kg)
     * @return The acceleration of the skater
     */
    public static double getAngledPlaneAcceleration(double gravitationalAcceleration, double dynamicFrictionCoefficient, double planeAngle, double skaterMass) {

        double horizontalGravitationalForce = skaterMass * gravitationalAcceleration * Math.sin(Math.toRadians(planeAngle));
        double verticalGravitationalForce = skaterMass * gravitationalAcceleration * Math.cos(Math.toRadians(planeAngle));
        double frictionForce = - (dynamicFrictionCoefficient * verticalGravitationalForce);
        double totalForce = horizontalGravitationalForce + frictionForce;

        return Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(totalForce / skaterMass));
    }

    public double getModelAcceleration() {
        // Will use the values of the animationModel object and plug them in the general method
        // And since i was not able to test the method i don't know if this method works
        return getAngledPlaneAcceleration(this.planet.getGravitationalAcceleration(), this.plane.kineticFrictionCoefficientProperty().get(),
                                          this.plane.planeCoefficientProperty().get(), this.skater.skaterMassProperty().get());
    }

    // MARK - Object properties
    private Planet planet;
    private SkaterPlane plane;
    private Skater skater;
    private double skaterInitialHeight;

    private DoubleProperty animationSpeedProperty;
    private BooleanProperty isInSlowMotionProperty;
    private BooleanProperty isPausedProperty;

    public BooleanProperty isPausedProperty() {
        return this.isPausedProperty;
    }

    public BooleanProperty isInSlowMotionProperty() {
        return this.isInSlowMotionProperty;
    }

    public DoubleProperty animationSpeedProperty() {
        return this.animationSpeedProperty;
    }

    /**
     *  Returns the planet used for the animation
     * @return The planet used in the animation
     */
    public Planet getPlanet() {
        return this.planet;
    }

    /**
     *  Returns the plane used for the animation
     * @return The plane used in the animation
     */
    public SkaterPlane getPlane() {
        return this.plane;
    }

    /**
     *  Returns the skater used in the animation
     * @return The skater
     */
    public Skater getSkater() {
        return this.skater;
    }

    /**
     *  Sets a new planet for the animation
     * @param planet The new planet
     */
    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    /**
     *  Sets a new plane for the animation
     * @param plane The new plane
     */
    public void setPlane(SkaterPlane plane) {
        this.plane = plane;
    }

    /**
     *  Sets a new skater for the animation
     * @param skater The new skater
     */
    public void setSkater(Skater skater) {
        this.skater = skater;
    }


    /**
     *  Set a new initial height for the skater
     * @param skaterInitialHeight The new skater's initial height, in m
     */
    public void setSkaterInitialHeight(double skaterInitialHeight){
        this.skaterInitialHeight = skaterInitialHeight;
    }

    /**
     *  Instantiates a new animation model
     * @param planet The planet that will be used for the animation, with a name and gravitational acceleration constant <em>g</em>
     * @param plane The plane the skater will ride on
     * @param skater The skater that will ride on the plane
     * @param isInSlowMotion Whether the animation starts in slow motion or not <br> <em>true</em> if it does, <em>false</em> otherwise
     * @param skaterInitialHeight The skater's initial height, in m
     */
    public AnimationModel(Planet planet, SkaterPlane plane, Skater skater, boolean isInSlowMotion, double skaterInitialHeight) {

        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);

        this.planet = planet;
        this.plane = plane;
        this.skater = skater;
        this.skaterInitialHeight = skaterInitialHeight;

        this.animationSpeedProperty = new SimpleDoubleProperty();
        this.isInSlowMotionProperty = new SimpleBooleanProperty(isInSlowMotion);
        this.isPausedProperty = new SimpleBooleanProperty(false);
    }

    public void toggleSlowMotion() {
        this.isInSlowMotionProperty.set(!this.isInSlowMotionProperty.get());
    }
}
