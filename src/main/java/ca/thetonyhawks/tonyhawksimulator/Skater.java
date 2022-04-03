package ca.thetonyhawks.tonyhawksimulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *  A class representing the skater going down the plane
 */
public class Skater {

//    assertEquals(140, Skater.getKineticEnergy(70, 2));
//    assertEquals(215.63, Skater.getKineticEnergy(69, 2.5));
//    assertEquals(4648.86, Skater.getKineticEnergy(89, 10.221));
//    assertEquals(141.02, Skater.getKineticEnergy(50, 2.375));

    /**
     *  Calculates the kinetic energy of the skater
     * @param skaterMass The skater's mass (in kg)
     * @param skaterVelocity The skater's velocity (in m/s)
     * @return The skater's kinetic energy (in J)
     */
    public static double getKineticEnergy(double skaterMass, double skaterVelocity) {
        double kineticEnergyDouble = 0.5 * skaterMass * Math.pow(skaterVelocity, 2);
        String kineticEnergyString = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(kineticEnergyDouble);
        double parsedDouble = Double.parseDouble(kineticEnergyString);
        return Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(0.5 * skaterMass * Math.pow(skaterVelocity, 2)));
    }

    /**
     *  Calculates the skater's potential gravitational energy
     * @param skaterMass The skater's mass (in kg)
     * @param gravitationalAcceleration The gravitational acceleration acting on the skater (in m/s^2)
     * @param skaterHeight The skater's height (in m)
     * @return The skater's potential gravitational energy (in J)
     */
    public static double getPotentialGravitationalEnergy(double skaterMass, double gravitationalAcceleration, double skaterHeight) {
        return Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(skaterMass * gravitationalAcceleration * skaterHeight));
    }

    /**
     *  The default skater mass, in kg, which is 70.00 kg
     */
    public static final double DEFAULT_SKATER_MASS = 70.0;


    private DoubleProperty skaterMassProperty;
    private DoubleProperty heightProperty;
    private DoubleProperty positionProperty;
    private DoubleProperty velocityProperty;
    private DoubleProperty accelerationProperty;
    private DoubleProperty xProperty;
    private DoubleProperty yProperty;


    // MARK - Properties getter
    public DoubleProperty heightProperty() {
        return this.heightProperty;
    }
    public DoubleProperty velocityProperty() {
        return this.velocityProperty;
    }
    public DoubleProperty accelerationProperty() {
        return this.accelerationProperty;
    }
    public DoubleProperty xProperty(){
        return this.xProperty;
    }
    public DoubleProperty yProperty(){
        return this.yProperty;
    }
    public DoubleProperty positionProperty() {
        return this.positionProperty;
    }


    public DoubleProperty skaterMassProperty() {
        return this.skaterMassProperty;
    }

    /**
     *  Sets a new height value for the skater
     * @param heightProperty The skater's new height value, in m
     */
    public void setHeightProperty(double heightProperty) {
        this.heightProperty.set(heightProperty);
    }

    /**
     *  Sets the skater's velocity value
     * @param velocityProperty The skater's new velocity value, in m/s
     */
    public void setVelocityProperty(double velocityProperty) {
        this.velocityProperty.set(velocityProperty);
    }

    /**
     *  Modifies the skater's acceleration value
     * @param accelerationProperty The skater's new acceleration value, in m/s^2
     */
    public void setAccelerationProperty(double accelerationProperty) {
        this.accelerationProperty.set(accelerationProperty);
    }

    /**
     *  Puts the skater on a new horizontal coordinate
     * @param xProperty The new horizontal coordinate <em>x</em>
     */
    public void setXProperty(double xProperty) {
        this.xProperty.set(xProperty);
    }

    /**
     *  Puts the skater on a new vertical coordinate
     * @param yProperty The new vertical coordinate <em>y</em>
     */
    public void setYProperty(double yProperty) {
        this.yProperty.set(yProperty);
    }

    public Skater(double mass) {
        this.skaterMassProperty = new SimpleDoubleProperty(mass);
        this.heightProperty = new SimpleDoubleProperty(0);
        this.positionProperty = new SimpleDoubleProperty(0);
        this.velocityProperty = new SimpleDoubleProperty(0);
        this.accelerationProperty = new SimpleDoubleProperty(0);
    }
}
