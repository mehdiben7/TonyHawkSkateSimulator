package ca.thetonyhawks.tonyhawksimulator.model;

import javafx.animation.AnimationTimer;

/**
 *  A timer that keeps track of the elapsed time of the skater animation, in order to determine its position and its velocity
 */
public class SkaterAnimationTimer extends AnimationTimer {

    /**
     *  The animation's model
     */
    private AnimationModel animationModel;

    private double skaterVelocity;

    /**
     *  The first timestamp of the timer (in ns)
     */
    private double firstTimestamp;

    @Override
    public void handle(long l) {
        if(firstTimestamp == -1.0)
            firstTimestamp = l;

        double timeSinceTimestamp = l - firstTimestamp;
        double elapsedTime = timeSinceTimestamp / 1E9;

        skaterVelocity = elapsedTime * animationModel.getSkater().accelerationProperty().get();

        animationModel.getSkater().velocityProperty().set(Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(skaterVelocity)));

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.firstTimestamp = -1.0;
    }

    /**
     *  Pauses the timer but keeps the elapsed time in memory
     *  can be resumed using {@link #start()}
     */
    public void pause() {
        super.stop();
    }

    public SkaterAnimationTimer(AnimationModel animationModel) {
        super();
        this.firstTimestamp = -1.0;
        this.skaterVelocity = 0.0;
        this.animationModel = animationModel;
    }
}
