package ca.thetonyhawks.tonyhawksimulator;

public class AngledSkaterPlane extends SkaterPlane{
    private double angle = 0.0;

    public AngledSkaterPlane(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
