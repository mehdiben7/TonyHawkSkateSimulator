package ca.thetonyhawks.tonyhawksimulator;

public class SkaterPlane {

    private double kineticFrictionCoefficient;

    public SkaterPlane(double kineticFrictionCoefficient) {
        this.kineticFrictionCoefficient = kineticFrictionCoefficient;
    }

    public double getKineticFrictionCoefficient() {
        return kineticFrictionCoefficient;
    }
}
