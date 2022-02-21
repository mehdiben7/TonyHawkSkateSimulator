package ca.thetonyhawks.tonyhawksimulator;

public class AnimationModel {
    private Planet planet;
    private SkaterPlane plane;
    private  Skater skater;
    private  boolean isInSlowMotion;
    private  double skaterInitialHeight;

    public Planet getPlanet() {
        return planet;
    }

    public SkaterPlane getPlane() {
        return plane;
    }

    public Skater getSkater() {
        return skater;
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

    }

    public void setSkaterInitialHeight(double skaterInitialHeight){

    }

}
