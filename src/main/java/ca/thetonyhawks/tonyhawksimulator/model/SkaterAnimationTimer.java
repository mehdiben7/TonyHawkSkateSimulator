package ca.thetonyhawks.tonyhawksimulator.model;

import javafx.animation.AnimationTimer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SkaterAnimationTimer extends AnimationTimer {
    private long lastUpdateTimeStamp;
    private double timeElapsed = 0.0;

    private AnimationModel animationModel;

    @Override
    public void handle(long now) {

        double t = (System.nanoTime() - now) / 1E9;
        timeElapsed += t;


    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(timeElapsed));
        timeElapsed = 0.0;
    }

    public void pause() {
        super.stop();

    }

    public SkaterAnimationTimer(AnimationModel animationModel) {

        this.animationModel = animationModel;
        this.lastUpdateTimeStamp = 0;
    }
}
