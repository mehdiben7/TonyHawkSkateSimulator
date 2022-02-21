package ca.thetonyhawks.tonyhawksimulator;

public class ParabolaSkaterPlane extends SkaterPlane{
    private double aCoefficient = 0.0;

    public ParabolaSkaterPlane(double aCoefficient) {
        this.aCoefficient = aCoefficient;
    }

    public double getaCoefficient() {
        return aCoefficient;
    }

    public void setaCoefficient(double aCoefficient) {
        this.aCoefficient = aCoefficient;
    }
}
