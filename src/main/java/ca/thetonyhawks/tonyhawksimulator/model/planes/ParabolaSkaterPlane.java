package ca.thetonyhawks.tonyhawksimulator.model.planes;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
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
     *  Computes the height of any parabola plane
     * @param coefficient  The <em>a</em> coefficient of the plane
     * @return The height of the parabola plane (in m)
     */
    public static double getHeight(double coefficient) { // TODO Implement method to calculate height of a parabola plane
        return 0.0;
    }

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

    @Override
    public void updateModelPlaneHeightProperty() {
        double calculatedHeight = ParabolaSkaterPlane.getHeight(this.aCoefficientProperty.get());
        double formattedHeight = Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(calculatedHeight));
        this.planeHeightProperty.set(formattedHeight);
    }


}
