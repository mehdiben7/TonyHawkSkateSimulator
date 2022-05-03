package ca.thetonyhawks.tonyhawksimulator.controller;

import ca.thetonyhawks.tonyhawksimulator.model.AnimationModel;
import ca.thetonyhawks.tonyhawksimulator.model.Planet;
import ca.thetonyhawks.tonyhawksimulator.model.Skater;
import ca.thetonyhawks.tonyhawksimulator.model.SkaterAnimationTimer;
import ca.thetonyhawks.tonyhawksimulator.model.planes.AngledSkaterPlane;
import ca.thetonyhawks.tonyhawksimulator.model.planes.SkaterPlane;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * The Controller class linked to the main user interface window of the simulator <br>
 * whose FXML file is <em>UserInterface.fxml</em>
 */
public class TonyHawkSimulatorController {

    public static final ObjectProperty<Interpolator> BEZIER_INTERPOLATOR_PROPERTY = new SimpleObjectProperty<>(Interpolator.SPLINE(0.76, 0.05, 0.95, 0.69));

    public static final String MATCH_DECIMAL_CHARACTERS_REGEX = "\\d*";
    public static final String REPLACE_NON_DECIMAL_CHARACTERS_REGEX = "[^\\d.]";

    public static final AngledSkaterPlane DEFAULT_ANGLED_SKATER_PLANE = new AngledSkaterPlane(SkaterPlane.DEFAULT_FRICTION_COEFFICIENT,
            AngledSkaterPlane.DEFAULT_PLANE_ANGLE);


    public final AnimationModel animationModel = new AnimationModel(new Planet(Planet.PLANETS_GRAVITATIONAL_CONSTANTS[0]),
            DEFAULT_ANGLED_SKATER_PLANE,
            new Skater(Skater.DEFAULT_SKATER_MASS),
            false, this);
    private final SkaterAnimationTimer animationTimer = new SkaterAnimationTimer(animationModel);

    private final BooleanProperty showForceVectorsProperty;

    @FXML
    private ImageView skater;

    @FXML
    private BorderPane backgroundPane;

    @FXML
    private VBox rightPane, leftPane;

    @FXML
    private HBox bottomPane;



    /**
     * Menu item buttons that trigger the opening of complementary windows
     */
    @FXML
    private MenuItem about, database;

    /**
     * Start, pause, and reset buttons
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
    private TextField skaterMassField;

    @FXML
    private Label skaterPositionLabel, skaterSpeedLabel, skaterAccelerationLabel;

    @FXML
    public ComboBox<String> planetComboBox;

    @FXML
    private Pane midpane;

    @FXML
    private BarChart<String, Double> energyBarChart;

    private Path path1;

    private PathTransition pt;
    private final Path path = new Path();

    private final DoubleProperty upperEnergyBoundProperty;


    /**
     * Sets up the bar chart for displaying kinetic and potential gravitational energy
     */
    public void setUpEnergyChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Energy type");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Energy (in J)");
        energyBarChart.setAnimated(false);
        yAxis.setAnimated(false);
        yAxis.setForceZeroInRange(false);
    }


    /**
     * Updates the values of energy of the skater to the chart
     *
     * @param kineticEnergy                The skater's kinetic energy (in J)
     * @param potentialGravitationalEnergy The skater's potential gravitational energy (in J)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateEnergyValues(double kineticEnergy, double potentialGravitationalEnergy) {
        energyBarChart.getData().removeAll(energyBarChart.getData());

        XYChart.Series kineticEnergySeries = new XYChart.Series();
        kineticEnergySeries.setName("Kinetic Energy");
        kineticEnergySeries.getData().add(new XYChart.Data("E_k", kineticEnergy));


        XYChart.Series potentialGravitationalEnergySeries = new XYChart.Series();
        potentialGravitationalEnergySeries.setName("Potential Energy");
        potentialGravitationalEnergySeries.getData().add(new XYChart.Data("U_g", potentialGravitationalEnergy));

        energyBarChart.getData().addAll(kineticEnergySeries, potentialGravitationalEnergySeries);
        this.upperEnergyBoundProperty.set(Math.max(kineticEnergy, potentialGravitationalEnergy));
    }

    /**
     * Updates the value of the acceleration of the angled plane
     */
    private void updateAngledPlaneValues() {
        animationModel.getSkater().accelerationProperty().set(animationModel.getModelAcceleration());

    }

    /**
     * Updates the skater's mass in the animation's model
     */
    private void updateSkaterMassValue() {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
    }

    /**
     * Updates the fall duration of the skater (in s)
     */
    private void updateFallDuration() {
        animationModel.animationDurationProperty().set(animationModel.getModelFallDuration());
        double animationNormalSpeed = animationModel.animationDurationProperty().get();
        double animationSlowMotionSpeed = animationModel.animationDurationProperty().get() * 2;
        pt = new PathTransition(Duration.seconds(normalSpeedRadioButton.isSelected() ? animationNormalSpeed : animationSlowMotionSpeed), path, skater);

    }

    /**
     * Toggles the change between slow motion and regular speed of animation
     */
    @FXML
    private void motionSpeedChangeEventHandler() {
        double duration = normalSpeedRadioButton.isSelected() ? animationModel.animationDurationProperty().get() * 0.5 :
                animationModel.animationDurationProperty().get();

        pt.setDuration(Duration.seconds(duration));
    }

    /**
     * Sets up the animation of the skater along the plane
     */
    void setUpAngledPlaneAnimation() {

        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(angledPlaneLine.startXProperty());
        moveTo.yProperty().bind((angledPlaneLine.startYProperty().subtract(28)));

        LineTo lineTo = new LineTo();
        lineTo.xProperty().bind(angledPlaneLine.endXProperty());
        lineTo.yProperty().bind((angledPlaneLine.endYProperty().subtract(28)));

        path.getElements().addAll(moveTo, lineTo);

        pt.setCycleCount(1);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.interpolatorProperty().bind(BEZIER_INTERPOLATOR_PROPERTY);
        pt.playFromStart();
    }

    /**
     * Sets up the animation for the parabola
     *
     * @author Changfan & David
     * @apiNote Method is not functional yet
     */
    @Deprecated
    void animate2() { // TODO Use this method

        pt.setDuration(Duration.millis(5000));
        pt.setNode(skater);
        pt.setPath(path1);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount((int) 4f);
        pt.setAutoReverse(true);

        pt.play();
    }

    /**
     * Triggers the animation start, putting the skater on top of the plane
     */
    @FXML
    private void startEventHandler() {

        if (animationModel.hasBeenStartedBeforeProperty().get()) {
            if (animationModel.isPausedProperty().get()) {
                System.out.println("Paused -> Restarted");
                pt.play();
                animationTimer.start();
            } else {
                System.out.println("Animation resumes!");
                updateSkaterMassValue();
                updateAngledPlaneValues();
                updateFallDuration();
                pt.setInterpolator(BEZIER_INTERPOLATOR_PROPERTY.get());
                animationTimer.start();
                pt.play();

            }
        } else {
            System.out.println("Animation started !");
            updateSkaterMassValue();
            updateAngledPlaneValues();
            updateFallDuration();
            setUpAngledPlaneAnimation();
            animationTimer.start();
            animationModel.hasBeenStartedBeforeProperty().set(true);
        }

    }

    /**
     * Triggers the reset of the animation, putting the skater back on the top of the plane
     */
    @FXML
    private void resetEventHandler() {
        animationTimer.stop();
        if (pt != null) { // Prevents the animation from being reset if it has been started before
            animationModel.isPausedProperty().set(false);
            pt.stop();
            updateSkaterMassValue();
            updateAngledPlaneValues();
            updateFallDuration();
            animationTimer.start();
            pt.playFromStart();
        }
    }

    /**
     * Triggers the pause of the animation, stopping the "fall" of the skater on the plane
     */
    @FXML
    private void pauseEventHandler() {
        System.out.println("Animation paused!");
        animationModel.isPausedProperty().set(true);
        animationTimer.pause();
        pt.pause();
//        animationModel.hasBeenStartedBeforeProperty().set(true);
    }

    /**
     * Can be called by other classes to pause the animation of the skater and keep physical coherence
     */
    public void pauseAnimation() {
        pt.pause();
    }

    /**
     * Triggers the opening of the <em>import</em> database window
     */
    @FXML
    private void showImportDatabaseWindow() {
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
     * Triggers the opening of the <em>about</em> window
     */
    @FXML
    private void showAboutWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ca.thetonyhawks.tonyhawksimulator/About.fxml"));
        Parent aboutContent = null;
        try {
            aboutContent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Stage aboutStage = new Stage();
        Scene scene = new Scene(aboutContent);

        aboutStage.setTitle("About this project");
        aboutStage.setScene(scene);
        aboutStage.show();

    }

    @FXML
    public void onSkaterMassEntered() {
        animationModel.getSkater().skaterMassProperty().set(Double.parseDouble(skaterMassField.getText()));
        updateAngledPlaneValues();

    }


    public TonyHawkSimulatorController() {
        this.showForceVectorsProperty = new SimpleBooleanProperty(false);
        this.upperEnergyBoundProperty = new SimpleDoubleProperty(2_500.0);
    }

    public void changePlanet(ActionEvent event){
        System.out.println("Changed planet");
        if (planetComboBox.getValue() == "Moon"){
            backgroundPane.getStylesheets().clear();
            backgroundPane.getStylesheets().add(getClass().getResource("..\\CSS_stylesheets\\Moon.css").toExternalForm());

        } else if(planetComboBox.getValue() == "Mars"){

        }
    }


    public void initialize() {


        setUpEnergyChart();

        // Temporary - Disabling non-functional parts of the UI for the beta


        showForceVectorsCheckBox.setDisable(true);


        midpane.rotateProperty().bind(planeAngleSlider.valueProperty().subtract(45));
        planetComboBox.setItems(animationModel.getPlanet().getPlanetNameObservableList());
        planetComboBox.getSelectionModel().selectFirst();
        planetComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            int selectedItemIndex = planetComboBox.getSelectionModel().getSelectedIndex();
            double gravitationalConstant = Planet.PLANETS_GRAVITATIONAL_CONSTANTS[selectedItemIndex];
            animationModel.getPlanet().getGravitationalAccelerationProperty().set(gravitationalConstant);

        });
        planetComboBox.disableProperty().bind(animationModel.isPausedProperty().not());



        skaterMassField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches(MATCH_DECIMAL_CHARACTERS_REGEX)) {
                skaterMassField.setText(t1.replaceAll(REPLACE_NON_DECIMAL_CHARACTERS_REGEX, ""));

            }
        });


        skaterMassField.setText(String.valueOf(animationModel.getSkater().skaterMassProperty().get()));
        skaterMassField.disableProperty().bind(animationModel.isPausedProperty().not());

        planeAngleSlider.valueProperty().bindBidirectional(animationModel.getPlane().planeCoefficientProperty());
        planeAngleSlider.disableProperty().bind(animationModel.isPausedProperty().not());

        planeDynamicCoefficientSlider.valueProperty().bindBidirectional(animationModel.getPlane().kineticFrictionCoefficientProperty());

        slowMotionRadioButton.selectedProperty().bindBidirectional(animationModel.isInSlowMotionProperty());

        showForceVectorsCheckBox.selectedProperty().bindBidirectional(this.showForceVectorsProperty);

        skater.layoutXProperty().bind(angledPlaneLine.startXProperty().add(55));
        skater.layoutYProperty().bind(angledPlaneLine.startYProperty().subtract(88));

        animationModel.getPlane().planeCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newAngle = (double) t1;
            String formattedNewAngleString = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newAngle);
            planeAngleLabel.setText(formattedNewAngleString + " deg");
        });
        animationModel.getPlane().kineticFrictionCoefficientProperty().addListener((observableValue, number, t1) -> {
            double newDynamicCoefficient = (double) t1;
            String formattedNewDynamicCoefficient = AnimationModel.TWO_DECIMALS_PHYSICS_DECIMAL_FORMAT.format(newDynamicCoefficient);
            planeDynamicCoefficientLabel.setText("μ_k = " + formattedNewDynamicCoefficient);

        });

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




    }

}
