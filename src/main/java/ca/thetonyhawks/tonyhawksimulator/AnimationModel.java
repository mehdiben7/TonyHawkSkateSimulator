package ca.thetonyhawks.tonyhawksimulator;

public class AnimationModel {
    private Planet planet;
    private SkaterPlane plane;
    private  Skater skater;
    private  boolean isInSlowMotion;
    private  double skaterInitialHeight;

    public Planet getPlanet() {
        return this.planet;
    }

    public SkaterPlane getPlane() {
        return this.plane;
    }

    public Skater getSkater() {
        return this.skater;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public void setPlane(SkaterPlane plane) {
        this.plane = plane;
    }

    public void setSkater(Skater skater) {
        this.skater = skater;
    }

    public boolean isInSlowMotion(){
       return this.isInSlowMotion;
    }

    public void toggleSlowMotion(){
        this.isInSlowMotion = !this.isInSlowMotion;
    }

    public void setSkaterInitialHeight(double skaterInitialHeight){
        this.skaterInitialHeight = skaterInitialHeight;
    }

    public AnimationModel(Planet planet, SkaterPlane plane, Skater skater, boolean isInSlowMotion, double skaterInitialHeight) {
        this.planet = planet;
        this.plane = plane;
        this.skater = skater;
        this.isInSlowMotion = isInSlowMotion;
        this.skaterInitialHeight = skaterInitialHeight;
    }
}
