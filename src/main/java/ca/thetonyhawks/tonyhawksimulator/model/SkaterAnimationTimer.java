package ca.thetonyhawks.tonyhawksimulator.model;

import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.geometry.Point2D;

import java.util.function.DoubleConsumer;
import java.util.function.Function;

/**
 *  A timer that keeps track of the elapsed time of the skater animation, in order to determine its position and its velocity
 */
public class SkaterAnimationTimer extends AnimationTimer {

    /**
     *  The animation's model
     */
    private final AnimationModel animationModel;


    /**
     *  The velocity of the skater (in m/s)
     */
    private double skaterVelocity;

    /**
     *  The first timestamp of the timer (in ns)
     */
    private double firstTimestamp;

//    public Interpolator getInterpolate() {
////        return value -> {
////            Point2D angledPlaneStart = animationModel.getController().getPlaneStart();
////            Point2D angledPlaneEnd = animationModel.getController().getPlaneEnd();
////            double progression = animationModel.getSkater().positionProperty().get() / AngledSkaterPlane.PLANE_LENGTH;
////            double divisor = 1 / progression;
////            Point2D midPoint = new Point2D((angledPlaneEnd.getX() - angledPlaneStart.getX()) / divisor, (angledPlaneEnd.getY() - angledPlaneStart.getY()) / divisor);
////            animationModel.getController().moveSkaterTo(midPoint);
////        };
//
//        new Interpolator() {
//            @Override
//            protected double curve(double v) {
//                return 0;
//            }
//        };
//    }

    @Override
    public void handle(long l) {
        if(firstTimestamp == -1.0)
            firstTimestamp = l;

        double timeSinceTimestamp = l - firstTimestamp;
        double elapsedTime = timeSinceTimestamp / 1E9;

        skaterVelocity = elapsedTime * animationModel.getSkater().accelerationProperty().get();

        animationModel.getSkater().velocityProperty().set(Double.parseDouble(AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(skaterVelocity)));

        animationModel.getSkater().positionProperty().set(AnimationModel.getInstantaneousPosition(animationModel.getSkater().accelerationProperty().get(), elapsedTime));

        animationModel.getController().updateEnergyValues(animationModel.getSkater().kineticEnergyProperty().get(),
                                            animationModel.getSkater().potentialGravitationalEnergyProperty().get());

//        if(animationModel.getPlane() instanceof AngledSkaterPlane) {
//            Point2D angledPlaneStart = animationModel.getController().getPlaneStart();
//            Point2D angledPlaneEnd = animationModel.getController().getPlaneEnd();
//            double progression = animationModel.getSkater().positionProperty().get() / AngledSkaterPlane.PLANE_LENGTH;
//            double divisor = 1 / progression;
//            Point2D midPoint = new Point2D((angledPlaneEnd.getX() - angledPlaneStart.getX()) / divisor, (angledPlaneEnd.getY() - angledPlaneStart.getY()) / divisor);
//            animationModel.getController().moveSkaterTo(midPoint);
//        }


        if(animationModel.getPlane() instanceof AngledSkaterPlane && animationModel.getSkater().positionProperty().get() >= AngledSkaterPlane.PLANE_LENGTH) {
            this.pause();
            animationModel.getController().pauseAnimation();
        }

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

    /**
     *  Instantiates a new SkaterAnimationTimer related to the provided model
     * @param animationModel The model of the animation related to the timer
     */
    public SkaterAnimationTimer(AnimationModel animationModel) {
        super();
        this.firstTimestamp = -1.0;
        this.skaterVelocity = 0.0;
        this.animationModel = animationModel;

    }
}
