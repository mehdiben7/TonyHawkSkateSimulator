package ca.thetonyhawks.tonyhawksimulator;

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

    /**
     *  The <em>a</em> coefficient of the plane function <em>f(x) = a * x^2</em>
     */
    private double aCoefficient = ParabolaSkaterPlane.DEFAULT_PARABOLA_A_COEFFICIENT;

    /**
     *  Instantiates a new parabola skater plane
     * @param kineticFrictionCoefficient The kinetic friction coefficient <em>µ_k</em>
     * @param aCoefficient The <em>a</em> coefficient of the plane function <em>f(x) = a * x^2</em>
     */
    public ParabolaSkaterPlane(double kineticFrictionCoefficient, double aCoefficient) {
        super(kineticFrictionCoefficient);
        this.aCoefficient = aCoefficient;
    }

    /**
     * Gets the <em>a</em> coefficient of the parabola plane's function
     * @return The <em>a</em> coefficient
     */
    public double getACoefficient() {
        return aCoefficient;
    }

    /**
     *  Sets a new <em>a</em> coefficient for the parabola plane
     * @param aCoefficient The <em>a</em> coefficient of the plane function <em>f(x) = a * x^2</em>
     */
    public void setACoefficient(double aCoefficient) {
        this.aCoefficient = aCoefficient;
    }
}
