package ca.thetonyhawks.tonyhawksimulator.model;

import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;

/**
 *  The animation's model
 */
public class AnimationModel {

    public static final DecimalFormat TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT = new DecimalFormat("0.00");

    /**
     *  Computes the fall of an object on an angled plane
     * @param planeLength The length of the plane (in m)
     * @param horizontalAcceleration The horizontal acceleration acting on the object (in m/s^2)
     * @return The fall duration (in s)
     */
    public static double getFallDuration(double planeLength, double horizontalAcceleration) {
        double fallDuration =  Math.sqrt((2 * planeLength) / horizontalAcceleration);

        return Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(fallDuration));
    }

    public double getModelFallDuration() {
        return getFallDuration(AngledSkaterPlane.PLANE_LENGTH, skater.accelerationProperty().get());
    }


    /**
     *
     * @param gravitationalAcceleration The gravitational acceleration of the planet the skater is skating on (in m/s^2)
     * @param dynamicFrictionCoefficient The friction coefficient between the skater and the plane if any, otherwise 0
     * @param planeAngle The angle of the plane (in degrees)
     * @param skaterMass The skater mass (in kg)
     * @return The acceleration of the skater
     */
    public static double getAngledPlaneAcceleration(double gravitationalAcceleration, double dynamicFrictionCoefficient, double planeAngle, double skaterMass) { // TODO Move this method to AngledSkaterPlane ?
        double horizontalGravitationalForce = skaterMass * gravitationalAcceleration * Math.sin(Math.toRadians(planeAngle));
        double verticalGravitationalForce = skaterMass * gravitationalAcceleration * Math.cos(Math.toRadians(planeAngle));
        double frictionForce = - (dynamicFrictionCoefficient * verticalGravitationalForce);
        double totalForce = horizontalGravitationalForce + frictionForce; // TODO Should we make sure this is never negative (in case of very low angle/very high kinetic coefficient

        return Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(totalForce / skaterMass));
    }

    public double getModelAcceleration() {
        double computedAngledPlaneAcceleration = getAngledPlaneAcceleration(this.planet.getGravitationalAccelerationProperty().get(), this.plane.kineticFrictionCoefficientProperty().get(), this.plane.planeCoefficientProperty().get(), this.skater.skaterMassProperty().get());
        return computedAngledPlaneAcceleration;
    }


    // MARK - Object properties
    private Planet planet;
    private SkaterPlane plane;
    private Skater skater;
    private double skaterInitialHeight;

    private DoubleProperty animationSpeedProperty;
    private BooleanProperty isInSlowMotionProperty;
    private BooleanProperty isPausedProperty;

    private ObservableList<String> planeTypesProperty;

    public BooleanProperty isPausedProperty() {
        return this.isPausedProperty;
    }

    public BooleanProperty isInSlowMotionProperty() {
        return this.isInSlowMotionProperty;
    }

    public DoubleProperty animationDurationProperty() {
        return this.animationSpeedProperty;
    }

    public ObservableList<String> planeTypesProperty() {
        return this.planeTypesProperty;
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

        DecimalFormatSymbols dfs = TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setDecimalFormatSymbols(dfs);

        planeTypesProperty = FXCollections.observableList(Arrays.asList(SkaterPlane.PLANE_TYPES));


        this.planet = planet;
        this.plane = plane;
        this.skater = skater;
        this.skater.setPlanet(this.planet);
        this.skater.setPlane(this.plane);
        this.skaterInitialHeight = skaterInitialHeight;

        this.animationSpeedProperty = new SimpleDoubleProperty();
        this.isInSlowMotionProperty = new SimpleBooleanProperty(isInSlowMotion);
        this.isPausedProperty = new SimpleBooleanProperty(false);
    }

    public void toggleSlowMotion() {
        this.isInSlowMotionProperty.set(!this.isInSlowMotionProperty.get());
    }
}
