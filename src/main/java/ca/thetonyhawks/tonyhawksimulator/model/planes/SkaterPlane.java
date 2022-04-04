package ca.thetonyhawks.tonyhawksimulator.model.planes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *  An abstract class representing a plane with no define shape
 * @see AngledSkaterPlane
 * @see ParabolaSkaterPlane
 */
public abstract class SkaterPlane {

    public static final String[] PLANE_TYPES = {"Angled plane", "Parabola"};

    /**
     *  Default kinetic friction coefficient, which 0 with no friction
     */
    public static final double DEFAULT_FRICTION_COEFFICIENT = 0.0;

    /**
     *  Kinetic friction coefficient <em>µ_k</em>
     */
    private final DoubleProperty kineticFrictionCoefficientProperty;

    public abstract DoubleProperty planeCoefficientProperty();

    /**
     *  Instantiates a new plane with provided kinetic friction coefficient
     * @param kineticFrictionCoefficient Kinetic friction coefficient <em>µ_k</em>
     */
    public SkaterPlane(double kineticFrictionCoefficient) {
        this.kineticFrictionCoefficientProperty = new SimpleDoubleProperty(kineticFrictionCoefficient);
    }

    /**
     *  Returns the kinetic friction coefficient <em>µ_k</em> in the kinetic friction relation <em>f_kf = µ_k * F_N</em>
     * @return Kinetic friction coefficient <em>µ_k</em>
     */
    public DoubleProperty kineticFrictionCoefficientProperty() {
        return this.kineticFrictionCoefficientProperty;
    }


}
