package ca.thetonyhawks.tonyhawksimulator.model.planes;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import static ca.thetonyhawks.tonyhawksimulator.model.AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT;

/**
 * A class representing an angled plane for the skater to ride on <br>
 *  Parent & Sister class:
 * @see SkaterPlane
 * @see ParabolaSkaterPlane
 */
public class AngledSkaterPlane extends SkaterPlane {

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
        totalForce = totalForce >= 0 ? totalForce : 0.0;

        return Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(totalForce / skaterMass));
    }

    public static final double PLANE_LENGTH = 30.0;

    /**
     *  Calculates the height of the top of the angled plane
     * @param planeWidth The width of the plane (in m)
     * @param planeAngle The angle of the plane (in degrees)
     * @return The height of the top of the angled plane (in m)
     */
    public static double getHeight(double planeWidth, double planeAngle) {
        double calculatedHeight = planeWidth * Math.sin(Math.toRadians(planeAngle));
        return Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(calculatedHeight));
    }

    public DoubleProperty angleProperty;

    /**
     *  Default angle for angled skater plane, which is 45 deg
     */
    public static final double DEFAULT_PLANE_ANGLE = 45;

    /**
     *  The angle of the plane
     */
    private double angle;

    /**
     *  Instantiates a new angled skater plane
     * @param kineticFrictionCoefficient Kinetic friction coefficient <em>µ_k</em>
     * @param angle The angle of the plane
     */
    public AngledSkaterPlane(double kineticFrictionCoefficient, double angle) {
        super(kineticFrictionCoefficient);
        this.angle = angle;
        this.angleProperty = new SimpleDoubleProperty();
        this.angleProperty.setValue(angle);


        this.angleProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateModelPlaneHeightProperty();
            }
        });
    }

    /**
     *  Gets the angle of the plane
     * @return The angle of the plane
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     *  Sets a new angle for the plane
     * @param angle The new plane's angle
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public DoubleProperty planeCoefficientProperty() {
        return this.angleProperty;
    }


    @Override
    public void updateModelPlaneHeightProperty() {
        double calculatedHeight = AngledSkaterPlane.getHeight(AngledSkaterPlane.PLANE_LENGTH, this.angleProperty.get());
        double formattedAngledPlaneHeight = Double.parseDouble(TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(calculatedHeight));
        this.planeHeightProperty().set(formattedAngledPlaneHeight);
    }


}
