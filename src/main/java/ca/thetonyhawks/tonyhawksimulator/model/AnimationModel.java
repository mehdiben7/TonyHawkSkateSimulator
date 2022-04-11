package ca.thetonyhawks.tonyhawksimulator.model;

import ca.thetonyhawks.tonyhawksimulator.controller.TonyHawkSimulatorController;
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

import static ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane.getAngledPlaneAcceleration;

/**
 *  The animation's model
 */
public class AnimationModel {

    private TonyHawkSimulatorController controller;


    public static final DecimalFormat TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT = new DecimalFormat("0.00");



    /**
     *  Computes the fall of an object on an angled plane
     * @param planeLength The length of the plane (in m)
     * @param horizontalAcceleration The horizontal acceleration acting on the object (in m/s^2)
     * @return The fall duration (in s)
     */
    public static double getFallDuration(double planeLength, double horizontalAcceleration) {
        double fallDuration =  Math.sqrt((2 * planeLength) / horizontalAcceleration);

        return Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(fallDuration));
    }

    public double getModelFallDuration() {
        return getFallDuration(AngledSkaterPlane.PLANE_LENGTH, skater.accelerationProperty().get());
    }

    public TonyHawkSimulatorController getController() {
        return this.controller;
    }

//
//    public double getModelInstantaneousPosition() {
//        return getInstantaneousPosition(this.skater.velocityProperty().get(), this.skater.accelerationProperty().get());
//    }

    public static double getInstantaneousPosition(double horizontalAcceleration, double elapsedTime) { // TODO Solve problem with instantaneous position method
        double instantaneousPosition = 0.5 * horizontalAcceleration * Math.pow(elapsedTime, 2);
        String formattedPosition = TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(instantaneousPosition);

        return Double.parseDouble(formattedPosition);
    }




    public double getModelAcceleration() {
        return getAngledPlaneAcceleration(this.planet.getGravitationalAccelerationProperty().get(),
                                            this.plane.kineticFrictionCoefficientProperty().get(),
                                            this.plane.planeCoefficientProperty().get(),
                                            this.skater.skaterMassProperty().get());
    }


    // MARK - Object properties
    private final Planet planet;
    private SkaterPlane plane;
    private final Skater skater;

    private final DoubleProperty animationSpeedProperty;
    private final BooleanProperty isInSlowMotionProperty;
    private final BooleanProperty isPausedProperty;

    private final ObservableList<String> planeTypesProperty;

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
     *  Sets a new plane for the animation
     * @param plane The new plane
     */
    public void setPlane(SkaterPlane plane) {
        this.plane = plane;
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

        planeTypesProperty = FXCollections.observableList(Arrays.asList(SkaterPlane.PLANE_TYPES));


        this.planet = planet;
        this.plane = plane;
        this.skater = skater;
        this.skater.setPlanet(this.planet);
        this.skater.setPlane(this.plane);

        this.animationSpeedProperty = new SimpleDoubleProperty();
        this.isInSlowMotionProperty = new SimpleBooleanProperty(isInSlowMotion);
        this.isPausedProperty = new SimpleBooleanProperty(false);

        this.controller = controller;
    }

}
