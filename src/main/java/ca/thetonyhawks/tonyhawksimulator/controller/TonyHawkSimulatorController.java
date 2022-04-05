package ca.thetonyhawks.tonyhawksimulator.controller;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import ca.thetonyhawks.tonyhawksimulator.model.Planet;
import ca.thetonyhawks.tonyhawksimulator.model.Skater;
import ca.thetonyhawks.tonyhawksimulator.model.SkaterAnimationTimer;
import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.ParabolaSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.CubicCurveTo;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 *  The Controller class linked to the main user interface window of the simulator <br>
 *   whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {

    public static final String MATCH_DECIMAL_CHARACTERS_REGEX = "\\d*";
    public static final String REPLACE_NON_DECIMAL_CHARACTERS_REGEX = "[^\\d.]";

    public static final AngledSkaterPlane DEFAULT_ANGLED_SKATER_PLANE = new AngledSkaterPlane(SkaterPlane.DEFAULT_FRICTION_COEFFICIENT, 45);
    public static final ParabolaSkaterPlane DEFAULT_PARABOLA_SKATER_PLANE = new ParabolaSkaterPlane(SkaterPlane.DEFAULT_FRICTION_COEFFICIENT, 2);

    private AnimationModel animationModel = new AnimationModel(new Planet(Planet.PLANETS_GRAVITATIONAL_CONSTANTS[0]), DEFAULT_ANGLED_SKATER_PLANE, new Skater(Skater.DEFAULT_SKATER_MASS), false, 10 );

    private SkaterAnimationTimer skaterAnimationTimer;

    private BooleanProperty showForceVectorsProperty;

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
    private Line angledPlaneLine;

    @FXML
    private QuadCurve parabolaPlaneCurve;

    private CubicCurveTo planeParabola;

    @FXML
    private HBox centerPanel;

    @FXML
    private TextField skaterMassField, skaterInitialHeightField;

    @FXML
    private Label skaterPositionLabel, skaterSpeedLabel, skaterAccelerationLabel;

    @FXML
    private ComboBox<String> planetComboBox, planeTypesComboBox;

    @FXML
    private Pane midpane;

    private Path path1;

    private PathTransition pt;
    private Path path = new Path();



    private void updateAngledPlaneValues() {
        animationModel.getSkater().accelerationProperty().set(animationModel.getModelAcceleration());

    }

    private void updateSkaterMassValue() {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
    }

    /**
     *  Toggles the change between slow motion and regular speed of animation
     * @param actionEvent An event representing the switch between radio buttons
     */
    @FXML
    private void motionSpeedChangeEventHandler(ActionEvent actionEvent) {
        double duration = normalSpeedRadioButton.isSelected() ? animationModel.animationDurationProperty().get() * 0.5 :
                                                                animationModel.animationDurationProperty().get();

        pt.setDuration(Duration.seconds(duration));
    }

    void animate(Line line) {

        double animationNormalSpeed = animationModel.animationDurationProperty().get() * 0.5;
        double animationSlowMotionSpeed = animationModel.animationDurationProperty().get(); // The two values should be inverted, but it doesn't work IDK why

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

   void animate2(){



        pt.setDuration(Duration.millis(5000));
        pt.setNode(skater);
        pt.setPath(path1);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount((int) 4f);
        pt.setAutoReverse(true);

        pt.play();
    }



    /**
     *  Triggers the animation start, putting the skater on top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void startEventHandler(ActionEvent actionEvent) {

        if(animationModel.isPausedProperty().get()) {
            System.out.println("Amination resumes!");
            updateSkaterMassValue();
            updateAngledPlaneValues();
            skaterAnimationTimer.start();
            pt.play();
        }
        else {
            System.out.println("Animation started !");
            updateSkaterMassValue();
            updateAngledPlaneValues();
            skaterAnimationTimer.start();
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
        skaterAnimationTimer.stop();
        pt.pause();
        animationModel.isPausedProperty().set(true);
    }

    /**
     *  Triggers the reset of the animation, putting the skater back on the top of the plane
     * @param actionEvent An event representing the click on the about menu item button
     */
    @FXML
    private void resetEventHandler(ActionEvent actionEvent) {
        // TODO Prevent reset if animation hasn't been started before
        System.out.println("reset");
        skaterAnimationTimer.stop();
        pt.stop();
        updateSkaterMassValue();
        updateAngledPlaneValues();
        pt.playFromStart();
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
        updateAngledPlaneValues();

    }

    @FXML
    public void onSkaterInitialHeightEntered(ActionEvent actionEvent) {
        animationModel.getSkater().initialHeightProperty().set(Double.parseDouble(skaterInitialHeightField.getText()));
    }

    public TonyHawkSimulatorController() {
        this.showForceVectorsProperty = new SimpleBooleanProperty(false);
        this.skaterAnimationTimer = new SkaterAnimationTimer(animationModel);
    }

    public void initialize() {

        planetComboBox.setItems(animationModel.getPlanet().getPlanetNameObservableList());
        planetComboBox.getSelectionModel().selectFirst();
        planetComboBox.valueProperty().addListener((observableValue, s, t1) -> {


            int selectedItemIndex = planetComboBox.getSelectionModel().getSelectedIndex();

            double gravitationalConstant = Planet.PLANETS_GRAVITATIONAL_CONSTANTS[selectedItemIndex];
            System.out.println("new grav const. : " + gravitationalConstant);
            animationModel.getPlanet().getGravitationalAccelerationProperty().set(gravitationalConstant);

        });

        planeTypesComboBox.setItems(animationModel.planeTypesProperty());
        planeTypesComboBox.getSelectionModel().selectFirst();
        planeTypesComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            if (t1.equalsIgnoreCase("Angled plane")) {
                System.out.println("An angled plane");
                midpane.getChildren().remove(planeParabola);
                midpane.getChildren().add(angledPlaneLine);
                animationModel.setPlane(DEFAULT_ANGLED_SKATER_PLANE);



            } else if (t1.equalsIgnoreCase("Parabola")) {
                //*********************************** initializes path without fxml
                path1 = new Path();
                //width of the arc path
                int x = 400;
                //height
                int height=x-100;
                planeParabola = new CubicCurveTo();

                planeParabola.setControlX1(200);
                planeParabola.setControlY1(x);
                planeParabola.setControlX2(300);
                planeParabola.setControlY2(x);
                planeParabola.setX(400);
                planeParabola.setY(100);

                path1.setStroke(Color.BLACK);
                path1.setStrokeWidth(5);
                path1.setStrokeType(StrokeType.INSIDE);

                path1.getElements().add (new MoveTo(100, 100));
                path1.getElements().add (planeParabola);


                //**********************************************
                System.out.println("A parabola");
                //path1.setVisible(true);
//                parabolaPlaneCurve.setVisible(true);
//                angledPlaneLine.setVisible(false);
                animationModel.setPlane(DEFAULT_PARABOLA_SKATER_PLANE);
                midpane.getChildren().add(path1);
                midpane.getChildren().remove(angledPlaneLine);

            }
            // This should never happen, but if the selected option is neither Angled plane nor Parabola, we do
            // not do anything
        });



        skaterMassField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(MATCH_DECIMAL_CHARACTERS_REGEX)) {
                skaterMassField.setText(t1.replaceAll(REPLACE_NON_DECIMAL_CHARACTERS_REGEX, ""));

            }
        });
        skaterInitialHeightField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(MATCH_DECIMAL_CHARACTERS_REGEX)) {
                skaterInitialHeightField.setText(t1.replaceAll(REPLACE_NON_DECIMAL_CHARACTERS_REGEX, ""));
            }
        });

        skaterMassField.setText(String.valueOf(animationModel.getSkater().skaterMassProperty().get()));
        skaterInitialHeightField.setText(String.valueOf(animationModel.getSkater().initialHeightProperty().get()));

        planeAngleSlider.valueProperty().bindBidirectional(animationModel.getPlane().planeCoefficientProperty());
        planeAngleSlider.disableProperty().bind(animationModel.isPausedProperty().not());

        animationModel.getPlane().planeCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newAngle = (double) t1;
            String formattedNewAngleString = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newAngle);
            planeAngleLabel.setText(formattedNewAngleString + " deg");
        });

        planeDynamicCoefficientSlider.valueProperty().bindBidirectional(animationModel.getPlane().kineticFrictionCoefficientProperty());

        animationModel.getPlane().kineticFrictionCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newDynamicCoefficient = (double) t1;
            String formattedNewDynamicCoefficient = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newDynamicCoefficient);
            planeDynamicCoefficientLabel.setText("μ_k = " + formattedNewDynamicCoefficient);

        });



        slowMotionRadioButton.selectedProperty().bindBidirectional(animationModel.isInSlowMotionProperty());


        showForceVectorsCheckBox.selectedProperty().bindBidirectional(this.showForceVectorsProperty);

        skater.layoutXProperty().bind(angledPlaneLine.startXProperty());
        skater.layoutYProperty().bind(angledPlaneLine.startYProperty());

        animationModel.getSkater().positionProperty().addListener((observableValue, number, t1) -> {
            double newPosition = (double) t1;
            String formattedNewPosition = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newPosition);
            skaterPositionLabel.setText("x = " + formattedNewPosition + " m");
        });

        animationModel.getSkater().velocityProperty().addListener((observableValue, number, t1) -> {
            double newSpeed = (double) t1;
            String formattedNewSpeed = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newSpeed);
            skaterSpeedLabel.setText("v = " + formattedNewSpeed + " m/s");
        });

        animationModel.getSkater().accelerationProperty().addListener((observableValue, number, t1) -> {
            double newAcceleration = (double) t1;
            String formattedNewAcceleration = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newAcceleration);
            System.out.println(formattedNewAcceleration);
            skaterAccelerationLabel.setText("a = " + formattedNewAcceleration + " m/s^2");
        });

        animationModel.animationDurationProperty().set(10);
    }


}
