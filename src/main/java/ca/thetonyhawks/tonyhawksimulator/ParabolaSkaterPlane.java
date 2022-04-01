package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *  A class representing a parabola plane for the skater to ride on <br>
 *  Parent & Sister class:
 * @see SkaterPlane
 * @see AngledSkaterPlane
 */
public class ParabolaSkaterPlane extends SkaterPlane {

    /**
     * The default <em>a</em> coefficient of the plane function <em>f(x) = a * x^2</em>, which is 1 <br>
     * such that <em>f(x) = x^2</em>
     */
    private static final double DEFAULT_PARABOLA_A_COEFFICIENT = 1.0;


    private DoubleProperty aCoefficientProperty;

    /**
     *  Instantiates a new parabola skater plane
     * @param kineticFrictionCoefficient The kinetic friction coefficient <em>µ_k</em>
     * @param aCoefficient The <em>a</em> coefficient of the plane function <em>f(x) = a * x^2</em>
     */
    public ParabolaSkaterPlane(double kineticFrictionCoefficient, double aCoefficient) {
        super(kineticFrictionCoefficient);
        this.aCoefficientProperty = new SimpleDoubleProperty(DEFAULT_PARABOLA_A_COEFFICIENT);
    }

    public DoubleProperty aCoefficientProperty() {
        return this.aCoefficientProperty;
    }

    @Override
    public DoubleProperty planeCoefficientProperty() {
        return this.aCoefficientProperty;
    }
}
