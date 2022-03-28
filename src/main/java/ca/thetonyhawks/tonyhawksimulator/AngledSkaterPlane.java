package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * A class representing an angled plane for the skater to ride on <br>
 *  Parent & Sister class:
 * @see SkaterPlane
 * @see ParabolaSkaterPlane
 */
public class AngledSkaterPlane extends SkaterPlane {

    public DoubleProperty angleProperty;

    /**
     *  Default angle for angled skater plane, which is 45 deg
     */
    private static final double DEFAULT_PLANE_ANGLE = 45;

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
    public DoubleProperty planeCoefficient() {
        return this.angleProperty;
    }


}
