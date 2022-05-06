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
     *  Default kinetic friction coefficient, which is 0 with no friction
     */
    public static final double DEFAULT_FRICTION_COEFFICIENT = 0.0;

    /**
     *  Kinetic friction coefficient <em>µ_k</em>
     */
    private final DoubleProperty kineticFrictionCoefficientProperty;

    /**
     *  The variable that changes the plane dimensions (either the angle in degrees for the {@link AngledSkaterPlane}
     *      and the <em>a</em> coefficient for the {@link ParabolaSkaterPlane}
     * @return The coefficient of the plane
     */
    public abstract DoubleProperty planeCoefficientProperty();

    /**
     *  A property that holds the height of the plane (in m)
     */
    protected DoubleProperty planeHeightProperty;

    /**
     *  Gets the property of the plane's height
     * @return The plane's height property (in m)
     */
    public DoubleProperty planeHeightProperty() {
        return this.planeHeightProperty;
    }

    /**
     *  Updates planeHeightProperty with the values computed with the static methods in {@link AngledSkaterPlane} and {@link ParabolaSkaterPlane}
     */
    public abstract void updateModelPlaneHeightProperty();

    /**
     *  Instantiates a new plane with provided kinetic friction coefficient
     * @param kineticFrictionCoefficient Kinetic friction coefficient <em>µ_k</em>
     */
    public SkaterPlane(double kineticFrictionCoefficient) {
        this.kineticFrictionCoefficientProperty = new SimpleDoubleProperty(kineticFrictionCoefficient);
        this.planeHeightProperty = new SimpleDoubleProperty(0);
    }

    /**
     *  Returns the kinetic friction coefficient <em>µ_k</em> in the kinetic friction relation <em>f_kf = µ_k * F_N</em>
     * @return Kinetic friction coefficient <em>µ_k</em>
     */
    public DoubleProperty kineticFrictionCoefficientProperty() {
        return this.kineticFrictionCoefficientProperty;
    }


}
