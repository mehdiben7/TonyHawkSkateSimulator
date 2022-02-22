package ca.thetonyhawks.tonyhawksimulator;

public class AngledSkaterPlane extends SkaterPlane{
    private double angle = 0.0;

    public AngledSkaterPlane(double dynamicFrictionCoefficient, double angle) {
        super(dynamicFrictionCoefficient);
        this.angle = angle;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
