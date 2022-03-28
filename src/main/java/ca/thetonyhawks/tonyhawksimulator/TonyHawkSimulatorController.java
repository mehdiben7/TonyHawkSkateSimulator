package ca.thetonyhawks.tonyhawksimulator;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.text.DecimalFormat;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 *  The Controller class linked to the main user interface window of the simulator <br>
 *   whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {

    public static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat("0.00");
    public static final Pattern DOUBLE_PATTERN = Pattern.compile("\\\\d*|\\\\d+\\\\.\\\\d*"); // TODO Solve RegEx problems
    public static final TextFormatter DOUBLE_PATTERN_TEXT_FORMATTER = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
        return DOUBLE_PATTERN.matcher(change.getControlNewText()).matches() ? change : null;
    });

    AnimationModel animationModel = new AnimationModel(new Planet("A planet", 100), new AngledSkaterPlane(0, 45), new Skater(75), false, 10 );


    private BooleanProperty showForceVectorsProperty;
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
    private Slider planeAngleSlider, planeDynamicCoefficientSlider;

    @FXML
    private Label planeAngleLabel, planeDynamicCoefficientLabel;

    @FXML
    private CheckBox showForceVectorsCheckBox;

    @FXML
    private Pane skaterPlanePane; // TODO Is this useful ? (dont think so)

    @FXML
    private Line angledPlaneLine;

    @FXML
    private HBox centerPanel;

    @FXML
    private TextField skaterMassField, skaterInitialHeightField; // TODO Only allow double values

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
     *  Triggers the animation start, putting the skater on top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void startEventHandler(ActionEvent actionEvent) {

        if(animationModel.isPausedProperty().get()) {
            System.out.println("Amination resumes!");
            pt.play();
        }
        else {
            System.out.println("Animation started !");
            animate(angledPlaneLine);
            animationModel.isPausedProperty().set(true);
        }

    }

    /**
     *  Triggers the pause of the animation, stopping the "fall" of the skater on the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void pauseEventHandler(ActionEvent actionEvent) {
        System.out.println("Animation paused!");
        pt.pause();
        animationModel.isPausedProperty().set(true);
    }

    /**
     *  Triggers the reset of the animation, putting the skater back on the top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void resetEventHandler(ActionEvent actionEvent) { // TODO Fix the crash error
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
        }

        Stage importDatabaseStage = new Stage();
        Scene databaseScene = new Scene(databaseContent, 400, 600);
        importDatabaseStage.setTitle("Import planets' acceleration values");
        importDatabaseStage.setScene(databaseScene);
        importDatabaseStage.setResizable(false);
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
            ex.printStackTrace();
        }

        Stage aboutStage = new Stage();
        Scene scene = new Scene(aboutContent, 920, 400);
        aboutStage.setMinWidth(920);
        aboutStage.setMinHeight(400);

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

    @FXML
    public void onSkaterInitialHeightEntered(ActionEvent actionEvent) {
        animationModel.getSkater().heightProperty().set(Double.parseDouble(skaterInitialHeightField.getText()));
    }

    public TonyHawkSimulatorController() {
//        if(animationModel.getPlane() instanceof AngledSkaterPlane) {
//            System.out.println("Angled");
//        } else if(animationModel.getPlane() instanceof ParabolaSkaterPlane) {
//            System.out.println("Parabola");
//        }
        this.showForceVectorsProperty = new SimpleBooleanProperty(false);
    }

    public void initialize() {

        skaterMassField.setTextFormatter(DOUBLE_PATTERN_TEXT_FORMATTER);
//        skaterInitialHeightField.setTextFormatter(DOUBLE_PATTERN_TEXT_FORMATTER);

        planeAngleSlider.valueProperty().bindBidirectional(animationModel.getPlane().planeCoefficient());
        planeAngleSlider.disableProperty().bind(animationModel.isPausedProperty().not());

        animationModel.getPlane().planeCoefficient().addListener((observableValue, number, t1) -> {
            double newAngle = (double) t1;
            String formattedNewAngleString = TWO_DECIMAL_PLACES.format(newAngle);
            planeAngleLabel.setText(formattedNewAngleString + " deg");
        });

        planeDynamicCoefficientSlider.valueProperty().bindBidirectional(animationModel.getPlane().kineticFrictionCoefficientProperty());

        animationModel.getPlane().kineticFrictionCoefficientProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double newDynamicCoefficient = (double) t1;
                String formattedNewDynamicCoefficient = TWO_DECIMAL_PLACES.format(newDynamicCoefficient);
                planeDynamicCoefficientLabel.setText("μ_k = " + formattedNewDynamicCoefficient);

            }
        });

        slowMotionRadioButton.selectedProperty().bindBidirectional(animationModel.isInSlowMotionProperty());


        showForceVectorsCheckBox.selectedProperty().bindBidirectional(this.showForceVectorsProperty);

        skater.layoutXProperty().bind(angledPlaneLine.startXProperty());
        skater.layoutYProperty().bind(angledPlaneLine.startYProperty());

        animationModel.animationSpeedProperty().set(10);

        // MARK - The next change listeners are for testing purposes only

        animationModel.getSkater().skaterMassProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("hey btw you changed the skater mass");
            }
        });

        animationModel.getSkater().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("hey btw you changed the skater's initial height");
            }
        });

        this.showForceVectorsProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("hey, you changed the visibility of the force vectors. new value: " + t1);
            }
        });


    }


}
