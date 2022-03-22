package ca.thetonyhawks.tonyhawksimulator;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *  The Controller class linked to the main user interface window of the simulator <br>
 *   whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {

    public static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat("0.00");

    AnimationModel animationModel = new AnimationModel(new Planet("A planet", 100), new AngledSkaterPlane(0, 45), new Skater(75), false, 10 );

    private PathTransition angledPlaneSkaterPathTransition;
    private Path angledPlanePath;

    @FXML
    private Rectangle skater;

    /**
     *  Menu item buttons that trigger the opening of complementary windows
     */
    @FXML
    private MenuItem about, database;

    /**
     *  Start, pause, and reset buttons
     */
    @FXML
    private Button start, pause, reset;

    @FXML
    private RadioButton normalSpeedRadioButton, slowMotionRadioButton;

    @FXML
    private ToggleGroup speed;

    @FXML
    private Slider planeAngleSlider;

    @FXML
    private Label planeAngleLabel;

    @FXML
    private Pane skaterPlanePane; // TODO Is this useful ?

    @FXML
    private Line angledPlaneLine;

    @FXML
    private HBox centerPanel;

    @FXML
    private TextField skaterMassField, skaterInitialHeightField;

    private PathTransition pt;
    private Path path = new Path();

    /**
     *  Toggles the change between slow motion and regular speed of animation
     * @param actionEvent An event representing the switch between radio buttons
     */
    @FXML
    private void motionSpeedChangeEventHandler(ActionEvent actionEvent) {
        double duration = normalSpeedRadioButton.isSelected() ? animationModel.animationSpeedProperty().get() * 0.5 :
                                                                animationModel.animationSpeedProperty().get();

        pt.setDuration(Duration.seconds(duration));
    }

    /**
     *  Triggers the animation start, putting the skater on top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void startEventHandler(ActionEvent actionEvent) {

        System.out.println("Animation started !");

        if(animationModel.isPausedProperty().get()) {
            pt.play();
            animationModel.isPausedProperty().set(false);
        }
        else {
            animate(angledPlaneLine);
            animationModel.isPausedProperty().set(true);
        }

    }

    void animate(Line line) {

        double animationNormalSpeed = animationModel.animationSpeedProperty().get() * 0.5;
        double animationSlowMotionSpeed = animationModel.animationSpeedProperty().get(); // The two values should be inverted, but it doesn't work IDK why

        skater.setX(line.getStartX());
        skater.setY(700 - line.getStartY());
        MoveTo moveTo = new MoveTo();
        moveTo.setX(angledPlaneLine.getStartX());
        moveTo.setY(angledPlaneLine.getStartY());

        LineTo lineTo = new LineTo();
        lineTo.setX(angledPlaneLine.getEndX());
        lineTo.setY(angledPlaneLine.getEndY());

        path.getElements().addAll(moveTo, lineTo);

        pt = new PathTransition(Duration.seconds(normalSpeedRadioButton.isSelected() ? animationNormalSpeed : animationSlowMotionSpeed), path, skater);

        pt.setCycleCount(Animation.INDEFINITE);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.interpolatorProperty().setValue(Interpolator.LINEAR);
        pt.playFromStart();
    }

    /**
     *  Triggers the pause of the animation, stopping the "fall" of the skater on the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void pauseEventHandler(ActionEvent actionEvent) {
        pt.pause();
        animationModel.isPausedProperty().set(true);
    }

    /**
     *  Triggers the reset of the animation, putting the skater back on the top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void resetEventHandler(ActionEvent actionEvent) {
        System.out.println("reset");
        pt.stop();
        pt.playFromStart();
        planeAngleSlider.setDisable(false);
    }

    /**
     *  Triggers the opening of the <em>import</em> database window
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void showImportDatabaseWindow(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/ImportDatabase.fxml"));
        Parent databaseContent = null;

        try {
            databaseContent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            // TODO catch exception
        }

        Stage importDatabaseStage = new Stage();
        Scene databaseScene = new Scene(databaseContent, 400, 600);
        importDatabaseStage.setTitle("Import planets' acceleration values");
        importDatabaseStage.setScene(databaseScene);
        importDatabaseStage.setResizable(false);
        // TODO Make sure the database import UI is responsive
        importDatabaseStage.show();
    }

    /**
     *  Triggers the opening of the <em>about</em> window
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void showAboutWindow(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/About.fxml"));
        Parent aboutContent = null;
        try {
            aboutContent = loader.load();
        } catch (IOException ex) {
            // TODO Actually catch IOException
            ex.printStackTrace();
        }

        Stage aboutStage = new Stage();
        Scene scene = new Scene(aboutContent, 920, 400);
        // TODO Change minimum size of About window

        aboutStage.setTitle("About this project");
        aboutStage.setScene(scene);
        aboutStage.show();

    }

    @FXML
    public void planeAngleSliderDragDetected(MouseEvent mouseEvent) {
    }

    @FXML
    public void planeAngleSliderDragDone(MouseEvent mouseEvent) {

    }

    @FXML
    public void onSkaterMassEntered(ActionEvent actionEvent) {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
    }

    public TonyHawkSimulatorController() {
        if(animationModel.getPlane() instanceof AngledSkaterPlane) {
            System.out.println("Angled");
        } else if(animationModel.getPlane() instanceof ParabolaSkaterPlane) {
            System.out.println("Parabola");
        }
    }

    public void initialize() {
        planeAngleSlider.valueProperty().bindBidirectional(animationModel.getPlane().angleOrAValueProperty());
        planeAngleSlider.disableProperty().bind(animationModel.isPausedProperty().not());

        animationModel.getPlane().angleOrAValueProperty().addListener((observableValue, number, t1) -> {
            double newAngle = (double) t1;
            String formattedNewAngleString = TWO_DECIMAL_PLACES.format(newAngle);
            planeAngleLabel.setText(formattedNewAngleString + " deg");
        });

        slowMotionRadioButton.selectedProperty().bindBidirectional(animationModel.isInSlowMotionProperty());


        skater.layoutXProperty().bind(angledPlaneLine.startXProperty());
        skater.layoutYProperty().bind(angledPlaneLine.startYProperty());

        animationModel.animationSpeedProperty().set(10);

        // Temporary


    }


}
