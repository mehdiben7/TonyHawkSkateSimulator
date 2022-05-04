package ca.thetonyhawks.tonyhawksimulator.model;

import ca.thetonyhawks.tonyhawksimulator.controller.TonyHawkSimulatorController;
import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane.getAngledPlaneAcceleration;

/**
 *  The animation's model
 */
public class AnimationModel {

    private final TonyHawkSimulatorController controller;


    public static final DecimalFormat TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT = new DecimalFormat("0.00");

    private final BooleanProperty pausedProperty;


    /**
     *  Computes the fall of an object on an angled plane
     * @param planeLength The length of the plane (in m)
     * @param horizontalAcceleration The horizontal acceleration acting on the object (in m/s^2)
     * @return The fall duration (in s)
     */
    public static double getFallDuration(double planeLength, double horizontalAcceleration) {
        double fallDuration =  Math.sqrt((2 * planeLength) / horizontalAcceleration);
        System.out.println("Fall duration " + fallDuration + " s");
        return Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(fallDuration));
    }

    /**
     *  Gets the fall duration for this specific model's plane using
     *  {@link AnimationModel}'s getFallDuration(double, double)
     * @return The fall duration for this model (in s)
     */
    public double getModelFallDuration() {
        return getFallDuration(AngledSkaterPlane.PLANE_LENGTH, skater.accelerationProperty().get());
    }

    /**
     *  Gets the controller linked to this model
     * @return The controller linked to this model
     */
    public TonyHawkSimulatorController getController() {
        return this.controller;
    }

    /**
     *  Calculates the instantaneous position for any plane with a specific horizontal acceleration and time span
     * @param horizontalAcceleration The horizontal acceleration, orthogonal to the normal of the plane (in m/s^2)
     * @param elapsedTime The elapsed time since the beginning of the fall of the particle
     * @return The instantaneous position of the particle with respect to the start of its fall (in m)
     */
    public static double getInstantaneousPosition(double horizontalAcceleration, double elapsedTime) {
        double instantaneousPosition = 0.5 * horizontalAcceleration * Math.pow(elapsedTime, 2);
        String formattedPosition = TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(instantaneousPosition);
        return Double.parseDouble(formattedPosition);
    }

    /**
     *  Gets the acceleration for this specific model
     * @return The acceleration of this model (in m/s^2)
     */
    public double getModelAcceleration() {
        return getAngledPlaneAcceleration(this.planet.getGravitationalAccelerationProperty().get(),
                                            this.plane.kineticFrictionCoefficientProperty().get(),
                                            this.plane.planeCoefficientProperty().get(),
                                            this.skater.skaterMassProperty().get());
    }

    private final Planet planet;
    private final SkaterPlane plane;
    private final Skater skater;

    private final DoubleProperty animationSpeedProperty;
    private final BooleanProperty isInSlowMotionProperty;
    private final BooleanProperty hasBeenStartBeforeProperty;

    public BooleanProperty hasBeenStartedBeforeProperty() {
        return this.hasBeenStartBeforeProperty;
    }

    public BooleanProperty isInSlowMotionProperty() {
        return this.isInSlowMotionProperty;
    }

    public DoubleProperty animationDurationProperty() {
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
     *  Instantiates a new animation model
     * @param planet The planet that will be used for the animation, with a name and gravitational acceleration constant <em>g</em>
     * @param plane The plane the skater will ride on
     * @param skater The skater that will ride on the plane
     * @param isInSlowMotion Whether the animation starts in slow motion or not <br> <em>true</em> if it does, <em>false</em> otherwise
     */
    public AnimationModel(Planet planet, SkaterPlane plane, Skater skater, boolean isInSlowMotion, TonyHawkSimulatorController controller) {


        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormatSymbols dfs = TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.setDecimalFormatSymbols(dfs);


        this.planet = planet;
        this.plane = plane;
        this.skater = skater;
        this.skater.setPlanet(this.planet);
        this.skater.setPlane(this.plane);

        this.animationSpeedProperty = new SimpleDoubleProperty();
        this.isInSlowMotionProperty = new SimpleBooleanProperty(isInSlowMotion);
        this.hasBeenStartBeforeProperty = new SimpleBooleanProperty(false);
        this.pausedProperty = new SimpleBooleanProperty(true);
        this.controller = controller;
    }

    public BooleanProperty isPausedProperty() {
        return this.pausedProperty;
    }

}
