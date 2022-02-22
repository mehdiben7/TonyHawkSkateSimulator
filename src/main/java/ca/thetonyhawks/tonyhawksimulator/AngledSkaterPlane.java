package ca.thetonyhawks.tonyhawksimulator;

public class AngledSkaterPlane extends SkaterPlane{
    private double angle = SkaterPlane.DEFAULT_FRICTION_COEFFICIENT;

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
