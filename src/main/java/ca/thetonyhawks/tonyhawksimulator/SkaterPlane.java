package ca.thetonyhawks.tonyhawksimulator;

public class SkaterPlane {
    public static final double DEFAULT_FRICTION_COEFFICIENT = 0.0;

    private double kineticFrictionCoefficient;

    public SkaterPlane(double kineticFrictionCoefficient) {
        this.kineticFrictionCoefficient = kineticFrictionCoefficient;
    }

    public double getKineticFrictionCoefficient() {
        return kineticFrictionCoefficient;
    }
}
