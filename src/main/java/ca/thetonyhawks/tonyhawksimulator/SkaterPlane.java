package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;

/**
 *  An abstract class representing a plane with no define shape
 * @see AngledSkaterPlane
 * @see ParabolaSkaterPlane
 */
public abstract class SkaterPlane {
    /**
     *  Default kinetic friction coefficient, which 0 with no friction
     */
    public static final double DEFAULT_FRICTION_COEFFICIENT = 0.0;

    /**
     *  Kinetic friction coefficient <em>µ_k</em>
     */
    private double kineticFrictionCoefficient;

    public abstract DoubleProperty angleOrAValueProperty(); // TODO Find a better name because this is hideous

//    public abstract DoubleProperty planeStartXProperty();
//    public abstract DoubleProperty planeStartYProperty();
//    public abstract DoubleProperty planeEndXProperty();
//    public abstract DoubleProperty planeEndYProperty();

    /**
     *  Instantiates a new plane with provided kinetic friction coefficient
     * @param kineticFrictionCoefficient Kinetic friction coefficient <em>µ_k</em>
     */
    public SkaterPlane(double kineticFrictionCoefficient) {
        this.kineticFrictionCoefficient = kineticFrictionCoefficient;
    }

    /**
     *  Returns the kinetic friction coefficient <em>µ_k</em> in the kinetic friction relation <em>f_kf = µ_k * F_N</em>
     * @return Kinetic friction coefficient <em>µ_k</em>
     */
    public double getKineticFrictionCoefficient() {
        return kineticFrictionCoefficient;
    }
}
