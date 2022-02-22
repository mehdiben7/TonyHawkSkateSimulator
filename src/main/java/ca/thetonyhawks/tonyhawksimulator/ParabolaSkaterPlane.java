package ca.thetonyhawks.tonyhawksimulator;

public class ParabolaSkaterPlane extends SkaterPlane {
    private double aCoefficient = SkaterPlane.DEFAULT_FRICTION_COEFFICIENT;

    public ParabolaSkaterPlane(double kineticFrictionCoefficient, double aCoefficient) {
        super(kineticFrictionCoefficient);
        this.aCoefficient = aCoefficient;
    }

    public double getACoefficient() {
        return aCoefficient;
    }

    public void setACoefficient(double aCoefficient) {
        this.aCoefficient = aCoefficient;
    }
}
