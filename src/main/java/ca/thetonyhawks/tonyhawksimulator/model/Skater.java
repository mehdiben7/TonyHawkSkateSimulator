package ca.thetonyhawks.tonyhawksimulator.model;

import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *  A class representing the skater going down the plane
 */
public class Skater {

    private Planet planet;
    private SkaterPlane plane;

    /**
     *  Calculates the kinetic energy of the skater
     * @param skaterMass The skater's mass (in kg)
     * @param skaterVelocity The skater's velocity (in m/s)
     * @return The skater's kinetic energy (in J)
     */
    public static double getKineticEnergy(double skaterMass, double skaterVelocity) {
        return Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(0.5 * skaterMass * Math.pow(skaterVelocity, 2)));
    }

    /**
     *  Calculates the skater's potential gravitational energy
     * @param skaterMass The skater's mass (in kg)
     * @param gravitationalAcceleration The gravitational acceleration acting on the skater (in m/s^2)
     * @param skaterHeight The skater's height (in m)
     * @param kineticEnergy The skater's kinetic energy (in J)
     * @return The skater's potential gravitational energy (in J)
     */
    public static double getPotentialGravitationalEnergy(double skaterMass, double gravitationalAcceleration, double skaterHeight, double kineticEnergy) {
        double pGE = Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format((skaterMass * gravitationalAcceleration * skaterHeight) - kineticEnergy));

        return pGE >= 0 ? pGE : 0;
    }



    /**
     *  The default skater mass, in kg, which is 70.00 kg
     */
    public static final double DEFAULT_SKATER_MASS = 70.0;


    private final DoubleProperty skaterMassProperty;
    private final DoubleProperty positionProperty;
    private final DoubleProperty velocityProperty;
    private final DoubleProperty accelerationProperty;
    private final DoubleProperty kineticEnergyProperty;
    private final DoubleProperty potentialGravitationalEnergyProperty;


    public DoubleProperty velocityProperty() {
        return this.velocityProperty;
    }
    public DoubleProperty accelerationProperty() {
        return this.accelerationProperty;
    }
    public DoubleProperty positionProperty() {
        return this.positionProperty;
    }
    public DoubleProperty kineticEnergyProperty() {
        return this.kineticEnergyProperty;
    }
    public DoubleProperty potentialGravitationalEnergyProperty() {
        return this.potentialGravitationalEnergyProperty;
    }

    public DoubleProperty skaterMassProperty() {
        return this.skaterMassProperty;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public void setPlane(SkaterPlane skaterPlane) {
        this.plane = skaterPlane;
    }

    /**
     *  Instantiates a new Skater object with a specific mass
     * @param mass The mass of the skater (in kg)
     */
    public Skater(double mass) {
        this.skaterMassProperty = new SimpleDoubleProperty(mass);
        this.positionProperty = new SimpleDoubleProperty(0);
        this.velocityProperty = new SimpleDoubleProperty(0);
        this.accelerationProperty = new SimpleDoubleProperty(0);
        this.kineticEnergyProperty = new SimpleDoubleProperty(0);
        this.potentialGravitationalEnergyProperty = new SimpleDoubleProperty(0);

        this.velocityProperty.addListener((observable, oldValue, newValue) -> {
            double currentVelocity = (double) newValue;

            plane.updateModelPlaneHeightProperty();

            kineticEnergyProperty.set(Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(
                                                    getKineticEnergy(skaterMassProperty.get(), currentVelocity))));



            potentialGravitationalEnergyProperty.set(Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(
                                                    getPotentialGravitationalEnergy(skaterMassProperty.get(),
                                                            planet.getGravitationalAccelerationProperty().get(),
                                                            plane.planeHeightProperty().get(), kineticEnergyProperty.get()))));
        });

        // MARK - Temporary

//        this.potentialGravitationalEnergyProperty.addListener((observable, oldValue, newValue) -> System.out.println((double) newValue + " is the potential energy in Joules"));

    }
}
